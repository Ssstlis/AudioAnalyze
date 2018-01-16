package Fox.core.lib.services.acoustid;


import java.util.HashMap;

public class AcoustIDRequestConfig
{
    private HashMap<String, Boolean> ConfigMap = new HashMap<>();

    public AcoustIDRequestConfig()
    {
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
                      false
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
        return ConfigMap.containsKey("recordings");
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
        return ConfigMap.containsKey("recordingids");
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
        return ConfigMap.containsKey("releases");
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
        return ConfigMap.containsKey("releaseids");
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
        return ConfigMap.containsKey("releasegroups");
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
        return ConfigMap.containsKey("releasegroupids");
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
        return ConfigMap.containsKey("tracks");
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
        return ConfigMap.containsKey("usermeta");
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
        return ConfigMap.containsKey("sources");
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
        return ConfigMap.containsKey("compress");
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