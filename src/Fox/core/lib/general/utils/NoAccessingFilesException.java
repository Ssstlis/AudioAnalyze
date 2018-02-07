package Fox.core.lib.general.utils;

public class NoAccessingFilesException extends Exception
{
    public NoAccessingFilesException()
    {
        super();
    }

    public NoAccessingFilesException(String msg)
    {
        super(msg);
    }

    public NoAccessingFilesException(String msg, Throwable cause)
    {
        super(msg, cause);
    }

    public NoAccessingFilesException(Throwable cause)
    {
        super(cause);
    }

    public NoAccessingFilesException(String msg,
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
