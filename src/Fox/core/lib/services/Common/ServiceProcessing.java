package Fox.core.lib.services.Common;

import Fox.core.lib.general.DOM.FingerPrint;
import Fox.core.lib.general.DOM.ID3V2;
import Fox.core.lib.general.utils.AcoustIDException;
import Fox.core.lib.general.utils.NoMatchesException;
import Fox.core.lib.services.LastFM.LastFMApi;
import Fox.core.lib.services.acoustid.AcoustIDClient;
import Fox.core.lib.services.acoustid.LookupByFP.sources.ByFingerPrint;
import Fox.core.lib.services.acoustid.LookupByFP.sources.Error;
import Fox.test.testing;
import org.jetbrains.annotations.NotNull;
import org.musicbrainz.android.api.webservice.MusicBrainzWebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static Fox.core.lib.services.Common.Sifter.Sifting;
import static Fox.core.main.AudioAnalyzeLibrary.NO_COUNT;
import static Fox.core.main.AudioAnalyzeLibrary.logger;
import static java.util.logging.Level.SEVERE;

public class ServiceProcessing
{

    public ServiceProcessing()
    {

    }

    public static void Processing(
            @NotNull AcoustIDClient AIDClient,
            @NotNull LastFMApi lastFMApi,
            @NotNull MusicBrainzWebClient musicBrainzWebClient,
            @NotNull FingerPrint AudioPrint,
            boolean Trust,
            @NotNull ConcurrentHashMap<String, List<ID3V2>> Target,
            int count)
            throws
            AcoustIDException,
            NoMatchesException
    {
        if (count <= 0)
        {
            logger.log(SEVERE, NO_COUNT);
            throw new IllegalArgumentException("Impossible to return less that zero or equals zero size of results.");
        }

        String location = AudioPrint.getLocation();
        ByFingerPrint AIDResp = AIDClient.LookupByFingerPrint(AudioPrint);


        if (AIDResp == null)
        {
            AcoustIDException acoustID_lookup_error = new AcoustIDException("Lookup error.");
            logger.log(SEVERE, "", acoustID_lookup_error);
            throw acoustID_lookup_error;
        }

        if (AIDResp.hasError() && AIDResp.getErr().hasMessage())
        {
            Error err = AIDResp.getErr();
            logger.log(SEVERE, "AcoustID error occure");
            throw new AcoustIDException("Error code: " + err.getCode() + " message: " + err.getMessage());
        }

        testing.dbg2.put(location, AIDResp);

        List<SimpleInfo> AfterSift = Sifting(AudioPrint,
                AIDResp,
                count,
                Trust);

        testing.dbg5.put(location, AfterSift);

        for (SimpleInfo elem : AfterSift)
        {

            List<ID3V2> temp = new ArrayList<>();
            try
            {
                ID3V2 buildTag = BuildTagProcessing.BuildTag(
                        lastFMApi,
                        musicBrainzWebClient,
                        elem);

                if (buildTag != null)
                    temp.add(buildTag);

                Target.put(location,
                        temp);

            } catch (NoMatchesException e)
            {
                logger.log(SEVERE, "An unexpected matches lookup error occur.", e);
            }
        }
    }
}
