package core.lib.service.acousticid.LookupByFP;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ByFingerPrint
{
    private String status;
    private List<Result> Result;
    private Error err;

    public ByFingerPrint()
    {

    }

    @NotNull
    public String getStatus() {
        return status;
    }

    public ByFingerPrint setStatus(@NotNull String status) {
        this.status = status;
        return this;
    }

    @NotNull
    public Error getErr() {
        return err;
    }

    public ByFingerPrint setErr(@NotNull Error err) {
        this.err = err;
        return this;
    }

    @NotNull
    public List<Result> getResult() {
        return Result;
    }

    public void setResult(@NotNull List<Result> result)
    {
        Result = result;
    }

    public boolean hasError()
    {
        return err!=null;
    }
}
