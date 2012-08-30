package sysSrc.common.changeLanguage;

import sysSrc.common.SysBaseCom;
import sysSrc.framework.SysBaseDTOMap;
import baseSrc.framework.BaseDAO;

public class FunTopDAO extends BaseDAO {

	public SysBaseDTOMap runPageLoad(FunTopForm form,SysBaseCom baseCom){
		
		SysBaseDTOMap ret = new SysBaseDTOMap();		
		
		ret.setForwardId("indexGo");			
		
		return ret;
	}
	
	public SysBaseDTOMap runChangeLanguage(FunTopForm form,SysBaseCom baseCom){
		
		SysBaseDTOMap ret = new SysBaseDTOMap();
		
		ret.setForwardId("indexGo");
		return ret;
	}
	


}
