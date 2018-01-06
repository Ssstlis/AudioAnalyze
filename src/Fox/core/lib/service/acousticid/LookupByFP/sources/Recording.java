package Fox.core.lib.service.acousticid.LookupByFP.sources;

import org.jetbrains.annotations.NotNull;

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
                     @NotNull String title,
                     @NotNull String id,
                     @NotNull List<Artist> Artists)
    {
        this.Artists = Artists;
        this.duration = duration;
        this.sources = sources;
        this.id = id;
        this.title = title;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @NotNull
    public String getId() {
        return this.id;
    }

    public void setId(@NotNull String id) {
        this.id = id;
    }

    @NotNull
    public String getTitle() {
        return this.title;
    }

    public void setTitle(@NotNull String title) {
        this.title = title;
    }

    @NotNull
    public List<Artist> getArtists() {
        return this.Artists;
    }

    public void setArtists(@NotNull List<Artist> Artist) {
        this.Artists = Artist;
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

    @NotNull
    public List<Releasegroup> getReleasegroups() {
        return Releasegroups;
    }

    public void setReleasegroups(@NotNull List<Releasegroup> releasegroups) {
        Releasegroups = releasegroups;
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
