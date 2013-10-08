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

import com.dvdprime.server.mobile.dao.ConfigDAO;
import com.dvdprime.server.mobile.domain.Config;
import com.dvdprime.server.mobile.factory.DaoFactory;
import com.dvdprime.server.mobile.model.ConfigDTO;
import com.dvdprime.server.mobile.util.StringUtil;
import com.google.common.collect.Lists;

/**
 * 설정 제어 로직
 *
 * @author 작은광명
 * @version 1.0
 * @created 2013. 10. 8. 오후 11:57:56
 * @history
 */
public class ConfigBO
{
    /** Logger */
    private Logger logger = LoggerFactory.getLogger(ConfigBO.class);
    
    /**
     * 설정 목록 조회
     * 
     * @param memberId
     *            회원 아이디
     * @return
     */
    public List<Config> searchConfigList(String memberId)
    {
        List<Config> mResult = null;
        
        if (StringUtil.isNotBlank(memberId))
        {
            try (SqlSession sqlSession = DaoFactory.getInstance().openSession())
            {
                List<ConfigDTO> resultList = new ConfigDAO(sqlSession).selectConfigList(new ConfigDTO(memberId));
                
                if (resultList != null && !resultList.isEmpty())
                {
                    mResult = Lists.newArrayList();
                    for (ConfigDTO config : resultList)
                    {
                        mResult.add(new Config(config.getType(), config.getEnabled()));
                    }
                }
            }
            catch (Exception e)
            {
                logger.error("caught a " + e.getClass() + " with message: " + e.getMessage(), e);
            }
        }
        
        return mResult;
    }
    
    /**
     * 설정 정보를 수정
     * 
     * @param memberId
     *            회원 아이디
     * @param type
     *            설정 종류
     * @param enabled
     *            설정값
     * @return
     */
    public boolean modifyConfigOne(String memberId, String type, boolean enabled)
    {
        boolean result = false;
        ConfigDTO dto = new ConfigDTO(memberId, type, enabled);
        
        try (SqlSession sqlSession = DaoFactory.getInstance().openSession(true))
        {
            if (memberId != null && type != null)
            {
                ConfigDAO dao = new ConfigDAO(sqlSession);
                if (dao.selectConfigCount(dto) > 0)
                {
                    result = dao.insertConfigOne(dto) > 0;
                }
                else
                {
                    result = dao.updateConfigOne(dto) > 0;
                }
            }
        }
        catch (Exception e)
        {
            logger.error("caught a " + e.getClass() + " with message: " + e.getMessage(), e);
        }
        
        return result;
    }
}
