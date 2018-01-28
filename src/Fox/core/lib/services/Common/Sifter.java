package Fox.core.lib.services.Common;

import Fox.core.lib.services.acoustid.LookupByFP.sources.ByFingerPrint;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class Sifter
{
    public Sifter()
    {
    }

    public HashMap<String, SimpleInfo> Sifting(
            @NotNull ByFingerPrint target,
            boolean trust)
    {
        //TODO SIFTING RESULTS FROM AID
        return new HashMap<>();
    }
}
