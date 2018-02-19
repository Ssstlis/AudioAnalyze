package Fox.core.lib.services.LastFM.CommonSources;

public class attr
{
    private String position;
    private String rank;
    private String For;

    public attr()
    {

    }

    public attr(
            String position,
            String rank,
            String For)
    {
        this.position = position;
        this.rank = rank;
        this.For = For;
    }

    public attr(attr copy)
    {
        if (copy != null)
        {
            this.position = copy.position;
            this.rank = copy.rank;
            this.For = copy.For;
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
        return (position != null && !position.isEmpty());
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
        return (rank != null && !rank.isEmpty());
    }

    public String getFor()
    {
        return For;
    }

    public void setFor(String aFor)
    {
        For = aFor;
    }

    public boolean hasFor()
    {
        return (For != null && !For.isEmpty());
    }
}
