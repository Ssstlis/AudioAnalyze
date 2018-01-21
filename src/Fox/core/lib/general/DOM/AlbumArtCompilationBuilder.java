package Fox.core.lib.general.DOM;

import Fox.core.lib.services.LastFM.Album.search.sources.Search;
import org.musicbrainz.android.api.data.ReleaseInfo;

import java.util.List;

public class AlbumArtCompilationBuilder
{
    public AlbumArtCompilationBuilder()
    {

    }

    public AlbumArtCompilation buildAlbumArtCompilation(
            Search LFNResponseSearch,
            List<ReleaseInfo> MBInfo
                                                       )
    {
        if (LFNResponseSearch == null || !LFNResponseSearch.hasError()
                && (MBInfo == null || MBInfo.isEmpty()))
        {
            return null;
        }

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
