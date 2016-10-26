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

package com.w3ma.m3u8parser.merger;

import com.w3ma.m3u8parser.data.Playlist;
import com.w3ma.m3u8parser.exception.PlaylistParseException;
import com.w3ma.m3u8parser.parser.M3U8Parser;
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
public class M3U8MergerTest {

    private ResourcesUtil resourcesUtil;

    @Before
    public void setup() {
        resourcesUtil = new ResourcesUtil();
    }

    @Test
    public void parseTest() {
        final InputStream workingPlaylist1 = resourcesUtil.getInputStream(getClass(), "workingPlaylist1.m3u8");
        final InputStream workingPlaylist2 = resourcesUtil.getInputStream(getClass(), "workingPlaylist2.m3u8");
        final InputStream workingPlaylist3 = resourcesUtil.getInputStream(getClass(), "workingPlaylist3.m3u8");
        assertNotNull(workingPlaylist1);
        assertNotNull(workingPlaylist2);
        assertNotNull(workingPlaylist3);

        M3U8Parser m3U8Parser;
        final PlaylistMerger playlistMerger = new PlaylistMerger();
        try {
            m3U8Parser = new M3U8Parser(workingPlaylist1, M3U8ItemScanner.Encoding.UTF_8);
            final Playlist playlist1 = m3U8Parser.parse();
            assertNotNull(playlist1);

            m3U8Parser = new M3U8Parser(workingPlaylist2, M3U8ItemScanner.Encoding.UTF_8);
            final Playlist playlist2 = m3U8Parser.parse();
            assertNotNull(playlist2);

            final Playlist mergedPlaylist = playlistMerger.mergePlaylist(playlist1, playlist2);
            assertNotNull(mergedPlaylist);

            assertEquals(4, mergedPlaylist.getTrackSetMap().size());
            assertEquals(3, mergedPlaylist.getTrackSetMap().get("A").size());
            assertEquals(2, mergedPlaylist.getTrackSetMap().get("B").size());
            assertEquals(2, mergedPlaylist.getTrackSetMap().get("C").size());
            assertEquals(1, mergedPlaylist.getTrackSetMap().get("D").size());

            m3U8Parser = new M3U8Parser(workingPlaylist3, M3U8ItemScanner.Encoding.UTF_8);
            final Playlist playlist3 = m3U8Parser.parse();
            assertNotNull(playlist3);
            final Playlist mergedPlaylistWithSameElements = playlistMerger.mergePlaylist(mergedPlaylist, playlist3);
            assertNotNull(mergedPlaylistWithSameElements);

            assertEquals(4, mergedPlaylistWithSameElements.getTrackSetMap().size());
            assertEquals(3, mergedPlaylistWithSameElements.getTrackSetMap().get("A").size());
            assertEquals(2, mergedPlaylistWithSameElements.getTrackSetMap().get("B").size());
            assertEquals(2, mergedPlaylistWithSameElements.getTrackSetMap().get("C").size());
            assertEquals(1, mergedPlaylistWithSameElements.getTrackSetMap().get("D").size());

        } catch (IOException | ParseException | PlaylistParseException e) {
            // the test has failed
            fail();
        } finally {
            IOUtils.closeQuietly(workingPlaylist1);
            IOUtils.closeQuietly(workingPlaylist2);
            IOUtils.closeQuietly(workingPlaylist3);
        }
    }
}
