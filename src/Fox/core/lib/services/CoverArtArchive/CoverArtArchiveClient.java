package Fox.core.lib.services.CoverArtArchive;

import Fox.core.lib.connectors.HttpGetClient;
import Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.CoverArtStructureBuilder;
import Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources.AlbumArt;
import Fox.core.lib.services.Elapsed;

public class CoverArtArchiveClient
{
    private final static String httpkey = "http://coverartarchive.org/release/";
    private HttpGetClient RequestClient;

    public CoverArtArchiveClient()
    {
        RequestClient = new HttpGetClient();
    }

    public AlbumArt LookupAlbumArt(String mbid)
    {
        if (mbid != null)
        {
            RequestClient.build(
                    httpkey + mbid + '/'
                               );
            CoverArtArchiveResponse response = new CoverArtArchiveResponse(
                    RequestClient
                            .run(
                                    (System.currentTimeMillis() - Elapsed.CoverArtArchiveElapse > Elapsed.CoverArtArchiveElapseState || !Elapsed.CoverArtArchiveUsage)
                                    ? (0) : (System.currentTimeMillis() - Elapsed.CoverArtArchiveElapse)
                                )
            );

            Elapsed.CoverArtArchiveUsage = true;
            Elapsed.CoverArtArchiveElapse = System.currentTimeMillis();

            AlbumArt temp = new CoverArtStructureBuilder()
                    .buildAlbumArt(response);
            return temp;
        }
        return null;
    }

}
