package core.utils;

import core.lib.FingerPrint;
import core.lib.FingerPrintThread;
import core.windows.FileDestinationWindows;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WindowsFPcalc implements FingerPrintThread {

    @Override
    public void getFingerPrint(@NotNull String location, @NotNull FingerPrint target) throws Exception
    {
        try {
            final String Source = new FileDestinationWindows()
                    .GetCurrentDir(WindowsFPcalc.class)
                    .addElem("fpcalc.exe", true)
                    .toString();

            String duration, print;
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
            if (exit_value == 0) {
                duration = result.substring(result.indexOf("DURATION=") + 9, result.indexOf("DURATION=") + 12);
                print = result.substring(result.indexOf("FINGERPRINT=") + 12);
                target.setPrint(print);
                target.setDuration(duration);
                target.setLocation(location);
            }
            process.destroy();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
