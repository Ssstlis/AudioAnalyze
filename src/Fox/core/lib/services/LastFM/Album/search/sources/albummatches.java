package Fox.core.lib.services.LastFM.Album.search.sources;

import java.util.List;

public class albummatches
{
    private List<album> albumList;

    public albummatches()
    {

    }

    public albummatches(List<album> albumList)
    {
        this.albumList = album.getAlbumListCopy(albumList);
    }

    public albummatches(albummatches copy)
    {
        if (copy != null)
        {
            this.albumList = album.getAlbumListCopy(copy.albumList);
        }
    }

    public List<album> getAlbumList()
    {
        return album.getAlbumListCopy(albumList);
    }

    public void setAlbumList(List<album> albumList)
    {
        this.albumList = album.getAlbumListCopy(albumList);
    }

    public boolean hasAlbumList()
    {
        return (albumList != null && !albumList.isEmpty());
    }
}
