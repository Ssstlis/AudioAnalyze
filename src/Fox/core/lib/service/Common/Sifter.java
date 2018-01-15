package Fox.core.lib.service.Common;

import Fox.core.lib.service.acoustid.LookupByFP.sources.ByFingerPrint;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class Sifter
{
    public Sifter(){}

    public HashMap<String, SimpleInfo> Sifting(@NotNull ByFingerPrint target,
                                               boolean trust)
    {
        //TODO SIFTING RESULTS FROM AID
        return new HashMap<>();
    }
}
