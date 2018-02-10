package Fox.core.lib.services.AcoustID.LookupByFP.sources;


import java.util.ArrayList;
import java.util.List;

public class Error
{
    private String message;
    private int code;

    public Error()
    {
    }

    public Error(
            String mes,
            int code)
    {
        this.code = code;
        this.message = mes;
    }

    public Error(Error copy)
    {
        if (copy != null)
        {
            this.message = copy.message;
            this.code = copy.code;
        }
    }

    public static List<Error> ErrorListCopy(List<Error> copy)
    {
        List<Error> temp = null;

        if (copy != null)
        {
            temp = new ArrayList<>();

            for (Error elem : copy)
            {
                temp.add(new Error(elem));
            }
        }

        return temp;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public boolean hasCode()
    {
        return code != 0;
    }

    public boolean hasMessage()
    {
        return message != null && message.length() > 0;
    }
}
