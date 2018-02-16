package Fox.core.lib.general.templates;

import Fox.core.lib.general.data.FingerPrint;
import Fox.core.lib.general.utils.FingerPrintProcessingException;
import org.jetbrains.annotations.NotNull;

/**
 * The interface provides a method to create a music track imprint.
 * You must use the chromaprint library to create and return an instance of the FingerPrint class.
 */
public interface FingerPrintThread
{
    /** You must use the chromaprint library to create and return an instance of FingerPrint class.
     * @param location file path
     * @return FingerPrint instance
     * @throws FingerPrintProcessingException you choose. Advice: throw it if chromaprint process exit with error.
     */
    FingerPrint getFingerPrint(@NotNull String location)
            throws FingerPrintProcessingException;
}
