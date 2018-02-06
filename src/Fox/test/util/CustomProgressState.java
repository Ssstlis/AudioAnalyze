package Fox.test.util;

import Fox.core.lib.general.templates.ProgressState;
import Fox.core.lib.general.utils.Exceptions;
import Fox.core.main.AudioAnalyzeLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CustomProgressState
        extends ProgressState
{
    private static final Logger logger = LoggerFactory.getLogger(AudioAnalyzeLibrary.class);
    public CustomProgressState(
            int size,
            String name,
            String desc)
            throws
            Exceptions.ProgressStateException
    {
        super(size,
              name,
              desc
             );
    }

    @Override
    public void onDone()
    {
        if (logger.isInfoEnabled())
            logger.info("Progress bar {} is done", this.name);
    }

    @Override
    protected void onResize()
    {

    }

    @Override
    public void onChange()
    {
        if (logger.isInfoEnabled())
            logger.info("{} \\ {} \\ {}",this.state, this.size, this.desc);
    }

}
