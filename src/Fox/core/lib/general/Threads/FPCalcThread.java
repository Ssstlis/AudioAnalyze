package Fox.core.lib.general.Threads;

import Fox.core.lib.general.DOM.FingerPrint;
import Fox.core.lib.general.templates.FingerPrintThread;
import Fox.core.lib.general.templates.ProgressState;
import Fox.core.main.SearchLib;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;


public class FPCalcThread
        implements Callable<FingerPrint>
{
    private static final Logger logger = LoggerFactory.getLogger(SearchLib.class);
    private FingerPrintThread executor;
    private volatile ProgressState Line, Common;
    private String location;

    public FPCalcThread(
            @NotNull FingerPrintThread executor,
            @NotNull String location,
            @NotNull ProgressState ProgressLine,
            @NotNull ProgressState CommonLine)
    {
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
            synchronized (Line)
            {
                Line.update();
            }
            synchronized (Common)
            {
                Common.update();
            }
        }
        return fingerPrint;
    }
}
