package Fox.core.lib.services.AcoustID.LookupByFP.sources;

import java.util.ArrayList;
import java.util.List;

public class Release
{
    private Integer track_count;
    private Integer medium_count;
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

    public Release(
            Integer track_count,
            Integer medium_count,
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
            {
                temp.add(new Release(elem));
            }
        }

        return temp;
    }

    public Integer getTrack_count()
    {
        return track_count;
    }

    public void setTrack_count(Integer track_count)
    {
        this.track_count = track_count;
    }

    public boolean hasTrack_count()
    {
        return track_count != null && track_count > 0;
    }

    public Integer getMedium_count()
    {
        return medium_count;
    }

    public void setMedium_count(Integer medium_count)
    {
        this.medium_count = medium_count;
    }

    public boolean hasMedium_count()
    {
        return medium_count != null && medium_count > 0;
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
        return country != null && !country.isEmpty();
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
        return title != null && !title.isEmpty();
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
        return id != null && !id.isEmpty();
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
        return Artists != null && !Artists.isEmpty();
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
        return Mediums != null && !Mediums.isEmpty();
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