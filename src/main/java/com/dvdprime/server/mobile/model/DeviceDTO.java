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

import com.dvdprime.server.mobile.request.DeviceRequest;
import com.dvdprime.server.mobile.util.DateUtil;

/**
 * 디바이스 데이터 모델
 * 
 * @author 작은광명
 * @version 1.0
 * @created 2013. 10. 2. 오후 6:01:53
 * @history
 */
@Data
public class DeviceDTO {
    /**
     * 디바이스 고유번호
     */
    private int seq;

    /**
     * 회원 아이디
     */
    private String memberId;

    /**
     * 디바이스 토큰
     */
    private String token;

    /**
     * 디바이스 버전
     */
    private String version;

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

    // ////////////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // ////////////////////////////////////////////////////////////////////
    public DeviceDTO() {}

    public DeviceDTO(String id, String token) {
        this.memberId = id;
        this.token = token;
    }

    public DeviceDTO(DeviceRequest req) {
        this.memberId = req.getId();
        this.token = req.getDeviceToken();
        this.version = req.getVersion();
        this.creationDecimal = DateUtil.getCurrentTimeDecimal();
        this.updatedDecimal = this.creationDecimal;
    }
}
