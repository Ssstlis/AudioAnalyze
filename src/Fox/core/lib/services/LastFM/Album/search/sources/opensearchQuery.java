package Fox.core.lib.services.LastFM.Album.search.sources;

public class opensearchQuery
{
    private String text;
    private String role;
    private String searchTerms;
    private String StartPage;

    public opensearchQuery()
    {

    }

    public opensearchQuery(String text,
                           String role,
                           String searchTerms,
                           String startPage)
    {
        this.role = role;
        this.searchTerms = searchTerms;
        this.StartPage = startPage;
        this.text = text;
    }

    public opensearchQuery(opensearchQuery copy)
    {
        if (copy!=null)
        {
            this.role = copy.role;
            this.searchTerms = copy.searchTerms;
            this.StartPage = copy.StartPage;
            this.text = copy.text;
        }
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public boolean hasText()
    {
        return (text!=null && !text.isEmpty());
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }


    public boolean hasRole()
    {
        return (role!=null && !role.isEmpty());
    }

    public String getSearchTerms()
    {
        return searchTerms;
    }

    public void setSearchTerms(String searchTerms)
    {
        this.searchTerms = searchTerms;
    }


    public boolean hasSearchTerms()
    {
        return (searchTerms!=null && !searchTerms.isEmpty());
    }

    public String getStartPage()
    {
        return StartPage;
    }

    public void setStartPage(String startPage)
    {
        StartPage = startPage;
    }

    public boolean hasStartPage()
    {
        return (StartPage!=null && !StartPage.isEmpty());
    }
}
