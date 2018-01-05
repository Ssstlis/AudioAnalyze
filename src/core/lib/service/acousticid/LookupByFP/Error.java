package core.lib.service.acousticid.LookupByFP;


import org.jetbrains.annotations.NotNull;

public class Error
{
    private String message;
    private int code;

    public Error(){}

    public Error(@NotNull String mes, int code)
    {
        this.code = code;
        this.message = mes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
