package Fox.core.lib.services.LastFM.CommonSources;

public class streamable
{
    private String text;
    private String fulltrack;

    public streamable()
    {

    }

    public streamable(
            String text,
            String fulltrack)
    {
        this.text = text;
        this.fulltrack = fulltrack;
    }

    public streamable(streamable copy)
    {
        if (copy != null)
        {
            this.fulltrack = copy.fulltrack;
            this.text = copy.text;
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

    public String getFulltrack()
    {
        return fulltrack;
    }

    public void setFulltrack(String fulltrack)
    {
        this.fulltrack = fulltrack;
    }

    public boolean hasFulltrack()
    {
        return (fulltrack != null && !fulltrack.isEmpty());
    }
}
