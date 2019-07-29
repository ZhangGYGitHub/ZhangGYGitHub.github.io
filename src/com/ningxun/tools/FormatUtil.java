package com.ningxun.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtil {
	
	/**
	 * long类型的时间转换成String类型的时间
	 * @author hujian
	 * @date 2017年8月13日
	 * @version 1.0
	 * @param time
	 * @return 返回格式 yyyy-MM-dd HH:mm:ss
	 */
	public static final String formatDateTime(Long time){
		if(time  == null ) return "";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return  df.format(new Date(time)); 
	}
	
	/**
	 * 获取日期时间
	 * @author hujian
	 * @date 2017年7月20日
	 * @version 1.0
	 * @param time
	 * @return 返回格式 yyyy-MM-dd HH:mm
	 */
	public static final String formatDateTime(Date time){
		if(time == null ) return "";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return  df.format(time); 
	}
	
	/**
	 * 获取日期
	 * @author hujian
	 * @date 2017年7月20日
	 * @version 1.0
	 * @param time
	 * @return 返回格式 yyyy-MM-dd
	 */
	public static final String formatDate(Date time){
		if(time == null ) return "";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return  df.format(time); 
	}
	
	/**
	 * 获取时间
	 * @author hujian
	 * @date 2017年7月20日
	 * @version 1.0
	 * @param time
	 * @return 返回格式 HH:mm
	 */
	public static final String formatTime(Date time){
		if(time == null ) return "";
		DateFormat df = new SimpleDateFormat("HH:mm");
		return  df.format(time); 
	}
	
	/**
	 * 获取星期几
	 * @author hujian
	 * @date 2017年7月20日
	 * @version 1.0
	 * @param time
	 * @return 返回格式 HH:mm
	 */
	public static final String formatWeek(Date time){
		if(time == null ) return "";
		DateFormat df = new SimpleDateFormat("EEEE");
		return  df.format(time); 
	}
	
//	public static void main(String[] args) {
//		System.out.println(formatWeek(new Date()));
//	}
}
