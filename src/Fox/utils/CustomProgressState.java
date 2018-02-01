package Fox.utils;

import Fox.core.lib.general.templates.ProgressState;
import Fox.core.lib.general.utils.ProgressStateException;

import java.util.logging.Level;

import static Fox.core.main.AudioAnalyzeLibrary.logger;
import static java.util.logging.Level.INFO;

public class CustomProgressState
        extends ProgressState
{

    public CustomProgressState(
            int size,
            String name,
            String desc)
            throws ProgressStateException
    {
        super(size,
              name,
              desc
             );
    }

    @Override
    public void onDone()
    {
        logger.log(INFO,"Progress bar " + this.name + " is done.");
    }

    @Override
    protected void onResize()
    {

    }

    @Override
    public void onChange()
    {
        logger.log(INFO,this.state + "\\" + this.size + "\\" + this.desc);
    }

}
