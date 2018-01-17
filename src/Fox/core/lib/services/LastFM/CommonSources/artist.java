package Fox.core.lib.services.LastFM.CommonSources;

public class artist
{
    private String name;
    private String mbid;
    private String url;

    public artist()
    {

    }

    public artist(String name,
                  String mbid,
                  String url)
    {
        this.mbid = mbid;
        this.name = name;
        this.url = url;
    }

    public artist(artist copy)
    {
        if (copy!=null)
        {
            this.url = copy.url;
            this.mbid = copy.mbid;
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

    public String getMbid()
    {
        return mbid;
    }

    public void setMbid(String mbid)
    {
        this.mbid = mbid;
    }

    public boolean hasMbid()
    {
        return (mbid!=null && !mbid.isEmpty());
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
