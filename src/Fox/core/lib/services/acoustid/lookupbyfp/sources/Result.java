package Fox.core.lib.services.AcoustID.LookupByFP.sources;


import java.util.ArrayList;
import java.util.List;

public class Result
{
    private float score;
    private String id;
    private List<Recording> Recordings;
    private List<Releasegroup> Releasegroups;
    private List<Release> Releases;

    public Result()
    {
    }

    public Result(
            float score,
            String id,
            List<Recording> Recordings,
            List<Releasegroup> Releasegroups,
            List<Release> Releases)
    {
        this.score = score;
        this.id = id;
        this.Recordings = Recording.RecordingListCopy(Recordings);
        this.Releasegroups = Releasegroup.ReleasegroupListCopy(Releasegroups);
        this.Releases = Release.ReleaseListCopy(Releases);
    }

    public Result(Result copy)
    {
        if (copy != null)
        {
            this.id = copy.id;
            this.score = copy.score;
            this.Recordings = Recording.RecordingListCopy(copy.Recordings);
            this.Releasegroups = Releasegroup.ReleasegroupListCopy(copy.Releasegroups);
            this.Releases = Release.ReleaseListCopy(copy.Releases);
        }
    }

    public static List<Result> ResultListCopy(List<Result> copy)
    {
        List<Result> temp = null;

        if (copy != null)
        {
            temp = new ArrayList<>();

            for (Result elem : copy)
            {
                temp.add(new Result(elem));
            }
        }

        return temp;
    }

    public String getId()
    {
        return this.id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public float getScore()
    {
        return this.score;
    }

    public void setScore(float score)
    {
        this.score = score;
    }

    public List<Recording> getRecordings()
    {
        return Recording.RecordingListCopy(this.Recordings);
    }

    public void setRecordings(List<Recording> recording)
    {
        this.Recordings = Recording.RecordingListCopy(recording);
    }

    public boolean hasId()
    {
        return id != null && id.length() > 0;
    }

    public boolean hasScore()
    {
        return score != 0.0;
    }

    public boolean hasRecordings()
    {
        return Recordings != null && Recordings.size() > 0;
    }

    public List<Releasegroup> getReleasegroups()
    {
        return Releasegroup.ReleasegroupListCopy(this.Releasegroups);
    }

    public void setReleasegroups(List<Releasegroup> releasegroups)
    {
        Releasegroups = Releasegroup.ReleasegroupListCopy(releasegroups);
    }

    public boolean hasReleasegroups()
    {
        return Releasegroups != null && Releasegroups.size() > 0;
    }

    public List<Release> getReleases()
    {
        return Release.ReleaseListCopy(Releases);
    }

    public void setReleases(List<Release> releases)
    {
        Releases = Release.ReleaseListCopy(releases);
    }

    public boolean hasReleases()
    {
        return Releases != null && Releases.size() > 0;
    }
}
