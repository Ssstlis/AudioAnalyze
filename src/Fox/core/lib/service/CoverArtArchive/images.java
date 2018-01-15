package Fox.core.lib.service.CoverArtArchive;

import java.util.List;

public class images
{
    //TODO REFORMAT AND REFACTOR
    private List<String> types;
    private thumbnails thumbnails;
    private boolean approved,front,back;
    private String comment, image;
    private long id, edit;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isBack() {
        return back;
    }

    public void setBack(boolean back) {
        this.back = back;
    }

    public boolean isFront() {
        return front;
    }

    public void setFront(boolean front) {
        this.front = front;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public thumbnails getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(thumbnails thumbnails) {
        this.thumbnails = thumbnails;
    }

    public long getEdit() {
        return edit;
    }

    public void setEdit(long edit) {
        this.edit = edit;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
