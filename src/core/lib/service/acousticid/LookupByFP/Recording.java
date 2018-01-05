package core.lib.service.acousticid.LookupByFP;

import java.util.List;

public class Recording
{
    private int duration;
    private String title, id;
    private List<Artist> Artist;

    public Recording(){}
    public int getDuration()
    {
        return this.duration;
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    public String getId()
    {
        return this.id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public List<Artist> getArtist()
    {
        return this.Artist;
    }

    public void setArtist(List<Artist> Artist)
    {
        this.Artist = Artist;
    }
}
