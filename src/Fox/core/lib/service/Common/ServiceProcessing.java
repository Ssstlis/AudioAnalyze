package Fox.core.lib.service.Common;

import Fox.core.lib.general.FingerPrint;
import Fox.core.lib.general.ID3V2;
import Fox.core.lib.service.acoustid.AcoustIDClient;
import Fox.core.lib.service.acoustid.LookupByFP.sources.ByFingerPrint;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceProcessing
{
    private AcoustIDClient AIDClient;

    public ServiceProcessing(@NotNull AcoustIDClient AIDClient)
    {
        this.AIDClient = AIDClient;
    }

    public void Processing(@NotNull FingerPrint AudioPrint,
                           boolean Trust,
                           @NotNull ConcurrentHashMap<String, List<ID3V2>> Target)
    {
        String                      location  = AudioPrint.getLocation();
        ByFingerPrint               AIDResp   = AIDClient.LookupByFingerPrint(AudioPrint);
        HashMap<String, SimpleInfo> AfterSift = new Sifter().Sifting(AIDResp, Trust);

        for(HashMap.Entry entry:AfterSift.entrySet())
        {
            //TODO LASTFM REQUEST
            //TODO COVERART REQUEST
            //TODO BUILD TAGLIST
            //THIS IS TEMPLATE
            List<ID3V2> temp = new ArrayList<>();
            for(int i = 0; i < 2;i++)
                temp.add(new ID3V2(null,null,null));

            Target.put(location, temp);
        }
    }

}
