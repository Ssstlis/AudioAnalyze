package Fox.core.lib.service.acoustid;

import org.jetbrains.annotations.NotNull;

public class AcoustIDResponse
{
    private String source;

    public AcoustIDResponse(@NotNull String src) {
        this.source = src;
    }

    public String getSource() {
        return source;
    }

}
