package Fox.core.lib.services.LastFM.Track.getInfo.sources;

public class artist extends Fox.core.lib.services.LastFM.CommonSources.artist
{
    private String mbid;

    public artist()
    {

    }

    public artist(String name,
                  String mbid,
                  String url)
    {
        super(name, url);
        this.mbid = mbid;
    }

    public artist(artist copy)
    {
        if (copy!=null)
        {
            this.setName(copy.getName());
            this.setUrl(copy.getUrl());
            this.mbid = copy.mbid;
        }
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
}
