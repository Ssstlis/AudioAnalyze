package test.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TFile
{
    private String loc;

    public TFile(String location)
    {
        loc = location;
    }

    public void Append(String source) throws IOException
    {
        File file = new File(loc);
        FileWriter writer = new FileWriter(loc, true);
        if (file.exists())
        {
            writer.write(source);
            writer.flush();
            writer.close();
            file = null;
            writer = null;
        }
        else
        {
            if (file.createNewFile())
            {
                writer.write(source);
                writer.flush();
                writer.close();
            }
            file = null;
            writer = null;
        }
    }

}
