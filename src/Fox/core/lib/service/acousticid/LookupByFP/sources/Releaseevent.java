package Fox.core.lib.service.acousticid.LookupByFP.sources;


public class Releaseevent
{
    private String country;
    private Date date;

    public Releaseevent(){}

    public Releaseevent(String country,
                        Date date)
    {
        this.country = country;
        this.date = date;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean hasCountry()
    {
        return country!=null && country.length()>0;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean hasDate()
    {
        return date!=null;
    }
}
