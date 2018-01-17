package Fox.core.lib.services.LastFM.Track.getInfo.sources;

import Fox.core.lib.services.LastFM.CommonSources.artist;
import Fox.core.lib.services.LastFM.CommonSources.streamable;
import Fox.core.lib.services.LastFM.CommonSources.toptags;
import Fox.core.lib.services.LastFM.CommonSources.wiki;

public class TrackInfo
{
    private String name;
    private String mbid;
    private String url;
    private String duration;
    private String listeners;
    private String playcount;
    private streamable stream;
    private artist artist;
    private album album;
    private wiki wiki;
    private toptags toptags;

    public TrackInfo()
    {

    }

    public TrackInfo(String name,
                     String mbid,
                     String url,
                     String duration,
                     String listeners,
                     String playcount,
                     streamable stream,
                     artist artist,
                     album album,
                     wiki wiki,
                     toptags toptags)
    {
        this.duration = duration;
        this.listeners = listeners;
        this.mbid = mbid;
        this.name = name;
        this.playcount = playcount;
        this.url = url;
        this.stream = new streamable(stream);
        this.artist = new artist(artist);
        this.album = new album(album);
        this.wiki = new wiki(wiki);
        this.toptags = new toptags(toptags);
    }

    public TrackInfo(TrackInfo copy)
    {
        if (copy!=null)
        {
            this.wiki = new wiki(copy.wiki);
            this.album = new album(copy.album);
            this.artist = new artist(copy.artist);
            this.stream = new streamable(copy.stream);
            this.playcount = copy.playcount;
            this.listeners = copy.listeners;
            this.duration = copy.duration;
            this.url = copy.url;
            this.mbid = copy.mbid;
            this.name = copy.name;
            this.toptags = new toptags(copy.toptags);
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

    public String getDuration()
    {
        return duration;
    }

    public void setDuration(String duration)
    {
        this.duration = duration;
    }

    public boolean hasDuration()
    {
        return (duration!=null && !duration.isEmpty());
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
        return (listeners!=null && !listeners.isEmpty());
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
        return (playcount!=null && !playcount.isEmpty());
    }

    public streamable getStreamable()
    {
        return new streamable(stream);
    }

    public void setStreamable(streamable stream)
    {
        this.stream = new streamable(stream);
    }

    public boolean hasStreamable()
    {
        return stream!=null;
    }

    public artist getArtist()
    {
        return new artist(artist);
    }

    public void setArtist(artist artist)
    {
        this.artist = new artist(artist);
    }

    public boolean hasArtist()
    {
        return artist!=null;
    }

    public album getAlbum()
    {
        return new album(album);
    }

    public void setAlbum(album album)
    {
        this.album = new album(album);
    }

    public boolean hasAlbum()
    {
        return album!=null;
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
        return wiki!=null;
    }

    public toptags getToptags()
    {
        return new toptags(toptags);
    }

    public void setToptags(toptags toptags)
    {
        this.toptags = new toptags(toptags);
    }
}
