package Fox.core.main;

import Fox.core.lib.general.DOM.AlbumArtCompilation;
import Fox.core.lib.general.DOM.Art;
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
import Fox.core.lib.services.LastFM.LastFMClient;
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
    public CoverArtSearch()
    {
    }

    public AlbumArtCompilation run(
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
            AlbumInfo LFMInfo = new LastFMClient().Album
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
                {
                    List<image> elem = album.getImages();
                    int size = elem.size();

                    String text = null;
                    String sizes = null;

                    for (int i = size - 1; i >= 0; i--)
                    {
                        image image = elem.get(i);
                        if (image.hasText())
                        {
                            text = image.getText();
                            sizes = image.getSize();
                            break;
                        }
                    }

                    if (text == null)
                    {
                        throw new NoMatchesException("No matches.");
                    }

                    ArtList.add(new Art(text,
                                        sizes,
                                        albumArtist,
                                        albumName,
                                        target.LastFM
                    ));
                }

                if (album.hasMbid())
                {
                    AlbumArt lookupAlbumArt = new CoverArtArchiveClient().LookupAlbumArt(album.getMbid());

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
            Search searchLF = new LastFMClient().Album
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
                            {
                                List<Fox.core.lib.services.LastFM.CommonSources.image> imgList = elem.getImages();
                                int size = imgList.size();

                                String text = null;
                                String sizes = null;

                                for (int i = size - 1; i >= 0; i--)
                                {
                                    image image = imgList.get(i);
                                    if (image.hasText())
                                    {
                                        text = image.getText();
                                        sizes = image.getSize();
                                        break;
                                    }
                                }

                                if (text != null && sizes != null)
                                    ArtList.add(new Art(text,
                                                        sizes,
                                                        elemArtist,
                                                        elemName,
                                                        target.LastFM
                                    ));
                            }
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

                    CoverArtArchiveClient coverArtArchiveClient = new CoverArtArchiveClient();

                    for (int i = 0, size = releaseInfos.size();
                         ArtList.size() != count && i < size;
                         i++)
                    {
                        AlbumArt albumArt = coverArtArchiveClient.LookupAlbumArt(releaseInfos.get(i)
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
                throw new NoMatchesException("No matches.");
            }
        }


        throw new NoMatchesException("No matches.");
    }
}
