package Fox.core.lib.services.LastFM.Track.getInfo.sources;

import Fox.core.lib.services.LastFM.CommonSources.Error;

public class TrackInfo extends Error
{
    private track track;

    public TrackInfo()
    {

    }

    public TrackInfo(track track,
                     Integer error,
                     String message)
    {
        super(error, message);
        this.track = new track(track);
    }

    public TrackInfo(TrackInfo copy)
    {
        if (copy!=null)
        {
            this.track = new track(copy.track);
            this.setError(copy.getError());
            this.setMessage(copy.getMessage());
        }
    }

    public track getTrack()
    {
        return new track(track);
    }

    public void setTrack(track track)
    {
        this.track = new track(track);
    }

    public boolean hasTrack()
    {
        return track!=null;
    }
}
