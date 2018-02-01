package Fox.core.lib.services.CoverArtArchive;

import Fox.core.lib.connectors.HttpGetClient;
import Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.CoverArtStructureBuilder;
import Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources.AlbumArt;
import Fox.core.lib.services.LastFM.Album.getInfo.sources.AlbumInfo;

import java.io.IOException;

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
                response = new CoverArtArchiveResponse(RequestClient.run(0));

            } catch (Exception e)
            {
                logger.log(SEVERE, "", e );
                return null;
            }

            if (response != null && response.hasSource())
                buildAlbumArt = CoverArtStructureBuilder.buildAlbumArt(response);
            return buildAlbumArt;
        }
        return null;
    }

}
