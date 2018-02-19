package Fox.core.lib.services.LastFM.Artist.getInfo.sources;

public class stats
{
    private String listeners;
    private String playcount;

    public stats()
    {

    }

    public stats(
            String listeners,
            String playcount)
    {
        this.listeners = listeners;
        this.playcount = playcount;
    }

    public stats(stats copy)
    {
        if (copy != null)
        {
            this.playcount = copy.playcount;
            this.listeners = copy.listeners;
        }
    }

    public String getListeners()
    {
        return listeners;
    }

    public void setListeners(String listeners)
    {
        this.listeners = listeners;
    }

    public boolean hasListeners()
    {
        return (listeners != null && !listeners.isEmpty());
    }

    public String getPlaycount()
    {
        return playcount;
    }

    public void setPlaycount(String playcount)
    {
        this.playcount = playcount;
    }

    public boolean hasPlaycount()
    {
        return (playcount != null && !playcount.isEmpty());
    }
}
