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
package com.dvdprime.server.mobile.factory;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dvdprime.server.mobile.config.MyBatisConfig;

/**
 * Master DB 접속을 도와주는 설정이다.
 * 
 * @author 작은광명
 * @version 1.0
 * @created 2013. 10. 4. 오후 6:11:19
 * @history
 */
public class DaoFactory {
    /** Logger */
    private static Logger logger = LoggerFactory.getLogger(DaoFactory.class);

    /**
     * Master SqlSessionFactory instances are thread safe, so you only need one. In this case, we'll use a static singleton. So sue me. ;-)
     */
    private static SqlSessionFactory sqlSessionFactory;

    /**
     * Master Database Session Factory
     * 
     * @return SqlSessionFactory
     */
    public static SqlSessionFactory getInstance() {
        /**
         * It's not a good idea to put code that can fail in a class initializer, but for sake of argument, here's how you configure an SQL Map.
         */
        if (sqlSessionFactory == null) {
            // SqlSessionFactoryBuilder를 초기화
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            // Properties의 SqlSessionFactory를 선언한다.
            sqlSessionFactory = builder.build(new MyBatisConfig().getConfig());
            logger.info("SqlSessionFactory Declared!!!");
        }
        return sqlSessionFactory;
    }

}