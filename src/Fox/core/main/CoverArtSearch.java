package Fox.core.main;

import Fox.core.lib.general.DOM.AlbumArtCompilation;
import Fox.core.lib.general.DOM.Art;
import Fox.core.lib.general.utils.target;
import Fox.core.lib.services.CoverArtArchive.CoverArtArchiveClient;
import Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources.AlbumArt;
import Fox.core.lib.services.LastFM.Album.getInfo.sources.AlbumInfo;
import Fox.core.lib.services.LastFM.Album.getInfo.sources.album;
import Fox.core.lib.services.LastFM.Album.search.sources.Search;
import Fox.core.lib.services.LastFM.Album.search.sources.albummatches;
import Fox.core.lib.services.LastFM.Album.search.sources.results;
import Fox.core.lib.services.LastFM.CommonSources.image;
import Fox.core.lib.services.LastFM.LastFMClient;
import org.jetbrains.annotations.NotNull;
import org.musicbrainz.android.api.data.ReleaseInfo;
import org.musicbrainz.android.api.webservice.MusicBrainzWebClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CoverArtSearch
{
    public CoverArtSearch()
    {
    }

    public AlbumArtCompilation run(
            @NotNull String AlbumName,
            String ArtistName,
            target source,
            Integer count)
    {
        AlbumArtCompilation temp = new AlbumArtCompilation(AlbumName,
                                                           ArtistName,
                                                           null);

        if (source == null || AlbumName.isEmpty() || count == null || count <= 0)
        {
            return null;
        }



        if (ArtistName != null && !ArtistName.isEmpty())
        {
            AlbumInfo LFMInfo = new LastFMClient().Album
                                                  .getInfo(null,
                                                           ArtistName,
                                                           AlbumName,
                                                           null,
                                                           null,
                                                           null
                                                       );
            if (LFMInfo!=null && LFMInfo.hasError())
            {
                return temp;
            }

            if (LFMInfo!=null && LFMInfo.hasAlbum())
            {
                album album = LFMInfo.getAlbum();
                List<Art> ArtList = new ArrayList<>();

                if (album.hasImages())
                {
                    List<image> elem = album.getImages();
                    int         size = elem.size();

                    ArtList.add(new Art(elem.get(size-1).getText(),
                                        elem.get(size-1).getSize(),
                                        target.LastFM));
                }

                if (album.hasMbid())
                {
                    AlbumArt lookupAlbumArt = new CoverArtArchiveClient().LookupAlbumArt(album.getMbid());

                    if (lookupAlbumArt!=null && lookupAlbumArt.hasImages())
                    {
                        for (Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources.image elem : lookupAlbumArt.getImages())
                            if (elem.hasFront())
                                ArtList.add(new Art(elem.getImage(),
                                                    "mega",
                                                    target.CoverArtArchive));
                    }
                }
                temp.setArtList(ArtList);
                return temp;
            }
        }

        if (source == target.LastFM)
        {
            Search searchLF = new LastFMClient().Album
                                                .search(count,
                                                        null,
                                                        AlbumName
                                                       );

            if (searchLF!=null && !searchLF.hasError() && searchLF.hasResults())
            {
                results LFMResults = searchLF.getResults();
                if (LFMResults.hasAlbummatches())
                {
                    albummatches LFMResultsAlbummatches = LFMResults.getAlbummatches();

                    if (LFMResultsAlbummatches.hasAlbumList())
                    {
                        List<Fox.core.lib.services.LastFM.Album.search.sources.album> LFMResultsAlbummatchesAlbumList = LFMResultsAlbummatches.getAlbumList();

                        List<Art> ArtList = new ArrayList<>();

                        for (Fox.core.lib.services.LastFM.Album.search.sources.album elem : LFMResultsAlbummatchesAlbumList)
                        {
                            if (elem.hasImages())
                            {
                                List<Fox.core.lib.services.LastFM.CommonSources.image> imgList = elem.getImages();
                                int                                                    size    = imgList.size();

                                Fox.core.lib.services.LastFM.CommonSources.image ImgElem = imgList.get(size - 1);

                                ArtList.add(new Art(ImgElem.getText(),
                                                    ImgElem.getSize(),
                                                    target.LastFM
                                ));
                            }
                        }
                        temp.setArtList(ArtList);
                        return temp;
                    }
                }
            }
        }

        if (source == target.MusicBrainz)
        {
            try
            {
                LinkedList<ReleaseInfo> releaseInfos = new MusicBrainzWebClient("AudioAnalyzeApp").searchRelease(AlbumName);
                if (releaseInfos!=null && !releaseInfos.isEmpty())
                {
                    List<Art> ArtList = new ArrayList<>();

                    CoverArtArchiveClient coverArtArchiveClient = new CoverArtArchiveClient();

                    for (int i = 0, size = releaseInfos.size(); ArtList.size()!=count && i < size ; i++)
                    {
                        AlbumArt albumArt = coverArtArchiveClient.LookupAlbumArt(releaseInfos.get(i)
                                                                                             .getReleaseMbid());
                        if (albumArt!=null && albumArt.hasImages())
                        {
                                for (Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources.image elem : albumArt.getImages())
                                    if (elem.hasFront())
                                        ArtList.add(new Art(elem.getImage(),
                                                            "mega",
                                                            target.CoverArtArchive));
                        }
                    }
                    temp.setArtList(ArtList);
                    return temp;
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return temp;
    }
}
