package Fox.core.lib.general.templates;

import Fox.core.lib.general.data.FingerPrint;
import Fox.core.lib.general.utils.FingerPrintProcessingException;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 *
 */
public interface FingerPrintThread
{
    FingerPrint getFingerPrint(@NotNull String location)
            throws FingerPrintProcessingException;
    List<FingerPrint> getFingerPrint(@NotNull final List<String> location)
            throws FingerPrintProcessingException;
}
