package Fox.core.lib.services.Common;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class SimpleInfo implements Comparable<SimpleInfo>
{
    private String Artist;
    private String MBID;
    private String Title;
    private Integer usages;

    public SimpleInfo()
    {

    }

    public SimpleInfo(
            String artist,
            String mbid,
            String title,
            Integer usages)
    {
        this.Artist = artist;
        this.MBID = mbid;
        this.Title = title;
        this.usages = usages;
    }

    public SimpleInfo(@NotNull SimpleInfo copy)
    {
        this.usages = copy.usages;
        this.Artist = copy.Artist;
        this.MBID = copy.MBID;
        this.Title = copy.Title;
    }

    public String getArtist()
    {
        return Artist;
    }

    public void setArtist(String artist)
    {
        Artist = artist;
    }

    public boolean hasArtist()
    {
        return (Artist!=null && !Artist.isEmpty());
    }

    public String getMBID()
    {
        return MBID;
    }

    public void setMBID(String MBID)
    {
        this.MBID = MBID;
    }

    public boolean hasMBID()
    {
        return (MBID!=null && !MBID.isEmpty());
    }

    public String getTitle()
    {
        return Title;
    }

    public void setTitle(String title)
    {
        Title = title;
    }

    public boolean hasTitle()
    {
        return (Title!=null && !Title.isEmpty());
    }

    public Integer getUsages()
    {
        return usages;
    }

    public void setUsages(Integer usages)
    {
        this.usages = usages;
    }

    public boolean hasUsages()
    {
        return (usages!=null && usages == 0);
    }

    /** Comparing, implements by Comparable<T> based on usages comparing
     * @param o comparing instance
     * @return 1 if instance usage more, then usage of o param. 0 if equals. -1 if less.
     */
    @Override
    public int compareTo(@NotNull SimpleInfo o)
    {
        if (this.usages > o.usages)
            return 1;
        if (this.usages < o.usages)
            return -1;
        return 0;
    }

}
