package Fox.core.lib.services.LastFM.Track;

import Fox.core.lib.connectors.HttpGetClient;
import Fox.core.lib.services.common.Elapsed;
import Fox.core.lib.services.LastFM.LastFMApi;
import Fox.core.lib.services.LastFM.Track.getInfo.GetInfoBuilder;
import Fox.core.lib.services.LastFM.Track.getInfo.sources.TrackInfo;
import Fox.core.main.SearchLib;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LastFMTrackClient
{
    private static Logger logger;
    public LastFMTrackClient()
    {

    }

    public static TrackInfo getInfo(
            String mbid,
            @NotNull String track,
            @NotNull String artist,
            String username,
            Boolean AutoCorrect
                            )
    {
        logger = LoggerFactory.getLogger(SearchLib.class);
        HttpGetClient RequestHTTPClient = new HttpGetClient(logger);
        final String method_name = "?method=track.getInfo";
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

        if (!track.isEmpty())
        {
            track = "&track=" + track;
        }

        if (!artist.isEmpty())
        {
            artist = "&artist=" + artist;
        }

        String request = LastFMApi.api_root +
                method_name +
                LastFMApi.api_key +
                LastFMApi.format +
                track +
                artist +
                optional;

        RequestHTTPClient.build(request);

        String response;
        TrackInfo buildTrackInfo = null;

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
            buildTrackInfo = GetInfoBuilder.buildTrackInfo(response);

        return buildTrackInfo;
    }
}
