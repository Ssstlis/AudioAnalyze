# ID3V2 class

The class provides an encapsulation of the metadata for music tracks.<br>

## Constructors
- `public ID3V2()` empty constructor
- `public ID3V2(String title, String artistMBID, String trackMBID,
        String albumMBID, String year, String artist,
        String album, String comment, String tag,
        Integer number, List<String> ArtLinks)`<br>constructor with parameters<br>
        

## Methods
- `public String getAlbum()` return Album title
- `public String getAlbumMBID()` return Album MBID
- `public String getArtist()` return Artist name
- `public String getArtistMBID()` return Artist MBID
- `public List<String> getArtLinks()` return List of Album covers
- `public String getComment()` return comment for track
- `public Integer getNumber()` return track position in album
- `public String getTag()` return tag for track or album
- `public String getTitle()` return track title
- `public String getTrackMBID()` return track MBID
- `public String getYear()` return year of track or album
- `public boolean hasAlbum()` return true if Album title not null and not empty, else in other case
- `public boolean hasAlbumMBID()` return true if Album MBID not null and not empty, else in other case
- `public boolean hasArtist()` return true if Artist name not null and not empty, else in other case
- `public boolean hasArtistMBID()` return true if Artist MBId not null and not empty, else in other case
- `public boolean hasArtLinks()` return true if List of Album covers not null and not empty, else in other case
- `public boolean hasComment()` return true if comment for track not null and not empty, else in other case
- `public boolean hasNumber()` return true if track position in album not null and not empty, else in other case
- `public boolean hasTag()` return true if tag for track or album not null and not empty, else in other case
- `public boolean hasTitle()` return true if title not null and not empty, else in other case
- `public boolean hasTrackMBID()` return true if track MBID not null and not empty, else in other case
- `public boolean hasYear()` return true if year of track or album not null and not empty, else in other case
- `public void setAlbum(String album)` set Album title
- `public void setAlbumMBID(String albumMBID)` set Album MBID
- `public void setArtist(String artist)` set Artist name
- `public void setArtistMBID(String artistMBID)` set Artist MBID
- `public void setArtLinks(List<String> artLinks)` set List of Album covers
- `public void setComment(String comment)` set comment for track
- `public void setNumber(Integer number)` set track position in album
- `public void setTag(String tag)` set tag for track or album
- `public void setTitle(String title)` set title
- `public void setTrackMBID(String trackMBID)` set track MBID
- `public void setYear(String year)` set year of track or album