package Fox.core.lib.general.utils;

public class Exceptions
{
    public static class AcoustIDException extends Exception
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

    public static class FingerPrintProcessingException extends Exception
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

    public static class NoAccessingFilesException extends Exception
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

    public static class NoBuildException extends Exception
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

    public static class NoMatchesException
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

    public static class ProgressStateException extends Exception
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
}
