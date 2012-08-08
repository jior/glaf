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
	 * �ڸ����ñ�������м������´��룺
	 * 
	 * 1.����Service private SerialNumberService serialNumberService; public void
	 * setSerialNumberService(SerialNumberService serialNumberService){
	 * this.serialNumberService = serialNumberService;
	 * logger.info("SerialNumberService Injected"); }
	 * 
	 * 2.�����������ͬ���ӣ� Map pros = new HashMap();
	 * pros.put(Constants.SYS_MOD,Constants.SYS_CON); pros.put("category", new
	 * Integer(contract.getCategory())); pros.put("kind", new
	 * Integer(contract.getKind())); pros.put("type", new
	 * Integer(contract.getType()));
	 * contract.setContractNo(serialNumberService.getSerialNumber(pros));
	 * 
	 * 3.��action-xxxx.xml��,Ϊ��Actionע��serialNumberService <property
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

	// ����
	private String getABISerialNumber(Map params) {
		// ȡģ�����
		String module = (String) params.get(Constants.SYS_MOD);
		// ѯ�۴���
		StringBuffer sn = new StringBuffer("J");
		// ������
		String typeCode = (String) params.get("type");
		if (StringUtils.isEmpty(typeCode)) {
			typeCode = "E";// ����
		}
		sn.append(typeCode);
		// ����·�
		sn.append(WebUtil.dateToString(new Date(), "yyMM"));
		// ���к�
		sn.append(getCurrentSerialNumber(module, 3));
		return sn.toString();
	}

	// ����
	private String getABIMSerialNumber(Map params) {
		// ȡģ�����
		String module = (String) params.get(Constants.SYS_MOD);
		// ѯ�۴���
		StringBuffer sn = new StringBuffer("MJ");
		// ������
		String typeCode = (String) params.get("type");
		if (StringUtils.isEmpty(typeCode)) {
			typeCode = "E";// ����
		}
		sn.append(typeCode);
		// ����·�
		sn.append(WebUtil.dateToString(new Date(), "yyMM"));
		// ���к�
		sn.append(getCurrentSerialNumber(module, 3));
		return sn.toString();
	}

	// ��ͬ
	private String getCONSerialNumber(Map params) {

		// ȡģ�����
		String module = (String) params.get(Constants.SYS_MOD);

		try {
			bdm = BaseDataManager.getInstance();
		} catch (Exception e) {
		}

		Calendar date = Calendar.getInstance();
		Integer i = null;
		String sn = "";
		// �ɹ������
		sn = "PD";
		// ��Ŀ������
		i = (Integer) params.get("category");
		if (i != null) {
			bdi = bdm.getValue(i.intValue(), "ZD0003");
			if (bdi != null) {
				sn += bdi.getCode();
			} else {
				sn += "Z";
			}
		}

		// ��
		sn += String.valueOf(date.get(Calendar.YEAR));
		// ��
		int month = date.get(Calendar.MONTH) + 1;
		if (month < 10) {
			sn += "0" + month;
		} else {
			sn += month;
		}
		// ���к�
		sn += getCurrentSerialNumber(module, 3);

		return sn;
	}

	// �ز�
	private String getIMPSerialNumber(Map params) {
		// ȡģ�����
		String module = (String) params.get(Constants.SYS_MOD);

		try {
			bdm = BaseDataManager.getInstance();
		} catch (Exception e) {
		}

		String sn = "T";// ������Ŀ��T�ض��ɹ����
		Calendar date = Calendar.getInstance();
		String year = String.valueOf(date.get(Calendar.YEAR));
		sn += year.substring(2);// �������
		sn += getCurrentSerialNumber(module, 4);// ��ˮ�ţ�4λ��ˮ��
		return sn;
	}

	// Ԥ��
	private String getBUDSerialNumber(Map params) {
		return "";
	}

	// ѯ��
	private String getQUESerialNumber(Map params) {
		// ȡģ�����
		String module = (String) params.get(Constants.SYS_MOD);
		// ѯ�۴���
		StringBuffer sn = new StringBuffer("I");
		// ������
		String typeCode = (String) params.get("type");
		if (StringUtils.isEmpty(typeCode)) {
			typeCode = "E";// ����
		}
		sn.append(typeCode);
		// ����·�
		sn.append(WebUtil.dateToString(new Date(), "yyMM"));
		// ���к�
		sn.append(getCurrentSerialNumber(module, 3));
		return sn.toString();
	}

	// Ŀ¼ѯ��
	private String getQUEMSerialNumber(Map params) {
		// ȡģ�����
		String module = (String) params.get(Constants.SYS_MOD);
		// ѯ�۴���
		StringBuffer sn = new StringBuffer("MI");
		// ������
		String typeCode = (String) params.get("type");
		if (StringUtils.isEmpty(typeCode)) {
			typeCode = "E";// ����
		}
		sn.append(typeCode);
		// ����·�
		sn.append(WebUtil.dateToString(new Date(), "yyMM"));
		// ���к�
		sn.append(getCurrentSerialNumber(module, 3));
		return sn.toString();
	}

	// ����
	private String getORDSerialNumber(Map params) {
		// ȡģ�����
		String module = (String) params.get(Constants.SYS_MOD);
		try {
			bdm = BaseDataManager.getInstance();
		} catch (Exception e) {
		}

		Integer i = null;
		String sn = "O";
		// ��Ŀ������
		i = (Integer) params.get("category");
		if (i != null) {
			bdi = bdm.getValue(i.intValue(), "ZD0003");
			if (bdi != null) {
				sn += bdi.getCode();
			} else {
				sn += "Z";
			}
		}

		// ����
		sn += WebUtil.dateToString(new Date(), "yyMM");

		// ���к�
		sn += getCurrentSerialNumber(module, 3);

		return sn.toString();
	}

	// �ɹ�
	private String getPURSerialNumber(Map params) {
		// ȡģ�����
		String module = (String) params.get(Constants.SYS_MOD);
		String sn = "";
		// �ɹ����
		String category = (String) params.get("category");
		// �ɹ�����
		String type = (String) params.get("type");
		// ��������
		String deptNo = (String) params.get("deptNo");
		// ���
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(new Date()).substring(2, 4);
		sn += category.trim() + type.trim() + deptNo.trim();
		sn += year.trim();
		sn += getCurrentSerialNumber(module, 4).trim();
		return sn.toString();
	}

	// Ŀ¼�ɹ�
	private String getCATESerialNumber(Map params) {
		// ȡģ�����
		String module = (String) params.get(Constants.SYS_MOD);
		String sn = "M";
		// �ɹ����
		String category = (String) params.get("category");
		// �ɹ�����
		String type = (String) params.get("type");
		// ��������
		String deptNo = (String) params.get("deptNo");
		// ���
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(new Date()).substring(2, 4);
		sn += category.trim() + type.trim() + deptNo.trim();
		sn += year.trim();
		sn += getCurrentSerialNumber(module, 4).trim();
		return sn.toString();
	}

	// ����
	private String getREPSerialNumber(Map params) {

		return "";
	}

	// ����
	private String getPAYSerialNumber(Map params) {

		String module = (String) params.get(Constants.SYS_MOD);

		try {
			bdm = BaseDataManager.getInstance();
		} catch (Exception e) {
			logger.info(" baseDataManager is Error!");
		}

		StringBuffer sb = new StringBuffer();

		// ģ�����
		sb.append("P");

		// �ɹ����
		String category = (String) params.get("category");

		if (category != null) {
			bdi = bdm.getValue(Integer.parseInt(category), "ZD0003");
			if (bdi != null) {
				sb.append(bdi.getCode());
			} else {
				sb.append("E");// ����
			}
		}
		// ����
		sb.append(WebUtil.dateToString(new Date(), "yyMMdd"));

		// �Զ����
		sb.append(getCurrentSerialNumber(module, 3));

		return sb.toString();
	}

	// ��Ʒ
	private String getGOODSerialNumber(Map params) {
		// ȡģ�����
		String module = (String) params.get(Constants.SYS_MOD);
		// ��Ʒ����
		StringBuffer sn = new StringBuffer("");
		// ������
		String typeCode = (String) params.get("type");
		if (StringUtils.isEmpty(typeCode)) {
			typeCode = "E";// ����
		}
		sn.append(typeCode);
		// ����·�
		sn.append(WebUtil.dateToString(new Date(), "yyMM"));
		// ���к�
		sn.append(getCurrentSerialNumber(module, 4));
		return sn.toString();
	}

	// Ŀ¼��Ʒ���뵥
	private String getGOODAPPLYSerialNumber(Map params) {
		// ȡģ�����
		String module = (String) params.get(Constants.SYS_MOD);
		// ��Ʒ�������
		StringBuffer sn = new StringBuffer("");
		// ������
		String typeCode = (String) params.get("type");
		sn.append("M");
		if (StringUtils.isEmpty(typeCode)) {
			typeCode = "E";// ����
		}
		if (typeCode != null) {
			sn.append(typeCode);
		}

		// ����·�
		sn.append(WebUtil.dateToString(new Date(), "yyMM"));
		// ���к�
		sn.append(getCurrentSerialNumber(module, 4));
		return sn.toString();
	}

	// Ŀ¼�ɹ�������
	private String getORDMOSerialNumber(Map params) {
		// ȡģ�����
		String module = (String) params.get(Constants.SYS_MOD);
		try {
			bdm = BaseDataManager.getInstance();
		} catch (Exception e) {
		}

		Integer i = null;
		String sn = "MO";
		// ��Ŀ������
		i = (Integer) params.get("category");
		if (i != null) {
			bdi = bdm.getValue(i.intValue(), "ZD0003");
			if (bdi != null) {
				sn += bdi.getCode();
			} else {
				sn += "Z";
			}
		}

		// ����
		sn += WebUtil.dateToString(new Date(), "yyMM");

		// ���к�
		sn += getCurrentSerialNumber(module, 3);

		return sn.toString();
	}

	// Ŀ¼�ɹ�������
	private String getORDMCSerialNumber(Map params) {
		// ȡģ�����
		String module = (String) params.get(Constants.SYS_MOD);
		try {
			bdm = BaseDataManager.getInstance();
		} catch (Exception e) {
		}

		Integer i = null;
		String sn = "MC";
		// ��Ŀ������
		i = (Integer) params.get("category");
		if (i != null) {
			bdi = bdm.getValue(i.intValue(), "ZD0003");
			if (bdi != null) {
				sn += bdi.getCode();
			} else {
				sn += "Z";
			}
		}

		// ����
		sn += WebUtil.dateToString(new Date(), "yyMM");

		// ���к�
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

		// ģ�����
		sb.append("MY");
		// �ɹ����
		String category = (String) params.get("category");

		if (category != null) {
			bdi = bdm.getValue(Integer.parseInt(category), "ZD0003");
			if (bdi != null) {
				sb.append(bdi.getCode());
			} else {
				sb.append("E");// ����
			}
		}
		// ����
		sb.append(WebUtil.dateToString(new Date(), "yyMM"));

		// �Զ����
		sb.append(getCurrentSerialNumber(module, 3));

		return sb.toString();
	}

	// Ŀ¼�ɹ�����
	private String getPAYMSerialNumber(Map params) {

		String module = (String) params.get(Constants.SYS_MOD);

		try {
			bdm = BaseDataManager.getInstance();
		} catch (Exception e) {
			logger.info(" baseDataManager is Error!");
		}

		StringBuffer sb = new StringBuffer();

		// ģ�����
		sb.append("MP");

		// �ɹ����
		String category = (String) params.get("category");

		if (category != null) {
			bdi = bdm.getValue(Integer.parseInt(category), "ZD0003");
			if (bdi != null) {
				sb.append(bdi.getCode());
			} else {
				sb.append("E");// ����
			}
		}
		// ����
		sb.append(WebUtil.dateToString(new Date(), "yyMM"));

		// �Զ����
		sb.append(getCurrentSerialNumber(module, 3));

		return sb.toString();
	}

	/**
	 * �Ե�һ��Ӧ�̵ľ��������Զ����
	 * 
	 * @param params
	 * @return
	 */
	private String getCommendSerialNumber(Map params) {
		String module = (String) params.get(Constants.SYS_MOD);

		StringBuffer sb = new StringBuffer();
		// ģ�����
		sb.append("SSA");
		// ����
		sb.append(WebUtil.dateToString(new Date(), "yyMM"));
		// �Զ����
		sb.append(getCurrentSerialNumber(module, 3));

		return sb.toString();
	}

	public synchronized String getSerialNumber(Map params) {
		// ��ȡģ��No
		String module = (String) params.get(Constants.SYS_MOD);
		// ����
		if (module.equals(Constants.SYS_ABI)) {
			return getABISerialNumber(params);
		}
		// Ŀ¼����
		if (module.equals(Constants.SYS_ABIM)) {
			return getABIMSerialNumber(params);
		}
		// Ԥ��
		if (module.equals(Constants.SYS_BUD)) {
			return getBUDSerialNumber(params);
		}
		// ��ͬ
		if (module.equals(Constants.SYS_CON)) {
			return getCONSerialNumber(params);
		}
		// �ز�
		if (module.equals(Constants.SYS_IMP)) {
			return getIMPSerialNumber(params);
		}
		// ����
		if (module.equals(Constants.SYS_ORD)) {
			return getORDSerialNumber(params);
		}
		// �ɹ�
		if (module.equals(Constants.SYS_PUR)) {
			return getPURSerialNumber(params);
		}
		// ѯ��
		if (module.equals(Constants.SYS_QUE)) {
			return getQUESerialNumber(params);
		}
		// Ŀ¼ѯ��
		if (module.equals(Constants.SYS_QUEM)) {
			return getQUEMSerialNumber(params);
		}
		// ����
		if (module.equals(Constants.SYS_REP)) {
			return getREPSerialNumber(params);
		}
		// ����
		if (module.equals(Constants.SYS_PAY)) {
			return getPAYSerialNumber(params);
		}
		// ��һ��Ӧ�̵ľ�������
		if (module.equals(Constants.SYS_COM)) {
			return getCommendSerialNumber(params);
		}
		// ���ڹ�Ӧ��
		if (module.equals(Constants.SYS_SUP)) {
			return getSUPSerialNumber(params);
		}
		// ��ʱ��Ӧ�� && ѯ�۹�Ӧ��
		if (module.equals(Constants.SYS_TEMPSUP)) {
			return getTEMPSUPSerialNumber(params);
		}
		// ��Ʒ
		if (module.equals(Constants.SYS_GOOD)) {
			return getGOODSerialNumber(params);
		}

		// Ŀ¼�ɹ�������
		if (module.equals(Constants.SYS_ORDMO)) {
			return getORDMOSerialNumber(params);
		}
		// Ŀ¼�ɹ��۸�������
		if (module.equals(Constants.SYS_ORDMC)) {
			return getORDMCSerialNumber(params);
		}
		// Ŀ¼�ɹ����յ�
		if (module.equals(Constants.SYS_CKACM)) {
			return getCKACMSerialNumber(params);
		}
		// Ŀ¼�ɹ�Ӧ����֪ͨ��
		if (module.equals(Constants.SYS_PAYM)) {
			return getPAYMSerialNumber(params);
		}

		if (module.equals(Constants.SYS_CATE)) {
			return getCATESerialNumber(params);
		}
		// Ŀ¼�ɹ���Ʒ���뵥
		if (module.equals(Constants.SYS_GOODAPPLYM)) {
			return getGOODAPPLYSerialNumber(params);
		}
		throw new RuntimeException("�����ڴ�ģ����룬�����������");
	}

	/**
	 * ����ָ��len�����ص�ǰ���кű���
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
	 * ��ȡ��ǰ���к�
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
			// ����Ƿ��ѹ���������
			int intervel = serialNumber.getIntervelNo();
			Calendar lastCal = Calendar.getInstance();
			lastCal.setTime(serialNumber.getLastDate());// 2007-05-15

			Calendar curCal = Calendar.getInstance();
			Date curDate = WebUtil.stringToDate(WebUtil
					.dateToString(new Date()));// ֻ���������յ�����
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
				logger.info("������¼��㣺 retInt = " + retInt);
			}
			retInt = serialNumber.getCurrentSerail();

			// ���к�������
			serialNumber.setCurrentSerail(retInt + 1);
			logger.info("��ţ� retInt = " + retInt);
			logger.info("���������ţ� CurrentSerail = "
					+ serialNumber.getCurrentSerail());
			update(serialNumber);
		}
		return retInt;
	}

	public boolean update(SerialNumber bean) {
		return abstractDao.update(bean);
	}

	private static String getCodeNumber(int category, int area) {
		String categoryCode = ""; // ���ֻ�Ǹ��Լ����ı���
		String areaCode = ""; // ���ֻ�Ǹ��Լ����ı���
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

	// ���ڹ�Ӧ��
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

	// ��ʱ��Ӧ�� && ѯ�۹�Ӧ��
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

	// ͨ��ģ��NO��category��area������SerialNumber�ж�Ӧ��moduleNo���Ӷ���ȡSerialNumber����
	public synchronized List getSupplierSerialNumber(Map params) {
		// ��ȡģ��No
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
		// ��ȡģ��No
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
		Date curDate = WebUtil.stringToDate(WebUtil.dateToString(new Date()));// ֻ���������յ�����
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