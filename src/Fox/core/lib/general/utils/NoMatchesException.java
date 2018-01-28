package Fox.core.lib.general.utils;

public class NoMatchesException
        extends Exception
{
    public NoMatchesException()
    {
        super();
    }

    public NoMatchesException(String msg)
    {
        super(msg);
    }

    public NoMatchesException(String msg, Throwable cause)
    {
        super(msg, cause);
    }

    public NoMatchesException(Throwable cause)
    {
        super(cause);
    }

    public NoMatchesException(String msg,
                              Throwable cause,
                              boolean enableSuppression,
                              boolean writableStackTrace
                              )
    {
        super(msg,
              cause,
              enableSuppression,
              writableStackTrace
             );
    }
}
