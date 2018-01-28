package Fox.core.lib.general.DOM;

import Fox.core.lib.general.utils.target;

import java.util.ArrayList;
import java.util.List;

public class Art
{
    private String url;
    private String size;
    private String artist;
    private String album;
    private target source;

    public Art()
    {

    }

    public Art(
            String url,
            String size,
            String artist,
            String album,
            target source)
    {
        this.url = url;
        this.size = size;
        this.artist = artist;
        this.album = album;
        this.source = source;
    }

    public Art(Art copy)
    {
        if (copy != null)
        {
            this.size = copy.size;
            this.url = copy.url;
            this.source = copy.source;
            this.artist = copy.artist;
            this.album = copy.album;
        }
    }

    public static List<Art> ArtListCopy(List<Art> copy)
    {
        List<Art> temp = null;

        if (copy != null)
        {
            temp = new ArrayList<>();

            for (Art elem : copy)
            {
                temp.add(new Art(elem));
            }
        }

        return temp;
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
        return (url != null && !url.isEmpty());
    }

    public target getSource()
    {
        return source;
    }

    public void setSource(target source)
    {
        this.source = source;
    }

    public boolean hashSource()
    {
        return source != null;
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

    public String getAlbum()
    {
        return album;
    }

    public void setAlbum(String album)
    {
        this.album = album;
    }

    public boolean hasAlbum()
    {
        return (album != null && !album.isEmpty());
    }
}
