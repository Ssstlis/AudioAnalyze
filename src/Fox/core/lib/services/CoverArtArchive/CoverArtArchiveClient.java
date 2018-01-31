package Fox.core.lib.services.CoverArtArchive;

import Fox.core.lib.connectors.HttpGetClient;
import Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.CoverArtStructureBuilder;
import Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources.AlbumArt;

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
            CoverArtArchiveResponse response = new CoverArtArchiveResponse(
                    RequestClient
                            .run(0)
            );

            return new CoverArtStructureBuilder()
                    .buildAlbumArt(response);
        }
        return null;
    }

}
