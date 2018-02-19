package Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources;

import java.util.List;

public class image
{
    private List<String> types;
    private thumbnail thumbnail;
    private Boolean approved;
    private Boolean front;
    private Boolean back;
    private String comment;
    private String image;
    private Long id;
    private Long edit;

    public image()
    {

    }

    public image(
            List<String> types,
            thumbnail thumbnail,
            Boolean approved,
            Boolean front,
            Boolean back,
            String comment,
            String image,
            Long id,
            Long edit)
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

    public void setBack(Boolean back)
    {
        this.back = back;
    }

    public Boolean hasBack()
    {
        return back != null && back;
    }

    public Boolean isFront()
    {
        return front;
    }

    public void setFront(Boolean front)
    {
        this.front = front;
    }

    public boolean hasFront()
    {
        return front != null && front;
    }

    public Boolean isApproved()
    {
        return approved;
    }

    public void setApproved(Boolean approved)
    {
        this.approved = approved;
    }

    public boolean hasApproved()
    {
        return approved != null && approved;
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

    public Long getEdit()
    {
        return edit;
    }

    public void setEdit(Long edit)
    {
        this.edit = edit;
    }

    public boolean hasEdit()
    {
        return edit != null && edit > 0;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public boolean hasId()
    {
        return id != null && id > 0;
    }
}
