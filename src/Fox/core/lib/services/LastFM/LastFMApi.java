package Fox.core.lib.services.LastFM;

import Fox.core.lib.services.LastFM.Album.LastFMAlbumClient;
import Fox.core.lib.services.LastFM.Artist.LastFMArtistClient;
import Fox.core.lib.services.LastFM.Track.LastFMTrackClient;

public class LastFMApi
{
    public static final String api_root = "http://ws.audioscrobbler.com/2.0/";
    public static final String api_key = "&api_key=8aa1524f43ed54e092d754739ea33fd1";
    public static final String format = "&format=json";

    public LastFMTrackClient Track = new LastFMTrackClient();
    public LastFMArtistClient Artist = new LastFMArtistClient();
    public LastFMAlbumClient Album = new LastFMAlbumClient();

    public LastFMApi()
    {
    }
}
