package Fox.core.lib.services.Common;

public class SimpleInfo
{
    private String Artist;
    private String MBID;
    private String Title;
    private String Album;

    public SimpleInfo(
            String artist,
            String mbid,
            String title,
            String album)
    {
        this.Artist = artist;
        this.MBID = mbid;
        this.Title = title;
        this.Album = album;
    }

    public String getArtist()
    {
        return Artist;
    }

    public void setArtist(String artist)
    {
        Artist = artist;
    }

    public String getMBID()
    {
        return MBID;
    }

    public void setMBID(String MBID)
    {
        this.MBID = MBID;
    }

    public String getTitle()
    {
        return Title;
    }

    public void setTitle(String title)
    {
        Title = title;
    }

    public String getAlbum()
    {
        return Album;
    }

    public void setAlbum(String album)
    {
        Album = album;
    }
}
