package Fox.core.lib.services.AcoustID.LookupByFP.sources;

import java.util.ArrayList;
import java.util.List;

public class Date
{
    private Integer day;
    private Integer month;
    private Integer year;

    public Date()
    {
    }

    public Date(
            Integer day,
            Integer month,
            Integer year)
    {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Date(Date copy)
    {
        if (copy != null)
        {
            this.year = copy.year;
            this.month = copy.month;
            this.day = copy.day;
        }
    }

    public static List<Date> DateListCopy(List<Date> copy)
    {
        List<Date> temp = null;

        if (copy != null)
        {
            temp = new ArrayList<>();

            for (Date elem : copy)
            {
                temp.add(new Date(elem));
            }
        }

        return temp;
    }

    public Integer getDay()
    {
        return day;
    }

    public void setDay(Integer day)
    {
        this.day = day;
    }

    public boolean hasDay()
    {
        return day != null && day != 0;
    }

    public Integer getMonth()
    {
        return month;
    }

    public void setMonth(Integer month)
    {
        this.month = month;
    }

    public boolean hasMonth()
    {
        return month != null && month != 0;
    }

    public Integer getYear()
    {
        return year;
    }

    public void setYear(Integer year)
    {
        this.year = year;
    }

    public boolean hasYear()
    {
        return year != null && year != 0;
    }
}
