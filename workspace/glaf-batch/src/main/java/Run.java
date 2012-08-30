import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import glaf.batch.BaseBatch;
import glaf.batch.BatchConstans;
import glaf.batch.exception.BatchException;
import glaf.batch.log.BatchExeLogFile;

public class Run {

	/**定时运行主函数
	 * @param args 参数：-b 需要执行的具体业务把batch（必须有的参数）
	 *                   -c csv文件
	 *                   -t txt文件
	 *                   -s 其他参数（多个时以逗号进行区分）
	 *                   -u 执行者（为空时表示自动触发），
	 *                      如果为自动触发，则默认为job，
	 *                      如果是日志管理画面认为触发，则为出发者名称
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		
		
		List<String> otherPara = new ArrayList<String>();
		
		Map<String,Object> allArgs = new HashMap<String,Object>();
		
		
		//获取当前路径
		String sfile = Run.class.getResource("/").getFile();
		String spath = java.net.URLDecoder.decode(sfile,"utf-8");  
		BatchConstans.BATCH_PATH = spath;
		String lastChar = StringUtils.right(BatchConstans.BATCH_PATH, 1);
		if (StringUtils.equals(lastChar, "/") 
				|| StringUtils.equals(lastChar, "\\")) {
			BatchConstans.BATCH_PATH = StringUtils.left(BatchConstans.BATCH_PATH, 
					BatchConstans.BATCH_PATH.length()-1);
		}
		
		//记录日志
		BatchExeLogFile logFile = new BatchExeLogFile("Run");
		logFile.info("String[] args:" + args);
		logFile.info("BatchConstans.BATCH_PATH:" + BatchConstans.BATCH_PATH);
		
		try {
			//参数解析
			//-b 指定要执行的业务batch
			String b = getParaValue("-b", args);
			if (StringUtils.isEmpty(b)) {
				throw new BatchException("未指定批处理文件。");
			}
			logFile.info("-b:" + b);
			
			//-c 指定csv文件数据源位置
			String c = getParaValue("-c", args);
			logFile.info("-c:" + c);
			//-t 指定txt文件数据源位置
			String t = getParaValue("-t", args);
			logFile.info("-t:" + t);
			//-s 其他参数，以逗号区分
			String s = getParaValue("-s", args);
			logFile.info("-s:" + s);
			if (!StringUtils.isEmpty(s)) {
				String[] paras = StringUtils.split(s, 
						BatchConstans.MAINFUN_OTHERPARAS_SPLITOR);
				if (paras != null && paras.length > 0) {
					for (String para : paras) {
						otherPara.add(para);
					}
				}
			}
			String u = getParaValue("-u", args);
			if(null == u || "".equals(u)){
				u = "job";
			}
			logFile.info("-u:" + u);
			
			//生成batch实例
			Class<?> newBatch = Class.forName(b);
			BaseBatch batch = (BaseBatch)newBatch.newInstance();
			logFile.info("batch创建实例成功");
			
			String bName = batch.getClass().getSimpleName();
			batch.setBatchID(bName);
			batch.setLogFile(new BatchExeLogFile(bName));
			logFile.info("batchName:" + bName);
			
			//将参数传递给batch
			allArgs.put("-c", c);
			allArgs.put("-t", t);
			allArgs.put("-s", otherPara);
			allArgs.put("-u", u);
			batch.setArgs(allArgs);
			logFile.info("参数成功传递给了batch。");
			
			//执行batch
			batch.run();
			
		} catch (Exception e) {
			e.printStackTrace();
			logFile.error(e.getMessage());
		}
	}
	
	/**
	 * 分解参数
	 * @param key  参数命令标识
	 * @param args 需要分解出的参数
	 * @return     分解出的参数值
	 * @throws BatchException
	 */
	public static String getParaValue(String key, String[] args) throws BatchException {
		if (args != null && args.length > 0) {
			for (int i=0; i<args.length; i++)
			{
				if (StringUtils.equalsIgnoreCase(args[i], key)) {
					if (i>=args.length) {
						throw new BatchException("参数格式错误,未指定参数值：-" + key);
					}
					return args[i+1];
				}
			}
		}
		return "";
	}

}
