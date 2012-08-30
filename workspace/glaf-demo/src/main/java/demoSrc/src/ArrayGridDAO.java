package demoSrc.src;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sysSrc.common.SysBaseCom;
import sysSrc.framework.SysBaseDTOMap;

import net.sf.json.JSONArray;

import demoSrc.orm.TArraygrid;

import baseSrc.common.AutoArrayList;
import baseSrc.common.BaseUtility;
import baseSrc.common.LogHelper;
import baseSrc.framework.BaseConstants;
import baseSrc.framework.BaseDAO;

public class ArrayGridDAO  extends BaseDAO {
	protected static LogHelper log = new LogHelper(ArrayGridDAO.class);
	
	public SysBaseDTOMap runPageLoad(ArrayGridForm form, SysBaseCom baseCom) {
		SysBaseDTOMap dtoMap = new SysBaseDTOMap();
		AutoArrayList detailList = new AutoArrayList(ArrayGridDetail.class);
		
		
		ArrayGridDetail agDTO = new ArrayGridDetail();
		
		agDTO.setCompany("c1");
		agDTO.setPrice(12);
		agDTO.setChange(13);
		agDTO.setPctChange(14);
		agDTO.setLastChange("9/1 12:00am");
		detailList.add(agDTO);
		
		ArrayGridDetail agDTO1 = new ArrayGridDetail();
		agDTO1.setCompany("tr test");
		agDTO1.setPrice(10);
		agDTO1.setChange(11);
		agDTO1.setPctChange(12);
		agDTO1.setLastChange("9/12 12:00am");
		detailList.add(agDTO1);
		
		dtoMap.setDetailList(detailList);
		dtoMap.setForwardId("indexGo");
		
		
		return dtoMap;
	}
	
	public SysBaseDTOMap runPageCtrl(ArrayGridForm form, SysBaseCom baseCom){

		//创建控制接口对象
		SysBaseDTOMap dtoMap = new SysBaseDTOMap();

		//取得每一页的数据对象
		List<?> list = getDataFormDB(form,baseCom);
		
		//定义画面中数据元素
		AutoArrayList detailList = new AutoArrayList(ArrayGridDetail.class);
		
		//根据取得数值设置画面元素值
		if(!BaseUtility.isListNull(list)){
			for(int i = 0;i<list.size();i++){
				TArraygrid tabObj = (TArraygrid)list.get(i);
				ArrayGridDetail detail = new ArrayGridDetail();
				detail.setCompany(tabObj.getCompany());
				detail.setPrice(tabObj.getPrice());
				detail.setChange(tabObj.getChange());
				detail.setPctChange(tabObj.getChangeper());
				detail.setLastChange(tabObj.getLaseupdated().toString());
				detailList.add(detail);
				}
		}
		
		//把设置好的值存放到画面对应FORMBEAN中
		dtoMap.setDetailList(detailList);
		
		dtoMap.setForwardId("indexGo");
		form.setGridData(JSONArray.fromObject(detailList).toString());
		return dtoMap;
	}
	
	private List<?> getDataFormDB(ArrayGridForm form,SysBaseCom baseCom){
		
		//定义检索表名

		String tabName = "TArraygrid";
		
		//创建检索条件参数对象

		Map<String, Object> params = new HashMap<String, Object>();		
		
		//定义检索条件

		StringBuffer whereHql = new StringBuffer(" WHERE ");
		if(!BaseUtility.isStringNull(form.getS_Name())){
			whereHql.append("company like :company AND");
			params.put("company", form.getS_Name());
		}
		whereHql.append(" 1=1");
		
		//取得当前检索条件的数据总条数
		
		int count = this.dbAccess.getResutlsTotal(tabName,whereHql.toString(),params);
		
		//取得每一页的显示条数
		int pageSize = BaseConstants.ISC_PAGE_SIZE_FIVE;
		
		//取得每一页的数据对象
		List<?> list = this.dbAccess.getResutlsForPage(tabName,whereHql.toString(),params,form,count,pageSize);
		return list;
	}
	
}
