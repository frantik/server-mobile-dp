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

import com.dvdprime.server.mobile.dao.FilterDAO;
import com.dvdprime.server.mobile.domain.Filter;
import com.dvdprime.server.mobile.factory.DaoFactory;
import com.dvdprime.server.mobile.model.FilterDTO;
import com.dvdprime.server.mobile.request.FilterRequest;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

/**
 * 필터 제어 로직
 * 
 * @author 작은광명
 * @version 1.0
 * @created 2013. 10. 8. 오후 11:58:22
 * @history
 */
public class FilterBO {
    /** Logger */
    private Logger logger = LoggerFactory.getLogger(FilterBO.class);

    /**
     * 필터 목록 조회
     * 
     * @param id
     *            회원 아이디
     * @return
     */
    public List<Filter> searchFilterList(String id) {
        List<Filter> mResult = null;

        if (id != null) {
            try (SqlSession sqlSession = DaoFactory.getInstance().openSession()) {
                List<FilterDTO> resultList = new FilterDAO(sqlSession).selectFilterList(new FilterDTO(id));
                if (resultList != null && !resultList.isEmpty()) {
                    mResult = Lists.newArrayList();
                    for (FilterDTO filter : resultList) {
                        mResult.add(new Filter(filter.getTargetId(), filter.getTargetNick()));
                    }
                }
            } catch (Exception e) {
                logger.error("caught a " + e.getClass() + " with message: " + e.getMessage(), e);
            }
        }

        return mResult;
    }

    /**
     * 필터 추가
     * 
     * @param request
     *            {@link FilterRequest}
     * @return
     */
    public boolean creationFilterOne(FilterRequest request) {
        boolean result = false;

        if (request.getId() != null && request.getTargetId() != null && request.getTargetNick() != null) {
            try (SqlSession sqlSession = DaoFactory.getInstance().openSession(true)) {
                FilterDAO dao = new FilterDAO(sqlSession);
                FilterDTO dto = new FilterDTO(request);
                if (dao.selectFilterCount(dto) == 0) {
                    result = new FilterDAO(sqlSession).insertFilterOne(dto) > 0;
                }
            } catch (Exception e) {
                logger.error("caught a " + e.getClass() + " with message: " + e.getMessage(), e);
            }
        }

        return result;
    }

    /**
     * 필터 삭제
     * 
     * @param memberId
     *            회원 아이디
     * @param targetId
     *            대상 아이디
     * @return
     */
    public boolean removeFilterOne(String memberId, String targetId) {
        boolean result = false;

        if (memberId != null && targetId != null) {
            try (SqlSession sqlSession = DaoFactory.getInstance().openSession(true)) {
                FilterDAO dao = new FilterDAO(sqlSession);
                if (targetId.contains(",")) {
                    for (String id : Splitter.on(",").omitEmptyStrings().trimResults().split(targetId)) {
                        result = dao.deleteFilterOne(new FilterDTO(memberId, id)) > 0;
                    }
                } else {
                    result = dao.deleteFilterOne(new FilterDTO(memberId, targetId)) > 0;
                }
            } catch (Exception e) {
                logger.error("caught a " + e.getClass() + " with message: " + e.getMessage(), e);
            }
        }

        return result;
    }
}
