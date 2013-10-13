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

import com.dvdprime.server.mobile.request.FilterRequest;
import com.dvdprime.server.mobile.util.DateUtil;

/**
 * 필터 데이터 모델
 * 
 * @author 작은광명
 * @version 1.0
 * @created 2013. 10. 2. 오후 6:01:53
 * @history
 */
@Data
public class FilterDTO
{
    /**
     * 필터 고유번호
     */
    private int seq;
    
    /**
     * 회원 아이디
     */
    private String memberId;
    
    /**
     * 대상 아이디
     */
    private String targetId;
    
    /**
     * 대상 닉네임
     */
    private String targetNick;
    
    /**
     * 등록시간 (timestamp)
     */
    private long creationTime;
    
    /**
     * 등록시간 (Decimal)
     */
    private String creationDecimal;
    
    /**
     * 수정시간 (timestamp)
     */
    private long updatedTime;
    
    /**
     * 수정시간 (Decimal)
     */
    private String updatedDecimal;
    
    // //////////////////////////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // //////////////////////////////////////////////////////////////////////////////////
    public FilterDTO() {
    }
    
    public FilterDTO(String memberId)
    {
        this.memberId = memberId;
    }
    
    public FilterDTO(String memberId, String targetId)
    {
        this.memberId = memberId;
        this.targetId = targetId;
    }
    
    public FilterDTO(FilterRequest req)
    {
        this.memberId = req.getId();
        this.targetId = req.getTargetId();
        this.targetNick = req.getTargetNick();
        this.creationDecimal = DateUtil.getCurrentTimeDecimal();
        this.updatedDecimal = this.creationDecimal;
    }
}
