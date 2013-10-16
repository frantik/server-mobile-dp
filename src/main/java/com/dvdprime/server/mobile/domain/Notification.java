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

import com.dvdprime.server.mobile.model.NotificationDTO;
import com.dvdprime.server.mobile.util.DateUtil;
import com.dvdprime.server.mobile.util.StringUtil;

/**
 * 알림 API 모델
 * 
 * @author 작은광명
 * @version 1.0
 * @created 2013. 10. 7. 오후 2:43:48
 * @history
 */
@Data
public class Notification {
    /** 글 제목 */
    private String title;

    /** 메시지 */
    private String message;

    /** 링크 URL */
    private String targetUrl;

    /** 해당 오브젝트 고유번호 */
    private String targetKey;

    /** 확인 여부 */
    private boolean readFlag;

    /** 등록 시간 */
    private long creationTime;

    // //////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // //////////////////////////////////////////////////////////////
    public Notification() {}

    public Notification(NotificationDTO dto) {
        this.title = dto.getTitle();
        this.message = StringUtil.replace(dto.getMessage(), "\n", " ");
        this.targetUrl = dto.getTargetUrl();
        this.targetKey = dto.getTargetId();
        this.readFlag = dto.getReadFlag().equals("01") ? true : false;
        this.creationTime = DateUtil.getTimestampByDecimal(dto.getCreationTime());
    }
}
