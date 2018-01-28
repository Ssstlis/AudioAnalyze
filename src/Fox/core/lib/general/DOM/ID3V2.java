package Fox.core.lib.general.DOM;

import Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources.AlbumArt;
import Fox.core.lib.services.LastFM.LastFMTrackInfoCompilation;
import org.musicbrainz.android.api.data.Recording;

import java.util.List;

public class ID3V2
{
    private String title, year, artist, album, comment, genre;
    private List<String> ArtLinks;
    private Integer number;

    //TODO NOT FINAL
    public ID3V2(
            LastFMTrackInfoCompilation compile,
            Recording a,
            AlbumArt b)
    {

    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getYear()
    {
        return year;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    public String getArtist()
    {
        return artist;
    }

    public void setArtist(String artist)
    {
        this.artist = artist;
    }

    public String getAlbum()
    {
        return album;
    }

    public void setAlbum(String album)
    {
        this.album = album;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getGenre()
    {
        return genre;
    }

    public void setGenre(String genre)
    {
        this.genre = genre;
    }

    public List<String> getArtLinks()
    {
        return ArtLinks;
    }

    public void setArtLinks(List<String> artLinks)
    {
        ArtLinks = artLinks;
    }

    public Integer getNumber()
    {
        return number;
    }

    public void setNumber(Integer number)
    {
        this.number = number;
    }
}
