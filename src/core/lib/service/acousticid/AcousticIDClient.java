package core.lib.service.acousticid;

import com.google.gson.JsonParser;
import core.lib.FingerPrint;
import core.lib.HttpClient;
import core.lib.service.acousticid.LookupByFP.ByFingerPrint;
import core.lib.service.acousticid.LookupByFP.StructureBuilder;


public class AcousticIDClient
{
    private HttpClient RequestClient;
    private final static String httpkey =
            "https://api.acoustid.org/v2/lookup?client=ZG11QsMYof&meta=recordings&format=json";

    public AcousticIDClient() {
        RequestClient = new HttpClient();
    }

    public ByFingerPrint LookupByFingerPrint(FingerPrint FPrint)
    {
        RequestClient
                .build(
                        httpkey +
                        "&duration=" +
                        FPrint.getDuration() +
                        "&fingerprint=" +
                        FPrint.getPrint()
                );
        String response = RequestClient.run();
        long l = System.currentTimeMillis();
        ByFingerPrint temp = new StructureBuilder().buildLookup(response);
        l = System.currentTimeMillis() - l;
        JsonParser parser = new JsonParser();

        return temp;
    }
}
