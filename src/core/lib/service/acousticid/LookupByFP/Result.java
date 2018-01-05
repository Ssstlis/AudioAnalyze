package core.lib.service.acousticid.LookupByFP;

import java.util.List;

public class Result
{
    private float score;
    private String id;
    private List<Recording> Recording;

    public Result(){}

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

    public List<Recording> getRecording()
    {
        return this.Recording;
    }

    public void setRecording(List<Recording> Recording)
    {
        this.Recording = Recording;
    }
}
