package glaf.batch;

import glaf.batch.exception.bus.BusException;
import glaf.batch.exception.sys.BatchIsLockedException;
import glaf.batch.jdbcAccess.DbPersistence;
import glaf.batch.jdbcAccess.JDBCAccess;
import glaf.batch.log.BatchExeLogFile;

public abstract class DbBatch extends BaseBatch{

	/**
	 * 业务batch处理实现
	 */
	public abstract void runBatch();
	
	/**
	 * batch执行过程控制
	 */
	public void run() {
		
		try 
		{
			if (logFile==null) {
				logFile = new BatchExeLogFile(this.batchID);
			}
			logFile.setClazzName("DbBatch");

			commonPrepare(); 
			
			logFile.info("批处理开始：" + this.batchID);
			runBatch();
			dba.commit();
			logFile.info("批处理结束：" + this.batchID);
			
			logFile.info("开始解锁：" + this.batchID);
			super.unlockBatch();
			logFile.info("解锁成功：" + this.batchID);
			
			//记录执行结果日志
			logDB.jobExeLog(super.dbLogID,
					        this.batchID,
					        "OK",
					        "",
					        logFile,
					        getSBatchExeDate(),
					        getEBatchExeDate(),
					        (String)super.args.get("-u"));
			
		}catch(Exception e) {
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
			
			String msg = "";
			if(!(e instanceof BusException)){
				msg = BatchConstans.NOTBUSEXCEPTION_MSG;
			}else{
				msg = e.getMessage();
			}
			logDB.jobExeLog(super.dbLogID,
					        this.batchID,
					        "NG" , 
					        msg,
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
}
