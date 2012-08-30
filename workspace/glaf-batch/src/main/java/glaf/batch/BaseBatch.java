package glaf.batch;

import java.io.File;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import glaf.batch.exception.BatchException;
import glaf.batch.exception.sys.BackupAragsFailedException;
import glaf.batch.exception.sys.BatchIsLockedException;
import glaf.batch.exception.sys.CheckArgsFailedException;
import glaf.batch.exception.sys.LockBatchFailedException;
import glaf.batch.jdbcAccess.DbPersistence;
import glaf.batch.jdbcAccess.JDBCAccess;
import glaf.batch.log.BatchExeLogFile;
import glaf.batch.log.BatchLogDB;
import glaf.batch.mail.Mail;


public abstract class BaseBatch  {
	protected Map<String,Object> args = new HashMap<String,Object>();
	protected BatchExeLogFile logFile;
	protected BatchLogDB logDB = new BatchLogDB();
	protected JDBCAccess dba = null;
	protected String batchID;
	protected Long dbLogID;
	private Date batchExeDate = new Date(System.currentTimeMillis());
	private Date batchExeEndDate;
	private final String CONFIG_FILE = "InputOutputPath.xml";
	private final String ARGS_CHECK_CONFIG_FILE = "ArgsCheckConfig.xml";
	private String inputPath;
	private String outputPath;
	
	/**
	 * 子类必须实现的batch执行流程控制方法
	 */
	public abstract void run();
	
	/**
	 * 锁定batch
	 * @throws LockBatchFailedException
	 */
	protected void lockBatch() throws LockBatchFailedException {
		
		JDBCAccess dbaLocker = null;
		
		try {
			
			DbPersistence dbpLocker = new DbPersistence(BatchConstans.LOCAL_DB_NAMRE);
			dbaLocker = new JDBCAccess(dbpLocker);
			dbaLocker.openConnection();
			
	
			int result = dbaLocker.update("update T_BATCHCONFIG set LOCKFLAG = '" + BatchConstans.LOCK_FLAG_LOCKED + "' " +
					" where BATNAME = '" + this.batchID + "' ");
			if (result<=0) {
				dbaLocker.rollback();
				throw new LockBatchFailedException ();
			}
			dbaLocker.commit();
		}
		catch(Exception e) {
			try{
				if (dbaLocker!=null) {
					dbaLocker.rollback();
				}
			}catch(Exception ex) {
			}
	
			throw new BatchException(e);
		}
		finally {
			if (dbaLocker != null) {
				try{
					dbaLocker.closeConnection();
				} catch(Exception e) {}
			}
		}
	}
	
	/**
	 * 解锁batch
	 */
	protected void unlockBatch() {
		JDBCAccess dbaLocker = null;
		
		try {
			DbPersistence dbpLocker = new DbPersistence(BatchConstans.LOCAL_DB_NAMRE);
			dbaLocker = new JDBCAccess(dbpLocker);
			dbaLocker.openConnection();
			
			dbaLocker.update("update T_BATCHCONFIG set LOCKFLAG = '" + BatchConstans.LOCK_FLAG_UNLOCKED + "' " +
					" where BATNAME = '" + this.batchID + "' ");
			dbaLocker.commit();
		}
		catch(Exception e) {
			try{
				if (dbaLocker!=null) {
					dbaLocker.rollback();
				}
			}catch(Exception ex) {
			}
	
			throw new BatchException(e);
		}
		finally {
			if (dbaLocker != null) {
				try{
					dbaLocker.closeConnection();
				} catch(Exception e) {}
			}
		}
	}
	
	/**
	 * 检查参数
	 * @throws CheckArgsFailedException
	 */
	protected void checkArgs() throws CheckArgsFailedException {
		boolean checkResult = true;
		
		//获取检查配置
		Map argsChecks = getArgsCheckConfig();
		
		//获取-s对应的参数
		List s = (List)args.get("-s");
		
		//循环判断参数是否和正则表达式 匹配
		if(null != s && s.size() > 0){
			for(int i=0;i<s.size();i++){
				String[] check = null;
				if(null != argsChecks.get(String.valueOf(i + 1))){
					check = (String[])argsChecks.get(String.valueOf(i + 1));
				}
				if(null != check && 0 < check.length){
					String regex = check[0];
					String message = "";
					if(1 < check.length){
						message = check[1];
					}
					Pattern pn = Pattern.compile(regex);
					Matcher mr = pn.matcher((String)s.get(i));
					if (!mr.matches()) {
						checkResult = false;
						logFile.error("-s参数检查错误：参数序号：" + (i + 1) + " ;错误内容：" + message);
					}
				}
			}
		}
		
		//如果有不匹配的,则抛出参数检查异常
		if(!checkResult){
			throw new CheckArgsFailedException("参数检查错误，batch执行失败,具体错误请查看日志！");
		}
	}
	
	/**
	 * 备份参数
	 * @throws BackupAragsFailedException
	 */
	protected void backupArags() throws BackupAragsFailedException {
		JDBCAccess dbaLocker = null;
		
		try {
			DbPersistence dbpLocker = new DbPersistence(BatchConstans.LOCAL_DB_NAMRE);
			dbaLocker = new JDBCAccess(dbpLocker);
			dbaLocker.openConnection();
			
			//获取参数，并保存到DB中
			String s_args = "";
			if(!args.keySet().isEmpty()){
				Iterator iterator = args.keySet().iterator();
				while(iterator.hasNext()){
					String key = (String)iterator.next();
					if(args.get(key) instanceof String){
						String value = (String)args.get(key);
						if(!"".equals(value)){
							s_args = s_args + " " + key + " " + value;
						}
						//将输入文件写入到日志表中
						if("-c".equals(key) || "-t".equals(key)){
							if(null != value && !"".equals(value)){
								if(value.contains(",")){
									String[] vals = value.split(",");
									for(int j=0;j<vals.length;j++){
										logIOFile(BatchConstans.FILE_TYPE_INPUT,vals[j]);
									}
								}else{
									logIOFile(BatchConstans.FILE_TYPE_INPUT,value);
								}
							}
						}
					}else if(args.get(key) instanceof List){
						List arg_arg = (List)args.get(key);
						String temp = "";
						if(0 != arg_arg.size()){
							for(int i=0;i<arg_arg.size();i++){
								String value = (String)arg_arg.get(i);
								temp = temp + "," + value;
								
								//将输入文件写入到日志表中
								if("-c".equals(key) || "-t".equals(key)){
									if(null != value && !"".equals(value)){
										logIOFile(BatchConstans.FILE_TYPE_INPUT,value);
									}
								}
							}
							temp = temp.substring(1,temp.length());
						}
						s_args = s_args + " " + key + " " + temp;
					}
				}
			}
			dbaLocker.update("INSERT INTO T_BATCHARGSBACK VALUES('" + this.batchID + "',to_date('" 
					+ getSBatchExeDate() + "','yyyy-mm-dd hh24:mi:ss'),'" + s_args + "')");
			dbaLocker.commit();
		}
		catch(Exception e) {
			try{
				if (dbaLocker!=null) {
					dbaLocker.rollback();
				}
			}catch(Exception ex) {
			}
	
			throw new BatchException(e);
		}
		finally {
			if (dbaLocker != null) {
				try{
					dbaLocker.closeConnection();
				} catch(Exception e) {}
			}
		}
	}

	/**
	 * batch执行错误时发送邮件给指定担当
	 */
	protected void sendErrorMail() {
		//TODO:
		try{
			Mail mail = new Mail();
			mail.sendMail("ser1", this.batchID);
		}catch(Exception e){
			logFile.error(e.getMessage());
		}
	}
	
	protected void initBatInputOutputPath() {
		getPathConfig();
	}
	
	/**
	 * 判断batch是否是锁定状态
	 * @return true 未锁定 
	 *         false 锁定 
	 */
	protected boolean isBatLocked(){
		boolean ret = true;
		
		JDBCAccess dbaLocker = null;
		
		try {
			DbPersistence dbpLocker = new DbPersistence(BatchConstans.LOCAL_DB_NAMRE);
			dbaLocker = new JDBCAccess(dbpLocker);
			dbaLocker.openConnection();
			
			ResultSet rs = dbaLocker.find("select LOCKFLAG from T_BATCHCONFIG where BATNAME = '" + this.batchID + "' for update ");
			String lockFlag = "";
			if(rs.next()){
				lockFlag = (String)rs.getString("LOCKFLAG");
			}
			if(!"0".equals(lockFlag)){
				ret = false;
			}
		}
		catch(Exception e) {
			try{
				if (dbaLocker!=null) {
					dbaLocker.rollback();
				}
			}catch(Exception ex) {
			}
	
			throw new BatchException(e);
		}
		finally {
			if (dbaLocker != null) {
				try{
					dbaLocker.closeConnection();
				} catch(Exception e) {}
			}
		}
		
		return ret;
	}
	
	/**
	 * 判断batch是否是锁定状态,如果没有锁定，则加锁
	 * @return true 未锁定 
	 *         false 锁定 
	 */
	protected boolean ifBatUnLockedThenLockedIt(){
		boolean ret = true;
		
		JDBCAccess dbaLocker = null;
		
		try {
			DbPersistence dbpLocker = new DbPersistence(BatchConstans.LOCAL_DB_NAMRE);
			dbaLocker = new JDBCAccess(dbpLocker);
			dbaLocker.openConnection();
			
			ResultSet rs = dbaLocker.find("select LOCKFLAG from T_BATCHCONFIG where BATNAME = '" + this.batchID + "' for update  ");
			String lockFlag = "";
			if(rs.next()){
				lockFlag = (String)rs.getString("LOCKFLAG");
			}
			if(!"0".equals(lockFlag)){
				ret = false;
			}else{
				//锁定bat
				logFile.info("开始锁定：" + this.batchID);
				int result = dbaLocker.update("update T_BATCHCONFIG set LOCKFLAG = '" + BatchConstans.LOCK_FLAG_LOCKED + "' " +
						" where BATNAME = '" + this.batchID + "' ");
				if (result<=0) {
					dbaLocker.rollback();
					throw new LockBatchFailedException ();
				}
				dbaLocker.commit();
				logFile.info("锁定成功：" + this.batchID);
			}
		}
		catch(Exception e) {
			try{
				if (dbaLocker!=null) {
					dbaLocker.rollback();
				}
			}catch(Exception ex) {
			}
	
			throw new BatchException(e);
		}
		finally {
			if (dbaLocker != null) {
				try{
					dbaLocker.closeConnection();
				} catch(Exception e) {}
			}
		}
		
		return ret;
	}
	
	/**
	 * 继承本类的batch处理类，在调用业务处理前的共通准备工作
	 */
	protected void commonPrepare() {

		//生成DB日志的id
		createDBLogID();
		
		
		//打开数据库链接
		if (dba != null) {
			dba.openConnection();
		}else {
			DbPersistence dbPersistence = new DbPersistence(BatchConstans.LOCAL_DB_NAMRE);
			dba = new JDBCAccess(dbPersistence);
			dba.openConnection();
		}
		dba.setSqlConfigFile(this.batchID + ".sql.xml");
		
		//备份参数
		backupArags();
		logFile.info("备份参数成功");
		//检查参数
		checkArgs();
		logFile.info("检查参数成功");
		
		//读取输入输出路径
		initBatInputOutputPath();
		logFile.info("获取输入输出路径成功");
		logFile.info("inputPath：" + this.inputPath);
		logFile.info("outputPath：" + this.outputPath);
		
		//判断batch是否为锁定状态，如果是则退出本次执行,如果不是，则加锁
		if(!ifBatUnLockedThenLockedIt()){
			throw new BatchIsLockedException("batch正在运行中，本次执行取消！");
		}
		//if(!isBatLocked()){
		//	throw new BatchIsLockedException("batch正在运行中，本次执行取消！");
		//}
		//锁定batch
		//logFile.info("开始锁定：" + this.batchID);
		//lockBatch();
		//logFile.info("锁定成功：" + this.batchID);


	}
	
	/**
	 * 记录输入输出文件
	 * @param fileIOType 输入/输出类型
	 * @param filePath   文件全路径
	 */
	protected void logIOFile(String fileIOType,String filePath){
		JDBCAccess dbaLocker = null;
		
		try {
			
			DbPersistence dbpLocker = new DbPersistence(BatchConstans.LOCAL_DB_NAMRE);
			dbaLocker = new JDBCAccess(dbpLocker);
			dbaLocker.openConnection();
			
			File iFile = new File(filePath);
			String fileName = iFile.getName();
	
			int result = dbaLocker.update("insert into t_batchiofile " +
					" (ID,IOFLAG,FILEPATH,FILENAME) values " +
					" (" + this.dbLogID + ",'" + fileIOType + "','" + filePath + "','" + fileName + "')");
			
			if (result<=0) {
				dbaLocker.rollback();
				throw new BatchException ("记录输入/输出文件异常");
			}
			dbaLocker.commit();
		}catch(Exception e) {
			try{
				if (dbaLocker!=null) {
					dbaLocker.rollback();
				}
			}catch(Exception ex) {
			}
	
			throw new BatchException(e);
		}finally {
			if (dbaLocker != null) {
				try{
					dbaLocker.closeConnection();
				} catch(Exception e) {}
			}
		}
	}
	
	protected void logCheckErrorDataFile(Long logId,String fileName,String filePath) throws LockBatchFailedException {
		
		JDBCAccess dbaLocker = null;
		
		try {
			
			DbPersistence dbpLocker = new DbPersistence(BatchConstans.LOCAL_DB_NAMRE);
			dbaLocker = new JDBCAccess(dbpLocker);
			dbaLocker.openConnection();
			
	
			int result = dbaLocker.insert("insert into T_BATCHCHECKERRORDADA(ID,FILEPATH,FILENAME) " +
					   " values(" + logId + ", '" + filePath + "','" + fileName + "') ");
			if (result<=0) {
				dbaLocker.rollback();
				throw new LockBatchFailedException ();
			}
			dbaLocker.commit();
		}
		catch(Exception e) {
			try{
				if (dbaLocker!=null) {
					dbaLocker.rollback();
				}
			}catch(Exception ex) {
			}
	
			throw new BatchException(e);
		}
		finally {
			if (dbaLocker != null) {
				try{
					dbaLocker.closeConnection();
				} catch(Exception e) {}
			}
		}
	}
	
	/**
	 * 获取记录db日志的id
	 */
	private void createDBLogID(){
		JDBCAccess dbaLocker = null;
		
		try {
			
			DbPersistence dbpLocker = new DbPersistence(BatchConstans.LOCAL_DB_NAMRE);
			dbaLocker = new JDBCAccess(dbpLocker);
			dbaLocker.openConnection();
	
			ResultSet result = dbaLocker.find("select S_BATCHEXELOGID.nextval as id from dual");
			if(result.next()){
				this.dbLogID = result.getLong(1);
			}
		}catch(Exception e) {
			throw new BatchException(e);
		}finally {
			if (dbaLocker != null) {
				try{
					dbaLocker.closeConnection();
				} catch(Exception e) {}
			}
		}
	}
	
	private void getPathConfig() {
		try {
			// 设置配置文件路径
			String configFile = BatchConstans.BATCH_PATH + "\\configfiles\\" + CONFIG_FILE;
			File f = new File(configFile);
			//读取文件信息
			SAXReader reader = new SAXReader();
			Document doc = reader.read(f);
			Element root = doc.getRootElement();
			Element defaultPath = null;
			
			this.inputPath = "";
			this.outputPath = "";
			for (Iterator i = root.elementIterator("defaultPath"); i.hasNext();) {
				defaultPath = (Element) i.next();
					this.inputPath = (String)defaultPath.elementText("inputpath");
					this.outputPath = (String)defaultPath.elementText("outputpath");
				
			}
			Element batPath = null;
			for (Iterator i = root.elementIterator("batPath"); i.hasNext();) {
				batPath = (Element) i.next();
				if(this.batchID.equals((String)batPath.elementText("batname"))){
					this.inputPath = (String)batPath.elementText("inputpath");
					this.outputPath = (String)batPath.elementText("outputpath");
				}
			}
		} catch (DocumentException e) {
			throw new BatchException(e);
		}
	}
	
	/**
	 * 根据当前batch的name获取参数检查配置文件内容
	 * 返回值：键名String：参数的顺序号
	 *         键值String[]：0--检查的正则表达式
	 *                       1--检查出错时需要提示的错误信息
	 * @return
	 */
	private Map<String,String[]> getArgsCheckConfig() {
		Map ret = new HashMap();
		try {
			// 设置配置文件路径
			String configFile = BatchConstans.BATCH_PATH + "\\configfiles\\" + ARGS_CHECK_CONFIG_FILE;
			File f = new File(configFile);
			//读取文件信息
			SAXReader reader = new SAXReader();
			Document doc = reader.read(f);
			Element root = doc.getRootElement();
			Element batArgsCheckConfig = null;
			
			for (Iterator i = root.elementIterator("batArgsCheckConfig"); i.hasNext();) {
				batArgsCheckConfig = (Element) i.next();
				if(this.batchID.equals((String)batArgsCheckConfig.elementText("batname"))){
					Element args = null;
					for (Iterator j = batArgsCheckConfig.elementIterator("args"); j.hasNext();) {
						args = (Element) j.next();
						String[] check = new String[2];
						check[0] = (String)args.elementText("chectRegex");
						check[1] = (String)args.elementText("chectMessage");
						ret.put((String)args.attributeValue("index"), check);
					}
				}
			}
		} catch (DocumentException e) {
			throw new BatchException(e);
		}
		return ret;
	}

	public Map<String, Object> getArgs() {
		return args;
	}
	public void setArgs(Map<String, Object> args) {
		this.args = args;
	}
	public BatchExeLogFile getLogFile() {
		return logFile;
	}

	public void setLogFile(BatchExeLogFile logFile) {
		this.logFile = logFile;
	}

	public BatchLogDB getLogDB() {
		return logDB;
	}

	public void setLogDB(BatchLogDB logDB) {
		this.logDB = logDB;
	}

	public JDBCAccess getDba() {
		return dba;
	}

	public void setDba(JDBCAccess dba) {
		this.dba = dba;
	}

	public String getBatchID() {
		return batchID;
	}

	public void setBatchID(String batchID) {
		this.batchID = batchID;
	}

	public String getSBatchExeDate() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(this.batchExeDate);
	}

	public String getSBatchExeDate(String format) {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.format(this.batchExeDate);
	}
	
	public String getEBatchExeDate() {
		this.batchExeEndDate = new Date(System.currentTimeMillis());
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(this.batchExeEndDate);
	}
	
	public String getEBatchExeDate(String format) {
		this.batchExeEndDate = new Date(System.currentTimeMillis());
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.format(this.batchExeEndDate);
	}

	public String getInputPath() {
		return inputPath;
	}

	public void setInputPath(String inputPath) {
		this.inputPath = inputPath;
	}

	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}
	
	
}
