package Fox.core.lib.services.CoverArtArchive;

import Fox.core.lib.connectors.HttpGetClient;
import Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.CoverArtStructureBuilder;
import Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources.AlbumArt;
import Fox.core.lib.services.Elapsed;

import static Fox.core.main.AudioAnalyzeLibrary.logger;
import static java.util.logging.Level.SEVERE;

public class CoverArtArchiveClient
{
    private final static String httpkey = "http://coverartarchive.org/release/";

    public CoverArtArchiveClient()
    {
    }

    public static AlbumArt LookupAlbumArt(String mbid)
    {
        if (mbid != null)
        {
            HttpGetClient RequestClient = new HttpGetClient();
            RequestClient.build(
                    httpkey + mbid + '/'
                               );
            CoverArtArchiveResponse response;
            AlbumArt buildAlbumArt = null;

            try
            {
                response = new CoverArtArchiveResponse(RequestClient.run(Elapsed.MusicBrainzElapse()));

            } catch (Exception e)
            {
                logger.log(SEVERE, "Cover art archive failure at mbid " + mbid, e );
                return null;
            }

            if (response != null && response.hasSource())
                buildAlbumArt = CoverArtStructureBuilder.buildAlbumArt(response);
            return buildAlbumArt;
        }
        return null;
    }

}
