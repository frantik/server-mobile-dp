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

import com.dvdprime.server.mobile.bo.DeviceBO;
import com.dvdprime.server.mobile.bo.NotificationBO;
import com.dvdprime.server.mobile.constants.Const;
import com.dvdprime.server.mobile.domain.Gcm;
import com.dvdprime.server.mobile.model.DeviceDTO;
import com.dvdprime.server.mobile.model.NotificationDTO;
import com.dvdprime.server.mobile.util.DateUtil;
import com.dvdprime.server.mobile.util.GsonUtil;
import com.dvdprime.server.mobile.util.StringUtil;
import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.common.base.Stopwatch;

/**
 * 푸시 발송 워커
 * 
 * @author 작은광명
 * @version 1.0
 * @created 2013. 10. 8. 오후 10:41:21
 * @history
 */
public class NotificationWorker {
    /** Logger */
    private Logger logger = LoggerFactory.getLogger(NotificationWorker.class);

    /** 개발자 콘솔에서 발급받은 API Key */
    private final String API_KEY = "AIzaSyBHVjsrUUjmza0ZI--wi0rNiMlfqcKVoXo";

    /** Should be represented as 1 or true for true, anything else for false. Optional. The default value is false. **/
    private final boolean DELAY_WHILE_IDLE = true;

    /**
     * How long (in seconds) the message should be kept on GCM storage if the device is offline. Optional (default time-to-live is 4 weeks, and must be set as a JSON number). 30 min.
     **/
    private final int TIME_TO_LIVE = 1800;

    // 메세지 전송 실패시 재시도할 횟수
    private final int RETRY = 3;

    private List<ScheduledThreadPoolExecutor> execList = new ArrayList<ScheduledThreadPoolExecutor>();

    public NotificationWorker() {}

    public void start() {
        ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
        execList.add(exec);

        exec.scheduleWithFixedDelay(new Connector(), 0, 30, TimeUnit.SECONDS);
    }

    public void shutdown() {
        for (ScheduledThreadPoolExecutor exec : execList) {
            List<Runnable> list = exec.shutdownNow();

            logger.info("Reloader Shutdown.. {}", list.toString());
        }
    }

    private class Connector implements Runnable {
        /** 서비스 시작 */
        private Stopwatch stopWatch = new Stopwatch().start();

        @Override
        public void run() {
            try {
                stopWatch.reset();
                NotificationBO bo = new NotificationBO();
                List<NotificationDTO> resultList = bo.searchNotificationSendList();

                if (resultList != null && !resultList.isEmpty()) {

                    logger.info("{}개의 푸시 메시지를 가져왔습니다. ({}ms)", resultList.size(), stopWatch.elapsed(TimeUnit.MILLISECONDS));

                    for (NotificationDTO dto : resultList) {
                        // 현재 시간을 세팅
                        dto.setUpdatedDecimal(DateUtil.getCurrentTimeDecimal());
                        // 기본적인 디바이스 토큰이 없을 경우 전송하지 않는다.
                        if (StringUtil.isBlank(dto.getToken())) {
                            dto.setStatus(Const.STATUS_ERROR);
                            bo.modifyNotificationOne(dto);
                            logger.info("디바이스 토큰이 없어서 푸시 발송 취소: 알림고유번호[{}], 회원[{}]", new Object[] { dto.getSeq(), dto.getMemberId() });
                            continue;
                        }
                        // 푸시 메시지 수신 비활성화 일경우 전송하지 않는다.
                        if (StringUtil.equals(dto.getEnabled(), Const.ENABLED_OFF)) {
                            dto.setStatus(Const.STATUS_ERROR);
                            bo.modifyNotificationOne(dto);
                            logger.info("알림 수신거부 설정으로 발송 취소: 알림고유번호[{}], 회원[{}]", new Object[] { dto.getSeq(), dto.getMemberId() });
                            continue;
                        }

                        // 기본 준비 상태로 변경
                        dto.setStatus(Const.STATUS_SENDING);
                        bo.modifyNotificationOne(dto);

                        try {
                            Message message = new Message.Builder().collapseKey("collapseKey" + System.currentTimeMillis()).timeToLive(TIME_TO_LIVE).delayWhileIdle(DELAY_WHILE_IDLE).addData("action", "comment").addData("msg", GsonUtil.toJson(new Gcm(dto))).build();

                            logger.debug("Android Push Device Token : {}", dto.getToken());
                            logger.info("GCM send data: {}", message);
                            // 단일전송시에 사용
                            Result result = new Sender(API_KEY).send(message, dto.getToken(), RETRY);

                            if (result.getMessageId() != null) {
                                dto.setStatus(Const.STATUS_COMPLETED);
                                logger.debug("Android Push canonicalRegId : {}", result.getCanonicalRegistrationId());
                                if (result.getCanonicalRegistrationId() != null) {
                                    // devices.get(i) has more than on registration ID: update database
                                    DeviceBO deviceBO = new DeviceBO();
                                    if (deviceBO.searchDeviceOne(null, result.getCanonicalRegistrationId()) != null) {
                                        deviceBO.removeDeviceOne(new DeviceDTO(dto.getMemberId(), dto.getToken()));
                                    } else {
                                        DeviceDTO device = deviceBO.searchDeviceOne(dto.getMemberId(), dto.getToken());
                                        if (device != null) {
                                            device.setToken(result.getCanonicalRegistrationId());
                                            device.setUpdatedDecimal(DateUtil.getCurrentTimeDecimal());
                                            deviceBO.modifyDeviceOne(device);
                                        }
                                    }
                                }
                            } else {
                                logger.info("Android Push Error Result : [{}]{}", dto.getSeq(), result.getErrorCodeName());
                                if (Constants.ERROR_NOT_REGISTERED.equals(result.getErrorCodeName())) {
                                    dto.setStatus(Const.STATUS_ERROR);
                                    // DB에서 디바이스 정보 삭제
                                    new DeviceBO().removeDeviceOne(new DeviceDTO(dto.getMemberId(), dto.getToken()));
                                } else {
                                    dto.setStatus(Const.STATUS_ERROR);
                                }
                            }
                        } catch (Exception e) {
                            dto.setStatus(Const.STATUS_ERROR);
                            logger.error("안드로이드 푸시 메시지 전송 중 에러가 발생했습니다.", e);
                        }
                        bo.modifyNotificationOne(dto);
                    }
                }
            } catch (Exception e) {
                logger.error("caught a " + e.getClass() + " with message: " + e.getMessage(), e);
            } finally {
                logger.info("푸시 메시지 발송 작업 소요 시간 -> {}ms", stopWatch.elapsed(TimeUnit.MILLISECONDS));
            }
        }
    }
}
