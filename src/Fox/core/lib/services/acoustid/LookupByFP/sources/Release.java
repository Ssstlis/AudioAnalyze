package Fox.core.lib.services.acoustid.LookupByFP.sources;

import java.util.ArrayList;
import java.util.List;

public class Release
{
    private int track_count;
    private int medium_count;
    private String country;
    private String title;
    private String id;
    private List<Artist> Artists;
    private List<Releaseevent> Releaseevents;
    private List<Medium> Mediums;
    private Date date;

    public Release()
    {
    }

    public Release(int track_count,
                   int medium_count,
                   String country,
                   String title,
                   String id,
                   List<Artist> Artists,
                   List<Releaseevent> Releaseevents,
                   List<Medium> Mediums,
                   Date date)
    {
        this.Artists = Artist.ArtistListCopy(Artists);
        this.country = country;
        this.date = date;
        this.id = id;
        this.medium_count = medium_count;
        this.Releaseevents = Releaseevent.ReleaseeventListCopy(Releaseevents);
        this.title = title;
        this.Mediums = Medium.MediumListCopy(Mediums);
        this.track_count = track_count;
    }

    public Release(Release copy)
    {
        if (copy != null)
        {
            this.country = copy.country;
            this.date = copy.date;
            this.id = copy.id;
            this.medium_count = copy.medium_count;
            this.title = copy.title;
            this.Mediums = Medium.MediumListCopy(copy.Mediums);
            this.track_count = copy.track_count;
            this.Releaseevents = Releaseevent.ReleaseeventListCopy(copy.Releaseevents);
            this.Artists = Artist.ArtistListCopy(copy.Artists);
        }
    }

    public static List<Release> ReleaseListCopy(List<Release> copy)
    {
        List<Release> temp = null;

        if (copy != null)
        {
            temp = new ArrayList<>();
            for (Release elem : copy)
                temp.add(new Release(elem));
        }

        return temp;
    }

    public int getTrack_count()
    {
        return track_count;
    }

    public void setTrack_count(int track_count)
    {
        this.track_count = track_count;
    }

    public boolean hasTrack_count()
    {
        return track_count > 0;
    }

    public int getMedium_count()
    {
        return medium_count;
    }

    public void setMedium_count(int medium_count)
    {
        this.medium_count = medium_count;
    }

    public boolean hasMedium_count()
    {
        return medium_count > 0;
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

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public boolean hasTitle()
    {
        return title != null && title.length() > 0;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public boolean hasId()
    {
        return id != null && id.length() > 0;
    }

    public List<Artist> getArtists()
    {
        return Artist.ArtistListCopy(Artists);
    }

    public void setArtists(List<Artist> artists)
    {
        Artists = Artist.ArtistListCopy(artists);
    }

    public boolean hasArtists()
    {
        return Artists != null && Artists.size() > 0;
    }

    public List<Releaseevent> getReleaseevents()
    {
        return Releaseevent.ReleaseeventListCopy(Releaseevents);
    }

    public void setReleaseevents(List<Releaseevent> releaseevents)
    {
        Releaseevents = Releaseevent.ReleaseeventListCopy(releaseevents);
    }

    public boolean hasReleaseevents()
    {
        return Releaseevents != null && Releaseevents.size() > 0;
    }

    public List<Medium> getMediums()
    {
        return Medium.MediumListCopy(Mediums);
    }

    public void setMediums(List<Medium> mediums)
    {
        Mediums = Medium.MediumListCopy(mediums);
    }

    public boolean hasMediums()
    {
        return Mediums != null && Mediums.size() > 0;
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