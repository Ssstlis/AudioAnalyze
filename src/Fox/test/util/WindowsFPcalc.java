package Fox.test.util;

import Fox.core.lib.general.data.FingerPrint;
import Fox.core.lib.general.templates.FingerPrintThread;
import Fox.core.lib.general.utils.FingerPrintProcessingException;
import Fox.core.main.SearchLib;
import core.windows.FileDestinationWindows;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class WindowsFPcalc
        implements FingerPrintThread
{

    @Override
    public FingerPrint getFingerPrint(
            @NotNull String location)
            throws
            FingerPrintProcessingException
    {
        Logger logger = LoggerFactory.getLogger(SearchLib.class);
        FingerPrint target = new FingerPrint();
        try
        {
            final String Source = new FileDestinationWindows()
                    .GetCurrentDir(WindowsFPcalc.class)
                    .addElem("fpcalc.exe", true)
                    .toString();

            String duration;
            String print;
            String[] args = new String[]{Source, location};

            Process process = new ProcessBuilder()
                    .command(args)
                    .redirectErrorStream(true)
                    .start();

            InputStream stdin = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(stdin);
            BufferedReader br = new BufferedReader(isr);

            String line;
            String result = "";

            while ((line = br.readLine()) != null)
                result = result.concat(line + "\n");

            int exit_value = process.waitFor();
            if (exit_value == 0)
            {
                duration = result.substring(result.indexOf("DURATION=") + 9,
                                            result.indexOf("DURATION=") + 12);
                print = result.substring(result.indexOf("FINGERPRINT=") + 12);

                target.setPrint(print);
                if (logger.isInfoEnabled())
                    logger.info("Location: {}, duration {}", location, duration);
                target.setDuration(duration);
                target.setLocation(location);
            }
            process.destroy();
        }
        catch (Exception e)
        {
            throw new FingerPrintProcessingException(e);
        }
        return target;
    }
}
