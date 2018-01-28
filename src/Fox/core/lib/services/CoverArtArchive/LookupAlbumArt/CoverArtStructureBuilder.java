package Fox.core.lib.services.CoverArtArchive.LookupAlbumArt;

import Fox.core.lib.services.Common.ParseSupport;
import Fox.core.lib.services.CoverArtArchive.CoverArtArchiveResponse;
import Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources.AlbumArt;
import Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources.image;
import Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources.thumbnail;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CoverArtStructureBuilder
{

    public CoverArtStructureBuilder()
    {

    }

    public AlbumArt buildAlbumArt(
            @NotNull
                    CoverArtArchiveResponse response)
    {
        JsonParser parser = new JsonParser();

        if (!response.hasSource())
        {
            return null;
        }

        AlbumArt temp = new AlbumArt();
        String source = response.getSource();

        try
        {
            JsonElement element = parser.parse(source);

            JsonObject AlbumArtObj = element.getAsJsonObject();

            temp.setRelease((String)
                                    ParseSupport.GetSource(
                                            AlbumArtObj,
                                            "release",
                                            String.class
                                                          ));
            temp.setImages(buildImageList(AlbumArtObj));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return temp;
    }

    private List<image> buildImageList(JsonObject Obj)
    {
        List<image> temp = null;

        try
        {
            JsonArray ImagesJList = (JsonArray)
                    ParseSupport.GetSource(
                            Obj,
                            "images",
                            JsonArray.class
                                          );


            if (ImagesJList != null)
            {
                temp = new ArrayList<>();
                int size = ImagesJList.size();

                for (int i = 0; i < size; i++)
                {
                    temp.add(buildImage(ImagesJList.get(i)));
                }
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
        image temp = new image();

        try
        {
            JsonObject imageObj = element.getAsJsonObject();

            temp.setApproved((boolean)
                                     ParseSupport.GetSource(
                                             imageObj,
                                             "approved",
                                             boolean.class
                                                           ));
            temp.setFront((boolean)
                                  ParseSupport.GetSource(
                                          imageObj,
                                          "front",
                                          boolean.class
                                                        ));
            temp.setBack((boolean)
                                 ParseSupport.GetSource(
                                         imageObj,
                                         "back",
                                         boolean.class
                                                       ));
            temp.setEdit((long)
                                 ParseSupport.GetSource(
                                         imageObj,
                                         "edit",
                                         long.class
                                                       ));
            temp.setId((long)
                               ParseSupport.GetSource(
                                       imageObj,
                                       "id",
                                       long.class
                                                     ));
            temp.setComment((String)
                                    ParseSupport.GetSource(
                                            imageObj,
                                            "comment",
                                            String.class
                                                          ));
            temp.setImage((String)
                                  ParseSupport.GetSource(
                                          imageObj,
                                          "image",
                                          String.class
                                                        ));

            temp.setThumbnail(buildThumbnail(imageObj.get("thumbnail")));
            temp.setTypes(buildTypes(imageObj));

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return temp;
    }

    private List<String> buildTypes(JsonObject Obj)
    {
        List<String> temp = null;
        try
        {
            JsonArray TypesJList = (JsonArray)
                    ParseSupport.GetSource(
                            Obj,
                            "types",
                            JsonArray.class
                                          );


            if (TypesJList != null)
            {
                temp = new ArrayList<>();
                int size = TypesJList.size();

                for (int i = 0; i < size; i++)
                {
                    temp.add(TypesJList.get(i)
                                       .getAsString());
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return temp;
    }

    private thumbnail buildThumbnail(JsonElement element)
    {
        thumbnail temp = new thumbnail();

        try
        {
            JsonObject thumbnailObj = element.getAsJsonObject();

            temp.setLarge((String)
                                  ParseSupport.GetSource(
                                          thumbnailObj,
                                          "large",
                                          String.class
                                                        ));
            temp.setSmall((String)
                                  ParseSupport.GetSource(
                                          thumbnailObj,
                                          "small",
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
