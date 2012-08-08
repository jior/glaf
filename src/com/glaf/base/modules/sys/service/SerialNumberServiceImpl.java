package com.glaf.base.modules.sys.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.type.Type;

import com.glaf.base.dao.AbstractSpringDao;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.Constants;
import com.glaf.base.modules.sys.model.BaseDataInfo;
import com.glaf.base.modules.sys.model.SerialNumber;
import com.glaf.base.utils.WebUtil;

public class SerialNumberServiceImpl implements SerialNumberService {

	/**
	 * 在各虚获得编码的类中加入如下代码：
	 * 
	 * 1.引入Service private SerialNumberService serialNumberService; public void
	 * setSerialNumberService(SerialNumberService serialNumberService){
	 * this.serialNumberService = serialNumberService;
	 * logger.info("SerialNumberService Injected"); }
	 * 
	 * 2.插入参数（合同例子） Map pros = new HashMap();
	 * pros.put(Constants.SYS_MOD,Constants.SYS_CON); pros.put("category", new
	 * Integer(contract.getCategory())); pros.put("kind", new
	 * Integer(contract.getKind())); pros.put("type", new
	 * Integer(contract.getType()));
	 * contract.setContractNo(serialNumberService.getSerialNumber(pros));
	 * 
	 * 3.在action-xxxx.xml中,为该Action注入serialNumberService <property
	 * name="serialNumberService"> <ref bean="serialNumberProxy"/> </property>
	 * 
	 */

	private static final Log logger = LogFactory
			.getLog(SerialNumberServiceImpl.class);

	private BaseDataManager bdm = null;

	private BaseDataInfo bdi = null;

	private AbstractSpringDao abstractDao;

	public void setAbstractDao(AbstractSpringDao abstractDao) {
		this.abstractDao = abstractDao;
		logger.info("setAbstractDao");
	}

	// 决裁
	private String getABISerialNumber(Map params) {
		// 取模块代码
		String module = (String) params.get(Constants.SYS_MOD);
		// 询价代码
		StringBuffer sn = new StringBuffer("J");
		// 类别代码
		String typeCode = (String) params.get("type");
		if (StringUtils.isEmpty(typeCode)) {
			typeCode = "E";// 其他
		}
		sn.append(typeCode);
		// 年份月份
		sn.append(WebUtil.dateToString(new Date(), "yyMM"));
		// 序列号
		sn.append(getCurrentSerialNumber(module, 3));
		return sn.toString();
	}

	// 决裁
	private String getABIMSerialNumber(Map params) {
		// 取模块代码
		String module = (String) params.get(Constants.SYS_MOD);
		// 询价代码
		StringBuffer sn = new StringBuffer("MJ");
		// 类别代码
		String typeCode = (String) params.get("type");
		if (StringUtils.isEmpty(typeCode)) {
			typeCode = "E";// 其他
		}
		sn.append(typeCode);
		// 年份月份
		sn.append(WebUtil.dateToString(new Date(), "yyMM"));
		// 序列号
		sn.append(getCurrentSerialNumber(module, 3));
		return sn.toString();
	}

	// 合同
	private String getCONSerialNumber(Map params) {

		// 取模块代码
		String module = (String) params.get(Constants.SYS_MOD);

		try {
			bdm = BaseDataManager.getInstance();
		} catch (Exception e) {
		}

		Calendar date = Calendar.getInstance();
		Integer i = null;
		String sn = "";
		// 采购部简称
		sn = "PD";
		// 项目类别代码
		i = (Integer) params.get("category");
		if (i != null) {
			bdi = bdm.getValue(i.intValue(), "ZD0003");
			if (bdi != null) {
				sn += bdi.getCode();
			} else {
				sn += "Z";
			}
		}

		// 年
		sn += String.valueOf(date.get(Calendar.YEAR));
		// 月
		int month = date.get(Calendar.MONTH) + 1;
		if (month < 10) {
			sn += "0" + month;
		} else {
			sn += month;
		}
		// 序列号
		sn += getCurrentSerialNumber(module, 3);

		return sn;
	}

	// 重财
	private String getIMPSerialNumber(Map params) {
		// 取模块代码
		String module = (String) params.get(Constants.SYS_MOD);

		try {
			bdm = BaseDataManager.getInstance();
		} catch (Exception e) {
		}

		String sn = "T";// 审批项目：T特定采购相关
		Calendar date = Calendar.getInstance();
		String year = String.valueOf(date.get(Calendar.YEAR));
		sn += year.substring(2);// 审批年度
		sn += getCurrentSerialNumber(module, 4);// 流水号：4位流水号
		return sn;
	}

	// 预算
	private String getBUDSerialNumber(Map params) {
		return "";
	}

	// 询价
	private String getQUESerialNumber(Map params) {
		// 取模块代码
		String module = (String) params.get(Constants.SYS_MOD);
		// 询价代码
		StringBuffer sn = new StringBuffer("I");
		// 类别代码
		String typeCode = (String) params.get("type");
		if (StringUtils.isEmpty(typeCode)) {
			typeCode = "E";// 其他
		}
		sn.append(typeCode);
		// 年份月份
		sn.append(WebUtil.dateToString(new Date(), "yyMM"));
		// 序列号
		sn.append(getCurrentSerialNumber(module, 3));
		return sn.toString();
	}

	// 目录询价
	private String getQUEMSerialNumber(Map params) {
		// 取模块代码
		String module = (String) params.get(Constants.SYS_MOD);
		// 询价代码
		StringBuffer sn = new StringBuffer("MI");
		// 类别代码
		String typeCode = (String) params.get("type");
		if (StringUtils.isEmpty(typeCode)) {
			typeCode = "E";// 其他
		}
		sn.append(typeCode);
		// 年份月份
		sn.append(WebUtil.dateToString(new Date(), "yyMM"));
		// 序列号
		sn.append(getCurrentSerialNumber(module, 3));
		return sn.toString();
	}

	// 订单
	private String getORDSerialNumber(Map params) {
		// 取模块代码
		String module = (String) params.get(Constants.SYS_MOD);
		try {
			bdm = BaseDataManager.getInstance();
		} catch (Exception e) {
		}

		Integer i = null;
		String sn = "O";
		// 项目类别代码
		i = (Integer) params.get("category");
		if (i != null) {
			bdi = bdm.getValue(i.intValue(), "ZD0003");
			if (bdi != null) {
				sn += bdi.getCode();
			} else {
				sn += "Z";
			}
		}

		// 年月
		sn += WebUtil.dateToString(new Date(), "yyMM");

		// 序列号
		sn += getCurrentSerialNumber(module, 3);

		return sn.toString();
	}

	// 采购
	private String getPURSerialNumber(Map params) {
		// 取模块代码
		String module = (String) params.get(Constants.SYS_MOD);
		String sn = "";
		// 采购类别
		String category = (String) params.get("category");
		// 采购性质
		String type = (String) params.get("type");
		// 部门区分
		String deptNo = (String) params.get("deptNo");
		// 年份
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(new Date()).substring(2, 4);
		sn += category.trim() + type.trim() + deptNo.trim();
		sn += year.trim();
		sn += getCurrentSerialNumber(module, 4).trim();
		return sn.toString();
	}

	// 目录采购
	private String getCATESerialNumber(Map params) {
		// 取模块代码
		String module = (String) params.get(Constants.SYS_MOD);
		String sn = "M";
		// 采购类别
		String category = (String) params.get("category");
		// 采购性质
		String type = (String) params.get("type");
		// 部门区分
		String deptNo = (String) params.get("deptNo");
		// 年份
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(new Date()).substring(2, 4);
		sn += category.trim() + type.trim() + deptNo.trim();
		sn += year.trim();
		sn += getCurrentSerialNumber(module, 4).trim();
		return sn.toString();
	}

	// 报价
	private String getREPSerialNumber(Map params) {

		return "";
	}

	// 付款
	private String getPAYSerialNumber(Map params) {

		String module = (String) params.get(Constants.SYS_MOD);

		try {
			bdm = BaseDataManager.getInstance();
		} catch (Exception e) {
			logger.info(" baseDataManager is Error!");
		}

		StringBuffer sb = new StringBuffer();

		// 模块类别
		sb.append("P");

		// 采购类别
		String category = (String) params.get("category");

		if (category != null) {
			bdi = bdm.getValue(Integer.parseInt(category), "ZD0003");
			if (bdi != null) {
				sb.append(bdi.getCode());
			} else {
				sb.append("E");// 其它
			}
		}
		// 年月
		sb.append(WebUtil.dateToString(new Date(), "yyMMdd"));

		// 自动编号
		sb.append(getCurrentSerialNumber(module, 3));

		return sb.toString();
	}

	// 物品
	private String getGOODSerialNumber(Map params) {
		// 取模块代码
		String module = (String) params.get(Constants.SYS_MOD);
		// 物品代码
		StringBuffer sn = new StringBuffer("");
		// 类别代码
		String typeCode = (String) params.get("type");
		if (StringUtils.isEmpty(typeCode)) {
			typeCode = "E";// 其他
		}
		sn.append(typeCode);
		// 年份月份
		sn.append(WebUtil.dateToString(new Date(), "yyMM"));
		// 序列号
		sn.append(getCurrentSerialNumber(module, 4));
		return sn.toString();
	}

	// 目录物品申请单
	private String getGOODAPPLYSerialNumber(Map params) {
		// 取模块代码
		String module = (String) params.get(Constants.SYS_MOD);
		// 物品申请代码
		StringBuffer sn = new StringBuffer("");
		// 类别代码
		String typeCode = (String) params.get("type");
		sn.append("M");
		if (StringUtils.isEmpty(typeCode)) {
			typeCode = "E";// 其他
		}
		if (typeCode != null) {
			sn.append(typeCode);
		}

		// 年份月份
		sn.append(WebUtil.dateToString(new Date(), "yyMM"));
		// 序列号
		sn.append(getCurrentSerialNumber(module, 4));
		return sn.toString();
	}

	// 目录采购订购单
	private String getORDMOSerialNumber(Map params) {
		// 取模块代码
		String module = (String) params.get(Constants.SYS_MOD);
		try {
			bdm = BaseDataManager.getInstance();
		} catch (Exception e) {
		}

		Integer i = null;
		String sn = "MO";
		// 项目类别代码
		i = (Integer) params.get("category");
		if (i != null) {
			bdi = bdm.getValue(i.intValue(), "ZD0003");
			if (bdi != null) {
				sn += bdi.getCode();
			} else {
				sn += "Z";
			}
		}

		// 年月
		sn += WebUtil.dateToString(new Date(), "yyMM");

		// 序列号
		sn += getCurrentSerialNumber(module, 3);

		return sn.toString();
	}

	// 目录采购订购单
	private String getORDMCSerialNumber(Map params) {
		// 取模块代码
		String module = (String) params.get(Constants.SYS_MOD);
		try {
			bdm = BaseDataManager.getInstance();
		} catch (Exception e) {
		}

		Integer i = null;
		String sn = "MC";
		// 项目类别代码
		i = (Integer) params.get("category");
		if (i != null) {
			bdi = bdm.getValue(i.intValue(), "ZD0003");
			if (bdi != null) {
				sn += bdi.getCode();
			} else {
				sn += "Z";
			}
		}

		// 年月
		sn += WebUtil.dateToString(new Date(), "yyMM");

		// 序列号
		sn += getCurrentSerialNumber(module, 3);

		return sn.toString();
	}

	private String getCKACMSerialNumber(Map params) {

		String module = (String) params.get(Constants.SYS_MOD);

		try {
			bdm = BaseDataManager.getInstance();
		} catch (Exception e) {
			logger.info(" baseDataManager is Error!");
		}

		StringBuffer sb = new StringBuffer();

		// 模块类别
		sb.append("MY");
		// 采购类别
		String category = (String) params.get("category");

		if (category != null) {
			bdi = bdm.getValue(Integer.parseInt(category), "ZD0003");
			if (bdi != null) {
				sb.append(bdi.getCode());
			} else {
				sb.append("E");// 其它
			}
		}
		// 年月
		sb.append(WebUtil.dateToString(new Date(), "yyMM"));

		// 自动编号
		sb.append(getCurrentSerialNumber(module, 3));

		return sb.toString();
	}

	// 目录采购付款
	private String getPAYMSerialNumber(Map params) {

		String module = (String) params.get(Constants.SYS_MOD);

		try {
			bdm = BaseDataManager.getInstance();
		} catch (Exception e) {
			logger.info(" baseDataManager is Error!");
		}

		StringBuffer sb = new StringBuffer();

		// 模块类别
		sb.append("MP");

		// 采购类别
		String category = (String) params.get("category");

		if (category != null) {
			bdi = bdm.getValue(Integer.parseInt(category), "ZD0003");
			if (bdi != null) {
				sb.append(bdi.getCode());
			} else {
				sb.append("E");// 其它
			}
		}
		// 年月
		sb.append(WebUtil.dateToString(new Date(), "yyMM"));

		// 自动编号
		sb.append(getCurrentSerialNumber(module, 3));

		return sb.toString();
	}

	/**
	 * 对单一供应商的决策评价自动编号
	 * 
	 * @param params
	 * @return
	 */
	private String getCommendSerialNumber(Map params) {
		String module = (String) params.get(Constants.SYS_MOD);

		StringBuffer sb = new StringBuffer();
		// 模块类别
		sb.append("SSA");
		// 年月
		sb.append(WebUtil.dateToString(new Date(), "yyMM"));
		// 自动编号
		sb.append(getCurrentSerialNumber(module, 3));

		return sb.toString();
	}

	public synchronized String getSerialNumber(Map params) {
		// 获取模块No
		String module = (String) params.get(Constants.SYS_MOD);
		// 决裁
		if (module.equals(Constants.SYS_ABI)) {
			return getABISerialNumber(params);
		}
		// 目录决裁
		if (module.equals(Constants.SYS_ABIM)) {
			return getABIMSerialNumber(params);
		}
		// 预算
		if (module.equals(Constants.SYS_BUD)) {
			return getBUDSerialNumber(params);
		}
		// 合同
		if (module.equals(Constants.SYS_CON)) {
			return getCONSerialNumber(params);
		}
		// 重财
		if (module.equals(Constants.SYS_IMP)) {
			return getIMPSerialNumber(params);
		}
		// 订单
		if (module.equals(Constants.SYS_ORD)) {
			return getORDSerialNumber(params);
		}
		// 采购
		if (module.equals(Constants.SYS_PUR)) {
			return getPURSerialNumber(params);
		}
		// 询价
		if (module.equals(Constants.SYS_QUE)) {
			return getQUESerialNumber(params);
		}
		// 目录询价
		if (module.equals(Constants.SYS_QUEM)) {
			return getQUEMSerialNumber(params);
		}
		// 报价
		if (module.equals(Constants.SYS_REP)) {
			return getREPSerialNumber(params);
		}
		// 付款
		if (module.equals(Constants.SYS_PAY)) {
			return getPAYSerialNumber(params);
		}
		// 单一供应商的决策评价
		if (module.equals(Constants.SYS_COM)) {
			return getCommendSerialNumber(params);
		}
		// 长期供应商
		if (module.equals(Constants.SYS_SUP)) {
			return getSUPSerialNumber(params);
		}
		// 临时供应商 && 询价供应商
		if (module.equals(Constants.SYS_TEMPSUP)) {
			return getTEMPSUPSerialNumber(params);
		}
		// 物品
		if (module.equals(Constants.SYS_GOOD)) {
			return getGOODSerialNumber(params);
		}

		// 目录采购订购单
		if (module.equals(Constants.SYS_ORDMO)) {
			return getORDMOSerialNumber(params);
		}
		// 目录采购价格联络书
		if (module.equals(Constants.SYS_ORDMC)) {
			return getORDMCSerialNumber(params);
		}
		// 目录采购验收单
		if (module.equals(Constants.SYS_CKACM)) {
			return getCKACMSerialNumber(params);
		}
		// 目录采购应付款通知书
		if (module.equals(Constants.SYS_PAYM)) {
			return getPAYMSerialNumber(params);
		}

		if (module.equals(Constants.SYS_CATE)) {
			return getCATESerialNumber(params);
		}
		// 目录采购物品申请单
		if (module.equals(Constants.SYS_GOODAPPLYM)) {
			return getGOODAPPLYSerialNumber(params);
		}
		throw new RuntimeException("不存在此模块编码，请检查输入代码");
	}

	/**
	 * 根据指定len，返回当前序列号编码
	 * 
	 * @param moudle
	 * @param len
	 * @return
	 */
	private String getCurrentSerialNumber(String moudle, int length) {
		String sn = String.valueOf(getCurrentSerialNumber(moudle));
		int len = sn.length();
		for (int i = 0; i < length - len; i++) {
			sn = "0" + sn;
		}
		return sn;
	}

	/**
	 * 获取当前序列号
	 * 
	 * @param module
	 * @return
	 */
	private int getCurrentSerialNumber(String module) {

		int retInt = 1;
		StringBuffer sb = new StringBuffer();
		Object[] values = new Object[] { module };
		Type[] types = new Type[] { Hibernate.STRING };
		sb.append(" from SerialNumber s where s.moduleNo=? ");
		List tempList = abstractDao.getList(sb.toString(), values, types);

		if (tempList != null && tempList.size() > 0) {

			SerialNumber serialNumber = (SerialNumber) tempList.iterator()
					.next();
			// 检测是否已过归零日期
			int intervel = serialNumber.getIntervelNo();
			Calendar lastCal = Calendar.getInstance();
			lastCal.setTime(serialNumber.getLastDate());// 2007-05-15

			Calendar curCal = Calendar.getInstance();
			Date curDate = WebUtil.stringToDate(WebUtil
					.dateToString(new Date()));// 只包含年月日的日期
			curCal.setTime(curDate); // 2007-07-21

			boolean reCaculate = true;
			if (intervel == Constants.INTERVEL_1) {
				// yue
				// lastCal.roll(Calendar.MONTH, 1);
				lastCal.set(Calendar.DAY_OF_MONTH, 1);
				curCal.set(Calendar.DAY_OF_MONTH, 1);
				reCaculate = curCal.after(lastCal);
			} else if (intervel == Constants.INTERVEL_2) {
				// nian
				// newCal.roll(Calendar.YEAR, 1);
				lastCal.set(Calendar.DAY_OF_MONTH, 1);
				lastCal.set(Calendar.MONTH, 1);
				curCal.set(Calendar.DAY_OF_MONTH, 1);
				curCal.set(Calendar.MONTH, 1);
				reCaculate = curCal.after(lastCal);
			} else if (intervel == Constants.INTERVEL_3) {
				// ri
				// newCal.roll(Calendar.DAY_OF_MONTH, 1);
				reCaculate = curCal.after(lastCal);
			} else {
				// leiji
				reCaculate = false;
			}

			// if (reCaculate
			// && newCal.getTime().compareTo(
			// Calendar.getInstance().getTime()) < 0) {
			if (reCaculate) {
				serialNumber.setCurrentSerail(retInt);
				serialNumber.setLastDate(new Date());
				logger.info("序号重新计算： retInt = " + retInt);
			}
			retInt = serialNumber.getCurrentSerail();

			// 序列号自增１
			serialNumber.setCurrentSerail(retInt + 1);
			logger.info("序号： retInt = " + retInt);
			logger.info("自增后的序号： CurrentSerail = "
					+ serialNumber.getCurrentSerail());
			update(serialNumber);
		}
		return retInt;
	}

	public boolean update(SerialNumber bean) {
		return abstractDao.update(bean);
	}

	private static String getCodeNumber(int category, int area) {
		String categoryCode = ""; // 这个只是给自己看的变量
		String areaCode = ""; // 这个只是给自己看的变量
		String module = "";
		switch (category) {
		case 235:
			categoryCode = "8";
			switch (area) {
			case 250:
				areaCode = "0";
				module = "C_8_AREA_0";
				break;
			case 251:
				areaCode = "9";
				module = "C_8_AREA_9";
				break;
			case 252:
				areaCode = "8";
				module = "C_8_AREA_8";
				break;
			case 253:
				areaCode = "7";
				module = "C_8_AREA_7";
				break;
			case 254:
				areaCode = "6";
				module = "C_8_AREA_6";
				break;
			case 255:
				areaCode = "5";
				module = "C_8_AREA_5";
				break;
			case 256:
				areaCode = "4";
				module = "C_8_AREA_4";
				break;
			case 257:
				areaCode = "3";
				module = "C_8_AREA_3";
				break;
			case 258:
				areaCode = "2";
				module = "C_8_AREA_2";
				break;
			case 259:
				areaCode = "1";
				module = "C_8_AREA_1";
				break;
			}
			break;
		case 236:
			categoryCode = "7";
			switch (area) {
			case 250:
				areaCode = "0";
				module = "C_7_AREA_0";
				break;
			case 251:
				areaCode = "9";
				module = "C_7_AREA_9";
				break;
			case 252:
				areaCode = "8";
				module = "C_7_AREA_8";
				break;
			case 253:
				areaCode = "7";
				module = "C_7_AREA_7";
				break;
			case 254:
				areaCode = "6";
				module = "C_7_AREA_6";
				break;
			case 255:
				areaCode = "5";
				module = "C_7_AREA_5";
				break;
			case 256:
				areaCode = "4";
				module = "C_7_AREA_4";
				break;
			case 257:
				areaCode = "3";
				module = "C_7_AREA_3";
				break;
			case 258:
				areaCode = "2";
				module = "C_7_AREA_2";
				break;
			case 259:
				areaCode = "1";
				module = "C_7_AREA_1";
				break;
			}
			break;
		case 237:
			categoryCode = "6";
			switch (area) {
			case 250:
				areaCode = "0";
				module = "C_6_AREA_0";
				break;
			case 251:
				areaCode = "9";
				module = "C_6_AREA_9";
				break;
			case 252:
				areaCode = "8";
				module = "C_6_AREA_8";
				break;
			case 253:
				areaCode = "7";
				module = "C_6_AREA_7";
				break;
			case 254:
				areaCode = "6";
				module = "C_6_AREA_6";
				break;
			case 255:
				areaCode = "5";
				module = "C_6_AREA_5";
				break;
			case 256:
				areaCode = "4";
				module = "C_6_AREA_4";
				break;
			case 257:
				areaCode = "3";
				module = "C_6_AREA_3";
				break;
			case 258:
				areaCode = "2";
				module = "C_6_AREA_2";
				break;
			case 259:
				areaCode = "1";
				module = "C_6_AREA_1";
				break;
			}
			break;
		case 238:
			categoryCode = "5";
			switch (area) {
			case 250:
				areaCode = "0";
				module = "C_5_AREA_0";
				break;
			case 251:
				areaCode = "9";
				module = "C_5_AREA_9";
				break;
			case 252:
				areaCode = "8";
				module = "C_5_AREA_8";
				break;
			case 253:
				areaCode = "7";
				module = "C_5_AREA_7";
				break;
			case 254:
				areaCode = "6";
				module = "C_5_AREA_6";
				break;
			case 255:
				areaCode = "5";
				module = "C_5_AREA_5";
				break;
			case 256:
				areaCode = "4";
				module = "C_5_AREA_4";
				break;
			case 257:
				areaCode = "3";
				module = "C_5_AREA_3";
				break;
			case 258:
				areaCode = "2";
				module = "C_5_AREA_2";
				break;
			case 259:
				areaCode = "1";
				module = "C_5_AREA_1";
				break;
			}
			break;
		case 239:
			categoryCode = "4";
			switch (area) {
			case 250:
				areaCode = "0";
				module = "C_4_AREA_0";
				break;
			case 251:
				areaCode = "9";
				module = "C_4_AREA_9";
				break;
			case 252:
				areaCode = "8";
				module = "C_4_AREA_8";
				break;
			case 253:
				areaCode = "7";
				module = "C_4_AREA_7";
				break;
			case 254:
				areaCode = "6";
				module = "C_4_AREA_6";
				break;
			case 255:
				areaCode = "5";
				module = "C_4_AREA_5";
				break;
			case 256:
				areaCode = "4";
				module = "C_4_AREA_4";
				break;
			case 257:
				areaCode = "3";
				module = "C_4_AREA_3";
				break;
			case 258:
				areaCode = "2";
				module = "C_4_AREA_2";
				break;
			case 259:
				areaCode = "1";
				module = "C_4_AREA_1";
				break;
			}
			break;
		case 240:
			categoryCode = "3";
			switch (area) {
			case 250:
				areaCode = "0";
				module = "C_3_AREA_0";
				break;
			case 251:
				areaCode = "9";
				module = "C_3_AREA_9";
				break;
			case 252:
				areaCode = "8";
				module = "C_3_AREA_8";
				break;
			case 253:
				areaCode = "7";
				module = "C_3_AREA_7";
				break;
			case 254:
				areaCode = "6";
				module = "C_3_AREA_6";
				break;
			case 255:
				areaCode = "5";
				module = "C_3_AREA_5";
				break;
			case 256:
				areaCode = "4";
				module = "C_3_AREA_4";
				break;
			case 257:
				areaCode = "3";
				module = "C_3_AREA_3";
				break;
			case 258:
				areaCode = "2";
				module = "C_3_AREA_2";
				break;
			case 259:
				areaCode = "1";
				module = "C_3_AREA_1";
				break;
			}
			break;
		case 241:
			categoryCode = "2";
			switch (area) {
			case 250:
				areaCode = "0";
				module = "C_2_AREA_0";
				break;
			case 251:
				areaCode = "9";
				module = "C_2_AREA_9";
				break;
			case 252:
				areaCode = "8";
				module = "C_2_AREA_8";
				break;
			case 253:
				areaCode = "7";
				module = "C_2_AREA_7";
				break;
			case 254:
				areaCode = "6";
				module = "C_2_AREA_6";
				break;
			case 255:
				areaCode = "5";
				module = "C_2_AREA_5";
				break;
			case 256:
				areaCode = "4";
				module = "C_2_AREA_4";
				break;
			case 257:
				areaCode = "3";
				module = "C_2_AREA_3";
				break;
			case 258:
				areaCode = "2";
				module = "C_2_AREA_2";
				break;
			case 259:
				areaCode = "1";
				module = "C_2_AREA_1";
				break;
			}
			break;
		case 242:
			categoryCode = "1";
			switch (area) {
			case 250:
				areaCode = "0";
				module = "C_1_AREA_0";
				break;
			case 251:
				areaCode = "9";
				module = "C_1_AREA_9";
				break;
			case 252:
				areaCode = "8";
				module = "C_1_AREA_8";
				break;
			case 253:
				areaCode = "7";
				module = "C_1_AREA_7";
				break;
			case 254:
				areaCode = "6";
				module = "C_1_AREA_6";
				break;
			case 255:
				areaCode = "5";
				module = "C_1_AREA_5";
				break;
			case 256:
				areaCode = "4";
				module = "C_1_AREA_4";
				break;
			case 257:
				areaCode = "3";
				module = "C_1_AREA_3";
				break;
			case 258:
				areaCode = "2";
				module = "C_1_AREA_2";
				break;
			case 259:
				areaCode = "1";
				module = "C_1_AREA_1";
				break;
			}
			break;
		case 243:
			categoryCode = "Q";
			module = "CATEGORY_Q";
			break;
		case 244:
			categoryCode = "P";
			module = "CATEGORY_P";
			break;
		case 245:
			categoryCode = "E";
			module = "CATEGORY_E";
			break;
		case 246:
			categoryCode = "D";
			module = "CATEGORY_D";
			break;
		case 247:
			categoryCode = "C";
			module = "CATEGORY_C";
			break;
		case 248:
			categoryCode = "B";
			module = "CATEGORY_B";
			break;
		case 249:
			categoryCode = "A";
			module = "CATEGORY_A";
			break;
		case 382:
			categoryCode = "F";
			module = "CATEGORY_F";
			break;
		}
		return module;
	}

	// 长期供应商
	private String getSUPSerialNumber(Map params) {
		Integer category = Integer.valueOf(params.get("category").toString());
		Integer area = Integer.valueOf(params.get("area").toString());
		String sn = "";
		try {
			bdm = BaseDataManager.getInstance();
		} catch (Exception e) {
			logger.info("getSUPSerialNumber in baseDataManager is Error!");
		}

		if (category != null && area != null) {
			bdi = bdm.getValue(category.intValue(), "ZD0012");
			if (bdi != null) {
				sn += bdi.getCode();
			}
			bdi = bdm.getValue(area.intValue(), "ZD0014");
			if (bdi != null) {
				sn += bdi.getCode();
			}

			String module = getCodeNumber(category.intValue(), area.intValue());
			sn += getCurrentSerialNumber(module, 2);
		}
		return sn;
	}

	// 临时供应商 && 询价供应商
	private String getTEMPSUPSerialNumber(Map params) {
		Integer category = Integer.valueOf(params.get("category").toString());
		String sn = "";
		try {
			bdm = BaseDataManager.getInstance();
		} catch (Exception e) {
			logger.info("getTEMPSUPSerialNumber in baseDataManager is Error!");
		}

		if (category != null) {
			bdi = bdm.getValue(category.intValue(), "ZD0013");
			if (bdi != null) {
				sn += bdi.getCode();
			}
			String module = getCodeNumber(category.intValue(), -1);
			sn += getCurrentSerialNumber(module, 3);
		}
		return sn;
	}

	// 通过模块NO、category、area来查找SerialNumber中对应的moduleNo，从而获取SerialNumber对象
	public synchronized List getSupplierSerialNumber(Map params) {
		// 获取模块No
		String module = (String) params.get(Constants.SYS_MOD);

		Integer category = Integer.valueOf(params.get("category").toString());
		Integer area = Integer.valueOf(params.get("area").toString());
		String moduleNo = "";
		try {
			bdm = BaseDataManager.getInstance();
		} catch (Exception e) {
			logger.info("updateSupplierSerialNumber in baseDataManager is Error!");
		}

		if (module.equals(Constants.SYS_SUP)) {
			if (category != null && area != null) {
				bdi = bdm.getValue(category.intValue(), "ZD0012");
				if (bdi != null) {
					moduleNo += "C_" + bdi.getCode() + "_AREA_";
				}
				bdi = bdm.getValue(area.intValue(), "ZD0014");
				if (bdi != null) {
					moduleNo += bdi.getCode();
				}
			}
		}
		if (module.equals(Constants.SYS_TEMPSUP)) {
			if (category != null) {
				bdi = bdm.getValue(category.intValue(), "ZD0013");
				if (bdi != null) {
					moduleNo += "CATEGORY_" + bdi.getCode();
				}
			}
		}

		logger.info("moduleNo----------------" + moduleNo);
		StringBuffer sb = new StringBuffer();
		sb.append("from SerialNumber s where s.moduleNo=?");
		Object[] values = { new String(moduleNo) };
		return abstractDao.getList(sb.toString(), values, null);
	}

	public synchronized List getImportSupplierSerialNumber(Map params) {
		// 获取模块No
		String module = (String) params.get(Constants.SYS_MOD);

		String category = params.get("category").toString();
		String area = params.get("area").toString();
		String moduleNo = "";
		try {
			bdm = BaseDataManager.getInstance();
		} catch (Exception e) {
			logger.info("updateSupplierSerialNumber in baseDataManager is Error!");
		}

		if (module.equals(Constants.SYS_SUP)) {
			if (category != null && area != null) {
				moduleNo += "C_" + category + "_AREA_" + area;
			}
		}
		if (module.equals(Constants.SYS_TEMPSUP)) {
			if (category != null) {
				moduleNo += "CATEGORY_" + category;
			}
		}
		logger.info("moduleNo----------------" + moduleNo);
		StringBuffer sb = new StringBuffer();
		sb.append("from SerialNumber s where s.moduleNo=?");
		Object[] values = { new String(moduleNo) };
		return abstractDao.getList(sb.toString(), values, null);
	}

	public static void main(String arg[]) {
		System.out.println("yyMMdd="
				+ WebUtil.dateToString(new Date(), "yyMMdd"));

		Calendar lastCal = Calendar.getInstance();
		lastCal.setTime(WebUtil.stringToDate("2007-07-21"));// 2007-05-15

		Calendar curCal = Calendar.getInstance();
		Date curDate = WebUtil.stringToDate(WebUtil.dateToString(new Date()));// 只包含年月日的日期
		curCal.setTime(curDate); // 2007-07-21

		boolean reCaculate = true;
		int intervel = Constants.INTERVEL_3;// ///////

		if (intervel == Constants.INTERVEL_1) {
			// yue
			// lastCal.roll(Calendar.MONTH, 1);
			lastCal.set(Calendar.DAY_OF_MONTH, 1);
			curCal.set(Calendar.DAY_OF_MONTH, 1);
			reCaculate = curCal.after(lastCal);
		} else if (intervel == Constants.INTERVEL_2) {
			// nian
			// newCal.roll(Calendar.YEAR, 1);
			lastCal.set(Calendar.DAY_OF_MONTH, 1);
			lastCal.set(Calendar.MONTH, 1);
			curCal.set(Calendar.DAY_OF_MONTH, 1);
			curCal.set(Calendar.MONTH, 1);
			reCaculate = curCal.after(lastCal);
		} else if (intervel == Constants.INTERVEL_3) {
			// ri
			// newCal.roll(Calendar.DAY_OF_MONTH, 1);
			reCaculate = curCal.after(lastCal);
		} else {
			// leiji
			reCaculate = false;
		}
		System.out.println(reCaculate);
	}
}