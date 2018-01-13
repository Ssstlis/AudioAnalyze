package Fox.core.lib.service.acousticid.LookupByFP;

import Fox.core.lib.service.acousticid.AcousticIDResponse;
import Fox.core.lib.service.acousticid.LookupByFP.sources.*;
import Fox.core.lib.service.acousticid.LookupByFP.sources.Error;
import Fox.core.lib.service.acousticid.LookupByFP.sources.Artist;
import com.google.gson.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class StructureBuilder {

    @Nullable
    private java.lang.Object GetSource(@NotNull JsonObject object,
                                       @NotNull String name,
                                       @NotNull Class target)
    {
        JsonElement element;
        if (object.has(name)) {
            element = object.get(name);
        } else {
            return null;
        }

        if (target == BigDecimal.class) {
            return element.getAsBigDecimal();
        }

        if (target == BigInteger.class) {
            return element.getAsBigInteger();
        }

        if (target == boolean.class) {
            return element.getAsBoolean();
        }

        if (target == byte.class) {
            return element.getAsByte();
        }

        if (target == char.class) {
            return element.getAsCharacter();
        }

        if (target == double.class) {
            return element.getAsDouble();
        }

        if (target == float.class) {
            return element.getAsFloat();
        }

        if (target == int.class) {
            return element.getAsInt();
        }

        if (target == JsonArray.class) {
            return element.getAsJsonArray();
        }

        if (target == JsonNull.class) {
            return element.getAsJsonNull();
        }

        if (target == JsonObject.class) {
            return element.getAsJsonObject();
        }

        if (target == JsonPrimitive.class) {
            return element.getAsJsonPrimitive();
        }

        if (target == long.class) {
            return element.getAsLong();
        }

        if (target == Number.class) {
            return element.getAsNumber();
        }

        if (target == short.class) {
            return element.getAsShort();
        }

        if (target == String.class) {
        return element.getAsString();
        }
        return null;
    }

    public StructureBuilder() {
    }

    @NotNull
    public ByFingerPrint buildLookup(@NotNull AcousticIDResponse response) {
        JsonParser parser = new JsonParser();
        ByFingerPrint temp = new ByFingerPrint();
        String source = response.getSource();


        JsonElement element = parser
                .parse(source);

        JsonObject LookupJObj = element
                .getAsJsonObject();

        temp.setStatus((String)
                GetSource(
                        LookupJObj,
                        "status",
                        String.class
                ));

        temp.setErr(buildError(LookupJObj.get("error")));
        temp.setResult(buildResultList(LookupJObj));

        return temp;
    }

    private List<Result> buildResultList(@NotNull JsonObject Obj)
    {
        List<Result> ResultList = null;
        try
        {
            JsonArray ResultsJList = (JsonArray)
                    GetSource(
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

    @NotNull
    private Result buildResult(@NotNull JsonElement element)
    {
        Result temp = new Result();
        JsonObject ResultJObj = element.getAsJsonObject();

        temp.setId((String)
                    GetSource(
                            ResultJObj,
                            "id",
                            String.class
                    ));

        temp.setScore((float)
                    GetSource(
                            ResultJObj,
                            "score",
                            float.class
                    ));

        temp.setRecordings(buildRecordingList(ResultJObj));
        temp.setReleasegroups(buildReleasegroupList(ResultJObj));
        temp.setReleases(buildReleaseList(ResultJObj));


        return temp;
    }

    private List<Recording> buildRecordingList(@NotNull JsonObject Obj)
    {
        List<Recording> temp = null;

        try
        {
            JsonArray RecordingsJList = (JsonArray)
                    GetSource(
                            Obj,
                            "recordings",
                            JsonArray.class
                    );


            if (RecordingsJList != null) {
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

    private List<Release> buildReleaseList(@NotNull JsonObject Obj)
    {
        List<Release> temp = null;
        try
        {
            JsonArray ReleasesJList = (JsonArray)
                    GetSource(
                            Obj,
                            "releases",
                            JsonArray.class
                    );

            if (ReleasesJList!=null)
            {
                temp = new ArrayList<>();
                int size = ReleasesJList.size();

                for(int i = 0; i < size; i++)
                    temp.add(buildRelease(ReleasesJList.get(i)));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return temp;
    }

    private List<Releasegroup> buildReleasegroupList(@NotNull JsonObject Obj)
    {
        List<Releasegroup> temp = null;
        try
        {
            JsonArray ReleasegroupJList = (JsonArray)
                    GetSource(
                            Obj,
                            "releasegroups",
                            JsonArray.class
                    );

            if (ReleasegroupJList!=null)
            {
                temp = new ArrayList<>();
                int size = ReleasegroupJList.size();

                for(int i = 0; i < size; i++)
                    temp.add(buildReleasegroup(ReleasegroupJList.get(i)));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return temp;
    }

    @NotNull
    private Recording buildRecording(@NotNull JsonElement element)
    {
        Recording temp = new Recording();
        JsonObject RecordingJObj = element.getAsJsonObject();

        temp.setDuration((int)
                GetSource(
                        RecordingJObj,
                        "duration",
                        int.class
                ));

        temp.setDuration((int)
                GetSource(
                        RecordingJObj,
                        "sources",
                        int.class
                ));

        temp.setTitle((String)
                GetSource(
                        RecordingJObj,
                        "title",
                        String.class
                ));

        temp.setId((String)
                GetSource(
                        RecordingJObj,
                        "id",
                        String.class
                ));


        temp.setArtists(buildArtistList(RecordingJObj));
        temp.setReleasegroups(buildReleasegroupList(RecordingJObj));

        return temp;
    }

    @NotNull
    private Releasegroup buildReleasegroup(@NotNull JsonElement element)
    {
        Releasegroup temp = new Releasegroup();
        JsonObject ReleasegroupObject = element.getAsJsonObject();

        temp.setType((String)
                GetSource(
                        ReleasegroupObject,
                        "type",
                        String.class
                ));

        temp.setId((String)
                GetSource(
                        ReleasegroupObject,
                        "id",
                        String.class
                ));

        temp.setTitle((String)
                GetSource(
                        ReleasegroupObject,
                        "title",
                        String.class
                ));

        temp.setSecondarytypes(buildSecondarytypeList(ReleasegroupObject));
        temp.setArtists(buildArtistList(ReleasegroupObject));
        temp.setReleases(buildReleaseList(ReleasegroupObject));

        return temp;
    }

    private List<String> buildSecondarytypeList(@NotNull JsonObject Obj)
    {
        List<String> temp = null;
        try
        {
            JsonArray SecondatytypesJList = (JsonArray)
                    GetSource(
                            Obj,
                            "secondarytypes",
                            JsonArray.class
                    );


            if (SecondatytypesJList != null) {
                temp = new ArrayList<>();
                int size = SecondatytypesJList.size();

                for (int i = 0; i < size; i++)
                    temp.add(SecondatytypesJList.get(i).getAsString());
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return temp;
    }

    private List<Artist> buildArtistList(@NotNull JsonObject Obj)
    {
        List<Artist> temp = null;
        try
        {
            JsonArray ArtistJList = (JsonArray)
                    GetSource(
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

    @NotNull
    private Artist buildArtist(@NotNull JsonElement element)
    {
        Artist temp = new Artist();
        JsonObject ArtistObject = element.getAsJsonObject();

        temp.setName((String)
                GetSource(
                        ArtistObject,
                        "name",
                        String.class
                ));


        temp.setId((String)
                GetSource(
                        ArtistObject,
                        "id",
                        String.class
                ));

        return temp;
    }

    @NotNull
    private Date buildDate(@NotNull JsonElement element)
    {
        Date temp = new Date();
        JsonObject DateObj = element.getAsJsonObject();

        temp.setDay((int)
                GetSource(
                        DateObj,
                        "day",
                        int.class
                ));

        temp.setMonth((int)
                GetSource(
                        DateObj,
                        "month",
                        int.class
                ));

        temp.setYear((int)
                GetSource(
                        DateObj,
                        "year",
                        int.class
                ));

        return temp;
    }

    @NotNull
    private Medium buildMeduim(@NotNull JsonElement element)
    {
        Medium temp = new Medium();
        JsonObject MediumObj = element.getAsJsonObject();

        temp.setPosition((int)
                GetSource(
                        MediumObj,
                        "position",
                        int.class
                ));

        temp.setTrack_count((int)
                GetSource(
                        MediumObj,
                        "track_count",
                        int.class
                ));

        temp.setFormat((String)
                GetSource(
                        MediumObj,
                        "format",
                        String.class
                ));

        temp.setTracks(buildTrackList(MediumObj));

        return temp;
    }

    private List<Track> buildTrackList(@NotNull JsonObject Obj)
    {
        List<Track> temp = null;
        try
        {
            JsonArray TrackJList = (JsonArray)
                    GetSource(
                            Obj,
                            "tracks",
                            JsonArray.class
                    );

            if (TrackJList != null) {
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

    @NotNull
    private Release buildRelease(@NotNull JsonElement element)
    {
        Release temp = new Release();

        JsonObject ReleaseObj = element.getAsJsonObject();

        temp.setTrack_count((int)
                GetSource(
                        ReleaseObj,
                        "track_count",
                        int.class
                ));

        temp.setMedium_count((int)
                GetSource(
                        ReleaseObj,
                        "medium_count",
                        int.class
                ));

        temp.setCountry((String)
                GetSource(
                        ReleaseObj,
                        "country",
                        String.class
                ));

        temp.setTitle((String)
                GetSource(
                        ReleaseObj,
                        "title",
                        String.class
                ));

        temp.setId((String)
                GetSource(
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


        return temp;
    }

    private List<Releaseevent> buildReleaseeventList(@NotNull JsonObject Obj)
    {
        List<Releaseevent> temp = null;

        try
        {
            JsonArray ReleaseeventJList = (JsonArray)
                    GetSource(
                            Obj,
                            "releaseevents",
                            JsonArray.class
                    );

            if (ReleaseeventJList != null) {
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

    private List<Medium> buildMediumList(@NotNull JsonObject Obj)
    {
        List<Medium> temp = null;

        try {
            JsonArray MediumJList = (JsonArray)
                    GetSource(
                            Obj,
                            "mediums",
                            JsonArray.class
                    );

            if (MediumJList != null) {
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

    @NotNull
    private Releaseevent buildReleaseevent(@NotNull JsonElement element)
    {
        Releaseevent temp = new Releaseevent();
        JsonObject ReleaseeventObj = element.getAsJsonObject();

        temp.setDate(buildDate(ReleaseeventObj.get("date")));

        temp.setCountry((String)
                GetSource(
                        ReleaseeventObj,
                        "country",
                        String.class
                ));

        return temp;
    }

    @NotNull
    private Track buildTrack(@NotNull JsonElement element)
    {
        Track temp = new Track();
        JsonObject TrackObj = element.getAsJsonObject();

        temp.setPosition((int)
                GetSource(
                        TrackObj,
                        "position",
                        int.class
                ));

        temp.setTitle((String)
                GetSource(
                        TrackObj,
                        "title",
                        String.class
                ));

        temp.setId((String)
                GetSource(
                        TrackObj,
                        "title",
                        String.class
                ));

        temp.setArtists(buildArtistList(TrackObj));

        return temp;
    }

    @NotNull
    private Error buildError(@NotNull JsonElement element)
    {
        Error temp = new Error();
        JsonObject ErrJObj = element.getAsJsonObject();

        temp.setMessage((String)
                GetSource(
                        ErrJObj,
                        "message",
                        String.class
                ));

        temp.setCode((int)
                GetSource(
                        ErrJObj,
                        "code",
                        int.class
                ));

        return temp;
    }
}