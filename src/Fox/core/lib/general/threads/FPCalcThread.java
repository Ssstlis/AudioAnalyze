package Fox.core.lib.general.threads;

import Fox.core.lib.general.data.FingerPrint;
import Fox.core.lib.general.templates.FingerPrintThread;
import Fox.core.lib.general.templates.ProgressState;
import Fox.core.main.SearchLib;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;


public class FPCalcThread
        implements Callable<FingerPrint>
{
    private static Logger logger;
    private FingerPrintThread executor;
    private volatile ProgressState Line, Common;
    private String location;
    private static final ExecutorService Pool = Executors.newFixedThreadPool(2, new ThreadFactory()
    {
        @Override
        public Thread newThread(@NotNull Runnable r)
        {
            return new Thread(r, "Progress bar call");
        }
    });

    public FPCalcThread(
            @NotNull FingerPrintThread executor,
            @NotNull String location,
            ProgressState ProgressLine,
            ProgressState CommonLine)
    {
        logger = LoggerFactory.getLogger(SearchLib.class);
        this.executor = executor;
        this.Line = ProgressLine;
        this.location = location;
        this.Common = CommonLine;
    }

    @Override
    public FingerPrint call()
    {
        FingerPrint fingerPrint = null;
        try
        {
            fingerPrint = executor.getFingerPrint(location);
        }
        catch (Exception e)
        {
            if (logger.isErrorEnabled())
                logger.error("", e);
            return null;
        }
        finally
        {
            if (Line != null)
                Pool.submit(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        synchronized (Line)
                        {
                            Line.update();
                        }
                    }
                });
            if (Common != null)
                Pool.submit(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        synchronized (Common)
                        {
                            Common.update();
                        }
                    }
                });
        }
        return fingerPrint;
    }
}
