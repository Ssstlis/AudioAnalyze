package Fox.core.lib.service.acousticid;

import org.jetbrains.annotations.NotNull;

public class AcousticIDResponse {
    private String source;

    public AcousticIDResponse(@NotNull String src) {
        this.source = src;
    }

    public String getSource() {
        return source;
    }

}
