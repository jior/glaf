package glaf.batch;

import java.util.List;

public class TestFileBatch extends SigleFileBatch{

	@Override
	public void runBatch() {
		//入参fileBodyContext为检查文件内容
		// TODO Auto-generated method stub
		
		/**
		//获取第一行的3列
		String s0 = ((String[])fileBodyContext.get(0))[0];
		String s1 = ((String[])fileBodyContext.get(0))[1];
		String s2 = ((String[])fileBodyContext.get(0))[2];
		
		logFile.info("开始runBatch;");
		this.dba.update("insert into T_CELLEDITING values('" + s0 + "','" + s1 + "','','" + s2 + "','')");
		logFile.info("结束runBatch;");
		**/
		
		
		logFile.info("开始runBatch;");
		
		/**
		//获取文件全部数据,检查出错时抛出异常
		try{
			List fileBodyContext = super.getAllRecords();
			if(null != fileBodyContext && fileBodyContext.size()>0){
				logFile.info("文件数据---------start");
				for(int i=0;i<fileBodyContext.size();i++){
					logFile.info(((String[])fileBodyContext.get(i))[0]+","+
							     ((String[])fileBodyContext.get(i))[1]+","+
							     ((String[])fileBodyContext.get(i))[2]+","+
							     ((String[])fileBodyContext.get(i))[3]);
				}
				logFile.info("文件数据---------end");
			}
			logFile.info("检查正常");
		}catch(Exception e){
			logFile.error("检查出错，异常中断");
		}
		**/
		
		/**
		//获取文件全部数据,检查出错时不抛出异常，返回检查正确的数据
		try{
			List fileBodyContext = super.getAllRecordsContinue();
			if(null != fileBodyContext && fileBodyContext.size()>0){
				logFile.info("文件数据---------start");
				for(int i=0;i<fileBodyContext.size();i++){
					logFile.info(((String[])fileBodyContext.get(i))[0]+","+
							     ((String[])fileBodyContext.get(i))[1]+","+
							     ((String[])fileBodyContext.get(i))[2]+","+
							     ((String[])fileBodyContext.get(i))[3]);
				}
				logFile.info("文件数据---------end");
			}
			logFile.info("检查正常");
		}catch(Exception e){
			logFile.error("检查出错，异常中断");
		}
		**/
		
		/**
		//分批获取数据，check出错时中断
		while(true){
			List fileBodyContext = super.getMoreRecords();
			if(null == fileBodyContext){
				break;
			}
			for(int i=0;i<fileBodyContext.size();i++){
				logFile.info(((String[])fileBodyContext.get(i))[0]+","+
							     ((String[])fileBodyContext.get(i))[1]+","+
							     ((String[])fileBodyContext.get(i))[2]+","+
							     ((String[])fileBodyContext.get(i))[3]);
			}
		}
		**/
		
		/**
		//分批获取数据，check出错时返回正确数据
		while(true){
			List fileBodyContext = super.getMoreRecordsContinue();
			if(null == fileBodyContext){
				break;
			}
			for(int i=0;i<fileBodyContext.size();i++){
				logFile.info(((String[])fileBodyContext.get(i))[0]+","+
					     ((String[])fileBodyContext.get(i))[1]+","+
					     ((String[])fileBodyContext.get(i))[2]+","+
					     ((String[])fileBodyContext.get(i))[3]);
			}
		}
		**/
		
		/**
		//分批获取指定条数数据，check出错时抛出异常
		while(true){
			List fileBodyContext = super.getMoreRecords(new Long(5));
			if(null == fileBodyContext){
				break;
			}
			for(int i=0;i<fileBodyContext.size();i++){
				logFile.info(((String[])fileBodyContext.get(i))[0]+","+
							     ((String[])fileBodyContext.get(i))[1]+","+
							     ((String[])fileBodyContext.get(i))[2]+","+
							     ((String[])fileBodyContext.get(i))[3]);
			}
		}
		**/
		
		
		//分批获取指定条数数据，check出错时继续得到正确数据
		while(true){
			List fileBodyContext = super.getMoreRecordsContinue(new Long(5));
			if(null == fileBodyContext){
				break;
			}
			logFile.info("---------------------");
			for(int i=0;i<fileBodyContext.size();i++){
				logFile.info(((String[])fileBodyContext.get(i))[0]+","+
							     ((String[])fileBodyContext.get(i))[1]+","+
							     ((String[])fileBodyContext.get(i))[2]+","+
							     ((String[])fileBodyContext.get(i))[3]);
			}
		}
		
		logFile.info("结束runBatch;");
	}

	@Override
	public void setCheckConfigId() {
		// TODO Auto-generated method stub
		//设置csv检查配置文件的id
		setCheckConfigId("测试001");
	}

	@Override
	public void setConfigFileName() {
		// TODO Auto-generated method stub
		//设置csv检查配置文件名
		setConfigFileName("configfiles/testBatchInOutCheckConfig.xml");
	}

}
