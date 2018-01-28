package Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources;

import java.util.List;

public class image
{
    private List<String> types;
    private thumbnail thumbnail;
    private boolean approved;
    private boolean front;
    private boolean back;
    private String comment;
    private String image;
    private long id;
    private long edit;

    public image()
    {

    }

    public image(
            List<String> types,
            thumbnail thumbnail,
            boolean approved,
            boolean front,
            boolean back,
            String comment,
            String image,
            long id,
            long edit)
    {
        this.types = types;
        this.thumbnail = thumbnail;
        this.approved = approved;
        this.front = front;
        this.back = back;
        this.comment = comment;
        this.image = image;
        this.id = id;
        this.edit = edit;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public boolean hasImage()
    {
        return (image != null && !image.isEmpty());
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public boolean hasComment()
    {
        return (comment != null && !comment.isEmpty());
    }

    public boolean isBack()
    {
        return back;
    }

    public void setBack(boolean back)
    {
        this.back = back;
    }

    public boolean hasBack()
    {
        return back;
    }

    public boolean isFront()
    {
        return front;
    }

    public void setFront(boolean front)
    {
        this.front = front;
    }

    public boolean hasFront()
    {
        return front;
    }

    public boolean isApproved()
    {
        return approved;
    }

    public void setApproved(boolean approved)
    {
        this.approved = approved;
    }

    public boolean hasApproved()
    {
        return approved;
    }

    public List<String> getTypes()
    {
        return types;
    }

    public void setTypes(List<String> types)
    {
        this.types = types;
    }

    public boolean hasTypes()
    {
        return (types != null && !types.isEmpty());
    }

    public thumbnail getThumbnail()
    {
        return thumbnail;
    }

    public void setThumbnail(thumbnail thumbnail)
    {
        this.thumbnail = thumbnail;
    }

    public boolean hasThumbnail()
    {
        return thumbnail != null;
    }

    public long getEdit()
    {
        return edit;
    }

    public void setEdit(long edit)
    {
        this.edit = edit;
    }

    public boolean hasEdit()
    {
        return edit > 0;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public boolean hasId()
    {
        return id > 0;
    }
}
