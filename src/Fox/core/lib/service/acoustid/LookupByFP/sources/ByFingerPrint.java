package Fox.core.lib.service.acoustid.LookupByFP.sources;

import java.util.ArrayList;
import java.util.List;

public class ByFingerPrint
{
    private String status;
    private List<Result> result;
    private Error err;

    public ByFingerPrint(){}

    public ByFingerPrint(String status,
                         List<Result> Results,
                         Error err)
    {
        this.result = Result.ResultListCopy(Results);
        this.err = new Error(err);
        this.status = status;
    }

    public ByFingerPrint(ByFingerPrint copy)
    {
        if (copy!=null)
        {
            this.status = copy.status;
            this.err = new Error(copy.err);
            this.result = Result.ResultListCopy(copy.result);
        }
    }

    public static List<ByFingerPrint> ByFingerPrintListCopy(List<ByFingerPrint> copy)
    {
        List<ByFingerPrint> temp = null;

        if (copy!=null)
        {
            temp = new ArrayList<>();

            for (ByFingerPrint elem:copy)
                temp.add(new ByFingerPrint(elem));
        }

        return temp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Error getErr() {
        return new Error(err);
    }

    public void setErr(Error err) {
        this.err = new Error(err);
    }

    public List<Result> getResult() {
        return Result.ResultListCopy(this.result);
    }

    public void setResult(List<Result> result) {
        this.result = Result.ResultListCopy(result);
    }

    public boolean hasError() {
        return err != null;
    }

    public boolean hasResults() {
        return result != null && result.size()>0;
    }

    public boolean hasStatus() {
        return status != null && status.length()>0;
    }
}
