package Fox.core.lib.services.LastFM.CommonSources;

public class attr
{
    private String position;
    private String rank;

    public attr()
    {

    }

    public attr(String position,
                String rank)
    {
        this.position = position;
        this.rank = rank;
    }

    public attr(attr copy)
    {
        if (copy!=null)
        {
            this.position = copy.position;
            this.rank = copy.rank;
        }
    }

    public String getPosition()
    {
        return position;
    }

    public void setPosition(String position)
    {
        this.position = position;
    }

    public boolean hasPosition()
    {
        return (position!=null && !position.isEmpty());
    }

    public String getRank()
    {
        return rank;
    }

    public void setRank(String rank)
    {
        this.rank = rank;
    }

    public boolean hasRank()
    {
        return (rank!=null && !rank.isEmpty());
    }
}
