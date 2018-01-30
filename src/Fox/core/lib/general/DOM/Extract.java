package Fox.core.lib.general.DOM;

public class Extract
{
    private String text;
    private String size;

    public Extract()
    {

    }

    public Extract(String text,
                   String size)
    {
        this.size = size;
        this.text = text;
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

    public String getSize()
    {
        return size;
    }

    public void setSize(String size)
    {
        this.size = size;
    }

    public boolean hasSize()
    {
        return (size != null && !size.isEmpty());
    }
}
