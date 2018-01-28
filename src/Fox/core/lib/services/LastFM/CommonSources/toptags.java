package Fox.core.lib.services.LastFM.CommonSources;

import java.util.List;

public class toptags
{
    private List<tag> tags;

    public toptags()
    {

    }

    public toptags(List<tag> tags)
    {
        this.tags = tag.tagListCopy(tags);
    }

    public toptags(toptags copy)
    {
        if (copy != null)
        {
            if (copy.hasTags())
            {
                this.tags = tag.tagListCopy(copy.tags);
            }
        }
    }

    public List<tag> getTags()
    {
        return tag.tagListCopy(tags);
    }

    public void setTags(List<tag> tags)
    {
        this.tags = tag.tagListCopy(tags);
    }

    public boolean hasTags()
    {
        return (tags != null && !tags.isEmpty());
    }
}
