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

import lombok.Data;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 필터 파라미터
 * 
 * @author 작은광명
 * 
 */
@Data
public class FilterRequest {
    /** 요청 회원 아이디 */
    @JsonProperty("id")
    private String id;

    /** 타겟 회원 토큰 */
    @JsonProperty("targetId")
    private String targetId;

    /** 타겟 회원 닉네임 */
    @JsonProperty("targetNick")
    private String targetNick;

    // //////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // //////////////////////////////////////////////////////////////
    public FilterRequest() {}

    public FilterRequest(String id, String targetId, String targetNick) {
        this.id = id;
        this.targetId = targetId;
        this.targetNick = targetNick;
    }
}
