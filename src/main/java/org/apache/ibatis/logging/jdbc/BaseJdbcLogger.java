/*
 *    Copyright 2009-2011 The MyBatis Team
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.logging.jdbc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.logging.Log;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/*
 * Base class for proxies to do logging
 */
public abstract class BaseJdbcLogger
{
    protected static final Set<String> SET_METHODS = Sets.newHashSet();
    
    protected static final Set<String> EXECUTE_METHODS = Sets.newHashSet();
    
    private Map<Object, Object> columnMap = Maps.newHashMap();
    
    private List<Object> columnNames = Lists.newArrayList();
    
    private List<Object> columnValues = Lists.newArrayList();
    
    private Log statementLog;
    
    public BaseJdbcLogger(Log log)
    {
        this.statementLog = log;
    }
    
    protected void setColumn(Object key, Object value)
    {
        this.columnMap.put(key, value);
        this.columnNames.add(key);
        this.columnValues.add(value);
    }
    
    protected Object getColumn(Object key)
    {
        return this.columnMap.get(key);
    }
    
    protected String getParameterValueString()
    {
        List<String> typeList = new ArrayList<String>(this.columnValues.size());
        for (Iterator<Object> i$ = this.columnValues.iterator(); i$.hasNext();)
        {
            Object value = i$.next();
            if (value == null)
                typeList.add("null");
            else
            {
                typeList.add(value + "(" + value.getClass().getSimpleName() + ")");
            }
        }
        String parameters = typeList.toString();
        return parameters.substring(1, parameters.length() - 1);
    }
    
    protected String getColumnString()
    {
        return this.columnNames.toString();
    }
    
    protected void clearColumnInfo()
    {
        this.columnMap.clear();
        this.columnNames.clear();
        this.columnValues.clear();
    }
    
    protected String removeBreakingWhitespace(String original)
    {
        // StringTokenizer whitespaceStripper = new StringTokenizer(original);
        // StringBuilder builder = new StringBuilder();
        // while (whitespaceStripper.hasMoreTokens())
        // {
        // builder.append(whitespaceStripper.nextToken());
        // builder.append(" ");
        // }
        // return builder.toString();
        
        return SqlTextUtil.leftTrim(original);
    }
    
    protected boolean isDebugEnabled()
    {
        return this.statementLog.isDebugEnabled();
    }
    
    protected boolean isTraceEnabled()
    {
        return this.statementLog.isTraceEnabled();
    }
    
    protected void debug(String text)
    {
        if (this.statementLog.isDebugEnabled()) this.statementLog.debug(text);
    }
    
    protected void trace(String text)
    {
        if (this.statementLog.isTraceEnabled()) this.statementLog.trace(text);
    }
    
    public Log getStatementLog()
    {
        return this.statementLog;
    }
    
    static
    {
        SET_METHODS.add("setString");
        SET_METHODS.add("setInt");
        SET_METHODS.add("setByte");
        SET_METHODS.add("setShort");
        SET_METHODS.add("setLong");
        SET_METHODS.add("setDouble");
        SET_METHODS.add("setFloat");
        SET_METHODS.add("setTimestamp");
        SET_METHODS.add("setDate");
        SET_METHODS.add("setTime");
        SET_METHODS.add("setArray");
        SET_METHODS.add("setBigDecimal");
        SET_METHODS.add("setAsciiStream");
        SET_METHODS.add("setBinaryStream");
        SET_METHODS.add("setBlob");
        SET_METHODS.add("setBoolean");
        SET_METHODS.add("setBytes");
        SET_METHODS.add("setCharacterStream");
        SET_METHODS.add("setClob");
        SET_METHODS.add("setObject");
        SET_METHODS.add("setNull");
        
        EXECUTE_METHODS.add("execute");
        EXECUTE_METHODS.add("executeUpdate");
        EXECUTE_METHODS.add("executeQuery");
        EXECUTE_METHODS.add("addBatch");
    }
}