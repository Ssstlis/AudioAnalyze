# FingerPrint

## FingerPrintThread interface
The FingerPrintThread interface provides the ability to define its own implementation of media file fingerprint creation.<br>
You must use a [Chromaprint](https://acoustid.org/chromaprint) library to build instance of FingerPrint class.

### Interface signature
```java
public interface FingerPrintThread
{
    FingerPrint getFingerPrint(@NotNull String location) throws FingerPrintProcessingException;
}
```
Method should throw FingerPrintProcessingException when you catching another exception while Chromaprint is running.

### FingerPrint class
FingerPrint class provide to encapsulate result of Chromaprint library.<br>
You need to create and fill an instance in accordance with the data that is returned by Chromaprint library without modifications.<br>
#### Short class definition
```java
public class FingerPrint
{
    private String print = "";
    private String duration = "";
    private String location = "";

    public FingerPrint(@NotNull String print, @NotNull String duration, @NotNull String location)
    {
        this.print = print;
        this.duration = duration;
        this.location = location;
    }
    
    public FingerPrint(){}
    //getters, setters...
}
```
### Fields:
- location must be the path of the file that you used to create the fingerprint
- print must be a fingerprint, created by Chromaprint
- duration must be a duration, created by Chromaprint