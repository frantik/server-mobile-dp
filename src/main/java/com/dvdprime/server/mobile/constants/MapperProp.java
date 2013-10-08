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

import java.util.HashMap;
import java.util.Map;

import com.dvdprime.server.mobile.util.PropertiesUtil;

/**
 * MyBatis의 Mapper Properties 파일에 정의된 Resource
 * 
 * @author 작은광명
 * @version 1.0
 * @created 2013. 10. 4. 오후 5:25:26
 * @history
 */
public class MapperProp
{
    
    private static Map<String, Object> properties = null;
    
    public static Map<String, Object> getProperties()
    {
        if (properties == null)
        {
            properties = PropertiesUtil.loadProperties("dvdprime/properties/mapper.properties.xml");
            
            if (properties == null)
            {
                properties = new HashMap<String, Object>();
            }
            properties.put("configResultmap", "dvdprime/mapper/config_resultmap.xml");
            properties.put("configDefault", "dvdprime/mapper/config_default.xml");
            properties.put("deviceResultmap", "dvdprime/mapper/device_resultmap.xml");
            properties.put("deviceDefault", "dvdprime/mapper/device_default.xml");
            properties.put("filterResultmap", "dvdprime/mapper/filter_resultmap.xml");
            properties.put("filterDefault", "dvdprime/mapper/filter_default.xml");
            properties.put("notificationResultmap", "dvdprime/mapper/notification_resultmap.xml");
            properties.put("notificationDefault", "dvdprime/mapper/notification_default.xml");
        }
        return properties;
    }
    
}
