package Fox.test;

import Fox.core.lib.general.DOM.ID3V2;
import Fox.core.lib.general.templates.ProgressState;
import Fox.core.lib.general.utils.ExecutableHelper;
import Fox.core.lib.general.utils.performance;
import Fox.core.main.AudioAnalyzeLibrary;
import Fox.test.util.Mp3Filter;
import Fox.utils.CustomProgressState;
import Fox.utils.WindowsFPcalc;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import static Fox.core.main.AudioAnalyzeLibrary.logger;

public class testing
{
    public static void main(String[] args)
    {
        Map<String, List<ID3V2>> Result = null;
        try
        {

            String mp3location = "D:\\music\\Born Handed";
            List<File> FileList = ExecutableHelper.GetFileList(mp3location,
                                                               new Mp3Filter()
                                                              );
            ProgressState Line1 = new CustomProgressState
                    (0,
                     "checker",
                     "checker"
                    );

            ProgressState Line2 = new CustomProgressState
                    (0,
                     "FP",
                     "FP"
                    );

            ProgressState Line3 = new CustomProgressState
                    (0,
                     "Service",
                     "Service"
                    );

            ProgressState Line4 = new CustomProgressState
                    (0,
                     "Common",
                     "Common"
                    );

            AudioAnalyzeLibrary Client = new AudioAnalyzeLibrary();

            long temp = System.currentTimeMillis();


            Client.buildStrings(ExecutableHelper.FilesToStrings(FileList));

            Result = Client.run(new WindowsFPcalc(),
                    Line1,
                    Line2,
                    Line3,
                    Line4,
                    performance.MAX,
                    false,
                    10,
                    true);

            temp = System.currentTimeMillis() - temp;
            String access = Result.size() == FileList.size() ? "good" : "bad";
            logger.log(Level.INFO, Long.toString(temp) + " " + access);
            System.currentTimeMillis();
        }
        catch (Exception e)
        {
            logger.log(Level.SEVERE, "", e);
        }
    }
}
