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
 * 응답 메시지 정의
 * 
 * @author 작은광명
 *
 */
public class ResponseMessage
{
    private static final String DEFAULT_MSG = "{\"status\":%s, \"message\":\"%s\"}";
    
    /* --------------------------------------------------------------------------------- */
    /* 공통 메시지 */
    /* --------------------------------------------------------------------------------- */
    /** 요청 성공 */
    public static final String SUCCESS = String.format(DEFAULT_MSG, HttpStatus.OK, "success");
    
    /** 요청 실패 */
    public static final String FAIL = String.format(DEFAULT_MSG, HttpStatus.OK, "fail");
    
    /** 존재하지 않는 요청 또는 잘못된 요청 */
    public static final String NOT_FOUND = String.format(DEFAULT_MSG, HttpStatus.BAD_REQUEST, "잘못된 요청입니다.");
    
    /** 내부 장애 또는 오류 */
    public static final String SERVER_ERROR = String.format(DEFAULT_MSG, HttpStatus.INTERNAL_SERVER_ERROR, "예상치 못한 장애가 발생했습니다. 잠시후에 다시 시도해주세요.");

}
