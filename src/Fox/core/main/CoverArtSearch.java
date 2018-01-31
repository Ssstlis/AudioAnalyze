package Fox.core.main;

import Fox.core.lib.general.DOM.AlbumArtCompilation;
import Fox.core.lib.general.DOM.Art;
import Fox.core.lib.general.DOM.Extract;
import Fox.core.lib.general.utils.NoMatchesException;
import Fox.core.lib.general.utils.target;
import Fox.core.lib.services.CoverArtArchive.CoverArtArchiveClient;
import Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources.AlbumArt;
import Fox.core.lib.services.LastFM.Album.getInfo.sources.AlbumInfo;
import Fox.core.lib.services.LastFM.Album.getInfo.sources.album;
import Fox.core.lib.services.LastFM.Album.search.sources.Search;
import Fox.core.lib.services.LastFM.Album.search.sources.albummatches;
import Fox.core.lib.services.LastFM.Album.search.sources.results;
import Fox.core.lib.services.LastFM.CommonSources.image;
import Fox.core.lib.services.LastFM.LastFMApi;
import org.jetbrains.annotations.NotNull;
import org.musicbrainz.android.api.data.ReleaseArtist;
import org.musicbrainz.android.api.data.ReleaseInfo;
import org.musicbrainz.android.api.webservice.MusicBrainzWebClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CoverArtSearch
{

    private static void AddMostResolutionImageLink(
            String Artist,
            String Album,
            List<image> imageList,
            @NotNull List<Art> targetList)
    {
        Extract extract;

        try
        {
            extract = image.extract(imageList);

            if (extract != null && extract.hasText())
                targetList.add(new Art(extract.getText(),
                                    extract.getSize(),
                                    Artist,
                                    Album,
                                    target.LastFM
                ));
        }
        catch (NoMatchesException e)
        {
            e.printStackTrace();
        }
    }

    public static AlbumArtCompilation run(
            @NotNull String AlbumName,
            String ArtistName,
            @NotNull target source,
            @NotNull Integer count)
            throws
            IllegalArgumentException,
            NoMatchesException
    {
        AlbumArtCompilation temp = new AlbumArtCompilation(AlbumName,
                                                           ArtistName,
                                                           null
        );

        if (AlbumName.isEmpty() || count <= 0)
        {
            throw new IllegalArgumentException();
        }

        if (ArtistName != null && !ArtistName.isEmpty())
        {
            AlbumInfo LFMInfo = new LastFMApi().Album
                    .getInfo(null,
                             ArtistName,
                             AlbumName,
                             null,
                             null,
                             true
                            );
            if (LFMInfo != null && LFMInfo.hasError())
            {
                throw new NoMatchesException("No matches.");
            }

            if (LFMInfo != null && LFMInfo.hasAlbum())
            {
                album album = LFMInfo.getAlbum();
                List<Art> ArtList = new ArrayList<>();

                String albumArtist = album.getArtist();
                String albumName = album.getName();

                if (album.hasImages())
                    AddMostResolutionImageLink(albumArtist,
                                               albumName,
                                               album.getImages(),
                                               ArtList);

                if (album.hasMbid())
                {
                    AlbumArt lookupAlbumArt = CoverArtArchiveClient.LookupAlbumArt(album.getMbid());

                    if (lookupAlbumArt != null && lookupAlbumArt.hasImages())
                    {
                        for (Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources.image elem : lookupAlbumArt.getImages())
                        {
                            if (elem.hasFront())
                            {
                                ArtList.add(new Art(elem.getImage(),
                                                    "mega",
                                                    albumArtist,
                                                    albumName,
                                                    target.CoverArtArchive
                                ));
                            }
                        }
                    }
                }
                temp.setArtList(ArtList);
                return temp;
            }
        }

        if (source == target.LastFM)
        {
            Search searchLF = new LastFMApi().Album
                    .search(count,
                            null,
                            AlbumName
                           );

            if (searchLF != null && searchLF.hasResults())
            {
                results LFMResults = searchLF.getResults();

                if (LFMResults.hasAlbummatches())
                {
                    albummatches LFMResultsAlbummatches = LFMResults.getAlbummatches();

                    if (LFMResultsAlbummatches.hasAlbumList())
                    {
                        List<Fox.core.lib.services.LastFM.Album.search.sources.album> LFMResultsAlbummatchesAlbumList = LFMResultsAlbummatches.getAlbumList();

                        List<Art> ArtList = new ArrayList<>();

                        int ResSize = LFMResultsAlbummatchesAlbumList.size();

                        for (int l = 0; ArtList.size() != count && l < ResSize; l++)
                        {
                            Fox.core.lib.services.LastFM.Album.search.sources.album elem = LFMResultsAlbummatchesAlbumList.get(l);

                            String elemArtist = elem.getArtist();
                            String elemName = elem.getName();

                            if (elem.hasImages())
                                AddMostResolutionImageLink(elemArtist,
                                                           elemName,
                                                           elem.getImages(),
                                                           ArtList);
                        }

                        if (ArtList.isEmpty())
                            throw new NoMatchesException("No matches.");

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

                if (releaseInfos != null && !releaseInfos.isEmpty())
                {
                    List<Art> ArtList = new ArrayList<>();

                    for (int i = 0, size = releaseInfos.size();
                         ArtList.size() != count && i < size;
                         i++)
                    {
                        AlbumArt albumArt = CoverArtArchiveClient.LookupAlbumArt(releaseInfos.get(i)
                                                                                             .getReleaseMbid());

                        String releaseInfoTitle = null;
                        String name = null;

                        ReleaseInfo releaseInfo = releaseInfos.get(i);

                        if (releaseInfo != null)
                        {

                            releaseInfoTitle = releaseInfo.getTitle();
                            ArrayList<ReleaseArtist> artists = releaseInfo.getArtists();

                            if (artists != null && !artists.isEmpty())
                            {
                                name = artists.get(0)
                                              .getName();
                            }
                        }


                        if (albumArt != null && albumArt.hasImages())
                        {
                            for (Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources.image elem : albumArt.getImages())
                            {
                                if (elem.hasFront())
                                {
                                    ArtList.add(new Art(elem.getImage(),
                                                        "mega",
                                                        name,
                                                        releaseInfoTitle,
                                                        target.CoverArtArchive
                                    ));
                                }
                            }
                        }
                    }
                    temp.setArtList(ArtList);
                    return temp;
                }

            }
            catch (IOException e)
            {
                e.printStackTrace();
                throw new NoMatchesException("No matches.", e);
            }
        }


        throw new NoMatchesException("No matches.");
    }
}
