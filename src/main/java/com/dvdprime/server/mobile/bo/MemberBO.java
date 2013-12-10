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

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dvdprime.server.mobile.dao.MemberDAO;
import com.dvdprime.server.mobile.factory.DaoFactory;
import com.dvdprime.server.mobile.model.MemberDTO;
import com.dvdprime.server.mobile.util.StringUtil;

/**
 * 회원 제어 로직
 * 
 * @author 작은광명
 * @version 1.0
 * @created 2013. 10. 8. 오후 11:58:10
 * @history
 */
public class MemberBO {
    /** Logger */
    private Logger logger = LoggerFactory.getLogger(MemberBO.class);

    /**
     * 회원 목록
     * 
     * @return
     */
    public List<MemberDTO> searchMemberList(MemberDTO dto) {
        List<MemberDTO> mResult = null;

        try (SqlSession sqlSession = DaoFactory.getInstance().openSession()) {
            mResult = new MemberDAO(sqlSession).selectMemberList(dto);
        } catch (Exception e) {
            logger.error("caught a " + e.getClass() + " with message: " + e.getMessage(), e);
        }

        return mResult;
    }

    /**
     * 차단 회원 카운트 조회
     * 
     * @param memberId
     *            회원 아이디
     * @return
     */
    public int searchMemberCount(String memberId) {
        int result = 0;

        if (memberId != null) {
            try (SqlSession sqlSession = DaoFactory.getInstance().openSession()) {
                result = new MemberDAO(sqlSession).selectMemberCount(memberId);
            } catch (Exception e) {
                logger.error("caught a " + e.getClass() + " with message: " + e.getMessage(), e);
            }
        }

        return result;
    }

    /**
     * 회원 정보 추가
     * 
     * @param dto
     *            {@link MemberDTO}
     * @return
     */
    public boolean creationMemberOne(MemberDTO dto) {
        boolean result = false;

        if (StringUtil.isNotBlank(dto.getMemberId())) {
            try (SqlSession sqlSession = DaoFactory.getInstance().openSession(true)) {
                result = new MemberDAO(sqlSession).insertMemberOne(dto) > 0;
            } catch (Exception e) {
                logger.error("caught a " + e.getClass() + " with message: " + e.getMessage(), e);
            }
        }

        return result;
    }

    /**
     * 회원 정보 삭제
     * 
     * @param memberId
     *            회원 아이디
     * @return
     */
    public boolean removeMemberOne(String memberId) {
        boolean result = false;

        if (StringUtil.isNotBlank(memberId)) {
            try (SqlSession sqlSession = DaoFactory.getInstance().openSession(true)) {
                result = new MemberDAO(sqlSession).deleteMemberOne(memberId) > 0;
            } catch (Exception e) {
                logger.error("caught a " + e.getClass() + " with message: " + e.getMessage(), e);
            }
        }

        return result;
    }
}
