package Fox.core.lib.services.LastFM.Artist.getInfo.sources;

public class link
{
    private String text;
    private String rel;
    private String href;

    public link()
    {

    }

    public link(
            String text,
            String rel,
            String href)
    {
        this.href = href;
        this.rel = rel;
        this.text = text;
    }

    public link(link copy)
    {
        if (copy != null)
        {
            this.text = copy.text;
            this.rel = copy.rel;
            this.href = copy.href;
        }
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public boolean hasText()
    {
        return (text != null && !text.isEmpty());
    }

    public String getRel()
    {
        return rel;
    }

    public void setRel(String rel)
    {
        this.rel = rel;
    }

    public boolean hasRel()
    {
        return (rel != null && !rel.isEmpty());
    }

    public String getHref()
    {
        return href;
    }

    public void setHref(String href)
    {
        this.href = href;
    }

    public boolean hasHref()
    {
        return (href != null && !href.isEmpty());
    }
}
