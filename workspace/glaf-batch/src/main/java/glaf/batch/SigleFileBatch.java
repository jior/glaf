package glaf.batch;

import java.io.File;
import java.util.List;

import glaf.batch.comom.CommonHandle;
import glaf.batch.comom.CsvHelper;
import glaf.batch.exception.bus.BusException;
import glaf.batch.exception.sys.BackupFileFailedException;
import glaf.batch.exception.sys.BatchIsLockedException;
import glaf.batch.exception.sys.MultiFileException;
import glaf.batch.exception.sys.OperFileFailedException;
import glaf.batch.exception.sys.SysException;
import glaf.batch.log.BatchExeLogFile;

public abstract class SigleFileBatch extends BaseBatch{

	private String fileFullName;
	private String checkConfigId;
	private String configFileName;
	private String fileType;
	private CsvHelper ch;
	private CsvHelper errorDataFile;

	public abstract void runBatch();
	public abstract void setCheckConfigId();
	public abstract void setConfigFileName();
	
	public void run() {
		try 
		{
			if (logFile==null) {
				logFile = new BatchExeLogFile(this.batchID);
			}
			logFile.setClazzName("SigleFileBatch");
			
			commonPrepare();
			//备份文件
			backUpFile();
			
			//业务batch给定列检查配置id
			setConfigFileName();
			setCheckConfigId();
			
			
			//判断文件类型，根据文件类型调用不同的方法读取文件内容
			String csvfile = (String)super.getArgs().get("-c");
			String txtfile = (String)super.getArgs().get("-t");
			
			if(!"".equals(csvfile) && !"".equals(txtfile)){
				logFile.error("文件源有两个：" + csvfile + "," + txtfile);
				throw new MultiFileException("只能有一个文件源！");
			}else if(!"".equals(csvfile)){
				this.fileFullName = csvfile;
				this.fileType = BatchConstans.FILE_TYPE_CSV;
				
				ch = null;
				//检查文件内容
				logFile.info("csv文件checkConfigId：" + checkConfigId);
				if(null != checkConfigId && !"".equals(checkConfigId)){
					ch = new CsvHelper(csvfile,checkConfigId,configFileName);
				}else{
					ch = new CsvHelper(csvfile);
				}

			}else if(!"".equals(txtfile)){
				this.fileFullName = txtfile;
				this.fileType = BatchConstans.FILE_TYPE_TXT;
			}
			
			logFile.info("批处理开始：" + this.batchID);
			//runBatch(this.fileContext);
			runBatch();
			dba.commit();
			logFile.info("批处理结束：" + this.batchID);
			
			logFile.info("开始解锁：" + this.batchID);
			super.unlockBatch();
			logFile.info("解锁成功：" + this.batchID);
			
			
			if(null != this.errorDataFile){
				logFile.info("记录check错误数据文件：" + this.errorDataFile.getFileName());
				File f = new File(this.errorDataFile.getFileName());
				super.logCheckErrorDataFile(super.dbLogID,f.getName(),this.errorDataFile.getFileName());
			}
			
			logDB.jobExeLog(super.dbLogID,
					        this.batchID,
					        "OK",
					        "",
					        logFile,
					        getSBatchExeDate(),
					        getEBatchExeDate(),
					        (String)super.args.get("-u"));
			

		} catch(Exception e) {
			if(!(e instanceof BatchIsLockedException)){
				//解锁batch
				logFile.info("开始解锁：" + this.batchID);
				super.unlockBatch();
				logFile.info("解锁成功：" + this.batchID);
			}
			//回滚数据
			try{
				if (dba!=null) {
					dba.rollback();
				}
			}catch(Exception ex) {
			}
			
			if(null != this.errorDataFile){
				logFile.info("记录check错误数据文件：" + this.errorDataFile.getFileName());
				File f = new File(this.errorDataFile.getFileName());
				super.logCheckErrorDataFile(super.dbLogID,f.getName(),this.errorDataFile.getFileName());
			}
			
			//记录日志
			e.printStackTrace();
			logFile.error(e.getMessage());
			
			String msg = "";
			if(!(e instanceof BusException)){
				msg = BatchConstans.NOTBUSEXCEPTION_MSG;
			}else{
				msg = e.getMessage();
			}
			logDB.jobExeLog(super.dbLogID,
					        this.batchID,
					        "NG", 
					        msg,
					        logFile,
					        getSBatchExeDate(),
					        getEBatchExeDate(),
					        (String)super.args.get("-u"));
			
			//发送batch执行失败的邮件
			sendErrorMail();
		}
		finally {
			//解锁batch
			//super.unlockBatch();
			//关闭DB链接
			if (dba != null) {
				try{
				dba.closeConnection();
				} catch(Exception e) {}
			}
		}
	}
	
	private void backUpFile() throws BackupFileFailedException {
		/**
		File fpath = new File(BatchConstans.BATCH_PATH + "\\" + this.getBatchID());
		if(!fpath.exists()){
			fpath.mkdirs();
		}
		File recFile = new File(this.fileFullName);
		File file = new File(BatchConstans.BATCH_PATH + "\\" + this.getBatchID() + "\\" + recFile.getName());
		//throw new BackupFileFailedException("not Complated!"); 
		 **/
		 
	}

	public String getFileFullName() {
		return fileFullName;
	}

	public void setFileFullName(String fileFullName) {
		this.fileFullName = fileFullName;
	}

	public String getCheckConfigId() {
		return checkConfigId;
	}

	public void setCheckConfigId(String checkConfigId) {
		this.checkConfigId = checkConfigId;
	}
	public String getConfigFileName() {
		return configFileName;
	}
	public void setConfigFileName(String configFileName) {
		this.configFileName = configFileName;
	}
	
	public String getFileType() {
		return fileType;
	}
	
	/**
	 * 一次获取文件全部内容,检查出错时抛出异常OperFileFailedException
	 * @return 文件内容list（不包含文件header）
	 */
	public List getAllRecords(){
		List ret = null;
		CommonHandle cch = new CommonHandle(this.logFile);
		try{
			if(this.fileType.equals(BatchConstans.FILE_TYPE_CSV)){
				createErrorDataFile();
				ret = cch.getAllRecordsBreak(this.fileType, this.ch,this.errorDataFile);
			}
		}catch(Exception e){
			throw new SysException(e);
		}
		return ret;
	}
	
	public List getAllRecordsContinue(){
		List ret = null;
		CommonHandle cch = new CommonHandle(this.logFile);
		try{
			if(this.fileType.equals(BatchConstans.FILE_TYPE_CSV)){
				createErrorDataFile();
				
				ret = cch.getAllRecordsContinue(this.fileType, this.ch,this.errorDataFile);
			}
		}catch(Exception e){
			throw new SysException(e);
		}
		return ret;
	}
	
	
	private void createErrorDataFile() throws Exception{
		if(null == this.errorDataFile){
			File csvFile = new File(this.ch.getFileName());
			String dateString = super.getSBatchExeDate("yyyyMMddHHmmss");
			String errorDataFilePath = BatchConstans.BATCH_PATH + 
				"\\logs\\batch_" + csvFile.getName() + "_errordata_" + dateString + ".csv";
			errorDataFile =  new CsvHelper(errorDataFilePath);
			errorDataFile.setDelimiter(ch.getDelimiter());
			errorDataFile.setIsFileAddModel(true);
		}
	}
	
	/**
	 * 获取1万条数据出来
	 * @return
	 */
	public List getMoreRecords(){
		List ret = null;
		CommonHandle cch = new CommonHandle(this.logFile);
		
		try{
			if(this.fileType.equals(BatchConstans.FILE_TYPE_CSV)){
				createErrorDataFile();
				ret = cch.getMoreRecordsBreak(this.fileType, this.ch,this.errorDataFile);
			}
		}catch(Exception e){
			throw new SysException(e);
		}
		return ret;
	}
	
	public List getMoreRecordsContinue(){
		List ret = null;
		CommonHandle cch = new CommonHandle(this.logFile);
		try{
			if(this.fileType.equals(BatchConstans.FILE_TYPE_CSV)){
				createErrorDataFile();
				ret = cch.getMoreRecordsContinue(this.fileType, this.ch, this.errorDataFile);
			}
		}catch(Exception e){
			throw new SysException(e);
		}
		return ret;
	}
	
	/**
	 * 根据传入的数据条数获取相应的数据出来
	 * @param nextRecordsCount
	 * @return
	 */
	public List getMoreRecords(Long nextRecordsCount){
		List ret = null;
		CommonHandle cch = new CommonHandle(this.logFile);
		
		try{
			if(this.fileType.equals(BatchConstans.FILE_TYPE_CSV)){
				createErrorDataFile();
				ret = cch.getMoreRecordsBreak(this.fileType, this.ch,this.errorDataFile,nextRecordsCount);
			}
		}catch(Exception e){
			throw new SysException(e);
		}
		return ret;
	}
	
	public List getMoreRecordsContinue(Long nextRecordsCount){
		List ret = null;
		CommonHandle cch = new CommonHandle(this.logFile);
		
		try{
			if(this.fileType.equals(BatchConstans.FILE_TYPE_CSV)){
				createErrorDataFile();
				ret = cch.getMoreRecordsContinue(this.fileType, this.ch,this.errorDataFile,nextRecordsCount);
			}
		}catch(Exception e){
			throw new SysException(e);
		}
		return ret;
	}
	
	public CsvHelper getErrorDataFile() {
		return errorDataFile;
	}
	public void setErrorDataFile(CsvHelper errorDataFile) {
		this.errorDataFile = errorDataFile;
	}
	
	
}
