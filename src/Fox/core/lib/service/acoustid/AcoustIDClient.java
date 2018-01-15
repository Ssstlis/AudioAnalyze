package Fox.core.lib.service.acoustid;

import Fox.core.lib.general.FingerPrint;
import Fox.core.lib.general.HttpGetClient;
import Fox.core.lib.service.Elapsed;
import Fox.core.lib.service.acoustid.LookupByFP.StructureBuilder;
import Fox.core.lib.service.acoustid.LookupByFP.sources.ByFingerPrint;

public class AcoustIDClient
{
    private HttpGetClient RequestClient;
    private final static String key = "ZG11QsMYof";
    private final static String httpkey =
            "https://api.acoustid.org/v2/lookup?client=" + key + "&format=json";
    private AcoustIDRequestConfig config;

    public AcoustIDClient(AcoustIDRequestConfig cfg) {
        RequestClient = new HttpGetClient();
        if (cfg!=null)
        {
            config = cfg;
        }
        else
        {
            config = new AcoustIDRequestConfig();
            config.setDefault();
        }
    }

    public void setConfig(AcoustIDRequestConfig cfg)
    {
        if (cfg!=null)
        {
            config = cfg;
        }
        else
        {
            config = new AcoustIDRequestConfig();
            config.setDefault();
        }
    }

    public ByFingerPrint LookupByFingerPrint(FingerPrint FPrint)
    {
        if (FPrint != null)
        {
            RequestClient
                    .build(
                            httpkey +
                                    "&duration=" +
                                    FPrint.getDuration() +
                                    "&fingerprint=" +
                                    FPrint.getPrint() +
                                    config.toString()
                          );

            AcoustIDResponse response = new AcoustIDResponse
                    (
                            RequestClient
                                    .run(
                                            (System.currentTimeMillis() - Elapsed.AcoustIDElapse > Elapsed.AcoustIDElapseState || !Elapsed.AcoustIDUsage)
                                                    ? (0) : (System.currentTimeMillis() - Elapsed.AcoustIDElapse)
                                        )
                    );

            Elapsed.AcoustIDUsage = true;
            Elapsed.AcoustIDElapse = System.currentTimeMillis();

            long l = System.currentTimeMillis();
            ByFingerPrint temp = new StructureBuilder()
                    .buildLookup(response);
            l = System.currentTimeMillis() - l;
            return temp;
        }
        return null;
    }
}
