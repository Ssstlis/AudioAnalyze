package Fox.core.lib.service.acousticid.LookupByFP.sources;

import java.util.List;

public class Track
{
    private int position;
    private String title;
    private String id;
    private List<Artist> Artists;

    public Track(){}

    public Track(int position,
                 String title,
                 String id,
                 List<Artist> Artists)
    {
        this.Artists = Artists;
        this.id = id;
        this.position = position;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean hasTitle()
    {
        return title!=null && title.length()>0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean hasId()
    {
        return id!=null && id.length()>0;
    }

    public List<Artist> getArtists() {
        return Artists;
    }

    public void setArtists(List<Artist> artists) {
        Artists = artists;
    }

    public boolean hasArtists()
    {
        return Artists!=null && Artists.size()>0;
    }
}
