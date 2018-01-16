package Fox.test.pool;

import Fox.core.lib.general.DOM.FingerPrint;
import Fox.test.util.Mp3Filter;
import Fox.utils.ExecutableHelper;

import java.io.File;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PoolTesting
{
    public static void main(String[] args) throws
                                           InterruptedException
    {
        String mp3location = "D:\\music";
        List<File> FileList = ExecutableHelper.GetFileList(mp3location,
                                                           new Mp3Filter()
                                                          );
        List<FingerPrint> TargetList = new CopyOnWriteArrayList<>();

        /*int N_CPUS = Runtime.getRuntime().availableProcessors();
        ExecutorService es  =  Executors.newFixedThreadPool(N_CPUS-2);

        long start = System.currentTimeMillis();
        for(File file:FileList)
            es.execute(new FPCalcThread(file.getPath(), TargetList, Line,es));

        /*ScheduledExecutorService service = Executors.newScheduledThreadPool(N_CPUS/2);

        service.scheduleWithFixedDelay(new MyThread(), 0, 2, TimeUnit.SECONDS);

        es.shutdown();
        es.awaitTermination(4,TimeUnit.MINUTES);
        long end = System.currentTimeMillis()-start;
        System.out.println("программа выполнялась " + end/60 + " секунд");
        System.out.println(TargetList.size());*/
        //new AudioAnalyzeLibrary(TargetList,Line).buildFiles(FileList).run(MAX);

    }
}
