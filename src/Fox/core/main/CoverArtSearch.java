package Fox.core.main;

import Fox.core.lib.general.DOM.AlbumArtCompilation;
import Fox.core.lib.general.DOM.Art;
import Fox.core.lib.general.utils.target;
import Fox.core.lib.services.CoverArtArchive.CoverArtArchiveClient;
import Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources.AlbumArt;
import Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources.thumbnail;
import Fox.core.lib.services.LastFM.Album.getInfo.sources.AlbumInfo;
import Fox.core.lib.services.LastFM.Album.getInfo.sources.album;
import Fox.core.lib.services.LastFM.Album.search.sources.Search;
import Fox.core.lib.services.LastFM.Album.search.sources.albummatches;
import Fox.core.lib.services.LastFM.Album.search.sources.results;
import Fox.core.lib.services.LastFM.CommonSources.image;
import Fox.core.lib.services.LastFM.LastFMClient;
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
            String AlbumName,
            String ArtistName,
            target source,
            Integer count)
    {
        AlbumArtCompilation temp = new AlbumArtCompilation(AlbumName,
                                                           ArtistName,
                                                           null);

        if (source == null || AlbumName == null || AlbumName.isEmpty() || count == null || count <= 0)
        {
            return temp;
        }



        if (ArtistName != null && !ArtistName.isEmpty())
        {
            AlbumInfo LFMInfo = new LastFMClient().Album()
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
                    for (image elem : album.getImages())
                        ArtList.add(new Art(elem.getText(),elem.getSize(),target.LastFM));

                AlbumArt lookupAlbumArt = new CoverArtArchiveClient().LookupAlbumArt(album.getMbid());

                if (lookupAlbumArt.hasImages())
                {
                    for (Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources.image elem:lookupAlbumArt.getImages())
                        if (elem.hasFront())
                        {
                            thumbnail elemThumbnail = elem.getThumbnail();
                            if (elemThumbnail!=null)
                            {
                                if (elemThumbnail.hasSmall())
                                    ArtList.add(new Art(elemThumbnail.getSmall(),"small",target.CoverArtArchive));

                                if (elemThumbnail.hasLarge())
                                    ArtList.add(new Art(elemThumbnail.getLarge(),"large",target.CoverArtArchive));
                            }
                            ArtList.add(new Art(elem.getImage(),"mega",target.CoverArtArchive));
                        }
                }

                temp.setArtList(ArtList);
            }
        }
        if (source == target.LastFM)
        {
            Search searchLF = new LastFMClient().Album()
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

                        if (!LFMResultsAlbummatchesAlbumList.isEmpty())
                        {
                            List<Art> ArtList = new ArrayList<>();

                            for (Fox.core.lib.services.LastFM.Album.search.sources.album elem:LFMResultsAlbummatchesAlbumList)
                                for(image ImgElem:elem.getImages())
                                    ArtList.add(new Art(ImgElem.getText(),ImgElem.getSize(),target.LastFM));

                            temp.setArtList(ArtList);
                        }

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
                    for (int i = 0; i < 10 || i < releaseInfos.size() ; i++)
                    {
                        AlbumArt albumArt = coverArtArchiveClient.LookupAlbumArt(releaseInfos.get(i)
                                                                                             .getReleaseMbid());
                        if (albumArt!=null && albumArt.hasImages())
                        {

                            for ()
                                ArtList.add(new Art(albumArt.));
                        }
                    }
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
