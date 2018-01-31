package Fox.core.lib.services.LastFM.Artist;

import Fox.core.lib.services.Elapsed;
import Fox.core.lib.services.LastFM.Artist.getInfo.GetInfoBuilder;
import Fox.core.lib.services.LastFM.Artist.getInfo.sources.ArtistInfo;
import Fox.core.lib.services.LastFM.LastFMApi;
import org.jetbrains.annotations.NotNull;

public class LastFMArtistClient
{
    public LastFMArtistClient()
    {

    }

    public ArtistInfo getInfo(
            String mbid,
            @NotNull String artist,
            String lang,
            String username,
            Boolean AutoCorrect
                             )
    {
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

        LastFMApi.RequestHTTPClient
                .build(
                        LastFMApi.api_root +
                                method_name +
                                LastFMApi.api_key +
                                LastFMApi.format +
                                artist +
                                optional
                      );
        String response = LastFMApi.RequestHTTPClient.run(Elapsed.LastFMElapse());
        return new GetInfoBuilder().buildArtistInfo(response);
    }
}
