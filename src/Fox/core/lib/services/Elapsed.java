package Fox.core.lib.services;

public class Elapsed
{
    private static final long
            AcoustIDElapseState = 330,
            LastFMElapseState = 250;
    private static long
            AcoustIDElapse,
            LastFMElapse;
    private static boolean
            AcoustIDUsage = false,
            LastFMUsage = false;

    public static long LastFMElapse()
    {
        long temp = System.currentTimeMillis() - LastFMElapse;

        LastFMElapse = System.currentTimeMillis();

        if (!LastFMUsage)
        {
            LastFMUsage = true;
            return 0;
        }
        if (temp > LastFMElapseState)
        {
            return 0;
        }
        else
        {
            return (LastFMElapseState - temp);
        }
    }

    public static long AcoustIDElapse()
    {
        long temp = System.currentTimeMillis() - AcoustIDElapse;

        AcoustIDElapse = System.currentTimeMillis();

        if (!AcoustIDUsage)
        {
            AcoustIDUsage = true;
            return 0;
        }
        if (temp > AcoustIDElapseState)
        {
            return 0;
        }
        else
        {
            return (AcoustIDElapseState - temp);
        }
    }
}
