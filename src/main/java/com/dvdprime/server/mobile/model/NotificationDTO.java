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

import com.dvdprime.server.mobile.request.NotificationRequest;
import com.dvdprime.server.mobile.util.DateUtil;

/**
 * 알림 데이터 모델
 * 
 * @author 작은광명
 * @version 1.0
 * @created 2013. 10. 2. 오후 6:01:53
 * @history
 */
@Data
public class NotificationDTO
{
    /**
     * 알림 고유번호
     */
    private int seq;
    
    /**
     * 회원 아이디
     */
    private String memberId;
    
    /**
     * 알림 종류
     */
    private String type;
    
    /**
     * 알림 중복방지키
     */
    private String dups;
    
    /**
     * 알림 제목
     */
    private String title;
    
    /**
     * 알림 메시지
     */
    private String message;
    
    /**
     * 대상 URL
     */
    private String targetUrl;
    
    /**
     * 대상 ID
     */
    private String targetId;
    
    /**
     * 발송 상태 (10:대기, 20:발송중, 90:발송완료, EE:발송실패)
     */
    private String status;
    
    /**
     * 확인 여부 (01:미확인, 02:확인)
     */
    private String readFlag;
    
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
    
    /**
     * 확인 안한 알림 갯수
     */
    private int unreadCount;
    
    /**
     * 디바이스 토큰
     */
    private String token;
    
    /**
     * 디바이스 버전
     */
    private String version;
    
    /**
     * 알림 수신 활성화 여부
     */
    private String enabled;
    
    /**
     * offset
     */
    private int offset;
    
    /**
     * 리스트 제한 갯수
     */
    private int limitCount;
    
    // /////////////////////////////////////////////////////////////////////////////
    //
    //
    //
    // /////////////////////////////////////////////////////////////////////////////
    public NotificationDTO()
    {
    }
    
    public NotificationDTO(NotificationRequest req)
    {
        this.memberId = req.getId();
        this.offset = (req.getPage() - 1) * req.getLimit();
        this.limitCount = req.getLimit();
        this.creationDecimal = req.getStartTime() > 0 ? DateUtil.getCurrentTimeDecimal(req.getStartTime()) : DateUtil.getCurrentTimeDecimal();
    }
    
    public NotificationDTO(String memberId, NotificationRequest req)
    {
        this.memberId = memberId;
        this.type = req.getType();
        this.title = req.getTitle();
        this.message = req.getMessage();
        this.targetUrl = req.getTargetUrl();
        this.targetId = req.getTargetKey();
        this.creationDecimal = DateUtil.getCurrentTimeDecimal();
        this.updatedDecimal = this.creationDecimal;
    }
    
}
