/**
 * Copyright 2013 작은광명
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dvdprime.server.mobile.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import com.google.common.collect.Maps;

/**
 * Properties XML 파일을 읽어온다.
 * 
 * @author 작은광명
 * @version 1.0
 * @created 2013. 10. 9. 오전 12:01:10
 * @history
 */
public class PropertiesUtil {

    private static Map<String, Map<String, Object>> resourcePropertiestMap = Maps.newHashMap();

    /**
     * db.properties.xml 파일을 읽어서 키 값을 Map에 설정한다.
     * 
     * @return Map<String, Object>
     */
    public static Map<String, Object> loadProperties(String path) {

        if (resourcePropertiestMap.get(path) == null) {
            Map<String, Object> resultMap = Maps.newHashMap();

            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            if (cl == null) {
                cl = ClassLoader.getSystemClassLoader();
            }
            URL url = cl.getResource(path);

            // 경로가 옳바르지 않을 경우 널을 리턴한다.
            if (url == null) {
                return null;
            }

            // 1. 프로퍼티 파일을 읽는다.
            Properties properties = new Properties();
            FileInputStream fileInputStream = null;
            try {
                if (new File(url.getPath()).exists()) {
                    fileInputStream = new FileInputStream(url.getPath());
                    properties.loadFromXML(fileInputStream);
                } else {
                    // 패스 경로에서 찾을 수 없을 경우 프로퍼티로 다시 찾기 시도한다.
                    Enumeration<URL> urls = cl.getResources(path);
                    while (urls.hasMoreElements()) {
                        url = (URL) urls.nextElement();
                        InputStream is = null;
                        try {
                            URLConnection con = url.openConnection();
                            con.setUseCaches(false);
                            is = con.getInputStream();
                            properties.loadFromXML(is);
                        } finally {
                            if (is != null) {
                                is.close();
                            }
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            // 2. 프로퍼티 파일을 루핑 돌면서 값을 전부 eventMap 에 담는다.
            Enumeration<?> enumeration = properties.keys();
            while (enumeration.hasMoreElements()) {
                String key = (String) enumeration.nextElement();
                resultMap.put(key, properties.get(key));
            }

            resourcePropertiestMap.put(path, resultMap);
        }

        return resourcePropertiestMap.get(path);
    }

}
