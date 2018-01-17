package Fox.core.lib.services.LastFM.CommonSources;

public class attr
{
    private String position;

    public attr()
    {

    }

    public attr(String position)
    {
        this.position = position;
    }

    public attr(attr copy)
    {
        if (copy!=null)
        {
            this.position = copy.position;
        }
    }

    public String getPosition()
    {
        return position;
    }

    public void setPosition(String position)
    {
        this.position = position;
    }

    public boolean hasPosition()
    {
        return (position!=null && !position.isEmpty());
    }
}
