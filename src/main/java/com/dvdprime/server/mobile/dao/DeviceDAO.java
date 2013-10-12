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
package com.dvdprime.server.mobile.dao;

import java.util.List;

import lombok.Data;

import org.apache.ibatis.session.SqlSession;

import com.dvdprime.server.mobile.model.DeviceDTO;

/**
 * 디바이스 관련 데이터 처리
 * 
 * @author 작은광명
 * @version 1.0
 * @created 2013. 10. 4. 오후 6:13:30
 * @history
 */
@Data
public class DeviceDAO
{
    
    private SqlSession sqlSession;
    
    public DeviceDAO(SqlSession sqlSession)
    {
        this.sqlSession = sqlSession;
    }
    
    /**
     * 디바이스 목록 조회
     * 
     * @param dto
     *            {@link DeviceDTO}
     * @return
     * @throws Exception
     */
    public List<DeviceDTO> selectDeviceList(DeviceDTO dto) throws Exception
    {
        return sqlSession.selectList("Device.selectDeviceList", dto);
    }
    
    /**
     * 디바이스 정보 조회
     * 
     * @param dto
     *            {@link DeviceDTO}
     * @return
     * @throws Exception
     */
    public DeviceDTO selectDeviceOne(DeviceDTO dto) throws Exception
    {
        return (DeviceDTO) sqlSession.selectOne("Device.selectDeviceOne", dto);
    }
    
    /**
     * 디바이스 갯수 조회
     * 
     * @param dto
     *            {@link DeviceDTO}
     * @return
     * @throws Exception
     */
    public int selectDeviceCount(DeviceDTO dto) throws Exception
    {
        return (int) sqlSession.selectOne("Device.selectDeviceCount", dto);
    }
    
    /**
     * 디바이스 정보 추가
     * 
     * @param dto
     *            {@link DeviceDTO}
     * @return
     * @throws Exception
     */
    public int insertDeviceOne(DeviceDTO dto) throws Exception
    {
        return sqlSession.insert("Device.insertDeviceOne", dto);
    }
    
    /**
     * 디바이스 정보 업데이트
     * 
     * @param dto
     *            {@link DeviceDTO}
     * @return
     * @throws Exception
     */
    public int updateDeviceOne(DeviceDTO dto) throws Exception
    {
        return sqlSession.update("Device.updateDeviceOne", dto);
    }
    
    /**
     * 디바이스 정보 삭제
     * 
     * @param dto
     *            {@link DeviceDTO}
     * @return
     * @throws Exception
     */
    public int deleteDeviceOne(DeviceDTO dto) throws Exception
    {
        return sqlSession.update("Device.deleteDeviceOne", dto);
    }
    
}
