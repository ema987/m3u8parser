/*
 * Copyright 2016 Emanuele Papa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.w3ma.m3u8parser.writer;

import com.w3ma.m3u8parser.data.Playlist;
import com.w3ma.m3u8parser.exception.PlaylistParseException;
import com.w3ma.m3u8parser.parser.M3U8Parser;
import com.w3ma.m3u8parser.scanner.M3U8ItemScanner;
import com.w3ma.m3u8parser.util.ResourcesUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Emanuele on 06/09/2016.
 */
public class M3U8PlaylistWriterTest {

    private ResourcesUtil resourcesUtil;

    @Before
    public void setup() {
        resourcesUtil = new ResourcesUtil();
    }

    @Test
    public void getByteArrayOutputStreamTest() throws ParseException, IOException, PlaylistParseException {
        final InputStream workingPlaylistInputStream = resourcesUtil.getInputStream(getClass(), "workingPlaylist.m3u8");
        assertNotNull(workingPlaylistInputStream);

        final M3U8Parser m3U8Parser = new M3U8Parser(workingPlaylistInputStream, M3U8ItemScanner.Encoding.UTF_8);
        final Playlist playlist = m3U8Parser.parse();

        final M3U8PlaylistWriter m3U8PlaylistWriter = new M3U8PlaylistWriter();
        final ByteArrayOutputStream byteArrayOutputStream = m3U8PlaylistWriter.getByteArrayOutputStream(playlist);
        assertNotNull(byteArrayOutputStream);

        final File testPlaylistFile = new File("testPlaylist.m3u8");
        byteArrayOutputStream.writeTo(new FileOutputStream(testPlaylistFile));
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();

        final Stream<String> readWrittenPlaylistStream = Files.lines(Paths.get(testPlaylistFile.toURI()));
        assertNotNull(readWrittenPlaylistStream);

        final List<String> readWrittenPlaylistList = readWrittenPlaylistStream.collect(Collectors.toList());

        assertEquals(9, readWrittenPlaylistList.size());

        assertEquals("#EXTM3U", readWrittenPlaylistList.get(0));
        assertEquals("#EXTINF:0 tvg-id=\"\" tvg-name=\"Video1\" tvg-logo=\"\" group-title=\"A\", Video1", readWrittenPlaylistList.get(1));
        assertEquals("http://server.name/stream/to/video1", readWrittenPlaylistList.get(2));
        assertEquals("#EXTINF:0 tvg-id=\"\" tvg-name=\"Video3\" tvg-logo=\"\" group-title=\"A\", Video3", readWrittenPlaylistList.get(3));
        assertEquals("http://server.name/stream/to/video3", readWrittenPlaylistList.get(4));
        assertEquals("#EXTINF:0 tvg-id=\"\" tvg-name=\"Video2\" tvg-logo=\"\" group-title=\"B\", Video2", readWrittenPlaylistList.get(5));
        assertEquals("http://server.name/stream/to/video2", readWrittenPlaylistList.get(6));
        assertEquals("#EXTINF:0 tvg-id=\"\" tvg-name=\"Video4\" tvg-logo=\"\" group-title=\"C\", Video4", readWrittenPlaylistList.get(7));
        assertEquals("http://server.name/stream/to/video4", readWrittenPlaylistList.get(8));

        Files.deleteIfExists(testPlaylistFile.toPath());
    }
}
