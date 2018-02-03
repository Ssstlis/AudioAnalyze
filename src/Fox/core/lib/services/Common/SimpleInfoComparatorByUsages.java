package Fox.core.lib.services.Common;


import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class SimpleInfoComparatorByUsages implements Comparator<SimpleInfo>
{
    @Override
    public int compare(@NotNull SimpleInfo a,
                       @NotNull SimpleInfo b)
    {
        return a.getUsages().compareTo(b.getUsages());
    }
}
