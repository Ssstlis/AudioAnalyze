package Fox.core.lib.services.LastFM.Album.getInfo.sources;

import Fox.core.lib.services.LastFM.CommonSources.attr;
import Fox.core.lib.services.LastFM.CommonSources.streamable;
import Fox.core.lib.services.LastFM.Track.getInfo.sources.artist;

import java.util.ArrayList;
import java.util.List;

public class track
{
    private String name;
    private String url;
    private String duration;
    private attr attribute;
    private streamable streamable;
    private artist artist;

    public track()
    {

    }

    public track(
            String name,
            String url,
            String duration,
            attr attribute,
            streamable streamable,
            artist artist)
    {
        this.name = name;
        this.url = url;
        this.duration = duration;
        this.attribute = new attr(attribute);
        this.streamable = new streamable(streamable);
        this.artist = new artist(artist);
    }

    public track(track copy)
    {
        if (copy != null)
        {
            this.name = copy.name;
            this.url = copy.url;
            this.duration = copy.duration;
            this.attribute = new attr(copy.attribute);
            this.streamable = new streamable(copy.streamable);
            this.artist = new artist(copy.artist);
        }
    }

    public static List<track> trackListCopy(List<track> tracks)
    {
        List<track> temp = null;

        if (tracks != null)
        {
            temp = new ArrayList<>();

            for (track elem : tracks)
            {
                temp.add(new track(elem));
            }
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
        return (duration != null && !duration.isEmpty());
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
        return attribute != null;
    }

    public streamable getStreamable()
    {
        return new streamable(streamable);
    }

    public void setStreamable(streamable streamable)
    {
        this.streamable = new streamable(streamable);
    }

    public boolean hasStreamable()
    {
        return streamable != null;
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
        return artist != null;
    }
}
