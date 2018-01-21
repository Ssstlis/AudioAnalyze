package Fox.core.lib.services.LastFM.Album.search.sources;

import Fox.core.lib.services.LastFM.CommonSources.Error;

public class Search extends Error
{
    private results results;

    public Search()
    {
        super();
    }

    public Search(results results,
                  Integer error,
                  String message)
    {
        super(error, message);
        this.results = new results(results);
    }

    public results getResults()
    {
        return new results(results);
    }

    public void setResults(results results)
    {
        this.results = new results(results);
    }

    public boolean hasResults()
    {
        return results!=null;
    }
}
