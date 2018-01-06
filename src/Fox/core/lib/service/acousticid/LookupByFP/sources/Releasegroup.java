package Fox.core.lib.service.acousticid.LookupByFP.sources;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Releasegroup {
    private String type;
    private String id;
    private String title;
    private List<String> secondarytypes;
    private List<Artist> Artists;
    private List<Release> Releases;

    public Releasegroup() {
    }

    public Releasegroup(@NotNull String type,
                        @NotNull String id,
                        @NotNull String title,
                        @NotNull List<String> secondarytypes,
                        @NotNull List<Artist> Artists,
                        @NotNull List<Release> Releases) {
        this.id = id;
        this.secondarytypes = secondarytypes;
        this.title = title;
        this.type = type;
        this.Artists = Artists;
        this.Releases = Releases;
    }

    @NotNull
    public String getType() {
        return this.type;
    }

    public void setType(@NotNull String type) {
        this.type = type;
    }

    @NotNull
    public String getTitle() {
        return this.title;
    }

    public void setTitle(@NotNull String title) {
        this.title = title;
    }

    @NotNull
    public String getId() {
        return this.id;
    }

    public void setId(@NotNull String id) {
        this.id = id;
    }

    @NotNull
    public List<String> getSecondarytypes() {
        return this.secondarytypes;
    }

    public void setSecondarytypes(@NotNull List<String> secondarytypes) {
        this.secondarytypes = secondarytypes;
    }

    public boolean hasId() {
        return id != null && id.length()>0;
    }

    public boolean hasType() {
        return type != null;
    }

    public boolean hasTitle() {
        return title != null;
    }

    public boolean hasSecondytypes() {
        return secondarytypes != null && secondarytypes.size() > 0;
    }

    @NotNull
    public List<Artist> getArtists() {
        return Artists;
    }

    public void setArtists(@NotNull List<Artist> artists) {
        Artists = artists;
    }

    @NotNull
    public List<Release> getReleases() {
        return Releases;
    }

    public void setReleases(@NotNull List<Release> releases) {
        Releases = releases;
    }

    public boolean hasArtists() {
        return Artists != null && Artists.size() > 0;
    }

    public boolean hasReleases() {
        return Releases != null && Releases.size() > 0;
    }
}
