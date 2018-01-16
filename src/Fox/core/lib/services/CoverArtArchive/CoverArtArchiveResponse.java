package Fox.core.lib.services.CoverArtArchive;

public class CoverArtArchiveResponse
{
    private String source;

    public CoverArtArchiveResponse()
    {

    }

    public CoverArtArchiveResponse(String source)
    {
        this.source = source;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public boolean hasSource()
    {
        return (source != null && !source.isEmpty());
    }
}
