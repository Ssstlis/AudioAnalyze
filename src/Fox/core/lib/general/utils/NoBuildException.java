package Fox.core.lib.general.utils;

public class NoBuildException extends Exception
{
    public NoBuildException()
    {
        super();
    }

    public NoBuildException(String msg)
    {
        super(msg);
    }

    public NoBuildException(String msg, Throwable cause)
    {
        super(msg, cause);
    }

    public NoBuildException(Throwable cause)
    {
        super(cause);
    }

    public NoBuildException(String msg,
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
