package Fox.core.lib.services.AcoustID.LookupByFP.sources;


import java.util.ArrayList;
import java.util.List;

public class Error
{
    private String message;
    private Integer code;

    public Error()
    {
    }

    public Error(
            String mes,
            Integer code)
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

    public Integer getCode()
    {
        return code;
    }

    public void setCode(Integer code)
    {
        this.code = code;
    }

    public boolean hasCode()
    {
        return code != null && code != 0;
    }

    public boolean hasMessage()
    {
        return message != null && message.length() > 0;
    }
}
