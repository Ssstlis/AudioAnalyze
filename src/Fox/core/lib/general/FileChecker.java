package Fox.core.lib.general;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FileChecker {
    private List<String>
            Accepted,
            Rejected;

    public FileChecker() {
        this.Accepted = new CopyOnWriteArrayList<>();
        this.Rejected = new CopyOnWriteArrayList<>();
    }

    public void SiftFileAsString(@NotNull List<String> Sources,
                                 @NotNull ProgressState Progress,
                                 @NotNull ProgressState Common)
            throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(2);

        for (String loc : Sources)
            es.execute(new FileCheckerThread(
                    loc,
                    Accepted,
                    Rejected,
                    Progress,
                    Common));

        es.shutdown();
        es.awaitTermination(1, TimeUnit.DAYS);
    }

    public void SiftFileAsFile(@NotNull List<File> Sources,
                               @NotNull ProgressState Progress,
                               @NotNull ProgressState Common)
            throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(2);

        for (File file : Sources)
            es.execute(new FileCheckerThread(
                    file.getPath(),
                    Accepted,
                    Rejected,
                    Progress,
                    Common));

        es.shutdown();
        es.awaitTermination(15, TimeUnit.MINUTES);
    }

    @NotNull
    public List<String> getAccepted() {
        return Accepted;
    }

    @NotNull
    public List<String> getRejected() {
        return Rejected;
    }

}
