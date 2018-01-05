package core.lib;

import org.jetbrains.annotations.NotNull;

public class FingerPrint
{
    private String print, duration, location;

    public FingerPrint(@NotNull String print,
                       @NotNull String duration,
                       @NotNull String location)
    {
        this.print = print;
        this.duration = duration;
        this.location = location;
    }
    public FingerPrint()
    {
        this.duration = "";
        this.print = "";
        this.location = "";

    }

    public String getPrint() {
        return print;
    }

    public String getDuration() {
        return duration;
    }

    public String getLocation() {
        return location;
    }

    public void setPrint(@NotNull String print) {
        this.print = print;
    }

    public void setDuration(@NotNull String duration) {
        this.duration = duration;
    }

    public void setLocation(@NotNull String location) {
        this.location = location;
    }

    public void rebuild(@NotNull FingerPrint copy)
    {
        this.duration = copy.duration;
        this.location = copy.location;
        this.print = copy.print;
    }

    @Override
    public String toString() {
        String res = "";
        res = res.concat(location);
        res = res.concat("\n");
        res = res.concat(duration);
        res = res.concat("\n");
        res = res.concat(print);
        return res;
    }
}
