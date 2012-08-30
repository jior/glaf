package glaf.batch;

import glaf.batch.exception.BatchException;
import glaf.batch.exception.bus.BusException;
import glaf.batch.exception.sys.OperFileFailedException;

import java.util.List;

public class TestMultiFileBatch extends MultiFileBatch{

	@Override
	public void runBatch() {
		// TODO Auto-generated method stub
		logFile.info("开始runBatch;");
		//分段获取第一个csv文件的内容
		while(true){
			List fileBodyContext = super.getMoreRecords(BatchConstans.FILE_TYPE_CSV,1,new Long(5));
			if(null == fileBodyContext){
				break;
			}
			for(int i=0;i<fileBodyContext.size();i++){
				String s0 = ((String[])fileBodyContext.get(i))[0];
				String s1 = ((String[])fileBodyContext.get(i))[1];
				String s2 = ((String[])fileBodyContext.get(i))[2];
				logFile.info("i:" + i + "     s0:" + s0 + "     s1:" + s1 + "     s2:" + s2);
			}
		}
		
		//分段获取第二个csv文件的内容
		while(true){
			List fileBodyContext = super.getMoreRecords(BatchConstans.FILE_TYPE_CSV,2,new Long(5));
			if(null == fileBodyContext){
				break;
			}
			for(int i=0;i<fileBodyContext.size();i++){
				String s0 = ((String[])fileBodyContext.get(i))[0];
				String s1 = ((String[])fileBodyContext.get(i))[1];
				String s2 = ((String[])fileBodyContext.get(i))[2];
				logFile.info("i:" + i + "     s0:" + s0 + "     s1:" + s1 + "     s2:" + s2);
			}
		}
		
		logFile.info("结束runBatch;");
		//throw new BatchException("test");
	}

	@Override
	public void setCheckConfigId() {
		// TODO Auto-generated method stub
		//表示-c参数的第一个文件，设置其检查ID为测试001
		setCheckConfigId(BatchConstans.FILE_TYPE_CSV,1,"测试001");
		//表示-c参数的第二个文件，设置其检查ID为测试001
		setCheckConfigId(BatchConstans.FILE_TYPE_CSV,2,"测试002");
	}

	@Override
	public void setConfigFileName() {
		// TODO Auto-generated method stub
		//表示-c参数的第一个文件，设置其检查文件为测试001
		setConfigFileName(BatchConstans.FILE_TYPE_CSV,1,"configfiles/testBatchInOutCheckConfig.xml");
		//表示-c参数的第二个文件，设置其检查文件为测试001
		setConfigFileName(BatchConstans.FILE_TYPE_CSV,2,"configfiles/cvsInOutCheckConfig.xml");
	}

}
