package Fox.core.lib.service.acoustid.LookupByFP.sources;

import java.util.ArrayList;
import java.util.List;

public class Artist
{
    private String id;
    private String name;

    public Artist() {
    }

    public Artist(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Artist(Artist copy)
    {
        if (copy!=null)
            {
            this.name = copy.name;
            this.id = copy.id;
            }
    }

    public static List<Artist> ArtistListCopy(List<Artist> copy)
    {
        List<Artist> temp = null;

        if (copy!=null)
            {
                temp = new ArrayList<>();
                for (Artist elem : copy)
                    temp.add(new Artist(elem));
            }
        return temp;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasId() {
        return id != null && id.length()>0;
    }

    public boolean hasName() {
        return name != null && name.length()>0;
    }
}
