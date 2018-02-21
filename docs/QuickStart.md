# Quick start
Here are some methods for using the library.<br>
All methods are static locate in SearchLib class.

### Logging
You can configure logging with props file or by using System.setProperty() depending on which implementation you import.<br>
You can get Logger with this instruction:
````java
org.slf4j.Logger loggername = org.slf4j.LoggerFactory.getLogger(SearchLib.class);
````

## Look up cover arts
To look up cover arts you need to know:
- Album title
- Artist name (optional)
- Target service
- Count of links

Call SearchLib static method "SearchCovers" by using this signature:
````java
public static AlbumArtCompilation SearchCovers(@NotNull String AlbumName, String ArtistName, target source, int count)
            throws llegalArgumentException,  NoMatchesException
````
### Return:
AlbumArtCompilation class instance contains:
- Album title
- Artist name
- List of Art class instances.

Art class instance contains:
- Cover url
- Cover size
- Artist name (for this instance)
- Album title (for this instance)
- Source (service that provided the cover)

Method may throws:
- IllegalArgumentException when album title is empty or *count* < 1.
- NoMatchesException when service responses or results are empty

### Typical usage pattern:
````java
package Foo;
//missing imports
public class Bar
{
    public static void main(String[] args)
    {
        String AlbumTitle = "Meteora";
        String ArtistName = "Linkin Park";
        try
        {
            AlbumArtCompilation SearchWithArtist = SearchLib.SearchCovers(AlbumTitle,
                                                                          ArtistName,
                                                                          target.MusicBrainz,
                                                                          5);
            
            AlbumArtCompilation SearchWitoutArtist = SearchLib.SearchCovers(AlbumTitle,
                                                                      null,
                                                                      target.MusicBrainz,
                                                                      5);
            if (SearchWithArtist.hasArtList())
                for (Art art : meteora.getArtList())
                    {
                        //do something....
                    }
                    
            if (SearchWithoutArtist.hasArtList())
                for (Art art : meteora.getArtList())
                    {
                        //do something....
                    }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
````

## Look up media tags
To look up media tags you need:
- [FingerPrint](FingerPrint.md) implementation
- From 4 to none [ProgressState](ProgressState.md) implementations
- Files path (few or one)
- Performance value
- Count of tags for each file

Call SearchLib static method SearchTags with signature:
````java
    @NotNull
    public static Entry<Map<String, List<ID3V2>>, List<String>> SearchTags(@NotNull List<String> Files, @NotNull FingerPrintThread YourFPCalcImplementation,
                                                                           ProgressState CheckerProgressBar, ProgressState FPProgressBar,
                                                                           ProgressState ServiceProgressBar, ProgressState CommonProgressBar,
                                                                           performance Speed, int count)
            throws InterruptedException, NoBuildException, IllegalArgumentException,
                   ProgressStateException, NoMatchesException, NoAccessingFilesException
````
or short signature for single file:
````java
    public static List<ID3V2> SearchTags(@NotNull String file,
                                         @NotNull FingerPrintThread YourFPCalcImplementation,
                                         ProgressState ProgressBar,
                                         int count)
                throws InterruptedException, IllegalArgumentException, ProgressStateException,
                       NoMatchesException, NoAccessingFilesException
````

### Fast signature explain
***ProgressState*** instances needs to notify you about process statuses inside the method.<br>
***CheckerProgressBar*** notifies you about file checking process status.(missing in signature for single file)<br>
***FPProgressBar*** notifies you about fingerprint build process status.(missing in signature for single file)<br>
***ServiceProgressBar*** notifies you about service build process status.(missing in signature for single file)<br>
***CommonProgressBar/ProgressBar*** notifies you about whole progress of process status.<br>  
***YourFingerPrintImplementation*** must be an instance of FingerPrintThread class or his child.<br>
***Speed*** is value of [performance enumeration](Performance.md). Value affects on the number of threads in the thread pool, higher value - more threads.(missing in signature for single file)<br>
***count*** value show how much tags will built for each file. If *count* equals 1, then library will be use smart search.

Methods may throw:
- IllegalArgumentException when you call methods with same instances of *ProgressState* or *count* < 1
- InterruptedException when you have problems with threads execute or await/sleep.
- ProgressStateException when an unexpected event occurs in processing.
- NoBuildException when files path list are empty
- NoAccessingFilesException when all files are rejected by checking.
- NoMatchesException when library has not found anything

***Return value by signature for List of String*** is instance of Map.Entry interface implementation.<br>
It contains:
- Map<String, List<[ID3V2](ID3V2.md)> > ***as key*** that contains:
  - String value that your ***YourFingerPrintImplementation*** inject in FingerPrint instance ***as key***
  - List<[ID3V2](ID3V2.md)> that contains list of founded and build tags data for track String ID provided by key in this Map ***as value***
- List<String> ***as value*** that contains rejected file path (not been tested for duration/format of the file or no results).

***Return value by signature for single file*** is instance of List<ID3V2>.<br>
It contains list of tags for the file you provided.

### Typical usage patterns:
````java
package Foo;
//missing imports
public class Bar
{
    public static void main(String[] args)
    {
        FingerPrintThread FPImplementation =  new FingerPrintThread()
        {
            //Override methods
        };
        ProgressState Line1 = new ProgressState(/*initial parameters*/)
        {
            //Override methods
        };
        ProgressState Line2 = new ProgressState(/*initial parameters*/)
        {
            //Override methods
        };
        ProgressState Line3 = new ProgressState(/*initial parameters*/)
        {
            //Override methods
        };
        ProgressState Line4 = new ProgressState(/*initial parameters*/)
        {
            //Override methods
        };
        
        List<String> FileList = new ArrayList<>();
        FileList.add("a.mp3");
        FileList.add("b.mp3");
        //and so on
        
        try
        {
            Entry<Map<String, List<ID3V2>>, List<String>> Result = SearchLib.SearchTags(FileList, FPImplementation, 
                                                                                        Line1, Line2, Line3, Line4,
                                                                                        performance.MAX, 5);
            Map<String, List<ID3V2>> key = Result.getKey();
            for(String elem : FileList)
                for(ID3V2 tag : key.get(elem)) 
                    {
                        //do something
                    }
        }
        catch(Exceprion e)
        {
            e.printStackTrace();
        }
        
        try
        {   Line1 = new ProgressState(/*initial parameters*/)
            {
                //Override methods
            };
            List<ID3V2> res = SearchLib.SearchTags(FileList.get(0), FPImplementation, Line1, 1);
            ID2V2 tag = res.get(0);
            //do something
        }
        catch (Exception e)
        {
            e.printStackTrace();        
        }
    }
}
````