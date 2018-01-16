package Fox.core.lib.services.LastFM.Track;

import Fox.core.lib.services.LastFM.LastFMClient;
import Fox.core.lib.services.LastFM.Track.getInfo.TrackInfo;
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
                             boolean AutoCorrect
                            )
    {
        final String method_name = "?method=track.getInfo";

        LastFMClient.RequestHTTPClient.build(LastFMClient.api_root+
                                        method_name+
                                        LastFMClient.api_key+
                                        LastFMClient.format);
        String response = LastFMClient.RequestHTTPClient.run(0);
        //TODO PARSE HERE
        return new TrackInfo();
    }
}
