package Fox.core.lib.services.AcoustID.LookupByFP.sources;

import java.util.ArrayList;
import java.util.List;

public class Artist
{
    private String id;
    private String name;
    private String joinphrase;

    public Artist()
    {
    }

    public Artist(
            String id,
            String name,
            String joinphrase)
    {
        this.id = id;
        this.name = name;
        this.joinphrase = joinphrase;
    }

    public Artist(Artist copy)
    {
        if (copy != null)
        {
            this.name = copy.name;
            this.id = copy.id;
            this.joinphrase = copy.joinphrase;
        }
    }

    public static List<Artist> ArtistListCopy(List<Artist> copy)
    {
        List<Artist> temp = null;

        if (copy != null)
        {
            temp = new ArrayList<>();
            for (Artist elem : copy)
            {
                temp.add(new Artist(elem));
            }
        }
        return temp;
    }

    public String getId()
    {
        return this.id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean hasId()
    {
        return id != null && id.length() > 0;
    }

    public boolean hasName()
    {
        return name != null && name.length() > 0;
    }

    public String getJoinphrase()
    {
        return joinphrase;
    }

    public void setJoinphrase(String joinphrase)
    {
        this.joinphrase = joinphrase;
    }

    public boolean hasJoinPhrase()
    {
        return (joinphrase!=null && !joinphrase.isEmpty());
    }
}
