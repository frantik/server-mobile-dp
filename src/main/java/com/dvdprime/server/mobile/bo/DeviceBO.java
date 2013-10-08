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
package com.dvdprime.server.mobile.bo;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dvdprime.server.mobile.dao.DeviceDAO;
import com.dvdprime.server.mobile.factory.DaoFactory;
import com.dvdprime.server.mobile.model.DeviceDTO;
import com.dvdprime.server.mobile.request.DeviceRequest;

/**
 * 디바이스 제어 로직
 *
 * @author 작은광명
 * @version 1.0
 * @created 2013. 10. 8. 오후 11:58:10
 * @history
 */
public class DeviceBO
{
    /** Logger */
    private Logger logger = LoggerFactory.getLogger(DeviceBO.class);
    
    /**
     * 회원 디바이스 정보 추가
     * 
     * @param request
     *            {@link DeviceRequest}
     * @return
     */
    public boolean creationDeviceOne(DeviceRequest request)
    {
        boolean result = false;
        
        if (request.getId() != null && request.getDeviceToken() != null && request.getVersion() != null)
        {
            try (SqlSession sqlSession = DaoFactory.getInstance().openSession(true))
            {
                result = new DeviceDAO(sqlSession).insertDeviceOne(new DeviceDTO(request)) > 0;
            }
            catch (Exception e)
            {
                logger.error("caught a " + e.getClass() + " with message: " + e.getMessage(), e);
            }
        }
        
        return result;
    }
    
    /**
     * 디바이스 정보 삭제
     * 
     * @param dto
     *            {@link DeviceDTO}
     * @return
     */
    public boolean removeDeviceOne(DeviceDTO dto)
    {
        boolean result = false;
        
        if (dto.getMemberId() != null && dto.getToken() != null)
        {
            try (SqlSession sqlSession = DaoFactory.getInstance().openSession(true))
            {
                result = new DeviceDAO(sqlSession).deleteDeviceOne(dto) > 0;
            }
            catch (Exception e)
            {
                logger.error("caught a " + e.getClass() + " with message: " + e.getMessage(), e);
            }
        }
        
        return result;
    }
}
