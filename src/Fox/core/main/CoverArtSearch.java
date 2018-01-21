package Fox.core.main;

import Fox.core.lib.general.DOM.AlbumArtCompilation;
import Fox.core.lib.services.Common.SimpleInfo;

public class CoverArtSearch
{
    public CoverArtSearch()
    {
    }

    public AlbumArtCompilation run(
            String AlbumName,
            String ArtistName,
            Integer count)
    {
        if (AlbumName == null || AlbumName.isEmpty() || count == null || count <= 0)
        {
            return null;
        }

        return new AlbumArtCompilation();
    }
}
