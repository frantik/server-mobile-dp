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
package com.dvdprime.server.mobile.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dvdprime.server.mobile.constants.DBProp;
import com.dvdprime.server.mobile.constants.MapperProp;
import com.dvdprime.server.mobile.constants.TypeAliasProp;

/**
 * MyBatis를 활용한 DB 접속 설정
 * 
 * @author 작은광명
 * @version 1.0
 * @created 2013. 10. 4. 오후 5:24:04
 * @history
 */
public class MyBatisConfig {

    /** Logger */
    private Logger logger = LoggerFactory.getLogger(MyBatisConfig.class);

    /** 접속 DB ID */
    /**
     * Constructor
     * 
     * @param id
     */
    public MyBatisConfig() {}

    /**
     * MyBatis 설정을 위한 Configuration을 반환한다.
     * 
     * @return
     */
    public Configuration getConfig() {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("master", transactionFactory, getTomcatDataSource());

        logger.info("MyBatis Configuration Initialization.");
        Configuration configuration = new Configuration(environment);
        configuration.setCacheEnabled(true);
        configuration.setLazyLoadingEnabled(false);
        configuration.setAggressiveLazyLoading(false);
        configuration.setUseColumnLabel(true);
        configuration.setUseGeneratedKeys(false);
        configuration.setAutoMappingBehavior(AutoMappingBehavior.PARTIAL);
        configuration.setDefaultExecutorType(ExecutorType.REUSE);
        configuration.setDefaultStatementTimeout(25000);
        configuration.setSafeRowBoundsEnabled(true);

        // Alias Type
        Iterator<String> it = TypeAliasProp.getProperties().keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            logger.info("typeAliasRegistry: [{}] -> [{}]", key, TypeAliasProp.getProperties().get(key));
            configuration.getTypeAliasRegistry().registerAlias(key, (String) TypeAliasProp.getProperties().get(key));
        }

        // Mapper
        it = MapperProp.getProperties().keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            logger.info("mapper loaded: [{}]", MapperProp.getProperties().get(key));
            try {
                InputStream inputStream = Resources.getResourceAsStream((String) MapperProp.getProperties().get(key));
                XMLMapperBuilder mapperParser = new XMLMapperBuilder(inputStream, configuration, (String) MapperProp.getProperties().get(key), configuration.getSqlFragments());
                mapperParser.parse();
            } catch (IOException e) {
                logger.error("mapper parsing 중 오류가 발생했습니다.");
            }
        }

        return configuration;
    }

    /**
     * <pre>
     * Tomcat JDBC DataSource를 설정한다.
     * 
     * <참고>
     * properties: http://dev.mysql.com/doc/refman/5.0/en/connector-j-reference-configuration-properties.html
     * http://tomcat.apache.org/tomcat-7.0-doc/jdbc-pool.html
     * </pre>
     * 
     * @return
     */
    private DataSource getTomcatDataSource() {
        logger.info("Tomcat DataSource Initialization.");
        DataSource dataSource = new DataSource();
        PoolProperties p = new PoolProperties();

        // 공통 설정
        p.setDriverClassName(DBProp.MYSQL_DRIVER_NAME);

        /*
         * (boolean) Register the pool with JMX or not. The default value is true.
         */
        p.setJmxEnabled(true);

        /* Validation */
        p.setTestWhileIdle(true);
        p.setTestOnBorrow(true);
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setValidationQuery("SELECT 1");

        /* Connection Pool Leaks */
        p.setRemoveAbandoned(true);
        p.setRemoveAbandonedTimeout(120);
        p.setLogAbandoned(true);

        /*
         * (int) The minimum amount of time an object may sit idle in the pool before it is eligible for eviction. The default value is 60000 (60 seconds). 서버 설정값은 172800(global.wait_timeout)
         */
        p.setMinEvictableIdleTimeMillis(10000);
        /*
         * (int) The number of milliseconds to sleep between runs of the idle connection validation/cleaner thread. This value should not be set under 1 second. It dictates how often we check for idle, abandoned connections, and how often we validate idle connections. The default value is 5000 (5 seconds).
         */
        p.setTimeBetweenEvictionRunsMillis(5000);

        /*
         * (String) A semicolon separated list of classnames extending org.apache.tomcat.jdbc.pool.JdbcInterceptor class. See Configuring JDBC interceptors below for more detailed description of syntaz and examples. These interceptors will be inserted as an interceptor into the chain of operations on a java.sql.Connection object. The default value is null. Predefined interceptors: org.apache.tomcat.jdbc.pool.interceptor. ConnectionState - keeps track of auto commit, read only, catalog and transaction
         * isolation level. org.apache.tomcat.jdbc.pool.interceptor. StatementFinalizer - keeps track of opened statements, and closes them when the connection is returned to the pool.
         */
        p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState; org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");

        /* Account */
        p.setUrl(DBProp.MYSQL_URL);
        p.setUsername(DBProp.MYSQL_USER);
        p.setPassword(DBProp.MYSQL_PASSWORD);
        p.setDefaultAutoCommit(true);

        /* Size */
        p.setInitialSize(1);
        p.setMaxActive(100);
        p.setMaxIdle(10);
        p.setMinIdle(5);
        p.setMaxWait(20000);

        Properties props = new Properties();
        props.put("useUnicode", "true");
        props.put("characterEncoding", "utf8");
        // props.put("failOverReadOnly", "yes");
        // props.put("roundRobinLoadBalance", "true");
        dataSource.setDbProperties(props);

        dataSource.setPoolProperties(p);

        return dataSource;
    }

}
