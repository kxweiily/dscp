package com.topideal.dscp.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 日期工具类
 * lizhenni 2020-06-17 11:30
 */
public class DateUtils {

    public static String NORMAL_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当天时间 date 格式
     *
     * @param fmt 时间格式 如"yyyy-MM-dd"
     * @return
     * @throws Exception
     */
    public static Date getNow(String fmt) throws Exception {
        long timeMillis = System.currentTimeMillis();
        if (StringUtils.isNotBlank(fmt)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fmt);
            return simpleDateFormat.parse(format(new Date(timeMillis), fmt));
        }
        return new Date(timeMillis);

    }


    /**
     * 获取昨天时间 date 格式
     */
    public static Date getYesterdayDate(String fmt) throws Exception {
        if (StringUtils.isNotBlank(fmt)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fmt);
            return simpleDateFormat.parse(format(minusDay(new Date(System.currentTimeMillis()), 1), fmt));
        }
        return minusDay(new Date(System.currentTimeMillis()), 1);

    }


    /**
     * 获取近七天时间 date 格式
     */

    public static Date getSevenDaysAgoDate() {
        return minusDay(new Date(System.currentTimeMillis()), 7);
    }

    /**
     * 格式化当前时间
     *
     * @return
     */
    public static String formatFullTime2() {
        return format(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 格式化时间
     *
     * @param date
     * @param fmt
     * @return
     */
    public static String format(Date date, String fmt) {
        if (date == null) {
            return "";
        }
        DateFormat formatter = new SimpleDateFormat(fmt);
        return formatter.format(date);
    }

    /**
     * 减几天
     *
     * @param date   时间
     * @param dayNum 要减的天数
     * @return
     */
    public static Date minusDay(Date date, int dayNum) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - dayNum);
        return cal.getTime();
    }

    /**
     * 日期格式字符串转换成日期
     *
     * @param str
     * @return
     */
    public static Date string2Date(String str) {
        SimpleDateFormat format = new SimpleDateFormat(NORMAL_FORMAT);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 日期格式字符串转换成日期(iDM日期使用)
     *
     * @param str
     * @return
     */
    public static Date stringToIdmDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 时间戳转换成字符串
     *
     * @param timestamp 时间戳
     * @return
     */
    public static String timestamp2String(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat(NORMAL_FORMAT);
        Long time = new Long(timestamp);
        String date = format.format(time);
        return date;
    }

    /**
     * 时间字符串转时间戳
     *
     * @param dateStr
     * @return
     */
    public static Long string2Timestamp(String dateStr) {
        Date date = string2Date(dateStr);
        return date.getTime();
    }
    
    /**
     * 计算两个日期之间的天数
     * @param starStr  “yyyy-MM-dd”
     * @param endStr “yyyy-MM-dd”
     * @return
     */
    public static Integer daysBetween(String starStr,String endStr) {
    	DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
    	Integer day=0;
		try {
			Date star = null;//开始时间
			Date endDay= null;//结束时间
			Date day1 = dft.parse(starStr);
			Date day2= dft.parse(endStr);
			if(day1.before(day2)) {
				star = day1;
				endDay= day2;
			}else {
				star = day2;
				endDay= day1;
			}
			
			
	        Date nextDay=star;
	        while(nextDay.before(endDay)){//当明天不在结束时间之前是终止循环
	        	Calendar cld = Calendar.getInstance();
	 	        cld.setTime(star);
	 	        cld.add(Calendar.DATE, 1);
	 	        star = cld.getTime();
	 	        //获得下一天日期字符串
	 	        nextDay = star; 
	 	        day+=1;
	        }
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day;
    }
}
