package Fox.core.lib.service.acousticid.LookupByFP.sources;

import org.jetbrains.annotations.NotNull;

public class Artist {
    private String id;
    private String name;

    public Artist() {
    }

    public Artist(@NotNull String id, @NotNull String name) {
        this.id = id;
        this.name = name;
    }

    @NotNull
    public String getId() {
        return this.id;
    }

    public void setId(@NotNull String id) {
        this.id = id;
    }

    @NotNull
    public String getName() {
        return this.name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public boolean hasId() {
        return id != null && id.length()>0;
    }

    public boolean hasName() {
        return name != null && name.length()>0;
    }
}
