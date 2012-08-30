//================================================================================================
//项目名称 ：    基盘
//功    能 ：   Constants
//文件名称 ：    BaseConstants.java                                   
//描    述 ：   基盘需要的常量
//================================================================================================
//修改履历                                                                
//年 月 日		区分		所 属/担 当           		内 容									标识        
//----------   	----   	------------------- ---------------                          ------        
//2009/04/28   	编写   	Intasect/余海涛    	 新規作成                                                                            
//================================================================================================
package baseSrc.framework;

public class BaseConstants {

	public static final String ISC_BASE_BEANCOM_ID = "beanComId";
	public static final String ISC_AUTHORITY_READONLY = "0";
	public static final String ISC_AUTHORITY_UPDATE = "1";
	public static final String ISC_FLG_AUTHORITY = "jspAuthority";
	public static final String ISC_DEFAULT_DTO_ID = "defaultDto";
	public static final String ISC_DEFAULT_DTO_ID_A = "defaultDto_A";
	public static final String ISC_DEFAULT_DTO_ID_B = "defaultDto_B";
	public static final String ISC_ERROR_DTO_ID = "errorDto";
	public static final String ISC_PRE_MESSAGES = "iscpremessages";
	public static final String ISC_SHOW_MESSAGES = "iscshowmessages";
	public static final String ISC_CHECKBOX_CHECKED = "on";
	public static final String ISC_MATH_MAX = "9999999999999";
	public static final String ISC_MATH_MIN = "0";
	public static final String ISC_MATH_FORMAT = "###,##0.0000";
	//文件加密解密密码
	public static final String ISC_ZIP_PASSWORD = "FLAGZIPPASSWORD";
	//分页行数
	public static final int    ISC_PAGE_SIZE_FIVE = 10;
	//压缩文件名称最大长度
	public static final int    ISC_ZIPNAME_SIZE_FIVE = 20;
	//数据库加密标示
	public static final String UPLOAD_DB_ZIP_PASW_FLAG = "1";
	//数据库不加密标示
	public static final String UPLOAD_DB_ZIP_NOPASW_FLAG = "2";
	//压缩文件后缀名
	public static final String UPLOAD_ZIP_LASTNAME="zip";
	//压缩文件后缀名(加密)
	public static final String UPLOAD_ZIP_PASW_LASTNAME = "zipGlaf";
	/** start 共通下拉框 **/
	public static final String ISC_LIGHT_DTO_ID = "lightDTO";
	/** end 共通下拉框 **/
	
	public static enum ReportType {
		PDF, EXCEL, HTML
	}
}
