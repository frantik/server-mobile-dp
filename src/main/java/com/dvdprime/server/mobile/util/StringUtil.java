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
package com.dvdprime.server.mobile.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 문자 관련 유틸
 * 
 * @author 작은광명
 * @version 1.0
 * @created 2013. 10. 7. 오후 3:13:15
 * @history
 */
public class StringUtil {
    public static final String EMPTY = "";

    private static final int INDEX_NOT_FOUND = -1;

    /**
     * <p>
     * 문자열(String)에 검색문자열(String)이 포함되어 있는지 검사한다.
     * </p>
     * 
     * <pre>
     * StringUtil.contains(null, *)    = false
     * StringUtil.contains(*, null)    = false
     * StringUtil.contains("han", "")  = true
     * StringUtil.contains("han", "h") = true
     * StringUtil.contains("han", "H") = false
     * </pre>
     * 
     * @see java.lang.String#indexOf(String)
     * @param str
     *            문자열
     * @param searchStr
     *            검색문자열
     * @return 문자열(String)에 검색 문자열이 포함되어 있을때 <code>true</code>, 문자열(String)에 검색 문자열이 포함되어 있지 않을때나, 문자열 또는 검색문자열이 <code>null</code>일때 <code>false</code>
     */
    public static boolean contains(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        return str.indexOf(searchStr) > INDEX_NOT_FOUND;
    }

    /**
     * <p>
     * 문자열(String) List에 검색문자열(String)이 포함되어 있는지 검사한다.
     * </p>
     * 
     * <pre>
     * StringUtil.contains("han", {"a", "b"}) = true
     * StringUtil.contains("han", {"aa", "bb"}) = false
     * </pre>
     * 
     * @param str
     *            문자열
     * @param keywords
     *            검색할 문자열 목록
     * @return
     */
    public static boolean contains(String str, String[] keywords) {
        if (str == null || keywords == null) {
            return false;
        }

        for (String key : keywords) {
            if (contains(str, key)) {
                return true;
            }
        }

        return false;
    }

    public static boolean contains(String str, List<String> keywords) {
        if (str == null || keywords == null) {
            return false;
        }

        for (String key : keywords) {
            if (contains(str, key)) {
                return true;
            }
        }

        return false;
    }

    /**
     * <p>
     * 문자열 배열(String Array)에 검색문자열(String)이 포함되어 있는지 검사한다.
     * </p>
     * 
     * <pre>
     * StringUtil.contains(null, *)    = false
     * StringUtil.contains(*, null)    = false
     * StringUtil.contains({"han", "kuk"}, "")  = false
     * StringUtil.contains({"han", "kuk"}, "h") = false
     * StringUtil.contains({"han", "kuk"}, "han") = true
     * </pre>
     * 
     * @see java.lang.String#indexOf(String)
     * @param str
     *            문자열 배열
     * @param searchStr
     *            검색문자열
     * @return 문자열 배열(String Array)에 검색 문자열과 같은 문자열이 포함되어 있을때 <code>true</code>, 문자열(String)에 검색 문자열이 포함되어 있지 않을때나, 문자열 또는 검색문자열이 <code>null</code>일때 <code>false</code>
     */
    public static boolean contains(String[] arr, String searchStr) {
        if (arr == null || searchStr == null) {
            return false;
        }

        for (String str : arr)
            if (equals(str, searchStr))
                return true;

        return false;
    }

    // ----------------------------------------------------------------------
    // 공백/여백문자 검사, 제거, 치환
    // ----------------------------------------------------------------------

    /**
     * <p>
     * 문자열(String)의 좌우 여백문자(white space)를 제거한후, 공백("")이거나 <code>null</code>인 검사한다.
     * </p>
     * 
     * <pre>
     * StringUtil.isBlank(null)    = true
     * StringUtil.isBlank("")      = true
     * StringUtil.isBlank("   ")   = true
     * StringUtil.isBlank("han")   = false
     * StringUtil.isBlank(" han ") = false
     * </pre>
     * 
     * @param str
     *            문자열
     * @return
     */
    public static boolean isBlank(String str) {
        if (str == null) {
            return true;
        }
        int strLen = str.length();
        if (strLen > 0) {
            for (int i = 0; i < strLen; i++) {
                if (Character.isWhitespace(str.charAt(i)) == false) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * <p>
     * 문자열(String)의 좌우 여백문자(white space)를 제거한후, 공백("")이 아니거나 <code>null</code>이 아닌지 검사한다.
     * </p>
     * 
     * <pre>
     * StringUtil.isNotBlank(null)    = false
     * StringUtil.isNotBlank("")      = false
     * StringUtil.isNotBlank("   ")   = false
     * StringUtil.isNotBlank("han")   = true
     * StringUtil.isNotBlank(" han ") = true
     * </pre>
     * 
     * @param str
     *            문자열
     * @return
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * <p>
     * 문자열(String)이 공백("")이거나 <code>null</code>인 검사한다.
     * </p>
     * 
     * <pre>
     * StringUtil.isEmpty(null)    = true
     * StringUtil.isEmpty("")      = true
     * StringUtil.isEmpty("   ")   = false
     * StringUtil.isEmpty("han")   = false
     * StringUtil.isEmpty(" han ") = false
     * </pre>
     * 
     * 
     * @param str
     *            검사할 문자열
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * <p>
     * 문자열(String)이 공백("")이 아니거나 <code>null</code>이 아닌지 검사한다.
     * </p>
     * 
     * <pre>
     * StringUtil.isNotEmpty(null)    = false
     * StringUtil.isNotEmpty("")      = false
     * StringUtil.isNotEmpty("   ")   = true
     * StringUtil.isNotEmpty("han")   = true
     * StringUtil.isNotEmpty(" han ") = true
     * </pre>
     * 
     * @param str
     *            검사할 문자열
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * <p>
     * 문자열이 숫자로만 구성되어 있는지 검사한다.
     * </p>
     * 
     * <pre>
     * StringUtil.isNumber(null) = false;
     * StringUtil.isNumber(&quot;&quot;) = false;
     * StringUtil.isNumber(&quot;1234&quot;) = true;
     * StringUtil.isNumber(&quot;abc123&quot;) = false;
     * </pre>
     * 
     * @param str
     *            검사할 문자열
     * @return
     */
    public static boolean isNumber(String str) {
        try {
            Integer.valueOf(str);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    // ----------------------------------------------------------------------
    // 문자열 비교
    // ----------------------------------------------------------------------
    /**
     * <p>
     * 두 문자열(String)이 일치하면 <code>true</code>을 반환한다.
     * </p>
     * 
     * <pre>
     * StringUtil.equals(null, null)   = true
     * StringUtil.equals(null, "")     = false
     * StringUtil.equals("", null)     = false
     * StringUtil.equals(null, "han")  = false
     * StringUtil.equals("han", null)  = false
     * StringUtil.equals("han", "han") = true
     * StringUtil.equals("han", "HAN") = false
     * </pre>
     * 
     * @see java.lang.String#equals(Object)
     * @param str1
     *            첫번째 문자열
     * @param str2
     *            두번째 문자열
     * @return 문자열(String)이 일치하면 <code>true</code>
     */
    public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    /**
     * <p>
     * 대소문자를 무시한, 두 문자열(String)이 일치하면 <code>true</code>을 반환한다.
     * </p>
     * 
     * <pre>
     * StringUtil.equalsIgnoreCase(null, null)   = true
     * StringUtil.equalsIgnoreCase(null, "")     = false
     * StringUtil.equalsIgnoreCase("", null)     = false
     * StringUtil.equalsIgnoreCase(null, "han")  = false
     * StringUtil.equalsIgnoreCase("han", null)  = false
     * StringUtil.equalsIgnoreCase("han", "han") = true
     * StringUtil.equalsIgnoreCase("han", "HAN") = true
     * </pre>
     * 
     * @see java.lang.String#equalsIgnoreCase(String)
     * @param str1
     *            첫번째 문자열
     * @param str2
     *            두번째 문자열
     * @return 대소문자를 무시한 문자열(String)이 일치하면 <code>true</code>
     */
    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
    }

    /**
     * <pre>
     * 여러 문자열을 치환할 경우 사용
     * </pre>
     * 
     * <pre>
     * StringUtil.format(&quot;{0} 테스트 {1}&quot;, &quot;변환&quot;, &quot;입니다&quot;) = &quot;변환 테스트 입니다&quot;
     * </pre>
     * 
     * @param str
     * @param obj
     * @return
     */
    public static String format(String str, Object... obj) {
        if (str == null || obj == null)
            return str;

        for (int i = 0; i < obj.length; i++)
            str = replace(str, "{" + i + "}", String.valueOf(obj[i]));

        return str;
    }

    /**
     * 문자열에서 이전문자를 새 문자로 대체해서 반환
     * 
     * @param s
     *            문자열
     * @param oldSub
     *            이전문자
     * @param newSub
     *            새 문자
     * @return String 결과 문자열
     */
    public static String replace(String s, String oldSub, String newSub) {
        if ((s == null) || (oldSub == null) || (newSub == null)) {
            return null;
        }

        int y = s.indexOf(oldSub);

        if (y >= 0) {
            StringBuffer sb = new StringBuffer();
            int length = oldSub.length();
            int x = 0;

            while (x <= y) {
                sb.append(s.substring(x, y));
                sb.append(newSub);
                x = y + length;
                y = s.indexOf(oldSub, x);
            }

            sb.append(s.substring(x));

            return sb.toString();
        } else {
            return s;
        }
    }

    /**
     * <p>
     * 문자열을 구분자로 나누어서, 문자열 배열로 만든다.
     * </p>
     * <p>
     * 배열의 문자열 중에 <code>null</code>과 공백("")도 포함한다.
     * </p>
     * 
     * <pre>
     * StringUtil.split("h-a-n", "-") = ["h", "a", "n"]
     * StringUtil.split("h--n", "-")  = ["h", "", "n"]
     * StringUtil.split(null, *)      = null
     * </pre>
     * 
     * @param str
     *            문자열
     * @param separator
     *            구분자
     * @return 구분자로 나누어진 문자열 배열
     */
    public static String[] split(String str, String separator) {
        if (str == null) {
            return null;
        }
        String[] result;
        int i = 0; // index into the next empty array element

        // --- Declare and create a StringTokenizer
        StringTokenizer st = new StringTokenizer(str, separator);
        // --- Create an array which will hold all the tokens.
        result = new String[st.countTokens()];

        // --- Loop, getting each of the tokens
        while (st.hasMoreTokens()) {
            result[i++] = st.nextToken();
        }

        return result;
    }

    /**
     * <p>
     * 시작 인덱스부터 문자열을 자는다.
     * </p>
     * <p>
     * 시작 인덱스가 0보다 작거나, 문자열의 총길이보다 크면 공백("")을 반환한다.
     * </p>
     * 
     * <pre>
     * StringUtil.substring(null, *)    = null
     * StringUtil.substring("", *)      = ""
     * StringUtil.substring("han", 1)   = "an"
     * StringUtil.substring("han", 615) = ""
     * StringUtil.substring("han", -1)  = ""
     * </pre>
     * 
     * @param str
     * @param beginIndex
     *            시작 인덱스(0부터 시작)
     * @return
     */
    public static String substring(String str, int beginIndex) {
        if (str == null) {
            return null;
        }

        if (beginIndex < 0) {
            return EMPTY;
        }

        if (beginIndex > str.length()) {
            return EMPTY;
        }

        return str.substring(beginIndex);
    }

    /**
     * <p>
     * 시작 인덱스부터 끝 인덱스까지 문자열을 자는다.
     * </p>
     * <p>
     * 시작 인덱스또는 끝 인덱스가 0보다 작으면 공백("")을 반환한다.
     * </p>
     * 
     * <pre>
     * StringUtil.substring(null, *, *)    = null
     * StringUtil.substring("", *, *)      = ""
     * StringUtil.substring("han", 1, 2)   = "a"
     * StringUtil.substring("han", 1, 3)   = "an"
     * StringUtil.substring("han", 1, 615) = "an"
     * StringUtil.substring("han", -1, *)  = ""
     * StringUtil.substring("han", *, -1)  = ""
     * </pre>
     * 
     * @param str
     * @param beginIndex
     * @param endIndex
     * @return
     */
    public static String substring(String str, int beginIndex, int endIndex) {
        if (str == null) {
            return null;
        }

        if (beginIndex < 0 || endIndex < 0) {
            return EMPTY;
        }

        if (endIndex > str.length()) {
            endIndex = str.length();
        }

        if (beginIndex > endIndex || beginIndex > str.length()) {
            return EMPTY;
        }

        return str.substring(beginIndex, endIndex);
    }

    /**
     * <p>
     * 처음 발견한 구분자의 위치까지 문자열을 자른다.
     * </p>
     * 
     * <pre>
     * StringUtil.substringBefore(null, *)       = null
     * StringUtil.substringBefore("", *)         = ""
     * StringUtil.substringBefore("han", null)   = "han"
     * StringUtil.substringBefore("han", "")     = ""
     * StringUtil.substringBefore("hanhan", "a") = "h"
     * StringUtil.substringBefore("hanhan", "g") = "hanhan"
     * </pre>
     * 
     * @param str
     *            문자열
     * @param separator
     *            구분자
     * @return
     */
    public static String substringBefore(String str, String separator)
    {
        if (isEmpty(str) || separator == null) { return str; }
        if (separator.length() == 0) { return EMPTY; }
        int endIndex = str.indexOf(separator);
        if (endIndex == INDEX_NOT_FOUND) { return str; }
        return str.substring(0, endIndex);
    }
    
    /**
     * <p>
     * 시작 문자부터 끝 문자열까지 자른다.
     * </p>
     * 
     * <pre>
     * StringUtil.substringBetween(null, *, *)       = null
     * StringUtil.substringBetween(*, null, *)       = null
     * StringUtil.substringBetween(*, *, null)       = null
     * StringUtil.substringBetween("h<a>n", "<", ">") = "a"
     * StringUtil.substringBetween("h<a><b>n", "<", ">") = "a"
     * </pre>
     * 
     * @param str
     *            문자열
     * @param separator
     *            구분자
     * @return
     * @since 1.1
     */
    public static String substringBetween(String str, String open, String close)
    {
        if (str == null || open == null || close == null) { return null; }
        
        int start = str.indexOf(open);
        if (start != INDEX_NOT_FOUND)
        {
            int end = str.indexOf(close, start + open.length());
            if (end != INDEX_NOT_FOUND)
            {
                return str.substring(start + open.length(), end);
            }
            else
            {
                // 끝이 없으면 null이 좋을까, 끝까지가 좋을까...
            }
        }
        return null;
    }
    
    /**
     * URL에서 해당 파라미터를 제외하고 반환한다.
     * 
     * @param url
     * @param excepts
     * @return
     */
    public static String removeParameter(String url, List<String> excepts) {
        if (url == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();

        try {
            URL mUrl = new URL(url);
            sb.append(mUrl.getProtocol()).append("://").append(mUrl.getHost()).append(mUrl.getPath()).append("?");
            String[] params = split(mUrl.getQuery(), "&");
            for (String p : params) {
                String[] arr = split(p, "=");
                if (!excepts.contains(arr[0])) {
                    sb.append("&").append(p);
                }
            }
        } catch (MalformedURLException e) {
            return null;
        }

        return sb.toString();
    }

    /**
     * URL의 파라미터의 값을 반환
     * 
     * @param url
     *            URL
     * @param param
     *            파라미터명
     * @return
     */
    public static String getParamValue(String url, String param) {
        if ((url == null) || (param == null))
            return null;

        StringBuffer sb = new StringBuffer();
        try {
            String[] arrStr = split(new URL(url).getQuery(), "&");
            if (arrStr != null && arrStr.length > 0) {
                for (String str : arrStr) {
                    String[] compare = split(str, "=");
                    if (equals(param, compare[0])) {
                        sb.append(compare[1]);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            return "";
        }

        return sb.toString();
    }

    /**
     * <p>
     * 문자열이 해당 길이보다 크면, 자른 후 줄임말을 붙여준다.
     * </p>
     * <p>
     * 길이는 기본문자들(영어/숫자등)이 1으로, 다국어(한글등)이면 2로 계산한다.
     * </p>
     * 
     * <pre>
     * StringUtil.curtail(null, *, *) = null
     * StringUtil.curtail("abcdefghijklmnopqr", 10, null) = "abcdefghij"
     * StringUtil.curtail("abcdefghijklmnopqr", 10, "..") = "abcdefgh.."
     * StringUtil.curtail("한글을 사랑합시다.", 10, null)   = "한글을 사랑"
     * StringUtil.curtail("한글을 사랑합시다.", 10, "..")   = "한글을 사.."
     * </pre>
     * 
     * 
     * @param str
     *            문자열
     * @param size
     *            길이(byte 길이)
     * @param tail
     *            줄임말
     * @return
     */
    public static String curtail(String str, int size, String tail) {
        if (str == null) {
            return null;
        }
        int strLen = str.length();
        int tailLen = (tail != null) ? tail.length() : 0;
        int maxLen = size - tailLen;
        int curLen = 0;
        int index = 0;
        for (; index < strLen && curLen < maxLen; index++) {
            if (Character.getType(str.charAt(index)) == Character.OTHER_LETTER) {
                curLen++;
            }
            curLen++;
        }

        if (index == strLen) {
            return str;
        } else {
            StringBuilder result = new StringBuilder();
            result.append(str.substring(0, index));
            if (tail != null) {
                result.append(tail);
            }
            return result.toString();
        }
    }

}