package Fox.core.lib.services.Common;

public class Elapsed
{
    private static final Long
            AcoustIDElapseState = (long) 330,
            LastFMElapseState = (long) 300,
            MusicBrainzElapseState = (long) 500;
    private static Long
            AcoustIDElapse = (long) System.currentTimeMillis(),
            LastFMElapse = (long) System.currentTimeMillis(),
            MusicBrainzElapse = (long) System.currentTimeMillis();

    public static synchronized long LastFMElapse()
    {
        long temp;
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis > LastFMElapse)
        {
            temp = 0;
            LastFMElapse = System.currentTimeMillis() + LastFMElapseState;
        } else
        {
            temp = LastFMElapse - currentTimeMillis;
            LastFMElapse += LastFMElapseState;
        }
        return temp;

    }

    public static synchronized long AcoustIDElapse()
    {
        long temp;
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis > AcoustIDElapse)
        {
            temp = 0;
            AcoustIDElapse = System.currentTimeMillis() + AcoustIDElapseState;
        } else
        {
            temp = AcoustIDElapse - currentTimeMillis;
            AcoustIDElapse += AcoustIDElapseState;
        }
        return temp;

    }

    public static synchronized long MusicBrainzElapse()
    {
        long temp;
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis > MusicBrainzElapse)
        {
            temp = 0;
            MusicBrainzElapse = System.currentTimeMillis() + MusicBrainzElapseState;
        } else
        {
            temp = MusicBrainzElapse - currentTimeMillis;
            MusicBrainzElapse += MusicBrainzElapseState;
        }
        return temp;

    }
}
