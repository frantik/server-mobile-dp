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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dvdprime.server.mobile.bo.NotificationBO;
import com.dvdprime.server.mobile.constants.Const;
import com.dvdprime.server.mobile.model.NotificationDTO;
import com.dvdprime.server.mobile.util.StringUtil;
import com.google.common.base.Stopwatch;

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
    
    /** Thread 실행 서비스 */
    private ExecutorService executorService = null;
    
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
            List<Runnable> list = exec.shutdownNow();
            
            logger.info("Reloader Shutdown.. {}", list.toString());
        }
    }
    
    private class Connector implements Runnable
    {
        /** 서비스 시작 */
        private Stopwatch stopWatch = new Stopwatch().start();
        
        @Override
        public void run()
        {
            try
            {
                NotificationBO bo = new NotificationBO();
                List<NotificationDTO> resultList = bo.searchNotificationSendList();
                
                if (resultList != null && !resultList.isEmpty())
                {
                    
                    logger.info("{}개의 푸시 메시지를 가져왔습니다. ({}ms)", resultList.size(), stopWatch.elapsed(TimeUnit.MILLISECONDS));
                    if (executorService == null) executorService = Executors.newFixedThreadPool(3);
                    
                    for (NotificationDTO dto : resultList)
                    {
                        // 기본적인 디바이스 토큰이 없을 경우 전송하지 않는다.
                        if (StringUtil.isBlank(dto.getToken()))
                        {
                            dto.setStatus(Const.STATUS_ERROR);
                            bo.modifyNotificationOne(dto);
                            logger.info("디바이스 토큰이 없어서 푸시 발송 취소: 알림고유번호[{}], 회원[{}]", new Object[] { dto.getSeq(), dto.getMemberId() });
                            continue;
                        }
                        // 푸시 메시지 수신 비활성화 일경우 전송하지 않는다.
                        if (StringUtil.equals(dto.getEnabled(), Const.ENABLED_OFF))
                        {
                            dto.setStatus(Const.STATUS_ERROR);
                            bo.modifyNotificationOne(dto);
                            logger.info("알림 수신거부 설정으로 발송 취소: 알림고유번호[{}], 회원[{}]", new Object[] { dto.getSeq(), dto.getMemberId() });
                            continue;
                        }
                        
                        // 기본 준비 상태로 변경
                        dto.setStatus(Const.STATUS_SENDING);
                        bo.modifyNotificationOne(dto);
                        
                        // GCM 메시지 전송 워커 탑재
                        executorService.submit(new AndroidSendWorker(dto));
                    }
                }
            }
            catch (Exception e)
            {
                logger.error("caught a " + e.getClass() + " with message: " + e.getMessage(), e);
            }
            finally
            {
                if (executorService != null)
                {
                    // This will make the executor accept no new threads
                    // and finish all existing threads in the queue
                    executorService.shutdown();
                    // Wait until all threads are finish
                    while (!executorService.isTerminated())
                    {
                    }
                    logger.info("푸시 메시지 발송 작업 소요 시간 -> {}ms", stopWatch.elapsed(TimeUnit.MILLISECONDS));
                }
                else
                {
                    logger.info("발송 메시지가 없습니다. 작업 소요 시간 -> {}ms", stopWatch.elapsed(TimeUnit.MILLISECONDS));
                }
            }
        }
    }
}
