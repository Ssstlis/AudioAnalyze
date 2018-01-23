package Fox.core.lib.services.LastFM;

import Fox.core.lib.connectors.HttpGetClient;
import Fox.core.lib.connectors.HttpsPostClient;
import Fox.core.lib.services.LastFM.Album.LastFMAlbumClient;
import Fox.core.lib.services.LastFM.Artist.LastFMArtistClient;
import Fox.core.lib.services.LastFM.Track.LastFMTrackClient;

public class LastFMClient
{
    public static final String api_root = "http://ws.audioscrobbler.com/2.0/";
    public static final String api_key = "&api_key=9a935ee818781ed7450c4decdc31611c";
    public static final String format = "&format=json";
    public static final HttpGetClient RequestHTTPClient = new HttpGetClient();
    public static final HttpsPostClient RequestHTTPSClient = new HttpsPostClient();

    public LastFMTrackClient Track = new LastFMTrackClient();
    public LastFMArtistClient Artist = new LastFMArtistClient();
    public LastFMAlbumClient Album = new LastFMAlbumClient();

    public LastFMClient()
    {
    }
}
