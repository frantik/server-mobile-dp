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

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dvdprime.server.mobile.bo.DeviceBO;
import com.dvdprime.server.mobile.bo.NotificationBO;
import com.dvdprime.server.mobile.constants.Const;
import com.dvdprime.server.mobile.model.DeviceDTO;
import com.dvdprime.server.mobile.model.NotificationDTO;
import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

/**
 * 안드로이드 메시지 발송 워커
 * 
 * @author 작은광명
 * @version 1.0
 * @created 2013. 10. 12. 오후 2:45:41
 * @history
 */
public class SendGCMWorker implements Callable<NotificationDTO>
{
    /** Logger */
    private Logger logger = LoggerFactory.getLogger(SendGCMWorker.class);
    
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
    
    private NotificationDTO data;
    
    /**
     * Constructor
     * 
     * @param PushNotificationDTO
     *            전송 데이터
     */
    public SendGCMWorker(NotificationDTO data)
    {
        this.data = data;
    }
    
    @Override
    public NotificationDTO call()
    {
        NotificationBO bo = new NotificationBO();
        
        try
        {
            Message message = new Message.Builder().collapseKey("collapseKey" + System.currentTimeMillis()).timeToLive(TIME_TO_LIVE).delayWhileIdle(DELAY_WHILE_IDLE).addData("msg", data.getMessage()).build();
            
            logger.debug("Android Push Device Token : {}", data.getToken());
            logger.info("GCM send data: {}", message);
            // 단일전송시에 사용
            Result result = new Sender(API_KEY).send(message, data.getToken(), RETRY);
            
            if (result.getMessageId() != null)
            {
                data.setStatus(Const.STATUS_COMPLETED);
                logger.debug("Android Push canonicalRegId : {}", result.getCanonicalRegistrationId());
                if (result.getCanonicalRegistrationId() != null)
                {
                    // devices.get(i) has more than on registration ID: update database
                    DeviceBO deviceBO = new DeviceBO();
                    DeviceDTO dto = deviceBO.searchDeviceOne(data.getMemberId(), data.getToken());
                    dto.setToken(result.getCanonicalRegistrationId());
                    deviceBO.modifyDeviceOne(dto);
                }
            }
            else
            {
                logger.info("Android Push Error Result : [{}]{}", data.getSeq(), result.getErrorCodeName());
                if (Constants.ERROR_NOT_REGISTERED.equals(result.getErrorCodeName()))
                {
                    data.setStatus(Const.STATUS_ERROR);
                    // DB에서 디바이스 정보 삭제
                    new DeviceBO().removeDeviceOne(new DeviceDTO(data.getMemberId(), data.getToken()));
                }
                else
                {
                    data.setStatus(Const.STATUS_ERROR);
                }
            }
        }
        catch (Exception e)
        {
            data.setStatus(Const.STATUS_ERROR);
            logger.error("안드로이드 푸시 메시지 전송 중 에러가 발생했습니다.", e);
        }
        bo.modifyNotificationOne(data);
        
        return data;
    }
    
}
