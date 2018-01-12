package Fox.core.lib.service.acousticid.LookupByFP.sources;


import java.util.List;

public class Result {
    private float score;
    private String id;
    private List<Recording> Recordings;
    private List<Releasegroup> Releasegroups;
    private List<Release> Releases;

    public Result() {
    }

    public Result(float score,
                  String id,
                  List<Recording> Recordings,
                  List<Releasegroup> Releasegroups,
                  List<Release> Releases)
    {
        this.score = score;
        this.id = id;
        this.Recordings = Recordings;
        this.Releasegroups = Releasegroups;
        this.Releases = Releases;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getScore() {
        return this.score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public List<Recording> getRecordings() {
        return this.Recordings;
    }

    public void setRecordings(List<Recording> Recording) {
        this.Recordings = Recording;
    }

    public boolean hasId() {
        return id != null && id.length()>0;
    }

    public boolean hasScore() {
        return score != 0.0;
    }

    public boolean hasRecordings() {
        return Recordings != null && Recordings.size() > 0;
    }

    public List<Releasegroup> getReleasegroups() {
        return Releasegroups;
    }

    public void setReleasegroups(List<Releasegroup> releasegroups) {
        Releasegroups = releasegroups;
    }

    public boolean hasReleasegroups()
    {
        return Releasegroups!=null && Releasegroups.size()>0;
    }

    public List<Release> getReleases() {
        return Releases;
    }

    public void setReleases(List<Release> releases) {
        Releases = releases;
    }

    public boolean hasReleases()
    {
        return Releases!=null && Releases.size()>0;
    }
}
