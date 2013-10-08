package org.apache.ibatis.logging.jdbc;

public class SqlTextUtil
{
    
    /**
     * 왼쪽Trim 첫줄을 기준으로 xml에 있던 왼쪽 공백을 제거한다.
     * 
     * @param str
     * @return
     */
    public static String leftTrim(String str)
    {
        if (str == null) return str;
        
        int s = 0;
        int e = 0;
        StringBuilder buf = new StringBuilder();
        
        String line = null;
        int cnt = 0;
        int iPos = 0;
        int cPos = 0;
        while ((e = str.indexOf('\n', s)) >= 0)
        {
            line = str.substring(s, e + 1);
            if (!line.trim().equals(""))
            {
                if (cnt == 0)
                {
                    iPos = getLeftPos(line);
                    buf.append(line.substring(iPos));
                }
                else
                {
                    cPos = getLeftPos(line);
                    
                    if (cPos < iPos)
                    {
                        buf.append(line.substring(cPos));
                    }
                    else
                    {
                        buf.append(line.substring(iPos));
                    }
                }
                
                cnt++;
            }
            
            s = e + 1;
        }
        
        line = str.substring(s);
        
        cPos = getLeftPos(line);
        if (cPos < iPos)
        {
            buf.append(line.substring(cPos));
        }
        else
        {
            buf.append(line.substring(iPos));
        }
        
        return buf.toString().trim();
    }
    
    private static int getLeftPos(String str)
    {
        if (str == null) return 0;
        
        char[] chs = str.toCharArray();
        for (int i = 0; i < chs.length; i++)
        {
            char ch = chs[i];
            if (ch == '\t' || ch == ' ') continue;
            
            return i;
        }
        
        return 0;
    }
    
}
