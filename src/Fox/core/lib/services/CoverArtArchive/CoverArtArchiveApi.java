package Fox.core.lib.services.CoverArtArchive;

import Fox.core.lib.connectors.HttpGetClient;
import Fox.core.lib.services.Common.Elapsed;
import Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.CoverArtStructureBuilder;
import Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources.AlbumArt;
import Fox.core.main.SearchLib;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CoverArtArchiveApi
{
    private static Logger logger;
    private final static String httpkey = "http://coverartarchive.org/release/";

    public CoverArtArchiveApi()
    {
    }

    public static AlbumArt LookupAlbumArt(String mbid)
    {
        logger = LoggerFactory.getLogger(SearchLib.class);
        if (mbid != null)
        {
            HttpGetClient RequestClient = new HttpGetClient(logger);
            RequestClient.build(
                    httpkey + mbid + '/'
                               );
            CoverArtArchiveResponse response;
            AlbumArt buildAlbumArt = null;

            try
            {
                response = new CoverArtArchiveResponse(RequestClient.run(Elapsed.MusicBrainzElapse(), 0));

            } catch (Exception e)
            {
                if (logger.isErrorEnabled())
                    logger.error("Cover art archive failure at mbid {}", mbid, e );
                return null;
            }

            if (response != null && response.hasSource())
                buildAlbumArt = CoverArtStructureBuilder.buildAlbumArt(response);
            return buildAlbumArt;
        }
        return null;
    }

    public static class CoverArtArchiveResponse
    {
        private String source;

        public CoverArtArchiveResponse()
        {

        }

        public CoverArtArchiveResponse(String source)
        {
            this.source = source;
        }

        public String getSource()
        {
            return source;
        }

        public void setSource(String source)
        {
            this.source = source;
        }

        public boolean hasSource()
        {
            return (source != null && !source.isEmpty());
        }
    }
}
