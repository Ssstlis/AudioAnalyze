package Fox.core.lib.services.LastFM.Artist.getInfo.sources;

import Fox.core.lib.services.LastFM.CommonSources.wiki;

public class bio extends wiki
{
    private links links;

    public bio()
    {

    }

    public bio(String published,
               String summary,
               String content,
               links links)
    {
        super(published, summary, content);
        this.links = new links(links);
    }

    public bio(bio copy)
    {
        if (copy!=null)
        {
            this.setContent(copy.getContent());
            this.setPublished(copy.getPublished());
            this.setSummary(copy.getSummary());
            this.links = new links(copy.links);
        }
    }

    public links getLinks()
    {
        return new links(links);
    }

    public void setLinks(links links)
    {
        this.links = new links(links);
    }

    public boolean hasLinks()
    {
        return links!=null;
    }
}
