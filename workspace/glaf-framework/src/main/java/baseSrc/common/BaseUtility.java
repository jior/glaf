//================================================================================================
//项目名称 ：    基盘
//功    能 ：   基盘共通操作
//文件名称 ：    BaseUtility.java                                   
//描    述 ：    
//================================================================================================
//修改履历                                                                
//年 月 日		区分		所 属/担 当           		内 容									标识        
//----------   	----   	------------------- ---------------                          ------        
//2009/04/28   	编写   	Intasect/廖学志    	 新規作成                                                                            
//================================================================================================

package baseSrc.common;

import java.text.DecimalFormat;
import java.util.List;

import baseSrc.framework.BaseConstants;


public class BaseUtility {
	
	public static long getTestProcessDifination()
	{
		return 2031820;
	}

//	/**
//	 * 判断字符串是否为空//	 * @param str
//	 * @return
//	 */
//	public static boolean isStringNull(String str){
//		boolean ret = false;
//		
//		if(null == str || "".equals(str)){
//			ret = true;
//		}
//		return ret;
//	}
	
	/**
	 * 判断List是否为空
	 * @param list
	 * @return
	 */
	public static boolean isListNull(List<?> list){
		boolean ret = false;
		
		if(null == list || 0 == list.size()){
			ret = true;
		}
		return ret;
	}

	/**
	 * 判断字符串是否为空过滤"null"
	 * @param str
	 * @return
	 */
	public static boolean isStringNull(String str){
		boolean ret = false;
		
		if(null == str || "".equals(str) || "null".equals(str)){
			ret = true;
		}
		return ret;
	}
	
	/**
	 * 取得一个随机数
	 * @param max 随即数最大值
	 * @param min 随即数最小值
	 * @param format 随即数式样
	 * @return
	 */
	private static double getRounNumber(String max,String min){
		double d = Math.random();
		try{
			double dmax = Double.parseDouble(max);
			double dmin = Double.parseDouble(min);
			if(dmax < dmin){
				return 0;
			}
			d = (dmax - dmin)*d+dmin;
		}catch (Exception e) {
			d = 0 ;
		}
		return d;
	}
	/**
	 * 取得一个随机数
	 * @param max 随即数最大值
	 * @param min 随即数最小值
	 * @param format 随即数式样
	 * @return
	 */
	public static String getRounNumber(String max,String min,String format){
		String ret = "";
		try{
			if(BaseUtility.isStringNull(max)){
				max = BaseConstants.ISC_MATH_MAX;
			}
			if(BaseUtility.isStringNull(min)){
				min = BaseConstants.ISC_MATH_MIN;
			}
			if(BaseUtility.isStringNull(format)){
				format = BaseConstants.ISC_MATH_FORMAT;
			}
			
			double d = getRounNumber(max,min);
			ret = BaseUtility.getFormat(d, format);
		}catch (Exception e) {
			ret = "" ;
		}
		return ret;
	}
	
	/**
	 * 格式化函数
	 * @param d
	 * @param format 式样
	 * @return
	 */
	public static String getFormat(Double d,String format){
		String ret = "";
		try{
		if(d == 0){
			return "";
		}
		DecimalFormat   df2   =   new   DecimalFormat( format); 
		ret = df2.format(d);
		}catch (Exception e) {
			ret = d + "" ;
		}
		return ret;
	}
	
	/**
	 * 格式化函数
	 * @param str
	 * @param format 式样
	 * @return
	 */
	public static String getFormat(String str,String format){
		String ret = "";
		try{
		if(BaseUtility.isStringNull(str)){
			return "";
		}
		DecimalFormat   df2   =   new   DecimalFormat( format); 
		ret = df2.format(Double.parseDouble(str));
		}catch (Exception e) {
			ret = str;
		}
		return ret;
	}
	
	public static String getString(Object obj){
		String ret = "";
		if(obj != null){
			ret = obj.toString();
		}
		if(BaseUtility.isStringNull(ret)){
			ret = "";
		}
		return ret;
	}
	
//	public static void main(String[] args) {
//		for(int i=0;i<1000;i++){
//			String str = BaseUtility.getRounNumber("99", "1", "##0.0000");
//			System.out.println(str);
//		}
//	}
}
