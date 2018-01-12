package Fox.core.lib.service.acousticid;

import Fox.core.lib.general.FingerPrint;
import Fox.core.lib.general.HttpClient;
import Fox.core.lib.service.acousticid.LookupByFP.StructureBuilder;
import Fox.core.lib.service.acousticid.LookupByFP.sources.ByFingerPrint;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;

public class AcousticIDClient {
    private HttpClient RequestClient;
    private final static String key = "ZG11QsMYof";
    private final static String httpkey =
            "https://api.acoustid.org/v2/lookup?client="+key + "&format=json";
    private AcousticIDRequestConfig config;

    public AcousticIDClient(@NotNull AcousticIDRequestConfig cfg) {
        RequestClient = new HttpClient();
        config = cfg;
    }

    public ByFingerPrint LookupByFingerPrint(FingerPrint FPrint) {
        RequestClient
                .build(
                        httpkey +
                                "&duration=" +
                                FPrint.getDuration() +
                                "&fingerprint=" +
                                FPrint.getPrint() +
                                buildMetaFromConfig(config)
                );
        AcousticIDResponse response = new AcousticIDResponse(RequestClient.run());
        long l = System.currentTimeMillis();
        ByFingerPrint temp = new StructureBuilder().buildLookup(response);
        l = System.currentTimeMillis() - l;
        JsonParser parser = new JsonParser();

        return temp;
    }

    private String buildMetaFromConfig(AcousticIDRequestConfig cfg)
    {
        String temp = null;
        if (cfg != null)
        {
            temp = "";
            if (cfg.isRecordings())
            {
                temp = temp.concat("+recordings");
            }
            if (cfg.isRecordingids())
            {
                temp = temp.concat("+recordingids");
            }
            if (cfg.isReleasegroups())
            {
                temp = temp.concat("+releasegroups");
            }
            if (cfg.isReleasegroupids())
            {
                temp = temp.concat("+releasegroupids");
            }
            if (cfg.isReleases())
            {
                temp = temp.concat("+releases");
            }
            if (cfg.isReleaseids())
            {
                temp = temp.concat("+releaseids");
            }
            if (cfg.isSources())
            {
                temp = temp.concat("+sources");
            }
            if (cfg.isTracks())
            {
                temp = temp.concat("+tracks");
            }
            if (cfg.isUsermeta())
            {
                temp = temp.concat("+usermeta");
            }
            if (cfg.isCompress())
            {
                temp = temp.concat("+compress");
            }

            if (temp.length()>0)
            {
                temp = temp.substring(1);
                temp = "&meta=".concat(temp);
            }
        }
        return temp;
    }
}
