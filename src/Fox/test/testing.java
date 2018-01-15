package Fox.test;

import Fox.core.lib.general.ProgressState;
import Fox.core.lib.general.ID3V2;
import Fox.core.lib.general.performance;
import Fox.core.main.AudioAnalyzeLibrary;
import Fox.test.util.Mp3Filter;
import Fox.utils.CustomProgressState;
import Fox.utils.ExecutableHelper;
import Fox.utils.WindowsFPcalc;

import java.io.File;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class testing {
    public static void main(String[] args) {
        try {

            String mp3location = "D:\\music\\Cursed";
            List<File> FileList = ExecutableHelper.GetFileList(mp3location, new Mp3Filter());
            ProgressState Line1 = new CustomProgressState
                    (0, "checker", "checker");

            ProgressState Line2 = new CustomProgressState
                    (0, "FP", "FP");

            ProgressState Line3 = new CustomProgressState
                    (0, "Service", "Service");

            ProgressState Line4 = new CustomProgressState
                    (0, "Common", "Common");

            AudioAnalyzeLibrary Client = new AudioAnalyzeLibrary();

            for (int i = 0; i < 4; i++) {

                System.out.println(i);
                long temp = System.currentTimeMillis();


                Client.buildStrings(ExecutableHelper.FilesToStrings(FileList));
                ConcurrentHashMap<String, List<ID3V2>> Result = Client.run(
                        new WindowsFPcalc(),
                        Line1,
                        Line2,
                        Line3,
                        Line4,
                        performance.MAX,
                        true
                                                                          );

                System.out.println(System.currentTimeMillis() - temp);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
