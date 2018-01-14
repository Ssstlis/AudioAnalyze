package Fox.utils;

import Fox.core.lib.general.ProgressState;

public class CustomProgressState implements ProgressState {

    public int state, size;
    public String desc, name;

    @Override
    public ProgressState create(int size, String name, String desc) {
        this.size = size;
        this.name = name;
        this.desc = desc;
        return this;
    }

    @Override
    public void update(int now, String state) {
        this.state = now;
        if (!state.equals(desc))
            this.desc = state;
        //System.out.println(this.state + "\\" + this.size + "\\" +this.desc);

        if (this.state == this.size)
            this.onDone();
    }

    @Override
    public int getState() {
        return this.state;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public void onDone() {
        System.out.println("Progress bar " + name + " is done.");
    }

    @Override
    public boolean isDone()
    {
        return state == size;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }
}
