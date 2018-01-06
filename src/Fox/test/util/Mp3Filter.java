package Fox.test.util;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3File;

import java.io.File;
import java.io.FileFilter;

public class Mp3Filter implements FileFilter {
    @Override
    public boolean accept(File pathname) {
        try {
            if (!pathname.getName().endsWith(".mp3"))
                return false;
            MP3File mp3File = (MP3File) AudioFileIO.read(pathname);
            return mp3File.getAudioHeader().getTrackLength() > 119;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

