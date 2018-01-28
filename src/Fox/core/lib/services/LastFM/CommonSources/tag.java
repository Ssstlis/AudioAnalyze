package Fox.core.lib.services.LastFM.CommonSources;

import java.util.ArrayList;
import java.util.List;

public class tag
{
    private String name;
    private String url;

    public tag()
    {

    }

    public tag(
            String name,
            String url)
    {
        this.name = name;
        this.url = url;
    }

    public tag(tag copy)
    {
        if (copy != null)
        {
            this.url = copy.url;
            this.name = copy.name;
        }
    }

    public static List<tag> tagListCopy(List<tag> copy)
    {
        List<tag> temp = null;

        if (copy != null)
        {
            temp = new ArrayList<>();
            for (tag elem : copy)
            {
                temp.add(new tag(elem));
            }
        }

        return temp;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public boolean hasName()
    {
        return (name != null && !name.isEmpty());
    }

    public boolean hasUrl()
    {
        return (url != null && !url.isEmpty());
    }
}
