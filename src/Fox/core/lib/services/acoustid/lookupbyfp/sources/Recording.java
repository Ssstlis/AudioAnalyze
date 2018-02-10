package Fox.core.lib.services.AcoustID.LookupByFP.sources;

import Fox.core.lib.general.utils.Sorts;
import Fox.core.lib.general.utils.Sorts.Converter;
import Fox.core.lib.services.common.SimpleInfo;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.lang.Math.abs;

public class Recording implements Comparable<Recording>
{
    private Integer duration;
    private Integer sources;
    private String title;
    private String id;
    private List<Artist> Artists;
    private List<Releasegroup> Releasegroups;

    public Recording()
    {
    }

    public Recording(
            Integer duration,
            Integer sources,
            String title,
            String id,
            List<Artist> Artists,
            List<Releasegroup> Releasegroups)
    {
        this.Releasegroups = Releasegroup.ReleasegroupListCopy(Releasegroups);
        this.Artists = Artist.ArtistListCopy(Artists);
        this.duration = duration;
        this.sources = sources;
        this.id = id;
        this.title = title;
    }

    public Recording(Recording copy)
    {
        if (copy != null)
        {
            this.sources = copy.sources;
            this.title = copy.title;
            this.duration = copy.duration;
            this.id = copy.id;
            this.Releasegroups = Releasegroup.ReleasegroupListCopy(copy.Releasegroups);
            this.Artists = Artist.ArtistListCopy(copy.Artists);
        }
    }

    public static List<Recording> RecordingListCopy(List<Recording> copy)
    {
        List<Recording> temp = null;

        if (copy != null)
        {
            temp = new ArrayList<>();
            for (Recording elem : copy)
            {
                temp.add(new Recording(elem));
            }
        }

        return temp;
    }

    public Integer getDuration()
    {
        return this.duration;
    }

    public void setDuration(Integer duration)
    {
        this.duration = duration;
    }

    public String getId()
    {
        return this.id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public List<Artist> getArtists()
    {
        return Artist.ArtistListCopy(this.Artists);
    }

    public void setArtists(List<Artist> artist)
    {
        this.Artists = Artist.ArtistListCopy(artist);
    }

    public boolean hasDuration()
    {
        return duration !=null && duration != 0;
    }

    public boolean hasTitle()
    {
        return title != null && title.length() > 0;
    }

    public boolean hasId()
    {
        return id != null && id.length() > 0;
    }

    public boolean hasArtists()
    {
        return Artists != null && Artists.size() > 0;
    }

    public List<Releasegroup> getReleasegroups()
    {
        return Releasegroup.ReleasegroupListCopy(this.Releasegroups);
    }

    public void setReleasegroups(List<Releasegroup> releasegroups)
    {
        Releasegroups = Releasegroup.ReleasegroupListCopy(releasegroups);
    }

    public boolean hasReleasegroups()
    {
        return Releasegroups != null && Releasegroups.size() > 0;
    }

    public Integer getSources()
    {
        return sources;
    }

    public void setSources(Integer sources)
    {
        this.sources = sources;
    }

    public boolean hasSources()
    {
        return sources != null && sources != 0;
    }

    @Override
    public int compareTo(@NotNull Recording o)
    {
        if (this.sources > o.sources)
            return 1;
        if (this.sources < o.sources)
            return -1;
        return 0;
    }

    public static class RecordingRelativator implements Sorts.Relativator<Recording, Integer, Long>
    {
        @Override
        public Long RelativeCompare(Recording o1, Integer o2)
                throws IllegalArgumentException
        {
            if (o1 == null || !o1.hasDuration() || o1.getDuration() <= 0 ||  o2 == null || o2 <= 0)
                throw new IllegalArgumentException();

            Integer duration = o1.getDuration();

            return (long)abs(duration - o2);
        }
    }

    public static class RecordingComparator implements Comparator<Recording>
    {
        @Override
        public int compare(@NotNull Recording a,
                           @NotNull Recording b)
        {
            return a.getSources().compareTo(b.getSources());
        }

        public Comparator<Recording> reversed()
        {
            return new Comparator<Recording>()
            {
                @Override
                public int compare(@NotNull Recording a,
                                   @NotNull Recording b)
                {
                    return Integer.compare(0, a.getSources().compareTo(b.getSources()));
                }

                public Comparator<Recording> reversed()
                {
                    return new RecordingComparator();
                }
            };
        }
    }

    public static class RecordingSimpleInfoConverter implements Converter<Recording, SimpleInfo>
    {
        @Override
        public SimpleInfo Convert(Recording source)
        {
            SimpleInfo temp = null;
            if (source != null)
            {
                temp = new SimpleInfo();

                temp.setTrackMBID(source.getId());

                if (source.hasArtists())
                {
                    Artist artist = source.getArtists().get(0);

                    StringBuilder ArtistNameBuilder = new StringBuilder();
                    for(Artist elem : source.getArtists())
                    {
                        ArtistNameBuilder.append(elem.getName()).append(((elem.hasJoinPhrase()) ? elem.getJoinphrase() : ""));
                    }
                    temp.setArtist(ArtistNameBuilder.toString());
                    temp.setArtistMBID(artist.getId());
                }

                temp.setTitle(source.getTitle());
                temp.setUsages(source.getSources());
            }
            return temp;
        }
    }
}
