package Fox.core.lib.services.acoustid;

import org.jetbrains.annotations.NotNull;

public class AcoustIDResponse
{
    private String source;

    public AcoustIDResponse()
    {

    }

    public AcoustIDResponse(@NotNull String src)
    {
        this.source = src;
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
