package Fox.core.lib.services.AcoustID.LookupByFP.sources;

import java.util.ArrayList;
import java.util.List;

public class Track
{
    private int position;
    private String title;
    private String id;
    private List<Artist> Artists;

    public Track()
    {
    }

    public Track(
            int position,
            String title,
            String id,
            List<Artist> Artists)
    {
        this.Artists = Artist.ArtistListCopy(Artists);
        this.id = id;
        this.position = position;
        this.title = title;
    }

    public Track(Track copy)
    {
        if (copy != null)
        {
            this.title = copy.title;
            this.id = copy.id;
            this.position = copy.position;
            this.Artists = Artist.ArtistListCopy(copy.Artists);
        }
    }

    public static List<Track> TrackListCopy(List<Track> copy)
    {
        List<Track> temp = null;

        if (copy != null)
        {
            temp = new ArrayList<>();
            for (Track elem : copy)
            {
                temp.add(new Track(elem));
            }
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

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public boolean hasTitle()
    {
        return title != null && title.length() > 0;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public boolean hasId()
    {
        return id != null && id.length() > 0;
    }

    public List<Artist> getArtists()
    {
        return Artist.ArtistListCopy(Artists);
    }

    public void setArtists(List<Artist> artists)
    {
        Artists = Artist.ArtistListCopy(artists);
    }

    public boolean hasArtists()
    {
        return Artists != null && Artists.size() > 0;
    }
}
