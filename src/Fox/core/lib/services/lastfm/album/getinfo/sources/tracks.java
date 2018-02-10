package Fox.core.lib.services.LastFM.Album.getInfo.sources;

import java.util.List;

public class tracks
{
    private List<track> tracks;

    public tracks()
    {

    }

    public tracks(List<track> tracks)
    {
        this.tracks = tracks;
    }

    public tracks(tracks copy)
    {
        if (tracks != null)
        {
            this.tracks = track.trackListCopy(copy.tracks);
        }
    }

    public List<track> getTracks()
    {
        return track.trackListCopy(tracks);
    }

    public void setTracks(List<track> tracks)
    {
        this.tracks = track.trackListCopy(tracks);
    }
}
