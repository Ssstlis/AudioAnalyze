package Fox.core.lib.general.Threads;

import Fox.core.lib.general.DOM.FingerPrint;
import Fox.core.lib.general.templates.FingerPrintThread;
import Fox.core.lib.general.templates.ProgressState;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

import static Fox.core.main.AudioAnalyzeLibrary.logger;

public class FPCalcThread
        implements Runnable
{
    private FingerPrintThread executor;
    private volatile ProgressState Line, Common;
    private FingerPrint message;
    private String location;

    public FPCalcThread(
            @NotNull FingerPrintThread executor,
            @NotNull String location,
            @NotNull FingerPrint transfer,
            @NotNull ProgressState ProgressLine,
            @NotNull ProgressState CommonLine)
    {
        this.executor = executor;
        this.Line = ProgressLine;
        this.message = transfer;
        this.location = location;
        this.Common = CommonLine;
    }

    @Override
    public void run()
    {
        try
        {
            synchronized (message)
            {
                executor.getFingerPrint(location,
                                        message
                                       );
                message.notify();
            }
        }
        catch (Exception e)
        {
            logger.log(Level.SEVERE, "", e);
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

    }
}
