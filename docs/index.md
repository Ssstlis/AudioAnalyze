# Audio analyze 
This library provides convenient methods for music tags and album covers searching.<br>
It is platform independent and requires you to implement few interfaces from itself.<br>
The library uses its own data sorting and filtering methods for compatibility with Android.<br>
For quick start, you need to download the [latest release](https://github.com/Ssstlis/AudioAnalyze/releases/latest) and slf4j logger implementation for your platform.<br>

## All documentation
- [Quick start](QuickStart.md)
- [ProgressState class](ProgressState.md)
- [FingerPrint interface](FingerPrint.md)
- [ ] [Samples](Samples.md)
- [Future tools](Future.md)

## Dependencies
- [Google GSON 2.8.2](https://github.com/google/gson/releases/)
- [okhttp 3.9.1](https://github.com/square/okhttp/releases/)
- [okio     1.13.0](https://github.com/square/okio/releases/)
- [slf4j 1.7.25](https://www.slf4j.org/dist/slf4j-1.7.25.zip)
- [MusicBrainzAndroid](https://github.com/Ssstlis/AudioAnalyze/releases/download/v1.0.0/MusicBrainzAndroid.jar)
- [JAudioTagger 2.2.3](https://github.com/Ssstlis/AudioAnalyze/releases/download/v1.0.0/jaudiotagger-2.2.3.jar)

### Logging
You can configure logging with props file or by using System.setProperty() depending on which implementation you import.<br>
You can get Logger with this instruction:
````java
org.slf4j.Logger loggername = org.slf4j.LoggerFactory.getLogger(SearchLib.class);
````

### Small examples
Album covers look up:
```java
package Foo;

public class Bar
{
    public static void main(String[] args)
    {
        try
        {
            final Logger logger = LoggerFactory.getLogger(SearchLib.class);
            AlbumArtCompilation meteora = SearchLib.SearchCovers("Meteora", null, target.MusicBrainz, 5);
            String success = (meteora != null && meteora.hasArtList()) ? "good" : "bad";
            if (logger.isInfoEnabled())
                logger.info("{}", success);
        }
        catch (Exception e)
        {
            if (logger.isErrorEnabled())
                logger.error("", e);
        }
    }
}

```
Tracks tags look up:
```java
package Foo;

public class someclass
{
    public static void main(String[] args)
    {
        Entry<Map<String, List<ID3V2>>, List<String>> Result = null;
        try
        {
            final Logger logger = LoggerFactory.getLogger(SearchLib.class);
            String mp3location = "D:\\music\\Born Handed";
            List<File> FileList = ExecutableHelper.GetFileList(mp3location, new Mp3Filter());
            
            ProgressState Line1 = new CustomProgressState(0, "checker", "checker");
            ProgressState Line2 = new CustomProgressState(0, "FP", "FP");
            ProgressState Line3 = new CustomProgressState(0, "Service", "Service");
            ProgressState Line4 = new CustomProgressState(0, "Common", "Common");
            
            long temp = System.currentTimeMillis();
            Result = SearchLib.SearchTags(ExecutableHelper.FilesToStrings(FileList), new WindowsFPcalc(),
                                          Line1, Line2, Line3, Line4, performance.MAX, true, 5);
            
            temp = System.currentTimeMillis() - temp;
            String success = Result.getKey().size() == FileList.size() ? "good" : "bad";
            if (logger.isInfoEnabled())
                logger.info("{} {}", temp, success);
        }
        catch (Exception e)
        {
            if (logger.isErrorEnabled())
                logger.error("", e);
        }
    }
}
```

## License
````
MIT License

Copyright (c) 2018 Ivan Aristov (Ssstlis/Fox)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
````