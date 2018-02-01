package Fox.core.lib.general.templates;

import Fox.core.lib.general.utils.ProgressStateException;

public abstract class ProgressState
{
    protected int state, size;
    protected String desc, name;

    protected ProgressState(
            int size,
            String name,
            String desc) throws ProgressStateException
    {
        if (size < 0)
            throw new ProgressStateException("Can`t set progress bar size to zero or less.");
        this.size = size;
        this.desc = desc;
        this.name = name;
    }

    protected abstract void onDone();
    protected abstract void onResize();
    protected abstract void onChange();

    public void update(
            int now,
            String desc)
            throws ProgressStateException
    {
        if (now < 0)
            throw new ProgressStateException("Can`t change status of progress less then zero");
        this.state = now;
        this.desc = desc;

        this.onChange();

        if (now >= size)
        {
            this.onDone();
        }
    }

    public void update(String desc)
    {
        this.state++;
        this.desc = desc;

        this.onChange();

        if (state >= size)
        {
            this.onDone();
        }
    }

    public void update()
    {
        this.state++;

        this.onChange();

        if (state >= size)
        {
            this.onDone();
        }
    }


    public int getState()
    {
        return state;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size) throws ProgressStateException
    {
        if (size <= 0)
            throw new ProgressStateException("Can`t resize progress bar size to zero or less.");
        this.size = size;
        onResize();
        if (state >= size)
        {
            onDone();
        }
    }

    public String getName()
    {
        return name;
    }

    public String getDesc()
    {
        return desc;
    }

    public boolean isDone()
    {
        return state == size;
    }
}
