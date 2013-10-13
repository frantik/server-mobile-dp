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
package com.dvdprime.server.mobile.constants;

/**
 * 정의 코드
 * 
 * @author 작은광명
 * @version 1.0
 * @created 2013. 10. 7. 오후 3:04:20
 * @history
 */
public class Const {
    /**
     * 활성화
     */
    public static final String ENABLED_ON = "01";

    /**
     * 비활성화
     */
    public static final String ENABLED_OFF = "02";

    /**
     * 알림 종류 - 댓글
     */
    public static final String TYPE_CMT = "01";

    /**
     * 알림 종류 - 쪽지
     */
    public static final String TYPE_MEMO = "02";

    /**
     * 알림 발송 상태 - 대기
     */
    public static final String STATUS_READY = "10";

    /**
     * 알림 발송 상태 - 발송중
     */
    public static final String STATUS_SENDING = "20";

    /**
     * 알림 발송 상태 - 발송완료
     */
    public static final String STATUS_COMPLETED = "90";

    /**
     * 알림 발송 상태 - 발송실패
     */
    public static final String STATUS_ERROR = "EE";

    /**
     * 읽지 않음
     */
    public static final String READ_FLAG_01 = "01";

    /**
     * 읽음
     */
    public static final String READ_FLAG_02 = "02";

}
