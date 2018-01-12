package Fox.core.lib.service.acousticid.LookupByFP.sources;


import java.util.List;

public class Medium
{
    private int position;
    private int track_count;
    private String format;
    private List<Track> Tracks;

    public Medium(){}

    public Medium(int position,
                  int track_count,
                  String format,
                  List<Track> Tracks)
    {
        this.position = position;
        this.track_count = track_count;
        this.Tracks = Tracks;
        this.format = format;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean hasPosition()
    {
        return position!=0;
    }

    public int getTrack_count() {
        return track_count;
    }

    public void setTrack_count(int track_count) {
        this.track_count = track_count;
    }

    public boolean hasTrack_count()
    {
        return track_count!=0;
    }

    public List<Track> getTracks() {
        return Tracks;
    }

    public void setTracks(List<Track> tracks) {
        Tracks = tracks;
    }

    public boolean hasTracks()
    {
        return Tracks!=null && Tracks.size()>0;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public boolean hasFormat()
    {
        return format!=null && format.length()>0;
    }
}
