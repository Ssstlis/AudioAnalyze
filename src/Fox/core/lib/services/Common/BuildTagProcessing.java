package Fox.core.lib.services.Common;

import Fox.core.lib.general.DOM.Extract;
import Fox.core.lib.general.DOM.ID3V2;
import Fox.core.lib.general.utils.NoMatchesException;
import Fox.core.lib.services.CoverArtArchive.CoverArtArchiveClient;
import Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources.AlbumArt;
import Fox.core.lib.services.LastFM.Album.getInfo.sources.AlbumInfo;
import Fox.core.lib.services.LastFM.CommonSources.attr;
import Fox.core.lib.services.LastFM.CommonSources.image;
import Fox.core.lib.services.LastFM.CommonSources.toptags;
import Fox.core.lib.services.LastFM.CommonSources.wiki;
import Fox.core.lib.services.LastFM.LastFMApi;
import Fox.core.lib.services.LastFM.Track.getInfo.sources.TrackInfo;
import Fox.core.lib.services.LastFM.Track.getInfo.sources.album;
import Fox.core.lib.services.LastFM.Track.getInfo.sources.artist;
import Fox.core.lib.services.LastFM.Track.getInfo.sources.track;
import org.jetbrains.annotations.NotNull;
import org.musicbrainz.android.api.data.*;
import org.musicbrainz.android.api.webservice.MusicBrainzWebClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import static Fox.core.main.AudioAnalyzeLibrary.logger;
import static java.util.logging.Level.INFO;

public class BuildTagProcessing
{
    private static MusicBrainzWebClient MBClient;
    private static LastFMApi lastFMApi;

    public BuildTagProcessing()
    {
    }

    private static ID3V2 BuildTag(
            TrackInfo LFMTrackInfo,
            Recording MBTrackInfo)
            throws NoMatchesException
    {

        if (LFMTrackInfo.hasTrack())
        {
            ID3V2 temp = new ID3V2();
            track lfmInfoTrack = LFMTrackInfo.getTrack();
            ReleaseInfo mbInfoRelease = null;
            ReleaseArtist releaseArtist = null;

            if (MBTrackInfo != null)
            {
                ArrayList<ReleaseInfo> mbTrackInfoReleases = MBTrackInfo.getReleases();

                if (mbTrackInfoReleases!=null && !mbTrackInfoReleases.isEmpty())
                    mbInfoRelease = MBTrackInfo.getReleases().get(0);

                ArrayList<ReleaseArtist> mbTrackInfoArtists = MBTrackInfo.getArtists();

                if (mbTrackInfoArtists !=null && !mbTrackInfoArtists.isEmpty())
                    releaseArtist = MBTrackInfo.getArtists().get(0);
            }

            if (lfmInfoTrack.hasName())
                temp.setTitle(lfmInfoTrack.getName());
            else
                if (MBTrackInfo != null)
                    temp.setTitle(MBTrackInfo.getTitle());

            if (lfmInfoTrack.hasArtist())
            {
                artist lfmInfoTrackArtist = lfmInfoTrack.getArtist();
                if (lfmInfoTrackArtist.hasName())
                    temp.setArtist(lfmInfoTrackArtist.getName());
                else if (releaseArtist != null)
                    temp.setArtist(releaseArtist.getName());
            }

            if (lfmInfoTrack.hasAlbum())
            {
                album lfmInfoTrackAlbum = lfmInfoTrack.getAlbum();

                if (lfmInfoTrackAlbum.hasAttribute())
                {
                    attr lfmInfoTrackAlbumAttribute = lfmInfoTrackAlbum.getAttribute();

                    if (lfmInfoTrackAlbumAttribute.hasPosition())
                    {
                        temp.setNumber(Integer.parseInt(lfmInfoTrackAlbumAttribute.getPosition()));
                    }
                }
                else if (mbInfoRelease != null)
                    temp.setNumber(mbInfoRelease.getTracksNum());

                if (lfmInfoTrackAlbum.hasTitle())
                    temp.setAlbum(lfmInfoTrackAlbum.getTitle());
                else if (mbInfoRelease != null)
                    temp.setAlbum(mbInfoRelease.getTitle());

                List<String> LinkList = new ArrayList<>();

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
                        e.printStackTrace();
                    }
                }

                if (lfmInfoTrackAlbum.hasMbid())
                {

                    try
                    {
                        Release lookupRelease = MBClient.lookupRelease(lfmInfoTrackAlbum.getMbid());
                        String lookupReleaseDate = lookupRelease.getDate();
                        if (lookupReleaseDate != null && !lookupReleaseDate.isEmpty())
                        {
                            int indexOf = lookupReleaseDate.indexOf("-");

                            if (indexOf >= 0)
                                lookupReleaseDate = lookupReleaseDate.substring(0,indexOf);

                            temp.setYear(lookupReleaseDate);
                        }
                        else
                            throw new IOException();
                    }
                    catch (IOException e)
                    {
                        AlbumInfo albumInfo = lastFMApi.Album.getInfo(lfmInfoTrackAlbum.getMbid(),
                                                                      lfmInfoTrackAlbum.getArtist(),
                                                                      lfmInfoTrackAlbum.getTitle(),
                                                                      null,
                                                                      null,
                                                                      true);
                        if (albumInfo.hasAlbum())
                        {
                            Fox.core.lib.services.LastFM.Album.getInfo.sources.album infoAlbum = albumInfo.getAlbum();

                            if (infoAlbum.hasWiki())
                            {
                                wiki infoAlbumWiki = infoAlbum.getWiki();

                                if (infoAlbumWiki.hasPublished())
                                    try
                                    {
                                        String infoAlbumWikiPublished = infoAlbumWiki.getPublished();
                                        int u = infoAlbumWikiPublished.lastIndexOf(',');
                                        temp.setYear(infoAlbumWikiPublished.substring(u - 4, u));
                                    }
                                    catch (Exception e1)
                                    {
                                        if (lfmInfoTrack.hasWiki())
                                        {
                                            wiki lfmInfoTrackWiki = lfmInfoTrack.getWiki();

                                            try
                                            {
                                                String infoAlbumWikiPublished1 = lfmInfoTrackWiki.getPublished();
                                                int u = infoAlbumWikiPublished1.lastIndexOf(',');
                                                temp.setYear(infoAlbumWikiPublished1.substring(u - 4, u));
                                            }
                                            catch (Exception e2)
                                            {
                                                temp.setYear("");
                                            }
                                        }
                                    }
                            }
                        }
                        else
                        {
                            try
                            {
                                if (lfmInfoTrack.hasWiki())
                                {
                                    wiki lfmInfoTrackWiki = lfmInfoTrack.getWiki();
                                    if (lfmInfoTrackWiki.hasPublished())
                                    {
                                        String lfmInfoTrackWikiPublished = lfmInfoTrackWiki.getPublished();
                                        int u = lfmInfoTrackWikiPublished.lastIndexOf(',');
                                        temp.setYear(lfmInfoTrackWikiPublished.substring(u - 4,
                                                                                         u));
                                    }

                                }
                            }
                            catch (Exception e2)
                            {
                                temp.setYear("");
                            }
                        }
                    }

                    AlbumArt lookupAlbumArt = CoverArtArchiveClient.LookupAlbumArt(lfmInfoTrackAlbum.getMbid());

                    if (lookupAlbumArt != null && lookupAlbumArt.hasImages())
                    {
                        for (Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources.image elem : lookupAlbumArt.getImages())
                        {
                            if (elem.hasFront())
                            {
                                LinkList.add(elem.getImage());
                            }
                        }
                    }
                }

                temp.setArtLinks(LinkList);
            }

            if (lfmInfoTrack.hasWiki())
            {
                wiki lfmInfoTrackWiki = lfmInfoTrack.getWiki();

                if (lfmInfoTrackWiki.hasContent())
                    temp.setComment(lfmInfoTrackWiki.getContent());

                if (!lfmInfoTrackWiki.hasContent() && lfmInfoTrackWiki.hasSummary())
                    temp.setComment(lfmInfoTrackWiki.getSummary());
            }

            if (lfmInfoTrack.hasToptags())
            {
                toptags lfmInfoTrackToptags = lfmInfoTrack.getToptags();
                if (lfmInfoTrackToptags.hasTags())
                    temp.setGenre(lfmInfoTrackToptags.getTags().get(0).getName());
                else if (MBTrackInfo != null)
                {
                    LinkedList<Tag> tags = MBTrackInfo.getTags();
                    if (tags != null && !tags.isEmpty())
                    {

                        temp.setGenre(tags.get(0)
                                          .getText());
                    }
                }
            }
            return temp;
        }
        else if (MBTrackInfo != null)
        {
            ID3V2 temp = new ID3V2();

            ReleaseInfo releaseInfo = null;
            ReleaseArtist releaseArtist = null;
            Tag tag = null;

            ArrayList<ReleaseInfo> releases = MBTrackInfo.getReleases();
            if (!releases.isEmpty())
                releaseInfo = releases.get(0);

            ArrayList<ReleaseArtist> artists = MBTrackInfo.getArtists();
            if (!artists.isEmpty())
                releaseArtist = artists.get(0);


            LinkedList<Tag> tags = MBTrackInfo.getTags();
            if (!tags.isEmpty())
                tag = tags.get(0);

            if (releaseInfo != null)
            {
                temp.setYear(releaseInfo.getDate());
                temp.setAlbum(releaseInfo.getTitle());
                temp.setNumber(releaseInfo.getTracksNum());

                List<String> LinkList = new ArrayList<>();

                AlbumArt lookupAlbumArt = CoverArtArchiveClient.LookupAlbumArt(releaseInfo.getReleaseMbid());

                if (lookupAlbumArt != null && lookupAlbumArt.hasImages())
                {
                    for (Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources.image elem : lookupAlbumArt.getImages())
                    {
                        if (elem.hasFront())
                        {
                            LinkList.add(elem.getImage());
                        }
                    }
                    temp.setArtLinks(LinkList);
                }
            }

            if (releaseArtist != null)
            {
                temp.setArtist(releaseArtist.getName());
            }

            if (tag != null)
                temp.setGenre(tag.getText());

            temp.setTitle(MBTrackInfo.getTitle());
            return temp;
        }
        throw new NoMatchesException("No matches.");
    }

    public static ID3V2 BuildTag(@NotNull LastFMApi lastFMApi,
                          @NotNull MusicBrainzWebClient MBClient,
                          @NotNull SimpleInfo track)
            throws
            NoMatchesException,
            NullPointerException
    {
        BuildTagProcessing.MBClient = MBClient;
        BuildTagProcessing.lastFMApi = lastFMApi;

        //Variable from LFM when request with MBID
        TrackInfo LFMInfoWMBID;
        //Variable from LFM when request without MBID
        TrackInfo LFMInfo;
        //Variable form MB whe lookup with MBID from SimpleInfo
        Recording MBInfo = null;
        //Variable form MB whe lookup with MBID from variable from LFM when request without MBID
        Recording MBSecInfo = null;

        LFMInfoWMBID = lastFMApi.Track.getInfo(track.getMBID(),
                                               track.getTitle(),
                                               track.getArtist(),
                                               null,
                                               true
                                              );

        LFMInfo = lastFMApi.Track.getInfo(null,
                                          track.getTitle(),
                                          track.getArtist(),
                                          null,
                                          true);
        try
        {
            MBInfo = MBClient.lookupRecording(track.getMBID());

        }
        catch (IOException e)
        {
            logger.log(Level.SEVERE, "MusicBrainzException at MBID " + track.getMBID(), e);
        }

        try
        {
            if (LFMInfo != null && !LFMInfo.hasMessage() && LFMInfo.hasTrack())
            {
                track track1 = LFMInfo.getTrack();
                if (track1.hasMbid())
                    MBSecInfo = MBClient.lookupRecording(LFMInfo.getTrack()
                                                                .getMbid());
            }
        }
        catch (IOException e)
        {
            logger.log(Level.SEVERE, "MusicBrainzException at MBID " + track.getMBID(), e);
        }

        //When LastFM and MB have recording with equals mbid in head
        if (MBInfo != null || LFMInfoWMBID != null && !LFMInfoWMBID.hasMessage())
        {
            logger.log(INFO, "first scenario");
            return BuildTag(LFMInfoWMBID, MBInfo);
        }

        //When LastFM lookup has recording with Artist and track name -> mbid and MB have recording with equals mbid in head
        if (MBSecInfo != null || LFMInfo != null && !LFMInfo.hasMessage())
        {
            logger.log(INFO, "second scenario");
            return BuildTag(LFMInfo, MBSecInfo);
        }

        throw new NoMatchesException("No matches.");
    }
}
