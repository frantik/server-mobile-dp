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
package com.dvdprime.server.mobile.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * Restful Jersey에 요청한 Request Headers Logging Filter
 * 
 * {@link http://www.wetfeetblog.com/servlet-filer-to-log-request-and-response-details-and-payload/431}
 * </pre>
 * 
 * @author 작은광명
 * @version 1.0
 * @created 2013. 10. 8. 오후 11:59:59
 * @history
 */
public class JerseyAccessLoggingFilter implements Filter {
    /** Logging */
    private Logger logger = LoggerFactory.getLogger(JerseyAccessLoggingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // This is doing simple logging and so doesn't need to
        // use or keep a reference to the filter config...
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        Map<String, String> requestMap = this.getTypesafeRequestMap(httpServletRequest);

        final String logMessage = new StringBuilder("REST Request - ").append("[HTTP METHOD:").append(httpServletRequest.getMethod()).append("] [PATH INFO:").append(httpServletRequest.getPathInfo()).append("] [REQUEST HEADERS:").append(requestMap).append("] [REMOTE ADDRESS:").append(httpServletRequest.getRemoteAddr()).append("]").toString();

        logger.info(logMessage);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}

    private Map<String, String> getTypesafeRequestMap(HttpServletRequest request) {

        Map<String, String> typesafeRequestMap = new HashMap<String, String>();

        Enumeration<?> requestHeaderNames = request.getHeaderNames();

        while (requestHeaderNames.hasMoreElements()) {

            String requestHeaderName = (String) requestHeaderNames.nextElement();

            String requestHeaderValue = request.getHeader(requestHeaderName);

            typesafeRequestMap.put(requestHeaderName, requestHeaderValue);

        }

        return typesafeRequestMap;
    }

}
