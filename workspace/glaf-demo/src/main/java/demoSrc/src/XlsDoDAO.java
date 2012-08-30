package demoSrc.src;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import sysSrc.common.SysBaseCom;
import sysSrc.framework.SysBaseDTOMap;
import sysSrc.orm.TUsers;

import baseSrc.common.ExcelOper;
import baseSrc.framework.BaseDAO;
 
public class XlsDoDAO extends BaseDAO{
	//将xls数据导入DB中
	public SysBaseDTOMap DaoRuXls(XlsDoForm form,SysBaseCom baseCom){
		SysBaseDTOMap ret = new SysBaseDTOMap();
		//将xls中数据导入map中
		ExcelOper xls =new ExcelOper(new File(form.getFileName()));
		HashMap<String, List> map = new HashMap<String, List>();
		map=xls.getExcelMapByCol(0, 0, "first");
		//将map中数据插入到DB中
		int i;
		for(i=1;i<=map.size();i++){
			TUsers tbUsers=new TUsers();
			List l=new ArrayList();
			l=map.get(String.valueOf(i));
			tbUsers.setFUserid(l.get(0).toString());
			tbUsers.setFName(l.get(1).toString());
			tbUsers.setFPassword(l.get(2).toString());
			dbAccess.saveOrUpdate(tbUsers);
		}
		ret.setForwardId("xlsDo");
		return ret;
	}
	/**
	 * 测试根据模板生成导出文件
	 */
	public SysBaseDTOMap DaoChuXls(XlsDoForm form,SysBaseCom baseCom) {
		SysBaseDTOMap ret = new SysBaseDTOMap();
		XLSTransformer transformer = new XLSTransformer();
		Map<String, Object> data = new HashMap<String, Object>();
		//定义对象
		List<TUsers> exList = new ArrayList<TUsers>();
		
		TUsers TUsers = new TUsers();
		//将数据赋给对象属性
		TUsers.setFUserid("a");
		TUsers.setFName("XXX");
		TUsers.setFPassword("1234567");
		exList.add(TUsers);
		
		//TUsers TUsers1 = new TUsers();
		TUsers.setFUserid("b");
		TUsers.setFName("yyyy");
		TUsers.setFPassword("1234567");
		exList.add(TUsers);
		
		data.put("exList", exList);
		try {
			//将模板转换为xls数据
			//transformer.transformXLS("d:\\user.xls", data, form.getFileName());
			ret.setForwardId("xlsDo");
			return ret;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	 
}
