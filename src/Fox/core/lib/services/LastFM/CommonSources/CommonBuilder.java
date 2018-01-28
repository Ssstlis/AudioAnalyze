package Fox.core.lib.services.LastFM.CommonSources;

import Fox.core.lib.services.Common.ParseSupport;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class CommonBuilder
{
    public CommonBuilder()
    {

    }

    protected artist buildArtist(JsonElement element)
    {
        artist temp = null;
        try
        {
            JsonObject ArtistObj = element.getAsJsonObject();

            temp = new artist();

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

    protected attr buildAttribute(JsonElement element)
    {
        attr temp = null;
        try
        {
            JsonObject ArtistObj = element.getAsJsonObject();

            temp = new attr();

            temp.setPosition((String)
                                     ParseSupport.GetSource(
                                             ArtistObj,
                                             "position",
                                             String.class
                                                           ));

            temp.setRank((String)
                                 ParseSupport.GetSource(
                                         ArtistObj,
                                         "rank",
                                         String.class
                                                       ));
            temp.setFor((String)
                                ParseSupport.GetSource(
                                        ArtistObj,
                                        "for",
                                        String.class
                                                      ));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return temp;
    }

    protected Error buildError(JsonElement element)
    {
        Error temp = null;
        try
        {
            JsonObject ArtistObj = element.getAsJsonObject();

            temp = new Error();

            temp.setError((Integer)
                                  ParseSupport.GetSource(
                                          ArtistObj,
                                          "error",
                                          int.class
                                                        ));
            temp.setMessage((String)
                                    ParseSupport.GetSource(
                                            ArtistObj,
                                            "message",
                                            String.class
                                                          ));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return temp;
    }

    protected image buildImage(JsonElement element)
    {
        image temp = null;
        try
        {
            JsonObject ArtistObj = element.getAsJsonObject();

            temp = new image();

            temp.setText((String)
                                 ParseSupport.GetSource(
                                         ArtistObj,
                                         "#text",
                                         String.class
                                                       ));
            temp.setSize((String)
                                 ParseSupport.GetSource(
                                         ArtistObj,
                                         "size",
                                         String.class
                                                       ));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return temp;
    }

    protected List<image> buildImageList(JsonObject Obj)
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
            if (ImageJList != null)
            {
                temp = new ArrayList<>();
                int size = ImageJList.size();

                for (int i = 0; i < size; i++)
                {
                    temp.add(buildImage(ImageJList.get(i)));
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return temp;
    }

    protected streamable buildStreamable(JsonElement element)
    {
        streamable temp = null;

        try
        {
            JsonObject StreamableObj = element.getAsJsonObject();

            temp = new streamable();

            temp.setText((String)
                                 ParseSupport.GetSource(
                                         StreamableObj,
                                         "#text",
                                         String.class
                                                       ));
            temp.setFulltrack((String)
                                      ParseSupport.GetSource(
                                              StreamableObj,
                                              "fulltrack",
                                              String.class
                                                            ));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return temp;
    }

    protected tag buildTag(JsonElement element)
    {
        tag temp = null;

        try
        {
            JsonObject TagObj = element.getAsJsonObject();

            temp = new tag();

            temp.setUrl((String)
                                ParseSupport.GetSource(
                                        TagObj,
                                        "url",
                                        String.class
                                                      ));
            temp.setName((String)
                                 ParseSupport.GetSource(
                                         TagObj,
                                         "name",
                                         String.class
                                                       ));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return temp;
    }

    protected List<tag> buildTagList(JsonObject Obj)
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
            if (TagJList != null)
            {
                temp = new ArrayList<>();
                int size = TagJList.size();

                for (int i = 0; i < size; i++)
                {
                    temp.add(buildTag(TagJList.get(i)));
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return temp;
    }

    protected toptags buildToptags(JsonElement element)
    {
        toptags temp = null;

        try
        {
            JsonObject ToptagsObj = element.getAsJsonObject();

            temp = new toptags();

            temp.setTags(buildTagList(ToptagsObj));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return temp;
    }

    protected wiki buildWiki(JsonElement element)
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
}
