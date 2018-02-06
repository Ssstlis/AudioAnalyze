package Fox.core.lib.services.LastFM.Track;

import Fox.core.lib.services.Common.Elapsed;
import Fox.core.lib.services.LastFM.LastFMApi;
import Fox.core.lib.services.LastFM.Track.getInfo.GetInfoBuilder;
import Fox.core.lib.services.LastFM.Track.getInfo.sources.TrackInfo;
import Fox.core.main.AudioAnalyzeLibrary;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LastFMTrackClient
{
    private static final Logger logger = LoggerFactory.getLogger(AudioAnalyzeLibrary.class);
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

        LastFMApi.RequestHTTPClient
                .build(
                        LastFMApi.api_root +
                                method_name +
                                LastFMApi.api_key +
                                LastFMApi.format +
                                track +
                                artist +
                                optional
                      );

        String response;
        TrackInfo buildTrackInfo = null;

        try
        {
            response = LastFMApi.RequestHTTPClient.run(Elapsed.LastFMElapse());
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
