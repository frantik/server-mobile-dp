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

import com.dvdprime.server.mobile.model.MemberDTO;

/**
 * 회원 관련 데이터 처리
 * 
 * @author 작은광명
 * @version 1.0
 * @created 2013. 12. 9. 오전 10:44:46
 * @history
 */
@Data
public class MemberDAO
{
    
    private SqlSession sqlSession;
    
    public MemberDAO(SqlSession sqlSession)
    {
        this.sqlSession = sqlSession;
    }
    
    /**
     * 회원 목록 조회
     * 
     * @param dto
     *            {@link MemberDTO}
     * @return
     * @throws Exception
     */
    public List<MemberDTO> selectMemberList(MemberDTO dto) throws Exception
    {
        return sqlSession.selectList("Member.selectMemberList", dto);
    }
    
    /**
     * 회원 정보 추가
     * 
     * @param dto
     *            {@link MemberDTO}
     * @return
     * @throws Exception
     */
    public int insertMemberOne(MemberDTO dto) throws Exception
    {
        return sqlSession.insert("Member.insertMemberOne", dto);
    }
    
    /**
     * 회원 정보 삭제
     * 
     * @param memberId
     *            회원 아이디
     * @return
     * @throws Exception
     */
    public int deleteMemberOne(String memberId) throws Exception
    {
        return sqlSession.update("Member.deleteMemberOne", memberId);
    }
    
}
