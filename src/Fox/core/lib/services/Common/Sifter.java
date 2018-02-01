package Fox.core.lib.services.Common;

import Fox.core.lib.general.utils.AcoustIDException;
import Fox.core.lib.general.utils.NoMatchesException;
import Fox.core.lib.services.acoustid.LookupByFP.sources.ByFingerPrint;
import Fox.core.lib.services.acoustid.LookupByFP.sources.Error;
import Fox.core.lib.services.acoustid.LookupByFP.sources.Recording;
import Fox.core.lib.services.acoustid.LookupByFP.sources.Result;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sifter
{
    public Sifter()
    {
    }

    private static int TracksEncounter(@NotNull ByFingerPrint target)
            throws AcoustIDException
    {
        int result = 0;
        if (target.hasError() && target.getErr().hasMessage())
        {
            Error err = target.getErr();
            throw new AcoustIDException(" TE module. Error code: " + err.getCode() + " message: "+ err.getMessage());
        }

        for(Result elem : target.getResult())
            if (elem.hasRecordings())
                result += elem.getRecordings().size();

        return result;
    }

    private static List<Recording> CompressResult(@NotNull ByFingerPrint target)
            throws AcoustIDException
    {
        if (target.hasError() && target.getErr().hasMessage())
        {
            Error err = target.getErr();
            throw new AcoustIDException(" CR module. Error code: " + err.getCode() + " message: "+ err.getMessage());
        }

        List<Recording> RecordingsStorage = new ArrayList<>();

        for(Result elem:target.getResult())
            if (elem.hasRecordings())
                for(Recording elemR:elem.getRecordings())
                    if (elemR.hasArtists())
                        RecordingsStorage.add(new Recording(elemR));

        return RecordingsStorage;
    }

    private static boolean IsEntire(String self, List<String> elems)
    {

        if (self == null || elems == null)
            return false;

        for(String elem:elems)
            if (elem.equalsIgnoreCase(self))
                return true;

        return false;
    }

    private static void SiftingByArtist(@NotNull List<Recording> recordingList)
    {
        Map<String, Integer> AssistMap = new HashMap<>();
        List<String> AssistList = new ArrayList<>();
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

        for(Map.Entry pair : AssistMap.entrySet())
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
                if (!IsEntire(Artist, AssistList))
                {
                    recordingList.remove(i--);
                    size--;
                }
            }
        }
    }

    private static SimpleInfo TrustSift(@NotNull List<Recording> recordingList)
    {
        SiftingByArtist(recordingList);
        List<SimpleInfo> simpleInfos = FrequentSift(recordingList, 1);

        if (simpleInfos != null && !simpleInfos.isEmpty())
            return simpleInfos.get(0);

        return new SimpleInfo();
    }

    /*private static List<Recording> RecSortingBackward(@NotNull List<Recording> elems)
    {
        return Sorting(elems, false);
    }

    private static List<Recording> RecordingSortingForward(@NotNull List<Recording> elems)
    {
        return Sorting(elems, true);
    }*/

    private static List<SimpleInfo> SimpleInfoSortingForward(@NotNull List<SimpleInfo> elems)
    {
        return Sorting(elems, true);
    }

    private static List<SimpleInfo> SimpleInfoSortingBackward(@NotNull List<SimpleInfo> elems)
    {
        return Sorting(elems, false);
    }

    private static List<SimpleInfo> Sorting(@NotNull List<SimpleInfo> elems, boolean IsForward)
    {
        int left = 0;
        SimpleInfo buff;
        int right = elems.size() - 1;
        do
        {
            for (int i = left; i < right; i++)
            {
                if ((!IsForward && elems.get(i).compareTo(elems.get(i + 1)) < 0)
                        || (IsForward && elems.get(i).compareTo(elems.get(i + 1)) > 0))
                {
                    buff = elems.get(i);
                    elems.remove(i);
                    elems.add(i + 1, buff);
                }
            }
            right--;
            for (int i = right; i > left; i--)
            {
                if ((!IsForward && elems.get(i).compareTo(elems.get(i - 1)) > 0)
                        || (IsForward && elems.get(i).compareTo(elems.get(i - 1)) < 0))
                {
                    buff = elems.get(i);
                    elems.remove(i);
                    elems.add(i - 1, buff);
                }
            }
            left++;
        }
        while (left < right);

        return elems;
    }

    private static List<SimpleInfo> MergingByUsages(@NotNull List<SimpleInfo> ElemList)
    {
        Map<String, Integer> AssistMap = new HashMap<>();
        Map<String, Boolean> SecAssistMap = new HashMap<>();
        List<SimpleInfo> Target = new ArrayList<>();

        for (SimpleInfo Elem : ElemList)
        {
            if (Elem.hasMBID())
            {
                String mbid = Elem.getMBID();
                Integer count = AssistMap.get(mbid);
                AssistMap.put(mbid, count == null ? Elem.getUsages() : Elem.getUsages() + count);
            }
        }

        for (String Elem : AssistMap.keySet())
            for (SimpleInfo InfoElem : ElemList)
                if (InfoElem.hasMBID()
                        && InfoElem.getMBID().equalsIgnoreCase(Elem)
                        && SecAssistMap.get(Elem) == null)
                {
                    SimpleInfo path = new SimpleInfo(InfoElem.getArtist(),
                                                     InfoElem.getMBID(),
                                                     InfoElem.getTitle(),
                                                     AssistMap.get(Elem)
                    );

                    SecAssistMap.put(Elem, true);
                    Target.add(path);
                    break;
                }

        return Target;
    }

    private static SimpleInfo Converting(Recording source)
    {
        SimpleInfo temp = null;

        if (source != null)
        {
            temp = new SimpleInfo();

            temp.setMBID(source.getId());

            if (source.hasArtists())
                temp.setArtist(source.getArtists().get(0).getName());

            temp.setTitle(source.getTitle());
            temp.setUsages(source.getSources());
        }

        return temp;
    }

    private static List<SimpleInfo> FrequentSift(@NotNull List<Recording> recordingList,
                                                 int count)
    {

        List<SimpleInfo> IntermediateList = new ArrayList<>();

        for (Recording RecordingListElem : recordingList)
            IntermediateList.add(Sifter.Converting(RecordingListElem));

        IntermediateList = Sifter.MergingByUsages(IntermediateList);

        IntermediateList = Sifter.SimpleInfoSortingBackward(IntermediateList);
        int size1 = IntermediateList.size();

        List<SimpleInfo> FinalList = new ArrayList<>();

        for(int i = 0, size = count == 0 ? size1 : count > size1 ? size1 : count; i < size; i++)
            FinalList.add(IntermediateList.get(i));

        return FinalList;
    }

    public static List<SimpleInfo> Sifting(@NotNull ByFingerPrint target,
                                           boolean trust,
                                           int count)
            throws AcoustIDException,
                   NoMatchesException
    {
        int encounter = Sifter.TracksEncounter(target);

        List<SimpleInfo> store = new ArrayList<>();

        if (encounter == 0)
            throw new NoMatchesException("No matches at AcoustID");

        if (count < 0)
            throw new IllegalArgumentException("Impossible to return less that zero or equals zero size of results.");

        if (encounter == 1)
        {
            SimpleInfo info = new SimpleInfo();

            if (target.hasResults())
            {
                Result result = target.getResult()
                                      .get(0);

                if (result.hasRecordings())
                    info = Sifter.Converting(result.getRecordings().get(0));
            }

            store.add(info);
        }
        else
        {
            List<Recording> compressResult = Sifter.CompressResult(target);

            if (trust)
                store.add(Sifter.TrustSift(compressResult));
            else
                store.addAll(Sifter.FrequentSift(compressResult,
                                                 count));
        }
        return store;
    }
}