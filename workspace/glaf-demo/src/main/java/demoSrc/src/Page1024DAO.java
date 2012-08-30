package demoSrc.src;

import baseSrc.framework.BaseDAO;

import java.text.SimpleDateFormat;

import sysSrc.common.SysBaseCom;
import sysSrc.framework.SysBaseDTOMap;

import demoSrc.orm.TTestL;

public class Page1024DAO extends BaseDAO {

	public SysBaseDTOMap runPageLoad(Page1024Form form,SysBaseCom baseCom) throws Exception {

		SysBaseDTOMap ret = new SysBaseDTOMap();
		TTestL testData = new TTestL();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-DD hh:mm:ss");
		String strCdate = simpleDateFormat.format(System.currentTimeMillis());
		testData.setFWritetime(simpleDateFormat.parse(strCdate));
		form.setWriteTime("2009-03-27");
		form.setDestineQty(0.0);
		ret.setForwardId("go1024");

		return ret;
	}
	
	public SysBaseDTOMap submitApply(Page1024Form form,SysBaseCom baseCom) {

		SysBaseDTOMap ret = new SysBaseDTOMap();
		form.setWriteTime("2009-03-27");
		form.setStuffApplyNo("A10000");
		form.setDepName("製造部");
		form.setUserName("担当1(submit)");
		form.setUseDeptSubmitDate("2009-03-26");
		ret.setForwardId("go1024");
		
		return ret;
		
		
		
		
		
	}
}
