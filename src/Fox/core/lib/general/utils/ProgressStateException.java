package Fox.core.lib.general.utils;

public class ProgressStateException extends Exception
{
    public ProgressStateException()
    {
        super();
    }

    public ProgressStateException(String msg)
    {
        super(msg);
    }

    public ProgressStateException(String msg, Throwable cause)
    {
        super(msg, cause);
    }

    public ProgressStateException(Throwable cause)
    {
        super(cause);
    }

    public ProgressStateException(String msg,
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
