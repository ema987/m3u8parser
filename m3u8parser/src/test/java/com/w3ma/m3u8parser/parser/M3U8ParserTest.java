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

package com.w3ma.m3u8parser.parser;

import com.w3ma.m3u8parser.data.Playlist;
import com.w3ma.m3u8parser.exception.PlaylistParseException;
import com.w3ma.m3u8parser.scanner.M3U8ItemScanner;
import com.w3ma.m3u8parser.util.ResourcesUtil;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

import static org.junit.Assert.*;

/**
 * Created by Emanuele on 01/09/2016.
 */
public class M3U8ParserTest {

    private ResourcesUtil resourcesUtil;

    @Before
    public void setup() {
        resourcesUtil = new ResourcesUtil();
    }

    @Test
    public void parseTest() {
        final InputStream workingPlaylist = resourcesUtil.getInputStream(getClass(), "workingPlaylist.m3u8");
        assertNotNull(workingPlaylist);
        final M3U8Parser m3U8Parser = new M3U8Parser(workingPlaylist, M3U8ItemScanner.Encoding.UTF_8);
        try {
            final Playlist playlist = m3U8Parser.parse();
            assertNotNull(playlist);
            assertEquals(3, playlist.getTrackSetMap().size());
            assertEquals(2, playlist.getTrackSetMap().get("A").size());
            assertEquals(1, playlist.getTrackSetMap().get("B").size());
            assertEquals(1, playlist.getTrackSetMap().get("C").size());
        } catch (IOException | ParseException | PlaylistParseException e) {
            // the test has failed
            fail();
        } finally {
            IOUtils.closeQuietly(workingPlaylist);
        }
    }
}
