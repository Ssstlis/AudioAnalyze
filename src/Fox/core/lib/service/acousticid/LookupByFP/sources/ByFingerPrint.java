package Fox.core.lib.service.acousticid.LookupByFP.sources;

import java.util.List;

public class ByFingerPrint {
    private String status;
    private List<Result> Result;
    private Error err;

    public ByFingerPrint() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Error getErr() {
        return err;
    }

    public void setErr(Error err) {
        this.err = err;
    }

    public List<Result> getResult() {
        return Result;
    }

    public void setResult(List<Result> result) {
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
