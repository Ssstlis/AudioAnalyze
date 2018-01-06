package Fox.core.lib.service.acousticid.LookupByFP.sources;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ByFingerPrint {
    private String status;
    private List<Fox.core.lib.service.acousticid.LookupByFP.sources.Result> Result;
    private Error err;

    public ByFingerPrint() {

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

    public void setResult(@NotNull List<Result> result) {
        Result = result;
    }

    public boolean hasError() {
        return err != null;
    }

    public boolean hasResults() {
        return Result != null && Result.size()>0;
    }

    public boolean hasStatus() {
        return status != null && status.length()>0;
    }
}
