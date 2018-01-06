package Fox.core.lib.service.acousticid.LookupByFP.sources;


import org.jetbrains.annotations.NotNull;

public class Error {
    private String message;
    private int code;

    public Error() {
    }

    public Error(@NotNull String mes, int code) {
        this.code = code;
        this.message = mes;
    }

    @NotNull
    public String getMessage() {
        return message;
    }

    public void setMessage(@NotNull String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean hasCode() {
        return code != 0;
    }

    public boolean hasMessage() {
        return message != null && message.length()>0;
    }
}
