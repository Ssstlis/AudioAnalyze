package Fox.core.lib.services.LastFM.Album.getInfo;

import Fox.core.lib.services.Common.ParseSupport;
import Fox.core.lib.services.LastFM.Album.getInfo.sources.AlbumInfo;
import Fox.core.lib.services.LastFM.Album.getInfo.sources.album;
import Fox.core.lib.services.LastFM.Album.getInfo.sources.track;
import Fox.core.lib.services.LastFM.Album.getInfo.sources.tracks;
import Fox.core.lib.services.LastFM.CommonSources.CommonBuilder;
import Fox.core.lib.services.LastFM.CommonSources.Error;
import Fox.core.lib.services.LastFM.Track.getInfo.sources.artist;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class GetInfoBuilder
        extends CommonBuilder
{
    public GetInfoBuilder()
    {

    }

    public AlbumInfo buildAlbumInfo(String source)
    {
        if (source == null || source.isEmpty())
        {
            return null;
        }

        JsonParser parser = new JsonParser();
        AlbumInfo temp = null;

        try
        {
            JsonElement element = parser.parse(source);

            JsonObject AlbumInfoObj = element.getAsJsonObject();

            temp = new AlbumInfo();

            Error error = buildError(element);

            temp.setError(error.getError());
            temp.setMessage(error.getMessage());

            temp.setAlbum(buildAlbum(AlbumInfoObj.get("album")));

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return temp;
    }

    private album buildAlbum(JsonElement element)
    {
        album temp = null;

        try
        {
            JsonObject AlbumObj = element.getAsJsonObject();

            temp = new album();

            temp.setName((String)
                                 ParseSupport.GetSource(
                                         AlbumObj,
                                         "name",
                                         String.class
                                                       ));
            temp.setArtist((String)
                                   ParseSupport.GetSource(
                                           AlbumObj,
                                           "artist",
                                           String.class
                                                         ));
            temp.setMbid((String)
                                 ParseSupport.GetSource(
                                         AlbumObj,
                                         "mbid",
                                         String.class
                                                       ));
            temp.setUrl((String)
                                ParseSupport.GetSource(
                                        AlbumObj,
                                        "url",
                                        String.class
                                                      ));
            temp.setListeners((String)
                                      ParseSupport.GetSource(
                                              AlbumObj,
                                              "listeners",
                                              String.class
                                                            ));
            temp.setPlaycount((String)
                                      ParseSupport.GetSource(
                                              AlbumObj,
                                              "playcount",
                                              String.class
                                                            ));
            temp.setImages(buildImageList(AlbumObj));
            temp.setTracks(buildTracks(AlbumObj.get("tracks")));
            temp.setTags(buildToptags(AlbumObj.get("tags")));
            temp.setWiki(buildWiki(AlbumObj.get("wiki")));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return temp;
    }

    private tracks buildTracks(JsonElement element)
    {
        tracks temp = null;

        try
        {
            JsonObject TracksObj = element.getAsJsonObject();

            temp = new tracks();

            temp.setTracks(buildTrackList(TracksObj));

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return temp;
    }

    private List<track> buildTrackList(JsonObject Obj)
    {
        List<track> temp = null;

        try
        {
            JsonArray TrackJList = (JsonArray)
                    ParseSupport.GetSource(
                            Obj,
                            "track",
                            JsonArray.class
                                          );

            if (TrackJList != null)
            {
                temp = new ArrayList<>();
                int size = TrackJList.size();

                for (int i = 0; i < size; i++)
                {
                    temp.add(buildTrack(TrackJList.get(i)));
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return temp;
    }

    private track buildTrack(JsonElement element)
    {
        track temp = null;

        try
        {
            JsonObject TrackObj = element.getAsJsonObject();

            temp = new track();

            temp.setName((String)
                                 ParseSupport.GetSource(
                                         TrackObj,
                                         "name",
                                         String.class
                                                       ));
            temp.setUrl((String)
                                ParseSupport.GetSource(
                                        TrackObj,
                                        "url",
                                        String.class
                                                      ));
            temp.setDuration((String)
                                     ParseSupport.GetSource(
                                             TrackObj,
                                             "duration",
                                             String.class
                                                           ));
            temp.setAttribute(buildAttribute(TrackObj.get("@attr")));
            temp.setStreamable(buildStreamable(TrackObj.get("streamable")));
            temp.setArtist(buildArtist(TrackObj.get("artist")));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return temp;
    }

    @Override
    protected artist buildArtist(JsonElement element)
    {
        artist temp = null;

        try
        {
            JsonObject ArtistObj = element.getAsJsonObject();

            temp = new artist();

            temp.setUrl((String)
                                ParseSupport.GetSource(
                                        ArtistObj,
                                        "url",
                                        String.class
                                                      ));
            temp.setName((String)
                                 ParseSupport.GetSource(
                                         ArtistObj,
                                         "name",
                                         String.class
                                                       ));
            temp.setMbid((String)
                                 ParseSupport.GetSource(
                                         ArtistObj,
                                         "mbid",
                                         String.class
                                                       ));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return temp;
    }
}
