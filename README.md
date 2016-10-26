M3U8Parser
=

M3U8Parser is a Java library which exposes classes to parse, merge and write M3U8 playlists.

This parser is based on http://ss-iptv.com/en/users/documents/m3u

It is a very simple library and it doesn't take care of all the attributes; it just parsers a playlist like the following one:
 

>  #EXTM3U  
>  #EXTINF:0 tvg-id="1" tvg-name="Name1" tvg-logo="http://mylogos.domain/name1logo.png" group-title="Group1", Name1  
>  http://server.name/stream/to/video1
>  #EXTINF:0 tvg-id="2" tvg-name="Name2" tvg-logo="http://mylogos.domain/name2logo.png" group-title="Group1", Name2  
>  http://server.name/stream/to/video2 
>  #EXTINF:0, tvg-id="3" tvg-name="Name3" tvg-logo="http://mylogos.domain/name3logo.png" group-title="Group2", Name3  
>  http://server.name/stream/to/video3

 
Main classes
-

 - M3U8Parser: it parsers a .m3u8 text file and generates a Playlist object.
 - PlaylistMerger: it merges two or more Playlist objects into one.
 - M3U8PlaylistWriter: it converts a Playlist object into a ByteArrayOutputStream which can be written to a file (or wherever) to have a .m3u8 playlist as the one parsed by M3U8Parser.

How to use it
-
Import the source code as a module in your project or download it, build the .jar and then import the .jar into your project.

**Parsing a playlist from file**
 Create an M3U8Parser object to parse a file and create a Playlist object:
 
 `M3U8Parser m3U8Parser = new M3U8Parser(new URL("URLPointingToThePlaylistFile").openStream(), M3U8ItemScanner.Encoding.UTF_8);
 Playlist playlist = m3U8Parser.parse();`

**Merge Playlists**
Create a PlaylistMerger object and merge two or more Playlist objects:
`PlaylistMerger playlistMerger = new PlaylistMerger();
Playlist playlist = playlistMerger.mergePlaylist(playlist1, playlist2);`

**Write Playlist**
Create a M3U8PlaylistWriter object and write the ByteArrayOutputStream where you need to:
`M3U8PlaylistWriter m3U8PlaylistWriter = new M3U8PlaylistWriter();
ByteArrayOutputStream byteArrayOutputStream = m3U8PlaylistWriter.getByteArrayOutputStream(playlist);`

Contributions
-
Pull requests are welcome

License
-
Apache License 2.0

