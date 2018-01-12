package Fox.core.lib.service.acousticid.LookupByFP.sources;

public class Artist {
    private String id;
    private String name;

    public Artist() {
    }

    public Artist(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasId() {
        return id != null && id.length()>0;
    }

    public boolean hasName() {
        return name != null && name.length()>0;
    }
}
