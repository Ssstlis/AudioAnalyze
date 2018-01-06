package Fox.core.lib.service.acousticid;

import Fox.core.lib.general.FingerPrint;
import Fox.core.lib.general.HttpClient;
import Fox.core.lib.service.acousticid.LookupByFP.StructureBuilder;
import Fox.core.lib.service.acousticid.LookupByFP.sources.ByFingerPrint;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;

public class AcousticIDClient {
    private HttpClient RequestClient;
    private final static String httpkey =
            "https://api.acoustid.org/v2/lookup?client=ZG11QsMYof&meta=recordings&format=json";
    private AcousticIDRequestConfig config;

    public AcousticIDClient(@NotNull AcousticIDRequestConfig cfg) {
        RequestClient = new HttpClient();
    }

    public ByFingerPrint LookupByFingerPrint(FingerPrint FPrint) {
        RequestClient
                .build(
                        httpkey +
                                "&duration=" +
                                FPrint.getDuration() +
                                "&fingerprint=" +
                                FPrint.getPrint()
                );
        AcousticIDResponse response = new AcousticIDResponse(RequestClient.run(), config);
        long l = System.currentTimeMillis();
        ByFingerPrint temp = new StructureBuilder().buildLookup(response);
        l = System.currentTimeMillis() - l;
        JsonParser parser = new JsonParser();

        return temp;
    }
}
