package Fox.core.lib.services.LastFM.Album;

import Fox.core.lib.services.Elapsed;
import Fox.core.lib.services.LastFM.Album.getInfo.GetInfoBuilder;
import Fox.core.lib.services.LastFM.Album.getInfo.sources.AlbumInfo;
import Fox.core.lib.services.LastFM.Album.search.SearchBuilder;
import Fox.core.lib.services.LastFM.Album.search.sources.Search;
import Fox.core.lib.services.LastFM.LastFMApi;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static Fox.core.main.AudioAnalyzeLibrary.logger;
import static java.util.logging.Level.SEVERE;

public class LastFMAlbumClient
{
    public LastFMAlbumClient()
    {

    }

    public AlbumInfo getInfo(
            String mbid,
            @NotNull String artist,
            @NotNull String album,
            String lang,
            String username,
            Boolean AutoCorrect
                            )
    {
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

        if (!album.isEmpty())
        {
            album = "&album=" + album;
        }

        if (!artist.isEmpty())
        {
            artist = "&artist=" + artist;
        }

        LastFMApi.RequestHTTPClient
                .build(
                        LastFMApi.api_root +
                                method_name +
                                LastFMApi.api_key +
                                LastFMApi.format +
                                album +
                                artist +
                                optional
                      );
        String response = null;
        AlbumInfo buildAlbumInfo = null;

        try
        {
            response = LastFMApi.RequestHTTPClient.run(Elapsed.LastFMElapse());
        }
        catch (IOException | InterruptedException e)
        {
            logger.log(SEVERE, "", e );
            return null;
        }
        if (response != null)
            buildAlbumInfo = GetInfoBuilder.buildAlbumInfo(response);

        return buildAlbumInfo;
    }

    public Search search(
            Integer limit,
            Integer page,
            @NotNull String album
                        )
    {
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

        if (!album.isEmpty())
        {
            album = "&album=" + album;
        }

        LastFMApi.RequestHTTPClient
                .build(
                        LastFMApi.api_root +
                                method_name +
                                LastFMApi.api_key +
                                LastFMApi.format +
                                album +
                                optional
                      );
        String response = null;
        Search buildSearch = null;

        try
        {
            response = LastFMApi.RequestHTTPClient.run(Elapsed.LastFMElapse());
        }
        catch (IOException | InterruptedException e)
        {
            logger.log(SEVERE, "", e );
            return null;
        }

        if (response != null)
            buildSearch =  SearchBuilder.buildSearch(response);

        return buildSearch;
    }
}