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
package com.dvdprime.server.mobile.domain;

import lombok.Data;

/**
 * 설정 API 모델
 * 
 * @author 작은광명
 * @version 1.0
 * @created 2013. 10. 7. 오후 1:43:02
 * @history
 */
@Data
public class Config {
    /**
     * 설정 종류
     */
    private String type;

    /**
     * 설정 활성화 여부 (true: 활성화, false: 비활성화)
     */
    private boolean enabled;

    // //////////////////////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // //////////////////////////////////////////////////////////////////////////////
    public Config(String type, String enabled) {
        this.type = type;
        this.enabled = enabled.equals("01") ? true : false;
    }
}
