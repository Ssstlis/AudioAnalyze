# ProgressState abstract class
ProgressState is abstract class for simple event notification.

## Class definition
```java
public abstract class ProgressState
{
    protected int state;
    protected int size;
    protected String desc;
    protected String name;
    private boolean Done = false;

    protected ProgressState(int size,String name,String desc) throws ProgressStateException
    {
        if (size < 0) throw new ProgressStateException("Can`t set progress bar size lesser than zero.");
        this.size = size;
        this.desc = desc;
        this.name = name;
    }

    public void update(int now, String desc) throws ProgressStateException
    {
        if (now < 0) throw new ProgressStateException("Can`t change status of progress less then zero");
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

    public int getState(){return state;}
    
    public int getSize(){return size;}
    
    public void setSize(int size) throws ProgressStateException
    {
        if (size <= 0) throw new ProgressStateException("Can`t set progress bar size to zero or less.");
        this.size = size;
        if (state >= size && !Done)
        {
            onResize();
            onDone();
            Done = true;
            return;
        }
        if (size < state)
        {
            onResize();
            Done = false;
        }
    }
    
    public final String getName(){return name;}
    
    public String getDesc(){return desc;}

    public boolean isDone(){return Done;}
    
    protected abstract void onDone();
    protected abstract void onResize();
    protected abstract void onChange();
}
```
### Fields:
- state - current status of progress bar
- size - current size of progress bar
- desc - current description of progress bar
- name - name of progress bar
- Done - completion status

### Notifications
Calling "update(...)" and "setSize()" may cause callback methods:
- onChange() when current process status are changing
- onDode() when current process are complete
- onResize() when size of progress state are changing

You must override these methods in your implementation to track changes. 