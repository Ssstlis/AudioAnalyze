package Fox.core.lib.services.acoustid;

import Fox.core.lib.connectors.HttpGetClient;
import Fox.core.lib.general.DOM.FingerPrint;
import Fox.core.lib.services.Elapsed;
import Fox.core.lib.services.acoustid.LookupByFP.AcoustIDStructureBuilder;
import Fox.core.lib.services.acoustid.LookupByFP.sources.ByFingerPrint;

import static Fox.core.main.AudioAnalyzeLibrary.logger;
import static java.util.logging.Level.SEVERE;

public class AcoustIDClient
{
    private final static String key = "ZG11QsMYof";
    private final static String httpkey =
            "https://api.acoustid.org/v2/lookup?client=" + key + "&format=json";
    private HttpGetClient RequestClient;
    private AcoustIDRequestConfig config;

    public AcoustIDClient()
    {
        RequestClient = new HttpGetClient();
        config = new AcoustIDRequestConfig();
        config.setDefault();
    }

    public AcoustIDClient(AcoustIDRequestConfig cfg)
    {
        RequestClient = new HttpGetClient();
        if (cfg != null)
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
        if (cfg != null)
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
            RequestClient.build(
                    httpkey +
                            "&duration=" +
                            FPrint.getDuration() +
                            "&fingerprint=" +
                            FPrint.getPrint() +
                            config.toString()
                               );

            AcoustIDResponse response;
            ByFingerPrint fingerPrint = null;

            try
            {
                response = new AcoustIDResponse(RequestClient.run(Elapsed.AcoustIDElapse()));
            }
            catch (Exception e)
            {
                logger.log(SEVERE, "", e );
                return null;
            }

            if (response != null && response.hasSource())
                fingerPrint = AcoustIDStructureBuilder.buildLookup(response);

            return fingerPrint;
        }
        return null;
    }
}
