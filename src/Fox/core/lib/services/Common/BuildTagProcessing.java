package Fox.core.lib.services.Common;

import Fox.core.lib.general.DOM.Art;
import Fox.core.lib.general.DOM.Extract;
import Fox.core.lib.general.DOM.ID3V2;
import Fox.core.lib.general.utils.NoMatchesException;
import Fox.core.lib.general.utils.target;
import Fox.core.lib.services.CoverArtArchive.CoverArtArchiveClient;
import Fox.core.lib.services.CoverArtArchive.LookupAlbumArt.sources.AlbumArt;
import Fox.core.lib.services.LastFM.Album.getInfo.sources.AlbumInfo;
import Fox.core.lib.services.LastFM.CommonSources.*;
import Fox.core.lib.services.LastFM.LastFMClient;
import Fox.core.lib.services.LastFM.Track.getInfo.sources.TrackInfo;
import Fox.core.lib.services.LastFM.Track.getInfo.sources.album;
import Fox.core.lib.services.LastFM.Track.getInfo.sources.artist;
import Fox.core.lib.services.LastFM.Track.getInfo.sources.track;
import org.jetbrains.annotations.NotNull;
import org.musicbrainz.android.api.data.Recording;
import org.musicbrainz.android.api.data.Release;
import org.musicbrainz.android.api.webservice.MusicBrainzWebClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BuildTagProcessing
{
    private LastFMClient lastFMClient;
    private MusicBrainzWebClient MBClient;

    public BuildTagProcessing(LastFMClient client,
                              MusicBrainzWebClient MBClient)
    {
        if (client == null)
            lastFMClient = new LastFMClient();
        else
            lastFMClient = client;

        if (MBClient == null)
            this.MBClient = new MusicBrainzWebClient("AudioAnalyzeApp");
        else
            this.MBClient = MBClient;
    }

    public ID3V2 BuildTag(@NotNull SimpleInfo track)
            throws
            NoMatchesException
    {
        TrackInfo LFMInfoWMBID, LFMInfo;
        Recording MBInfo = null, MBSecInfo = null;
        ID3V2 temp = null;

        LFMInfoWMBID = lastFMClient.Track.getInfo(track.getMBID(),
                                                   track.getTitle(),
                                                   track.getArtist(),
                                                   null,
                                                   true
                                                  );
        LFMInfo = lastFMClient.Track.getInfo(null,
                                                    track.getTitle(),
                                                    track.getArtist(),
                                                    null,
                                                    true);
        try
        {
            MBInfo = MBClient.lookupRecording(track.getMBID());
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
            e.printStackTrace();
            System.out.println("MusicBrainzExc at MBID " + track.getMBID());
        }

        //When LastFM and MB have recording with equals mbid in head
        if (MBInfo != null || LFMInfoWMBID != null && !LFMInfoWMBID.hasMessage())
        {
            temp = new ID3V2();

            if (LFMInfoWMBID.hasTrack())
            {
                Fox.core.lib.services.LastFM.Track.getInfo.sources.track lfmInfoTrack = LFMInfoWMBID.getTrack();

                if (lfmInfoTrack.hasName())
                    temp.setTitle(lfmInfoTrack.getName());
                else
                    if (MBInfo != null)
                        temp.setTitle(MBInfo.getTitle());

                if (lfmInfoTrack.hasArtist())
                {
                    artist lfmInfoTrackArtist = lfmInfoTrack.getArtist();
                    if (lfmInfoTrackArtist.hasName())
                        temp.setArtist(lfmInfoTrackArtist.getName());
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

                    if (lfmInfoTrackAlbum.hasTitle())
                        temp.setAlbum(lfmInfoTrackAlbum.getTitle());

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
                                temp.setYear(lookupReleaseDate);
                            else
                                throw new IOException();
                        }
                        catch (IOException e)
                        {
                            AlbumInfo albumInfo = lastFMClient.Album.getInfo(lfmInfoTrackAlbum.getMbid(),
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
                        }

                        AlbumArt lookupAlbumArt = new CoverArtArchiveClient().LookupAlbumArt(lfmInfoTrackAlbum.getMbid());

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

                    temp.setGenre(lfmInfoTrackToptags.getTags().get(0).getName());
                }
            }

            return temp;
        }

        //When LastFM lookup has recording with Artist and track name -> mbid and MB have recording with equals mbid in head
        if (MBSecInfo != null || LFMInfo != null && !LFMInfo.hasMessage())
        {
            temp = new ID3V2();
            return temp;
        }

        throw new NoMatchesException();
    }
}
