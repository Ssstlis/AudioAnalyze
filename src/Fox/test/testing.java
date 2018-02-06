package Fox.test;

import Fox.core.lib.general.DOM.FingerPrint;
import Fox.core.lib.general.DOM.ID3V2;
import Fox.core.lib.general.templates.ProgressState;
import Fox.core.lib.general.utils.ExecutableHelper;
import Fox.core.lib.general.utils.performance;
import Fox.core.lib.services.Common.SimpleInfo;
import Fox.core.lib.services.LastFM.Track.getInfo.sources.TrackInfo;
import Fox.core.lib.services.acoustid.LookupByFP.sources.ByFingerPrint;
import Fox.core.lib.services.acoustid.LookupByFP.sources.Recording;
import Fox.core.main.AudioAnalyzeLibrary;
import Fox.test.util.Mp3Filter;
import Fox.utils.CustomProgressState;
import Fox.utils.WindowsFPcalc;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import static Fox.core.main.AudioAnalyzeLibrary.logger;
import static java.util.logging.Level.WARNING;

public class testing
{
    public static ConcurrentHashMap<String, FingerPrint> dbg1 = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String ,ByFingerPrint> dbg2 = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, List<Recording>> dbg3 = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, List<SimpleInfo>> dbg4 = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, List<SimpleInfo>> dbg41 = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, List<SimpleInfo>> dbg5 = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, Entry<org.musicbrainz.android.api.data.Recording, TrackInfo>> dbg6 = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, ID3V2> dbg7 = new ConcurrentHashMap<>();
    public static void main(String[] args)
    {

        Map<String, List<ID3V2>> Result = null;
        try
        {
            String mp3location = "D:\\music\\Born Handed";
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

            AudioAnalyzeLibrary Client = new AudioAnalyzeLibrary();

            long temp = System.currentTimeMillis();


            Client.buildStrings(ExecutableHelper.FilesToStrings(FileList));

            Result = Client.run(new WindowsFPcalc(),
                    Line1,
                    Line2,
                    Line3,
                    Line4,
                    performance.MAX,
                    true,
                    10,
                    false);

            temp = System.currentTimeMillis() - temp;
            String access = Result.size() == FileList.size() ? "good" : "bad";
            logger.log(WARNING, Long.toString(temp) + " " + access);
            System.currentTimeMillis();
        }
        catch (Exception e)
        {
            logger.log(Level.SEVERE, "", e);
        }
    }
}
