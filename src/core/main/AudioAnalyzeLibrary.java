package core.main;

import core.lib.*;
import core.lib.service.Elapsed;
import core.utils.ExecutableHelper;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AudioAnalyzeLibrary
{
    private List<String>
            Locations,
            Rejected;

    private ProgressState
            CheckBar,
            FPBar,
            ServiceBar,
            CommonBar;

    private FingerPrintThread targetFPCalcThread;
    private Map<String, Tag> Target;
    public static long time1;

    public AudioAnalyzeLibrary(@NotNull FingerPrintThread YourFPCalcThread,
                               @NotNull ProgressState CheckerProgressBar,
                               @NotNull ProgressState FPProgressBar,
                               @NotNull ProgressState ServiceProgressBar,
                               @NotNull ProgressState CommonProgressBar)
    {
        this.targetFPCalcThread = YourFPCalcThread;
        this.CheckBar = CheckerProgressBar;
        this.CommonBar = CommonProgressBar;
        this.FPBar = FPProgressBar;
        this.ServiceBar = ServiceProgressBar;
        this.Target = new HashMap<>();
    }

    public AudioAnalyzeLibrary buildStrings(@NotNull List<String> Files)
    {
        this.Locations = Files;
        return this;
    }

    public AudioAnalyzeLibrary buildFiles(@NotNull List<File> File)
    {
        this.Locations = ExecutableHelper.FilesToStrings(File);
        return this;
    }

    public AudioAnalyzeLibrary run(@NotNull performance Speed) throws InterruptedException
    {
        int N_CPUS = Runtime.getRuntime().availableProcessors();
        int CPU = 2;
        if (N_CPUS!=2)
            switch (Speed)
            {
                case MAX:
                    CPU = (N_CPUS-1>1) ? (N_CPUS-1) : (2) ;
                    break;
                case HALF:
                    CPU = (N_CPUS/2>1) ? (N_CPUS/2) : (2);
                    break;
                case CLOSETOMAX:
                    CPU = (N_CPUS*3/4>1) ? (N_CPUS*3/4) : (2);
                    break;
                case CLOSETOMIN:
                    CPU = (N_CPUS/4>1) ? (N_CPUS/4) : (2);
                    break;
            }
        System.out.println("Стартовало " + CPU + " потоков");
        CommonBar.setSize(Locations.size()*3);
        CheckBar.setSize(Locations.size());

        FileChecker Filereviewer = new FileChecker();
        Filereviewer.SiftFileAsString(Locations, CheckBar, CommonBar);
        Locations = Filereviewer.getAccepted();
        Rejected = Filereviewer.getRejected();
        CommonBar.setSize(CommonBar.getSize()-3*Filereviewer.getRejected().size());

        FPBar.setSize(Locations.size());
        ServiceBar.setSize(Locations.size());

        long CurrentTime = System.currentTimeMillis();

        Elapsed.AcousticIDElapse = CurrentTime;
        Elapsed.CoverArtArchiveElapse = CurrentTime;
        Elapsed.LastFMElapse = CurrentTime;
        Elapsed.MusicBrainzElapse = CurrentTime;
        /*TODO СДЕЛАЙ БЛЯТЬ ПО 2 ПОТОКА НА КАЖДОГО
         */
        ExecutorService es  =  Executors.newFixedThreadPool(CPU);
        for(String file:Locations)
        {
            time1 = System.currentTimeMillis();
            FingerPrint transfer = new FingerPrint();

            es.execute(new ServiceThread(
                    transfer,
                    Target,
                    ServiceBar,
                    CommonBar));

            es.execute(new FPCalcThread(
                    targetFPCalcThread,
                    file,
                    transfer,
                    FPBar,
                    CommonBar));
        }

        es.shutdown();
        es.awaitTermination(15, TimeUnit.SECONDS);

        return this;
    }

    public List<String> getRejected() {
        return Rejected;
    }

    public List<String> getAccepted() {
        return Locations;
    }
}
