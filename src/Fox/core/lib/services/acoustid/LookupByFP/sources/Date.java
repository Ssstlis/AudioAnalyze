package Fox.core.lib.services.acoustid.LookupByFP.sources;

import java.util.ArrayList;
import java.util.List;

public class Date
{
    private int day;
    private int month;
    private int year;

    public Date()
    {
    }

    public Date(
            int day,
            int month,
            int year)
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

    public int getDay()
    {
        return day;
    }

    public void setDay(int day)
    {
        this.day = day;
    }

    public boolean hasDay()
    {
        return day != 0;
    }

    public int getMonth()
    {
        return month;
    }

    public void setMonth(int month)
    {
        this.month = month;
    }

    public boolean hasMonth()
    {
        return month != 0;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public boolean hasYear()
    {
        return year != 0;
    }
}
