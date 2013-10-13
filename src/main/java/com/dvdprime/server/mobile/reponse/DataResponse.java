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
import lombok.EqualsAndHashCode;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

/**
 * Data 반환 클래스
 * 
 * @author 작은광명
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder({ "status", "message", "data" })
public class DataResponse extends AbstractEntity {
    /**
     * generated serial version ID
     */
    private static final long serialVersionUID = 2460697475380113544L;

    /** 목록 */
    private Object data;

    // ////////////////////////////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // ////////////////////////////////////////////////////////////////////////////////////
    public DataResponse() {}

    public DataResponse(Object data) {
        this.data = data;
    }

}
