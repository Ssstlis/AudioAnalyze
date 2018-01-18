package Fox.core.lib.services.LastFM.Album.getInfo.sources;

import Fox.core.lib.services.LastFM.CommonSources.Error;

public class AlbumInfo extends Error
{
    private album album;

    public AlbumInfo()
    {

    }

    public AlbumInfo(album artist,
                     Integer error,
                     String message)
    {
        super(error, message);
        this.album = new album(artist);
    }

    public AlbumInfo(AlbumInfo copy)
    {
        if (copy!=null)
        {
            this.album = new album(copy.album);
            this.setError(copy.getError());
            this.setMessage(copy.getMessage());
        }
    }

    public album getAlbum()
    {
        return album;
    }

    public void setAlbum(album album)
    {
        this.album = album;
    }

    public boolean hasAlbum()
    {
        return album!=null;
    }
}
