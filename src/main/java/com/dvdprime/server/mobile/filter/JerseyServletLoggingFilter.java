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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.output.TeeOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

/**
 * <pre>
 * Restful Jersey에 요청한 Request Logging Filter
 * 
 * {@link http://www.wetfeetblog.com/servlet-filer-to-log-request-and-response-details-and-payload/431}
 * </pre>
 * 
 * @author 작은광명
 * @version 1.0
 * @created 2013. 10. 9. 오전 12:00:10
 * @history
 */
public class JerseyServletLoggingFilter implements Filter {
    /** Logging */
    private Logger logger = LoggerFactory.getLogger(JerseyServletLoggingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // This is doing simple logging and so doesn't need to
        // use or keep a reference to the filter config...
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        Stopwatch sw = new Stopwatch().start();

        // Filter에서 Request Parameters를 확인할 경우 Resource에서 @FormParam을 사용하여 파라미터를 받을 수 없다.
        // 그래서 POST 또는 PUT 전송일 경우에는 RequestWrapper를 사용하지 않는다.
        // 예외처리...
        if (httpServletRequest.getMethod().equalsIgnoreCase("post") || httpServletRequest.getMethod().equalsIgnoreCase("put")) {
            BufferedResponseWrapper bufferedResponse = new BufferedResponseWrapper(httpServletResponse);

            StringBuilder initLogMessage = new StringBuilder("REST Request - ").append("[HTTP METHOD:").append(httpServletRequest.getMethod()).append("] [PATH INFO:").append(httpServletRequest.getPathInfo()).append("] [REMOTE ADDRESS:").append(httpServletRequest.getRemoteAddr()).append("]").append(" - is being processed... ");
            logger.info(initLogMessage.toString());

            chain.doFilter(request, bufferedResponse);

            initLogMessage.setLength(0);
            initLogMessage.append("REST Response - ").append("[HTTP METHOD:").append(httpServletRequest.getMethod()).append("] [PATH INFO:").append(httpServletRequest.getPathInfo()).append("] [REMOTE ADDRESS:").append(httpServletRequest.getRemoteAddr()).append("]").append(" [RESPONSE:").append(bufferedResponse.getContent()).append("]").append(" - processed succesfully in ").append(sw.elapsed(TimeUnit.MILLISECONDS)).append("ms ");
            logger.info(initLogMessage.toString());
        } else {
            Map<String, String> requestMap = this.getTypesafeRequestMap(httpServletRequest);
            BufferedRequestWrapper bufferedReqest = new BufferedRequestWrapper(httpServletRequest);
            BufferedResponseWrapper bufferedResponse = new BufferedResponseWrapper(httpServletResponse);

            StringBuilder initLogMessage = new StringBuilder("REST Request - ").append("[HTTP METHOD:").append(httpServletRequest.getMethod()).append("] [PATH INFO:").append(httpServletRequest.getPathInfo()).append("] [REQUEST PARAMETERS:").append(requestMap).append("] [REQUEST BODY:").append(bufferedReqest.getRequestBody()).append("] [REMOTE ADDRESS:").append(httpServletRequest.getRemoteAddr()).append("]").append(" - is being processed... ");
            logger.info(initLogMessage.toString());

            chain.doFilter(bufferedReqest, bufferedResponse);

            initLogMessage.setLength(0);
            initLogMessage.append("REST Response - ").append("[HTTP METHOD:").append(httpServletRequest.getMethod()).append("] [PATH INFO:").append(httpServletRequest.getPathInfo()).append("] [REMOTE ADDRESS:").append(httpServletRequest.getRemoteAddr()).append("]").append(" [RESPONSE:").append(bufferedResponse.getContent()).append("]").append(" - processed succesfully in ").append(sw.elapsed(TimeUnit.MILLISECONDS)).append("ms ");
            logger.info(initLogMessage.toString());
        }
    }

    @Override
    public void destroy() {
        logger.info("REST Web Services Servlet Filter is now shutting down due to container shutdown.");
    }

    private Map<String, String> getTypesafeRequestMap(HttpServletRequest request) {

        Map<String, String> typesafeRequestMap = new HashMap<String, String>();

        Enumeration<?> requestParamNames = request.getParameterNames();

        while (requestParamNames.hasMoreElements()) {

            String requestParamName = (String) requestParamNames.nextElement();

            String requestParamValue = request.getParameter(requestParamName);

            typesafeRequestMap.put(requestParamName, requestParamValue);

        }

        return typesafeRequestMap;
    }

    private static final class BufferedRequestWrapper extends HttpServletRequestWrapper {
        private ByteArrayInputStream bais = null;

        private ByteArrayOutputStream baos = null;

        private BufferedServletInputStream bsis = null;

        private byte[] buffer = null;

        public BufferedRequestWrapper(HttpServletRequest req) throws IOException {
            super(req);

            // Read InputStream and store its content in a buffer.
            InputStream is = req.getInputStream();
            this.baos = new ByteArrayOutputStream();
            byte buf[] = new byte[1024];
            int letti;
            while ((letti = is.read(buf)) > 0) {
                this.baos.write(buf, 0, letti);
            }
            this.buffer = this.baos.toByteArray();
        }

        @Override
        public ServletInputStream getInputStream() {
            // Generate a new InputStream by stored buffer
            this.bais = new ByteArrayInputStream(this.buffer);
            // Istantiate a subclass of ServletInputStream
            // (Only ServletInputStream or subclasses of it are accepted by
            // the servlet engine!)
            this.bsis = new BufferedServletInputStream(this.bais);

            return this.bsis;
        }

        String getRequestBody() throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.getInputStream()));
            String line = null;
            StringBuilder inputBuffer = new StringBuilder();
            do {
                line = reader.readLine();
                if (null != line) {
                    inputBuffer.append(line.trim());
                }
            } while (line != null);
            reader.close();
            return inputBuffer.toString().trim();
        }
    }

    /*
     * Subclass of ServletInputStream needed by the servlet engine. All inputStream methods are wrapped and are delegated to the ByteArrayInputStream (obtained as constructor parameter)!
     */
    private static final class BufferedServletInputStream extends ServletInputStream {
        private ByteArrayInputStream bais;

        public BufferedServletInputStream(ByteArrayInputStream bais) {
            this.bais = bais;
        }

        @Override
        public int available() {
            return this.bais.available();
        }

        @Override
        public int read() {
            return this.bais.read();
        }

        @Override
        public int read(byte[] buf, int off, int len) {
            return this.bais.read(buf, off, len);
        }
    }

    public class TeeServletOutputStream extends ServletOutputStream {

        private final TeeOutputStream targetStream;

        public TeeServletOutputStream(OutputStream one, OutputStream two) {
            targetStream = new TeeOutputStream(one, two);
        }

        @Override
        public void write(int arg0) throws IOException {
            this.targetStream.write(arg0);
        }

        public void flush() throws IOException {
            super.flush();
            this.targetStream.flush();
        }

        public void close() throws IOException {
            super.close();
            this.targetStream.close();
        }
    }

    public class BufferedResponseWrapper implements HttpServletResponse {
        HttpServletResponse original;

        TeeServletOutputStream tee;

        ByteArrayOutputStream bos;

        public BufferedResponseWrapper(HttpServletResponse response) {
            original = response;
        }

        public String getContent() {
            if (bos != null) {
                return bos.toString();
            } else {
                return "";
            }
        }

        public PrintWriter getWriter() throws IOException {
            return original.getWriter();
        }

        public ServletOutputStream getOutputStream() throws IOException {
            if (tee == null) {
                bos = new ByteArrayOutputStream();
                tee = new TeeServletOutputStream(original.getOutputStream(), bos);
            }
            return tee;
        }

        @Override
        public String getCharacterEncoding() {
            return original.getCharacterEncoding();
        }

        @Override
        public String getContentType() {
            return original.getContentType();
        }

        @Override
        public void setCharacterEncoding(String charset) {
            original.setCharacterEncoding(charset);
        }

        @Override
        public void setContentLength(int len) {
            original.setContentLength(len);
        }

        @Override
        public void setContentType(String type) {
            original.setContentType(type);
        }

        @Override
        public void setBufferSize(int size) {
            original.setBufferSize(size);
        }

        @Override
        public int getBufferSize() {
            return original.getBufferSize();
        }

        @Override
        public void flushBuffer() throws IOException {
            tee.flush();
        }

        @Override
        public void resetBuffer() {
            original.resetBuffer();
        }

        @Override
        public boolean isCommitted() {
            return original.isCommitted();
        }

        @Override
        public void reset() {
            original.reset();
        }

        @Override
        public void setLocale(Locale loc) {
            original.setLocale(loc);
        }

        @Override
        public Locale getLocale() {
            return original.getLocale();
        }

        @Override
        public void addCookie(Cookie cookie) {
            original.addCookie(cookie);
        }

        @Override
        public boolean containsHeader(String name) {
            return original.containsHeader(name);
        }

        @Override
        public String encodeURL(String url) {
            return original.encodeURL(url);
        }

        @Override
        public String encodeRedirectURL(String url) {
            return original.encodeRedirectURL(url);
        }

        @SuppressWarnings("deprecation")
        @Override
        public String encodeUrl(String url) {
            return original.encodeUrl(url);
        }

        @SuppressWarnings("deprecation")
        @Override
        public String encodeRedirectUrl(String url) {
            return original.encodeRedirectUrl(url);
        }

        @Override
        public void sendError(int sc, String msg) throws IOException {
            original.sendError(sc, msg);
        }

        @Override
        public void sendError(int sc) throws IOException {
            original.sendError(sc);
        }

        @Override
        public void sendRedirect(String location) throws IOException {
            original.sendRedirect(location);
        }

        @Override
        public void setDateHeader(String name, long date) {
            original.setDateHeader(name, date);
        }

        @Override
        public void addDateHeader(String name, long date) {
            original.addDateHeader(name, date);
        }

        @Override
        public void setHeader(String name, String value) {
            original.setHeader(name, value);
        }

        @Override
        public void addHeader(String name, String value) {
            original.addHeader(name, value);
        }

        @Override
        public void setIntHeader(String name, int value) {
            original.setIntHeader(name, value);
        }

        @Override
        public void addIntHeader(String name, int value) {
            original.addIntHeader(name, value);
        }

        @Override
        public void setStatus(int sc) {
            original.setStatus(sc);
        }

        @SuppressWarnings("deprecation")
        @Override
        public void setStatus(int sc, String sm) {
            original.setStatus(sc, sm);
        }

        @Override
        public int getStatus() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public String getHeader(String name) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Collection<String> getHeaders(String name) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Collection<String> getHeaderNames() {
            // TODO Auto-generated method stub
            return null;
        }

    }
}
