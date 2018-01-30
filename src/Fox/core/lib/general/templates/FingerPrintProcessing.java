package Fox.core.lib.general.templates;

import Fox.core.lib.general.DOM.FingerPrint;
import Fox.core.lib.general.utils.FingerPrintProcessingException;
import org.jetbrains.annotations.NotNull;

public interface FingerPrintProcessing
{

    @NotNull
    FingerPrint getFingerPrint(@NotNull String location)
            throws FingerPrintProcessingException;
}
