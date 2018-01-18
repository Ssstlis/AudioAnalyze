package Fox.core.lib.services.acoustid.LookupByFP;

import Fox.core.lib.services.Common.ParseSupport;
import Fox.core.lib.services.acoustid.AcoustIDResponse;
import Fox.core.lib.services.acoustid.LookupByFP.sources.*;
import Fox.core.lib.services.acoustid.LookupByFP.sources.Error;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AcoustIDStructureBuilder
{

    public AcoustIDStructureBuilder()
    {
    }

    public ByFingerPrint buildLookup(@NotNull AcoustIDResponse response)
    {

        if (!response.hasSource())
        {
            return null;
        }

        String source = response.getSource();
        JsonParser parser = new JsonParser();
        ByFingerPrint temp = null;

        try
        {
            JsonElement element = parser
                    .parse(source);

            JsonObject LookupJObj = element
                    .getAsJsonObject();

            temp = new ByFingerPrint();

            temp.setStatus((String)
                                   ParseSupport.GetSource(
                                           LookupJObj,
                                           "status",
                                           String.class
                                                         ));

            temp.setErr(buildError(LookupJObj.get("error")));
            temp.setResult(buildResultList(LookupJObj));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return temp;
    }

    private List<String> buildSecondarytypeList(JsonObject Obj)
    {
        List<String> temp = null;
        try
        {
            JsonArray SecondatytypesJList = (JsonArray)
                    ParseSupport.GetSource(
                            Obj,
                            "secondarytypes",
                            JsonArray.class
                                          );


            if (SecondatytypesJList != null)
            {
                temp = new ArrayList<>();
                int size = SecondatytypesJList.size();

                for (int i = 0; i < size; i++)
                    temp.add(SecondatytypesJList.get(i)
                                                .getAsString());
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return temp;
    }

    private Artist buildArtist(JsonElement element)
    {
        Artist temp = null;

        try
        {
            JsonObject ArtistObject = element.getAsJsonObject();

            temp = new Artist();

            temp.setName((String)
                                 ParseSupport.GetSource(
                                         ArtistObject,
                                         "name",
                                         String.class
                                                       ));


            temp.setId((String)
                               ParseSupport.GetSource(
                                       ArtistObject,
                                       "id",
                                       String.class
                                                     ));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return temp;
    }

    private Date buildDate(JsonElement element)
    {
        Date temp = null;

        try
        {
            JsonObject DateObj = element.getAsJsonObject();

            temp = new Date();

            temp.setDay((int)
                                ParseSupport.GetSource(
                                        DateObj,
                                        "day",
                                        int.class
                                                      ));

            temp.setMonth((int)
                                  ParseSupport.GetSource(
                                          DateObj,
                                          "month",
                                          int.class
                                                        ));

            temp.setYear((int)
                                 ParseSupport.GetSource(
                                         DateObj,
                                         "year",
                                         int.class
                                                       ));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return temp;
    }

    private Error buildError(JsonElement element)
    {
        Error temp = null;
        try
        {
            JsonObject ErrJObj = element.getAsJsonObject();

            temp = new Error();

            temp.setMessage((String)
                                    ParseSupport.GetSource(
                                            ErrJObj,
                                            "message",
                                            String.class
                                                          ));

            temp.setCode((int)
                                 ParseSupport.GetSource(
                                         ErrJObj,
                                         "code",
                                         int.class
                                                       ));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return temp;
    }

    private List<Result> buildResultList(JsonObject Obj)
    {
        List<Result> ResultList = null;
        try
        {
            JsonArray ResultsJList = (JsonArray)
                    ParseSupport.GetSource(
                            Obj,
                            "results",
                            JsonArray.class
                                          );

            if (ResultsJList != null)
            {
                ResultList = new ArrayList<>();
                int size = ResultsJList.size();

                for (int i = 0; i < size; i++)
                    ResultList.add(buildResult(ResultsJList.get(i)));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return ResultList;
    }

    private Result buildResult(JsonElement element)
    {
        Result temp = null;

        try
        {
            JsonObject ResultJObj = element.getAsJsonObject();

            temp = new Result();

            temp.setId((String)
                               ParseSupport.GetSource(
                                       ResultJObj,
                                       "id",
                                       String.class
                                                     ));

            temp.setScore((float)
                                  ParseSupport.GetSource(
                                          ResultJObj,
                                          "score",
                                          float.class
                                                        ));

            temp.setRecordings(buildRecordingList(ResultJObj));
            temp.setReleasegroups(buildReleasegroupList(ResultJObj));
            temp.setReleases(buildReleaseList(ResultJObj));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return temp;
    }

    private List<Recording> buildRecordingList(JsonObject Obj)
    {
        List<Recording> temp = null;

        try
        {
            JsonArray RecordingsJList = (JsonArray)
                    ParseSupport.GetSource(
                            Obj,
                            "recordings",
                            JsonArray.class
                                          );


            if (RecordingsJList != null)
            {
                temp = new ArrayList<>();
                int size = RecordingsJList.size();

                for (int i = 0; i < size; i++)
                    temp.add(buildRecording(RecordingsJList.get(i)));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return temp;
    }

    private List<Release> buildReleaseList(JsonObject Obj)
    {
        List<Release> temp = null;
        try
        {
            JsonArray ReleasesJList = (JsonArray)
                    ParseSupport.GetSource(
                            Obj,
                            "releases",
                            JsonArray.class
                                          );

            if (ReleasesJList != null)
            {
                temp = new ArrayList<>();
                int size = ReleasesJList.size();

                for (int i = 0; i < size; i++)
                    temp.add(buildRelease(ReleasesJList.get(i)));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return temp;
    }

    private List<Releasegroup> buildReleasegroupList(JsonObject Obj)
    {
        List<Releasegroup> temp = null;
        try
        {
            JsonArray ReleasegroupJList = (JsonArray)
                    ParseSupport.GetSource(
                            Obj,
                            "releasegroups",
                            JsonArray.class
                                          );

            if (ReleasegroupJList != null)
            {
                temp = new ArrayList<>();
                int size = ReleasegroupJList.size();

                for (int i = 0; i < size; i++)
                    temp.add(buildReleasegroup(ReleasegroupJList.get(i)));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return temp;
    }

    private Recording buildRecording(JsonElement element)
    {
        Recording temp = null;

        try
        {
            JsonObject RecordingJObj = element.getAsJsonObject();

            temp = new Recording();

            temp.setDuration((int)
                                     ParseSupport.GetSource(
                                             RecordingJObj,
                                             "duration",
                                             int.class
                                                           ));

            temp.setDuration((int)
                                     ParseSupport.GetSource(
                                             RecordingJObj,
                                             "sources",
                                             int.class
                                                           ));

            temp.setTitle((String)
                                  ParseSupport.GetSource(
                                          RecordingJObj,
                                          "title",
                                          String.class
                                                        ));

            temp.setId((String)
                               ParseSupport.GetSource(
                                       RecordingJObj,
                                       "id",
                                       String.class
                                                     ));


            temp.setArtists(buildArtistList(RecordingJObj));
            temp.setReleasegroups(buildReleasegroupList(RecordingJObj));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return temp;
    }

    private Releasegroup buildReleasegroup(JsonElement element)
    {
        Releasegroup temp = null;
        try
        {
            JsonObject ReleasegroupObject = element.getAsJsonObject();

            temp = new Releasegroup();

            temp.setType((String)
                                 ParseSupport.GetSource(
                                         ReleasegroupObject,
                                         "type",
                                         String.class
                                                       ));

            temp.setId((String)
                               ParseSupport.GetSource(
                                       ReleasegroupObject,
                                       "id",
                                       String.class
                                                     ));

            temp.setTitle((String)
                                  ParseSupport.GetSource(
                                          ReleasegroupObject,
                                          "title",
                                          String.class
                                                        ));

            temp.setSecondarytypes(buildSecondarytypeList(ReleasegroupObject));
            temp.setArtists(buildArtistList(ReleasegroupObject));
            temp.setReleases(buildReleaseList(ReleasegroupObject));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return temp;
    }

    private List<Artist> buildArtistList(JsonObject Obj)
    {
        List<Artist> temp = null;
        try
        {
            JsonArray ArtistJList = (JsonArray)
                    ParseSupport.GetSource(
                            Obj,
                            "artists",
                            JsonArray.class
                                          );


            if (ArtistJList != null)
            {
                temp = new ArrayList<>();
                int size = ArtistJList.size();

                for (int i = 0; i < size; i++)
                    temp.add(buildArtist(ArtistJList.get(i)));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return temp;
    }

    private Medium buildMeduim(JsonElement element)
    {
        Medium temp = null;

        try
        {
            JsonObject MediumObj = element.getAsJsonObject();

            temp = new Medium();

            temp.setPosition((int)
                                     ParseSupport.GetSource(
                                             MediumObj,
                                             "position",
                                             int.class
                                                           ));

            temp.setTrack_count((int)
                                        ParseSupport.GetSource(
                                                MediumObj,
                                                "track_count",
                                                int.class
                                                              ));

            temp.setFormat((String)
                                   ParseSupport.GetSource(
                                           MediumObj,
                                           "format",
                                           String.class
                                                         ));

            temp.setTracks(buildTrackList(MediumObj));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return temp;
    }

    private List<Track> buildTrackList(JsonObject Obj)
    {
        List<Track> temp = null;
        try
        {
            JsonArray TrackJList = (JsonArray)
                    ParseSupport.GetSource(
                            Obj,
                            "tracks",
                            JsonArray.class
                                          );

            if (TrackJList != null)
            {
                temp = new ArrayList<>();
                int size = TrackJList.size();

                for (int i = 0; i < size; i++)
                    temp.add(buildTrack(TrackJList.get(i)));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return temp;
    }

    private Release buildRelease(JsonElement element)
    {
        Release temp = null;

        try
        {
            JsonObject ReleaseObj = element.getAsJsonObject();

            temp = new Release();

            temp.setTrack_count((int)
                                        ParseSupport.GetSource(
                                                ReleaseObj,
                                                "track_count",
                                                int.class
                                                              ));

            temp.setMedium_count((int)
                                         ParseSupport.GetSource(
                                                 ReleaseObj,
                                                 "medium_count",
                                                 int.class
                                                               ));

            temp.setCountry((String)
                                    ParseSupport.GetSource(
                                            ReleaseObj,
                                            "country",
                                            String.class
                                                          ));

            temp.setTitle((String)
                                  ParseSupport.GetSource(
                                          ReleaseObj,
                                          "title",
                                          String.class
                                                        ));

            temp.setId((String)
                               ParseSupport.GetSource(
                                       ReleaseObj,
                                       "id",
                                       String.class
                                                     ));

            temp.setDate(
                    buildDate(
                            ReleaseObj.get("date")
                             ));


            temp.setArtists(buildArtistList(ReleaseObj));
            temp.setReleaseevents(buildReleaseeventList(ReleaseObj));
            temp.setMediums(buildMediumList(ReleaseObj));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return temp;
    }

    private List<Releaseevent> buildReleaseeventList(JsonObject Obj)
    {
        List<Releaseevent> temp = null;

        try
        {
            JsonArray ReleaseeventJList = (JsonArray)
                    ParseSupport.GetSource(
                            Obj,
                            "releaseevents",
                            JsonArray.class
                                          );

            if (ReleaseeventJList != null)
            {
                temp = new ArrayList<>();
                int size = ReleaseeventJList.size();

                for (int i = 0; i < size; i++)
                    temp.add(buildReleaseevent(ReleaseeventJList.get(i)));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return temp;
    }

    private List<Medium> buildMediumList(JsonObject Obj)
    {
        List<Medium> temp = null;

        try
        {
            JsonArray MediumJList = (JsonArray)
                    ParseSupport.GetSource(
                            Obj,
                            "mediums",
                            JsonArray.class
                                          );

            if (MediumJList != null)
            {
                temp = new ArrayList<>();
                int size = MediumJList.size();

                for (int i = 0; i < size; i++)
                    temp.add(buildMeduim(MediumJList.get(i)));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return temp;
    }

    private Releaseevent buildReleaseevent(JsonElement element)
    {
        Releaseevent temp = null;
        try
        {
            JsonObject ReleaseeventObj = element.getAsJsonObject();

            temp = new Releaseevent();

            temp.setDate(buildDate(ReleaseeventObj.get("date")));

            temp.setCountry((String)
                                    ParseSupport.GetSource(
                                            ReleaseeventObj,
                                            "country",
                                            String.class
                                                          ));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return temp;
    }

    private Track buildTrack(JsonElement element)
    {
        Track temp = null;
        try
        {
            JsonObject TrackObj = element.getAsJsonObject();

            temp = new Track();

            temp.setPosition((int)
                                     ParseSupport.GetSource(
                                             TrackObj,
                                             "position",
                                             int.class
                                                           ));

            temp.setTitle((String)
                                  ParseSupport.GetSource(
                                          TrackObj,
                                          "title",
                                          String.class
                                                        ));

            temp.setId((String)
                               ParseSupport.GetSource(
                                       TrackObj,
                                       "title",
                                       String.class
                                                     ));

            temp.setArtists(buildArtistList(TrackObj));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return temp;
    }
}