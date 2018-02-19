package Fox.core.lib.services.LastFM.Artist.getInfo.sources;

import Fox.core.lib.services.LastFM.CommonSources.Error;

public class ArtistInfo
        extends Error
{
    private artist artist;


    public ArtistInfo()
    {

    }

    public ArtistInfo(
            artist artist,
            Integer error,
            String message)
    {
        super(error,
              message
             );
        this.artist = new artist(artist);
    }

    public ArtistInfo(ArtistInfo copy)
    {
        if (copy != null)
        {
            this.artist = new artist(copy.artist);
            this.setError(copy.getError());
            this.setMessage(copy.getMessage());
        }
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
