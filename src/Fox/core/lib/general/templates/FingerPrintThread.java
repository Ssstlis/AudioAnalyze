package Fox.core.lib.general.templates;

import Fox.core.lib.general.data.FingerPrint;
import Fox.core.lib.general.utils.FingerPrintProcessingException;
import org.jetbrains.annotations.NotNull;

public interface FingerPrintThread
{
    FingerPrint getFingerPrint(
            @NotNull String location)
            throws
            FingerPrintProcessingException;
}
