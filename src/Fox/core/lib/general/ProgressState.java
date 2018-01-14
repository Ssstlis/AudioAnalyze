package Fox.core.lib.general;

public interface ProgressState {
    int state = 0, size = 0;
    String desc = "", name = "";

    ProgressState create(int size, String name, String desc);
    void update(int now, String state);
    int getState();
    int getSize();
    String getName();
    String getDesc();
    void setSize(int size);
    void onDone();
    boolean isDone();
}
