/**
 * 记录batch执行日志,事务单独处理，与业务执行不在同一个事务
 */
package glaf.batch.log;

import glaf.batch.BatchConstans;
import glaf.batch.jdbcAccess.DbPersistence;
import glaf.batch.jdbcAccess.JDBCAccess;

public class BatchLogDB {
	//数据库链接对象
	private JDBCAccess dba;

	/**
	 * 构造函数
	 */
	public BatchLogDB(){
		DbPersistence dbp = new DbPersistence(BatchConstans.LOCAL_DB_NAMRE);
		this.dba = new JDBCAccess(dbp);
	}
	
	/**
	 * 写job自动执行时产生的日志
	 * @param autoExeBatchLog
	 */
	public void jobExeLog(Long dbLogID, String batName,String exeResult,String exeResultMemo,
			BatchExeLogFile logFile,String batExeDate,String batExeEndDate, String batExeUser){
		this.dba.openConnection();
		try{
			this.dba.insert("insert into T_BATCHEXELOG " +
							" (ID,BATNAME,EXEDATE,EXECONTEXT,EXERESULT,EXEUSER," +
							"  LOGFILE,EXEENDDATE,BATBUSNAME,EXERESULTMEMO,LOGFILEPATH) " +
					        " values (" + dbLogID + "," +
					        "         '" + batName + "', " +
					        "         to_date('" + batExeDate + "','yyyy-mm-dd hh24:mi:ss')," +
					        "         ''," +
					        "         '" + exeResult + "'," + 
					        "         '" + batExeUser + "'," +
					        "         '" + logFile.getFile().getName() + "'," +
					        "         to_date('" + batExeEndDate + "','yyyy-mm-dd hh24:mi:ss')," +
					        "         (select BATBUSNAME from T_BATCHCONFIG where BATNAME = '" + batName + "')," +
					       	"         '" + exeResultMemo + "'," +
					        "         '" + logFile.getFile().getAbsolutePath() + "') ");
			this.dba.commit();
		}catch(Exception e){
			this.dba.rollback();
		}finally{
			this.dba.closeConnection();
		}
	}
	
	/**
	 * 写job自动执行时产生的日志
	 * @param autoExeBatchLog
	 */
	public void jobExeLog(String batName,String exeResult,String logFile){
		this.dba.openConnection();
		try{
			this.dba.insert("insert into T_BATCHEXELOG " +
							" (BATNAME,EXEDATE,EXECONTEXT,EXERESULT,EXEUSER,LOGFILE) " +
					        " values ('" + batName + "', " +
					        "        sysdate," +
					        "        (select WHAT from T_BATCHCONFIG where BATNAME = '" + batName + "')," +
					        "        '" + exeResult + "'," + 
					        "        'job'," +
					        "        '" + logFile + "') ");
			this.dba.commit();
		}catch(Exception e){
			this.dba.rollback();
		}finally{
			this.dba.closeConnection();
		}
	}

	/**
	 * 写手动执行时产生的日志
	 * @param batName   batch名称，对应t_batchconfig中的batname字段
	 * @param exeResult 执行结果：OK/NG
	 * @param exeUser   
	 * @param logFile
	 */
	public void manualExeLog(String batName,String exeResult,String exeUser,String logFile,String batExeDate){
		this.dba.openConnection();
		try{
			this.dba.insert("insert into T_BATCHEXELOG " +
							" (BATNAME,EXEDATE,EXECONTEXT,EXERESULT,EXEUSER,LOGFILE) " +
					        "values ('" + batName + "', " +
					        "        to_date('" + batExeDate + "','yyyy-mm-dd hh24:mi:ss')," +
					        "       (select WHAT from T_BATCHCONFIG where NAME = '" + batName + "')," +
					        "       EXERESULT = '" + exeResult + "'," + 
					        "       EXEUSER = '" + exeUser + "'," +
					        "       LOGFILE = '" + logFile + "') ");
			this.dba.commit();
		}catch(Exception e){
			this.dba.rollback();
		}finally{
			this.dba.closeConnection();
		}
	}
	
	/**
	 * 写手动执行时产生的日志
	 * @param batName   batch名称，对应t_batchconfig中的batname字段
	 * @param exeResult 执行结果：OK/NG
	 * @param exeUser   
	 * @param logFile
	 */
	public void manualExeLog(String batName,String exeResult,String exeUser,String logFile){
		this.dba.openConnection();
		try{
			this.dba.insert("insert into T_BATCHEXELOG " +
							" (BATNAME,EXEDATE,EXECONTEXT,EXERESULT,EXEUSER,LOGFILE) " +
					        "values ('" + batName + "', " +
					        "        sysdate," +
					        "       (select WHAT from T_BATCHCONFIG where NAME = '" + batName + "')," +
					        "       EXERESULT = '" + exeResult + "'," + 
					        "       EXEUSER = '" + exeUser + "'," +
					        "       LOGFILE = '" + logFile + "') ");
			this.dba.commit();
		}catch(Exception e){
			this.dba.rollback();
		}finally{
			this.dba.closeConnection();
		}
	}
	
}
