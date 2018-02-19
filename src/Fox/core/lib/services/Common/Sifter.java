package Fox.core.lib.services.Common;

import Fox.core.lib.general.data.FingerPrint;
import Fox.core.lib.general.data.ID3V2;
import Fox.core.lib.general.data.ID3V2.ID3V2Comparator;
import Fox.core.lib.general.utils.AcoustIDException;
import Fox.core.lib.general.utils.NoMatchesException;
import Fox.core.lib.services.AcoustID.LookupByFP.sources.*;
import Fox.core.lib.services.AcoustID.LookupByFP.sources.Error;
import Fox.core.lib.services.AcoustID.LookupByFP.sources.Recording.RecordingRelativator;
import Fox.core.lib.services.AcoustID.LookupByFP.sources.Recording.RecordingSimpleInfoConverter;
import Fox.core.main.SearchLib;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static Fox.core.lib.general.utils.Sorts.*;

public class Sifter
{
    private final static SimpleInfo SimpleInfoComparatorByUsages = new SimpleInfo();
    private final static Relativator RecordingRelativator = new RecordingRelativator();
    private final static RecordingSimpleInfoConverter converter = new RecordingSimpleInfoConverter();
    private static Logger logger;

    public static List<SimpleInfo> Sifting(FingerPrint FPrint,
                                           @NotNull ByFingerPrint target,
                                           int count,
                                           boolean trust)
            throws
            AcoustIDException,
            NoMatchesException
    {
        logger = LoggerFactory.getLogger(SearchLib.class);
        if (logger.isDebugEnabled())
            logger.debug("Start sifting for {}\n:{}", FPrint.getLocation(), FPrint.getDuration());
        if (count < 0)
            throw new IllegalArgumentException("Impossible to return less that zero or equals zero size of results.");

        int encounter = TracksEncounter(target);
        if (logger.isDebugEnabled())
            logger.debug("Encounter done");
        if (encounter == 0)
            throw new NoMatchesException("No matches at AcoustID");

        List<SimpleInfo> store = new ArrayList<>();

        if (encounter == 1)
        {

            if (logger.isDebugEnabled())
                logger.debug("Encounter first scenario");
            SimpleInfo info = new SimpleInfo();

            if (target.hasResults())
            {
                Result result = target.getResult().get(0);

                if (result.hasRecordings())
                    info = Convert(result.getRecordings().get(0), converter);
            }

            store.add(info);
        }
        else
        {
            if (logger.isDebugEnabled())
                logger.debug("Encounter second scenario");
            List<Recording> compressResult = Sifter.CompressResult(target);

            if (logger.isDebugEnabled())
                logger.debug("Compressing done");

            if (trust)
                store.addAll(TrustSift(compressResult, FPrint));
            else
                store.addAll(FrequentSift(compressResult,
                        count));
        }
        return store;
    }

    /**Sifting processing that return a list of SimpleInfo instances, represents the sift
     * @param recordingList target list to sifting from
     * @param count number of SimpleInfo instances in list
     * @return list of SimpleInfo instances
     */
    private static List<SimpleInfo> FrequentSift(@NotNull List<Recording> recordingList,
                                                 int count)
    {

        if (logger.isDebugEnabled())
            logger.debug("Frequent sift start");
        List<SimpleInfo> IntermediateList = Convert(recordingList, converter);
        if (logger.isDebugEnabled())
            logger.debug("Convert done");
        IntermediateList = MergingByUsages(IntermediateList);
        if (logger.isDebugEnabled())
            logger.debug("Merging done");
        IntermediateList = SimpleInfoSortingBackwardByUsage(IntermediateList);
        if (logger.isDebugEnabled())
            logger.debug("Backward sorting done");
        int size1 = IntermediateList.size();

        List<SimpleInfo> FinalList = new ArrayList<>(IntermediateList.size());
        int max_size = count > size1 ? size1 : count;
        int count_ = count == 0 ? size1 : max_size;
        for(int i = 0; i < count_; i++)
            FinalList.add(IntermediateList.get(i));

        if (logger.isDebugEnabled())
            logger.debug("Frequent sift done");
        return FinalList;
    }

    /**Sifting processing that return a one SimpleInfo instance, represents the smart sift
     * @param recordingList target list to sifting from
     * @return SimpleInfo instance
     */
    private static List<SimpleInfo> TrustSift(@NotNull final List<Recording> recordingList,
                                              @NotNull FingerPrint FP)
    {
        if (logger.isDebugEnabled())
            logger.debug("Trust sift start");
        SiftingByArtist(recordingList);
        if (logger.isDebugEnabled())
            logger.debug("Artist sift done");
        List<Recording> recordingList_ = RecordingRelativeSortingForward(recordingList,
                                                                         Integer.parseInt(FP.getDuration()));
        if (logger.isDebugEnabled())
            logger.debug("Relative sort done");
        List<SimpleInfo> IntermediateList = Convert(recordingList_, converter);
        if (logger.isDebugEnabled())
            logger.debug("Converting done");
        IntermediateList = MergingByUsages(IntermediateList);
        if (logger.isDebugEnabled())
            logger.debug("Merging done. Trust sift done.");
        SimpleInfo MostUsage = Sifter.ExtractFromListWithRemoving(IntermediateList);
        IntermediateList.add(0, MostUsage);
        IntermediateList = IntermediateList.subList(0, 2);
        return IntermediateList;
    }

    private static SimpleInfo ExtractFromListWithRemoving(List<SimpleInfo> intermediateList)
    {
        SimpleInfo temp = null;
        if (intermediateList != null)
        {
            int size = intermediateList.size();
            if (size == 1)
            {
                temp = intermediateList.remove(0);
            }
            else
            {
                temp = intermediateList.get(0);
                int index = 0;
                for(int i = 1; i < size; i++)
                {
                    SimpleInfo info = intermediateList.get(i);
                    if (info.compareTo(temp) > 0)
                    {
                        temp = info;
                        index = i;
                    }
                }
                return intermediateList.remove(index);
            }
        }
        return temp;
    }

    /**
     * @param elems target list to sorting
     * @return sorting list from the largest to the smallest
     */
    private static List<SimpleInfo> SimpleInfoSortingBackwardByUsage(@NotNull List<SimpleInfo> elems)
    {
        try
        {
            BackwardSorting(elems, SimpleInfoComparatorByUsages);
        }
        catch (IllegalArgumentException ignore)
        {

        }
        return elems;
    }

    /**
     * @param elems target list to sorting
     * @return sorting list from the smallest to the largest
     */
    private static List<Recording> RecordingRelativeSortingForward(@NotNull List<Recording> elems,
                                                                   Integer duration)
    {
        try
        {
            ForwardRelativeSort(elems, RecordingRelativator, duration);
        }
        catch (IllegalArgumentException ignore)
        {

        }
        return elems;
    }

    /**
     * @param target AcoustID response instance
     * @return number of recordings in AcoustID response
     * @throws AcoustIDException if AcoustID response contains Error instance
     */
    private static int TracksEncounter(@NotNull ByFingerPrint target)
            throws
            AcoustIDException
    {
        int result = 0;
        if (target.hasError() && target.getErr().hasMessage())
        {
            Error err = target.getErr();
            throw new AcoustIDException("Error code: " + err.getCode() + " message: "+ err.getMessage());
        }

        for(Result elem : target.getResult())
            if (elem.hasRecordings())
                result += elem.getRecordings().size();

        return result;
    }

    /**
     * For easy processing needs to compress few lists to single list.
     * @param target is ByFingerPrint instance, contains list of Recording list.
     * @return Compressing Recording list form ByFingerPrint instance.
     * @throws AcoustIDException if target contains Error instance.
     */
    private static List<Recording> CompressResult(@NotNull ByFingerPrint target)
            throws
            AcoustIDException
    {
        if (target.hasError() && target.getErr().hasMessage())
        {
            Error err = target.getErr();
            throw new AcoustIDException("Error code: " + err.getCode() + " message: "+ err.getMessage());
        }

        List<Recording> RecordingsStorage = null;

        if (target.hasResults())
        {
            RecordingsStorage = new ArrayList<>();

            for (Result elem : target.getResult())
                if (elem.hasRecordings())
                    for (Recording elemR : elem.getRecordings())
                        if (elemR.hasArtists())
                            RecordingsStorage.add(new Recording(elemR));
        }
        return RecordingsStorage;
    }

    /**
     * Left few instances with artist, which meet in list in maximum count
     * @param recordingList Recording list that be sifting
     */
    private static void SiftingByArtist(@NotNull List<Recording> recordingList)
    {
        Map<String, Integer> AssistMap = new HashMap<>();
        List<String> AssistList = new ArrayList<>(recordingList.size());
        int max = 0;

        for(Recording elem : recordingList)
            if (elem.hasArtists())
            {
                String Artist = elem.getArtists()
                                    .get(0)
                                    .getName();

                Integer HowMuch = AssistMap.get(Artist);
                AssistMap.put(Artist, HowMuch == null ? 1 : HowMuch + 1);
            }

        for(Integer elem : AssistMap.values())
            if (elem > max)
                max = elem;

        for(Entry pair : AssistMap.entrySet())
            if ((Integer)pair.getValue() == max)
                AssistList.add((String)pair.getKey());
        int size = recordingList.size();
        for(int i = 0; i < size; i++)
        {
            Recording recording = recordingList.get(i);
            if (recording.hasArtists())
            {
                String Artist = recording.getArtists()
                                       .get(0)
                                       .getName();
                if (!AssistList.contains(Artist))
                {
                    recordingList.remove(i--);
                    size--;
                }
            }
        }
    }

    /**
     * @param ElemList target to merging
     * @return instance of merging target instances by usages
     */
    private static List<SimpleInfo> MergingByUsages(@NotNull List<SimpleInfo> ElemList)
    {
        Map<String, Boolean> SecAssistMap = new HashMap<>();
        List<SimpleInfo> Target = new ArrayList<>();

        for(SimpleInfo elem:ElemList)
        {
            int count = 0;
            String elemMBID = elem.getTrackMBID();
            if (elem.hasTrackMBID() && SecAssistMap.get(elemMBID) == null)
            {
                for (SimpleInfo elem1 : ElemList)
                    if (elemMBID.equalsIgnoreCase(elem1.getTrackMBID()))
                        count += elem1.getUsages();

                Target.add(new SimpleInfo(elem.getArtist(),
                                          elemMBID,
                                          elem.getTitle(),
                                          elem.getAlbum(),
                                          elem.getArtistMBID(),
                                          elem.getAlbumMBID(),
                                          count));
                SecAssistMap.put(elemMBID, true);
            }
        }
        return Target;
    }

    public static List<ID3V2> Choosing(List<ID3V2> temp)
    {
        if (temp != null && !temp.isEmpty())
        {
            ForwardSorting(temp, new ID3V2Comparator());
            List<ID3V2> result = new ArrayList<>();
            result.add(temp.get(0));
            return result;
        }
        return null;
    }
}