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

package com.glaf.base.modules;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.BaseDataInfo;
import com.glaf.base.modules.sys.model.Dictory;
import com.glaf.base.modules.sys.model.SubjectCode;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysFunction;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.DictoryService;
import com.glaf.base.modules.sys.service.SubjectCodeService;
import com.glaf.base.modules.sys.service.SysFunctionService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.service.SysUserService;

public class BaseDataManager {
	private final static Log logger = LogFactory.getLog(BaseDataManager.class);

	private static BaseDataManager instance = new BaseDataManager();

	private static Map<String, List<BaseDataInfo>> baseDataMap = new Hashtable<String, List<BaseDataInfo>>();

	private Map<String, Object> beanMap = new Hashtable<String, Object>();

	private Map<String, Object> serviceMap = new Hashtable<String, Object>();

	public static String SV_NAMES[] = { "sysUserService", // 用户Service
			"sysRoleService", // 角色Service
			"sysDepartmentService", // 部门Service
			"sysTreeService", // 目录树Service
			"dictoryService", // 字典Service
			"sysFunctionService", // 模块功能Service
			"sysDeptRoleService", // 部门角色Service
			"subjectCodeService" // 费用科目Service
	};

	public Map<String, Object> getServiceMap() {
		return serviceMap;
	}

	/**
	 * 设置服务列表
	 * 
	 * @param map
	 */
	public void setServiceMap(Map<String, Object> map) {
		this.serviceMap = map;
		logger.info("service size:" + serviceMap.size());
	}

	/**
	 * 根据bean编号获取bean
	 * 
	 * @param beanId
	 * @return
	 */
	public Object getBean(String beanId) {
		if (beanMap != null) {
			return beanMap.get(beanId);
		}
		return null;
	}

	public Map<String, Object> getBeanMap() {
		return beanMap;
	}

	public void setBeanMap(Map<String, Object> beanMap) {
		this.beanMap = beanMap;
	}

	/**
	 * 获取服务对象
	 * 
	 * @param key
	 */
	public Object getService(int key) {
		if (serviceMap.containsKey(SV_NAMES[key])) {
			return serviceMap.get(SV_NAMES[key]);
		}
		return null;
	}

	/**
	 * 单例模式
	 * 
	 * @return
	 */
	public static BaseDataManager getInstance() {
		logger.debug("BaseDataManager.getInstance");
		return instance;
	}

	/**
	 * 获取某种类型的基础数据
	 * 
	 * @param key
	 * @return
	 */
	public List<BaseDataInfo> getBaseData(String key) {
		if (baseDataMap.containsKey(key)) {
			return (List<BaseDataInfo>) baseDataMap.get(key);
		}
		return null;
	}

	/**
	 * 根据类型返回对象列表
	 * 
	 * @param key
	 * @return
	 */
	public Iterator<BaseDataInfo> getList(String key) {
		List<BaseDataInfo> list = getBaseData(key);
		if (list != null) {
			return list.iterator();
		} else {
			return null;
		}
	}

	/**
	 * 根据类型返回对象列表
	 * 
	 * @param 新增
	 * 
	 * @return
	 */
	public List<BaseDataInfo> getList2(String key) {
		List<BaseDataInfo> list = getBaseData(key);
		if (list != null) {
			return list;
		} else {
			return null;
		}
	}

	/**
	 * 
	 * 
	 * @param valueId
	 * @param key
	 * @return
	 */
	public BaseDataInfo getValue(Long valueId, String key) {
		if (valueId != null) {
			return getValue(valueId.intValue(), key);
		}

		return null;
	}

	/**
	 * 
	 * 
	 * @param valueId
	 * @param key
	 * @return
	 */
	public BaseDataInfo getValue(Integer valueId, String key) {
		if (valueId != null) {
			return getValue(valueId.intValue(), key);
		}

		return null;
	}

	/**
	 * 根据数据对象id和类型返回对象
	 * 
	 * @param valueId
	 * @param key
	 * @return
	 */
	public BaseDataInfo getValue(long valueId, String key) {
		return getValue((int) valueId, key);
	}

	/**
	 * 根据数据对象id和类型返回对象
	 * 
	 * @param valueId
	 * @param key
	 * @return
	 */
	public BaseDataInfo getValue(int valueId, String key) {
		BaseDataInfo ret = null;

		Iterator<BaseDataInfo> iter = getList(key);
		while (iter != null && iter.hasNext()) {
			BaseDataInfo temp = (BaseDataInfo) iter.next();
			if (temp.getId() == valueId) {
				ret = temp;
				break;
			}
		}

		return ret;
	}

	/**
	 * 根据数据对象code和类型返回对象
	 * 
	 * @param valueId
	 * @param key
	 * @return
	 */
	public BaseDataInfo getValue(String code, String key) {
		BaseDataInfo ret = null;

		Iterator<BaseDataInfo> iter = getList(key);
		while (iter.hasNext()) {
			BaseDataInfo temp = (BaseDataInfo) iter.next();
			if (StringUtils.equals(temp.getCode(), code)) {
				ret = temp;
				break;
			}
		}

		return ret;
	}

	/**
	 * 根据数据对象name和类型返回对象
	 * 
	 * @param value
	 * @param key
	 * @return
	 */
	public BaseDataInfo getBaseData(String name, String key) {
		BaseDataInfo ret = null;

		Iterator<BaseDataInfo> iter = getList(key);
		while (iter.hasNext()) {
			BaseDataInfo temp = (BaseDataInfo) iter.next();
			if (StringUtils.equals(temp.getName(), name)) {
				ret = temp;
				break;
			}
		}

		return ret;
	}

	/**
	 * 根据数据对象no和类型返回对象
	 * 
	 * @param value
	 * @param key
	 * @return
	 */
	public BaseDataInfo getBaseDataWithNo(String no, String key) {
		BaseDataInfo ret = null;

		Iterator<BaseDataInfo> iter = getList(key);
		while (iter.hasNext()) {
			BaseDataInfo temp = (BaseDataInfo) iter.next();
			if (StringUtils.equals(temp.getNo(), no)) {
				ret = temp;
				break;
			}
		}

		return ret;
	}

	/**
	 * 根据编号和类型返回对象名称
	 * 
	 * @param no
	 * @param key
	 * @return
	 */
	public String getStringValueByNo(String no, String key) {
		BaseDataInfo obj = getBaseDataWithNo(no, key);
		if (obj != null) {
			return obj.getName();
		} else {
			return "";
		}
	}

	/**
	 * 根据数据对象id和类型返回对象名称
	 * 
	 * @param valueId
	 * @param key
	 * @return
	 */
	public String getStringValue(long valueId, String key) {
		return getStringValue((int) valueId, key);
	}

	/**
	 * 根据数据对象id和类型返回对象名称
	 * 
	 * @param valueId
	 * @param key
	 * @return
	 */
	public String getStringValue(int valueId, String key) {
		BaseDataInfo obj = getValue(valueId, key);
		if (obj != null) {
			return obj.getName();
		} else {
			return "";
		}
	}

	/**
	 * 
	 * 
	 * @param valueId
	 * @param key
	 * @return
	 */
	public String getStringValue(Long valueId, String key) {
		Long v = (valueId == null ? Long.valueOf(0) : valueId);
		BaseDataInfo obj = getValue(v.intValue(), key);
		if (obj != null) {
			return obj.getName();
		} else {
			return "";
		}
	}

	/**
	 * 
	 * 
	 * @param valueId
	 * @param key
	 * @return
	 */
	public String getStringValue(Integer valueId, String key) {
		Integer v = (valueId == null ? Integer.valueOf(0) : valueId);
		BaseDataInfo obj = getValue(v.intValue(), key);
		if (obj != null) {
			return obj.getName();
		} else {
			return "";
		}
	}

	/**
	 * 根据数据对象id和类型返回对象名称
	 * 
	 * @param valueId
	 * @param key
	 * @return
	 */
	public String getStringValue(String code, String key) {
		BaseDataInfo obj = getValue(code, key);
		if (obj != null) {
			return obj.getName();
		} else {
			return "";
		}
	}

	/**
	 * 获取上一级科目
	 * 
	 * @param valueId
	 * @param key
	 * @return
	 */
	public BaseDataInfo getParentSubjectValue(long valueId, String key) {
		BaseDataInfo bdi = getValue((int) valueId, key);
		if (bdi != null) {
			BaseDataInfo bdi2 = getValue(bdi.getParentId(), key);
			if (bdi2 != null) {
				bdi = bdi2;
			}
		}
		return bdi;
	}

	/**
	 * 获取上一级科目
	 * 
	 * @param valueId
	 * @param key
	 * @return
	 */
	public String getWithParentStringValue(int valueId, String key) {
		String str = "";
		BaseDataInfo bdi = getValue(valueId, key);
		str = bdi == null ? "" : bdi.getName();
		if (bdi != null) {
			BaseDataInfo bdi2 = getValue(bdi.getParentId(), key);
			if (bdi2 != null) {
				bdi = bdi2;
				str = bdi2.getName() + "\\" + str;
			}
		}
		return str;
	}

	public String getWithParentStringValue(Integer valueId, String key) {
		if (valueId != null) {
			return getWithParentStringValue(valueId.intValue(), key);
		}
		return "";

	}

	public String getWithParentStringValue(long valueId, String key) {
		return getWithParentStringValue((int) valueId, key);
	}

	public String getWithParentStringValue(Long valueId, String key) {
		if (valueId != null) {
			return getWithParentStringValue(valueId.intValue(), key);
		}
		return "";
	}

	/**
	 * 获取上一级科目名称
	 * 
	 * @param valueId
	 * @param key
	 * @return
	 */
	public String getParentName(long valueId, String key) {
		String str = "";
		BaseDataInfo bdi = getValue(valueId, key);
		if (bdi != null) {
			BaseDataInfo bdi2 = getValue(bdi.getParentId(), key);
			if (bdi2 != null) {
				str = bdi2.getName();
			}
		}
		return str;
	}

	/**
	 * 根据数据对象id和类型返回根对象
	 * 
	 * @param valueId
	 * @param key
	 * @return
	 */
	public BaseDataInfo getParentValue(int valueId, String key) {
		BaseDataInfo ret = getValue(valueId, key);
		if (ret != null && ret.getParentId() != 0
				&& valueId != ret.getParentId()) {
			ret = getParentValue(ret.getParentId(), key);
		}
		return ret;
	}

	/**
	 * 根据数据对象id和类型返回根对象名称
	 * 
	 * @param valueId
	 * @param key
	 * @return
	 */
	public String getParentStringValue(int valueId, String key) {
		BaseDataInfo ret = getParentValue(valueId, key);
		return ret == null ? "" : ret.getName();
	}

	/**
	 * 根据数据对象id和类型返回对象详细目录名称（包含父信息）
	 * 
	 * @param valueId
	 * @param key
	 * @return
	 */
	public String getWithParentValue(int valueId, String key) {
		BaseDataInfo ret = getValue(valueId, key);
		String s = ret == null ? "" : ret.getName();
		if (ret != null && ret.getParentId() != 0
				&& valueId != ret.getParentId()) {
			s = getWithParentValue(ret.getParentId(), key) + "\\" + s;
		}
		return s;
	}

	public String getWithParentValue(Integer valueId, String key) {
		if (valueId != null) {
			return getWithParentValue(valueId.intValue(), key);
		} else {
			return "";
		}
	}

	public String getWithParentValue(long valueId, String key) {
		return getWithParentValue((int) valueId, key);
	}

	public String getWithParentValue(Long valueId, String key) {
		if (valueId != null) {
			return getWithParentValue(valueId.intValue(), key);
		} else {
			return "";
		}
	}

	/**
	 * 根据数据对象id和类型返回对象详细目录名称（包含父信息,中间用省略号）
	 * 
	 * @param valueId
	 * @param key
	 * @return
	 */
	public String getWithParentString(int valueId, String key) {
		String rst = getWithParentValue(valueId, key);
		rst = rst.replaceAll("([^\\\\]*\\\\)(.*)(\\\\[^\\\\]*)", "$1...$3");
		return rst;
	}

	public String getWithParentString(Integer valueId, String key) {
		if (valueId != null) {
			return getWithParentValue(valueId.intValue(), key);
		} else {
			return "";
		}
	}

	public String getWithParentString(long valueId, String key) {
		return getWithParentValue((int) valueId, key);
	}

	public String getWithParentString(Long valueId, String key) {
		if (valueId != null) {
			return getWithParentValue(valueId.intValue(), key);
		} else {
			return "";
		}
	}

	/**
	 * 刷新基础信息数据（有基础信息变更时调用）
	 * 
	 */
	public void refreshBaseData() {
		initBaseData();
	}

	/**
	 * 初始化内存中基础数据
	 * 
	 */
	private void initBaseData() {
		// 用户信息
		loadUsers();
		// 部门结构
		loadDepartments();
		// 模块功能
		loadFunctions();
		// 数据字典
		loadDictInfo();
		// 科目代码
		loadSubjectCode();
	}

	/**
	 * 装载用户信息
	 */
	private void loadUsers() {
		try {
			if (serviceMap.containsKey(SV_NAMES[0])) {
				logger.info("装载用户信息开始...");
				SysUserService service = (SysUserService) serviceMap
						.get(SV_NAMES[0]);
				List<SysUser> list = service.getSysUserList();
				if (list != null) {
					Iterator<SysUser> iter = list.iterator();
					List<BaseDataInfo> tmp = new ArrayList<BaseDataInfo>();
					while (iter.hasNext()) {
						SysUser bean = (SysUser) iter.next();
						if (bean != null) {
							BaseDataInfo bdi = new BaseDataInfo();
							bdi.setId(bean.getId());// 用户id
							bdi.setName(bean.getName());// 用户名称
							bdi.setCode(bean.getCode());// 用户招聘号
							bdi.setValue(bean.getAccount());
							bdi.setExt1(bean.getTelephone());// 用户电话
							logger.info("id:" + bean.getId() + ",name:"
									+ bean.getName() + ",telephone:"
									+ bean.getTelephone());
							tmp.add(bdi);
						}
					}
					baseDataMap.put(Constants.BD_KEYS[0], tmp);
				}
				logger.info("装载用户信息结束");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("提取用户信息失败！");
		}
	}

	/**
	 * 装载部门信息
	 */
	private void loadDepartments() {
		try {
			if (serviceMap.containsKey(SV_NAMES[2])) {
				logger.info("装载部门信息开始...");
				SysTreeService service = (SysTreeService) serviceMap
						.get(SV_NAMES[3]);
				SysTree parent = service
						.getSysTreeByCode(SysConstants.TREE_DEPT);
				List<SysTree> list = new ArrayList<SysTree>();
				service.getSysTree(list, (int) parent.getId(), 0);

				// 显示所有部门列表
				if (list != null) {
					Iterator<SysTree> iter = list.iterator();
					List<BaseDataInfo> tmp = new ArrayList<BaseDataInfo>();
					while (iter.hasNext()) {
						SysTree tree = (SysTree) iter.next();
						SysDepartment bean = tree.getDepartment();
						if (bean != null) {
							BaseDataInfo bdi = new BaseDataInfo();
							bdi.setId(bean.getId());// 部门id
							bdi.setName(bean.getName());// 部门名称
							bdi.setCode(bean.getCode());// 部门代码
							bdi.setNo(bean.getNo());// 部门编号
							bdi.setDeep(tree.getDeep());
							// bdi.setParentId((int) tree.getParent());
							SysTree parentTree = service.findById(tree
									.getParentId());
							if (parentTree != null
									&& parentTree.getDepartment() != null
									&& parent.getId() != parentTree.getId()) {// 不等于部门结构,则取部门
								bdi.setParentId((int) parentTree
										.getDepartment().getId());
							} else {
								bdi.setParentId((int) parent.getParentId());
							}

							logger.info("id:" + bean.getId() + ",name:"
									+ bean.getName() + "&no:" + bean.getNo());
							tmp.add(bdi);
						}
					}
					baseDataMap.put(Constants.BD_KEYS[1], tmp);
				}
				logger.info("装载部门信息结束");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("提取部门数据失败！");
		}
	}

	/**
	 * 装载模块信息
	 */
	public void loadFunctions() {
		try {
			if (serviceMap.containsKey(SV_NAMES[5])) {
				logger.info("装载模块信息开始...");
				SysFunctionService service = (SysFunctionService) serviceMap
						.get(SV_NAMES[5]);
				List<SysFunction> list = service.getSysFunctionList();
				// 显示所有模块列表
				if (list != null && !list.isEmpty()) {
					Iterator<SysFunction> iter = list.iterator();
					List<BaseDataInfo> tmp = new ArrayList<BaseDataInfo>();
					while (iter.hasNext()) {
						SysFunction bean = (SysFunction) iter.next();
						if (bean != null) {
							BaseDataInfo bdi = new BaseDataInfo();
							bdi.setId(bean.getId());// 模块id
							bdi.setName(bean.getName());// 模块名称
							bdi.setCode(bean.getFuncMethod());// 模块方法
							logger.info("id:" + bean.getId() + ",name:"
									+ bean.getName() + ", method:"
									+ bean.getFuncMethod());
							tmp.add(bdi);
						}
					}
					baseDataMap.put(Constants.BD_KEYS[15], tmp);
				}
				logger.info("装载模块信息结束");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("提取模块数据失败！");
		}
	}

	/**
	 * 装载字典信息
	 */
	public void loadDictInfo() {
		try {
			if (serviceMap.containsKey(SV_NAMES[4])) {
				logger.info("装载字典信息开始...");
				DictoryService dictoryService = (DictoryService) serviceMap
						.get(SV_NAMES[4]);
				List<SysTree> trees = dictoryService.getAllCategories();
				for (int i = 0; i < trees.size(); i++) {
					SysTree treeNode = trees.get(i);
					if (treeNode != null) {
						List<Dictory> list = dictoryService
								.getAvailableDictoryList(treeNode.getId());
						if (list != null && !list.isEmpty()) {
							Iterator<Dictory> iter = list.iterator();
							List<BaseDataInfo> tmp = new ArrayList<BaseDataInfo>();
							while (iter.hasNext()) {
								Dictory bean = (Dictory) iter.next();
								BaseDataInfo bdi = new BaseDataInfo();
								bdi.setId(bean.getId());// 字典id
								bdi.setName(bean.getName());// 字典名称
								bdi.setCode(bean.getCode());// 字典代码
								bdi.setValue(bean.getValue());// 字典代码
								bdi.setExt1(bean.getExt1());// 扩展字段1(投资汇率)
								bdi.setExt2(bean.getExt2());// 扩展字段2(费用汇率)

								bdi.setExt3(bean.getExt3());// 扩展字段1(投资汇率设置人)
								bdi.setExt4(bean.getExt4());// 扩展字段1(费用汇率设置人)

								bdi.setExt5(bean.getExt5());// 扩展字段1(投资汇率最后修改时间)
								bdi.setExt6(bean.getExt6());// 扩展字段1(费用汇率最后修改时间)
								bdi.setDeep(0);

								logger.info("id:" + bean.getId() + ",name:"
										+ bean.getName() + ",code:"
										+ bean.getCode() + ",value:"
										+ bean.getValue());
								tmp.add(bdi);
							}
							baseDataMap.put(treeNode.getCode(), tmp);
						}
					}
				}
				logger.info("装载字典信息结束.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("提取字典数据失败！");
		}
	}

	/**
	 * 
	 * 装载预算科目信息
	 * 
	 */
	public void loadSubjectCode() {
		try {
			if (serviceMap.containsKey(SV_NAMES[7])) {
				logger.info("装载科目代码开始...");
				SubjectCodeService service = (SubjectCodeService) serviceMap
						.get(SV_NAMES[7]);
				List<SubjectCode> list = service.getSubjectCodeList();
				// 显示所有列表
				if (list != null) {
					Iterator<SubjectCode> iter = list.iterator();
					List<BaseDataInfo> tmp = new ArrayList<BaseDataInfo>();
					while (iter.hasNext()) {
						SubjectCode bean = (SubjectCode) iter.next();
						if (bean != null) {
							BaseDataInfo bdi = new BaseDataInfo();
							bdi.setId(bean.getId());// 分类id
							bdi.setName(bean.getSubjectName());// 分类名称
							String code = bean.getSubjectCode();
							code = code.substring(code.indexOf(".") + 1);
							bdi.setCode(code);// 分类编号
							logger.info("id:" + bean.getId() + ",SubjectName:"
									+ bean.getSubjectName() + ", SubjectCode:"
									+ code);
							tmp.add(bdi);
						}
					}
					baseDataMap.put(Constants.BD_KEYS[22], tmp);
				}
				logger.info("装载科目代码信息结束");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("提取科目代码数据失败！");
		}
	}

}