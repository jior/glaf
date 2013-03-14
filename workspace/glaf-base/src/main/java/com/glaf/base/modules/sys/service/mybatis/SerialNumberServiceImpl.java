/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.base.modules.sys.service.mybatis;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.Constants;
import com.glaf.base.modules.sys.mapper.SerialNumberMapper;
import com.glaf.base.modules.sys.model.BaseDataInfo;
import com.glaf.base.modules.sys.model.SerialNumber;

import com.glaf.base.modules.sys.query.SerialNumberQuery;
import com.glaf.base.modules.sys.service.*;
import com.glaf.base.utils.WebUtil;
import com.glaf.core.dao.PersistenceDAO;
import com.glaf.core.id.IdGenerator;

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
 */

@Service("serialNumberService")
@Transactional(readOnly = true)
@SuppressWarnings("rawtypes")
public class SerialNumberServiceImpl implements SerialNumberService {

	private static final Log logger = LogFactory
			.getLog(SerialNumberServiceImpl.class);

	public static void main(String arg[]) {
		logger.debug("yyMMdd=" + WebUtil.dateToString(new Date(), "yyMMdd"));

		Calendar lastCal = Calendar.getInstance();
		lastCal.setTime(WebUtil.stringToDate("2007-07-21"));

		Calendar curCal = Calendar.getInstance();
		Date curDate = WebUtil.stringToDate(WebUtil.dateToString(new Date()));// 只包含年月日的日期
		curCal.setTime(curDate);

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
			reCaculate = false;
		}
		logger.debug(reCaculate);
	}

	private BaseDataInfo bdi = null;

	private BaseDataManager bdm = null;

	protected IdGenerator idGenerator;

	protected PersistenceDAO persistenceDAO;

	protected SerialNumberMapper serialNumberMapper;

	protected SqlSessionTemplate sqlSessionTemplate;

	public int count(SerialNumberQuery query) {
		query.ensureInitialized();
		return serialNumberMapper.getSerialNumberCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			serialNumberMapper.deleteSerialNumberById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			SerialNumberQuery query = new SerialNumberQuery();
			query.rowIds(rowIds);
			serialNumberMapper.deleteSerialNumbers(query);
		}
	}

	private String getABIMSerialNumber(Map params) {
		// 取模块代码
		String module = (String) params.get(Constants.SYS_MOD);

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

	private String getABISerialNumber(Map params) {
		// 取模块代码
		String module = (String) params.get(Constants.SYS_MOD);

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

	private String getBUDSerialNumber(Map params) {
		return "";
	}

	private String getCATESerialNumber(Map params) {
		// 取模块代码
		String module = (String) params.get(Constants.SYS_MOD);
		String sn = "M";

		String category = (String) params.get("category");

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

	/**
	 * 获取当前序列号
	 * 
	 * @param module
	 * @return
	 */
	private int getCurrentSerialNumber(String module) {

		int retInt = 1;

		SerialNumberQuery query = new SerialNumberQuery();
		query.moduleNo(module);

		List<SerialNumber> tempList = this.list(query);

		if (tempList != null && tempList.size() > 0) {

			SerialNumber serialNumber = (SerialNumber) tempList.iterator()
					.next();
			// 检测是否已过归零日期
			int intervel = serialNumber.getIntervelNo();
			Calendar lastCal = Calendar.getInstance();
			lastCal.setTime(serialNumber.getLastDate());

			Calendar curCal = Calendar.getInstance();
			Date curDate = WebUtil.stringToDate(WebUtil
					.dateToString(new Date()));// 只包含年月日的日期
			curCal.setTime(curDate);

			boolean reCaculate = true;
			if (intervel == Constants.INTERVEL_1) {
				// lastCal.roll(Calendar.MONTH, 1);
				lastCal.set(Calendar.DAY_OF_MONTH, 1);
				curCal.set(Calendar.DAY_OF_MONTH, 1);
				reCaculate = curCal.after(lastCal);
			} else if (intervel == Constants.INTERVEL_2) {
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
				reCaculate = false;
			}

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

	private String getGOODAPPLYSerialNumber(Map params) {
		// 取模块代码
		String module = (String) params.get(Constants.SYS_MOD);

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

	@SuppressWarnings("unchecked")
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
		SerialNumberQuery query = new SerialNumberQuery();
		query.moduleNo(moduleNo);

		List<SerialNumber> list = this.list(query);

		return list;
	}

	private String getIMPSerialNumber(Map params) {
		// 取模块代码
		String module = (String) params.get(Constants.SYS_MOD);

		try {
			bdm = BaseDataManager.getInstance();
		} catch (Exception e) {
		}

		String sn = "T";// 审批项目
		Calendar date = Calendar.getInstance();
		String year = String.valueOf(date.get(Calendar.YEAR));
		sn += year.substring(2);// 审批年度
		sn += getCurrentSerialNumber(module, 4);// 流水号：4位流水号
		return sn;
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

	// 采购
	private String getPURSerialNumber(Map params) {
		// 取模块代码
		String module = (String) params.get(Constants.SYS_MOD);
		String sn = "";

		String category = (String) params.get("category");

		String type = (String) params.get("type");

		String deptNo = (String) params.get("deptNo");
		// 年份
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(new Date()).substring(2, 4);
		sn += category.trim() + type.trim() + deptNo.trim();
		sn += year.trim();
		sn += getCurrentSerialNumber(module, 4).trim();
		return sn.toString();
	}

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

	private String getREPSerialNumber(Map params) {
		return "";
	}

	public SerialNumber getSerialNumber(Long id) {
		if (id == null) {
			return null;
		}
		SerialNumber serialNumber = serialNumberMapper.getSerialNumberById(id);
		return serialNumber;
	}

	public synchronized String getSerialNumber(Map params) {
		// 获取模块No
		String module = (String) params.get(Constants.SYS_MOD);

		if (module.equals(Constants.SYS_ABI)) {
			return getABISerialNumber(params);
		}

		if (module.equals(Constants.SYS_ABIM)) {
			return getABIMSerialNumber(params);
		}

		if (module.equals(Constants.SYS_BUD)) {
			return getBUDSerialNumber(params);
		}

		if (module.equals(Constants.SYS_CON)) {
			return getCONSerialNumber(params);
		}

		if (module.equals(Constants.SYS_IMP)) {
			return getIMPSerialNumber(params);
		}

		if (module.equals(Constants.SYS_ORD)) {
			return getORDSerialNumber(params);
		}

		if (module.equals(Constants.SYS_PUR)) {
			return getPURSerialNumber(params);
		}

		if (module.equals(Constants.SYS_QUE)) {
			return getQUESerialNumber(params);
		}

		if (module.equals(Constants.SYS_QUEM)) {
			return getQUEMSerialNumber(params);
		}

		if (module.equals(Constants.SYS_REP)) {
			return getREPSerialNumber(params);
		}

		if (module.equals(Constants.SYS_PAY)) {
			return getPAYSerialNumber(params);
		}

		if (module.equals(Constants.SYS_COM)) {
			return getCommendSerialNumber(params);
		}

		if (module.equals(Constants.SYS_GOOD)) {
			return getGOODSerialNumber(params);
		}

		if (module.equals(Constants.SYS_ORDMO)) {
			return getORDMOSerialNumber(params);
		}

		if (module.equals(Constants.SYS_ORDMC)) {
			return getORDMCSerialNumber(params);
		}

		if (module.equals(Constants.SYS_CKACM)) {
			return getCKACMSerialNumber(params);
		}

		if (module.equals(Constants.SYS_PAYM)) {
			return getPAYMSerialNumber(params);
		}

		if (module.equals(Constants.SYS_CATE)) {
			return getCATESerialNumber(params);
		}

		if (module.equals(Constants.SYS_GOODAPPLYM)) {
			return getGOODAPPLYSerialNumber(params);
		}
		throw new RuntimeException("不存在此模块编码，请检查输入代码");
	}

	public int getSerialNumberCountByQueryCriteria(SerialNumberQuery query) {
		return serialNumberMapper.getSerialNumberCount(query);
	}

	public List<SerialNumber> getSerialNumbersByQueryCriteria(int start,
			int pageSize, SerialNumberQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SerialNumber> rows = sqlSessionTemplate.selectList(
				"getSerialNumbers", query, rowBounds);
		return rows;
	}

	// 通过模块NO、category、area来查找SerialNumber中对应的moduleNo，从而获取SerialNumber对象
	@SuppressWarnings("unchecked")
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

		SerialNumberQuery query = new SerialNumberQuery();
		query.moduleNo(moduleNo);

		List<SerialNumber> list = this.list(query);

		return list;
	}

	public List<SerialNumber> list(SerialNumberQuery query) {
		query.ensureInitialized();
		List<SerialNumber> list = serialNumberMapper.getSerialNumbers(query);
		return list;
	}

	@Transactional
	public void save(SerialNumber serialNumber) {
		if (serialNumber.getId() == 0L) {
			serialNumber.setId(idGenerator.nextId());
			// serialNumber.setCreateDate(new Date());
			serialNumberMapper.insertSerialNumber(serialNumber);
		} else {
			serialNumberMapper.updateSerialNumber(serialNumber);
		}
	}

	@Resource
	@Qualifier("myBatisDbIdGenerator")
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@Resource
	public void setPersistenceDAO(PersistenceDAO persistenceDAO) {
		this.persistenceDAO = persistenceDAO;
	}

	@Resource
	public void setSerialNumberMapper(SerialNumberMapper serialNumberMapper) {
		this.serialNumberMapper = serialNumberMapper;
	}

	@Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public boolean update(SerialNumber bean) {
		this.save(bean);
		return true;
	}
}