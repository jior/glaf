package demoSrc.src;
import sysSrc.common.SysBaseCom;
import baseSrc.framework.BaseDAO;
import baseSrc.framework.BaseDTOMap;

public class UploadDownDAO extends BaseDAO {
	public static final String XGSTATS = "1";
	/**
	 * 初始化页面
	 * 
	 * @param form
	 * @param baseCom
	 * @return
	 */
	public BaseDTOMap runPageLoad(UploadDownForm form, SysBaseCom baseCom) {

		BaseDTOMap dtoMap = new BaseDTOMap();
		
		dtoMap.setForwardId("uploadDownGo");
		return dtoMap;
	}

}
