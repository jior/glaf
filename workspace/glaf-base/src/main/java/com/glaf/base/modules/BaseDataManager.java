package com.glaf.base.modules;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

	private static BaseDataManager instance;

	private static Map baseDataMap = new Hashtable();

	private Map beanMap = new Hashtable();

	private Map serviceMap = new Hashtable();

	public static String SV_NAMES[] = { "sysUserService", // 0：用户Service
			"sysRoleService", // 1：角色Service
			"sysDepartmentService", // 2：部门Service
			"sysTreeService", // 3：目录树Service
			"dictoryService", // 4：字典Service
			"sysFunctionService", // 5：模块功能Service
			"supplierService", // 6：供应商Service
			"sysDeptRoleService", // 7：部门角色Service
			"goodsCategoryService", // 8：采购物品分类Service
			"subjectCodeService" // 9: 费用科目Service
	};

	public Map getServiceMap() {
		return serviceMap;
	}

	/**
	 * 设置服务列表
	 * 
	 * @param map
	 */
	public void setServiceMap(Map map) {
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

	public Map getBeanMap() {
		return beanMap;
	}

	public void setBeanMap(Map beanMap) {
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
	 * @throws Exception
	 */
	public static synchronized BaseDataManager getInstance() {
		logger.info("getInstance");
		if (instance == null) {
			instance = new BaseDataManager();
		}
		return instance;
	}

	/**
	 * 获取某种类型的基础数据
	 * 
	 * @param key
	 * @return
	 */
	public List getBaseData(String key) {
		if (baseDataMap.containsKey(key)) {
			return (List) baseDataMap.get(key);
		}
		return null;
	}

	/**
	 * 根据类型返回对象列表
	 * 
	 * @param key
	 * @return
	 */
	public Iterator getList(String key) {
		List list = getBaseData(key);
		if (list != null) {
			return list.iterator();
		} else {
			return null;
		}
	}
	/**
	 * 根据类型返回对象列表
	 * 
	 * @param  新增 by  happy 2011-8-11
	 * @return
	 */
	public List getList2(String key) {
		List list = getBaseData(key);
		if (list != null) {
			return list;
		} else {
			return null;
		}
	}
	
	/**
	 * add by kxr 2010-10-12
	 * @param valueId
	 * @param key
	 * @return
	 */
	public BaseDataInfo getValue(Long valueId, String key){
		if(valueId != null){
			return getValue(valueId.intValue(), key);
		} 
		
		return null;
	}
	
	/**
	 * add by kxr 2010-10-12
	 * @param valueId
	 * @param key
	 * @return
	 */
	public BaseDataInfo getValue(Integer valueId, String key){
		if(valueId != null){
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
		try {
			Iterator iter = getList(key);
			while (iter != null && iter.hasNext()) {
				BaseDataInfo temp = (BaseDataInfo) iter.next();
				if (temp.getId() == valueId) {
					ret = temp;
					break;
				}
			}
		} catch (Exception e) {
			logger.error(e);
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
		try {
			Iterator iter = getList(key);
			while (iter.hasNext()) {
				BaseDataInfo temp = (BaseDataInfo) iter.next();
				if (temp.getCode().equals(code)) {
					ret = temp;
					break;
				}
			}
		} catch (Exception e) {
			logger.error(e);
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
		try {
			Iterator iter = getList(key);
			while (iter.hasNext()) {
				BaseDataInfo temp = (BaseDataInfo) iter.next();
				if (temp.getName().equals(name)) {
					ret = temp;
					break;
				}
			}
		} catch (Exception e) {
			logger.error(e);
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
		try {
			Iterator iter = getList(key);
			while (iter.hasNext()) {
				BaseDataInfo temp = (BaseDataInfo) iter.next();
				if (temp.getNo().equals(no)) {
					ret = temp;
					break;
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return ret;
	}
	
	/**
	 * 根据编号和类型返回对象名称
	 * author:key
	 * createDate:2010-7-21
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
	 * add by kxr
	 * @param valueId
	 * @param key
	 * @return
	 */
	public String getStringValue(Long valueId, String key) {
		Long v = (valueId == null?Long.valueOf(0):valueId);
		BaseDataInfo obj = getValue(v.intValue(), key);
		if (obj != null) {
			return obj.getName();
		} else {
			return "";
		}
	}
	
	/**
	 * add by kxr 2010-10-12
	 * @param valueId
	 * @param key
	 * @return
	 */
	public String getStringValue(Integer valueId, String key) {
		Integer v = (valueId == null?Integer.valueOf(0):valueId);
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
		if(valueId != null){
			return getWithParentStringValue(valueId.intValue(), key);
		}
		return "";
		
	}
	public String getWithParentStringValue(long valueId, String key) {
		return getWithParentStringValue((int)valueId, key);
	}
	public String getWithParentStringValue(Long valueId, String key) {
		if(valueId != null){
			return getWithParentStringValue(valueId.intValue(), key);
		}
		return "";
	}
	
	/**
	 * 获取上一级科目名称
	 * @param valueId
	 * @param key
	 * @return
	 * @author key 2011-08-04
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
		if(valueId != null){
			return getWithParentValue(valueId.intValue(), key);
		} else {
			return "";
		}
	}
	public String getWithParentValue(long valueId, String key) {
		return getWithParentValue((int)valueId, key);
	}
	public String getWithParentValue(Long valueId, String key) {
		if(valueId != null){
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
		if(valueId != null){
			return getWithParentValue(valueId.intValue(), key);
		} else {
			return "";
		}
	}
	public String getWithParentString(long valueId, String key) {
		return getWithParentValue((int)valueId, key);
	}
	public String getWithParentString(Long valueId, String key) {
		if(valueId != null){
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
		// 预算科目
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
				List list = service.getSysUserList();
				if (list != null) {
					Iterator iter = list.iterator();
					List tmp = new ArrayList();
					while (iter.hasNext()) {
						SysUser bean = (SysUser) iter.next();
						if (bean != null) {
							BaseDataInfo bdi = new BaseDataInfo();
							bdi.setId(bean.getId());// 用户id
							bdi.setName(bean.getName());// 用户名称
							bdi.setCode(bean.getCode());// 用户招聘号
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
				List list = new ArrayList();
				service.getSysTree(list, (int) parent.getId(), 0);

				// 显示所有部门列表
				if (list != null) {
					Iterator iter = list.iterator();
					List tmp = new ArrayList();
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
							//bdi.setParentId((int) tree.getParent());
							SysTree parentCurrTree = service.findById(tree.getParent());
							if(parentCurrTree != null && parent.getId() != parentCurrTree.getId()){// 不等于部门结构,则取部门
								bdi.setParentId((int)parentCurrTree.getDepartment().getId());
							}else{
								bdi.setParentId((int)parent.getParent());
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
				List list = service.getSysFunctionList();
				// 显示所有模块列表
				if (list != null) {
					Iterator iter = list.iterator();
					List tmp = new ArrayList();
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
		String[] treeNode = { "0111", // 投资类型
				// "0113", // 采购类别
				"0114", // 采购性质
				"0115", // 合同性质
				"0116", // 合同类型
				"0117", // 结算币种
				"0118", // 项目类别
				"0119", // 付款方式
				"0120", // 要求收到票据
				"0121", // 款项名称
				"0122", // 行业种类(长期供应产商)
				"0124", // 行业种类(临时供应商)
				"0123", // 地区
				"0125", // 计量单位
				"0126", // 颜色
				"0127", // 职务
				"0128", // 目录采购分类
				"0112", // 合同模板
				"0129", //询价单附件
				"0130",	//费用预算分类
				"0131",  //目录采购结算方式
				"0132",  //车型
				"0133",  //生技内/外
				"0134",  //核算项目(key)
				"0135",  //进项类别(key)(增值税类型)
				"0136",  //应付类别(key)
				"0137",	 //关税(happy)
		};
		String[] mapKeys = { "ZD0002", // 2：投资类型
				// "ZD0003", //3：采购类别
				"ZD0004", // 4：采购性质
				"ZD0005", // 5：合同性质
				"ZD0006", // 6：合同类型
				"ZD0007", // 7：结算币种
				"ZD0008", // 8：项目类别
				"ZD0009", // 9：付款方式
				"ZD0010", // 10：要求收到票据
				"ZD0011", // 11：款项名称
				"ZD0012", // 12：行业种类(长期供应产商)
				"ZD0013", // 13：行业种类(临时供应商)
				"ZD0014", // 14：地区
				"ZD0016", // 16: 记量单位
				"ZD0017", // 17: 颜色
				"ZD0019", // 18: 职务
				"ZD0020", // 19: 目录采购分类
				"ZD0021", // 20: 合同模板
				"ZD0023",  // 22： 询价单附件
				"ZD0024",	//23: 费用预算分类
				"ZD0025",	//24: 目录采购结算方式
				"ZD0026",   //25:车型
				"ZD0027",	//26:生技内/外
				"ZD0028",	//28:核算项目(key)
				"ZD0029",	//29:进项类别(key)(增值税类型)
				"ZD0030",	//30:应付类别(key)
				"ZD0031",	//31:关税(happy)
		};
		try {
			if (serviceMap.containsKey(SV_NAMES[4])) {
				logger.info("装载字典信息开始...");
				for (int i = 0; i < treeNode.length; i++) {
					logger.info("node:" + treeNode[i] + "; key:" + mapKeys[i]);

					SysTreeService treeService = (SysTreeService) serviceMap
							.get(SV_NAMES[3]);
					SysTree parent = treeService.getSysTreeByCode(treeNode[i]);
					DictoryService service = (DictoryService) serviceMap
							.get(SV_NAMES[4]);
					List list = service.getAvailableDictoryList(parent.getId());
					if (list != null) {
						Iterator iter = list.iterator();
						List tmp = new ArrayList();
						while (iter.hasNext()) {
							Dictory bean = (Dictory) iter.next();
							BaseDataInfo bdi = new BaseDataInfo();
							bdi.setId(bean.getId());// 字典id
							bdi.setName(bean.getName());// 字典名称
							bdi.setCode(bean.getCode());// 字典代码
							bdi.setExt1(bean.getExt1());// 扩展字段1(投资汇率)
							bdi.setExt2(bean.getExt2());// 扩展字段2(费用汇率)
							
							bdi.setExt3(bean.getExt3());// 扩展字段1(投资汇率设置人)
							bdi.setExt4(bean.getExt4());// 扩展字段1(费用汇率设置人)
							
							bdi.setExt5(bean.getExt5());// 扩展字段1(投资汇率最后修改时间)
							bdi.setExt6(bean.getExt6());// 扩展字段1(费用汇率最后修改时间)
							bdi.setDeep(0);

							logger.info("id:" + bean.getId() + ",name:"
									+ bean.getName() + ",code:"
									+ bean.getCode());
							tmp.add(bdi);
						}
						baseDataMap.put(mapKeys[i], tmp);
					}
				}
				logger.info("装载字典信息结束");
			}
		} catch (Exception e) {
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
			if (serviceMap.containsKey(SV_NAMES[9])) {
				logger.info("装载科目代码开始...");
				SubjectCodeService service = (SubjectCodeService) serviceMap
						.get(SV_NAMES[9]);
				List list = service.getSubjectCodeList();
				// 显示所有列表
				if (list != null) {
					Iterator iter = list.iterator();
					List tmp = new ArrayList();
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
