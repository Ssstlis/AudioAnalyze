package Fox.core.lib.general.utils;

public class AcoustIDException extends Exception
{
    public AcoustIDException()
    {
        super("An AcoustID result occur.");
    }

    public AcoustIDException(int code, String msg)
    {
        super("Error code: " + code + " message: "+ msg);
    }

    public AcoustIDException(String msg)
    {
        super("An AcoustID result occur. "+msg);
    }

    public AcoustIDException(String msg, Throwable cause)
    {
        super("An AcoustID result occur. " +msg, cause);
    }

    public AcoustIDException(Throwable cause)
    {
        super("An AcoustID result occur.",cause);
    }

    public AcoustIDException(String msg,
                            Throwable cause,
                            boolean enableSuppression,
                            boolean writableStackTrace
                           )
    {
        super("An AcoustID result occur." + msg,
              cause,
              enableSuppression,
              writableStackTrace
             );
    }
}
