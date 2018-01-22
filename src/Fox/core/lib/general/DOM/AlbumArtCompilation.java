package Fox.core.lib.general.DOM;

import java.util.List;

public class AlbumArtCompilation
{
    private List<Art> ArtList;
    private String Title;
    private String Artist;

    public AlbumArtCompilation()
    {

    }

    public AlbumArtCompilation(String title,
                               String artist,
                               List<Art> ArtList)
    {
        this.Title = title;
        this.Artist = artist;
        this.ArtList = Art.ArtListCopy(ArtList);
    }

    public AlbumArtCompilation(AlbumArtCompilation copy)
    {
        if (copy!=null)
        {
            this.ArtList = Art.ArtListCopy(copy.ArtList);
            this.Artist = copy.Artist;
            this.Title = copy.Title;
        }
    }

    public AlbumArtCompilation(List<Art> artList)
    {

        ArtList = Art.ArtListCopy(artList);
    }

    public List<Art> getArtList()
    {
        return Art.ArtListCopy(ArtList);
    }

    public void setArtList(List<Art> artList)
    {
        ArtList = Art.ArtListCopy(ArtList);
    }

    public boolean hasArtList()
    {
        return (ArtList!=null && !ArtList.isEmpty());
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

    public void setArtist(String artist)
    {
        Artist = artist;
    }

    public String getArtist()
    {
        return Artist;
    }

    public boolean hasArtist()
    {
        return (Artist!=null && !Artist.isEmpty());
    }
}
