package Fox.core.lib.services.LastFM.Artist;

import Fox.core.lib.connectors.HttpGetClient;
import Fox.core.lib.services.Common.Elapsed;
import Fox.core.lib.services.LastFM.Artist.getInfo.GetInfoBuilder;
import Fox.core.lib.services.LastFM.Artist.getInfo.sources.ArtistInfo;
import Fox.core.lib.services.LastFM.LastFMApi;
import Fox.core.main.SearchLib;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class LastFMArtistClient
{
    private static final Logger logger = LoggerFactory.getLogger(SearchLib.class);
    public LastFMArtistClient()
    {

    }

    public static ArtistInfo getInfo(
            String mbid,
            @NotNull String artist,
            String lang,
            String username,
            Boolean AutoCorrect
                             )
    {
        HttpGetClient RequestHTTPClient = new HttpGetClient(logger);
        final String method_name = "?method=artist.getInfo";
        String optional = "";

        if (AutoCorrect != null)
        {
            if (AutoCorrect)
            {
                optional += "&autocorrect=1";
            }
            else
            {
                optional += "&autocorrect=0";
            }
        }

        if (username != null && !username.isEmpty())
        {
            optional += "&username=" + username;
        }

        if (mbid != null && !mbid.isEmpty())
        {
            optional += "&mbid=" + mbid;
        }

        if (lang != null && !lang.isEmpty())
        {
            optional += "&lang=" + lang;
        }

        if (!artist.isEmpty())
        {
            artist = "&artist=" + artist;
        }

        RequestHTTPClient
                .build(
                        LastFMApi.api_root +
                                method_name +
                                LastFMApi.api_key +
                                LastFMApi.format +
                                artist +
                                optional
                      );

        String response;
        ArtistInfo buildArtistInfo = null;

        try
        {
            response = RequestHTTPClient.run(Elapsed.LastFMElapse(), 0);
        }
        catch (Exception e)
        {
            if (logger.isErrorEnabled())
                logger.error("", e);
            return null;
        }

        if (response != null)
            buildArtistInfo = GetInfoBuilder.buildArtistInfo(response);

        return buildArtistInfo;
    }
}
