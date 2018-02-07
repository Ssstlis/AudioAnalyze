package Fox.test.util;

import Fox.core.main.SearchLib;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;

public class Mp3Filter
        implements FileFilter
{
    private static final Logger logger = LoggerFactory.getLogger(SearchLib.class);
    @Override
    public boolean accept(File pathname)
    {
        try
        {
            if (!pathname.getName()
                         .endsWith(".mp3"))
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
}

