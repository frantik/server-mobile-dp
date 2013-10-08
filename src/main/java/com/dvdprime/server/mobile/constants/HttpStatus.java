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
 * Http Response Status Code를 정의한 클래스
 * 
 * @author 작은광명
 * @history
 */
public class HttpStatus
{
    
    /* 2xx Success */
    
    /**
     * 200 OK
     * 
     * <pre>
     * Standard response for successful HTTP requests. 
     * The actual response will depend on the request method used. 
     * In a GET request, the response will contain an entity corresponding to the requested resource. 
     * In a POST request the response will contain an entity describing or containing the result of the action.
     * </pre>
     */
    public static final int OK = 200;
    
    /**
     * 201 Created
     * 
     * <pre>
     * The request has been fulfilled and resulted in a new resource being created.
     * </pre>
     */
    public static final int CREATED = 201;
    
    /* 3xx Redirection */
    
    /**
     * 304 Not Modified
     * 
     * <pre>
     * Indicates the resource has not been modified since last requested. 
     * Typically, the HTTP client provides a header like the If-Modified-Since header to provide a time against which to compare. 
     * Using this saves bandwidth and reprocessing on both the server and client, 
     * as only the header data must be sent and received in comparison to the entirety of the page being re-processed by the server, 
     * then sent again using more bandwidth of the server and client.
     * </pre>
     */
    public static final int NOT_MODIFIED = 304;
    
    /* 4xx Client Error */
    
    /**
     * 400 Bad Request
     * 
     * <pre>
     * The request cannot be fulfilled due to bad syntax.
     * </pre>
     */
    public static final int BAD_REQUEST = 400;
    
    /**
     * 401 Unauthorized
     * 
     * <pre>
     * Similar to 403 Forbidden, but specifically for use when authentication is required and has failed or has not yet been provided. 
     * The response must include a WWW-Authenticate header field containing a challenge applicable to the requested resource.
     * </pre>
     */
    public static final int UNAUTHORIZED = 401;
    
    /**
     * 403 Forbidden
     * 
     * <pre>
     * The request was a valid request, but the server is refusing to respond to it. 
     * Unlike a 401 Unauthorized response, authenticating will make no difference. 
     * On servers where authentication is required, 
     * this commonly means that the provided credentials were successfully authenticated 
     * but that the credentials still do not grant the client permission to access the resource 
     * (e.g. a recognized user attempting to access restricted content).
     * </pre>
     */
    public static final int FORBIDDEN = 403;
    
    /**
     * 404 Not Found
     * 
     * <pre>
     * The requested resource could not be found but may be available again in the future.
     * Subsequent requests by the client are permissible.
     * </pre>
     */
    public static final int NOT_FOUND = 404;
    
    /* 5xx Server Error */
    
    /**
     * 500 Internal Server Error
     * 
     * <pre>
     * A generic error message, given when no more specific message is suitable.
     * </pre>
     */
    public static final int INTERNAL_SERVER_ERROR = 500;
    
    /**
     * 503 Service Unavailable
     * 
     * <pre>
     * The server is currently unavailable (because it is overloaded or down for maintenance). 
     * Generally, this is a temporary state.
     * </pre>
     */
    public static final int SERVICE_UNAVAILABLE = 503;
}
