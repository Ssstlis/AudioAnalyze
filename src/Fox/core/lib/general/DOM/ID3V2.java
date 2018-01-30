package Fox.core.lib.general.DOM;

import java.util.List;

public class ID3V2
{
    private String title;
    private String year;
    private String artist;
    private String album;
    private String comment;
    private String genre;
    private List<String> ArtLinks;
    private Integer number;

    public ID3V2()
    {

    }

    public ID3V2(String title,
                 String year,
                 String artist,
                 String album,
                 String comment,
                 String genre,
                 Integer number,
                 List<String> ArtLinks
                )
    {
        this.album = album;
        this.artist = artist;
        this.ArtLinks = ArtLinks;
        this.comment = comment;
        this.genre = genre;
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

    public String getGenre()
    {
        return genre;
    }

    public void setGenre(String genre)
    {
        this.genre = genre;
    }

    public boolean hasGenre()
    {
        return (genre != null && !genre.isEmpty());
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
        return (number != null && number != 0);
    }
}
