package Fox.core.lib.services.LastFM.Album.getInfo.sources;

import java.util.List;

public class tracks
{
    private List<track> tracks_;

    public tracks()
    {

    }

    public tracks(List<track> tracks)
    {
        this.tracks_ = tracks;
    }

    public tracks(tracks copy)
    {
        if (tracks_ != null)
        {
            this.tracks_ = track.trackListCopy(copy.tracks_);
        }
    }

    public List<track> getTracks()
    {
        return track.trackListCopy(tracks_);
    }

    public void setTracks(List<track> tracks)
    {
        this.tracks_ = track.trackListCopy(tracks);
    }
}
