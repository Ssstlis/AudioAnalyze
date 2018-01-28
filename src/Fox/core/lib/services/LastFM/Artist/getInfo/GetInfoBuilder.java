package Fox.core.lib.services.LastFM.Artist.getInfo;

import Fox.core.lib.services.Common.ParseSupport;
import Fox.core.lib.services.LastFM.Artist.getInfo.sources.*;
import Fox.core.lib.services.LastFM.CommonSources.CommonBuilder;
import Fox.core.lib.services.LastFM.CommonSources.Error;
import Fox.core.lib.services.LastFM.CommonSources.wiki;
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

    public ArtistInfo buildArtistInfo(String response)
    {
        if (response == null || response.isEmpty())
        {
            return null;
        }

        JsonParser parser = new JsonParser();
        ArtistInfo temp = null;

        try
        {
            JsonElement element = parser
                    .parse(response);

            JsonObject ArtistInfoObj = element
                    .getAsJsonObject();

            temp = new ArtistInfo();

            Error error = buildError(element);

            temp.setError(error.getError());
            temp.setMessage(error.getMessage());
            temp.setArtist(buildArtist2(ArtistInfoObj.get("artist")));

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return temp;
    }

    private artist buildArtist2(JsonElement element)
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
            temp.setStreamable((String)
                                       ParseSupport.GetSource(
                                               ArtistObj,
                                               "streamable",
                                               String.class
                                                             ));
            temp.setOntour((String)
                                   ParseSupport.GetSource(
                                           ArtistObj,
                                           "ontour",
                                           String.class
                                                         ));
            temp.setImages(buildImageList(ArtistObj));
            temp.setStats(buildStats(ArtistObj.get("stats")));
            temp.setSimilar(buildSimilar(ArtistObj.get("similar")));
            temp.setTags(buildToptags(ArtistObj.get("tags")));
            temp.setBio(buildBio(ArtistObj.get("bio")));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return temp;
    }

    private stats buildStats(JsonElement element)
    {
        stats temp = null;

        try
        {
            JsonObject statsObj = element.getAsJsonObject();
            temp = new stats();

            temp.setListeners((String)
                                      ParseSupport.GetSource(
                                              statsObj,
                                              "listeners",
                                              String.class
                                                            ));
            temp.setPlaycount((String)
                                      ParseSupport.GetSource(
                                              statsObj,
                                              "playcount",
                                              String.class
                                                            ));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return temp;
    }

    private similar buildSimilar(JsonElement element)
    {
        similar temp = null;

        try
        {
            JsonObject SimilarObj = element.getAsJsonObject();
            temp = new similar();

            temp.setArtists(buildArtistImageList(SimilarObj));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return temp;
    }

    private List<artistimages> buildArtistImageList(JsonObject similarObj)
    {

        List<artistimages> temp = null;

        try
        {
            JsonArray ArtistImageJList = (JsonArray)
                    ParseSupport.GetSource(
                            similarObj,
                            "artist",
                            JsonArray.class
                                          );
            if (ArtistImageJList != null)
            {
                temp = new ArrayList<>();
                int size = ArtistImageJList.size();

                for (int i = 0; i < size; i++)
                {
                    temp.add(buildArtistImage(ArtistImageJList.get(i)));
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return temp;
    }

    private artistimages buildArtistImage(JsonElement element)
    {
        artistimages temp = null;

        try
        {
            JsonObject ArtistImageObj = element.getAsJsonObject();
            temp = new artistimages();

            Fox.core.lib.services.LastFM.CommonSources.artist artist = buildArtist(element);

            temp.setName(artist.getName());
            temp.setUrl(artist.getUrl());

            temp.setImages(buildImageList(ArtistImageObj));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return temp;
    }

    private bio buildBio(JsonElement element)
    {
        bio temp = null;

        try
        {
            JsonObject BioObj = element.getAsJsonObject();
            temp = new bio();

            wiki wiki = buildWiki(element);

            temp.setContent(wiki.getContent());
            temp.setPublished(wiki.getPublished());
            temp.setSummary(wiki.getSummary());

            temp.setLinks(buildLinks(BioObj.get("links")));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return temp;
    }

    private link buildLink(JsonElement element)
    {
        link temp = null;

        try
        {
            JsonObject LinkObj = element.getAsJsonObject();
            temp = new link();

            temp.setHref((String)
                                 ParseSupport.GetSource(
                                         LinkObj,
                                         "href",
                                         String.class
                                                       ));
            temp.setRel((String)
                                ParseSupport.GetSource(
                                        LinkObj,
                                        "rel",
                                        String.class
                                                      ));
            temp.setText((String)
                                 ParseSupport.GetSource(
                                         LinkObj,
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

    private links buildLinks(JsonElement element)
    {
        links temp = null;

        try
        {
            JsonObject LinksObj = element.getAsJsonObject();
            temp = new links();

            temp.setLink(buildLink(LinksObj.get("link")));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return temp;
    }
}