# ProgressState abstract class
ProgressState is abstract class for simple event notification.

## Constructors
- `protected ProgressState(int size,String name,String desc) throws ProgressStateException`
   Create new instance of ProgressState class with same params.<br>May produce `ProgressStateException` when size < 0.

## Fields
- `protected int state;` - current state value of progress bar
- `protected int size;` - current size value of progress bar
- `protected String desc;` - current description value of progress bar
- `protected String name;` - name value of progress bar
- `private boolean Done = false;` - completion status

## Methods
- `public void update(int now, String desc) throws ProgressStateException` update state value and description value of current instance with new values.<br>May produce `ProgressStateException` when state < 0.
- `public void update(String desc)` increment state value and update description value of current instance with new value
- `public void update()` increment state value of ProgressState instance.
- `public int getState()` return state value of ProgressState instance.
- `public int getSize()` return size value of ProgressState instance.
- `public void setSize(int size) throws ProgressStateException` set new size of ProgressState instance.<br>May produce `ProgressStateException` when size <= 0.
- `public final String getName()` return name value of ProgressState instance.
- `public String getDesc()` return description value of ProgressState instance.
- `public boolean isDone()` return Done value of ProgressState instance.

## Abstract methods
- `protected abstract void onDone()` call when progress is done.
- `protected abstract void onResize()` call when bar maximum is changing.
- `protected abstract void onChange()` call when value is changing.

You must override these methods in your implementation to track changes. 