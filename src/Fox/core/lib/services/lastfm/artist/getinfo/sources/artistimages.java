package Fox.core.lib.services.LastFM.Artist.getInfo.sources;

import Fox.core.lib.services.LastFM.CommonSources.image;

import java.util.ArrayList;
import java.util.List;

public class artistimages
        extends Fox.core.lib.services.LastFM.CommonSources.artist
{
    private List<image> images;

    public artistimages()
    {

    }

    public artistimages(
            String name,
            String url,
            List<image> images)
    {
        super(name,
              url
             );
        this.images = image.imageListCopy(images);
    }

    public artistimages(artistimages copy)
    {
        this.images = image.imageListCopy(copy.images);
        this.setName(copy.getName());
        this.setUrl(copy.getUrl());
    }

    public static List<artistimages> artistimagesListCopy(List<artistimages> copy)
    {
        List<artistimages> temp = null;

        if (copy != null)
        {
            temp = new ArrayList<>();
            for (artistimages elem : copy)
            {
                temp.add(new artistimages(elem));
            }
        }
        return temp;
    }

    public List<image> getImages()
    {
        return image.imageListCopy(images);
    }

    public void setImages(List<image> images)
    {
        this.images = image.imageListCopy(images);
    }

    public boolean hasImages()
    {
        return (images != null && !images.isEmpty());
    }
}
