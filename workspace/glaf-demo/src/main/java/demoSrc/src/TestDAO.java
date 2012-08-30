package demoSrc.src;



import sysSrc.common.SysBaseCom;
import sysSrc.framework.SysBaseDTOMap;
import baseSrc.framework.BaseDAO;

public class TestDAO extends BaseDAO {

	public SysBaseDTOMap run(TestActionForm form,SysBaseCom baseCom){

		SysBaseDTOMap ret = new SysBaseDTOMap();
		/**
		TSpringUser tSpringUser = 
			(TSpringUser)this.dbAccess.load(TSpringUser.class, "yeah1");
		
		tSpringUser.setFlgUsing("2");
		this.dbAccess.saveOrUpdate(tSpringUser);		
		
		
		ret.setDefaultDTO(tSpringUser.getName());
		*/
		ret.setForwardId("login");
		return ret;
	}

}
