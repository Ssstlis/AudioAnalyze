package Fox.core.lib.services.Common;

import Fox.core.lib.general.DOM.FingerPrint;
import Fox.core.lib.general.DOM.ID3V2;
import Fox.core.lib.general.utils.AcoustIDException;
import Fox.core.lib.general.utils.NoMatchesException;
import Fox.core.lib.services.LastFM.LastFMClient;
import Fox.core.lib.services.acoustid.AcoustIDClient;
import Fox.core.lib.services.acoustid.LookupByFP.sources.ByFingerPrint;
import Fox.core.lib.services.acoustid.LookupByFP.sources.Error;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceProcessing
{
    private AcoustIDClient AIDClient;
    private LastFMClient lastFMClient;

    public ServiceProcessing(@NotNull AcoustIDClient AIDClient,
                             @NotNull LastFMClient lastFMClient)
    {
        this.AIDClient = AIDClient;
    }

    public void Processing(
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

        for (SimpleInfo entry : AfterSift)
        {
            //TODO LASTFM REQUEST
            //TODO COVERART REQUEST
            //TODO BUILD TAGLIST
            //THIS IS TEMPLATE
            List<ID3V2> temp = new ArrayList<>();
            for (int i = 0; i < 2; i++)
            {
                temp.add(new ID3V2(null,
                                   null,
                                   null
                ));
            }

            Target.put(location,
                       temp
                      );
        }
    }

}
