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

import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 * 이 클래스는 날짜 관련 함수를 제공합니다.
 * </p>
 * 
 * @author 작은광명
 * @version 1.0
 * @created 2013. 10. 7. 오후 1:39:37
 * @history
 */
public class DateUtil {
    /**
     * Timestamp를 가지고 Decimal 타입(yyyymmddhh24miss)로 변환하여 반환한다.
     * 
     * @param timestamp
     *            타임스탬프 (long)
     * @return
     */
    public static long getDecimalByTimestamp(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        return getCurrentTimeLongType(cal);
    }

    /**
     * DB에 저장한 Decimal 타입(yyyymmddhh24miss)을 받아서 실제 timestamp 형식으로 반환한다.
     * 
     * @param time
     *            long type (yyyymmddhh24miss)
     * @return timestamp
     */
    public static long getTimestampByDecimal(long time) {
        String date = String.valueOf(time);
        return getTimestampByDecimal(date);
    }

    /**
     * DB에 저장한 Decimal 타입(yyyymmddhh24miss)을 받아서 실제 timestamp 형식으로 반환한다.
     * 
     * @param time
     *            String type (yyyymmddhh24miss)
     * @return timestamp
     */
    public static long getTimestampByDecimal(String date) {
        if (date == null || date.length() < 14)
            return 0;

        String year = StringUtil.substring(date, 0, 4);
        String month = StringUtil.substring(date, 4, 6);
        String day = StringUtil.substring(date, 6, 8);
        String hour = StringUtil.substring(date, 8, 10);
        String minute = StringUtil.substring(date, 10, 12);
        String second = StringUtil.substring(date, 12, 14);

        Date d = toDate(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day), Integer.valueOf(hour), Integer.valueOf(minute), Integer.valueOf(second));

        return d.getTime();
    }

    /**
     * MySQL에 입력할 Date형식인 Decimal(17,3) 형식으로 <BR>
     * 현재 시간을 변환하여 반환한다.
     * 
     * @return String yyyymmddhh24miss.sss
     */
    public static String getCurrentTimeDecimal() {
        return getCurrentTimeDecimal(Calendar.getInstance());
    }

    /**
     * MySQL에 입력할 Date형식인 Decimal(17,3) 형식으로 <BR>
     * 전달받은 timestampl 파라미터 날짜를 변환하여 반환한다.
     * 
     * @param timestamp
     * @return
     */
    public static String getCurrentTimeDecimal(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);

        return String.format("%04d%02d%02d%02d%02d%02d.%03d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND), cal.get(Calendar.MILLISECOND));
    }

    /**
     * MySQL에 입력할 Date형식인 Decimal(17,3) 형식으로 <BR>
     * 전달받은 cal 파라미터 날짜를 변환하여 반환한다.
     * 
     * @param cal
     *            설정할 Calender 값
     * @return String wwwwmmddhh24miss.sss
     */
    public static String getCurrentTimeDecimal(Calendar cal) {
        return String.format("%04d%02d%02d%02d%02d%02d.%03d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND), cal.get(Calendar.MILLISECOND));
    }

    /**
     * 현재 시간의 Decimal 타입중 Long 타입으로 반환한다.
     * 
     * @return
     */
    public static long getCurrentTimeLongType(Calendar cal) {
        String dateString = String.format("%04d%02d%02d%02d%02d%02d%03d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND), cal.get(Calendar.MILLISECOND));
        return Long.valueOf(dateString);
    }

    /**
     * 현재 시간의 Decimal 타입중 Long 타입으로 반환한다.
     * 
     * @return
     */
    public static long getCurrentTimeLongType() {
        return getCurrentTimeLongType(Calendar.getInstance());
    }

    /**
     * <p>
     * 년, 월, 일, 시, 분, 초를 입력받아 날짜형(Date)으로 변환한다.
     * </p>
     * 
     * @param year
     *            년
     * @param month
     *            월(1-12)
     * @param day
     *            일
     * @param hour
     *            시
     * @param min
     *            분
     * @param sec
     *            초
     * @return
     */
    public static Date toDate(int year, int month, int day, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, hour, minute, second);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

}
