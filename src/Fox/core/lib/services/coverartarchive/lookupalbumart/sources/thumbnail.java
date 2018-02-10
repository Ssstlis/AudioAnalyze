package Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources;

public class thumbnail
{
    private String large, small;

    public thumbnail()
    {
    }

    public thumbnail(
            String large,
            String small)
    {
        this.large = large;
        this.small = small;
    }

    public String getLarge()
    {
        return large;
    }

    public void setLarge(String large)
    {
        this.large = large;
    }

    public String getSmall()
    {
        return small;
    }

    public void setSmall(String small)
    {
        this.small = small;
    }

    public boolean hasLarge()
    {
        return (large != null && !large.isEmpty());
    }

    public boolean hasSmall()
    {
        return (small != null && !small.isEmpty());
    }
}
