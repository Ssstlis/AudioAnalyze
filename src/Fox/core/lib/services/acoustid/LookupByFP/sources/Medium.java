package Fox.core.lib.services.acoustid.LookupByFP.sources;


import java.util.ArrayList;
import java.util.List;

public class Medium
{
    private int position;
    private int track_count;
    private String format;
    private List<Track> Tracks;

    public Medium()
    {
    }

    public Medium(int position,
                  int track_count,
                  String format,
                  List<Track> Tracks)
    {
        this.position = position;
        this.track_count = track_count;
        this.Tracks = Track.TrackListCopy(Tracks);
        this.format = format;
    }

    public Medium(Medium copy)
    {
        if (copy != null)
        {
            this.position = copy.position;
            this.track_count = copy.track_count;
            this.format = copy.format;
            this.Tracks = Track.TrackListCopy(copy.Tracks);
        }
    }

    public static List<Medium> MediumListCopy(List<Medium> copy)
    {
        List<Medium> temp = null;

        if (copy != null)
        {
            temp = new ArrayList<>();
            for (Medium elem : copy)
                temp.add(new Medium(elem));
        }
        return temp;
    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    public boolean hasPosition()
    {
        return position != 0;
    }

    public int getTrack_count()
    {
        return track_count;
    }

    public void setTrack_count(int track_count)
    {
        this.track_count = track_count;
    }

    public boolean hasTrack_count()
    {
        return track_count != 0;
    }

    public List<Track> getTracks()
    {
        return Track.TrackListCopy(Tracks);
    }

    public void setTracks(List<Track> tracks)
    {
        Tracks = Track.TrackListCopy(tracks);
    }

    public boolean hasTracks()
    {
        return Tracks != null && Tracks.size() > 0;
    }

    public String getFormat()
    {
        return format;
    }

    public void setFormat(String format)
    {
        this.format = format;
    }

    public boolean hasFormat()
    {
        return format != null && format.length() > 0;
    }
}
