package Fox.core.lib.service.acousticid;

import org.jetbrains.annotations.NotNull;

public class AcousticIDResponse {
    private String source;
    private AcousticIDRequestConfig config;

    public AcousticIDResponse(@NotNull String src,
                              @NotNull AcousticIDRequestConfig cfg) {
        this.source = src;
        this.config = cfg;
    }

    public String getSource() {
        return source;
    }

    public AcousticIDRequestConfig getConfig() {
        return config;
    }
}
