package Fox.core.lib.services.LastFM.Album.search;

import Fox.core.lib.services.Common.ParseSupport;
import Fox.core.lib.services.LastFM.Album.search.sources.*;
import Fox.core.lib.services.LastFM.CommonSources.CommonBuilder;
import Fox.core.lib.services.LastFM.CommonSources.Error;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class SearchBuilder
        extends CommonBuilder
{
    public SearchBuilder()
    {

    }

    public Search buildSearch(String source)
    {
        if (source == null || source.isEmpty())
        {
            return null;
        }

        JsonParser parser = new JsonParser();
        Search temp = null;

        try
        {
            JsonElement element = parser
                    .parse(source);

            JsonObject SearchObj = element
                    .getAsJsonObject();
            temp = new Search();

            Error error = buildError(element);

            temp.setError(error.getError());
            temp.setMessage(error.getMessage());

            temp.setResults(buildResults(SearchObj.get("results")));

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return temp;
    }

    private results buildResults(JsonElement element)
    {
        results temp = null;

        try
        {
            JsonObject ResultsObj = element.getAsJsonObject();

            temp = new results();

            temp.setAttr(buildAttribute(ResultsObj.get("@attr")));
            temp.setQuery(buildQuery(ResultsObj.get("opensearch:Query")));
            temp.setItemsPerPage((String)
                                         ParseSupport.GetSource(
                                                 ResultsObj,
                                                 "opensearch:itemsPerPage",
                                                 String.class
                                                               ));
            temp.setStartIndex((String)
                                       ParseSupport.GetSource(
                                               ResultsObj,
                                               "opensearch:startIndex",
                                               String.class
                                                             ));
            temp.setTotalResults((String)
                                         ParseSupport.GetSource(
                                                 ResultsObj,
                                                 "opensearch:totalResults",
                                                 String.class
                                                               ));
            temp.setAlbummatches(buildAlbummatches(ResultsObj.get("albummatches")));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return temp;
    }

    private albummatches buildAlbummatches(JsonElement element)
    {
        albummatches temp = null;

        try
        {
            JsonObject AlbummatchesObj = element.getAsJsonObject();
            temp = new albummatches();

            temp.setAlbumList(buildAlbumList(AlbummatchesObj));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return temp;
    }

    private List<album> buildAlbumList(JsonObject albummatchesObj)
    {
        List<album> temp = null;

        try
        {
            JsonArray AlbumJList = (JsonArray)
                    ParseSupport.GetSource(
                            albummatchesObj,
                            "album",
                            JsonArray.class
                                          );
            if (AlbumJList != null)
            {
                temp = new ArrayList<>();
                int size = AlbumJList.size();

                for (int i = 0; i < size; i++)
                {
                    temp.add(buildAlbum(AlbumJList.get(i)));
                }
            }
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
            temp.setMbid((String)
                                 ParseSupport.GetSource(
                                         AlbumObj,
                                         "mbid",
                                         String.class
                                                       ));
            temp.setName((String)
                                 ParseSupport.GetSource(
                                         AlbumObj,
                                         "name",
                                         String.class
                                                       ));
            temp.setStreamable((String)
                                       ParseSupport.GetSource(
                                               AlbumObj,
                                               "streamable",
                                               String.class
                                                             ));
            temp.setUrl((String)
                                ParseSupport.GetSource(
                                        AlbumObj,
                                        "url",
                                        String.class
                                                      ));
            temp.setImages(buildImageList(AlbumObj));

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return temp;
    }

    private opensearchQuery buildQuery(JsonElement element)
    {
        opensearchQuery temp = null;

        try
        {
            JsonObject QueryObj = element.getAsJsonObject();
            temp = new opensearchQuery();
            temp.setRole((String)
                                 ParseSupport.GetSource(
                                         QueryObj,
                                         "role",
                                         String.class
                                                       ));
            temp.setText((String)
                                 ParseSupport.GetSource(
                                         QueryObj,
                                         "#text",
                                         String.class
                                                       ));
            temp.setSearchTerms((String)
                                        ParseSupport.GetSource(
                                                QueryObj,
                                                "searchTerms",
                                                String.class
                                                              ));
            temp.setStartPage((String)
                                      ParseSupport.GetSource(
                                              QueryObj,
                                              "startPage",
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
