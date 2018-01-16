package Fox.utils;

import Fox.core.lib.general.templates.ProgressState;

public class CustomProgressState
        extends ProgressState
{

    public CustomProgressState(int size,
                               String name,
                               String desc)
    {
        super(size,
              name,
              desc
             );
    }

    @Override
    public void onDone()
    {
        System.out.println("Progress bar " + this.name + " is done.");
    }

    @Override
    public void onChange()
    {
        System.out.println(this.state + "\\" + this.size + "\\" + this.desc);
    }

}
