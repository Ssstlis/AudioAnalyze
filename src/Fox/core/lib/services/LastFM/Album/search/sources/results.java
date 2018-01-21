package Fox.core.lib.services.LastFM.Album.search.sources;

import Fox.core.lib.services.LastFM.CommonSources.attr;

public class results
{
    private opensearchQuery opensearchQuery;
    private String openSearchTotalResults;
    private String openSearchStartIndex;
    private String openSearchItemsPerPage;
    private albummatches albummatches;
    private attr attr;

    public results()
    {

    }

    public results(opensearchQuery Query,
                   String TotalResults,
                   String StartIndex,
                   String ItemsPerPage,
                   albummatches albummatches,
                   attr attr)
    {
        this.albummatches = new albummatches(albummatches);
        this.attr = new attr(attr);
        this.openSearchItemsPerPage = ItemsPerPage;
        this.opensearchQuery = new opensearchQuery(Query);
        this.openSearchStartIndex = StartIndex;
        this.openSearchTotalResults = TotalResults;
    }

    public results(results copy)
    {
        if (copy!=null)
        {
            this.albummatches = new albummatches(copy.albummatches);
            this.attr = new attr(copy.attr);
            this.openSearchItemsPerPage = copy.openSearchItemsPerPage;
            this.opensearchQuery = new opensearchQuery(copy.opensearchQuery);
            this.openSearchStartIndex = copy.openSearchStartIndex;
            this.openSearchTotalResults = copy.openSearchTotalResults;
        }
    }

    public opensearchQuery getQuery()
    {
        return new opensearchQuery(opensearchQuery);
    }

    public void setQuery(opensearchQuery opensearchQuery)
    {
        this.opensearchQuery = new opensearchQuery(opensearchQuery);
    }

    public String getTotalResults()
    {
        return openSearchTotalResults;
    }

    public void setTotalResults(String TotalResults)
    {
        this.openSearchTotalResults = TotalResults;
    }

    public String getStartIndex()
    {
        return openSearchStartIndex;
    }

    public void setStartIndex(String StartIndex)
    {
        this.openSearchStartIndex = StartIndex;
    }

    public String getItemsPerPage()
    {
        return openSearchItemsPerPage;
    }

    public void setItemsPerPage(String ItemsPerPage)
    {
        this.openSearchItemsPerPage = ItemsPerPage;
    }

    public albummatches getAlbummatches()
    {
        return new albummatches(albummatches);
    }

    public void setAlbummatches(albummatches albummatches)
    {
        this.albummatches = new albummatches(albummatches);
    }

    public attr getAttr()
    {
        return new attr(attr);
    }

    public void setAttr(attr attr)
    {
        this.attr = new attr(attr);
    }
}
