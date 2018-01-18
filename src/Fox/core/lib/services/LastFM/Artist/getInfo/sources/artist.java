package Fox.core.lib.services.LastFM.Artist.getInfo.sources;

import Fox.core.lib.services.LastFM.CommonSources.image;
import Fox.core.lib.services.LastFM.CommonSources.toptags;

import java.util.List;

public class artist extends Fox.core.lib.services.LastFM.Track.getInfo.sources.artist
{
    private List<image> images;
    private String streamable;
    private String ontour;
    private stats stats;
    private similar similar;
    private toptags tags;
    private bio bio;

    public artist()
    {

    }

    public artist(String name,
                  String mbid,
                  String url,
                  String streamable,
                  String ontour,
                  List<image> images,
                  stats stats,
                  similar similar,
                  toptags tags,
                  bio bio)
    {
        super(name, mbid, url);
        this.streamable = streamable;
        this.ontour = ontour;
        this.images = image.imageListCopy(images);
        this.stats = new stats(stats);
        this.similar = new similar(similar);
        this.tags = new toptags(tags);
        this.bio = new bio(bio);
    }

    public artist(artist copy)
    {
        this.setName(copy.getName());
        this.setUrl(copy.getUrl());
        this.setMbid(copy.getMbid());
        this.streamable = copy.streamable;
        this.ontour = copy.ontour;
        this.images = image.imageListCopy(copy.images);
        this.stats = new stats(copy.stats);
        this.similar = new similar(copy.similar);
        this.tags = new toptags(copy.tags);
        this.bio = new bio(copy.bio);
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

    public String getOntour()
    {
        return ontour;
    }

    public void setOntour(String ontour)
    {
        this.ontour = ontour;
    }

    public boolean hasOntour()
    {
        return (ontour!=null && !ontour.isEmpty());
    }

    public stats getStats()
    {
        return new stats(stats);
    }

    public void setStats(stats stats)
    {
        this.stats = new stats(stats);
    }

    public boolean hasStats()
    {
        return stats!=null;
    }

    public similar getSimilar()
    {
        return new similar(similar);
    }

    public void setSimilar(similar similar)
    {
        this.similar = new similar(similar);
    }

    public boolean hasSimilar()
    {
        return similar!=null;
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
        return tags!=null;
    }

    public bio getBio()
    {
        return new bio(bio);
    }

    public void setBio(bio bio)
    {
        this.bio = new bio(bio);
    }

    public boolean hasBio()
    {
        return bio!=null;
    }
}
