package Fox.core.lib.service.acousticid;

public class AcousticIDRequestConfig {
    private boolean recordings;
    private boolean recordingids;
    private boolean releases;
    private boolean releaseids;
    private boolean releasegroups;
    private boolean releasegroupids;
    private boolean tracks;
    private boolean usermeta;
    private boolean sources;
    private boolean compress;

    public AcousticIDRequestConfig() {
    }

    public void setDefault() {
        recordings = true;
    }

    public void setAll() {
        recordings = true;
        releasegroups = true;
        recordingids = true;
        releasegroupids = true;
        releases = true;
        releaseids = true;
        tracks = true;
        usermeta = true;
        sources = true;
        compress = true;
    }

    public void setMax() {
        recordings = true;
        releasegroups = true;
        recordingids = true;
        releasegroupids = true;
        releases = true;
        releaseids = true;
        tracks = true;
        usermeta = true;
        sources = true;
    }

    public void unsetAll() {
        recordings = false;
        releasegroups = false;
        recordingids = false;
        releasegroupids = false;
        releases = false;
        releaseids = false;
        tracks = false;
        usermeta = false;
        sources = false;
        compress = false;
    }

    public boolean isRecordings() {
        return recordings;
    }

    public void setRecordings() {
        this.recordings = true;
    }

    public void unsetRecordings() {
        this.recordings = false;
    }

    public boolean isRecordingids() {
        return recordingids;
    }

    public void setRecordingids() {
        this.recordingids = true;
    }

    public void unsetRecordingids() {
        this.recordingids = false;
    }

    public boolean isReleases() {
        return releases;
    }

    public void setReleases() {
        this.releases = true;
    }

    public void unsetReleases() {
        this.releases = false;
    }

    public boolean isReleaseids() {
        return releaseids;
    }

    public void setReleaseids() {
        this.releaseids = true;
    }

    public void unsetReleaseids() {
        this.releaseids = false;
    }

    public boolean isReleasegroups() {
        return releasegroups;
    }

    public void setReleasegroups() {
        this.releasegroups = true;
    }

    public void unsetReleasegroups() {
        this.releasegroups = false;
    }

    public boolean isReleasegroupids() {
        return releasegroupids;
    }

    public void setReleasegroupids() {
        this.releasegroupids = true;
    }

    public void unsetReleasegroupids() {
        this.releasegroupids = false;
    }

    public boolean isTracks() {
        return tracks;
    }

    public void setTracks() {
        this.tracks = true;
    }

    public void unsetTracks() {
        this.tracks = false;
    }

    public boolean isUsermeta() {
        return usermeta;
    }

    public void setUsermeta() {
        this.usermeta = true;
    }

    public void unsetUsermeta() {
        this.usermeta = false;
    }

    public boolean isSources() {
        return sources;
    }

    public void setSources() {
        this.sources = true;
    }

    public void unsetSources() {
        this.sources = false;
    }

    public boolean isCompress() {
        return compress;
    }

    public void setCompress() {
        this.compress = true;
    }

    public void unsetCompress() {
        this.compress = false;
    }
}