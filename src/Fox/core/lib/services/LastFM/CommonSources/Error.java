package Fox.core.lib.services.LastFM.CommonSources;

public class Error
{
    private Integer error;
    private String message;

    public Error()
    {

    }

    public Error(
            Integer error,
            String message)
    {
        this.message = message;
        this.error = error;
    }

    public Error(Error copy)
    {
        if (copy != null)
        {
            this.error = copy.error;
            this.message = copy.message;
        }
    }

    public Integer getError()
    {
        return error;
    }

    public void setError(Integer error)
    {
        this.error = error;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public boolean hasMessage()
    {
        return (message != null && !message.isEmpty());
    }

    public boolean hasError()
    {
        return error != null;
    }
}
