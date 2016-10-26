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

package com.w3ma.m3u8parser.util;

import org.junit.Assert;

import java.io.InputStream;

/**
 * Created by Emanuele on 01/09/2016.
 */
public class ResourcesUtil {

    public InputStream getInputStream(final Class clazz, final String resourceName) {
        final InputStream inputStream = clazz.getResourceAsStream(resourceName);
        Assert.assertNotNull("File " + resourceName + " not found in the classpath of the class " + clazz, inputStream);
        return inputStream;
    }

}
