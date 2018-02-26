package Fox.core.lib.general.data;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class ID3V2
{
    private String title;
    private String ArtistMBID;
    private String TrackMBID;
    private String AlbumMBID;
    private String year;
    private String artist;
    private String album;
    private String comment;
    private String tag;
    private List<String> ArtLinks;
    private Integer number;

    public ID3V2()
    {

    }

    public ID3V2(String title,
                 String artistMBID,
                 String trackMBID,
                 String albumMBID,
                 String year,
                 String artist,
                 String album,
                 String comment,
                 String tag,
                 Integer number,
                 List<String> ArtLinks
    )
    {
        ArtistMBID = artistMBID;
        TrackMBID = trackMBID;
        AlbumMBID = albumMBID;
        this.album = album;
        this.artist = artist;
        this.ArtLinks = ArtLinks;
        this.comment = comment;
        this.tag = tag;
        this.number = number;
        this.title = title;
        this.year = year;
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
        return (title != null && !title.isEmpty());
    }

    public String getYear()
    {
        return year;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    public boolean hasYear()
    {
        return (year != null && !year.isEmpty());
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

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public boolean hasComment()
    {
        return (comment != null && !comment.isEmpty());
    }

    public String getTag()
    {
        return tag;
    }

    public void setTag(String tag)
    {
        this.tag = tag;
    }

    public boolean hasTag()
    {
        return (tag != null && !tag.isEmpty());
    }

    public List<String> getArtLinks()
    {
        return ArtLinks;
    }

    public void setArtLinks(List<String> artLinks)
    {
        ArtLinks = artLinks;
    }

    public boolean hasArtLinks()
    {
        return (ArtLinks != null && !ArtLinks.isEmpty());
    }

    public Integer getNumber()
    {
        return number;
    }

    public void setNumber(Integer number)
    {
        this.number = number;
    }

    public boolean hasNumber()
    {
        return (number != null);
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

    public String getTrackMBID()
    {
        return TrackMBID;
    }

    public void setTrackMBID(String trackMBID)
    {
        TrackMBID = trackMBID;
    }

    public boolean hasTrackMBID()
    {
        return (TrackMBID != null && !TrackMBID.isEmpty());
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

    public static class ID3V2Comparator implements Comparator<ID3V2>
    {

        @Override
        public int compare(@NotNull ID3V2 a,
                           @NotNull ID3V2 b)
        {
            if (!a.hasYear() && !b.hasYear())
                return 0;
            if (!a.hasYear() && b.hasYear())
                return -1;
            if (a.hasYear() && !b.hasYear())
                return 1;
            Integer aYear = Integer.valueOf(a.year);
            Integer bYear = Integer.valueOf(b.year);
            return aYear.compareTo(bYear);
        }

        public Comparator<ID3V2> reversed()
        {
            return new Comparator<ID3V2>()
            {
                @Override
                public int compare(ID3V2 a, ID3V2 b)
                {
                    if (!a.hasYear() && !b.hasYear())
                        return 0;
                    if (!a.hasYear() && b.hasYear())
                        return 1;
                    if (a.hasYear() && !b.hasYear())
                        return -1;
                    Integer aYear = Integer.parseInt(a.year);
                    Integer bYear = Integer.parseInt(b.year);
                    return bYear.compareTo(aYear);
                }

                public Comparator<ID3V2> reversed()
                {
                    return new ID3V2Comparator();
                }
            };
        }
    }
}
