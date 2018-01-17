package Fox.core.lib.services.LastFM.Track.getInfo;

import Fox.core.lib.services.Common.ParseSupport;
import Fox.core.lib.services.LastFM.CommonSources.*;
import Fox.core.lib.services.LastFM.Track.getInfo.sources.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GetInfoBuilder
{
    public GetInfoBuilder()
    {

    }

    public TrackInfo buildTrackInfo(@NotNull String source)
    {
        JsonParser parser = new JsonParser();
        TrackInfo temp = null;

        try
        {
            JsonElement element =  parser
                    .parse(source);

            JsonObject TrackInfoObj = element
                    .getAsJsonObject();

            temp = new TrackInfo();

            temp.setDuration((String)
                                     ParseSupport.GetSource(
                                             TrackInfoObj,
                                             "duration",
                                             String.class
                                                           ));
            temp.setMbid((String)
                                     ParseSupport.GetSource(
                                             TrackInfoObj,
                                             "mbid",
                                             String.class
                                                           ));
            temp.setUrl((String)
                                     ParseSupport.GetSource(
                                             TrackInfoObj,
                                             "url",
                                             String.class
                                                           ));
            temp.setListeners((String)
                                     ParseSupport.GetSource(
                                             TrackInfoObj,
                                             "listeners",
                                             String.class
                                                           ));
            temp.setPlaycount((String)
                                     ParseSupport.GetSource(
                                             TrackInfoObj,
                                             "playcount",
                                             String.class
                                                           ));

            temp.setStreamable(buildStreamable(TrackInfoObj.get("streamable")));
            temp.setArtist(buildArtist(TrackInfoObj.get("artist")));
            temp.setAlbum(buildAlbum(TrackInfoObj.get("album")));
            temp.setToptags(buildToptags(TrackInfoObj.get("toptags")));
            temp.setWiki(buildWiki(TrackInfoObj.get("wiki")));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return temp;
    }

    private wiki buildWiki(JsonElement element)
    {
        wiki temp = null;

        try
        {
            JsonObject WikiObj = element.getAsJsonObject();

            temp = new wiki();
            temp.setContent((String)
                                     ParseSupport.GetSource(
                                             WikiObj,
                                             "content",
                                             String.class
                                                           ));
            temp.setPublished((String)
                                     ParseSupport.GetSource(
                                             WikiObj,
                                             "published",
                                             String.class
                                                           ));
            temp.setSummary((String)
                                     ParseSupport.GetSource(
                                             WikiObj,
                                             "summary",
                                             String.class
                                                           ));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return temp;
    }

    private toptags buildToptags(JsonElement element)
    {
        toptags temp = null;
        try
        {
            JsonObject toptagsObj = element.getAsJsonObject();

            temp = new toptags();
            temp.setTags(buildTagList(toptagsObj));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return temp;
    }

    private List<tag> buildTagList(JsonObject Obj)
    {
        List<tag> temp = null;

        try
        {
            JsonArray TagJList = (JsonArray)
                    ParseSupport.GetSource(
                            Obj,
                            "tag",
                            JsonArray.class
                                          );

            if (TagJList!=null)
            {
                temp = new ArrayList<>();
                int size = TagJList.size();

                for(int i = 0;i < size; i++)
                    temp.add(buildTag(TagJList.get(i)));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return temp;
    }

    private tag buildTag(JsonElement element)
    {
        tag temp = null;

        try
        {
            JsonObject TagObj = element.getAsJsonObject();
            temp = new tag();

            temp.setName((String)
                                     ParseSupport.GetSource(
                                             TagObj,
                                             "name",
                                             String.class
                                                           ));
            temp.setUrl((String)
                                     ParseSupport.GetSource(
                                             TagObj,
                                             "url",
                                             String.class
                                                           ));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return temp;
    }

    private streamable buildStreamable(JsonElement element)
    {
        streamable temp = null;

        try
        {
            JsonObject StreamableObj = element.getAsJsonObject();

            temp = new streamable();

            temp.setFulltrack((String)
                                     ParseSupport.GetSource(
                                             StreamableObj,
                                             "fulltrack",
                                             String.class
                                                           ));
            temp.setText((String)
                                     ParseSupport.GetSource(
                                             StreamableObj,
                                             "#text",
                                             String.class
                                                           ));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return temp;
    }

    private artist buildArtist(JsonElement element)
    {
        artist temp = null;

        try
        {
            JsonObject ArtistObj = element.getAsJsonObject();
            temp = new artist();

            temp.setMbid((String)
                                     ParseSupport.GetSource(
                                             ArtistObj,
                                             "mbid",
                                             String.class
                                                           ));
            temp.setName((String)
                                     ParseSupport.GetSource(
                                             ArtistObj,
                                             "name",
                                             String.class
                                                           ));
            temp.setUrl((String)
                                     ParseSupport.GetSource(
                                             ArtistObj,
                                             "url",
                                             String.class
                                                           ));
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
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return temp;
    }

    private attr buildAttribute(JsonElement element)
    {
        attr temp = null;

        try
        {
            JsonObject AttributeObj = element.getAsJsonObject();

            temp = new attr();

            temp.setPosition((String)
                                     ParseSupport.GetSource(
                                             AttributeObj,
                                             "position",
                                             String.class
                                                           ));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return temp;
    }

    private List<image> buildImageList(JsonObject Obj)
    {
        List<image> temp = null;
        try
        {
            JsonArray ImageJList = (JsonArray)
                    ParseSupport.GetSource(
                            Obj,
                            "image",
                            JsonArray.class
                                          );

            if (ImageJList!=null)
            {
                temp = new ArrayList<>();
                int size = ImageJList.size();

                for(int i = 0; i < size; i++)
                    temp.add(buildImage(ImageJList.get(i)));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return temp;

    }

    private image buildImage(JsonElement element)
    {
        image temp = null;

        try
        {
            JsonObject ImageObj = element.getAsJsonObject();

            temp = new image();

            temp.setSize((String)
                                     ParseSupport.GetSource(
                                             ImageObj,
                                             "size",
                                             String.class
                                                           ));
            temp.setText((String)
                                     ParseSupport.GetSource(
                                             ImageObj,
                                             "#text",
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
