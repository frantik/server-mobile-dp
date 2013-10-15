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

import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dvdprime.server.mobile.util.HttpUtil;
import com.dvdprime.server.mobile.util.StringUtil;
import com.google.common.collect.Maps;

public class NoticationBOTest {
    private Logger logger = LoggerFactory.getLogger(NoticationBOTest.class);
    
    @Test
    public void searchCommentKey() {
        Map<String, String> result = Maps.newHashMap();
        try {
            String data = HttpUtil.httpConnect("http://dvdprime.donga.com/bbs/view.asp?major=ME&minor=E1&master_id=40&bbsfword_id=&master_sel=&fword_sel=&SortMethod=&SearchCondition=&SearchConditionTxt=&bbslist_id=2378141&page=1");
            String content = "좋은 프로그램 감사합니다.\n\n시간이 되시면 폰트 크기 조절하는 기능을 추가해주십시요.\n\n글씨가 너무 적..";
            if (data != null) {
                int lastIndex = data.lastIndexOf(StringUtil.curtail(content, 20, null));
                if (lastIndex > -1) {
                    data = data.substring(40000, lastIndex);
                    int nickIndex = data.lastIndexOf("<strong>");
                    int cmtIndex = data.lastIndexOf("anchor_");
                    result.put("type", nickIndex > cmtIndex ? "child" : "parent");
                    // 닉네임 추출
                    if (nickIndex > -1) {
                        logger.info("data ===> " + data.substring(nickIndex));
                        logger.info("nick ===> " + StringUtil.substringBetween(data.substring(nickIndex), "<strong>", "</strong>"));
                        result.put("nick", StringUtil.substringBetween(data.substring(nickIndex), "<strong>", "</strong>"));
                    }
                    if (cmtIndex > -1) {
                        logger.info("data ===> " + data.substring(cmtIndex));
                        logger.info("comment_id ===> " + StringUtil.substringBetween(data.substring(cmtIndex), "_", "\""));
                        result.put("cmt", StringUtil.substringBetween(data.substring(cmtIndex), "_", "\""));
                    }
                    logger.info("result ===> " + result);
                }
            }
        } catch (Exception e) {
            logger.error("caught a " + e.getClass() + " with message: " + e.getMessage(), e);
        }
    }

}
