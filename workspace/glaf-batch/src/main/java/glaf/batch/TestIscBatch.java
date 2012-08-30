package glaf.batch;

import java.util.HashMap;
import java.util.Map;

public class TestIscBatch extends DbBatch{

	@Override
	public void runBatch() {
		// TODO Auto-generated method stub
		logFile.info("开始runBatch;");
		
		//方式1：直接拼写sql
		this.dba.update("update t_arraygrid set PRICE = 2 where COMPANY = 'tr2'");
		
		//方式2：将sql文写入本batch对应的config文件
		//      （TestIscBatch.sql.xml，该文件必须以.sql.xml为文件名结尾，且放置在configfiles目录下）
		//       调用方法根据id获取出对应的sql文，然后可对sql文进行再加工，之后再检索
		String updateSql = this.dba.getSqlById("updateArrayGrid", null);
		updateSql = updateSql + "where COMPANY = 'tr1'";
		this.dba.update(updateSql);
		
		//方式3：将sql文写入config文件，并将sql文中变量参数用 :变量名 的方式进行标注
		//       在获取sql文时，将变量也一同传入，可获得完整的sql文。
		//       最后再进行相应的操作
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("COMPANY", "tr3");
		param.put("PRICE", 1);
		String selectSql = this.dba.getSqlById("selectArrayGrid", param);
		this.dba.find(selectSql);
		
		logFile.info("结束runBatch;");
		
	}

}
