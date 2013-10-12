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

import com.dvdprime.server.mobile.constants.Const;
import com.dvdprime.server.mobile.dao.NotificationDAO;
import com.dvdprime.server.mobile.domain.Notification;
import com.dvdprime.server.mobile.factory.DaoFactory;
import com.dvdprime.server.mobile.model.NotificationDTO;
import com.dvdprime.server.mobile.request.NotificationRequest;
import com.dvdprime.server.mobile.util.DateUtil;
import com.dvdprime.server.mobile.util.StringUtil;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

/**
 * 알림 제어 로직
 * 
 * @author 작은광명
 * @version 1.0
 * @created 2013. 10. 8. 오후 11:58:35
 * @history
 */
public class NotificationBO
{
    /**
     * Logger
     */
    private Logger logger = LoggerFactory.getLogger(NotificationBO.class);
    
    /**
     * 알림 목록 조회
     * 
     * @param request
     *            {@link NotificationRequest}
     * @return
     */
    public List<Notification> searchNotificationList(NotificationRequest request)
    {
        List<Notification> mResult = null;
        
        if (request.getId() != null)
        {
            try (SqlSession sqlSession = DaoFactory.getInstance().openSession(true))
            {
                NotificationDTO dto = new NotificationDTO(request);
                NotificationDAO dao = new NotificationDAO(sqlSession);
                List<NotificationDTO> resultList = dao.selectNotificationList(dto);
                if (resultList != null && !resultList.isEmpty())
                {
                    mResult = Lists.newArrayList();
                    String updatedDecimal = DateUtil.getCurrentTimeDecimal();
                    for (NotificationDTO notification : resultList)
                    {
                        mResult.add(new Notification(notification));
                        // 조회한 내용은 모두 읽음으로 변경한다.
                        notification.setReadFlag(Const.READ_FLAG_02);
                        notification.setUpdatedDecimal(updatedDecimal);
                        dao.updateNotificationOne(notification);
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
     * 알림 전송할 목록 조회
     * 
     * @return
     */
    public List<NotificationDTO> searchNotificationSendList()
    {
        List<NotificationDTO> mResult = null;
        
        try (SqlSession sqlSession = DaoFactory.getInstance().openSession())
        {
            mResult = new NotificationDAO(sqlSession).selectNotificationSendList();
        }
        catch (Exception e)
        {
            logger.error("caught a " + e.getClass() + " with message: " + e.getMessage(), e);
        }
        
        return mResult;
    }
    
    /**
     * 확인 안한 알림갯수 조회
     * 
     * @param memberId
     *            회원 아이디
     * @return
     */
    public int searchNotificationCount(String memberId)
    {
        int result = 0;
        
        if (memberId != null)
        {
            try (SqlSession sqlSession = DaoFactory.getInstance().openSession())
            {
                NotificationDTO dto = new NotificationDTO();
                dto.setMemberId(memberId);
                ;
                dto.setReadFlag(Const.READ_FLAG_01);
                result = new NotificationDAO(sqlSession).selectNotificationCount(dto);
            }
            catch (Exception e)
            {
                logger.error("caught a " + e.getClass() + " with message: " + e.getMessage(), e);
            }
        }
        
        return result;
    }
    
    /**
     * 알림 정보 등록
     * 
     * @param request
     *            {@link NotificationRequest}
     * @return
     */
    public boolean creationNotificationOne(NotificationRequest request)
    {
        boolean result = false;
        
        if (request.getIds() != null && request.getMessage() != null && request.getTargetUrl() != null)
        {
            try (SqlSession sqlSession = DaoFactory.getInstance().openSession(true))
            {
                NotificationDAO dao = new NotificationDAO(sqlSession);
                for (String id : Splitter.on(",").omitEmptyStrings().trimResults().split(request.getIds()))
                {
                    NotificationDTO dto = new NotificationDTO(id, request);
                    // 댓글 알림일 경우
                    if (StringUtil.equals(request.getType(), Const.TYPE_CMT))
                    {
                        String dups = new StringBuffer().append(StringUtil.getParamValue(request.getTargetUrl(), "major")).append(StringUtil.getParamValue(request.getTargetUrl(), "minor"))
                                .append(StringUtil.getParamValue(request.getTargetUrl(), "master_id")).append(StringUtil.getParamValue(request.getTargetUrl(), "bbslist_id")).toString();
                        dto.setDups(dups);
                        if (dao.selectNotificationCount(dto) > 0)
                        {
                            dto.setReadFlag(Const.READ_FLAG_01);
                            dto.setStatus(Const.STATUS_READY);
                            dao.updateNotificationOne(dto);
                        }
                        else
                        {
                            dao.insertNotificationOne(dto);
                        }
                    }
                    else if (StringUtil.equals(request.getType(), Const.TYPE_MEMO))
                    {
                        result = dao.insertNotificationOne(new NotificationDTO(id, request)) > 0;
                    }
                }
            }
            catch (Exception e)
            {
                logger.error("caught a " + e.getClass() + " with message: " + e.getMessage(), e);
            }
        }
        
        return result;
    }
    
    /**
     * 알림 메시지 정보 수정
     * 
     * @param dto
     *            {@link NotificationDTO}
     */
    public void modifyNotificationOne(NotificationDTO dto)
    {
        if (dto.getSeq() > 0)
        {
            try (SqlSession sqlSession = DaoFactory.getInstance().openSession(true))
            {
                new NotificationDAO(sqlSession).updateNotificationOne(dto);
            }
            catch (Exception e)
            {
                logger.error("caught a " + e.getClass() + " with message: " + e.getMessage(), e);
            }
        }
    }
}
