package Fox.test;

import Fox.core.lib.general.DOM.AlbumArtCompilation;
import Fox.core.lib.general.DOM.ID3V2;
import Fox.core.lib.general.templates.ProgressState;
import Fox.core.lib.general.utils.ExecutableHelper;
import Fox.core.lib.general.utils.performance;
import Fox.core.lib.general.utils.target;
import Fox.core.main.SearchLib;
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
    private static Logger logger;
    public static void main(String[] args)
    {
        logger = LoggerFactory.getLogger(SearchLib.class);

        Entry<Map<String, List<ID3V2>>, List<String>> Result = null;
        try
        {
            String mp3location = "C:\\Users\\Ssstlis\\Desktop\\music\\test";
            List<File> FileList = ExecutableHelper.GetFileList(mp3location,
                                                               new Mp3Filter()
                                                              );
            //AlbumArtCompilation meteora = SearchLib.SearchCovers("Meteora", null, target.MusicBrainz, 5);
            //FileList.clear();
            //FileList.add(new File("C:\\Users\\Ssstlis\\Desktop\\music\\Born Hanged\\Black Tongue - Born Hanged - 05 Coma.mp3"));
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
            Map<String, List<ID3V2>> res = null;
            if (FileList.size() == 1)
                res = SearchLib.SearchTags(ExecutableHelper.FilesToStrings(FileList).get(0),
                                           new WindowsFPcalc(),
                                           Line1,
                                           true,
                                           5);
            else
            Result = SearchLib.SearchTags(ExecutableHelper.FilesToStrings(FileList),
                                new WindowsFPcalc(),
                    Line1,
                    null,
                    Line3,
                    Line4,
                    performance.MAX,
                    true,
                    5);

            temp = System.currentTimeMillis() - temp;
            String success = Result.getKey().size() == FileList.size() ? "good" : "bad";
            if (logger.isInfoEnabled())
                logger.info("{} {}", temp, success);
            System.currentTimeMillis();
        }
        catch (Exception e)
        {
            if (logger.isErrorEnabled())
                logger.error("", e);
        }
    }
}
