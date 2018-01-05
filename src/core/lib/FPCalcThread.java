package core.lib;

import org.jetbrains.annotations.NotNull;

public class FPCalcThread implements Runnable
{
    private FingerPrintThread executor;
    private ProgressState Line, Common;
    private FingerPrint message;
    private String location;

    public FPCalcThread(@NotNull FingerPrintThread executor,
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
    public void run() {
            try {
                synchronized (message)
                {
                    executor.getFingerPrint(location, message);
                    message.notify();
                    synchronized (Line)
                    {
                        Line.update(Line.state+1, Line.desc);
                    }
                    synchronized (Common)
                    {
                        Common.update(Common.state+1, Common.desc);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

    }
}
