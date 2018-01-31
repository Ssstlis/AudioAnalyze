package Fox.core.lib.services.LastFM.Track;

import Fox.core.lib.services.Elapsed;
import Fox.core.lib.services.LastFM.LastFMApi;
import Fox.core.lib.services.LastFM.Track.getInfo.GetInfoBuilder;
import Fox.core.lib.services.LastFM.Track.getInfo.sources.TrackInfo;
import org.jetbrains.annotations.NotNull;


public class LastFMTrackClient
{
    public LastFMTrackClient()
    {

    }

    public TrackInfo getInfo(
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

        String response = LastFMApi.RequestHTTPClient.run(Elapsed.LastFMElapse());
        return new GetInfoBuilder().buildTrackInfo(response);
    }
}
