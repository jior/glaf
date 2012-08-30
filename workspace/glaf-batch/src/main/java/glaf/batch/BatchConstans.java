package glaf.batch;

public class BatchConstans {
	//默认的本地DB链接
	public static String LOCAL_DB_NAMRE = "localdb";
	
	//batch是否锁定的标志
	public static String LOCK_FLAG_LOCKED = "1"; //锁定
	public static String LOCK_FLAG_UNLOCKED = "0"; //未锁定
	
	//batch运行主目录
	public static String BATCH_PATH = ""; 
	
	public static String MAINFUN_OTHERPARAS_SPLITOR = ",";
	public static String MAINFUN_C_PARAS_SPLITOR = ",";
	
	//文件类型
	public static final String FILE_TYPE_CSV = "CSV";
	public static final String FILE_TYPE_TXT = "TXT";
	
	//输入输出区分
	public static final String FILE_TYPE_INPUT = "I";
	public static final String FILE_TYPE_OUTPUT = "O";
	
	//消息
	public static final String NOTBUSEXCEPTION_MSG = "请联系管理员！";
	
	//文件检查模式
	public static final String FILE_CHECK_MODE_BREAK = "BREAK";
	public static final String FILE_CHECK_MODE_CONTINUE = "CONTINUE";
}
