package Fox.core.lib.service.acousticid.LookupByFP.sources;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Result {
    private float score;
    private String id;
    private List<Recording> Recordings;

    public Result() {
    }

    public Result(float score,
                  @NotNull String id,
                  @NotNull List<Recording> Recordings) {
        this.score = score;
        this.id = id;
        this.Recordings = Recordings;
    }

    @NotNull
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

    @NotNull
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
}
