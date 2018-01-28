package Fox.core.lib.general.Threads;

import Fox.core.lib.general.DOM.FingerPrint;
import Fox.core.lib.general.DOM.ID3V2;
import Fox.core.lib.general.templates.ProgressState;
import Fox.core.lib.services.Common.ServiceProcessing;
import Fox.core.lib.services.acoustid.AcoustIDClient;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceThread
        implements Runnable
{
    private FingerPrint FPrint;
    private volatile ProgressState Local, Common;
    private AcoustIDClient AIDClient;
    private boolean Trust;
    private ConcurrentHashMap<String, List<ID3V2>> target;


    public ServiceThread(
            @NotNull AcoustIDClient AIDClient,
            @NotNull FingerPrint FPrint,
            @NotNull ConcurrentHashMap<String, List<ID3V2>> Target,
            @NotNull ProgressState ServiceState,
            @NotNull ProgressState CommonProgress,
            boolean Trust
                        )
    {
        this.AIDClient = AIDClient;
        this.FPrint = FPrint;
        this.Common = CommonProgress;
        this.Local = ServiceState;
        this.Trust = Trust;
        this.target = Target;
    }

    @Override
    public void run()
    {
        try
        {
            synchronized (FPrint)
            {
                FPrint.wait(4000);
                //System.out.println(FPrint.toString());
                //TimeUnit.MILLISECONDS.sleep(2000);
                ServiceProcessing ProcessingClient = new ServiceProcessing(AIDClient);

                ProcessingClient.Processing(FPrint,
                                            Trust,
                                            target
                                           );

                Local.update();
                Common.update();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
