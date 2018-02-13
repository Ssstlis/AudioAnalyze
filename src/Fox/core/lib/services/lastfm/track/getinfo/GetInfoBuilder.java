package Fox.core.lib.services.LastFM.Track.getInfo;

import Fox.core.lib.services.Common.ParseSupport;
import Fox.core.lib.services.LastFM.CommonSources.CommonBuilder;
import Fox.core.lib.services.LastFM.CommonSources.Error;
import Fox.core.lib.services.LastFM.Track.getInfo.sources.TrackInfo;
import Fox.core.lib.services.LastFM.Track.getInfo.sources.album;
import Fox.core.lib.services.LastFM.Track.getInfo.sources.artist;
import Fox.core.lib.services.LastFM.Track.getInfo.sources.track;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GetInfoBuilder
        extends CommonBuilder
{
    public GetInfoBuilder()
    {

    }

    public static TrackInfo buildTrackInfo(String source)
    {
        if (source == null || source.isEmpty())
        {
            return null;
        }

        JsonParser parser = new JsonParser();
        TrackInfo temp = null;

        try
        {
            JsonElement element = parser
                    .parse(source);

            JsonObject TrackInfoObj = element
                    .getAsJsonObject();
            temp = new TrackInfo();

            Error error = buildError(element);

            temp.setError(error.getError());
            temp.setMessage(error.getMessage());

            temp.setTrack(buildTrack(TrackInfoObj.get("track")));
        }
        catch (Exception ignored)
        {

        }

        return temp;
    }

    private static track buildTrack(JsonElement element)
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
            temp.setDuration((String)
                                     ParseSupport.GetSource(
                                             TrackObj,
                                             "duration",
                                             String.class
                                                           ));
            temp.setMbid((String)
                                 ParseSupport.GetSource(
                                         TrackObj,
                                         "mbid",
                                         String.class
                                                       ));
            temp.setUrl((String)
                                ParseSupport.GetSource(
                                        TrackObj,
                                        "url",
                                        String.class
                                                      ));
            temp.setListeners((String)
                                      ParseSupport.GetSource(
                                              TrackObj,
                                              "listeners",
                                              String.class
                                                            ));
            temp.setPlaycount((String)
                                      ParseSupport.GetSource(
                                              TrackObj,
                                              "playcount",
                                              String.class
                                                            ));

            temp.setStreamable(buildStreamable(TrackObj.get("streamable")));
            temp.setArtist(buildArtist1(TrackObj.get("artist")));
            temp.setAlbum(buildAlbum(TrackObj.get("album")));
            temp.setToptags(buildToptags(TrackObj.get("toptags")));
            temp.setWiki(buildWiki(TrackObj.get("wiki")));
        }
        catch (Exception ignored)
        {

        }

        return temp;
    }

    private static artist buildArtist1(JsonElement element)
    {
        artist temp = null;

        try
        {
            JsonObject ArtistObj = element.getAsJsonObject();
            temp = new artist();
            Fox.core.lib.services.LastFM.CommonSources.artist artist = buildArtist(element);

            temp.setUrl(artist.getUrl());
            temp.setName(artist.getName());

            temp.setMbid((String)
                                 ParseSupport.GetSource(
                                         ArtistObj,
                                         "mbid",
                                         String.class
                                                       ));
        }
        catch (Exception ignored)
        {

        }
        return temp;
    }

    private static album buildAlbum(JsonElement element)
    {
        album temp = null;

        try
        {
            JsonObject AlbumObj = element.getAsJsonObject();
            temp = new album();

            temp.setArtist((String)
                                   ParseSupport.GetSource(
                                           AlbumObj,
                                           "artist",
                                           String.class
                                                         ));
            temp.setTitle((String)
                                  ParseSupport.GetSource(
                                          AlbumObj,
                                          "title",
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

            temp.setAttribute(buildAttribute(AlbumObj.get("@attr")));
            temp.setImages(buildImageList(AlbumObj));
        }
        catch (Exception ignored)
        {

        }

        return temp;
    }
}
