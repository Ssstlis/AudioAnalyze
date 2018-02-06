package Fox.core.lib.services.Common;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class SimpleInfo implements Comparable<SimpleInfo>
{
    private String Artist;
    private String TrackMBID;
    private String Title;
    private String Album;
    private String ArtistMBID;
    private String AlbumMBID;
    private Integer usages;

    public SimpleInfo()
    {

    }

    public SimpleInfo(
            String artist,
            String trackmbid,
            String title,
            String album,
            String artistMBID, String albumMBID, Integer usages)
    {
        this.Artist = artist;
        this.TrackMBID = trackmbid;
        this.Title = title;
        Album = album;
        ArtistMBID = artistMBID;
        AlbumMBID = albumMBID;
        this.usages = usages;
    }

    public SimpleInfo(@NotNull SimpleInfo copy)
    {
        this.usages = copy.usages;
        this.Artist = copy.Artist;
        this.TrackMBID = copy.TrackMBID;
        this.Title = copy.Title;
        Album = copy.Album;
        ArtistMBID = copy.ArtistMBID;
        AlbumMBID = copy.AlbumMBID;
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

    public String getTrackMBID()
    {
        return TrackMBID;
    }

    public void setTrackMBID(String trackMBID)
    {
        this.TrackMBID = trackMBID;
    }

    public boolean hasTrackMBID()
    {
        return (TrackMBID !=null && !TrackMBID.isEmpty());
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

    public String getAlbum()
    {
        return Album;
    }

    public void setAlbum(String album)
    {
        Album = album;
    }

    public boolean hasAlbum()
    {
        return (Album != null && !Album.isEmpty());
    }

    public String getArtistMBID()
    {
        return ArtistMBID;
    }

    public void setArtistMBID(String artistMBID)
    {
        ArtistMBID = artistMBID;
    }

    public boolean hasArtistMBID()
    {
        return (ArtistMBID != null && !ArtistMBID.isEmpty());
    }

    public String getAlbumMBID()
    {
        return AlbumMBID;
    }

    public void setAlbumMBID(String albumMBID)
    {
        AlbumMBID = albumMBID;
    }

    public boolean hasAlbumMBID()
    {
        return (AlbumMBID != null && !AlbumMBID.isEmpty());
    }

    public static class SimpleInfoComparatorByUsages implements Comparator<SimpleInfo>
    {
        @Override
        public int compare(@NotNull SimpleInfo a,
                           @NotNull SimpleInfo b)
        {
            return a.getUsages().compareTo(b.getUsages());
        }
    }
}
