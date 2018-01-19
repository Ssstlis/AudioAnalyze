package Fox.core.lib.services.LastFM.Album;

import Fox.core.lib.services.LastFM.Album.getInfo.GetInfoBuilder;
import Fox.core.lib.services.LastFM.Album.getInfo.sources.AlbumInfo;
import Fox.core.lib.services.LastFM.LastFMClient;
import org.jetbrains.annotations.NotNull;

public class LastFMAlbumClient
{
    public LastFMAlbumClient()
    {

    }
    public AlbumInfo getInfo(String mbid,
                             @NotNull String artist,
                             @NotNull String album,
                             String lang,
                             String username,
                             Boolean AutoCorrect
                            )
    {
        final String method_name = "?method=album.getInfo";
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
            optional += "&username=" + username;

        if (mbid!=null && !mbid.isEmpty())
            optional += "&mbid=" + mbid;

        if (lang!=null && !lang.isEmpty())
            optional += "&lang=" + lang;

        if (!album.isEmpty())
            album = "&album="+album;

        if (!artist.isEmpty())
            artist = "&artist="+artist;

        LastFMClient.RequestHTTPClient
                .build(
                        LastFMClient.api_root+
                                method_name+
                                LastFMClient.api_key+
                                LastFMClient.format+
                                album+
                                artist+
                                optional
                      );
        String response = LastFMClient.RequestHTTPClient.run(0);
        return new GetInfoBuilder().buildAlbumInfo(response);
    }
}