package Fox.core.lib.services.acoustid.LookupByFP;

import Fox.core.lib.services.acoustid.LookupByFP.sources.Recording;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class RecordingComparator implements Comparator<Recording>
{
    @Override
    public int compare(@NotNull Recording a,
                       @NotNull Recording b)
    {
        return a.getSources().compareTo(b.getSources());
    }
}
