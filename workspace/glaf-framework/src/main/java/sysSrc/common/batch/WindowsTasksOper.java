package sysSrc.common.batch;

import baseSrc.framework.BaseException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import sysSrc.orm.TBatchconfig;

public class WindowsTasksOper {
	
	public void deleteTask(TBatchconfig bc){
		String cmd = "schtasks /delete /tn " + bc.getBatname() + " /f";
		exeCmdWaitForResult(cmd);
	}
	
	public void deleteTask(String batchName){
		String cmd = "schtasks /delete /tn " + batchName + " /f";
		exeCmdWaitForResult(cmd);
	}
	
	public void addTask(TBatchconfig bc){
		String cmd = "schtasks /create  /ru vss /rp vss /sc ";
		BigDecimal interval = bc.getInterval();
		Date exeDate = bc.getLastexedate();
		
		if(0==interval.compareTo(new BigDecimal(1))){
			//按天循环
			cmd = cmd + " daily ";
		}else if(0==interval.compareTo(new BigDecimal(7))){
			//按周循环
			cmd = cmd + " weekly ";
			
			Calendar cal = Calendar.getInstance();   
			cal.setTime(exeDate); 
			int weekday = cal.get(cal.DAY_OF_WEEK);
			String weekly = "";
			switch (weekday){
				case 1:  weekly = "SUN"; break;
				case 2:  weekly = "MON"; break;
				case 3:  weekly = "TUE"; break;
				case 4:  weekly = "WED"; break;
				case 5:  weekly = "THU"; break;
				case 6:  weekly = "FRI"; break;
				case 7:  weekly = "SAT"; break;
			}
			cmd = cmd + " /d " + weekly;
		}else if(0==interval.compareTo(new BigDecimal(30))){
			//按月循环
			cmd = cmd + " monthly /d " + exeDate.getDay();
		}
		String what = bc.getWhat();
		cmd = cmd + " /tn " + bc.getBatname() +  " /tr \"" + what +  "\"  ";
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String s = sf.format(exeDate);
		String yyyymmdd = s.substring(0,10);
		String hhmmss = s.substring(11,s.length());
		cmd = cmd + " /st " + hhmmss + " /sd " + yyyymmdd;

		exeCmdWaitForResult(cmd);
	}
	
	/**
	 * 创建新进程以执行命令，并等待命令执行结果
	 * @param cmd
	 */
	public void exeCmdWaitForResult(String cmd){
		Runtime run = Runtime.getRuntime();
		try {
			Process p = run.exec(cmd);// 启动另一个进程来执行命令
			BufferedInputStream in = new BufferedInputStream(p.getInputStream());
			BufferedReader inBr = new BufferedReader(new InputStreamReader(in));

			String lineStr = "";
			while (inBr.readLine() != null){
				lineStr = lineStr + inBr.readLine();
			}
			if (p.waitFor() != 0) {
				if (p.exitValue() == 1){// p.exitValue()==0表示正常结束，1：非正常结束
					throw new BaseException("操作定时任务失败:" + lineStr);
				}
			}
			inBr.close();
			in.close();
		} catch (Exception e) {
			throw new BaseException("操作定时任务异常: " + e.getMessage());
		}
	}
	
	/**
	 * 创建新进程以执行命令，不等待命令执行结果
	 * @param cmd
	 */
	public void exeCmdNotWaitForResult(String cmd){
		Runtime run = Runtime.getRuntime();
		try {
			Process p = run.exec(cmd);// 启动另一个进程来执行命令
			/**
			BufferedInputStream in = new BufferedInputStream(p.getInputStream());
			BufferedReader inBr = new BufferedReader(new InputStreamReader(in));

			String lineStr = "";
			while (inBr.readLine() != null){
				lineStr = lineStr + inBr.readLine();
			}
			if (p.waitFor() != 0) {
				if (p.exitValue() == 1){// p.exitValue()==0表示正常结束，1：非正常结束
					throw new BaseException("操作定时任务失败:" + lineStr);
				}
			}
			inBr.close();
			in.close();
			**/
		} catch (Exception e) {
			throw new BaseException("操作命令异常: " + e.getMessage());
		}
	}
}
