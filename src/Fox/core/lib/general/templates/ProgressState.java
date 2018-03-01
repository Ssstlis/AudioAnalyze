package Fox.core.lib.general.templates;

import Fox.core.lib.general.utils.ProgressStateException;

/**
 * Progress state class allows you to use simple progress class with notifications and simple callbacks possibilities.
 * Progress state size can`t be less than zero and when you want to set size <=0 you need to catching
 * ProgressStateException.
 * Also current progress state status can`s be less than zero or equals and you also need to catching
 * ProgressStateException.
 */
public abstract class ProgressState
{
    /**
     * Value of the current progress.
     */
    protected int state;
    /**
     * Value of whole progress bar size.
     */
    protected int size;
    /**
     * Description of current progress bar status.
     */
    protected String desc;
    /**
     * Name of progress bar.
     */
    protected String name;
    private boolean Done = false;


    /** Constructor.
     * @param size size of constructed progress bar.
     * @param name name of progress bar.
     * @param desc starting description of progress bar.
     * @throws ProgressStateException if size < 0.
     */
    protected ProgressState(
            int size,
            String name,
            String desc) throws
            ProgressStateException
    {
        if (size < 0)
            throw new ProgressStateException("Can`t set progress bar size lesser than zero.");
        this.size = size;
        this.desc = desc;
        this.name = name;
    }

    /**
     * Common onDone event callback.
     * Calls it when progress status == progress state size.
     */
    protected abstract void onDone();

    /**
     * Common onResize event callback.
     * Calls it when progress state size change.
     */
    protected abstract void onResize();

    /**
     * Common onResize event callback.
     * Calls it when progress state status change.
     */
    protected abstract void onChange();

    /** Updating progress bar with new values.
     *  Calling onChange() if process are not complete.
     *  Calling onDone() if state >= size of progress bar.
     * @param now set current progress bar status.
     * @param desc set current progress bar description.
     * @throws ProgressStateException if now < 0.
     */
    public void update(
            int now,
            String desc)
            throws
            ProgressStateException
    {
        if (now < 0)
            throw new ProgressStateException("Can`t change status of progress less then zero");
        if (!Done)
        {
            this.state = now;
            this.desc = desc;
            this.onChange();
            if (state >= size)
            {
                this.onDone();
                Done = true;
            }
        }
    }

    /** Updating progress bar with new values.
     * Status incrementing.
     * Calling onChange() if process are not complete.
     * Calling onDone() if state >= size of progress bar.
     * @param desc set current description.
     */
    public void update(String desc)
    {
        if (!Done)
        {
            this.state++;
            this.desc = desc;
            this.onChange();
            if (state >= size)
            {
                this.onDone();
                Done = true;
            }
        }
    }

    /**
     * Updating progress bar with incrementing status.
     * Calling onChange() if process are not complete.
     * Calling onDone() if state >= size of progress bar.
     */
    public void update()
    {
        if (!Done)
        {
            this.state++;
            this.onChange();
            if (state >= size)
            {
                this.onDone();
                Done = true;
            }
        }
    }

    /**
     * @return current status of progress bar.
     */
    public int getState()
    {
        return state;
    }

    /**
     * @return current size of progress bar.
     */
    public int getSize()
    {
        return size;
    }

    /** Resizing progress bar.
     * Call onResize() if size are changing.
     * Calling onDone() if state >= size of progress bar.
     * @param size value to set as size of progress size.
     * @throws ProgressStateException if size <= 0.
     */
    public void setSize(int size) throws
            ProgressStateException
    {
        if (size <= 0)
            throw new ProgressStateException("Can`t set progress bar size to zero or less.");
        this.size = size;
        if (state >= size && !Done)
        {
            onResize();
            onDone();
            Done = true;
            return;
        }
        if (size > state)
        {
            onResize();
            Done = false;
        }
    }

    /**
     * @return name of progress bar.
     */
    public final String getName()
    {
        return name;
    }

    /**
     * @return current description of progress bar.
     */
    public String getDesc()
    {
        return desc;
    }

    /**
     * @return true if state == size, else false.
     */
    public boolean isDone()
    {
        return state == size;
    }
}
