package Fox.core.lib.general.threads;

import Fox.core.lib.general.data.FingerPrint;
import Fox.core.lib.general.data.ID3V2;
import Fox.core.lib.general.templates.ProgressState;
import Fox.core.lib.general.utils.AcoustIDException;
import Fox.core.lib.general.utils.NoMatchesException;
import Fox.core.lib.services.AcoustID.AcoustIDApi;
import Fox.core.lib.services.Common.ServiceProcessing;
import Fox.core.main.SearchLib;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;


public class ServiceThread
        implements Runnable
{
    private static Logger logger;
    private final FingerPrint FPrint;
    private final ProgressState Local;
    private final ProgressState Common;
    private final AcoustIDApi AIDClient;
    private final boolean Trust;
    private final Map<String, List<ID3V2>> target;
    private final int count;
    private final List<String> Rejected;
    private static final ExecutorService Pool = Executors.newFixedThreadPool(2, new ThreadFactory()
    {
        @Override
        public Thread newThread(@NotNull Runnable r)
        {
            return new Thread(r, "Progress bar call");
        }
    });


    public ServiceThread(
            FingerPrint FPrint,
            @NotNull Map<String, List<ID3V2>> Target,
            ProgressState ServiceState,
            ProgressState CommonProgress,
            boolean Trust,
            int count,
            List<String> Rejected)
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
        this.Rejected = Rejected;
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
        catch (NoMatchesException | AcoustIDException e)
        {
            if (Rejected != null)
                Rejected.add(FPrint.getLocation());
            if (logger.isErrorEnabled())
                logger.error("{} {}", FPrint.getLocation(), e.getMessage());
        }
        finally
        {
            if (Local != null)
                Pool.submit(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        synchronized (Local)
                        {
                            Local.update();
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
    }
}
