package Fox.core.lib.services.LastFM.Artist.getInfo.sources;

public class links
{
    private link link;

    public links()
    {

    }

    public links(link link)
    {
        this.link = new link(link);
    }

    public links(links copy)
    {
        if (copy!=null)
        {
            this.link = new link(copy.link);
        }
    }

    public link getLink()
    {
        return new link(link);
    }

    public void setLink(link link)
    {
        this.link = new link(link);
    }
}
