package Fox.core.main;

import Fox.core.lib.general.DOM.AlbumArtCompilation;
import Fox.core.lib.general.DOM.Art;
import Fox.core.lib.general.DOM.Extract;
import Fox.core.lib.general.utils.Exceptions;
import Fox.core.lib.general.utils.target;
import Fox.core.lib.services.CoverArtArchive.CoverArtArchiveApi;
import Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources.AlbumArt;
import Fox.core.lib.services.LastFM.Album.getInfo.sources.AlbumInfo;
import Fox.core.lib.services.LastFM.Album.getInfo.sources.album;
import Fox.core.lib.services.LastFM.Album.search.sources.Search;
import Fox.core.lib.services.LastFM.Album.search.sources.albummatches;
import Fox.core.lib.services.LastFM.Album.search.sources.results;
import Fox.core.lib.services.LastFM.CommonSources.image;
import org.jetbrains.annotations.NotNull;
import org.musicbrainz.android.api.data.ReleaseArtist;
import org.musicbrainz.android.api.data.ReleaseInfo;
import org.musicbrainz.android.api.webservice.MusicBrainzWebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static Fox.core.lib.services.Common.Elapsed.MusicBrainzElapse;
import static Fox.core.lib.services.LastFM.Album.LastFMAlbumClient.getInfo;
import static Fox.core.lib.services.LastFM.Album.LastFMAlbumClient.search;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class CoverArtSearch
{

    private static final Logger logger = LoggerFactory.getLogger(CoverArtSearch.class);

    private static void AddMostResolutionImageLink(String Artist,
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
        catch (Exceptions.NoMatchesException e)
        {
            if (logger.isErrorEnabled())
                logger.error("", e);
        }
    }

    public static AlbumArtCompilation run(
            @NotNull String AlbumName,
            String ArtistName,
            @NotNull target source,
            @NotNull Integer count)
            throws
            IllegalArgumentException,
            Exceptions.NoMatchesException
    {
        AlbumArtCompilation temp = new AlbumArtCompilation(AlbumName,
                                                           ArtistName,
                                                           null
        );

        if (AlbumName.isEmpty() || count <= 0)
        {
            if (logger.isErrorEnabled())
                logger.error("Illegal album title or number of arts.");
            throw new IllegalArgumentException();
        }

        if (logger.isDebugEnabled())
            logger.debug("starting search");

        if (ArtistName != null && !ArtistName.isEmpty())
        {
            if (logger.isDebugEnabled())
                logger.debug("first scenario");

            AlbumInfo LFMInfo =
                    getInfo(null,
                             ArtistName,
                             AlbumName,
                             null,
                             null,
                             true
                            );
            if (LFMInfo == null || LFMInfo.hasError())
            {
                throw new Exceptions.NoMatchesException("No matches.");
            }

            if (LFMInfo.hasAlbum())
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
                    if (logger.isDebugEnabled())
                        logger.debug("Cover Art lookup start");
                    try
                    {
                        MILLISECONDS.sleep(MusicBrainzElapse());
                    }
                    catch (InterruptedException e)
                    {
                        if (logger.isErrorEnabled())
                            logger.error("", e);
                    }
                    AlbumArt lookupAlbumArt = CoverArtArchiveApi.LookupAlbumArt(album.getMbid());

                    if (logger.isDebugEnabled())
                        logger.debug("Cover Art lookup end");
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
            if (logger.isDebugEnabled())
                logger.debug("second scenario");

            if (logger.isDebugEnabled())
                logger.debug("LastFM lookup start");

            Search searchLF =
                    search(count,
                            null,
                            AlbumName
                           );

            if (logger.isDebugEnabled())
                logger.debug("LastFM lookup end");

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
                            throw new Exceptions.NoMatchesException("No matches.");

                        temp.setArtList(ArtList);
                        return temp;
                    }
                }
            }
        }

        if (source == target.MusicBrainz)
        {
            if (logger.isDebugEnabled())
                logger.debug("third scenario");
            try
            {
                if (logger.isDebugEnabled())
                    logger.debug("MB lookup start");
                try
                {
                    MILLISECONDS.sleep(MusicBrainzElapse());
                }
                catch (InterruptedException e)
                {
                    if (logger.isErrorEnabled())
                        logger.error("", e);
                }
                LinkedList<ReleaseInfo> releaseInfos = new MusicBrainzWebClient("AudioAnalyzeApp").searchRelease(AlbumName);

                if (logger.isDebugEnabled())
                    logger.debug("MB lookup end");

                if (releaseInfos != null && !releaseInfos.isEmpty())
                {
                    List<Art> ArtList = new ArrayList<>();

                    for (int i = 0, size = releaseInfos.size();
                         ArtList.size() != count && i < size;
                         i++)
                    {
                        if (logger.isDebugEnabled())
                            logger.debug("CoverArt lookup start");
                        try
                        {
                            MILLISECONDS.sleep(MusicBrainzElapse());
                        }
                        catch (InterruptedException e)
                        {
                            if (logger.isErrorEnabled())
                                logger.error("", e);
                        }
                        AlbumArt albumArt = CoverArtArchiveApi.LookupAlbumArt(releaseInfos.get(i)
                                                                                          .getReleaseMbid());
                        if (logger.isDebugEnabled())
                            logger.debug("CoverArt lookup end");

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
                if (logger.isErrorEnabled())
                    logger.error("", e);
                throw new Exceptions.NoMatchesException("No matches.", e);
            }
        }

        throw new Exceptions.NoMatchesException("No matches.");
    }
}
