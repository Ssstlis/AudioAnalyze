package Fox.core.lib.services.LastFM.CommonSources;

public class wiki
{
    private String published;
    private String summary;
    private String content;

    public wiki()
    {

    }

    public wiki(
            String published,
            String summary,
            String content)
    {
        this.content = content;
        this.published = published;
        this.summary = summary;
    }

    public wiki(wiki copy)
    {
        if (copy != null)
        {
            this.content = copy.content;
            this.summary = copy.summary;
            this.published = copy.published;
        }
    }

    public String getPublished()
    {
        return published;
    }

    public void setPublished(String published)
    {
        this.published = published;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public boolean hasPublished()
    {
        return (published != null && !published.isEmpty());
    }

    public boolean hasSummary()
    {
        return (summary != null && !summary.isEmpty());
    }

    public boolean hasContent()
    {
        return (content != null && !content.isEmpty());
    }
}
