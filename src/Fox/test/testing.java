package Fox.test;

import Fox.core.lib.general.DOM.ID3V2;
import Fox.core.lib.general.templates.ProgressState;
import Fox.core.lib.general.utils.ExecutableHelper;
import Fox.core.lib.general.utils.performance;
import Fox.core.main.AudioAnalyzeLibrary;
import Fox.test.util.Mp3Filter;
import Fox.test.util.CustomProgressState;
import Fox.test.util.WindowsFPcalc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class testing
{
    private static final Logger logger = LoggerFactory.getLogger(AudioAnalyzeLibrary.class);
    public static void main(String[] args)
    {

        Entry<Map<String, List<ID3V2>>, List<String>> Result = null;
        try
        {
            String mp3location = "C:\\Users\\Ssstlis\\Desktop\\music\\Born Hanged";
            List<File> FileList = ExecutableHelper.GetFileList(mp3location,
                                                               new Mp3Filter()
                                                              );
            //FileList.clear();
            //FileList.add(new File("D:\\music\\Born Handed\\Black Tongue - Born Hanged - 05 Coma.mp3"));
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


            long temp = System.currentTimeMillis();



            Result = AudioAnalyzeLibrary.run(ExecutableHelper.FilesToStrings(FileList),
                                new WindowsFPcalc(),
                    Line1,
                    Line2,
                    Line3,
                    Line4,
                    performance.MAX,
                    true,
                    5);

            temp = System.currentTimeMillis() - temp;
            String access = Result.getKey().size() == FileList.size() ? "good" : "bad";
            if (logger.isWarnEnabled())
                logger.warn("{} {}", temp, access);
            System.currentTimeMillis();
        }
        catch (Exception e)
        {
            if (logger.isErrorEnabled())
                logger.error("", e);
        }
    }
}
