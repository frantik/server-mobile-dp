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
package com.dvdprime.server.mobile.listener;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dvdprime.server.mobile.factory.DaoFactory;
import com.dvdprime.server.mobile.worker.NotificationWorker;
import com.google.common.base.Stopwatch;

/**
 * 리소스 초기화 및 워밍업
 * 
 * @author 작은광명
 * @version 1.0
 * @created 2013. 10. 8. 오후 10:38:33
 * @history
 */
public class ResourceWarmupListener implements ServletContextListener {
    /** Logger */
    private Logger logger = LoggerFactory.getLogger(ResourceWarmupListener.class);

    /** 푸시 메시지 발송 매니저 */
    private NotificationWorker ncWorker = new NotificationWorker();

    /*
     * Context를 초기화 할 경우 작업을 처리한다. (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet .ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        Stopwatch sw = new Stopwatch().start();
        logger.info("---------------------------------------------------------------");
        logger.info("--- START RESTful");
        logger.info("---------------------------------------------------------------");

        try {
            logger.info("--- connect mysql  ---");
            DaoFactory.getInstance();
            logger.info("--- start notification manager ---");
            ncWorker.start();
            logger.info("------------------------------------------------------------------------");
            logger.info("SUCCESS RESTful");
        } catch (Exception e) {
            logger.error("caught a " + e.getClass() + " with message: " + e.getMessage(), e);
            logger.info("------------------------------------------------------------------------");
            logger.info("ERROR RESTful");
        } finally {
            logger.info("------------------------------------------------------------------------");
            logger.info("Total time: {}{}", sw.elapsed(TimeUnit.SECONDS), "s");
            logger.info("Finished at: {}", new Date());
            logger.info("------------------------------------------------------------------------");
        }
    }

    /*
     * Context가 종료될 경우 작업을 처리한다. (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet. ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ncWorker.shutdown();

        logger.debug(this.getClass().toString() + ".contextDestroyed");
    }

}
