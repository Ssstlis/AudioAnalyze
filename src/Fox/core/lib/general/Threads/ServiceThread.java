package Fox.core.lib.general.Threads;

import Fox.core.lib.general.DOM.FingerPrint;
import Fox.core.lib.general.DOM.ID3V2;
import Fox.core.lib.general.templates.ProgressState;
import Fox.core.lib.services.Common.ServiceProcessing;
import Fox.core.lib.services.acoustid.AcoustIDApi;
import Fox.core.main.SearchLib;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


public class ServiceThread
        implements Runnable
{
    private static final Logger logger = LoggerFactory.getLogger(SearchLib.class);
    private FingerPrint FPrint;
    private volatile ProgressState Local, Common;
    private AcoustIDApi AIDClient;
    private boolean Trust;
    private Map<String, List<ID3V2>> target;
    private int count;


    public ServiceThread(
            @NotNull FingerPrint FPrint,
            @NotNull Map<String, List<ID3V2>> Target,
            @NotNull ProgressState ServiceState,
            @NotNull ProgressState CommonProgress,
            boolean Trust,
            int count)
    {
        AcoustIDApi.AcoustIDRequestConfig AIDConfig = new AcoustIDApi.AcoustIDRequestConfig();
        AIDConfig.setDefault();
        this.AIDClient = new AcoustIDApi(AIDConfig);
        this.FPrint = FPrint;
        this.Common = CommonProgress;
        this.Local = ServiceState;
        this.Trust = Trust;
        this.target = Target;
        this.count = count;
    }

    @Override
    public void run()
    {
        try
        {

                ServiceProcessing.Processing(AIDClient,
                                             FPrint,
                                             Trust,
                                             target,
                                             count
                                            );

        }
        catch (Exception e)
        {
            if (logger.isErrorEnabled())
                logger.error("", e);
        }
        finally
        {
            synchronized (Local)
            {
                Local.update();
            }
            synchronized (Common)
            {
                Common.update();
            }
        }
    }
}
