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
package com.dvdprime.server.mobile.constants;

import java.util.Map;

import com.dvdprime.server.mobile.util.PropertiesUtil;

/**
 * DB properties 파일에 정의된 Resource
 *
 * @author 작은광명
 * @version 1.0
 * @created 2013. 10. 8. 오후 10:23:45
 * @history
 */
public class DBProp
{
    
    private static Map<String, Object> dbProperties = null;
    
    private static Map<String, Object> getDbProperties()
    {
        if (dbProperties == null)
        {
            dbProperties = PropertiesUtil.loadProperties("dvdprime/properties/db.properties.xml");
        }
        
        return dbProperties;
    }
    
    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // DB Properties
    //
    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /** MySQL Driver Class Name */
    public static final String MYSQL_DRIVER_NAME = "com.mysql.jdbc.Driver";
    
    /** MySQL Master DB URL */
    public static final String MYSQL_URL = (String) getDbProperties().get("jdbc.mysql.url");
    
    /** MySQL Master DB User Name */
    public static final String MYSQL_USER = (String) getDbProperties().get("jdbc.mysql.username");
    
    /** MySQL Master DB Password */
    public static final String MYSQL_PASSWORD = (String) getDbProperties().get("jdbc.mysql.password");
    
}
