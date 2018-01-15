package Fox.core.lib.service.CoverArtArchive;

import java.util.List;

public class CoverArtObject
{
    //TODO REFORMAT AND REFACTOR
    private String       release;
    private List<images> images;

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public List<images> getImages() {
        return images;
    }

    public void setImages(List<images> images) {
        this.images = images;
    }
}
