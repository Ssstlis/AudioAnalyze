package Fox.core.lib.services.acoustid.LookupByFP.sources;

import java.util.ArrayList;
import java.util.List;

public class Releasegroup
{
    private String type;
    private String id;
    private String title;
    private List<String> secondarytypes;
    private List<Artist> Artists;
    private List<Release> Releases;

    public Releasegroup()
    {
    }

    public Releasegroup(String type,
                        String id,
                        String title,
                        List<String> secondarytypes,
                        List<Artist> Artists,
                        List<Release> Releases)
    {
        this.id = id;
        this.title = title;
        this.type = type;
        this.Releases = Release.ReleaseListCopy(Releases);
        this.Artists = Artist.ArtistListCopy(Artists);
        if (secondarytypes != null)
        {
            this.secondarytypes = new ArrayList<>();
            this.secondarytypes.addAll(secondarytypes);
        }
    }

    public Releasegroup(Releasegroup copy)
    {
        if (copy != null)
        {
            this.id = copy.id;
            this.Releases = Release.ReleaseListCopy(copy.Releases);
            this.Artists = Artist.ArtistListCopy(copy.Artists);
            this.title = copy.title;
            this.type = copy.type;
            if (copy.secondarytypes != null)
            {
                this.secondarytypes = new ArrayList<>();
                secondarytypes.addAll(copy.secondarytypes);
            }
        }
    }

    public static List<Releasegroup> ReleasegroupListCopy(List<Releasegroup> copy)
    {
        List<Releasegroup> temp = null;

        if (copy != null)
        {
            temp = new ArrayList<>();

            for (Releasegroup elem : copy)
                temp.add(new Releasegroup(elem));
        }

        return temp;
    }

    public String getType()
    {
        return this.type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getId()
    {
        return this.id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public List<String> getSecondarytypes()
    {
        List<String> temp = null;
        if (this.secondarytypes != null)
        {
            temp = new ArrayList<>();
            temp.addAll(this.secondarytypes);
        }
        return temp;
    }

    public void setSecondarytypes(List<String> secondarytypes)
    {
        List<String> temp = null;
        if (secondarytypes != null)
        {
            temp = new ArrayList<>();
            temp.addAll(secondarytypes);
        }
        this.secondarytypes = temp;
    }

    public boolean hasId()
    {
        return id != null && id.length() > 0;
    }

    public boolean hasType()
    {
        return type != null;
    }

    public boolean hasTitle()
    {
        return title != null;
    }

    public boolean hasSecondarytypes()
    {
        return secondarytypes != null && secondarytypes.size() > 0;
    }

    public List<Artist> getArtists()
    {
        return Artist.ArtistListCopy(this.Artists);
    }

    public void setArtists(List<Artist> artists)
    {
        Artists = Artist.ArtistListCopy(artists);
    }

    public List<Release> getReleases()
    {
        return Release.ReleaseListCopy(this.Releases);
    }

    public void setReleases(List<Release> releases)
    {
        Releases = Release.ReleaseListCopy(releases);
    }

    public boolean hasArtists()
    {
        return Artists != null && Artists.size() > 0;
    }

    public boolean hasReleases()
    {
        return Releases != null && Releases.size() > 0;
    }
}