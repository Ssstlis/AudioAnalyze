package Fox.core.lib.general;

import org.jetbrains.annotations.NotNull;

public interface FingerPrintThread {
    void getFingerPrint(
            @NotNull String location,
            @NotNull FingerPrint target) throws Exception;
}
