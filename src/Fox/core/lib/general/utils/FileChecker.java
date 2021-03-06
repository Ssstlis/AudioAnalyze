package Fox.core.lib.general.utils;

import Fox.core.lib.general.threads.FileCheckerThread;
import Fox.core.lib.general.templates.ProgressState;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.concurrent.*;

public class FileChecker
{
    private List<String> Accepted;
    private List<String> Rejected;

    public void SiftFileAsString(
            @NotNull List<String> Sources,
            ProgressState Progress,
            ProgressState Common)
            throws
            InterruptedException
    {
        this.Accepted = new CopyOnWriteArrayList<>();
        this.Rejected = new CopyOnWriteArrayList<>();

        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime()
                                                                 .availableProcessors(), new ThreadFactory()
        {
            @Override
            public Thread newThread(@NotNull Runnable r)
            {
                return new Thread(r, "File checker pool");
            }
        });

        for (String loc : Sources)
        {
            es.execute(new FileCheckerThread(
                    loc,
                    Accepted,
                    Rejected,
                    Progress,
                    Common
            ));
        }

        es.shutdown();
        es.awaitTermination(10*Sources.size(),
                            TimeUnit.SECONDS
                           );
    }

    public void SiftFileAsFile(
            @NotNull List<File> Sources,
            ProgressState Progress,
            ProgressState Common)
            throws
            InterruptedException
    {

        this.Accepted = new CopyOnWriteArrayList<>();
        this.Rejected = new CopyOnWriteArrayList<>();
        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (File file : Sources)
        {
            es.execute(new FileCheckerThread(
                    file.getPath(),
                    Accepted,
                    Rejected,
                    Progress,
                    Common
            ));
        }

        es.shutdown();
        es.awaitTermination(10*Sources.size(),
                            TimeUnit.SECONDS
                           );
    }

    @NotNull
    public List<String> getAccepted()
    {
        return Accepted;
    }

    @NotNull
    public List<String> getRejected()
    {
        return Rejected;
    }

}
