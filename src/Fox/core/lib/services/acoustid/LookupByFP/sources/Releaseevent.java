package Fox.core.lib.services.acoustid.LookupByFP.sources;


import java.util.ArrayList;
import java.util.List;

public class Releaseevent
{
    private String country;
    private Date date;

    public Releaseevent()
    {
    }

    public Releaseevent(
            String country,
            Date date)
    {
        this.country = country;
        this.date = date;
    }

    public Releaseevent(Releaseevent copy)
    {
        if (copy != null)
        {
            this.date = copy.date;
            this.country = copy.country;
        }
    }

    public static List<Releaseevent> ReleaseeventListCopy(List<Releaseevent> copy)
    {
        List<Releaseevent> temp = null;

        if (copy != null)
        {
            temp = new ArrayList<>();
            for (Releaseevent elem : copy)
            {
                temp.add(new Releaseevent(elem));
            }
        }
        return temp;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public boolean hasCountry()
    {
        return country != null && country.length() > 0;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public boolean hasDate()
    {
        return date != null;
    }
}
