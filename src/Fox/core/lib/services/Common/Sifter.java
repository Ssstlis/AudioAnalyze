package Fox.core.lib.services.Common;

import Fox.core.lib.general.DOM.FingerPrint;
import Fox.core.lib.general.DOM.ID3V2;
import Fox.core.lib.general.DOM.ID3V2.ID3V2Comparator;
import Fox.core.lib.general.utils.AcoustIDException;
import Fox.core.lib.general.utils.NoMatchesException;
import Fox.core.lib.general.utils.Sorts;
import Fox.core.lib.services.acoustid.LookupByFP.sources.*;
import Fox.core.lib.services.acoustid.LookupByFP.sources.Error;
import Fox.core.main.SearchLib;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static Fox.core.lib.general.utils.Sorts.RelativeSort;
import static Fox.core.lib.general.utils.Sorts.Sort;

public class Sifter
{
    private static SimpleInfo.SimpleInfoComparatorByUsages SimpleInfoComparatorByUsages = new SimpleInfo.SimpleInfoComparatorByUsages();
    private static Sorts.Relativator RecordingRelativator = new Recording.RecordingRelativator();
    private static Logger logger;


    public Sifter()
    {
    }

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
                Result result = target.getResult()
                        .get(0);

                if (result.hasRecordings())
                    info = Converting(result.getRecordings().get(0));
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
        List<SimpleInfo> IntermediateList = Converting(recordingList);
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

        for(int i = 0, size = count == 0 ? size1 : count > size1 ? size1 : count; i < size; i++)
            FinalList.add(IntermediateList.get(i));

        if (logger.isDebugEnabled())
            logger.debug("Frequent sift done");
        return FinalList;
    }

    /**Sifting processing that return a one SimpleInfo instance, represents the smart sift
     * @param recordingList target list to sifting from
     * @return SimpleInfo instance
     */
    private static List<SimpleInfo> TrustSift(@NotNull List<Recording> recordingList,
                                        @NotNull FingerPrint FP)
    {
        if (logger.isDebugEnabled())
            logger.debug("Trust sift start");
        SiftingByArtist(recordingList);
        if (logger.isDebugEnabled())
            logger.debug("Artist sift done");
        recordingList = RecordingRelativeSortingForward(recordingList, Integer.parseInt(FP.getDuration()));
        if (logger.isDebugEnabled())
            logger.debug("Relative sort done");
        List<SimpleInfo> IntermediateList = Converting(recordingList);
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
     * @return sorting list from the smallest to the largest
     */
    private static List<SimpleInfo> SimpleInfoSortingForwardByUsage(@NotNull List<SimpleInfo> elems)
    {
        try
        {
            Sort(elems, SimpleInfoComparatorByUsages, true);
        }
        catch (IllegalArgumentException ignore)
        {

        }
        return elems;
    }

    /**
     * @param elems target list to sorting
     * @return sorting list from the largest to the smallest
     */
    private static List<SimpleInfo> SimpleInfoSortingBackwardByUsage(@NotNull List<SimpleInfo> elems)
    {
        try
        {
            Sort(elems, SimpleInfoComparatorByUsages, false);
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
            RelativeSort(elems, RecordingRelativator, duration, true);
        }
        catch (IllegalArgumentException ignore)
        {

        }
        return elems;
    }

    /**
     * @param elems target list to sorting
     * @return sorting list from the largest to the smallest
     */
    private static List<Recording> RecordingRelativeSortingBackward(@NotNull List<Recording> elems,
                                                                    Integer duration)
    {
        try
        {
            RelativeSort(elems, RecordingRelativator, duration, false);
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
     * @param recordingList Recording list, will be sifting
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

        for(int i = 0, size = recordingList.size(); i < size; i++)
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

    /**
     * Converting Recording list to SimpleInfo list.
     * @param recordingList What Recording list you need to convert into SimpleInfo list
     * @return converting object
     */
    private static List<SimpleInfo> Converting(@NotNull List<Recording> recordingList)
    {
        List<SimpleInfo> temp = new ArrayList<>(recordingList.size());

        for(Recording elem : recordingList)
            temp.add(Converting(elem));

        return temp;
    }

    /** Converting one Recording object to SimpleInfo object
     * @param source What Recording you need to convert into SimpleInfo
     * @return converting object
     */
    private static SimpleInfo Converting(Recording source)
    {
        SimpleInfo temp = null;

        if (source != null)
        {
            temp = new SimpleInfo();

            temp.setTrackMBID(source.getId());

            if (source.hasArtists())
            {
                Artist artist = source.getArtists()
                                      .get(0);

                StringBuilder ArtistNameBuilder = new StringBuilder();
                for(Artist elem : source.getArtists())
                {
                    ArtistNameBuilder.append(elem.getName()).append(((elem.hasJoinPhrase()) ? elem.getJoinphrase() : ""));
                }
                temp.setArtist(ArtistNameBuilder.toString());
                temp.setArtistMBID(artist.getId());
            }

            temp.setTitle(source.getTitle());
            temp.setUsages(source.getSources());
        }

        return temp;
    }

    public static List<ID3V2> Choosing(List<ID3V2> temp)
    {
        Sort(temp, new ID3V2Comparator(), true);
        List<ID3V2> result = new ArrayList<>();
        result.add(temp.get(0));
        return result;
    }
}