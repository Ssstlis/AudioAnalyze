package Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources;

import java.util.List;

public class AlbumArt
{
    private String release;
    private List<image> images;

    public AlbumArt()
    {

    }

    public AlbumArt(String release,
                    List<image> images)
    {
        this.images = images;
        this.release = release;
    }

    public String getRelease()
    {
        return release;
    }

    public void setRelease(String release)
    {
        this.release = release;
    }

    public boolean hasRelease()
    {
        return (release != null && !release.isEmpty());
    }

    public List<image> getImages()
    {
        return images;
    }

    public void setImages(List<image> image)
    {
        this.images = image;
    }

    public boolean hasImages()
    {
        return (images != null && !images.isEmpty());
    }
}
