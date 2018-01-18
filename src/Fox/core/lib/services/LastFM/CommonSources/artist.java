package Fox.core.lib.services.LastFM.CommonSources;

public class artist
{
    private String name;
    private String url;

    public artist()
    {

    }

    public artist(String name,
                  String url)
    {
        this.name = name;
        this.url = url;
    }

    public artist(artist copy)
    {
        if (copy!=null)
        {
            this.url = copy.url;
            this.name = copy.name;
        }
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean hasName()
    {
        return (name!=null && !name.isEmpty());
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public boolean hasUrl()
    {
        return (url!=null && !url.isEmpty());
    }
}
