package Fox.core.lib.services.LastFM.Track.getInfo.sources;

import Fox.core.lib.services.LastFM.CommonSources.attr;
import Fox.core.lib.services.LastFM.CommonSources.image;

import java.util.List;

public class album
{
    private String artist;
    private String title;
    private String mbid;
    private String url;
    private List<image> images;
    private attr attribute;

    public album()
    {

    }

    public album(String artist,
                 String title,
                 String mbid,
                 String url,
                 List<image> images,
                 attr attribute)
    {
        this.artist = artist;
        this.attribute = new attr(attribute);
        this.images = image.imageListCopy(images);
        this.mbid = mbid;
        this.url = url;
        this.title = title;
    }

    public album(album copy)
    {
        if (copy!=null)
        {
            this.attribute = new attr(copy.attribute);
            this.images = image.imageListCopy(copy.images);
            this.url = copy.url;
            this.mbid = copy.mbid;
            this.artist = copy.artist;
            this.title = copy.title;
        }
    }

    public String getArtist()
    {
        return artist;
    }

    public void setArtist(String artist)
    {
        this.artist = artist;
    }

    public boolean hasArtist()
    {
        return (artist!=null && !artist.isEmpty());
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public boolean hasTitle()
    {
        return (title!=null && !title.isEmpty());
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

    public List<image> getImages()
    {
        return image.imageListCopy(images);
    }

    public void setImages(List<image> images)
    {
        this.images = image.imageListCopy(images);
    }

    public boolean hasImages()
    {
        return (images!=null && !images.isEmpty());
    }

    public attr getAttribute()
    {
        return new attr(attribute);
    }

    public void setAttribute(attr attribute)
    {
        this.attribute = new attr(attribute);
    }

    public boolean hasAttribute()
    {
        return attribute!=null;
    }
}
