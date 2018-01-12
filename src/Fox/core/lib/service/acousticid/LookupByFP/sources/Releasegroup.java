package Fox.core.lib.service.acousticid.LookupByFP.sources;


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

    public Releasegroup(String type,
                        String id,
                        String title,
                        List<String> secondarytypes,
                        List<Artist> Artists,
                        List<Release> Releases) {
        this.id = id;
        this.secondarytypes = secondarytypes;
        this.title = title;
        this.type = type;
        this.Artists = Artists;
        this.Releases = Releases;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getSecondarytypes() {
        return this.secondarytypes;
    }

    public void setSecondarytypes(List<String> secondarytypes) {
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

    public List<Artist> getArtists() {
        return Artists;
    }

    public void setArtists(List<Artist> artists) {
        Artists = artists;
    }

    public List<Release> getReleases() {
        return Releases;
    }

    public void setReleases(List<Release> releases) {
        Releases = releases;
    }

    public boolean hasArtists() {
        return Artists != null && Artists.size() > 0;
    }

    public boolean hasReleases() {
        return Releases != null && Releases.size() > 0;
    }
}
