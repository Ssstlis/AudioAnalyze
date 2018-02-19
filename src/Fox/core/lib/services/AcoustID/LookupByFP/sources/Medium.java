package Fox.core.lib.services.AcoustID.LookupByFP.sources;


import java.util.ArrayList;
import java.util.List;

public class Medium
{
    private Integer position;
    private Integer track_count;
    private String format;
    private List<Track> Tracks;

    public Medium()
    {
    }

    public Medium(
            Integer position,
            Integer track_count,
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
            {
                temp.add(new Medium(elem));
            }
        }
        return temp;
    }

    public Integer getPosition()
    {
        return position;
    }

    public void setPosition(Integer position)
    {
        this.position = position;
    }

    public boolean hasPosition()
    {
        return position != null && position != 0;
    }

    public Integer getTrack_count()
    {
        return track_count;
    }

    public void setTrack_count(Integer track_count)
    {
        this.track_count = track_count;
    }

    public boolean hasTrack_count()
    {
        return track_count != null && track_count != 0;
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
        return Tracks != null && !Tracks.isEmpty();
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
        return format != null && !format.isEmpty();
    }
}
