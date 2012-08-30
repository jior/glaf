package demoSrc.src;

import sysSrc.common.SysBaseCom;
import sysSrc.framework.SysBaseDTOMap;
import baseSrc.framework.BaseConstants;
import baseSrc.framework.BaseDAO;

public class ReportShowDAO extends BaseDAO {

	public SysBaseDTOMap runPageLoad(ReportShowForm form, SysBaseCom baseCom) {

		SysBaseDTOMap dtoMap = new SysBaseDTOMap();
	
		dtoMap.setReportTpl("/demoConfigfiles/reportTpl/ReportTestPcrt1.prpt");
		dtoMap.setReportType(BaseConstants.ReportType.PDF);
		dtoMap.setReportQuery("SHOWTEXT","SELECT A.cid AS NO, A.payableyears AS CLNY, A.suppno AS GYS, A.productno AS PF, A.productname AS PM, A.sumpricedis AS XSJE, A.sumprice AS JZJE  FROM t_gridpay A ORDER BY A.suppno ");
		return dtoMap;
	}
	
}
