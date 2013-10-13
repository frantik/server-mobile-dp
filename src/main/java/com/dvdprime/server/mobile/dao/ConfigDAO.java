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

import com.dvdprime.server.mobile.model.ConfigDTO;

/**
 * 설정 관련 데이터 처리
 * 
 * @author 작은광명
 * @version 1.0
 * @created 2013. 10. 4. 오후 6:13:30
 * @history
 */
@Data
public class ConfigDAO {

    private SqlSession sqlSession;

    public ConfigDAO(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    /**
     * 설정 목록 조회
     * 
     * @param dto
     *            {@link ConfigDTO}
     * @return
     */
    public List<ConfigDTO> selectConfigList(ConfigDTO dto) throws Exception {
        return sqlSession.selectList("Config.selectConfigList", dto);
    }

    /**
     * 설정 갯수 조회
     * 
     * @param dto
     *            {@link ConfigDTO}
     * @return
     * @throws Exception
     */
    public int selectConfigCount(ConfigDTO dto) throws Exception {
        return (int) sqlSession.selectOne("Config.selectConfigCount", dto);
    }

    /**
     * 설정 정보 추가
     * 
     * @param dto
     *            {@link ConfigDTO}
     * @return
     */
    public int insertConfigOne(ConfigDTO dto) throws Exception {
        return sqlSession.insert("Config.insertConfigOne", dto);
    }

    /**
     * 설정 정보 업데이트
     * 
     * @param dto
     *            {@link ConfigDTO}
     * @return
     * @throws Exception
     */
    public int updateConfigOne(ConfigDTO dto) throws Exception {
        return sqlSession.update("Config.updateConfigOne", dto);
    }

}
