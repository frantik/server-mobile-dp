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
package com.dvdprime.server.mobile.model;

import lombok.Data;

import com.dvdprime.server.mobile.util.DateUtil;

/**
 * 설정 데이터 모델
 * 
 * @author 작은광명
 * @version 1.0
 * @created 2013. 10. 2. 오후 6:01:53
 * @history
 */
@Data
public class ConfigDTO {
    /**
     * 설정 고유번호
     */
    private int seq;

    /**
     * 회원 아이디
     */
    private String memberId;

    /**
     * 설정 종류
     */
    private String type;

    /**
     * 설정 활성화 여부 (01: 활성화, 02: 비활성화)
     */
    private String enabled;

    /**
     * 등록시간 (timestamp)
     */
    private long creationTime;

    /**
     * 등록시간 (decimal)
     */
    private String creationDecimal;

    /**
     * 수정시간 (timestamp)
     */
    private long updatedTime;

    /**
     * 수정시간 (decimal)
     */
    private String updatedDecimal;

    // //////////////////////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // //////////////////////////////////////////////////////////////////////////////
    public ConfigDTO() {}

    public ConfigDTO(String memberId) {
        this.memberId = memberId;
    }

    public ConfigDTO(String memberId, String type, boolean enabled) {
        this.memberId = memberId;
        this.type = type;
        this.enabled = enabled ? "01" : "02";
        this.creationDecimal = DateUtil.getCurrentTimeDecimal();
        this.updatedDecimal = this.creationDecimal;
    }
}
