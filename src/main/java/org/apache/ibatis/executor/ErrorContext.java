/*
 *    Copyright 2009-2012 The MyBatis Team
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
package org.apache.ibatis.executor;

import org.apache.ibatis.logging.jdbc.SqlTextUtil;

public class ErrorContext
{
    private static final String LINE_SEPARATOR = System.getProperty("line.separator", "\n");
    
    private static final ThreadLocal<ErrorContext> LOCAL = new ThreadLocal<ErrorContext>();
    
    private ErrorContext stored;
    
    private String resource;
    
    private String activity;
    
    private String object;
    
    private String message;
    
    private String sql;
    
    private Throwable cause;
    
    public static ErrorContext instance()
    {
        ErrorContext context = (ErrorContext) LOCAL.get();
        if (context == null)
        {
            context = new ErrorContext();
            LOCAL.set(context);
        }
        return context;
    }
    
    public ErrorContext store()
    {
        this.stored = this;
        LOCAL.set(new ErrorContext());
        return ((ErrorContext) LOCAL.get());
    }
    
    public ErrorContext recall()
    {
        if (this.stored != null)
        {
            LOCAL.set(this.stored);
            this.stored = null;
        }
        return ((ErrorContext) LOCAL.get());
    }
    
    public ErrorContext resource(String resource)
    {
        this.resource = resource;
        return this;
    }
    
    public ErrorContext activity(String activity)
    {
        this.activity = activity;
        return this;
    }
    
    public ErrorContext object(String object)
    {
        this.object = object;
        return this;
    }
    
    public ErrorContext message(String message)
    {
        this.message = message;
        return this;
    }
    
    public ErrorContext sql(String sql)
    {
        this.sql = sql;
        return this;
    }
    
    public ErrorContext cause(Throwable cause)
    {
        this.cause = cause;
        return this;
    }
    
    public ErrorContext reset()
    {
        this.resource = null;
        this.activity = null;
        this.object = null;
        this.message = null;
        this.sql = null;
        this.cause = null;
        LOCAL.remove();
        return this;
    }
    
    public String toString()
    {
        StringBuffer description = new StringBuffer();
        
        if (this.message != null)
        {
            description.append(LINE_SEPARATOR);
            description.append("### ");
            description.append(this.message);
        }
        
        if (this.resource != null)
        {
            description.append(LINE_SEPARATOR);
            description.append("### The error may exist in ");
            description.append(this.resource);
        }
        
        if (this.object != null)
        {
            description.append(LINE_SEPARATOR);
            description.append("### The error may involve ");
            description.append(this.object);
        }
        
        if (this.activity != null)
        {
            description.append(LINE_SEPARATOR);
            description.append("### The error occurred while ");
            description.append(this.activity);
        }
        
        if (this.sql != null)
        {
            description.append(LINE_SEPARATOR);
            description.append("### SQL: ");
            description.append(LINE_SEPARATOR);
            description.append(SqlTextUtil.leftTrim(sql));
            // description.append(this.sql.replace('\n', ' ').replace('\r', ' ').replace('\t', ' ').trim());
        }
        
        if (this.cause != null)
        {
            description.append(LINE_SEPARATOR);
            description.append("### Cause: ");
            description.append(this.cause.toString());
        }
        
        return description.toString();
    }
}