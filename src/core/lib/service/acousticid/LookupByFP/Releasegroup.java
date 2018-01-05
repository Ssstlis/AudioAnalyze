package core.lib.service.acousticid.LookupByFP;

import java.util.List;

public class Releasegroup
{
    private String type;
    private String id;
    private String  title;
    private List<String> secondarytypes;

    public Releasegroup(){}
    public String getType()
    {
        return this.type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getId()
    {
        return this.id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public List<String> getSecondarytypes()
    {
        return this.secondarytypes;
    }

    public void setSecondarytypes(List<String> secondarytypes)
    {
        this.secondarytypes = secondarytypes;
    }
}
