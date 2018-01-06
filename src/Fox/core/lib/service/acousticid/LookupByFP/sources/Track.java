package Fox.core.lib.service.acousticid.LookupByFP.sources;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Track
{
    private int position;
    private String title;
    private String id;
    private List<Artist> Artists;

    public Track(){}

    public Track(int position,
                 @NotNull String title,
                 @NotNull String id,
                 @NotNull List<Artist> Artists)
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

    @NotNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NotNull String title) {
        this.title = title;
    }

    public boolean hasTitle()
    {
        return title!=null && title.length()>0;
    }

    @NotNull
    public String getId() {
        return id;
    }

    public void setId(@NotNull String id) {
        this.id = id;
    }

    public boolean hasId()
    {
        return id!=null && id.length()>0;
    }

    @NotNull
    public List<Artist> getArtists() {
        return Artists;
    }

    public void setArtists(@NotNull List<Artist> artists) {
        Artists = artists;
    }

    public boolean hasArtists()
    {
        return Artists!=null && Artists.size()>0;
    }
}
