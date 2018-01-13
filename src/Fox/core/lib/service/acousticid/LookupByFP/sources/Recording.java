package Fox.core.lib.service.acousticid.LookupByFP.sources;

import java.util.ArrayList;
import java.util.List;

public class Recording {
    private int duration;
    private int sources;
    private String title;
    private String id;
    private List<Artist> Artists;
    private List<Releasegroup> Releasegroups;

    public Recording() {
    }

    public Recording(int duration,
                     int sources,
                     String title,
                     String id,
                     List<Artist> Artists,
                     List<Releasegroup> Releasegroups)
    {
        this.Releasegroups = Releasegroup.ReleasegroupListCopy(Releasegroups);
        this.Artists = Artist.ArtistListCopy(Artists);
        this.duration = duration;
        this.sources = sources;
        this.id = id;
        this.title = title;
    }

    public Recording(Recording copy)
    {
        if (copy!=null)
        {
            this.sources = copy.sources;
            this.title = copy.title;
            this.duration = copy.duration;
            this.id = copy.id;
            this.Releasegroups = Releasegroup.ReleasegroupListCopy(copy.Releasegroups);
            this.Artists = Artist.ArtistListCopy(copy.Artists);
        }
    }

    public static List<Recording> RecordingListCopy(List<Recording> copy)
    {
        List<Recording> temp = null;

        if (copy!=null)
        {
            temp = new ArrayList<>();
            for(Recording elem:copy)
                temp.add(new Recording(elem));
        }

        return temp;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Artist> getArtists() {
        return Artist.ArtistListCopy(this.Artists);
    }

    public void setArtists(List<Artist> artist) {
        this.Artists = Artist.ArtistListCopy(artist);
    }

    public boolean hasDuration() {
        return duration != 0;
    }

    public boolean hasTitle() {
        return title != null && title.length()>0;
    }

    public boolean hasId() {
        return id != null && id.length()>0;
    }

    public boolean hasArtists() {
        return Artists != null && Artists.size() > 0;
    }

    public List<Releasegroup> getReleasegroups() {
        return Releasegroup.ReleasegroupListCopy(this.Releasegroups);
    }

    public void setReleasegroups(List<Releasegroup> releasegroups) {
        Releasegroups = Releasegroup.ReleasegroupListCopy(releasegroups);
    }

    public boolean hasReleasegroups() {
        return Releasegroups != null && Releasegroups.size() > 0;
    }

    public int getSources() {
        return sources;
    }

    public void setSources(int sources) {
        this.sources = sources;
    }

    public boolean hasSources()
    {
        return sources!=0;
    }
}
