package org.apache.ibatis.scripting.xmltags;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ognl.OgnlContext;
import ognl.OgnlException;
import ognl.OgnlRuntime;
import ognl.PropertyAccessor;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;

import com.dvdprime.server.mobile.util.StringUtil;

/**
 * ibatis의 DynamicContext를 Override해서 사용하기 위한 클래스
 * 
 * @author Matthew
 * @version 1.0
 * @created 2012. 1. 26. 오후 8:53:45
 * @description ContextMap에 사용할 함수를 정의해서 mapper에서 사용한다.<br/>
 *              여기서의 함수를 사용하지 않고 자체 함수를 사용할 경우에는 <br/>
 *              <code>&lt;if test="@com.hellomarket.web.util.StringUtil@isEmpty(keyword)"&gt;</code> 와 같이<br/>
 *              사용할 수도 있다.
 * @history
 */
public class DynamicContext
{
    public static final String PARAMETER_OBJECT_KEY = "_parameter";
    
    public static final String DATABASE_ID_KEY = "_databaseId";
    
    private final ContextMap bindings;
    
    private final StringBuilder sqlBuilder = new StringBuilder();
    
    private int uniqueNumber = 0;
    
    public DynamicContext(Configuration configuration, Object parameterObject)
    {
        if ((parameterObject != null) && (!(parameterObject instanceof Map)))
        {
            MetaObject metaObject = configuration.newMetaObject(parameterObject);
            this.bindings = new ContextMap(metaObject);
        }
        else
        {
            this.bindings = new ContextMap(null);
        }
        this.bindings.put("_parameter", parameterObject);
        this.bindings.put("_databaseId", configuration.getDatabaseId());
    }
    
    public Map<String, Object> getBindings()
    {
        return this.bindings;
    }
    
    public void bind(String name, Object value)
    {
        this.bindings.put(name, value);
    }
    
    public void appendSql(String sql)
    {
        this.sqlBuilder.append(sql);
        this.sqlBuilder.append(" ");
    }
    
    public String getSql()
    {
        return this.sqlBuilder.toString().trim();
    }
    
    public int getUniqueNumber()
    {
        return (this.uniqueNumber++);
    }
    
    static
    {
        OgnlRuntime.setPropertyAccessor(ContextMap.class, new ContextAccessor());
    }
    
    public static class ContextAccessor implements PropertyAccessor
    {
        @SuppressWarnings("rawtypes")
        public Object getProperty(Map context, Object target, Object name) throws OgnlException
        {
            Map map = (Map) target;
            
            Object result = map.get(name);
            if (result != null) { return result; }
            
            Object parameterObject = map.get("_parameter");
            if (parameterObject instanceof Map) { return ((Map) parameterObject).get(name); }
            
            return null;
        }
        
        @SuppressWarnings({ "rawtypes", "unchecked" })
        public void setProperty(Map context, Object target, Object name, Object value) throws OgnlException
        {
            Map map = (Map) target;
            map.put(name, value);
        }
        
        @Override
        public String getSourceAccessor(OgnlContext arg0, Object arg1, Object arg2)
        {
            // TODO Auto-generated method stub
            return null;
        }
        
        @Override
        public String getSourceSetter(OgnlContext arg0, Object arg1, Object arg2)
        {
            // TODO Auto-generated method stub
            return null;
        }
    }
    
    public static class ContextMap extends HashMap<String, Object>
    {
        private static final long serialVersionUID = 2977601501966151582L;
        
        private MetaObject parameterMetaObject;
        
        public ContextMap(MetaObject parameterMetaObject)
        {
            this.parameterMetaObject = parameterMetaObject;
        }
        
        public Object get(Object key)
        {
            String strKey = (String) key;
            if (super.containsKey(strKey)) { return super.get(strKey); }
            
            if (this.parameterMetaObject != null)
            {
                Object object = this.parameterMetaObject.getValue(strKey);
                if (object != null)
                {
                    super.put(strKey, object);
                }
                
                return object;
            }
            
            return null;
        }
        
        // //////////////////////////////////////////////////////////////
        //
        // Custom Function 추가
        //
        // //////////////////////////////////////////////////////////////
        /**
         * <pre>
         * object 값이 존재하는지 여부를 체크하기 위한 함수
         * 
         * <code>&lt;if test="empty(value)"&gt;</code>
         * </pre>
         * 
         * @param obj
         * @return
         */
        public boolean empty(Object obj)
        {
            if (obj == null) return true;
            
            if (obj instanceof String)
            {
                return ((String) obj).equals("");
            }
            else if (obj instanceof List)
            {
                return ((List<?>) obj).isEmpty();
            }
            else if (obj instanceof Map)
            {
                return ((Map<?, ?>) obj).isEmpty();
            }
            else if (obj instanceof Object[])
            {
                return Array.getLength(obj) == 0;
            }
            else
            {
                return obj == null;
            }
        }
        
        /**
         * <pre>
         * 문자열에 검색 문자가 존재하는지 체크하기 위한 함수
         * </pre>
         * 
         * @param text
         *            대상 문자열
         * @param search
         *            찾을 문자열
         * @return
         */
        public boolean contains(String text, String search)
        {
            return StringUtil.contains(text, search);
        }
        
        /**
         * <pre>
         * 오브젝트의 길이나 개수를 반환하는 함수
         * 
         * <code>&lt;if test="length(value)"&gt;</code>
         * </pre>
         * 
         * @param obj
         * @return
         */
        public int length(Object obj)
        {
            if (obj == null) return 0;
            
            if (obj instanceof String)
            {
                return ((String) obj).length();
            }
            else if (obj instanceof List)
            {
                return ((List<?>) obj).size();
            }
            else if (obj instanceof Map)
            {
                return ((Map<?, ?>) obj).size();
            }
            else if (obj instanceof Object[])
            {
                return Array.getLength(obj);
            }
            else
            {
                return 0;
            }
        }
    }
}