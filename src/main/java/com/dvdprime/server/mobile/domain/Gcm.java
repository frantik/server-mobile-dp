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

/**
 * GCM 모델
 * 
 * @author 작은광명
 * 
 */
@Data
public class Gcm {
    /** 글 제목 */
    private String title;

    /** 메시지 */
    private String message;

    /** 링크 URL */
    private String targetUrl;

    /** 해당 오브젝트 고유번호 */
    private String targetKey;

    /** 확인 안한 알림 갯수 */
    private int count;

    // //////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // //////////////////////////////////////////////////////////////
    public Gcm() {}

    public Gcm(NotificationDTO dto) {
        this.title = dto.getTitle();
        this.message = dto.getMessage();
        this.targetUrl = dto.getTargetUrl();
        this.targetKey = dto.getTargetId();
        this.count = dto.getUnreadCount();
    }
}
