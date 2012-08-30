package glaf.batch;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import glaf.batch.comom.CommonHandle;
import glaf.batch.comom.CsvHelper;
import glaf.batch.exception.bus.BusException;
import glaf.batch.exception.sys.BackupFileFailedException;
import glaf.batch.exception.sys.BatchIsLockedException;
import glaf.batch.exception.sys.NoFileException;
import glaf.batch.exception.sys.OperFileFailedException;
import glaf.batch.exception.sys.SetCheckConfigException;
import glaf.batch.exception.sys.SysException;
import glaf.batch.log.BatchExeLogFile;

public abstract class MultiFileBatch extends BaseBatch{

	//存放-c传入的多文件源
	private List<Map<String,Object>> csvInputFiles;
	//存放-t传入的多文件源
	private List<Map<String,Object>> txtInputFiles;
	
	public abstract void runBatch();
	public abstract void setCheckConfigId();
	public abstract void setConfigFileName();
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try 
		{
			if (logFile==null) {
				logFile = new BatchExeLogFile(this.batchID);
			}
			logFile.setClazzName("MultiFileBatch");
			
			commonPrepare();
			logFile.info("准备工作调用成功！");
			//备份文件
			backUpFile();
			
			
			//初始化
			this.csvInputFiles = new ArrayList<Map<String,Object>>();
			this.txtInputFiles = new ArrayList<Map<String,Object>>();
			logFile.info("初始化成功！");
			
			//判断文件类型，根据文件类型调用不同的方法读取文件内容
			String csvfiles = (String)super.getArgs().get("-c");
			String txtfiles = (String)super.getArgs().get("-t");
			
			//判断文件源是否为空
			if("".equals(csvfiles.trim()) && "".equals(txtfiles.trim())){
				logFile.error("文件源为空！");
				throw new NoFileException("-c 和 -t 参数都没有设置");
			}
			
			//获取所有csv文件
			String[] csvfile = null;
			if(csvfiles.contains(BatchConstans.MAINFUN_C_PARAS_SPLITOR)){
				csvfile = csvfiles.split(BatchConstans.MAINFUN_C_PARAS_SPLITOR);
			}
			Map<String,String> cArgs = new HashMap<String,String>();
			if(null != csvfile){
				for(int i=0;i<csvfile.length;i++){
					Map<String,Object> temp = new HashMap<String,Object>();
					temp.put("filetype", BatchConstans.FILE_TYPE_CSV);
					temp.put("checkConfigId",null);
					temp.put("configFileName",null);
					temp.put("fileFullName", csvfile[i]);
					temp.put("csvHelper", null);
					temp.put("errorDataCsv", null);
					this.csvInputFiles.add(temp);
					logFile.info("csv文件源：" + i + " --- " + csvfile[i]);
					//将参数更新到基类中
					cArgs.put(String.valueOf(i+1), csvfile[i]);
				}
			}
			logFile.info("获取所有csv文件成功！");
			super.args.remove("-c");
			super.args.put("-c", cArgs);
			logFile.info("重设全局访问变量args的-c参数成功！");
			
			//获取所有txt文件
			String[] txtfile = null;
			if(txtfiles.contains(BatchConstans.MAINFUN_C_PARAS_SPLITOR)){
				txtfile = txtfiles.split(BatchConstans.MAINFUN_C_PARAS_SPLITOR);
			}
			Map<String,String> tArgs = new HashMap<String,String>();
			if(null != txtfile){
				for(int i=0;i<txtfile.length;i++){
					Map<String,Object> temp = new HashMap<String,Object>();
					temp.put("filetype", BatchConstans.FILE_TYPE_TXT);
					temp.put("checkConfigId",null);
					temp.put("configFileName",null);
					temp.put("fileFullName", txtfile[i]);
					this.txtInputFiles.add(temp);
					logFile.info("txt文件源：" + i + " --- " + txtfile[i]);
					//将参数更新到基类中
					tArgs.put(String.valueOf(i+1), txtfile[i]);
				}
			}
			logFile.info("获取所有txt文件成功！");
			super.args.remove("-t");
			super.args.put("-t", tArgs);
			logFile.info("重设全局访问变量args的-t参数成功！");
			
			//业务batch给定列检查配置id
			setConfigFileName();
			logFile.info("设置检查文件名成功！");
			setCheckConfigId();
			logFile.info("设置检查文件id成功！");
			
			//初始化csv文件处理共通对象
			if(null != csvfile){
				for(int i=0;i<csvfile.length;i++){
					Map<String,Object> temp = (Map)this.csvInputFiles.get(i);
					String checkConfigId = (String)temp.get("checkConfigId");
					String configFileName = (String)temp.get("configFileName");
					String fileFullName = (String)temp.get("fileFullName");
					CsvHelper ch = null;
					if(null != configFileName && !"".equals(configFileName.trim())){
						if(null == checkConfigId && "".equals(checkConfigId.trim())){
							logFile.error("检查文件名和检查id必须配合使用！");
							throw new SetCheckConfigException("configFileName 和 checkConfigId 必须同时设置！");
						}
						ch = new CsvHelper(fileFullName,checkConfigId,configFileName);
					}else{
						ch = new CsvHelper(fileFullName);
					}
					temp.remove("csvHelper");
					temp.put("csvHelper", ch);
					this.csvInputFiles.set(i, temp);
				}
			}
			logFile.info("初始化csv文件处理共通(CsvHelper)对象成功！");
			
			//初始化txt文件处理共通对象(暂不实现)
			if(null != txtfile){
				
			}
			
			logFile.info("批处理开始：" + this.batchID);
			runBatch();
			dba.commit();
			logFile.info("批处理结束：" + this.batchID);
			
			logFile.info("开始解锁：" + this.batchID);
			super.unlockBatch();
			logFile.info("解锁成功：" + this.batchID);
			
			if(null != csvfile){
				for(int i=0;i<csvfile.length;i++){
					Map<String,Object> temp = (Map)this.csvInputFiles.get(i);
					CsvHelper errorDataFile = (CsvHelper)temp.get("errorDataCsv");
					if(null != errorDataFile){
						logFile.info("记录check错误数据文件：" + errorDataFile.getFileName());
						File f = new File(errorDataFile.getFileName());
						super.logCheckErrorDataFile(super.dbLogID,f.getName(),errorDataFile.getFileName());
					}
				}
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
			
			//记录日志
			e.printStackTrace();
			logFile.error(e.getMessage());
			
			if(null != csvInputFiles && csvInputFiles.size()>0){
				for(int i=0;i<csvInputFiles.size();i++){
					Map<String,Object> temp = (Map)this.csvInputFiles.get(i);
					CsvHelper errorDataFile = (CsvHelper)temp.get("errorDataCsv");
					if(null != errorDataFile){
						logFile.info("记录check错误数据文件：" + errorDataFile.getFileName());
						File f = new File(errorDataFile.getFileName());
						super.logCheckErrorDataFile(super.dbLogID,f.getName(),errorDataFile.getFileName());
					}
				}
			}
			
			String msg = "";
			if(!(e instanceof BusException)){
				msg = BatchConstans.NOTBUSEXCEPTION_MSG;
			}else{
				msg = e.getMessage();
			}
			logDB.jobExeLog(super.dbLogID,
					        this.batchID,
					        "NG" , 
					        msg ,
					        logFile,
					        getSBatchExeDate(),
					        getEBatchExeDate(),
					        (String)super.args.get("-u"));
			
			//发送batch执行失败的邮件
			sendErrorMail();
		}
		finally {
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
	
	/**
	 * 设置文件源需要检查的checkid
	 * @param fileType      文件类型（csv/txt）
	 * @param argsNum       该文件在-c/-t参数中的顺序（从1开始计算）
	 * @param checkConfigId 检查id
	 */
	public void setCheckConfigId(String fileType,int argsNum,String checkConfigId){
		logFile.info("setCheckConfigId入参：fileType=" + fileType + ", checkConfigId=" + argsNum + ", checkConfigId=" + checkConfigId);
		if(fileType.equals(BatchConstans.FILE_TYPE_CSV)){
			if(null == this.csvInputFiles || 0 == this.csvInputFiles.size() 
					|| argsNum-1 > this.csvInputFiles.size()){
				logFile.error("[setCheckConfigId]:csvInputFiles为null或argsNum参数错误");
				throw new SetCheckConfigException("没有对应的csv文件源_setCheckConfigId");
			}
			Map temp = (Map)this.csvInputFiles.get(argsNum-1);
			temp.put("checkConfigId",checkConfigId);
			this.csvInputFiles.set(argsNum-1, temp);
		}
		if(fileType.equals(BatchConstans.FILE_TYPE_TXT)){
			if(null == this.txtInputFiles || 0 == this.txtInputFiles.size() 
					|| argsNum-1 > this.txtInputFiles.size()){
				logFile.error("[setCheckConfigId]:txtInputFiles为null或argsNum参数错误");
				throw new SetCheckConfigException("没有对应的txt文件源_setCheckConfigId");
			}
			Map temp = (Map)this.txtInputFiles.get(argsNum-1);
			temp.put("checkConfigId",checkConfigId);
			this.txtInputFiles.set(argsNum-1, temp);
		}
	}
	
	/**
	 * 设置文件源需要检查的配置文件名
	 * @param fileType      文件类型（csv/txt）
	 * @param argsNum       该文件在-c/-t参数中的顺序（从1开始计算）
	 * @param checkConfigId 检查文件名
	 */
	public void setConfigFileName(String fileType,int argsNum,String configFileName){
		logFile.info("setConfigFileName：fileType=" + fileType + ", checkConfigId=" + argsNum + ", checkConfigId=" + configFileName);
		if(fileType.equals(BatchConstans.FILE_TYPE_CSV)){
			if(null == this.csvInputFiles || 0 == this.csvInputFiles.size() 
					|| (argsNum-1) > this.csvInputFiles.size()){
				logFile.error("[setCheckConfigId]:csvInputFiles为null或argsNum参数错误");
				throw new SetCheckConfigException("没有对应的csv文件源_setConfigFileName");
			}
			Map temp = (Map)this.csvInputFiles.get(argsNum-1);
			temp.put("configFileName",configFileName);
			this.csvInputFiles.set(argsNum-1, temp);
		}
		if(fileType.equals(BatchConstans.FILE_TYPE_TXT)){
			if(null == this.txtInputFiles || 0 == this.txtInputFiles.size() 
					|| (argsNum-1) > this.txtInputFiles.size()){
				logFile.error("[setCheckConfigId]:txtInputFiles为null或argsNum参数错误");
				throw new SetCheckConfigException("没有对应的txt文件源_setConfigFileName");
			}
			Map temp = (Map)this.txtInputFiles.get(argsNum-1);
			temp.put("configFileName",configFileName);
			this.txtInputFiles.set(argsNum-1, temp);
		}
	}
	
	/**
	 * 一次获取文件全部内容,检查出错时终止
	 * @param fileType 文件类型（csv/txt）
	 * @param argsNum  需要获取的文件在-c/-t参数中的位置（从1开始计数）
	 * @return 文件内容list（不包含文件header）
	 */
	public List getAllRecords(String fileType,int argsNum){
		List ret = null;
		CommonHandle cch = new CommonHandle(this.logFile);
		try{
			if(fileType.equals(BatchConstans.FILE_TYPE_CSV)){
				
				if(null == this.csvInputFiles || 0 == this.csvInputFiles.size() 
						|| (argsNum-1) > this.csvInputFiles.size()){
					logFile.error("[getAllRecords]:csvInputFiles为null或argsNum参数错误");
					throw new SetCheckConfigException("没有对应的csv文件源_getAllRecords");
				}
				Map temp = (Map)this.csvInputFiles.get(argsNum - 1);
				CsvHelper ch = (CsvHelper)temp.get("csvHelper");
				
				createErrorDataFile(temp);
				CsvHelper errorData = (CsvHelper)temp.get("errorDataCsv");				
				
				ret = cch.getAllRecordsBreak(fileType, ch,errorData);
				temp.put("errorDataCsv", errorData);
			}
		}catch(Exception e){
			throw new SysException(e);
		}
		return ret;
	}
	
	private void createErrorDataFile(Map curCsvFile) throws Exception{
		CsvHelper ch = (CsvHelper)curCsvFile.get("csvHelper");
		
		CsvHelper errorData = null;
		if(null != curCsvFile.get("errorDataCsv")){
			errorData = (CsvHelper)curCsvFile.get("errorDataCsv");
		}else{
			File csvFile = new File(ch.getFileName());
			String dateString = super.getSBatchExeDate("yyyyMMddHHmmss");
			String errorDataFilePath = BatchConstans.BATCH_PATH + 
				"\\logs\\batch_" + csvFile.getName() + "_errordata_" + dateString + ".csv";
			errorData = new CsvHelper(errorDataFilePath);
			errorData.setDelimiter(ch.getDelimiter());
			errorData.setIsFileAddModel(true);
			curCsvFile.put("errorDataCsv", errorData);		
			
		}
		
	}

	
	public List getAllRecordsContinue(String fileType,int argsNum){
		List ret = null;
		CommonHandle cch = new CommonHandle(this.logFile);
		try{
			if(fileType.equals(BatchConstans.FILE_TYPE_CSV)){
				
				if(null == this.csvInputFiles || 0 == this.csvInputFiles.size() 
						|| (argsNum-1) > this.csvInputFiles.size()){
					logFile.error("[getAllRecords]:csvInputFiles为null或argsNum参数错误");
					throw new SetCheckConfigException("没有对应的csv文件源_getAllRecords");
				}
				Map temp = (Map)this.csvInputFiles.get(argsNum - 1);
				CsvHelper ch = (CsvHelper)temp.get("csvHelper");
				
				createErrorDataFile(temp);
				CsvHelper errorData = (CsvHelper)temp.get("errorDataCsv");
				
				ret = cch.getAllRecordsContinue(fileType, ch,errorData);
			}
		}catch(Exception e){
			throw new SysException(e);
		}
		return ret;
	}
	
	
	/**
	 * 获取1万条数据出来
	 * @param fileType 文件类型（csv/txt）
	 * @param argsNum  需要获取的文件在-c/-t参数中的位置（从1开始计数）
	 * @return 文件内容list（不包含文件header）
	 */
	public List getMoreRecords(String fileType,int argsNum){
		List ret = null;
		CommonHandle cch = new CommonHandle(this.logFile);

		try{
			if(fileType.equals(BatchConstans.FILE_TYPE_CSV)){
				
				if(null == this.csvInputFiles || 0 == this.csvInputFiles.size() 
						|| (argsNum - 1) > this.csvInputFiles.size()){
					logFile.error("[getMoreRecords]:csvInputFiles为null或argsNum参数错误");
					throw new SetCheckConfigException("没有对应的csv文件源_getAllRecords");
				}
				Map temp = (Map)this.csvInputFiles.get(argsNum - 1);
				CsvHelper ch = (CsvHelper)temp.get("csvHelper");
				
				createErrorDataFile(temp);
				CsvHelper errorData = (CsvHelper)temp.get("errorDataCsv");
				ret = cch.getMoreRecordsBreak(fileType, ch,errorData);
			}
		}catch(Exception e){
			throw new SysException(e);
		}
		return ret;
	}
	
	/**
	 * 根据传入的数据条数获取相应的数据出来
	 * @param fileType 文件类型（csv/txt）
	 * @param argsNum  需要获取的文件在-c/-t参数中的位置（从1开始计数）
	 * @param nextRecordsCount 一次获取的数据条数
	 * @return 文件内容list（不包含文件header）
	 */
	public List getMoreRecords(String fileType,int argsNum,Long nextRecordsCount){
		List ret = null;
		CommonHandle cch = new CommonHandle(this.logFile);
		try{
			if(fileType.equals(BatchConstans.FILE_TYPE_CSV)){
				if(null == this.csvInputFiles || 0 == this.csvInputFiles.size() 
						|| (argsNum-1) > this.csvInputFiles.size()){
					logFile.error("[getMoreRecords]:csvInputFiles为null或argsNum参数错误");
					throw new SetCheckConfigException("没有对应的csv文件源_getAllRecords");
				}
				Map temp = (Map)this.csvInputFiles.get(argsNum - 1);
				CsvHelper ch = (CsvHelper)temp.get("csvHelper");
				
				createErrorDataFile(temp);
				CsvHelper errorData = (CsvHelper)temp.get("errorDataCsv");
				ret = cch.getMoreRecordsBreak(fileType, ch,errorData);
			}
		}catch(Exception e){
			throw new SysException(e);
		}
		return ret;
		}
	
	public List getMoreRecordsContinue(String fileType,int argsNum,Long nextRecordsCount){
		List ret = null;
		CommonHandle cch = new CommonHandle(this.logFile);
		
		try{
			if(fileType.equals(BatchConstans.FILE_TYPE_CSV)){
				if(null == this.csvInputFiles || 0 == this.csvInputFiles.size() 
						|| (argsNum-1) > this.csvInputFiles.size()){
					logFile.error("[getMoreRecords]:csvInputFiles为null或argsNum参数错误");
					throw new SetCheckConfigException("没有对应的csv文件源_getAllRecords");
				}
				Map temp = (Map)this.csvInputFiles.get(argsNum - 1);
				CsvHelper ch = (CsvHelper)temp.get("csvHelper");
				createErrorDataFile(temp);
				CsvHelper errorData = (CsvHelper)temp.get("errorDataCsv");
				ret = cch.getMoreRecordsContinue(fileType, ch,errorData,nextRecordsCount);
			}
		}catch(Exception e){
			throw new SysException(e);
		}
		return ret;
	}
	
	public List getMoreRecordsContinue(String fileType,int argsNum){
		List ret = null;
		CommonHandle cch = new CommonHandle(this.logFile);
		try{
			if(fileType.equals(BatchConstans.FILE_TYPE_CSV)){
				if(null == this.csvInputFiles || 0 == this.csvInputFiles.size() 
						|| (argsNum-1) > this.csvInputFiles.size()){
					logFile.error("[getMoreRecords]:csvInputFiles为null或argsNum参数错误");
					throw new SetCheckConfigException("没有对应的csv文件源_getAllRecords");
				}
				Map temp = (Map)this.csvInputFiles.get(argsNum - 1);
				CsvHelper ch = (CsvHelper)temp.get("csvHelper");
				createErrorDataFile(temp);
				CsvHelper errorData = (CsvHelper)temp.get("errorDataCsv");
				ret = cch.getMoreRecordsContinue(fileType, ch,errorData);
			}
		}catch(Exception e){
			throw new SysException(e);
		}
		return ret;
	}
	
	
	public List<Map<String, Object>> getCsvInputFiles() {
		return csvInputFiles;
	}
	public List<Map<String, Object>> getTxtInputFiles() {
		return txtInputFiles;
	}
	
	
}
