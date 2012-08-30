package demoSrc.src;

import java.util.List;

import sysSrc.common.SysBaseCom;
import sysSrc.framework.SysBaseDTOMap;

import demoSrc.orm.TCellediting;
import demoSrc.orm.TCelleditingId;

import baseSrc.common.BaseCom;
import baseSrc.common.LogHelper;
import baseSrc.framework.BaseDAO;
import baseSrc.framework.BusException;
import baseSrc.framework.SelectCommonDTO;

public class CellEditingDAO   extends BaseDAO{
	protected static LogHelper log = new LogHelper(CellEditingDAO.class);
	
	public SysBaseDTOMap runPageLoad(CellEditingForm form, SysBaseCom baseCom) {
		SysBaseDTOMap dtoMap = new SysBaseDTOMap();
		
		//设置下拉框的值
		String[] values = {"1","2","3"};
		String[] labels = {"test1","test2","test3"};
		SelectCommonDTO sc = new SelectCommonDTO();
		sc.setLabels(labels);
		sc.setValues(values);
		dtoMap.setLightDTO(sc);
		
		dtoMap.setForwardId("indexGo");
		return dtoMap;
	}
	
	public SysBaseDTOMap runSave(CellEditingForm form, SysBaseCom baseCom){

		//创建控制接口对象
		SysBaseDTOMap dtoMap = new SysBaseDTOMap();
		List<?> l = form.getGridList(CellEditingDetail.class);
		
		try{
			for(int i=0;i<l.size();i++){
				CellEditingDetail ced = (CellEditingDetail)l.get(i);
				TCellediting tce = new TCellediting();
				TCelleditingId tceid = new TCelleditingId();
				tceid.setSelect1(ced.getSelect());
				tceid.setPrice(ced.getPrice());
				tce.setId(tceid);
				tce.setAvaildate(ced.getAvailDate());
				tce.setCommon(ced.getCommon());
				tce.setLight(ced.getLight());	
				this.dbAccess.saveOrUpdate(tce);
			}
			//设置下拉框的值
			String[] values = {"1","2","3"};
			String[] labels = {"test1","test2","test3"};
			SelectCommonDTO sc = new SelectCommonDTO();
			sc.setLabels(labels);
			sc.setValues(values);
			dtoMap.setLightDTO(sc);
			//保存成功，则提示操作成功
			dtoMap.setMsgId("info.saveOK");
			dtoMap.setForwardId("indexGo");
		}catch(Exception e){
			//设置下拉框的值
			String[] values = {"1","2","3"};
			String[] labels = {"test1","test2","test3"};
			SelectCommonDTO sc = new SelectCommonDTO();
			sc.setLabels(labels);
			sc.setValues(values);
			dtoMap.setLightDTO(sc);
			//保存异常时，提示异常消息
			dtoMap.setMsgId("info.saveNG");
			dtoMap.setForwardId("indexGo");
			log.error(baseCom, e.getMessage());
			//抛出异常：BusException
			throw new BusException(dtoMap);
		}
		
		
		return dtoMap;
	}


}
