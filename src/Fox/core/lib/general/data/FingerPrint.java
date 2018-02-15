package Fox.core.lib.general.data;

import org.jetbrains.annotations.NotNull;

public class FingerPrint
{
    private String print;
    private String duration;
    private String location;

    public FingerPrint(
            @NotNull String print,
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

    public FingerPrint(FingerPrint copy)
    {
        if (copy != null)
        {
            this.duration = copy.duration;
            this.location = copy.location;
            this.print = copy.print;
        }
    }

    public String getPrint()
    {
        return print;
    }

    public void setPrint(@NotNull String print)
    {
        this.print = print;
    }

    public String getDuration()
    {
        return duration;
    }

    public void setDuration(@NotNull String duration)
    {
        this.duration = duration;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(@NotNull String location)
    {
        this.location = location;
    }

    @Override
    public String toString()
    {
        String res = "";
        res = res.concat(location);
        res = res.concat("\n");
        res = res.concat(duration);
        res = res.concat("\n");
        res = res.concat(print);
        return res;
    }

    public boolean hasDuration()
    {
        return (duration != null && !duration.isEmpty());
    }

    public boolean hasLocation()
    {
        return (location != null && !location.isEmpty());
    }

    public boolean hasPrint()
    {
        return (print != null && !print.isEmpty());
    }
}
