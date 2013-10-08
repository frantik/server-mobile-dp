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
package com.dvdprime.server.mobile.worker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 푸시 발송 워커
 *
 * @author 작은광명
 * @version 1.0
 * @created 2013. 10. 8. 오후 10:41:21
 * @history
 */
public class NotificationWorker
{
    /** Logger */
    private Logger logger = LoggerFactory.getLogger(NotificationWorker.class);
    
    private List<ScheduledThreadPoolExecutor> execList = new ArrayList<ScheduledThreadPoolExecutor>();
    
    public NotificationWorker()
    {
    }
    
    public void start()
    {
        ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
        execList.add(exec);
        
        exec.scheduleWithFixedDelay(new Connector(), 0, 30, TimeUnit.SECONDS);
    }
    
    public void shutdown()
    {
        for (ScheduledThreadPoolExecutor exec : execList)
        {
            List<Runnable> rList = exec.shutdownNow();
            
            logger.info("Reloader Shutdown.. {}", rList.toString());
        }
    }
    
    private class Connector implements Runnable
    {
        
        @Override
        public void run()
        {
            logger.info("--- 푸시 메시지 발송 워커 시작");
            
            logger.info("--- 푸시 메시지 발송 워커 끝");
        }
    }
}
