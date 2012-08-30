package sysSrc.common.batch;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sysSrc.common.SysBaseCom;
import sysSrc.framework.SysBaseDTOMap;
import sysSrc.orm.TBatchconfig;
import sysSrc.orm.TBatchexelog;
import sysSrc.orm.TPrivilege;


import baseSrc.common.AutoArrayList;
import baseSrc.common.BaseUtility;
import baseSrc.common.LogHelper;
import baseSrc.framework.BaseConstants;
import baseSrc.framework.BaseDAO;
import baseSrc.framework.BusException;

public class ShowBatchExeLogDAO  extends BaseDAO {
	protected static LogHelper log = new LogHelper(ShowBatchExeLogDAO.class);
	
	public SysBaseDTOMap runPageLoad(ShowBatchExeLogForm form, SysBaseCom baseCom) {
		SysBaseDTOMap dtoMap = new SysBaseDTOMap();
		AutoArrayList detailList = new AutoArrayList(ShowBatchExeLogDetail.class);
		
		
		dtoMap.setDetailList(detailList);
		dtoMap.setForwardId("localGo");
		
		
		return dtoMap;
	}
	
	public SysBaseDTOMap runPageCtrl(ShowBatchExeLogForm form, SysBaseCom baseCom){

		//创建控制接口对象
		SysBaseDTOMap dtoMap = new SysBaseDTOMap();

		//取得每一页的数据对象
		List<?> list = getDataFormDB(form,baseCom);
		
		//定义画面中数据元素
		AutoArrayList detailList = new AutoArrayList(ShowBatchExeLogDetail.class);
		
		//根据取得数值设置画面元素值
		if(!BaseUtility.isListNull(list)){
			for(int i = 0;i<list.size();i++){
				TBatchexelog tabObj = (TBatchexelog)list.get(i);
				ShowBatchExeLogDetail bel = new ShowBatchExeLogDetail();
				bel.setNo(new Long(i) + 1 + (form.getPageNo()-1) * BaseConstants.ISC_PAGE_SIZE_FIVE);
				bel.setLogid(tabObj.getId());
				bel.setBatName(tabObj.getBatbusname());
				bel.setBatId(tabObj.getBatname());
				if(null != tabObj.getExeenddate()){
					bel.setExeEndDate(tabObj.getExeenddate().toString());
				}
				bel.setExeStartDate(tabObj.getExedate().toString());
				bel.setExeResult(tabObj.getExeresult());
				if(null != tabObj.getExeresultmemo()){
					bel.setExeResultMemo(tabObj.getExeresultmemo());
				}
				bel.setExeUser(tabObj.getExeuser());
				bel.setLogFile(tabObj.getLogfile());
				bel.setLogfilepath(tabObj.getLogfilepath());
				if(null != tabObj.getCheckerrordata()){
					bel.setCheckErrorData(tabObj.getCheckerrordata());
				}
				detailList.add(bel);
				}
		}
		
		//把设置好的值存放到画面对应FORMBEAN中
		dtoMap.setDetailList(detailList);
		form.setShowBatchExeLogDetail(detailList);
		dtoMap.setForwardId("localGo");
		//form.setGridData(JSONArray.fromObject(detailList).toString());
		return dtoMap;
	}
	
	private List<?> getDataFormDB(ShowBatchExeLogForm form,SysBaseCom baseCom){
		
		//定义检索表名

		String tabName = "TBatchexelog";
		
		//创建检索条件参数对象

		Map<String, Object> params = new HashMap<String, Object>();		
		
		//定义检索条件

		StringBuffer whereHql = new StringBuffer(" WHERE ");
		if(!BaseUtility.isStringNull(form.getF_batchid())){
			whereHql.append(" batname like :batname AND");
			params.put("batname", form.getF_batchid());
		}
		if(!BaseUtility.isStringNull(form.getF_batchExeStart())){
			whereHql.append("  to_char(exedate,'yyyy-mm-dd') >= :batchExeStart AND");
			params.put("batchExeStart", form.getF_batchExeStart());
		}
		if(!BaseUtility.isStringNull(form.getF_batchExeEnd())){
			whereHql.append("  to_char(exedate,'yyyy-mm-dd') <= :batchExeEnd AND");
			params.put("batchExeEnd", form.getF_batchExeEnd());
		}
		if(!BaseUtility.isStringNull(form.getF_batchExeUser())){
			whereHql.append(" exeuser like :batchExeUser AND");
			params.put("batchExeUser", form.getF_batchExeUser());
		}
		if(!BaseUtility.isStringNull(form.getF_batchBusName())){
			whereHql.append(" batbusname like :batchBusName AND");
			params.put("batchBusName", form.getF_batchBusName());
		}
		if(!BaseUtility.isStringNull(form.getF_batchExeResult()) && !form.getF_batchExeResult().equals("ALL")){
			whereHql.append(" exeresult like :batchExeResult and");
			params.put("batchExeResult", form.getF_batchExeResult());
		}
		
		List pris = baseCom.getRolePrivilege();
		List<String> batPris = new ArrayList<String>();
		for(int i=0;i<pris.size();i++){
			TPrivilege p = (TPrivilege)pris.get(i);
			if(p.getFPrivilegeid().contains("bat")){
				batPris.add(p.getFPrivilegename());
			}
		}
		if(null != batPris && batPris.size()>0){
			whereHql.append("  batname in (:batchCanSelected) ");
			params.put("batchCanSelected", batPris);
		}else{
			whereHql.append(" 1=1 ");
		}
		
		//取得当前检索条件的数据总条数
		
		int count = this.dbAccess.getResutlsTotal(tabName,whereHql.toString(),params);
		
		whereHql.append(" order by batname,exedate ");
		
		//取得每一页的显示条数
		int pageSize = BaseConstants.ISC_PAGE_SIZE_FIVE;
		
		//取得每一页的数据对象
		List<?> list = this.dbAccess.getResutlsForPage(tabName,whereHql.toString(),params,form,count,pageSize);
		return list;
	}
	
	public SysBaseDTOMap runReExeBatch(ShowBatchExeLogForm form, SysBaseCom baseCom){

		//创建控制接口对象
		SysBaseDTOMap dtoMap = new SysBaseDTOMap();
		
		String batName = form.getCurBatName();
		String batContext = form.getCurBatContext() + " -u " + baseCom.getUserName();
		TBatchconfig tce = (TBatchconfig)this.dbAccess.load(TBatchconfig.class, batName);
		File what = new File(tce.getWhat());
		String whatPath = what.getPath();
		if(what.isFile()){
			whatPath = what.getParent();
		}
		if(null == tce || null == tce.getLockflag() || tce.getLockflag().equals("1")){
			dtoMap.setMsgId("bat.canntReRun");
			dtoMap.setForwardId("localGo");
			throw new BusException(dtoMap);
		}else{
			try{
				WindowsTasksOper wto = new WindowsTasksOper();
				
				SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
				Date d = new Date();
				String now = sf.format(d);
				String batFileName = whatPath + "\\reExe_" + batName + "_" +  now + ".bat";
				batFileName = batFileName.replaceAll("\\\\", "\\/");
				File f = new File(batFileName);
				try {
					FileOutputStream fos = new FileOutputStream(f,true);
					String bw = "cd " + whatPath + " \r\n " + batContext;
					//String bw = batContext;
					
					fos.write(bw.getBytes());
					fos.close();
				} catch (FileNotFoundException e) {
					throw e;
				} catch (IOException e) {
					throw e;
				}
				wto.exeCmdNotWaitForResult(batFileName);
				//wto.exeCmdWaitForResult(batFileName);
				
				dtoMap = runPageCtrl(form,baseCom);
				
				dtoMap.setMsgId("bat.reRunOK");
				dtoMap.setForwardId("localGo");
			}catch(Exception e){
				dtoMap = runPageCtrl(form,baseCom);
				dtoMap.setMsgId("bat.reRunNG");
				dtoMap.addMsgArg(e.getMessage());
				dtoMap.setForwardId("localGo");
				throw new BusException(dtoMap);
			}
			
			dtoMap.setForwardId("localGo");
		}
		return dtoMap;
	}
	
}
