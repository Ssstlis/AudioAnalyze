package test;

import core.lib.ProgressState;
import core.lib.performance;
import core.main.AudioAnalyzeLibrary;
import core.utils.CustomProgressState;
import core.utils.ExecutableHelper;
import core.utils.WindowsFPcalc;
import core.windows.FileDestinationWindows;
import test.util.Mp3Filter;

import java.io.File;
import java.util.List;

public class testing {
    public static void main (String[] args)
    {
        try
        {
            /*ProgressState Line1 = new CustomProgressState().create(0,"checker","Line1");
            ProgressState Line2 = new CustomProgressState().create(0,"FP","Line2");
            ProgressState Line3 = new CustomProgressState().create(0,"Serv","Line3");
            ProgressState Line4 = new CustomProgressState().create(0,"Common","Line4");
*/
            //String mp3location = "C:\\Users\\Ssstlis\\Desktop\\music";

            /*System.out.println(ExecutableHelper.GetCurrentDir());
            System.out.println(ExecutableHelper.GetExecutableFile());*/

            String mp3location = "D:\\music\\Cursed";
            List<File> FileList = ExecutableHelper.GetFileList(mp3location, new Mp3Filter());
            //LibChecker LibCheckerClient = new LibChecker();
            //LibCheckerClient.check(mp3location);
            ProgressState Line1 = new CustomProgressState()
                    .create(0,"checker","checker");
            ProgressState Line2 = new CustomProgressState()
                    .create(0,"FP","FP");
            ProgressState Line3 = new CustomProgressState()
                    .create(0,"Serv","Serv");
            ProgressState Line4 = new CustomProgressState()
                    .create(0,"Common","Common");
            for(int i = 0;i<4;i++) {

                System.out.println(i);
                long temp = System.currentTimeMillis();
                AudioAnalyzeLibrary Client = new AudioAnalyzeLibrary(
                        new WindowsFPcalc(),
                        Line1,
                        Line2,
                        Line3,
                        Line4
                )
                        .buildStrings(ExecutableHelper.FilesToStrings(FileList))
                        .run(performance.MAX);
                System.out.println(System.currentTimeMillis()-temp);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
