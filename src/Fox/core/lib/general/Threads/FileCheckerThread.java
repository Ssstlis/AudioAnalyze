package Fox.core.lib.general.Threads;

import Fox.core.lib.general.templates.ProgressState;
import Fox.core.main.AudioAnalyzeLibrary;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public class FileCheckerThread
        implements Runnable
{
    private static final Logger logger = LoggerFactory.getLogger(AudioAnalyzeLibrary.class);
    private String location;
    private List<String> Target, Rejected;
    private ProgressState Line, Common;

    public FileCheckerThread(
            @NotNull String Source,
            @NotNull List<String> TargetList,
            @NotNull List<String> RejectedList,
            @NotNull ProgressState TargetProgress,
            @NotNull ProgressState CommonLine)
    {
        this.location = Source;
        this.Target = TargetList;
        this.Rejected = RejectedList;
        this.Line = TargetProgress;
        this.Common = CommonLine;
    }

    private static boolean acceptAsString(String check)
    {
        try
        {
            File pathname = new File(check);

            if (!check.endsWith(".mp3"))
            {
                return false;
            }

            MP3File mp3File = (MP3File) AudioFileIO.read(pathname);
            return mp3File.getAudioHeader()
                          .getTrackLength() > 119;
        }
        catch (Exception e)
        {
            if (logger.isErrorEnabled())
                logger.error("", e);
            return false;
        }
    }

    @Override
    public void run()
    {
        try
        {
            if (acceptAsString(location))
            {
                Target.add(location);
            }
            else
            {
                Rejected.add(location);
            }
        }
        catch (Exception e)
        {
            if (logger.isErrorEnabled())
                logger.error("", e);
        }
        finally
        {
            synchronized (Line)
            {
                Line.update();
            }
            synchronized (Common)
            {
                Common.update();
            }
        }
    }
}
