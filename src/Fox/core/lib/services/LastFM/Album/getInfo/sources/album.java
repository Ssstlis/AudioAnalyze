package Fox.core.lib.services.LastFM.Album.getInfo.sources;

import Fox.core.lib.services.LastFM.CommonSources.image;
import Fox.core.lib.services.LastFM.CommonSources.toptags;
import Fox.core.lib.services.LastFM.CommonSources.wiki;

import java.util.List;

public class album
{
    private String name;
    private String artist;
    private String mbid;
    private String url;
    private String listeners;
    private String playcount;
    private List<image> images;
    private tracks tracks;
    private toptags tags;
    private wiki wiki;


    public album()
    {

    }

    public album(
            String name,
            String artist,
            String mbid,
            String url,
            String listeners,
            String playcount,
            List<image> images,
            tracks tracks,
            toptags tags,
            wiki wiki
                )
    {
        this.name = name;
        this.artist = artist;
        this.mbid = mbid;
        this.url = url;
        this.listeners = listeners;
        this.playcount = playcount;
        this.images = image.imageListCopy(images);
        this.tracks = new tracks(tracks);
        this.tags = new toptags(tags);
        this.wiki = new wiki(wiki);
    }

    public album(album copy)
    {
        if (copy != null)
        {
            this.name = copy.name;
            this.artist = copy.artist;
            this.mbid = copy.mbid;
            this.url = copy.url;
            this.listeners = copy.listeners;
            this.playcount = copy.playcount;
            this.images = image.imageListCopy(copy.images);
            this.tracks = new tracks(copy.tracks);
            this.tags = new toptags(copy.tags);
            this.wiki = new wiki(copy.wiki);
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
        return (mbid != null && !mbid.isEmpty());
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

    public String getListeners()
    {
        return listeners;
    }

    public void setListeners(String listeners)
    {
        this.listeners = listeners;
    }

    public boolean hasListeners()
    {
        return (listeners != null && !listeners.isEmpty());
    }

    public String getPlaycount()
    {
        return playcount;
    }

    public void setPlaycount(String playcount)
    {
        this.playcount = playcount;
    }

    public boolean hasPlaycount()
    {
        return (playcount != null && !playcount.isEmpty());
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
        return (images != null && !images.isEmpty());
    }

    public tracks getTracks()
    {
        return new tracks(tracks);
    }

    public void setTracks(tracks tracks)
    {
        this.tracks = new tracks(tracks);
    }

    public boolean hasTracks()
    {
        return tracks != null;
    }

    public toptags getTags()
    {
        return new toptags(tags);
    }

    public void setTags(toptags tags)
    {
        this.tags = new toptags(tags);
    }

    public boolean hasTags()
    {
        return tags != null;
    }

    public wiki getWiki()
    {
        return new wiki(wiki);
    }

    public void setWiki(wiki wiki)
    {
        this.wiki = new wiki(wiki);
    }

    public boolean hasWiki()
    {
        return wiki != null;
    }
}
