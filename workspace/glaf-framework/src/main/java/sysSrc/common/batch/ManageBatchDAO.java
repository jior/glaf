package sysSrc.common.batch;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sysSrc.common.SysBaseCom;
import sysSrc.framework.SysBaseDTOMap;
import sysSrc.orm.TBatchconfig;

import net.sf.json.JSONArray;

import baseSrc.common.AutoArrayList;
import baseSrc.common.BaseUtility;
import baseSrc.common.LogHelper;
import baseSrc.framework.BaseConstants;
import baseSrc.framework.BaseDAO;
import baseSrc.framework.BusException;

public class ManageBatchDAO extends BaseDAO {
	protected static LogHelper log = new LogHelper(ManageBatchDAO.class);

	public SysBaseDTOMap runPageLoad(ManageBatchForm form, SysBaseCom baseCom) {
		SysBaseDTOMap dtoMap = new SysBaseDTOMap();
		AutoArrayList detailList = new AutoArrayList(ManageBatchDetail.class);

		dtoMap.setDetailList(detailList);
		dtoMap.setForwardId("localGo");

		return dtoMap;
	}

	public SysBaseDTOMap runPageCtrl(ManageBatchForm form, SysBaseCom baseCom) {

		// 创建控制接口对象
		SysBaseDTOMap dtoMap = new SysBaseDTOMap();

		// 取得每一页的数据对象
		List<?> list = getDataFormDB(form, baseCom);

		// 定义画面中数据元素
		AutoArrayList detailList = new AutoArrayList(ManageBatchDetail.class);

		// 根据取得数值设置画面元素值
		if (!BaseUtility.isListNull(list)) {
			for (int i = 0; i < list.size(); i++) {
				TBatchconfig tabObj = (TBatchconfig) list.get(i);
				ManageBatchDetail bel = new ManageBatchDetail();
				bel.setBatName(tabObj.getBatname());
				Date lastExeDate = tabObj.getLastexedate();

				bel.setStartDate(tabObj.getLastexedate().toString());
				bel.setInterval(tabObj.getInterval().toString());
				bel.setIsUse(tabObj.getEnable());
				bel.setWhat(tabObj.getWhat());
				String status = "stay";
				if ("1".equals(tabObj.getLockflag())) {
					status = "running";
				}
				bel.setStatus(status);
				detailList.add(bel);
			}
		}

		// 把设置好的值存放到画面对应FORMBEAN中
		dtoMap.setDetailList(detailList);

		dtoMap.setForwardId("localGo");
		form.setGridData(JSONArray.fromObject(detailList).toString());
		return dtoMap;
	}

	private List<?> getDataFormDB(ManageBatchForm form, SysBaseCom baseCom) {

		// 定义检索表名

		String tabName = "TBatchconfig";

		// 创建检索条件参数对象

		Map<String, Object> params = new HashMap<String, Object>();

		// 定义检索条件

		StringBuffer whereHql = new StringBuffer(" WHERE ");
		if (!BaseUtility.isStringNull(form.getS_Name())) {
			whereHql.append("batname like :batname AND");
			params.put("batname", form.getS_Name());
		}
		whereHql.append(" 1=1");
		whereHql.append(" order by batname ,lastexedate ");

		// 取得当前检索条件的数据总条数

		int count = this.dbAccess.getResutlsTotal(tabName, whereHql.toString(),
				params);

		// 取得每一页的显示条数
		int pageSize = BaseConstants.ISC_PAGE_SIZE_FIVE;

		// 取得每一页的数据对象
		List<?> list = this.dbAccess.getResutlsForPage(tabName,
				whereHql.toString(), params, form, count, pageSize);
		return list;
	}

	public SysBaseDTOMap runSave(ManageBatchForm form, SysBaseCom baseCom) {

		// 创建控制接口对象
		SysBaseDTOMap dtoMap = new SysBaseDTOMap();
		List<?> l = form.getGridList(ManageBatchDetail.class);

		try {
			for (int i = 0; i < l.size(); i++) {
				ManageBatchDetail ced = (ManageBatchDetail) l.get(i);

				// 判断当前bat是修改还是新增
				List tces = this.dbAccess.find("TBatchconfig",
						" where batname = '" + ced.getBatName() + "'", null);
				TBatchconfig tce = new TBatchconfig();

				WindowsTasksOper wto = new WindowsTasksOper();
				// 新增
				if (null == tces || 0 == tces.size()) {
					String startDate = ced.getStartDate();
					SimpleDateFormat sf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					tce = new TBatchconfig();
					tce.setBatname(ced.getBatName());
					tce.setLastexedate(sf.parse(startDate));
					tce.setInterval(new BigDecimal(ced.getInterval()));
					tce.setEnable(ced.getIsUse());
					tce.setWhat(ced.getWhat());
					tce.setLockflag("0");

					try {
						// 新增windows定时任务
						wto.addTask(tce);
					} catch (Exception e) {
						dtoMap.setMsgId("bat.creatTaskError");
						dtoMap.setForwardId("localGo");
						throw new BusException(dtoMap);
					}
				} else {
					tce = (TBatchconfig) tces.get(0);
					// 判断是否锁定
					if ("1".equals(tce.getLockflag())) {
						// 如果处于锁定状态，则抛出异常
						dtoMap.setMsgId("bat.canntSave");
						dtoMap.setForwardId("localGo");
						throw new BusException(dtoMap);
					}

					String startDate = ced.getStartDate();
					SimpleDateFormat sf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					tce.setLastexedate(sf.parse(startDate));
					tce.setInterval(new BigDecimal(ced.getInterval()));
					tce.setEnable(ced.getIsUse());
					tce.setWhat(ced.getWhat());

					try {
						// 删除定时任务
						wto.deleteTask(tce);
						// 重新建成定时任务
						wto.addTask(tce);
					} catch (Exception e) {
						dtoMap.setMsgId("bat.creatTaskError");
						dtoMap.setForwardId("localGo");
						throw new BusException(dtoMap);
					}

				}
				this.dbAccess.saveOrUpdate(tce);

			}
			// 保存成功，则提示操作成功
			dtoMap.setMsgId("info.saveOK");
			dtoMap.setForwardId("localGo");
		} catch (Exception e) {
			// 保存异常时，提示异常消息
			if (null == dtoMap.getMsgId() || "".equals(dtoMap.getMsgId())) {
				dtoMap.setMsgId("info.saveNG");
			}
			dtoMap.setForwardId("localGo");
			log.error(baseCom, e.getMessage());
			// 抛出异常：BusException
			throw new BusException(dtoMap);
		}

		return dtoMap;
	}

	public SysBaseDTOMap runDelete(ManageBatchForm form, SysBaseCom baseCom) {
		SysBaseDTOMap dtoMap = new SysBaseDTOMap();
		List<?> l = form.getGridList(ManageBatchDetail.class);

		try {
			for (int i = 0; i < l.size(); i++) {
				ManageBatchDetail ced = (ManageBatchDetail) l.get(i);
				this.dbAccess.delete("TBatchconfig",
						" where batname = '" + ced.getBatName() + "'", null);
				// 删除定时任务
				WindowsTasksOper wto = new WindowsTasksOper();
				wto.deleteTask(ced.getBatName());
			}
			// 保存成功，则提示操作成功
			dtoMap.setMsgId("info.saveOK");
			dtoMap.setForwardId("localGo");
		} catch (Exception e) {
			// 保存异常时，提示异常消息
			dtoMap.setMsgId("info.saveNG");
			dtoMap.setForwardId("localGo");
			log.error(baseCom, e.getMessage());
			// 抛出异常：BusException
			throw new BusException(dtoMap);
		}
		return dtoMap;
	}

}
