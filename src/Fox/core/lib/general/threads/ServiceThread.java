package Fox.core.lib.general.threads;

import Fox.core.lib.general.data.FingerPrint;
import Fox.core.lib.general.data.ID3V2;
import Fox.core.lib.general.templates.ProgressState;
import Fox.core.lib.services.common.ServiceProcessing;
import Fox.core.lib.services.AcoustID.AcoustIDApi;
import Fox.core.main.SearchLib;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


public class ServiceThread
        implements Runnable
{
    private static Logger logger;
    private FingerPrint FPrint;
    private volatile ProgressState Local, Common;
    private AcoustIDApi AIDClient;
    private boolean Trust;
    private Map<String, List<ID3V2>> target;
    private int count;


    public ServiceThread(
            @NotNull FingerPrint FPrint,
            @NotNull Map<String, List<ID3V2>> Target,
            ProgressState ServiceState,
            ProgressState CommonProgress,
            boolean Trust,
            int count)
    {
        logger = LoggerFactory.getLogger(SearchLib.class);
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
                                         count);

        }
        catch (Exception e)
        {
            if (logger.isErrorEnabled())
                logger.error("{} {}", FPrint.getLocation(), e.getMessage());
        }
        finally
        {
            if (Local != null)
                synchronized (Local)
                {
                    Local.update();
                }
            if (Common != null)
                synchronized (Common)
                {
                    Common.update();
                }
        }
    }
}
