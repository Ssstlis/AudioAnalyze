package Fox.core.lib.services.Common;

import Fox.core.lib.general.data.Extract;
import Fox.core.lib.general.data.ID3V2;
import Fox.core.lib.general.utils.NoMatchesException;
import Fox.core.lib.services.CoverArtArchive.CoverArtArchiveApi;
import Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources.AlbumArt;
import Fox.core.lib.services.LastFM.CommonSources.attr;
import Fox.core.lib.services.LastFM.CommonSources.image;
import Fox.core.lib.services.LastFM.CommonSources.toptags;
import Fox.core.lib.services.LastFM.CommonSources.wiki;
import Fox.core.lib.services.LastFM.Track.LastFMTrackClient;
import Fox.core.lib.services.LastFM.Track.getInfo.sources.TrackInfo;
import Fox.core.lib.services.LastFM.Track.getInfo.sources.album;
import Fox.core.lib.services.LastFM.Track.getInfo.sources.artist;
import Fox.core.lib.services.LastFM.Track.getInfo.sources.track;
import Fox.core.main.SearchLib;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.musicbrainz.android.api.data.*;
import org.musicbrainz.android.api.webservice.MusicBrainzWebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static Fox.core.lib.services.Common.Elapsed.MusicBrainzElapse;
import static Fox.core.main.SearchLib.NO_MATCHES;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class BuildTagProcessing
{
    private static Logger logger;
    private static final MusicBrainzWebClient MBClient = new MusicBrainzWebClient("AudioAnalyzeLib");
    private static final Map<String, Release> MusicBrainzReleaseCache = new HashMap<>();
    private static final Map<String, Artist> MusicBrainzArtistCache = new HashMap<>();
    private static final Map<String, Recording> MusicBrainzRecordingCache = new HashMap<>();

    public static void ClearCache()
    {
        MusicBrainzArtistCache.clear();
        MusicBrainzRecordingCache.clear();
        MusicBrainzReleaseCache.clear();
    }

    /**
     * Merge tracks data from MusicBrainz and LastFM.
     *
     * @param MB  Data from MusicBrainz.
     * @param LFM Data from LastFM.
     * @return Single ID3V2 data instance.
     */
    @Contract(value = "null, null -> null", pure = true)
    private static ID3V2 MergeTags(final ID3V2 MB, final ID3V2 LFM)
    {
        if (MB == null && LFM == null)
            return null;

        if (MB == null)
        {
            String lfmAlbumMBID = LFM.getAlbumMBID();
            if (lfmAlbumMBID != null)
            //Date section
            {
                try
                {
                    if (LFM.hasNumber() && LFM.getNumber() < 1)
                        LFM.setNumber(null);

                    Release lookupRelease;
                    synchronized (MusicBrainzReleaseCache)
                    {
                        Release CacheRelease = MusicBrainzReleaseCache.get(lfmAlbumMBID);
                        if (CacheRelease != null)
                        {
                            if (logger.isDebugEnabled())
                                logger.debug("using caching release {}. Size of releases cache {}", lfmAlbumMBID, MusicBrainzReleaseCache.size());
                            lookupRelease = CacheRelease;
                        }
                        else
                        {
                            MILLISECONDS.sleep(MusicBrainzElapse());
                            lookupRelease = MBClient.lookupRelease(lfmAlbumMBID);
                            if (lookupRelease != null && lookupRelease.getMbid() != null)
                            {
                                MusicBrainzReleaseCache.put(lfmAlbumMBID, lookupRelease);
                                if (logger.isDebugEnabled())
                                    logger.debug("add caching release {}. Size of releases cache {}", lfmAlbumMBID, MusicBrainzReleaseCache.size());
                            }
                        }
                    }
                    if (lookupRelease != null)
                    {
                        String lookupReleaseDate = lookupRelease.getDate();
                        int indexOf = lookupReleaseDate != null ? lookupReleaseDate.indexOf("-") : 0;
                        if (indexOf > 0)
                            lookupReleaseDate = lookupReleaseDate.substring(0, indexOf);
                        LFM.setYear(lookupReleaseDate);
                    }
                }
                catch (Exception e)
                {
                    if (logger.isErrorEnabled())
                        logger.error("MusicBrainzException at MBID {}", lfmAlbumMBID, e);
                }
            }
            return LFM;
        }

        if (LFM == null)
            return MB;

        //Merge section
        ID3V2 temp = new ID3V2();

        String AlbumMBID = null;
        String ArtistMBID = null;
        Release lookupRelease = null;
        Artist lookupArtist = null;

        if (MB.hasAlbumMBID())
            AlbumMBID = MB.getAlbumMBID();
        else if (LFM.hasAlbumMBID())
            AlbumMBID = LFM.getAlbumMBID();

        if (MB.hasArtistMBID())
            ArtistMBID = MB.getArtistMBID();
        else if (LFM.hasArtistMBID())
            ArtistMBID = LFM.getArtistMBID();

        try
        {
            if (AlbumMBID != null)
            {
                synchronized (MusicBrainzReleaseCache)
                {
                    Release CacheRelease = MusicBrainzReleaseCache.get(AlbumMBID);
                    if (CacheRelease != null)
                    {
                        lookupRelease = CacheRelease;
                        if (logger.isDebugEnabled())
                            logger.debug("using caching release {}. Size of releases cache {}", AlbumMBID, MusicBrainzReleaseCache.size());
                    }
                    else
                    {
                        MILLISECONDS.sleep(MusicBrainzElapse());
                        lookupRelease = MBClient.lookupRelease(AlbumMBID);
                        if (lookupRelease != null && lookupRelease.getMbid() != null)
                        {
                            MusicBrainzReleaseCache.put(AlbumMBID, lookupRelease);
                            if (logger.isDebugEnabled())
                                logger.debug("add caching release {}. Size of releases cache {}", AlbumMBID, MusicBrainzReleaseCache.size());
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            if (logger.isErrorEnabled())
                logger.error("MusicBrainzException at MBID {}", AlbumMBID, e);
        }

        try
        {
            if (ArtistMBID != null)
            {
                synchronized (MusicBrainzArtistCache)
                {
                    Artist CacheArtist = MusicBrainzArtistCache.get(ArtistMBID);
                    if (CacheArtist != null)
                    {
                        if (logger.isDebugEnabled())
                            logger.debug("using caching artist {}. Size of artist cache {}", ArtistMBID, MusicBrainzArtistCache.size());
                        lookupArtist = CacheArtist;
                    }
                    else
                    {
                        MILLISECONDS.sleep(MusicBrainzElapse());
                        lookupArtist = MBClient.lookupArtist(ArtistMBID);
                        if (lookupArtist != null && lookupArtist.getMbid() != null)
                        {
                            MusicBrainzArtistCache.put(ArtistMBID, lookupArtist);
                            if (logger.isDebugEnabled())
                                logger.debug("add caching artist {}. Size of artist cache {}", ArtistMBID, MusicBrainzArtistCache.size());
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            if (logger.isErrorEnabled())
                logger.error("MusicBrainzException at MBID {}", ArtistMBID, e);
        }

        if (logger.isErrorEnabled() && (lookupArtist == null || lookupArtist.getMbid() == null))
            logger.error("null lookup artist at mbid {}", ArtistMBID);
        if (logger.isErrorEnabled() && (lookupRelease == null || lookupRelease.getMbid() == null))
            logger.error("null lookup release at mbid {}", AlbumMBID);

        //Album section
        Album_section:
        {
            if (MB.hasAlbum() && LFM.hasAlbum())
            {
                temp.setAlbum(MB.getAlbum());
                break Album_section;
            }
            if (!MB.hasAlbum())
            {
                if (LFM.hasAlbumMBID() && lookupRelease != null)
                {
                    String lookupReleaseTitle = lookupRelease.getTitle();
                    if (lookupReleaseTitle != null && !lookupReleaseTitle.isEmpty())
                    {
                        temp.setAlbum(lookupReleaseTitle);
                        break Album_section;
                    }
                }
                temp.setAlbum(LFM.getAlbum());
            }

            if (!LFM.hasAlbum())
                temp.setAlbum(MB.getAlbum());
        }

        //Artist section
        Artist_section:
        {
            if (MB.hasArtist() && LFM.hasArtist())
            {
                temp.setArtist(MB.getArtist());
                break Artist_section;
            }
            if (!MB.hasArtist())
            {
                if (LFM.hasArtistMBID() && lookupArtist != null)
                {
                    String lookupArtistName = lookupArtist.getName();
                    if (lookupArtistName != null && !lookupArtistName.isEmpty())
                    {
                        temp.setArtist(lookupArtistName);
                        break Artist_section;
                    }
                }
                temp.setAlbum(LFM.getAlbum());
            }
            if (!LFM.hasArtist())
                temp.setArtist(MB.getArtist());
        }

        //Year section
        Year_section:
        {
            if (MB.hasYear() && LFM.hasYear())
            {
                temp.setYear(MB.getYear());
                break Year_section;
            }
            if (!MB.hasYear())
            {
                if (LFM.hasAlbumMBID() && lookupRelease != null)
                {
                    String lookupReleaseDate = lookupRelease.getDate();
                    if (lookupReleaseDate != null)
                    {
                        int indexOf = lookupReleaseDate.indexOf("-");
                        if (indexOf > 0)
                            lookupReleaseDate = lookupReleaseDate.substring(0, indexOf);
                        temp.setYear(lookupReleaseDate);
                        break Year_section;
                    }
                }
                temp.setYear(LFM.getYear());
            }
            if (!LFM.hasYear())
                temp.setYear(MB.getYear());
        }

        //ArtLinks section
        ArtLink_section:
        {
            if (MB.hasArtLinks() && LFM.hasArtLinks())
            {
                temp.setArtLinks(MB.getArtLinks());
                break ArtLink_section;
            }
            if (!MB.hasArtLinks())
            {
                temp.setArtLinks(LFM.getArtLinks());
                break ArtLink_section;
            }
            if (!LFM.hasArtLinks())
                temp.setArtLinks(MB.getArtLinks());
        }

        //Tag section
        Tag_section:
        {
            if (MB.hasTag() && LFM.hasTag())
            {
                temp.setTag(MB.getTag());
                break Tag_section;
            }
            if (!MB.hasTag())
            {
                temp.setTag(LFM.getTag());
                break Tag_section;
            }
            if (!LFM.hasTag())
                temp.setTag(MB.getTag());
        }

        //Title section
        Title_section:
        {
            if (MB.hasTitle() && LFM.hasTitle())
            {
                temp.setTitle(MB.getTitle());
                break Title_section;
            }
            if (!MB.hasTitle())
            {
                temp.setTitle(LFM.getTitle());
                break Title_section;
            }
            if (!LFM.hasTitle())
                temp.setTitle(MB.getTitle());
        }
        //Track number section
        Track_number_section:
        {
            if (MB.hasNumber() && MB.getNumber() > -1 && LFM.hasNumber() && LFM.getNumber() > -1)
            {
                temp.setNumber(MB.getNumber());
                break Track_number_section;
            }
            if (!MB.hasNumber() && LFM.hasNumber() && LFM.getNumber() > -1)
            {
                temp.setNumber(LFM.getNumber());
                break Track_number_section;
            }
            if (!LFM.hasNumber() && MB.hasNumber() && MB.getNumber() > -1)
                temp.setNumber(MB.getNumber());
        }
        //Info section
        Info_section:
        {
            if (!MB.hasComment())
                temp.setComment(LFM.getComment());
        }

        //Artist MBID section
        Artist_MBID_section:
        {
            if (MB.hasArtistMBID() && LFM.hasArtistMBID())
            {
                temp.setArtistMBID(MB.getArtistMBID());
                break Artist_MBID_section;
            }
            if (!MB.hasArtistMBID())
            {
                temp.setArtistMBID(LFM.getArtistMBID());
                break Artist_MBID_section;
            }
            if (!LFM.hasArtistMBID())
                temp.setArtistMBID(MB.getArtistMBID());
        }

        //Album MBID section
        Album_MBID_section:
        {
            if (MB.hasAlbumMBID() && LFM.hasAlbumMBID())
            {
                temp.setAlbumMBID(MB.getAlbumMBID());
                break Album_MBID_section;
            }
            if (!MB.hasAlbumMBID())
            {
                temp.setAlbumMBID(LFM.getAlbumMBID());
                break Album_MBID_section;
            }
            if (!LFM.hasAlbumMBID())
                temp.setAlbumMBID(MB.getAlbumMBID());
        }

        //Track MBID section
        Track_MBID_section:
        {
            if (MB.hasTrackMBID() && LFM.hasTrackMBID())
            {
                temp.setTrackMBID(MB.getTrackMBID());
                break Track_MBID_section;
            }
            if (!MB.hasTrackMBID())
            {
                temp.setTrackMBID(LFM.getTrackMBID());
                break Track_MBID_section;
            }
            if (!LFM.hasTrackMBID())
                temp.setTrackMBID(MB.getTrackMBID());
        }
        return temp;
    }

    /**
     * Converting Recording instance that contains track data into ID3V2 data instance.
     *
     * @param MBRecording Recording instance.
     * @return ID3V2 data instance.
     */
    private static ID3V2 TagFromMusicBrainz(final Recording MBRecording)
    {
        ID3V2 temp = null;

        if (MBRecording != null)
        {
            temp = new ID3V2();

            ReleaseInfo releaseInfo = null;
            List<ReleaseArtist> releaseArtist = null;
            Tag tag = null;

            ArrayList<ReleaseInfo> releases = MBRecording.getReleases();
            if (!releases.isEmpty())
                releaseInfo = releases.get(0);

            ArrayList<ReleaseArtist> artists = MBRecording.getArtists();
            if (!artists.isEmpty())
                releaseArtist = artists;

            LinkedList<Tag> tags = MBRecording.getTags();
            if (!tags.isEmpty())
            {
                tag = tags.get(0);
                int size = tags.size();
                for (int i = 1; i < size; i++)
                {
                    Tag tag1 = tags.get(i);
                    if (tag1.getCount() > tag.getCount())
                        tag = tag1;
                }
            }

            if (releaseInfo != null)
            {
                //Date section
                temp.setYear(releaseInfo.getDate());
                //Album title section
                temp.setAlbum(releaseInfo.getTitle());
                //Track number section
                int tracksNum = releaseInfo.getTracksNum();
                temp.setNumber(tracksNum > 0 ? tracksNum : null);
                //Album MBID section
                temp.setAlbumMBID(releaseInfo.getReleaseMbid());

                List<String> LinkList = new ArrayList<>();

                AlbumArt lookupAlbumArt = CoverArtArchiveApi.LookupAlbumArt(releaseInfo.getReleaseMbid());

                if (lookupAlbumArt != null && lookupAlbumArt.hasImages())
                {
                    for (Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources.image elem : lookupAlbumArt.getImages())
                        if (elem.hasFront())
                            LinkList.add(elem.getImage());
                    //Album Art section
                    temp.setArtLinks(LinkList);
                }
            }

            if (releaseArtist != null)
            {
                //Artist section
                StringBuilder ArtistNameBuilder = new StringBuilder();
                for (ReleaseArtist elem : releaseArtist)
                    ArtistNameBuilder.append(elem.getName()).append(" ");

                temp.setArtist(ArtistNameBuilder.toString());

                //Artist MBID section
                temp.setArtistMBID(releaseArtist.get(0).getMbid());
            }

            if (tag != null)
            //Tag section
                temp.setTag(tag.getText());

            //Title section
            temp.setTitle(MBRecording.getTitle());

            //Track MBID section
            temp.setTrackMBID(MBRecording.getMbid());
        }

        return temp;
    }

    /**
     * Converting TrackInfo instance that contains track data into ID3V2 data instance.
     *
     * @param LFMTrack TrackInfo instance.
     * @return ID3V2 data instance.
     */
    private static ID3V2 TagFromLastFM(final TrackInfo LFMTrack)
    {
        ID3V2 temp = null;

        if (LFMTrack != null && LFMTrack.hasTrack())
        {
            temp = new ID3V2();
            track lfmInfoTrack = LFMTrack.getTrack();

            //Title section
            if (lfmInfoTrack.hasName())
                temp.setTitle(lfmInfoTrack.getName());

            //Track MBID section
            if (lfmInfoTrack.hasMbid())
                temp.setTrackMBID(lfmInfoTrack.getMbid());

            if (lfmInfoTrack.hasArtist())
            {
                artist lfmInfoTrackArtist = lfmInfoTrack.getArtist();

                //Artist name section
                if (lfmInfoTrackArtist.hasName())
                    temp.setArtist(lfmInfoTrackArtist.getName());

                if (lfmInfoTrackArtist.hasMbid())
                    temp.setArtistMBID(lfmInfoTrackArtist.getMbid());
            }

            if (lfmInfoTrack.hasAlbum())
            {
                album lfmInfoTrackAlbum = lfmInfoTrack.getAlbum();

                //Album MBID section
                if (lfmInfoTrackAlbum.hasMbid())
                    temp.setAlbumMBID(lfmInfoTrackAlbum.getMbid());

                if (lfmInfoTrackAlbum.hasAttribute())
                {
                    attr lfmInfoTrackAlbumAttribute = lfmInfoTrackAlbum.getAttribute();

                    //Track number section
                    if (lfmInfoTrackAlbumAttribute.hasPosition())
                    {
                        int num = Integer.parseInt(lfmInfoTrackAlbumAttribute.getPosition());
                        temp.setNumber(num > 0 ? num : null);
                    }
                }

                //Album title section
                if (lfmInfoTrackAlbum.hasTitle())
                    temp.setAlbum(lfmInfoTrackAlbum.getTitle());

                List<String> LinkList = new ArrayList<>();

                //Album art section 1
                if (lfmInfoTrackAlbum.hasMbid())
                {
                    AlbumArt lookupAlbumArt = CoverArtArchiveApi.LookupAlbumArt(lfmInfoTrackAlbum.getMbid());

                    if (lookupAlbumArt != null && lookupAlbumArt.hasImages())
                        for (Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources.image elem : lookupAlbumArt.getImages())
                            if (elem.hasFront())
                                LinkList.add(elem.getImage());
                }

                //Album art section 2
                if (lfmInfoTrackAlbum.hasImages())
                {
                    List<image> lfmInfoTrackAlbumImages = lfmInfoTrackAlbum.getImages();

                    Extract extract;

                    try
                    {
                        extract = image.extract(lfmInfoTrackAlbumImages);
                        if (extract != null && extract.hasText())
                            LinkList.add(extract.getText());
                    }
                    catch (NoMatchesException e)
                    {
                        if (logger.isWarnEnabled())
                            logger.warn("catch exception {}", e.getMessage());
                    }
                }
                temp.setArtLinks(LinkList);
            }

            //Info section
            if (lfmInfoTrack.hasWiki())
            {
                wiki lfmInfoTrackWiki = lfmInfoTrack.getWiki();

                if (lfmInfoTrackWiki.hasContent())
                    temp.setComment(lfmInfoTrackWiki.getContent());

                if (!lfmInfoTrackWiki.hasContent() && lfmInfoTrackWiki.hasSummary())
                    temp.setComment(lfmInfoTrackWiki.getSummary());
            }

            //Tag section
            if (lfmInfoTrack.hasToptags())
            {
                toptags lfmInfoTrackToptags = lfmInfoTrack.getToptags();
                if (lfmInfoTrackToptags.hasTags())
                    temp.setTag(lfmInfoTrackToptags.getTags().get(0).getName());
            }
        }

        return temp;
    }

    private static ID3V2 BuildTag(
            final TrackInfo LFMTrackInfo,
            final Recording MBTrackInfo)
            throws
            NoMatchesException
    {
        ID3V2 fromLastFM = TagFromLastFM(LFMTrackInfo);
        ID3V2 fromMusicBrainz = TagFromMusicBrainz(MBTrackInfo);
        ID3V2 mergeTags = MergeTags(fromMusicBrainz, fromLastFM);

        if (mergeTags == null)
            throw new NoMatchesException(NO_MATCHES);

        return mergeTags;
    }

    /**
     * Try to build ID3V2 tag data from SimpleInfo with few requests to MusicBrainz and LastFM database.
     *
     * @param track SimpleInfo data.
     * @return ID3V2 tag.
     * @throws NoMatchesException   if MusicBrainz and LastFM DBs don`t contains data about this track.
     * @throws NullPointerException if try to call instance method to non-instanced variable.
     */
    public static ID3V2 BuildTag(@NotNull final SimpleInfo track)
            throws
            NoMatchesException,
            NullPointerException
    {
        logger = LoggerFactory.getLogger(SearchLib.class);
        String trackMBID = track.getTrackMBID();
        //Variable from LFM when request with MBID
        TrackInfo LFMInfoWMBID = null;
        //Variable from LFM when request without MBID
        TrackInfo LFMInfo = null;
        //Variable form MB whe lookup with MBID from SimpleInfo
        Recording MBInfo = null;
        //Variable form MB whe lookup with MBID from variable from LFM when request without MBID
        Recording MBSecInfo = null;

        try
        {
            LFMInfo = LastFMTrackClient.getInfo(null, track.getTitle(), track.getArtist(), null, true);
        }
        catch (IllegalArgumentException e)
        {
            if (logger.isErrorEnabled())
                logger.error("Problems with params...");
        }
        catch (Exception e)
        {
            if (logger.isErrorEnabled())
                logger.error("WTF?", e);
        }
        try
        {
            if (LFMInfo != null && !LFMInfo.hasMessage() && LFMInfo.hasTrack())
            {
                track track1 = LFMInfo.getTrack();
                if (track1.hasMbid())
                {
                    synchronized (MusicBrainzRecordingCache)
                    {
                        Recording CacheRecording = MusicBrainzRecordingCache.get(trackMBID);
                        if (CacheRecording != null)
                        {
                            MBSecInfo = CacheRecording;
                            if (logger.isDebugEnabled())
                                logger.debug("using caching recording {}. Size of recording cache {}", trackMBID, MusicBrainzRecordingCache.size());
                        }
                        else
                        {
                            MILLISECONDS.sleep(MusicBrainzElapse());
                            MBSecInfo = MBClient.lookupRecording(trackMBID);
                            if (MBSecInfo != null && MBSecInfo.getMbid() != null)
                            {
                                MusicBrainzRecordingCache.put(trackMBID, MBSecInfo);
                                if (logger.isDebugEnabled())
                                    logger.debug("using caching recording {}. Size of recording cache {}", trackMBID, MusicBrainzRecordingCache.size());
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            if (logger.isErrorEnabled())
                logger.error("MusicBrainzException at MBID {}", trackMBID, e);
        }

        try
        {
            LFMInfoWMBID = LastFMTrackClient.getInfo(trackMBID, track.getTitle(), track.getArtist(), null, true);
        }
        catch (IllegalArgumentException e)
        {
            if (logger.isErrorEnabled())
                logger.error("Problems with params...");
        }
        //When LastFM and MB have recording with equals mbid in head
        if (MBSecInfo != null || LFMInfoWMBID != null && !LFMInfoWMBID.hasMessage())
        {
            if (logger.isDebugEnabled())
                logger.debug("first scenario");
            return BuildTag(LFMInfoWMBID, MBSecInfo);
        }

        String LFMMBID = null;
        if (LFMInfo != null && LFMInfo.hasTrack())
        {
            track lfmInfoTrack = LFMInfo.getTrack();
            if (lfmInfoTrack.hasMbid())
                LFMMBID = lfmInfoTrack.getMbid();
        }
        if (LFMMBID != null)
        {
            try
            {
                synchronized (MusicBrainzRecordingCache)
                {
                    Recording CacheRecording = MusicBrainzRecordingCache.get(LFMMBID);
                    if (CacheRecording != null)
                    {
                        MBInfo = CacheRecording;
                        if (logger.isDebugEnabled())
                            logger.debug("using caching recording {}. Size of recording cache {}", LFMMBID, MusicBrainzRecordingCache.size());
                    }
                    else
                    {
                        MILLISECONDS.sleep(MusicBrainzElapse());
                        MBInfo = MBClient.lookupRecording(LFMMBID);
                        if (MBInfo != null && MBInfo.getMbid() != null)
                        {
                            MusicBrainzRecordingCache.put(LFMMBID, MBInfo);
                            if (logger.isDebugEnabled())
                                logger.debug("add caching recording {}. Size of recording cache {}", LFMMBID, MusicBrainzRecordingCache.size());
                        }
                    }
                }
            }
            catch (Exception e)
            {
                if (logger.isErrorEnabled())
                    logger.error("MusicBrainzException at MBID {}", trackMBID, e);
            }
        }

        //When LastFM lookup has recording with Artist and track name -> mbid and MB have recording with equals mbid in head
        if (MBInfo != null || LFMInfo != null && !LFMInfo.hasMessage())
        {
            if (logger.isDebugEnabled())
                logger.debug("second scenario");
            return BuildTag(LFMInfo, MBInfo);
        }

        //When have problem with another...
        if (track.hasTrackMBID())
        {
            try
            {
                String mbid = track.getTrackMBID();
                synchronized (MusicBrainzRecordingCache)
                {
                    Recording CacheRecording = MusicBrainzRecordingCache.get(mbid);
                    if (CacheRecording != null)
                    {
                        MBInfo = CacheRecording;
                        if (logger.isDebugEnabled())
                            logger.debug("using caching recording {}. Size of recording cache {}", mbid, MusicBrainzRecordingCache.size());
                    }
                    else
                    {
                        MILLISECONDS.sleep(MusicBrainzElapse());
                        MBInfo = MBClient.lookupRecording(mbid);
                        if (MBInfo != null && MBInfo.getMbid() != null)
                        {
                            MusicBrainzRecordingCache.put(mbid, MBInfo);
                            if (logger.isDebugEnabled())
                                logger.debug("add caching recording {}. Size of recording cache {}", mbid, MusicBrainzRecordingCache.size());
                        }
                    }
                }
                if (MBInfo != null && MBInfo.getMbid() != null)
                {
                    if (logger.isDebugEnabled())
                        logger.debug("third scenario");
                    return BuildTag(null, MBInfo);
                }
            }
            catch (Exception e)
            {
                if (logger.isErrorEnabled())
                    logger.error("MusicBrainzException at MBID {}", trackMBID, e);
            }
        }

        throw new NoMatchesException(NO_MATCHES);
    }
}
