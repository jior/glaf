package com.glaf.base.modules.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
 
import com.glaf.base.utils.PropertyUtilExtends;

public class BaseUtil {
	
	
 
	/**
	 * String->String
	 */
	public static String stringToString(String str){
		return str!=null?str:"";
	}
	
	/**
	 * Date -> String
	 */
	public static String dateToString(Date date){
		return dateToString(date,null);
	}
	/**
	 * Date -> String
	 */
	public static String dateToString(Date date,String format){
		String retStr = "";
		if( date!=null ){
			SimpleDateFormat sdf = new SimpleDateFormat((format==null?"yyyy-MM-dd":format));
			retStr = sdf.format(date);
		}
		return retStr;
	}
	
	/**
	 * String -> Date
	 */
	public static Date stringToDate(String date){
		return stringToDate(date,null);
	}
	/**
	 * String -> Date
	 */
	public static Date stringToDate(String date,String format){
		Date retDate = null;
		if( date!=null&&!"".equals(date)){
			SimpleDateFormat sdf = new SimpleDateFormat((format==null?"yyyy-MM-dd":format));
			try {
				retDate = sdf.parse(date);
			} catch (ParseException e) {}
		}
		return retDate;
	}
	
	/** 格式化数据并四舍五入
	 * 
	 * @param number 
	 * @param digits    小数位数
	 * @return
	 */
	public static String getFormatCurrency (double number) {
		return getFormatCurrency(number,0);
	}
	public static String getFormatCurrency (double number, int digits) {
		return String.valueOf(number);
	}
	
	/** 格式化数据并四舍五入
	 * 
	 * @param number 
	 * @param digits    小数位数
	 * @return
	 */
	public static String getFormarPercentage(double number) {
		return getFormarPercentage(number,0) ;
	}
	public static String getFormarPercentage(double number, int digits) {
		return String.valueOf(number)+ "%";
	}
	
	/**
	 * 拷贝BEAN
	 * @param dest
	 * @param orig
	 */
	
	public static void copyProperties(Object dest, Object orig){
		try{
			PropertyUtilExtends pue = new PropertyUtilExtends();
			//pue.setFormat("yyyy-MM-dd hh:mm:ss");
			pue.copyCustomerProperties(dest, orig);	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
