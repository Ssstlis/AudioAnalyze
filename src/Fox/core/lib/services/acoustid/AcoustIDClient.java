package Fox.core.lib.services.acoustid;

import Fox.core.lib.connectors.HttpGetClient;
import Fox.core.lib.general.DOM.FingerPrint;
import Fox.core.lib.services.Elapsed;
import Fox.core.lib.services.acoustid.LookupByFP.AcoustIDStructureBuilder;
import Fox.core.lib.services.acoustid.LookupByFP.sources.ByFingerPrint;

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

            /*try
            {
                String loc = FPrint.getLocation()
                                   .substring(FPrint.getLocation()
                                                    .lastIndexOf("\\") + 1);

                File tfile = new File(loc);
                FileWriter writer = new FileWriter(loc,
                                                   false
                );

                if (!tfile.exists())
                {
                    tfile.createNewFile();
                    writer.write(response.getSource());
                    writer.flush();
                    writer.close();
                }
                else
                    {

                        writer.write(response.getSource());
                        writer.flush();
                        writer.close();
                    }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }*/
            ByFingerPrint temp = new AcoustIDStructureBuilder()
                    .buildLookup(response);
            return temp;
        }
        return null;
    }
}
