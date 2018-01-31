package Fox.core.lib.services.Common;

import Fox.core.lib.general.DOM.FingerPrint;
import Fox.core.lib.general.DOM.ID3V2;
import Fox.core.lib.general.utils.AcoustIDException;
import Fox.core.lib.general.utils.NoMatchesException;
import Fox.core.lib.services.LastFM.LastFMApi;
import Fox.core.lib.services.acoustid.AcoustIDClient;
import Fox.core.lib.services.acoustid.LookupByFP.sources.ByFingerPrint;
import Fox.core.lib.services.acoustid.LookupByFP.sources.Error;
import org.jetbrains.annotations.NotNull;
import org.musicbrainz.android.api.webservice.MusicBrainzWebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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
        String location = AudioPrint.getLocation();
        ByFingerPrint AIDResp = AIDClient.LookupByFingerPrint(AudioPrint);

        if (AIDResp.hasError() && AIDResp.getErr().hasMessage())
        {
            Error err = AIDResp.getErr();
            throw new AcoustIDException("Error code: " + err.getCode() + " message: "+ err.getMessage());
        }

        if (count <= 0)
            throw new IllegalArgumentException("Impossible to return less that zero or equals zero size of results.");

        List<SimpleInfo> AfterSift = Sifter.Sifting(AIDResp,
                                                    Trust,
                                                    count
                                                   );

        for (SimpleInfo elem : AfterSift)
        {

            List<ID3V2> temp = new ArrayList<>();
            try
            {
            ID3V2 buildTag = BuildTagProcessing.BuildTag(lastFMApi,
                                                         musicBrainzWebClient,
                                                         elem);
            if (buildTag != null)
            temp.add(buildTag);

            Target.put(location,
                       temp);
            }
            catch (NoMatchesException e)
            {
                e.printStackTrace();
                System.out.println("An unxception matches lookup error occur.");
            }
        }
    }

}
