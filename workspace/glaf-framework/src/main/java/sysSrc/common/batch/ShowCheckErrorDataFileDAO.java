package sysSrc.common.batch;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sysSrc.common.SysBaseCom;
import sysSrc.framework.SysBaseDTOMap;
import sysSrc.orm.TBatchcheckerrordada;
import sysSrc.orm.TBatchiofile;


import baseSrc.common.AutoArrayList;
import baseSrc.common.BaseUtility;
import baseSrc.common.LogHelper;
import baseSrc.framework.BaseDAO;

public class ShowCheckErrorDataFileDAO  extends BaseDAO {
	protected static LogHelper log = new LogHelper(ShowCheckErrorDataFileDAO.class);
	
	public SysBaseDTOMap runPageLoad(ShowCheckErrorDataFileForm form, SysBaseCom baseCom) {
		//创建控制接口对象
		SysBaseDTOMap dtoMap = new SysBaseDTOMap();

		//取得每一页的数据对象
		List<?> list = getDataFormDB(form,baseCom);
		
		//定义画面中数据元素
		AutoArrayList detailList = new AutoArrayList(ShowCheckErrorDataFileDetail.class);
		
		//根据取得数值设置画面元素值
		if(!BaseUtility.isListNull(list)){
			for(int i = 0;i<list.size();i++){
				TBatchcheckerrordada tabObj = (TBatchcheckerrordada)list.get(i);
				ShowCheckErrorDataFileDetail bel = new ShowCheckErrorDataFileDetail();
				bel.setNo(String.valueOf(i+1));
				if(null != tabObj.getId().getFilename()){
					bel.setFileName(tabObj.getId().getFilename());
				}
				if(null != tabObj.getId().getFilepath()){
					bel.setFilePath(tabObj.getId().getFilepath());
				}
				detailList.add(bel);
			}
		}
		
		//把设置好的值存放到画面对应FORMBEAN中
		dtoMap.setDetailList(detailList);
		form.setShowCheckErrorDataFileDetail(detailList);
		dtoMap.setForwardId("localGo");
		return dtoMap;
	}
	
	public SysBaseDTOMap runPageCtrl(ShowCheckErrorDataFileForm form, SysBaseCom baseCom) {
		return runPageLoad(form,baseCom);
	}
	
	private List<?> getDataFormDB(ShowCheckErrorDataFileForm form,SysBaseCom baseCom){
		
		//定义检索表名

		String tabName = "TBatchcheckerrordada";
		
		//创建检索条件参数对象

		Map<String, Object> params = new HashMap<String, Object>();		
		
		//定义检索条件

		StringBuffer whereHql = new StringBuffer(" WHERE id.id = " + form.getLogid() + " ");
		whereHql.append(" order by id.filename ");
		
		List<?> list =  this.dbAccess.find(tabName,whereHql.toString(),params);
		
		return list;
	}
	
	
}
