package Fox.core.lib.general.utils;

public class FingerPrintProcessingException extends Exception
{
    public FingerPrintProcessingException()
    {
        super();
    }

    public FingerPrintProcessingException(String msg)
    {
        super(msg);
    }

    public FingerPrintProcessingException(String msg, Throwable cause)
    {
        super(msg, cause);
    }

    public FingerPrintProcessingException(Throwable cause)
    {
        super(cause);
    }

    public FingerPrintProcessingException(String msg,
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
