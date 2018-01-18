package Fox.core.lib.services.LastFM.Track;

import Fox.core.lib.services.LastFM.LastFMClient;
import Fox.core.lib.services.LastFM.Track.getInfo.GetInfoBuilder;
import Fox.core.lib.services.LastFM.Track.getInfo.sources.TrackInfo;
import org.jetbrains.annotations.NotNull;

public class LastFMTrackClient
{
    public LastFMTrackClient()
    {

    }

    public TrackInfo getInfo(String mbid,
                             @NotNull String track,
                             @NotNull String artist,
                             String username,
                             Boolean AutoCorrect
                            )
    {
        final String method_name = "?method=track.getInfo";
        String optional = "";

        if (AutoCorrect!=null)
            if (AutoCorrect)
            {
                optional += "&autocorrect=1";
            }
            else
            {
                optional += "&autocorrect=0";
            }

        if (username!=null && !username.isEmpty())
            optional += "&username="+username;

        if (mbid!=null && !mbid.isEmpty())
            optional += "&mbid="+mbid;

        LastFMClient.RequestHTTPClient
                    .build(
                            LastFMClient.api_root+
                            method_name+
                            LastFMClient.api_key+
                            LastFMClient.format+
                            "&track="+track+
                            "&artist="+artist+
                            optional
                          );
        String response = LastFMClient.RequestHTTPClient.run(0);
        return new GetInfoBuilder().buildTrackInfo(response);
    }
}
