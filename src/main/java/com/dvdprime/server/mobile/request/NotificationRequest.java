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
package com.dvdprime.server.mobile.request;

import java.util.Date;

import lombok.Data;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 알림 파라미터
 * 
 * @author 작은광명
 * 
 */
@Data
public class NotificationRequest {
    /** 회원 아이디 */
    @JsonIgnore
    private String id;

    /** 회원 아이디 목록(콤마 구분) */
    @JsonIgnore
    private String ids;

    /**
     * 알림 종류
     */
    @JsonIgnore
    private String type;

    /** 글 제목 */
    @JsonIgnore
    private String title;

    /** 메시지 */
    @JsonIgnore
    private String message;

    /** 링크 URL */
    @JsonIgnore
    private String targetUrl;

    /** 해당 오브젝트 고유번호 */
    @JsonIgnore
    private String targetKey;

    /** 페이지 */
    private int page;

    /** 한페이지 표시 갯수 */
    private int limit;

    /** 목록 호출용 시작 시간 */
    private long startTime;

    // //////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // //////////////////////////////////////////////////////////////
    public NotificationRequest() {}

    public NotificationRequest(String ids, String type, String title, String message, String targetUrl, String targetKey) {
        this.ids = ids;
        this.type = type;
        this.title = title;
        this.message = message;
        this.targetUrl = targetUrl;
        this.targetKey = targetKey;
    }

    public NotificationRequest(String id, int page, int limit, long startTime) {
        this.id = id;
        this.page = page;
        this.limit = limit;
        this.startTime = startTime == 0L ? new Date().getTime() : startTime;
    }
}
