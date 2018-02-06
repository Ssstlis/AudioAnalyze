package Fox.core.main;

import Fox.core.lib.general.DOM.FingerPrint;
import Fox.core.lib.general.DOM.ID3V2;
import Fox.core.lib.general.Threads.FPCalcThread;
import Fox.core.lib.general.Threads.ServiceThread;
import Fox.core.lib.general.templates.FingerPrintThread;
import Fox.core.lib.general.templates.ProgressState;
import Fox.core.lib.general.utils.Exceptions;
import Fox.core.lib.general.utils.ExecutableHelper;
import Fox.core.lib.general.utils.FileChecker;
import Fox.core.lib.general.utils.performance;
import Fox.core.lib.services.Common.BuildTagProcessing;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.MINUTES;

public class AudioAnalyzeLibrary
{
    private static final Logger logger = LoggerFactory.getLogger(AudioAnalyzeLibrary.class);
    private final static String CALL_WO_BUILD = "Trying to call method without build file list.";
    public final static String NO_COUNT = "Impossible to return less then zero or equals zero size of results.";


    public AudioAnalyzeLibrary()
    {
    }

    private static List<String> ExcludeDuplicate(@NotNull List<String> Files)
    {
        Map<String, Boolean> AssistMap = new HashMap<>();
        List<String> result = null;
        if (Files.size() != 0)
        {
            result = new ArrayList<>();
            for (String elem : Files)
            {
                String lowerCase = elem.toLowerCase();
                if (!AssistMap.containsKey(lowerCase))
                {
                    result.add(lowerCase);
                    AssistMap.put(lowerCase, true);
                }
            }
        }
        return result;
    }

    public static Entry<Map<String, List<ID3V2>>, List<String>> run(
            @NotNull List<String> Files,
            @NotNull FingerPrintThread YourFPCalcThread,
            @NotNull ProgressState CheckerProgressBar,
            @NotNull ProgressState FPProgressBar,
            @NotNull ProgressState ServiceProgressBar,
            @NotNull ProgressState CommonProgressBar,
            @NotNull performance Speed,
            boolean TrustMode,
            int count)
            throws
            InterruptedException,
            Exceptions.NoBuildException,
            IllegalArgumentException,
            Exceptions.ProgressStateException,
            Exceptions.NoMatchesException,
            Exceptions.NoAccessingFilesException
    {
        boolean isBuild = false;

        if (Files.size() == 0 || count < 0)
        {
            if (logger.isErrorEnabled())
                logger.error(NO_COUNT);
            throw new IllegalArgumentException(NO_COUNT);
        }

        List<String> Locations = ExcludeDuplicate(Files);

        if (Locations.size() != 0)
            isBuild = true;

        if (!isBuild)
        {
            if (logger.isErrorEnabled())
                logger.error(CALL_WO_BUILD);
            throw new Exceptions.NoBuildException(CALL_WO_BUILD);
        }

        CommonProgressBar.setSize(Locations.size() * 3);
        CheckerProgressBar.setSize(Locations.size());

        FileChecker FileReviewer = new FileChecker();

        if (logger.isDebugEnabled())
            logger.debug("Start files check");

        FileReviewer.SiftFileAsString(Locations,
                                      CheckerProgressBar,
                                      CommonProgressBar
                                     );

        if (logger.isDebugEnabled())
            logger.debug("End files check");

        Locations = FileReviewer.getAccepted();
        List<String> Rejected = FileReviewer.getRejected();
        int size = Locations.size();
        if (size == 0)
        {
            Exceptions.NoAccessingFilesException exception = new Exceptions.NoAccessingFilesException("No one file were accepted.");
            if (logger.isErrorEnabled())
                logger.error("",exception);
            throw exception;
        }

        Map<String, List<ID3V2>> target = new ConcurrentHashMap<>(size);

        if (Rejected.size() > 0)
            CommonProgressBar.setSize(CommonProgressBar.getSize() - 3 * Rejected.size());

        FPProgressBar.setSize(size);
        ServiceProgressBar.setSize(size);

        int N_CPUs = Runtime.getRuntime()
                            .availableProcessors();
        int CPU = 2;
        if (N_CPUs != 2)
        {
            switch (Speed)
            {
                case MAX:
                    CPU = (N_CPUs > 1) ? (N_CPUs) : (2);
                    break;
                case HALF:
                    CPU = (N_CPUs / 2 > 1) ? (N_CPUs / 2) : (2);
                    break;
                case CLOSETOMAX:
                    CPU = (N_CPUs * 3 / 4 > 1) ? (N_CPUs * 3 / 4) : (2);
                    break;
                case CLOSETOMIN:
                    CPU = (N_CPUs / 4 > 1) ? (N_CPUs / 4) : (2);
                    break;
            }
        }

        if (logger.isInfoEnabled())
            logger.info("Starting work with " + CPU + " threads");

        ExecutorService ServicePool = Executors.newFixedThreadPool(CPU, new ThreadFactory()
        {
            @Override
            public Thread newThread(@NotNull Runnable r)
            {
                return new Thread(r, "Service Pool");
            }
        });

        if (logger.isInfoEnabled())
            logger.info("Instance FingerPrint thread list");

        long t = System.currentTimeMillis();
        List<Callable<FingerPrint>> tasks = new ArrayList<>(size);

        for (String file : Locations)
        {
            tasks.add(new FPCalcThread(YourFPCalcThread,
                                       file,
                                       FPProgressBar,
                                       CommonProgressBar));
        }

        if (logger.isInfoEnabled())
            logger.info("Instance done in {} milliseconds", System.currentTimeMillis() - t);


        List<Future<FingerPrint>> futureList = ServicePool.invokeAll(tasks);
        while (futureList.size() > 0)
        {
            Future<FingerPrint> toRemove = null;
            for (Future<FingerPrint> thread : futureList)
                if (thread.isDone())
                {
                    toRemove = thread;
                    FingerPrint print = null;
                    try
                    {
                        print = thread.get();
                    }
                    catch (ExecutionException e)
                    {
                        if (logger.isTraceEnabled())
                            logger.error("", e);
                    }

                    if (print != null)
                    {
                        if (logger.isInfoEnabled())
                            logger.info("Start service thread");

                        ServicePool.submit(new ServiceThread(print,
                                                             target,
                                                             ServiceProgressBar,
                                                             CommonProgressBar,
                                                             TrustMode,
                                                             count));
                    }
                    break;
                }

            if (toRemove != null)
                futureList.remove(toRemove);
        }

        ServicePool.shutdown();
        ServicePool.awaitTermination(25, MINUTES);
        if (target.size() == 0)
        {
            Exceptions.NoMatchesException e = new Exceptions.NoMatchesException("No matches.");
            if (logger.isErrorEnabled())
                logger.error("", e);
            throw e;
        }
        return new ExecutableHelper.Entry<>(target, Rejected);
    }

    public static void ClearCache()
    {
        BuildTagProcessing.ClearCache();
    }
}
