package Fox.core.lib.service.LastFM;

import Fox.core.lib.general.HttpGetClient;
import org.jetbrains.annotations.NotNull;

public class LastFMClient
{
    private HttpGetClient RequestClient;
    private static final String http = "http://ws.audioscrobbler.com/2.0/?method=track.getInfo";
    private static final String key = "&api_key=9a935ee818781ed7450c4decdc31611c";
    private static final String format = "&format=json";

    public LastFMClient()
    {
        RequestClient = new HttpGetClient();
    }

    public void trackGetInfo(String mbid,
                             @NotNull String track,
                             @NotNull String artist,
                             String username,
                             boolean AutoCorrect
                            )
    {

    }
}
