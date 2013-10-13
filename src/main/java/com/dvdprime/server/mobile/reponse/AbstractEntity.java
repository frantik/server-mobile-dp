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

import java.io.Serializable;

import lombok.Data;

import org.codehaus.jackson.annotate.JsonProperty;

import com.dvdprime.server.mobile.constants.HttpStatus;

/**
 * Abstract domain that defines common functionality.
 */
@Data
public abstract class AbstractEntity implements Serializable {

    /**
     * generated serial version ID
     */
    private static final long serialVersionUID = -5608408180849622006L;

    /** 결과 코드 */
    @JsonProperty
    private int status = HttpStatus.OK;

    /** 결과 메시지 */
    @JsonProperty
    private String message = "success";

}
