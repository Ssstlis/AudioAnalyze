package Fox.core.main;

import Fox.core.lib.general.DOM.FingerPrint;
import Fox.core.lib.general.DOM.ID3V2;
import Fox.core.lib.general.Threads.FPCalcThread;
import Fox.core.lib.general.Threads.ServiceThread;
import Fox.core.lib.general.templates.FingerPrintThread;
import Fox.core.lib.general.templates.ProgressState;
import Fox.core.lib.general.utils.FileChecker;
import Fox.core.lib.general.utils.performance;
import Fox.core.lib.services.acoustid.AcoustIDClient;
import Fox.core.lib.services.acoustid.AcoustIDRequestConfig;
import Fox.utils.ExecutableHelper;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AudioAnalyzeLibrary
{
    private static AcoustIDClient AIDClient;
    private List<String>
            Locations,
            Rejected;
    private boolean isBuild = false;


    public AudioAnalyzeLibrary()
    {
        AcoustIDRequestConfig AIDConfig = new AcoustIDRequestConfig();
        AIDConfig.setDefault();
        AIDClient = new AcoustIDClient(AIDConfig);
    }

    public void buildStrings(@NotNull List<String> Files)
    {
        this.Locations = Files;
        if (Locations.size() > 0)
        {
            isBuild = true;
        }
    }

    public void buildFiles(@NotNull List<File> File)
    {
        this.Locations = ExecutableHelper.FilesToStrings(File);
        if (Locations.size() > 0)
        {
            isBuild = true;
        }
    }

    public ConcurrentHashMap<String, List<ID3V2>> run(@NotNull FingerPrintThread YourFPCalcThread,
                                                      @NotNull ProgressState CheckerProgressBar,
                                                      @NotNull ProgressState FPProgressBar,
                                                      @NotNull ProgressState ServiceProgressBar,
                                                      @NotNull ProgressState CommonProgressBar,
                                                      @NotNull performance Speed,
                                                      boolean TrustMode)
            throws
            Exception
    {
        CommonProgressBar.setSize(Locations.size() * 3);
        CheckerProgressBar.setSize(Locations.size());

        FileChecker FileReviewer = new FileChecker();

        FileReviewer.SiftFileAsString(Locations,
                                      CheckerProgressBar,
                                      CommonProgressBar
                                     );

        Locations = FileReviewer.getAccepted();
        Rejected = FileReviewer.getRejected();

        if (Locations == null || Locations.size() == 0)
        {
            return null;
        }

        ConcurrentHashMap<String, List<ID3V2>> target = new ConcurrentHashMap<>();

        CommonProgressBar.setSize(CommonProgressBar.getSize() - 3 * Rejected.size());

        FPProgressBar.setSize(Locations.size());
        ServiceProgressBar.setSize(Locations.size());

        int N_CPUs = Runtime.getRuntime()
                            .availableProcessors();
        int CPU = 2;
        if (N_CPUs != 2)
        {
            switch (Speed)
            {
                case MAX:
                    CPU = (N_CPUs - 1 > 1) ? (N_CPUs - 1) : (2);
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

        System.out.println("Стартовало " + CPU + " потоков");

        ExecutorService es = Executors.newFixedThreadPool(CPU);

        for (String file : Locations)
        {
            FingerPrint transfer = new FingerPrint();

            es.execute(new ServiceThread(
                    AIDClient,
                    transfer,
                    target,
                    ServiceProgressBar,
                    CommonProgressBar,
                    TrustMode
            ));

            es.execute(new FPCalcThread(
                    YourFPCalcThread,
                    file,
                    transfer,
                    FPProgressBar,
                    CommonProgressBar
            ));
        }

        es.shutdown();
        es.awaitTermination(15,
                            TimeUnit.MINUTES
                           );
        isBuild = false;

        return target;
    }

    public List<String> getRejected()
    {
        return Rejected;
    }

    public List<String> getAccepted()
    {
        return Locations;
    }

}
