package Fox.core.lib.service.acousticid.LookupByFP;

import Fox.core.lib.service.acousticid.AcousticIDRequestConfig;
import Fox.core.lib.service.acousticid.AcousticIDResponse;
import Fox.core.lib.service.acousticid.LookupByFP.sources.*;
import Fox.core.lib.service.acousticid.LookupByFP.sources.Error;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StructureBuilder {
    private AcousticIDRequestConfig config;

    public StructureBuilder() {

    }

    @NotNull
    public ByFingerPrint buildLookup(@NotNull AcousticIDResponse response) {
        JsonParser parser = new JsonParser();
        ByFingerPrint temp = new ByFingerPrint();
        String source = response.getSource();
        config = response.getConfig();

        try {
            JsonElement element = parser
                    .parse(source);

            if (element.isJsonObject()) {
                JsonObject LookupJObj = element
                        .getAsJsonObject();

                temp = temp.setStatus(LookupJObj
                        .get("status")
                        .getAsString());

                if (LookupJObj.has("Error")) {
                    temp = temp
                            .setErr(buildError(LookupJObj.get("Error")));
                } else {
                    if (LookupJObj.has("results")
                            &&
                            LookupJObj.get("results").isJsonArray()) {
                        JsonArray ResultsJList = LookupJObj
                                .get("results")
                                .getAsJsonArray();

                        List<Result> ResultsList = new ArrayList<>();
                        int size = ResultsJList.size();

                        for (int i = 0; i < size; i++)
                            ResultsList.add(buildResult(ResultsJList.get(i)));

                        temp.setResult(ResultsList);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    @NotNull
    private Result buildResult(@NotNull JsonElement element) {
        Result temp = new Result();
        if (element.isJsonObject()) {
            JsonObject ResultJObj = element.getAsJsonObject();
            if (ResultJObj.has("id")) {
                temp.setId(
                        ResultJObj
                                .get("id")
                                .getAsString());
            }
            if (ResultJObj.has("score")) {
                temp.setScore(
                        ResultJObj
                                .get("score")
                                .getAsFloat());
            }
            if (ResultJObj.has("recordings")) {
                if (ResultJObj.get("recordings").isJsonArray()) {
                    JsonArray RecordingsJList = ResultJObj
                            .get("recordings")
                            .getAsJsonArray();

                    List<Recording> RecordingList = new ArrayList<>();
                    int size = RecordingsJList.size();

                    for (int i = 0; i < size; i++)
                        RecordingList.add(buildRecording(RecordingsJList.get(i)));

                    temp.setRecordings(RecordingList);
                }
            }
        }
        return temp;
    }

    @NotNull
    private Recording buildRecording(@NotNull JsonElement element) {
        Recording temp = new Recording();
        if (element.isJsonObject()) {
            JsonObject RecordingJObj = element.getAsJsonObject();
            if (RecordingJObj.has("duration")) {
                temp.setDuration(
                        RecordingJObj
                                .get("duration")
                                .getAsInt());
            }
            if (RecordingJObj.has("title")) {
                temp.setTitle(
                        RecordingJObj
                                .get("title")
                                .getAsString());
            }
            if (RecordingJObj.has("id")) {
                temp.setId(
                        RecordingJObj
                                .get("id")
                                .getAsString());
            }
            if (RecordingJObj.has("artists")) {
                if (RecordingJObj.get("artists").isJsonArray()) {
                    JsonArray ArtistJList = RecordingJObj
                            .get("artists")
                            .getAsJsonArray();

                    List<Artist> RecordingList = new ArrayList<>();
                    int size = ArtistJList.size();

                    for (int i = 0; i < size; i++)
                        RecordingList.add(buildArtist(ArtistJList.get(i)));

                    temp.setArtists(RecordingList);
                }
            }
        }
        return temp;
    }

    @NotNull
    private Releasegroup buildReleasegroup(@NotNull JsonElement element) {
        if (element.isJsonObject()) {

        }
        return new Releasegroup();
    }

    @NotNull
    private Artist buildArtist(@NotNull JsonElement element) {
        Artist temp = new Artist();
        if (element.isJsonObject()) {
            JsonObject ArtistObject = element.getAsJsonObject();
            if (ArtistObject.has("id")
                    && ArtistObject.has("name")) {
                temp.setId(ArtistObject.get("id").getAsString());
                temp.setName(ArtistObject.get("name").getAsString());
            }
        }
        return temp;
    }

    @NotNull
    private Error buildError(@NotNull JsonElement element) {
        Error temp = new Error();

        if (element.isJsonObject()) {
            JsonObject ErrJObj = element.getAsJsonObject();
            if (ErrJObj.has("message")) {
                temp.setMessage(ErrJObj.get("message").getAsString());
            }
            if (ErrJObj.has("code")) {
                temp.setCode(ErrJObj.get("code").getAsInt());
            }
        }
        return temp;
    }
}
