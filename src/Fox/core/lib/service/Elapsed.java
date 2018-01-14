package Fox.core.lib.service;

public class Elapsed {

    public static long
            AcoustIDElapse,
            MusicBrainzElapse,
            LastFMElapse,
            CoverArtArchiveElapse;

    public static boolean
            AcoustIDUsage = false,
            MusicBrainzUsage = false,
            LastFMUsage = false,
            CoverArtArchiveUsage = false;

    public static final long
            AcoustIDElapseState = 330,
            MusicBrainzElapseState = 200,
            LastFMElapseState = 200,
            CoverArtArchiveElapseState = 330;
}
