package Fox.core.lib.services.LastFM;

import Fox.core.lib.services.LastFM.Album.getInfo.sources.AlbumInfo;
import Fox.core.lib.services.LastFM.Artist.getInfo.sources.ArtistInfo;
import Fox.core.lib.services.LastFM.Track.getInfo.sources.TrackInfo;

public class LastFMTrackInfoCompilation
{
    private TrackInfo trackInfo;
    private AlbumInfo albumInfo;
    private ArtistInfo artistInfo;

    public LastFMTrackInfoCompilation()
    {

    }

    public LastFMTrackInfoCompilation(
            TrackInfo trackInfo,
            ArtistInfo artistInfo,
            AlbumInfo albumInfo)
    {
        this.albumInfo = albumInfo;
        this.artistInfo = artistInfo;
        this.trackInfo = trackInfo;
    }

    public LastFMTrackInfoCompilation(LastFMTrackInfoCompilation copy)
    {
        if (copy != null)
        {
            this.trackInfo = new TrackInfo(copy.trackInfo);
            this.artistInfo = new ArtistInfo(copy.artistInfo);
            this.albumInfo = new AlbumInfo(copy.albumInfo);
        }
    }

    public TrackInfo getTrackInfo()
    {
        return new TrackInfo(trackInfo);
    }

    public void setTrackInfo(TrackInfo trackInfo)
    {
        this.trackInfo = new TrackInfo(trackInfo);
    }

    public boolean hasTrackInfo()
    {
        return trackInfo != null;
    }

    public AlbumInfo getAlbumInfo()
    {
        return new AlbumInfo(albumInfo);
    }

    public void setAlbumInfo(AlbumInfo albumInfo)
    {
        this.albumInfo = new AlbumInfo(albumInfo);
    }

    public boolean hasAlbumInfo()
    {
        return albumInfo != null;
    }

    public ArtistInfo getArtistInfo()
    {
        return new ArtistInfo(artistInfo);
    }

    public void setArtistInfo(ArtistInfo artistInfo)
    {
        this.artistInfo = new ArtistInfo(artistInfo);
    }

    public boolean hasArtistInfo()
    {
        return artistInfo != null;
    }
}
