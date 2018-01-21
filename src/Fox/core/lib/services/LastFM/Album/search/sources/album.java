package Fox.core.lib.services.LastFM.Album.search.sources;

import Fox.core.lib.services.LastFM.CommonSources.image;

import java.util.ArrayList;
import java.util.List;

public class album
{
    private String      name;
    private String      artist;
    private String      url;
    private String streamable;
    private String mbid;
    private List<image> images;

    public album()
    {

    }

    public album(
            String name,
            String artist,
            String url,
            String streamable,
            String mbid,
            List<image> images)
    {
        this.name = name;
        this.artist = artist;
        this.url = url;
        this.streamable = streamable;
        this.mbid = mbid;
        this.images = image.imageListCopy(images);
    }

    public album(album copy)
    {
        if (copy != null)
        {
            this.name = copy.name;
            this.artist = copy.artist;
            this.url = copy.url;
            this.images = image.imageListCopy(copy.images);
        }
    }


    public static List<album> getAlbumListCopy(List<album> albumList)
    {
        List<album> temp = null;
        if (albumList != null)
        {
            temp = new ArrayList<>();
            for (album elem : albumList)
                temp.add(new album(elem));
        }
        return temp;
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
        return (name != null && !name.isEmpty());
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
        return (artist != null && !artist.isEmpty());
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

    public String getStreamable()
    {
        return streamable;
    }

    public void setStreamable(String streamable)
    {
        this.streamable = streamable;
    }

    public boolean hasStreamable()
    {
        return (streamable!=null && !streamable.isEmpty());
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
