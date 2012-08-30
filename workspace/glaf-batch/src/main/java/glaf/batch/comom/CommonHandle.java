package glaf.batch.comom;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import glaf.batch.BatchConstans;
import glaf.batch.exception.sys.OperFileFailedException;
import glaf.batch.exception.sys.SetCheckConfigException;
import glaf.batch.log.BatchExeLogFile;

public class CommonHandle {
	
	protected BatchExeLogFile logFile;
	
	public CommonHandle(BatchExeLogFile logFile) {
		super();
		this.logFile = logFile;
	}
	
	public List getAllRecordsBreak(String fileType,Object inputFile,Object errorDataFile){
		logFile.info("getMoreRecords参数：fileType=" + fileType);
		List ret = null;
		try{
			if(fileType.equals(BatchConstans.FILE_TYPE_CSV)){
				CsvHelper ch = (CsvHelper)inputFile;
				if (!ch.getData()) {
					logFile.error("获取文件内容失败！");
					logFile.error("------start--------");
					// 获取失败
					for (int i = 0; i < ch.getMessageList().size(); i++) {
						logFile.error(ch.getMessageList().get(i));
					}
					logFile.error("------end--------");
					throw new OperFileFailedException("获取文件内容失败！");
				}
				
				try{
					checkCsvData(ch,BatchConstans.FILE_CHECK_MODE_BREAK);
				}catch(Exception e){
					CsvHelper errorDataCsv = (CsvHelper)errorDataFile;
					List eln = ch.getErrorLineNoList();
					List temp = ch.getCsvBodyList();
					if(null != eln && eln.size() > 0){
						for(int i=eln.size()-1;i>=0;i--){
							Integer no = Integer.parseInt((String)eln.get(i));
							errorDataCsv.writeRecord((String[])temp.get(no.intValue()));
						}
						errorDataCsv.close();
					}
					throw e;
				}
				
				ret = ch.getCsvBodyList();
			}
		}catch(Exception e){
			throw new OperFileFailedException(e);
		}
		
		return ret;
	}

	public List getAllRecordsContinue(String fileType,Object inputFile,Object errorDataFile){
		logFile.info("getMoreRecords参数：fileType=" + fileType);
		List ret = null;
		try{
			if(fileType.equals(BatchConstans.FILE_TYPE_CSV)){
				CsvHelper ch = (CsvHelper)inputFile;
				if (!ch.getData()) {
					logFile.error("获取文件内容失败！");
					logFile.error("------start--------");
					// 获取失败
					for (int i = 0; i < ch.getMessageList().size(); i++) {
						logFile.error(ch.getMessageList().get(i));
					}
					logFile.error("------end--------");
					throw new OperFileFailedException("获取文件内容失败！");
				}
				//如果需要检查数据，则先检查数据，检查出错时抛异常
				boolean checkRet = true;
				checkRet = checkCsvData(ch,BatchConstans.FILE_CHECK_MODE_CONTINUE);
				
				ret = ch.getCsvBodyList();
				//如果检查失败，则从数据列表中将错误的数据剔除掉
				CsvHelper errorDataCsv = (CsvHelper)errorDataFile;
				if(!checkRet){
					List eln = ch.getErrorLineNoList();
					if(null != eln && eln.size() > 0){
						for(int i=eln.size()-1;i>=0;i--){
							Integer no = Integer.parseInt((String)eln.get(i));
							errorDataCsv.writeRecord((String[])ret.get(no.intValue()));
							ret.remove(no.intValue());
						}
						errorDataCsv.close();
					}
				}
			}
		}catch(Exception e){
			throw new OperFileFailedException(e);
		}
		
		return ret;
	}
	
	public List getMoreRecordsBreak(String fileType,Object inputFile,Object errorDataFile){

		//return getMoreRecordsBreak(fileType,inputFile,errorDataFile,new Long(10000));
		return getMoreRecordsBreak(fileType,inputFile,errorDataFile,new Long(5));
		
	}
	
	public List getMoreRecordsContinue(String fileType,Object inputFile,Object errorDataFile){
		
		//return getMoreRecordsContinue(fileType,inputFile,errorDataFile,new Long(10000));
		return getMoreRecordsContinue(fileType,inputFile,errorDataFile,new Long(5));
		
	}
	
	public List getMoreRecordsBreak(String fileType,Object inputFile,Object errorDataFile,Long nextRecordsCount){
		
		List ret = null;
		try{
			if(fileType.equals(BatchConstans.FILE_TYPE_CSV)){
				CsvHelper ch = (CsvHelper)inputFile;
				if (!ch.getMoreData(nextRecordsCount)) {
					logFile.error("获取文件内容失败！");
					logFile.error("------start--------");
					// 获取失败
					for (int i = 0; i < ch.getMessageList().size(); i++) {
						logFile.error(ch.getMessageList().get(i));
					}
					logFile.error("------end--------");
					throw new OperFileFailedException("获取文件内容失败！");
				}
				//如果需要检查数据，则先检查数据，检查出错时抛异常
				try{
					checkCsvData(ch,BatchConstans.FILE_CHECK_MODE_BREAK);
				}catch(Exception e){
					CsvHelper errorDataCsv = (CsvHelper)errorDataFile;
					List eln = ch.getErrorLineNoList();
					List temp = ch.getCsvBodyList();
					if(null != eln && eln.size() > 0){
						for(int i=eln.size()-1;i>=0;i--){
							Integer no = Integer.parseInt((String)eln.get(i));
							errorDataCsv.writeRecord((String[])temp.get(no.intValue()));
						}
						errorDataCsv.close();
					}
					throw e;
				}
				ret = ch.getCsvBodyList();
			}
		}catch(Exception e){
			throw new OperFileFailedException(e);
		}
		
		return ret;
		
	}
	
	public List getMoreRecordsContinue(String fileType,Object inputFile,Object errorDataFile,Long nextRecordsCount){
		
		List ret = null;
		try{
			if(fileType.equals(BatchConstans.FILE_TYPE_CSV)){
				CsvHelper ch = (CsvHelper)inputFile;
				if (!ch.getMoreData(nextRecordsCount)) {
					logFile.error("获取文件内容失败！");
					logFile.error("------start--------");
					// 获取失败
					for (int i = 0; i < ch.getMessageList().size(); i++) {
						logFile.error(ch.getMessageList().get(i));
					}
					logFile.error("------end--------");
					throw new OperFileFailedException("获取文件内容失败！");
				}
				//如果需要检查数据，则先检查数据，检查出错时抛异常
				boolean checkRet = checkCsvData(ch,BatchConstans.FILE_CHECK_MODE_CONTINUE);
				
				ret = ch.getCsvBodyList();
				
				//如果检查失败，则从数据列表中将错误的数据剔除掉
				if(!checkRet){
					List eln = ch.getErrorLineNoList();
					if(null != eln && eln.size() > 0){
						CsvHelper errorDataCsv = (CsvHelper)errorDataFile;
						for(int i=eln.size()-1;i>=0;i--){
							Integer no = Integer.parseInt((String)eln.get(i));
							errorDataCsv.writeRecord((String[])ret.get(no.intValue()));
							ret.remove(no.intValue());
						}
						errorDataCsv.close();
					}
				}
			}
			
		}catch(Exception e){
			throw new OperFileFailedException(e);
		}
		
		return ret;
		
	}

	private boolean checkCsvData(CsvHelper ch,String checkMode){
		try{
			if(!ch.checkData()){
				
				logFile.error("检查文件失败！失败原因:");
				logFile.error("------start--------");
				for (int i = 0; i < ch.getMessageList().size(); i++) {
					//System.out.println(ch.getMessageList().get(i));
					logFile.error(ch.getMessageList().get(i));
				}
				logFile.error("------end--------");
				
				if(checkMode.equals(BatchConstans.FILE_CHECK_MODE_BREAK)){
					throw new OperFileFailedException("检查文件失败！");
				}
				return false;
			}
		}catch(Exception e){
			throw new OperFileFailedException(e);
		}
		return true;
	}

	public BatchExeLogFile getLogFile() {
		return logFile;
	}

	public void setLogFile(BatchExeLogFile logFile) {
		this.logFile = logFile;
	}
	
	
}
