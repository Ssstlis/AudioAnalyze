package Fox.core.lib.general.DOM;

import Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources.AlbumArt;
import Fox.core.lib.services.LastFM.LastFMTrackInfoCompilation;

public class AlbumArtCompilationBuilder
{
    public AlbumArtCompilationBuilder()
    {

    }

    public AlbumArtCompilation buildAlbumArtCompilation(AlbumArt CoverArchive, LastFMTrackInfoCompilation LastFMCompile)
    {
        if (CoverArchive==null || !CoverArchive.hasImages()
                || LastFMCompile==null || LastFMCompile.getAlbumInfo().hasError())
            return null;


        AlbumArtCompilation temp = null;

        try
        {

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return temp;
    }
}
