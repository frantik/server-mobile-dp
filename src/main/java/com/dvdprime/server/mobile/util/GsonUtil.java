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

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

/**
 * <pre>
 * Gson을 사용한 JSON 변환 유틸
 * 
 * {@link https://sites.google.com/site/gson/gson-user-guide}
 * 
 * @author 작은광명
 * </pre>
 */
public class GsonUtil
{
    private static final Gson gson = new Gson();
    
    /**
     * JSON String 으로 변환
     * 
     * @param src
     *            Object
     * @return
     */
    public static String toJson(Object src)
    {
        if (src == null) { return null; }
        
        return gson.toJson(src);
    }
    
    /**
     * Json String을 class로 변환
     * 
     * @param json
     *            json string
     * @param clazz
     *            변환할 클래스
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz)
    {
        if (json == null) { return null; }
        
        return gson.fromJson(json, clazz);
    }
    
    /**
     * Json Array로 변환
     * 
     * @param json
     *            json string
     * @return
     */
    public static JsonArray getAsJsonArray(String json)
    {
        if (json == null) { return null; }
        
        return new JsonParser().parse(json).getAsJsonArray();
    }
    
    /**
     * Json String을 특정 클래스 리스트로 변환
     * 
     * @param json
     *            json string
     * @param clazz
     *            class
     * @return
     */
    public static <T> List<T> getArrayList(String json, Class<T> clazz)
    {
        List<T> mResult = null;
        JsonArray jsonArray = getAsJsonArray(json);
        if (jsonArray != null && !jsonArray.isJsonNull())
        {
            mResult = new ArrayList<T>();
            for (int i = 0; i < jsonArray.size(); i++)
            {
                mResult.add(gson.fromJson(jsonArray.get(i), clazz));
            }
        }
        
        return mResult;
    }
}
