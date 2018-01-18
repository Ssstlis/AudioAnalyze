package Fox.core.lib.services.LastFM.Artist.getInfo.sources;

import java.util.List;

public class similar
{
    private List<artistimages> artistimagesList;

    public similar()
    {

    }

    public similar(List<artistimages> artistimagesList)
    {
        this.artistimagesList = artistimages.artistimagesListCopy(artistimagesList);
    }

    public similar(similar copy)
    {
        if (copy!=null)
        {
            this.artistimagesList = artistimages.artistimagesListCopy(copy.artistimagesList);
        }
    }

    public List<artistimages> getArtists()
    {
        return artistimages.artistimagesListCopy(artistimagesList);
    }

    public void setArtists(List<artistimages> artistimagesList)
    {
        this.artistimagesList = artistimages.artistimagesListCopy(artistimagesList);
    }

    public boolean hasArtists()
    {
        return (artistimagesList!=null && !artistimagesList.isEmpty());
    }
}
