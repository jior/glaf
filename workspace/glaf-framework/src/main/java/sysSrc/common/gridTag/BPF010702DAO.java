package sysSrc.common.gridTag;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import baseSrc.common.AutoArrayList;
import sysSrc.common.SysBaseCom;
import baseSrc.common.BaseUtility;
import baseSrc.common.DbAccess;
import baseSrc.framework.BaseConstants;
import baseSrc.framework.BaseDAO;
import baseSrc.framework.BaseDTOMap;

public class BPF010702DAO extends BaseDAO {
	public static final String XGSTATS = "1";
	/**
	 * 初始化页面
	 * 
	 * @param form
	 * @param baseCom
	 * @return
	 */
	public BaseDTOMap runPageLoad(BPF010702Form form, SysBaseCom baseCom) {

		BaseDTOMap dtoMap = new BaseDTOMap();
		
		dtoMap.setForwardId("BPF010702Go");
		return dtoMap;
	}
	/**
	 * 检索
	 * 
	 * @param form
	 * @param baseCom
	 * @return
	 */
	public BaseDTOMap runPageSearch(BPF010702Form form, SysBaseCom baseCom) {

		BaseDTOMap dtoMap = new BaseDTOMap();

		List<?> list = getDataFormDB(form, baseCom);

		setActionForm(list, form);
		
		dtoMap.setForwardId("BPF010702Go");
		return dtoMap;
	}

	private void setActionForm(List<?> list, BPF010702Form form) {

		// 定义画面中数据元素
		AutoArrayList detailList = new AutoArrayList(BPF010702Details.class);
		// 根据取得数值设置画面元素值
		int icount = 1;
		for (int i = 0; i < list.size(); i++) {
			BPF010702Details detail = new BPF010702Details();
			HashMap<String, Object> map= (HashMap<String, Object>) list.get(i);
			detail.setYfkjrny(BaseUtility.getString(map.get("PAYABLEYEARS".toLowerCase())));
			detail.setCjbh(BaseUtility.getString(map.get("SUPPNO".toLowerCase())));
			if(!BaseUtility.isStringNull(detail.getCjbh())){
				detail.setNo(icount+"");
				icount++;
			}
			detail.setPf(BaseUtility.getString(map.get("PRODUCTNO".toLowerCase())));
			detail.setPm(BaseUtility.getString(map.get("PRODUCTNAME".toLowerCase())));
			detail.setBhsje(BaseUtility.getString(map.get("SUMPRICEDIS".toLowerCase())));
			detail.setHsje(BaseUtility.getString(map.get("SUMPRICE".toLowerCase())));
			detailList.add(detail);
		}
		// 把设置好的值存放到画面对应FORMBEAN中
		form.setAllCount("" + detailList.size());
		form.setBPF010702Details(detailList);
	}

	private List<?> getDataFormDB(BPF010702Form form, SysBaseCom baseCom) {

			StringBuffer sql = new StringBuffer();
			HashMap<String, Object> para = new HashMap<String, Object>();
			sql.append("select a.*  ");
			sql.append("from T_GridPay a  ");
			sql.append(" where 1=1 ");
			if (StringUtils.isNotBlank(form.getCxny())) {
				sql.append("and PayableYears = '"
						+ StringUtils.replace(form.getCxny(), "'", "''").replace(" ", "").replace("-", "")
						+ "' ");
			}
			sql.append(" order by PayableYears,SuppNo ");
			
			List<HashMap<String,Object>> listget = (List<HashMap<String, Object>>) dbAccess.find2Map(sql.toString(), para);
			Grid t = new Grid();
			List<HashMap<String,Object>> list = 
					t.getTotalList(
							listget,
							"PAYABLEYEARS:SUPPNO", 
							"SUMPRICEDIS:SUMPRICE", 
							"PAYABLEYEARS", 
							"处理年月小计({0}):厂家小计({0})", 
							true, 
							"合计"
							);
			return list;
	}

	/**
	 * 设置下拉列表框
	 * @param dbAccess
	 * @param dtoMap
	 * @throws Exception
	 */
	private void setDto(DbAccess dbAccess,BaseDTOMap dtoMap) throws Exception {
	}
	
	public String pDouble(String pDouble) 
	{ 
		String d = "";
		try{
		if(BaseUtility.isStringNull(pDouble)){
			return "";
		}
		DecimalFormat   df2   =   new   DecimalFormat( "###,###,###,##0.00"); 
		d = df2.format(Double.parseDouble(pDouble));
		}catch (Exception e) {
			d = pDouble;
		}
	    return d ; 
	} 
	public String pBaifen(String pDouble) 
	{ 
		String d = "";
		try{
		if(BaseUtility.isStringNull(pDouble)){
			return "";
		}
		}catch (Exception e) {
			d = pDouble;
		}
		d = this.pDouble((Double.parseDouble(pDouble)*100)+"") + "%";
	    return d ; 
	} 

}
