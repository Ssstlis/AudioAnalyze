package Fox.core.lib.general.DOM;

import java.util.ArrayList;
import java.util.List;

public class Art
{
    private String url;
    private String size;

    public Art()
    {

    }

    public Art(String url,
               String size)
    {
        this.url = url;
        this.size = size;
    }

    public Art(Art copy)
    {
        if (copy!=null)
        {
            this.size = copy.size;
            this.url = copy.url;
        }
    }

    public static List<Art> ArtListCopy(List<Art> copy)
    {
        List<Art> temp = null;

        if (copy!=null)
        {
            temp = new ArrayList<>();
            int size = copy.size();

            for(Art elem:copy)
                temp.add(new Art(elem));
        }

        return temp;
    }

    public String getSize()
    {
        return size;
    }

    public void setSize(String size)
    {
        this.size = size;
    }

    public boolean hasSize()
    {
        return (size!=null && !size.isEmpty());
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public boolean hasUrl()
    {
        return (url!=null && !url.isEmpty());
    }
}
