package Fox.core.lib.services.acoustid;

import Fox.core.lib.connectors.HttpGetClient;
import Fox.core.lib.general.DOM.FingerPrint;
import Fox.core.lib.services.Common.Elapsed;
import Fox.core.lib.services.acoustid.LookupByFP.AcoustIDStructureBuilder;
import Fox.core.lib.services.acoustid.LookupByFP.sources.ByFingerPrint;
import Fox.core.main.AudioAnalyzeLibrary;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class AcoustIDApi
{
    private static final Logger logger = LoggerFactory.getLogger(AudioAnalyzeLibrary.class);
    private final static String key = "WweGbntpM7";
    private final static String httpkey =
            "https://api.acoustid.org/v2/lookup?client=" + key + "&format=json";
    private HttpGetClient RequestClient;
    private AcoustIDRequestConfig config;

    public AcoustIDApi()
    {
        RequestClient = new HttpGetClient();
        config = new AcoustIDRequestConfig();
        config.setDefault();
    }

    public AcoustIDApi(AcoustIDRequestConfig cfg)
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
                if (logger.isErrorEnabled())
                    logger.error("", e);
                return null;
            }

            if (response != null && response.hasSource())
                fingerPrint = AcoustIDStructureBuilder.buildLookup(response);

            return fingerPrint;
        }
        return null;
    }

    public static class AcoustIDRequestConfig
    {
        private HashMap<String, Boolean> ConfigMap = new HashMap<>();

        public AcoustIDRequestConfig()
        {

        }

        public static AcoustIDRequestConfig DefaultConfig()
        {
            AcoustIDRequestConfig temp = new AcoustIDRequestConfig();
            temp.setDefault();
            return temp;
        }

        @Override
        public String toString()
        {
            String temp = "";
            if (this.isRecordings())
            {
                temp = temp.concat("+recordings");
            }
            if (this.isRecordingids())
            {
                temp = temp.concat("+recordingids");
            }
            if (this.isReleasegroups())
            {
                temp = temp.concat("+releasegroups");
            }
            if (this.isReleasegroupids())
            {
                temp = temp.concat("+releasegroupids");
            }
            if (this.isReleases())
            {
                temp = temp.concat("+releases");
            }
            if (this.isReleaseids())
            {
                temp = temp.concat("+releaseids");
            }
            if (this.isSources())
            {
                temp = temp.concat("+sources");
            }
            if (this.isTracks())
            {
                temp = temp.concat("+tracks");
            }
            if (this.isUsermeta())
            {
                temp = temp.concat("+usermeta");
            }
            if (this.isCompress())
            {
                temp = temp.concat("+compress");
            }

            if (temp.length() > 0)
            {
                temp = temp.substring(1);
                temp = "&meta=".concat(temp);
            }
            return temp;
        }

        public void setDefault()
        {
            ConfigMap.put("recordings",
                          true
                         );
            ConfigMap.put("releasegroups",
                          true
                         );
            ConfigMap.put("recordingids",
                          false
                         );
            ConfigMap.put("releasegroupids",
                          false
                         );
            ConfigMap.put("releaseids",
                          false
                         );
            ConfigMap.put("releases",
                          false
                         );
            ConfigMap.put("tracks",
                          false
                         );
            ConfigMap.put("usermeta",
                          false
                         );
            ConfigMap.put("sources",
                          true
                         );
            ConfigMap.put("compress",
                          false
                         );
        }

        public void setAll()
        {
            setMax();
            ConfigMap.put("recordingids",
                          true
                         );
            ConfigMap.put("releasegroupids",
                          true
                         );
            ConfigMap.put("releaseids",
                          true
                         );
            ConfigMap.put("compress",
                          true
                         );
        }

        public void setMax()
        {
            ConfigMap.put("recordings",
                          true
                         );
            ConfigMap.put("releasegroups",
                          true
                         );
            ConfigMap.put("releases",
                          true
                         );
            ConfigMap.put("tracks",
                          true
                         );
            ConfigMap.put("usermeta",
                          true
                         );
            ConfigMap.put("sources",
                          true
                         );
        }

        public void unsetAll()
        {
            ConfigMap.put("recordings",
                          false
                         );
            ConfigMap.put("releasegroups",
                          false
                         );
            ConfigMap.put("recordingids",
                          false
                         );
            ConfigMap.put("releasegroupids",
                          false
                         );
            ConfigMap.put("releaseids",
                          false
                         );
            ConfigMap.put("releases",
                          false
                         );
            ConfigMap.put("tracks",
                          false
                         );
            ConfigMap.put("usermeta",
                          false
                         );
            ConfigMap.put("sources",
                          false
                         );
            ConfigMap.put("compress",
                          false
                         );
        }

        public boolean isRecordings()
        {
            return ConfigMap.containsKey("recordings") && ConfigMap.get("recordings");
        }

        public void setRecordings()
        {
            ConfigMap.put("recordings",
                          true
                         );
        }

        public void unsetRecordings()
        {
            ConfigMap.put("recordings",
                          false
                         );
        }

        public boolean isRecordingids()
        {
            return ConfigMap.containsKey("recordingids") && ConfigMap.get("recordingids");
        }

        public void setRecordingids()
        {
            ConfigMap.put("recordingids",
                          true
                         );
        }

        public void unsetRecordingids()
        {
            ConfigMap.put("recordingids",
                          false
                         );
        }

        public boolean isReleases()
        {
            return ConfigMap.containsKey("releases") && ConfigMap.get("releases");
        }

        public void setReleases()
        {
            ConfigMap.put("releases",
                          true
                         );
        }

        public void unsetReleases()
        {
            ConfigMap.put("releases",
                          false
                         );
        }

        public boolean isReleaseids()
        {
            return ConfigMap.containsKey("releaseids") && ConfigMap.get("releaseids");
        }

        public void setReleaseids()
        {
            ConfigMap.put("releaseids",
                          true
                         );
        }

        public void unsetReleaseids()
        {
            ConfigMap.put("releaseids",
                          false
                         );
        }

        public boolean isReleasegroups()
        {
            return ConfigMap.containsKey("releasegroups") && ConfigMap.get("releasegroups");
        }

        public void setReleasegroups()
        {
            ConfigMap.put("releasegroups",
                          true
                         );
        }

        public void unsetReleasegroups()
        {
            ConfigMap.put("releasegroups",
                          false
                         );
        }

        public boolean isReleasegroupids()
        {
            return ConfigMap.containsKey("releasegroupids") && ConfigMap.get("releasegroupids");
        }

        public void setReleasegroupids()
        {
            ConfigMap.put("releasegroupids",
                          true
                         );
        }

        public void unsetReleasegroupids()
        {
            ConfigMap.put("releasegroupids",
                          false
                         );
        }

        public boolean isTracks()
        {
            return ConfigMap.containsKey("tracks") && ConfigMap.get("tracks");
        }

        public void setTracks()
        {
            ConfigMap.put("tracks",
                          true
                         );
        }

        public void unsetTracks()
        {
            ConfigMap.put("tracks",
                          false
                         );
        }

        public boolean isUsermeta()
        {
            return ConfigMap.containsKey("usermeta") && ConfigMap.get("usermeta");
        }

        public void setUsermeta()
        {
            ConfigMap.put("usermeta",
                          true
                         );
        }

        public void unsetUsermeta()
        {
            ConfigMap.put("usermeta",
                          false
                         );
        }

        public boolean isSources()
        {
            return ConfigMap.containsKey("sources") && ConfigMap.get("sources");
        }

        public void setSources()
        {
            ConfigMap.put("sources",
                          true
                         );
        }

        public void unsetSources()
        {
            ConfigMap.put("sources",
                          false
                         );
        }

        public boolean isCompress()
        {
            return ConfigMap.containsKey("compress") && ConfigMap.get("compress");
        }

        public void setCompress()
        {
            ConfigMap.put("compress",
                          true
                         );
        }

        public void unsetCompress()
        {
            ConfigMap.put("compress",
                          false
                         );
        }
    }

    public static class AcoustIDResponse
    {
        private String source;

        public AcoustIDResponse()
        {

        }

        public AcoustIDResponse(@NotNull String src)
        {
            this.source = src;
        }

        public String getSource()
        {
            return source;
        }

        public void setSource(String source)
        {
            this.source = source;
        }

        public boolean hasSource()
        {
            return (source != null && !source.isEmpty());
        }
    }
}
