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
 * MyBatis의 TypeAlias Properties 파일에 정의된 Resource
 * 
 * @author 작은광명
 * @version 1.0
 * @created 2013. 10. 4. 오후 5:27:45
 * @history
 */
public class TypeAliasProp {

    private static Map<String, Object> properties = null;

    public static Map<String, Object> getProperties() {
        if (properties == null) {
            properties = PropertiesUtil.loadProperties("dvdprime/properties/typealias.properties.xml");

            if (properties == null) {
                properties = new HashMap<String, Object>();
            }
            properties.put("ConfigDto", "com.dvdprime.server.mobile.model.ConfigDTO");
            properties.put("DeviceDto", "com.dvdprime.server.mobile.model.DeviceDTO");
            properties.put("FilterDto", "com.dvdprime.server.mobile.model.FilterDTO");
            properties.put("MemberDto", "com.dvdprime.server.mobile.model.MemberDTO");
            properties.put("NotificationDto", "com.dvdprime.server.mobile.model.NotificationDTO");
        }
        return properties;
    }

}
