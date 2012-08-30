package demoSrc.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sysSrc.common.SysBaseCom;
import sysSrc.framework.SysBaseDTOMap;

/*使用LOAD方法来判断数据是否存在时候需要的引用
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
*/
import demoSrc.orm.TBasesample;

import baseSrc.common.AutoArrayList;
import baseSrc.common.BaseCom;
import baseSrc.common.BaseUtility;
import baseSrc.framework.BaseConstants;
import baseSrc.framework.BaseDAO;
import baseSrc.framework.BaseDTOMap;
import baseSrc.framework.CombCommonBean;

public class BaseSampleDAO extends BaseDAO{

	public SysBaseDTOMap runPageLoad(BaseSampleForm form,SysBaseCom baseCom){
		
		SysBaseDTOMap dtoMap = new SysBaseDTOMap();	
		
		//测试从sysBaseAactionForm中获取数据
		String t = form.getSysTestParm();
		//测试从SysBaseCom中获取数据是否正常
		String a = baseCom.getSysTestComParm();

		//测试下拉框
		List combTest = new ArrayList();
		CombCommonBean combBean1 = new CombCommonBean("a1","01");
		CombCommonBean combBean2 = new CombCommonBean("a2","02");
		CombCommonBean combBean3 = new CombCommonBean("a3","03");
		combTest.add(combBean1);
		combTest.add(combBean2);
		combTest.add(combBean3);
		form.setCombTestCollection(combTest);
		
		//测试共通处理类
		CommonDao commonDao = new CommonDao(this.dbAccess);
		String test = commonDao.getTBpConstantValueByCode();
		
		//设定DTO
		setDto(dtoMap);
		return dtoMap;
	}
	
	public SysBaseDTOMap runPageCtrl(BaseSampleForm form,SysBaseCom baseCom){
		
		
		//创建控制接口对象
		SysBaseDTOMap dtoMap = new SysBaseDTOMap();
		
		//测试原生sql的exeSQL方法
		String sql = "update t_iscsample set AGE = '0'";
		Map<String, Object> params = new HashMap<String, Object>();		
		this.dbAccess.exeSQL(sql, params);
		
		//取得每一页的数据对象
		List<?> list = new ArrayList();
		if(form.getSearchFlag().equals("search")){
			list = getDataFormDB(form,baseCom);
			//把取得的数据放到画面对应FORMBEAN中
			setActionForm(list,form);
		}
		//测试检索原生sql且分页
		if(form.getSearchFlag().equals("sqlsearchFY")){
			list = getDataFormDBBySqlFY(form,baseCom);
			setActionFormSql(list,form);
		}
		//测试检索原生sql且不分页
		if(form.getSearchFlag().equals("sqlsearch")){
			list = getDataFormDBBySql(form,baseCom);
			setActionFormSql(list,form);
		}
		

		//设定DTO
		setDto(dtoMap);
		//测试下拉框
		List combTest = new ArrayList();
		CombCommonBean combBean1 = new CombCommonBean("a1","01");
		CombCommonBean combBean2 = new CombCommonBean("a2","02");
		CombCommonBean combBean3 = new CombCommonBean("a3","03");
		combTest.add(combBean1);
		combTest.add(combBean2);
		combTest.add(combBean3);
		form.setCombTestCollection(combTest);
		
		//测试共通处理类
		CommonDao commonDao = new CommonDao(this.dbAccess);
		String test = commonDao.getTBpConstantValueByCode();
		return dtoMap;
	}
	
	public SysBaseDTOMap runPageBaseSampleSave(BaseSampleForm form,SysBaseCom baseCom){

		//创建控制接口对象
		SysBaseDTOMap dtoMap = new SysBaseDTOMap();

		//保存之前进行数据数据检查，可行则执行保存，不可行则返回页面
		if(checkInput(form,dtoMap)){
			//保存数据到DB
			if(saveDataToDB(form,dtoMap)){
			//取得每一页的数据对象
			List<?> list = getDataFormDB(form,baseCom);
			//把取得的数据放到画面对应FORMBEAN中
			setActionForm(list,form);
			}
		}else{
			//取得每一页的数据对象
			List<?> list = getDataFormDB(form,baseCom);
			//把取得和提交上来的数据进行整合，然后放到画面对应FORMBEAN中
			setActionFormForCheck(list,form,dtoMap);
		}
		//设定DTO
		setDto(dtoMap);
		//测试下拉框
		List combTest = new ArrayList();
		CombCommonBean combBean1 = new CombCommonBean("a1","01");
		CombCommonBean combBean2 = new CombCommonBean("a2","02");
		CombCommonBean combBean3 = new CombCommonBean("a3","03");
		combTest.add(combBean1);
		combTest.add(combBean2);
		combTest.add(combBean3);
		form.setCombTestCollection(combTest);
		
		//测试共通处理类
		CommonDao commonDao = new CommonDao(this.dbAccess);
		String test = commonDao.getTBpConstantValueByCode();
		return dtoMap;
	}

	public SysBaseDTOMap runPageBaseSampleDelete(BaseSampleForm form,SysBaseCom baseCom){

		//创建控制接口对象
		SysBaseDTOMap dtoMap = new SysBaseDTOMap();

		//删除DB中的数据
		deleteDataFromDB(form,dtoMap);
		
		//取得删除后当页的数据对象
		List<?> list = getDataFormDB(form,baseCom);

		//把取得的数据放到画面对应FORMBEAN中
		setActionForm(list,form);

		//设定DTO
		setDto(dtoMap);
		//测试下拉框
		List combTest = new ArrayList();
		CombCommonBean combBean1 = new CombCommonBean("a1","01");
		CombCommonBean combBean2 = new CombCommonBean("a2","02");
		CombCommonBean combBean3 = new CombCommonBean("a3","03");
		combTest.add(combBean1);
		combTest.add(combBean2);
		combTest.add(combBean3);
		form.setCombTestCollection(combTest);
		
		//测试共通处理类
		CommonDao commonDao = new CommonDao(this.dbAccess);
		String test = commonDao.getTBpConstantValueByCode();
		return dtoMap;
	}
	
	public SysBaseDTOMap runPageBaseSampleCreate(BaseSampleForm form,SysBaseCom baseCom){

		//创建控制接口对象
		SysBaseDTOMap dtoMap = new SysBaseDTOMap();

		//向DB中插入数据
		insertDataToDB(form);
		
		//取得插入后的数据对象
		List<?> list = getDataFormDB(form,baseCom);

		//把取得的数据放到画面对应FORMBEAN中
		setActionForm(list,form);

		//设定DTO
		setDto(dtoMap);
		//测试下拉框
		List combTest = new ArrayList();
		CombCommonBean combBean1 = new CombCommonBean("a1","01");
		CombCommonBean combBean2 = new CombCommonBean("a2","02");
		CombCommonBean combBean3 = new CombCommonBean("a3","03");
		combTest.add(combBean1);
		combTest.add(combBean2);
		combTest.add(combBean3);
		form.setCombTestCollection(combTest);
		
		//测试共通处理类
		CommonDao commonDao = new CommonDao(this.dbAccess);
		String test = commonDao.getTBpConstantValueByCode();
		return dtoMap;
	}
	
	private void insertDataToDB(BaseSampleForm form) {
		
		//创建映射表对象
		TBasesample tIscsample = new TBasesample();
		
		//把画面中的值设定到映射表对象
		tIscsample.setSysno(Integer.valueOf(form.getS_Sysno()));
		tIscsample.setName(form.getS_Name());
		tIscsample.setSex(form.getS_Sex());
		tIscsample.setAge(form.getS_Age());
		tIscsample.setCity(form.getS_City());
		tIscsample.setDeadyear(form.getS_DeadYear());
		
		//向DB中保存数据
		this.dbAccess.saveOrUpdate(tIscsample);
	}

	private void deleteDataFromDB(BaseSampleForm form, SysBaseDTOMap dtoMap) {
		
		//定义画面中数据元素
		AutoArrayList detailList= form.getBaseSampleDetails();
		
		//没有选中标志
		boolean flgSelected = false;
		
		for(int i = 0;i<detailList.size();i++){
			
			BaseSampleDetails detail = (BaseSampleDetails)detailList.get(i);
			if(BaseConstants.ISC_CHECKBOX_CHECKED.equals(detail.getChk())){
				
				//设置选中标志
				flgSelected = true;
				//定义删除表名
				String tabName = "TBasesample";
				//创建删除条件参数对象
				Map<String, Object> params = new HashMap<String, Object>();		
				//定义删除条件
				StringBuffer whereHql = new StringBuffer(" WHERE ");
				whereHql.append(" sysno=:sysno");
				params.put("sysno", detail.getSysno());
				//删除当前检索条件的数据
				this.dbAccess.delete(tabName,whereHql.toString(),params);
			}
		}
		if(!flgSelected){
			dtoMap.setMsgId("baseSample.selected");
		}
	}

	private boolean saveDataToDB(BaseSampleForm form,SysBaseDTOMap dtoMap) {
		boolean ret = true;
		//定义画面中数据元素
		AutoArrayList detailList= form.getBaseSampleDetails();
		for(int i = 0;i<detailList.size();i++){
			BaseSampleDetails detail = (BaseSampleDetails)detailList.get(i);
			if(BaseConstants.ISC_CHECKBOX_CHECKED.equals(detail.getChk())){
				
				/*使用LOAD方法来判断数据是否存在的例子
				 *使用请把下面14行注释掉（含注释行和代码行）
				try{
					TIscsample tIscsample = (TIscsample)this.dbAccess.load(TIscsample.class,detail.getSysno());
					tIscsample.setSex(BaseConstants.ISC_CHECKBOX_CHECKED.equals(detail.getSex())?"1":"2");
					tIscsample.setAge(detail.getAge());
					tIscsample.setCity(detail.getCity());
					//该条数据保存到DB
					this.dbAccess.saveOrUpdate(tIscsample);
				}
				catch(HibernateObjectRetrievalFailureException ex){
					dtoMap.setMsgId("baseSample.dataErr");
					dtoMap.addErrObjId("iscSampleDetails[" + i + "].sysno");
					ret = false;
				}
				*/
				
				//定义更新表名
				String tabName = "TBasesample";
				//创建更新值参数对象
				Map<String, Object> datas = new HashMap<String, Object>();
				datas.put("sex", BaseConstants.ISC_CHECKBOX_CHECKED.equals(detail.getSex())?"1":"2");
				datas.put("age", detail.getAge());
				datas.put("city", detail.getCity());

				//创建更新检索条件参数对象
				Map<String, Object> params = new HashMap<String, Object>();
				//定义检索条件
				StringBuffer whereHql = new StringBuffer("WHERE sysno=:sysno");
				params.put("sysno", Integer.valueOf(detail.getSysno()));
				this.dbAccess.update(tabName, datas, whereHql.toString(), params);
			}
		}
		return ret;
	}

	private boolean checkInput(BaseSampleForm form,SysBaseDTOMap dtoMap){
		
		//设置检查返回值：可行
		boolean ret = true;
		//定义画面中数据元素
		AutoArrayList detailList = form.getBaseSampleDetails();
		for(int i = 0;i<detailList.size();i++){
			BaseSampleDetails detail = (BaseSampleDetails)detailList.get(i);
			if(BaseConstants.ISC_CHECKBOX_CHECKED.equals(detail.getChk())){
				//输入内容检查：是男人就只能在成都
				if(BaseConstants.ISC_CHECKBOX_CHECKED.equals(detail.getSex()) && !"028".equals(detail.getCity())){
					//设置画面中要弹出的消息内容
					dtoMap.setMsgId("baseSample.man");
					//设置画面中要标红的控件
					dtoMap.addErrObjId("baseSampleDetails[" + i + "].city");
					dtoMap.addErrObjId("baseSampleDetails[" + i + "].sex");
					//返回值设为不可行
					ret = false;
					//跳出
					break;
				}
			}
		}
		return ret;
	}
	
	private List<?> getDataFormDB(BaseSampleForm form,SysBaseCom baseCom){
		
		//定义检索表名
		String tabName = "TBasesample";
		
		//创建检索条件参数对象
		Map<String, Object> params = new HashMap<String, Object>();		
		
		//定义检索条件
		StringBuffer whereHql = new StringBuffer(" WHERE ");
		if(!BaseUtility.isStringNull(form.getS_Sysno())){
			whereHql.append("sysno=:sysno AND");
			params.put("sysno", Integer.valueOf(form.getS_Sysno()));
		}
		if(!BaseUtility.isStringNull(form.getS_Name())){
			whereHql.append(" name LIKE :name AND");
			params.put("name", "%"+form.getS_Name()+"%");
		}
		if(!BaseUtility.isStringNull(form.getS_Sex())){
			whereHql.append(" sex=:sex AND");
			params.put("sex", form.getS_Sex());
		}
		if(!BaseUtility.isStringNull(form.getS_Age())){
			whereHql.append(" age=:age AND");
			params.put("age", (form.getS_Age()+ "  ").substring(0,2));
		}
		if(!BaseUtility.isStringNull(form.getS_Money())){
			whereHql.append(" money=:money AND");
			params.put("money", (form.getS_Money()+ "  ").substring(0,2));
		}
		if(!"000".equals(form.getS_City())){
			whereHql.append(" city=:city AND");
			params.put("city", form.getS_City());
		}
		if(!BaseUtility.isStringNull(form.getS_DeadYear())){
			whereHql.append(" deadyear LIKE :deadyear AND");
			params.put("deadyear", "%"+form.getS_DeadYear()+"%");
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
	
	private void setActionForm(List<?> list,BaseSampleForm form){
		
		//定义画面中数据元素
		AutoArrayList detailList = new AutoArrayList(BaseSampleDetails.class);
		
		//根据取得数值设置画面元素值
		if(!BaseUtility.isListNull(list)){
			for(int i = 0;i<list.size();i++){
				TBasesample tabObj = (TBasesample)list.get(i);
				BaseSampleDetails detail = new BaseSampleDetails();
				detail.setSysno(tabObj.getSysno());
				detail.setName(tabObj.getName());
				detail.setSex("1".equals(tabObj.getSex())?BaseConstants.ISC_CHECKBOX_CHECKED:null);
				detail.setAge(tabObj.getAge());
				detail.setCity(tabObj.getCity());
				detail.setDeadyear(tabObj.getDeadyear());
				detailList.add(detail);
				}
		}
		
		//把设置好的值存放到画面对应FORMBEAN中
		form.setBaseSampleDetails(detailList);
	}

	private void setActionFormForCheck(List<?> list,BaseSampleForm form,SysBaseDTOMap dtoMap){
		
		//把Form中的数据设定回画面
		AutoArrayList detailList = form.getBaseSampleDetails();
		
		//根据取得数值设置画面元素值，只设定画面无法提交后台的
		if(!BaseUtility.isListNull(list)){
			for(int i = 0;i<list.size();i++){
				TBasesample tabObj = (TBasesample)list.get(i);
				BaseSampleDetails detail = (BaseSampleDetails)detailList.get(i);
				detail.setName(tabObj.getName());
				detail.setDeadyear(tabObj.getDeadyear());
			}
		}
	}
	
	private void setDto(SysBaseDTOMap dtoMap){
		//设定画面下拉框
		BaseSampleDTO iscSampleDTO = new BaseSampleDTO();
		String[] values={"000","010","023","028","099"};
		String[] labels={" ","北京","重庆","成都","海外"};
		iscSampleDTO.setValues(values);
		iscSampleDTO.setLabels(labels);
		dtoMap.setDefaultDTO(iscSampleDTO);
		//设定画面跳转
		dtoMap.setForwardId("baseSampleGo");			

	}
	
	private List<?> getDataFormDBBySql(BaseSampleForm form,SysBaseCom baseCom){
		
		//定义检索表名

		String sql = "select SYSNO,NAME,SEX,AGE,CITY,DEADYEAR,MONEY from t_iscsample  ";
		
		//创建检索条件参数对象

		Map<String, Object> params = new HashMap<String, Object>();		
		
		//定义检索条件

		StringBuffer whereHql = new StringBuffer(" WHERE ");
		if(!BaseUtility.isStringNull(form.getS_Sysno())){
			whereHql.append("SYSNO=:sysno AND");
			params.put("sysno", Integer.valueOf(form.getS_Sysno()));
		}
		if(!BaseUtility.isStringNull(form.getS_Name())){
			whereHql.append(" NAME LIKE :name AND");
			params.put("name", "%"+form.getS_Name()+"%");
		}
		if(!BaseUtility.isStringNull(form.getS_Sex())){
			whereHql.append(" SEX=:sex AND");
			params.put("sex", form.getS_Sex());
		}
		if(!BaseUtility.isStringNull(form.getS_Age())){
			whereHql.append(" AGE=:age AND");
			params.put("age", (form.getS_Age()+ "  ").substring(0,2));
		}
		if(!BaseUtility.isStringNull(form.getS_Money())){
			whereHql.append(" MONEY=:money AND");
			params.put("money", (form.getS_Money()+ "  ").substring(0,2));
		}
		if(!"000".equals(form.getS_City())){
			whereHql.append(" CITY=:city AND");
			params.put("city", form.getS_City());
		}
		if(!BaseUtility.isStringNull(form.getS_DeadYear())){
			whereHql.append(" DEADYEAR LIKE :deadyear AND");
			params.put("deadyear", "%"+form.getS_DeadYear()+"%");
		}
		whereHql.append(" 1=1");
		
		sql = sql + whereHql.toString();
		
		//取得数据对象
		List<?> list = this.dbAccess.findSQL(sql,params);
		return list;
	}
	
	private List<?> getDataFormDBBySqlFY(BaseSampleForm form,SysBaseCom baseCom){
		
		//定义检索表名

		String sql = "select SYSNO,NAME,SEX,AGE,CITY,DEADYEAR,MONEY from t_iscsample  ";
		
		//创建检索条件参数对象

		Map<String, Object> params = new HashMap<String, Object>();		
		
		//定义检索条件

		StringBuffer whereHql = new StringBuffer(" WHERE ");
		if(!BaseUtility.isStringNull(form.getS_Sysno())){
			whereHql.append("SYSNO=:sysno AND");
			params.put("sysno", Integer.valueOf(form.getS_Sysno()));
		}
		if(!BaseUtility.isStringNull(form.getS_Name())){
			whereHql.append(" NAME LIKE :name AND");
			params.put("name", "%"+form.getS_Name()+"%");
		}
		if(!BaseUtility.isStringNull(form.getS_Sex())){
			whereHql.append(" SEX=:sex AND");
			params.put("sex", form.getS_Sex());
		}
		if(!BaseUtility.isStringNull(form.getS_Age())){
			whereHql.append(" AGE=:age AND");
			params.put("age", (form.getS_Age()+ "  ").substring(0,2));
		}
		if(!BaseUtility.isStringNull(form.getS_Money())){
			whereHql.append(" MONEY=:money AND");
			params.put("money", (form.getS_Money()+ "  ").substring(0,2));
		}
		if(!"000".equals(form.getS_City())){
			whereHql.append(" CITY=:city AND");
			params.put("city", form.getS_City());
		}
		if(!BaseUtility.isStringNull(form.getS_DeadYear())){
			whereHql.append(" DEADYEAR LIKE :deadyear AND");
			params.put("deadyear", "%"+form.getS_DeadYear()+"%");
		}
		whereHql.append(" 1=1");
		
		sql = sql + whereHql.toString();
		
		//取得当前检索条件的数据总条数

		int count = this.dbAccess.getResutlsTotalSQL(sql,params);
		
		//取得每一页的显示条数
		int pageSize = BaseConstants.ISC_PAGE_SIZE_FIVE;
		
		//取得每一页的数据对象
		List<?> list = this.dbAccess.getResutlsForPageSQL(sql,params,form,count,pageSize);
		return list;
	}
	
	private void setActionFormSql(List<?> list,BaseSampleForm form){
		
		//定义画面中数据元素

		AutoArrayList detailList = new AutoArrayList(BaseSampleDetails.class);
		
		//根据取得数值设置画面元素值

		if(!BaseUtility.isListNull(list)){
			for(int i = 0;i<list.size();i++){
				Object[] tabObj = (Object[])list.get(i);
				BaseSampleDetails detail = new BaseSampleDetails();
				detail.setSysno(Integer.parseInt(tabObj[0].toString()));
				detail.setName(tabObj[1].toString());
				detail.setSex("1".equals(tabObj[2].toString())?BaseConstants.ISC_CHECKBOX_CHECKED:null);
				detail.setAge(tabObj[3].toString());
				detail.setCity(tabObj[4].toString());
				detail.setDeadyear(tabObj[5].toString());
				detailList.add(detail);
				}
		}
		
		//把设置好的值存放到画面对应FORMBEAN中

		form.setBaseSampleDetails(detailList);
	}
	
	public SysBaseDTOMap runDownload(BaseSampleForm form,SysBaseCom baseCom){
		SysBaseDTOMap dtoMap = new SysBaseDTOMap();
		
		//测试下拉框
		List combTest = new ArrayList();
		CombCommonBean combBean1 = new CombCommonBean("a1","01");
		CombCommonBean combBean2 = new CombCommonBean("a2","02");
		CombCommonBean combBean3 = new CombCommonBean("a3","03");
		combTest.add(combBean1);
		combTest.add(combBean2);
		combTest.add(combBean3);
		form.setCombTestCollection(combTest);
		
		dtoMap.setFile("d:/测试test.xls");
		dtoMap.setFileContentType("xls");
		return dtoMap;
	}
}
