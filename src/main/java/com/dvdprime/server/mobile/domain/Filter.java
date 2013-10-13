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
 * 필터 API 메돌
 * 
 * @author 작은광명
 * @version 1.0
 * @created 2013. 10. 7. 오후 2:21:19
 * @history
 */
@Data
public class Filter {
    /**
     * 대상 아이디
     */
    private String targetId;

    /**
     * 대상 닉네임
     */
    private String targetNick;

    // ///////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // ///////////////////////////////////////////////////////////////
    public Filter(String targetId, String targetNick) {
        this.targetId = targetId;
        this.targetNick = targetNick;
    }
}
