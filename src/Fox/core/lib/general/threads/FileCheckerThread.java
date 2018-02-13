package Fox.core.lib.general.threads;

import Fox.core.lib.general.templates.ProgressState;
import Fox.core.main.SearchLib;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class FileCheckerThread
        implements Runnable
{
    private static Logger logger;
    private String location;
    private List<String> Target, Rejected;
    private ProgressState Line, Common;
    private static final ExecutorService Pool = Executors.newFixedThreadPool(2, new ThreadFactory()
    {
        @Override
        public Thread newThread(@NotNull Runnable r)
        {
            return new Thread(r, "Progress bar call");
        }
    });

    public FileCheckerThread(
            @NotNull String Source,
            @NotNull List<String> TargetList,
            @NotNull List<String> RejectedList,
            ProgressState TargetProgress,
            ProgressState CommonLine)
    {
        logger = LoggerFactory.getLogger(SearchLib.class);
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

            if (!check.endsWith(".mp3")
                    || !check.endsWith(".flac")
                    || !check.endsWith(".mp4")
                    || !check.endsWith(".ogg"))
                return false;

            return AudioFileIO.read(pathname).getAudioHeader().getTrackLength() > 119;
        }
        catch (Exception e)
        {
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
            if (Line != null)
                Pool.submit(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        synchronized (Line)
                        {
                            Line.update();
                        }
                    }
                });
            if (Common != null)
                Pool.submit(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        synchronized (Common)
                        {
                            Common.update();
                        }
                    }
                });
        }
    }
}
