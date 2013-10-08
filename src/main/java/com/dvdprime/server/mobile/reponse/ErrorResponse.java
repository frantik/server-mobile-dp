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
package com.dvdprime.server.mobile.reponse;

import lombok.Data;

import org.codehaus.jackson.annotate.JsonProperty;

import com.dvdprime.server.mobile.constants.HttpStatus;

/**
 * 에러 반환 클래스
 *
 * @author 작은광명
 */
@Data
public class ErrorResponse
{
    
    @JsonProperty("status")
    private int error;
    
    @JsonProperty("message")
    private String errorMessage;
    
    public ErrorResponse()
    {
        super();
    }
    
    public ErrorResponse(String errorMessage)
    {
        this.error = HttpStatus.FORBIDDEN;
        this.errorMessage = errorMessage;
    }
    
    public ErrorResponse(int error, String errorMessage)
    {
        super();
        this.error = error;
        this.errorMessage = errorMessage;
    }
    
}
