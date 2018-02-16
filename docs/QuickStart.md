# Quick start
Here you know how you can using library for yourself.
All methods are static locate in SearchLib class.

##### Look up cover arts
For looking up cover arts you need to know:
- Album title
- Artist name (optional)
- Target service
- Count of links

And call SearchLib static method SearchCovers with signature:
````java
public static AlbumArtCompilation SearchCovers(@NotNull String AlbumName, String ArtistName, target source, int count)
            throws llegalArgumentException,  NoMatchesException
````

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

Method throws:
- IllegalArgumentException when album title is empty or count < 1.
- NoMatchesException when service responses are empty or constains  


