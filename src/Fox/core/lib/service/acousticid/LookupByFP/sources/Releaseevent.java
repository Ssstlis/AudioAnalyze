package Fox.core.lib.service.acousticid.LookupByFP.sources;

import org.jetbrains.annotations.NotNull;

public class Releaseevent
{
    private String country;
    private Date date;

    public Releaseevent(){}

    public Releaseevent(@NotNull String country,
                        @NotNull Date date)
    {
        this.country = country;
        this.date = date;
    }

    @NotNull
    public String getCountry() {
        return country;
    }

    public void setCountry(@NotNull String country) {
        this.country = country;
    }

    public boolean hasCountry()
    {
        return country!=null && country.length()>0;
    }

    @NotNull
    public Date getDate() {
        return date;
    }

    public void setDate(@NotNull Date date) {
        this.date = date;
    }

    public boolean hasDate()
    {
        return date!=null;
    }
}
