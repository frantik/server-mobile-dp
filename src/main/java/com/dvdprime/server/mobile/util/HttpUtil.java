package com.dvdprime.server.mobile.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {

    /** Logger */
    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    
    /** URL */
    private static URL url;
    
    /** URLConnection */
    private static URLConnection httpConn;
    
    /**
     * 파라미터를 포함한 POST HTTP 요청
     * 
     * @param urlString
     *            요청 URL
     * @param params
     *            요청 파라미터
     * @return
     * @throws IOException
     */
    public synchronized static String httpConnect(String urlString) throws IOException
    {
        StringBuffer sb = new StringBuffer();
        try
        {
            url = new URL(urlString);
            httpConn = url.openConnection();
            
            logger.info("request url: {}", url.toString());
            
            // 캐시 설정
            httpConn.setUseCaches(false);
            httpConn.setDefaultUseCaches(false);
            
            BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "euc-kr"));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
            {
                sb.append(inputLine);
            }
            in.close();
        }
        catch (MalformedURLException e)
        {
            throw e;
        }
        catch (IOException e)
        {
            throw e;
        }
        
        return sb.toString();
    }
    
}
