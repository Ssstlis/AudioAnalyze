# AudioAnalyze
Artworks and tags look up library.<br>
The library uses own data sorting and filtering methods for compatibility with Android.<br>
For start using you need to attach dependencies listed in the [latest release](https://github.com/Ssstlis/AudioAnalyze/releases/latest) and slf4j logger implementation for your platform.
[Main documentation and guidelines](https://ssstlis.github.io/AudioAnalyze/)

## Search covers
To get album covers using a static method SearchCovers from SearchLib class, Fox.core.main package.<br>
Necessary params:
- Album title
- Target service
- Count of links

Option params:
- Artist name.

Return instance that contains:
- Request params and list of Arts

### Example
```java
public class testing
{
    private static final Logger logger = LoggerFactory.getLogger(SearchLib.class);
    public static void main(String[] args)
    {
        try
        {
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
## Search tags
To get media tags using a static method SearchTags from SearchLib class, Fox.core.main package.<br>
Necessary params:
- 4 different instance of ProgressState (they will be notify you about processing)
- Your own implementation of FingerPrintThread
- List of String (represents file paths)
- Value of performance (influence on number of threads)
- Operation mode
- Number of unique result for each file

Return instance of entry that contains results:
- Map of entries file path as key and list of tags as value as key
- List of file paths as value

### Example
```java
public class testing
{
    private static final Logger logger = LoggerFactory.getLogger(SearchLib.class);
    public static void main(String[] args)
    {
        Entry<Map<String, List<ID3V2>>, List<String>> Result = null;
        try
        {
            String mp3location = "D:\\music\\Born Handed";
            List<File> FileList = ExecutableHelper.GetFileList(mp3location, new FileFilter()
            {
                @Override
                public boolean accept(File pathname)
                {
                    return true;
                }
            });
            
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
