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

import com.dvdprime.server.mobile.model.FilterDTO;

/**
 * 필터 관련 데이터 처리
 * 
 * @author 작은광명
 * @version 1.0
 * @created 2013. 10. 4. 오후 6:13:30
 * @history
 */
@Data
public class FilterDAO
{
    
    private SqlSession sqlSession;
    
    public FilterDAO(SqlSession sqlSession)
    {
        this.sqlSession = sqlSession;
    }
    
    /**
     * 필터 목록 조회
     * 
     * @param dto
     *            {@link FilterDTO}
     * @return
     * @throws Exception
     */
    public List<FilterDTO> selectFilterList(FilterDTO dto) throws Exception
    {
        return sqlSession.selectList("Filter.selectFilterList", dto);
    }
    
    /**
     * 필터 갯수 조회
     * 
     * @param dto
     *            {@link FilterDTO}
     * @return
     * @throws Exception
     */
    public int selectFilterCount(FilterDTO dto) throws Exception
    {
        return (int) sqlSession.selectOne("Filter.selectFilterCount", dto);
    }
    
    /**
     * 필터 정보 추가
     * 
     * @param dto
     *            {@link FilterDTO}
     * @return
     * @throws Exception
     */
    public int insertFilterOne(FilterDTO dto) throws Exception
    {
        return sqlSession.insert("Filter.insertFilterOne", dto);
    }
    
    /**
     * 필터 정보 삭제
     * 
     * @param dto
     *            {@link FilterDTO}
     * @return
     * @throws Exception
     */
    public int deleteFilterOne(FilterDTO dto) throws Exception
    {
        return sqlSession.update("Filter.deleteFilterOne", dto);
    }
    
}
