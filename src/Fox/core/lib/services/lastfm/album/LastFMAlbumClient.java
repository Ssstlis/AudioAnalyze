package Fox.core.lib.services.LastFM.Album;

import Fox.core.lib.connectors.HttpGetClient;
import Fox.core.lib.services.Common.Elapsed;
import Fox.core.lib.services.LastFM.Album.getInfo.GetInfoBuilder;
import Fox.core.lib.services.LastFM.Album.getInfo.sources.AlbumInfo;
import Fox.core.lib.services.LastFM.Album.search.SearchBuilder;
import Fox.core.lib.services.LastFM.Album.search.sources.Search;
import Fox.core.lib.services.LastFM.LastFMApi;
import Fox.core.main.SearchLib;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class LastFMAlbumClient
{
    private static Logger logger;

    public static AlbumInfo getInfo(
            String mbid,
            @NotNull String artist,
            @NotNull String album,
            String lang,
            String username,
            Boolean AutoCorrect)
    {
        logger = LoggerFactory.getLogger(SearchLib.class);
        HttpGetClient RequestHTTPClient = new HttpGetClient(logger);
        final String method_name = "?method=album.getInfo";
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
        String request = LastFMApi.api_root +
                method_name +
                LastFMApi.api_key +
                LastFMApi.format +
                (!album.isEmpty() ? ("&album=" + album) : ("")) +
                (!artist.isEmpty() ? ("&artist=" + artist) : ("")) +
                optional;
        RequestHTTPClient.build(request);
        String response;
        AlbumInfo buildAlbumInfo = null;

        try
        {
            response = RequestHTTPClient.run(Elapsed.LastFMElapse(), 0);
        }
        catch (IOException | InterruptedException e)
        {
            if (logger.isErrorEnabled())
                logger.error("", e);
            return null;
        }
        if (response != null)
            buildAlbumInfo = GetInfoBuilder.buildAlbumInfo(response);

        return buildAlbumInfo;
    }

    public static Search search(
            Integer limit,
            Integer page,
            @NotNull String album
                        )
    {
        HttpGetClient RequestHTTPClient = new HttpGetClient(logger);
        final String method_name = "?method=album.search";
        String optional = "";

        if (limit != null && limit != 0)
        {
            optional += "&limit=" + limit.toString();
        }

        if (page != null && page != 0)
        {
            optional += "&page=" + page.toString();
        }

        String request = LastFMApi.api_root +
                method_name +
                LastFMApi.api_key +
                LastFMApi.format +
                (!album.isEmpty() ? ("&album=" + album) : ("")) +
                optional;

        RequestHTTPClient.build(request);
        String response;
        Search buildSearch = null;

        try
        {
            response = RequestHTTPClient.run(Elapsed.LastFMElapse(), 0);
        }
        catch (IOException | InterruptedException e)
        {
            if (logger.isErrorEnabled())
                logger.error("", e);
            return null;
        }

        if (response != null)
            buildSearch =  SearchBuilder.buildSearch(response);

        return buildSearch;
    }
}