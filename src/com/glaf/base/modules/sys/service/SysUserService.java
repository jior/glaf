package com.glaf.base.modules.sys.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import com.glaf.base.dao.AbstractSpringDao;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysDeptRole;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.model.SysUserRole;
import com.glaf.base.utils.PageResult;

public class SysUserService {
	private Log logger = LogFactory.getLog(SysUserService.class);

	private AbstractSpringDao abstractDao;

	public void setAbstractDao(AbstractSpringDao abstractDao) {
		this.abstractDao = abstractDao;
		logger.info("setAbstractDao");
	}

	/**
	 * 保存
	 * 
	 * @param bean
	 *            SysUser
	 * @return boolean
	 */
	public boolean create(SysUser bean) {
		return abstractDao.create(bean);
	}

	/**
	 * 更新
	 * 
	 * @param bean
	 *            SysUser
	 * @return boolean
	 */
	public boolean update(SysUser bean) {

		return abstractDao.update(bean);
	}

	public boolean updateUser(SysUser bean) {

		String sql = " update sys_user set lastLoginTime = ? ,lastLoginIP = ? where account = ? ";
		List values = new ArrayList();
		values.add(bean.getLastLoginTime());
		values.add(bean.getLastLoginIP());
		values.add(bean.getAccount());

		return abstractDao.executeSQL(sql, values);
	}

	/**
	 * 删除
	 * 
	 * @param bean
	 *            SysUser
	 * @return boolean
	 */
	public boolean delete(SysUser bean) {

		return abstractDao.delete(bean);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	public boolean delete(long id) {
		SysUser bean = findById(id);
		if (bean != null) {
			return delete(bean);
		} else {
			return false;
		}
	}

	/**
	 * 批量删除
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteAll(long[] id) {
		List list = new ArrayList();
		for (int i = 0; i < id.length; i++) {
			SysUser bean = findById(id[i]);
			if (bean != null)
				list.add(bean);
		}
		return abstractDao.deleteAll(list);
	}

	/**
	 * 获取对象
	 * 
	 * @param id
	 * @return
	 */
	public SysUser findById(long id) {
		if (id == 0)// add by key 2012-05-21
			return null;
		return (SysUser) abstractDao.find(SysUser.class, new Long(id));
	}

	/**
	 * 按名称查找对象
	 * 
	 * @param name
	 *            String
	 * @return SysUser
	 */
	public SysUser findByAccount(String account) {
		SysUser bean = null;
		Object[] values = new Object[] { account };
		String query = "from SysUser a where a.account=? order by a.id desc";
		List list = abstractDao.getList(query, values, null);
		if (list != null && list.size() > 0) {// 有记录
			bean = (SysUser) list.get(0);
		}

		return bean;
	}

	/**
	 * 按名称查找对象
	 * 
	 * @param name
	 *            String
	 * @return SysUser
	 */
	public SysUser findByAccountWithAll(String account) {
		SysUser bean = null;
		Object[] values = new Object[] { account };
		String query = "from SysUser a where a.account=? order by a.id desc";
		List list = abstractDao.getList(query, values, null);
		if (list != null && list.size() > 0) {// 有记录
			bean = (SysUser) list.get(0);
			if (bean != null) {
				Hibernate.initialize(bean.getApps());
				Hibernate.initialize(bean.getDepartment());
				Hibernate.initialize(bean.getRoles());
				Hibernate.initialize(bean.getUserRoles());
				Hibernate.initialize(bean.getFunctions());
			}
		}

		return bean;
	}

	/**
	 * 获取特定部门的员工数据集 分页列表
	 * 
	 * @param deptId
	 *            int
	 * @param pageNo
	 *            int
	 * @param pageSize
	 *            int
	 * @update by author: zoumin
	 * @update by author: agoo 2008-7-8
	 * @return
	 */
	public PageResult getSysUserList(int deptId, int pageNo, int pageSize) {
		if (deptId == 0) {// 显示所有用户
			// 计算总数
			String query = "select count(*) from SysUser a where a.accountType=0";
			int count = ((Long) abstractDao.getList(query, null, null)
					.iterator().next()).intValue();
			if (count == 0) {// 结果集为空
				PageResult pager = new PageResult();
				pager.setPageSize(pageSize);
				return pager;
			}
			// 查询列表
			query = "select a from SysUser a where a.accountType=0 order by a.id desc";
			return abstractDao.getList(query, null, pageNo, pageSize, count);
		} else {// 按部门显示用户
			// 计算总数
			Object[] values = new Object[] { new Long(deptId) };
			String query = "select count(*) from SysUser a where a.department.id=? ";
			int count = ((Long) abstractDao.getList(query, values, null)
					.iterator().next()).intValue();
			if (count == 0) {// 结果集为空
				PageResult pager = new PageResult();
				pager.setPageSize(pageSize);
				return pager;
			}
			// 查询列表
			query = "select a from SysUser a where a.department.id=? "
					+ "order by a.id desc";
			return abstractDao.getList(query, values, pageNo, pageSize, count);
		}

	}

	/**
	 * 查询获取sysUser列表
	 * 
	 * @param deptId
	 * @param fullName
	 * @param pageNo
	 * @param pageSize
	 * @update by author : zoumin ※显示系统用户结果集（即非供应商）
	 * @return
	 */
	public PageResult getSysUserList(int deptId, String fullName, int pageNo,
			int pageSize) {
		String query = "";
		List arrayList = new ArrayList();
		if (deptId == 0) {

			if (fullName == null || "".equals(fullName)) {
				query = "select count(*) from SysUser a where a.accountType=0";
				int count = ((Long) abstractDao.getList(query, null, null)
						.iterator().next()).intValue();
				if (count == 0) {// 结果集为空
					PageResult pager = new PageResult();
					pager.setPageSize(pageSize);
					return pager;
				}
				query = "select a from SysUser a where a.accountType=0 order by a.id desc";
				return abstractDao
						.getList(query, null, pageNo, pageSize, count);
			} else {

				Object[] values = new Object[] { new String("%" + fullName
						+ "%") };
				query = " select count(*) from SysUser a where a.name like ? and a.accountType=0";
				List list = abstractDao.getList(query, values, null);

				if (list == null) {// 结果集为空
					PageResult pager = new PageResult();
					pager.setPageSize(pageSize);
					return pager;
				}
				int count = list.size();
				query = "select a from SysUser a where a.name like ? and a.accountType=0";
				return abstractDao.getList(query, values, pageNo, pageSize,
						count);
			}
		} else {
			query = "select a from SysUser a where a.department.id=? and a.accountType=0";
			arrayList.add(new Long(deptId));

			if (fullName != null && !"".equals(fullName)) {
				query += "and a.name like ?";
				arrayList.add("%" + fullName + "%");
			}
			Object[] values = arrayList.toArray();
			List list = abstractDao.getList(query, values, null);
			if (list == null) {// 结果集为空
				PageResult pager = new PageResult();
				pager.setPageSize(pageSize);
				return pager;
			}
			int count = list.size();
			logger.info(query);
			logger.info("count = " + count);
			return abstractDao.getList(query, values, pageNo, pageSize, count);
		}

	}

	// public PageResult getSysUserList(Map params, int pageNo, int pageSize) {
	//
	// DetachedCriteria detachedCriteria = WebUtil.getCriteria(params,
	// Supplier.class);
	// detachedCriteria.addOrder(Order.desc("id"));
	// return abstractDao.getList(detachedCriteria, pageNo, pageSize);
	// }

	/**
	 * 获取列表
	 * 
	 * @param deptId
	 *            int
	 * @return List
	 */
	public List getSysUserList(int deptId) {
		// 计算总数
		Object[] values = new Object[] { new Long(deptId) };
		String query = "from SysUser a where a.department.id=? order by a.id desc";
		return abstractDao.getList(query, values, null);
	}

	/**
	 * 获取列表
	 * 
	 * @param deptId
	 *            int
	 * @return List
	 */
	public List getSysUserList() {
		// 计算总数
		String query = "from SysUser a order by a.id desc";
		return abstractDao.getList(query, null, null);
	}

	/**
	 * 获取列表
	 * 
	 * @param deptId
	 *            int
	 * @return List
	 */
	public List getSysUserWithDeptList() {
		// 计算总数
		String query = "from SysUser a order by a.id desc";
		List rows = abstractDao.getList(query, null, null);
		Iterator iterator = rows.iterator();
		while (iterator.hasNext()) {
			SysUser user = (SysUser) iterator.next();
			SysDepartment dept = user.getDepartment();
			if (dept == null) {
				Hibernate.initialize(user.getDepartment());
			}
		}
		return rows;
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	public Set getUserRoles(SysUser user) {
		Set set = new HashSet();
		Object[] values = new Object[] { user };
		String query = "from SysUserRole a where a.user=?";
		List rows = abstractDao.getList(query, values, null);
		Iterator iter = rows.iterator();
		while (iter.hasNext()) {
			SysUserRole bean = (SysUserRole) iter.next();
			set.add(bean.getDeptRole());
		}
		return set;
	}

	// public Set getRoleFunctions(SysDeptRole role){
	// Set set = new HashSet();
	// Object[] values = new Object[] {role};
	// String query = "from SysUserRole a where a.user=?";
	// List rows = abstractDao.getList(query, values, null);
	// Iterator iter = rows.iterator();
	// while(iter.hasNext()){
	// SysUserRole bean = (SysUserRole)iter.next();
	// set.add(bean.getDeptRole());
	// }
	// return set;
	// }
	// public Set getRoleApps(SysDeptRole role){
	// Set set = new HashSet();
	// Object[] values = new Object[] {role};
	// String query = "from SysUserRole a where a.user=?";
	// List rows = abstractDao.getList(query, values, null);
	// Iterator iter = rows.iterator();
	// while(iter.hasNext()){
	// SysUserRole bean = (SysUserRole)iter.next();
	// set.add(bean.getDeptRole());
	// }
	// return set;
	// }
	/**
	 * 其用户权限
	 * 
	 * @param user
	 * @return
	 */
	public SysUser getUserPrivileges(SysUser user) {
		SysUser bean = user;

		try {
			bean.setRoles(getUserRoles(bean));
			Iterator roles = bean.getRoles().iterator();
			while (roles.hasNext()) {
				SysDeptRole role = (SysDeptRole) roles.next();
				Set functions = role.getFunctions();
				Set apps = role.getApps();

				bean.getFunctions().addAll(functions);
				bean.getApps().addAll(apps);
			}

		} catch (Exception e) {
			logger.error(e);
		}
		return bean;
	}

	public SysUser getUserAndPrivileges(SysUser user) {
		SysUser bean = user;
		try {
			bean.setRoles(getUserRoles(bean));
			Iterator roles = bean.getRoles().iterator();
			while (roles.hasNext()) {
				SysDeptRole role = (SysDeptRole) roles.next();
				Set functions = role.getFunctions();
				Set apps = role.getApps();
				bean.getFunctions().addAll(functions);
				bean.getApps().addAll(apps);
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return bean;
	}

	/**
	 * 查找供应商用户 flag = true 表示该用户存在，否则为不存在
	 * 
	 * @param supplierNo
	 * @return
	 */
	public List getSupplierUser(String supplierNo) {
		Object[] values = new Object[] { supplierNo };
		String query = "from SysUser s where s.account=?";
		return abstractDao.getList(query, values, null);
	}

	/**
	 * 设置用户权限
	 * 
	 * @param user
	 *            系统用户
	 * @param delRoles
	 *            要删除的用户权限
	 * @param newRoles
	 *            要增加的用户权限
	 */
	public boolean updateRole(SysUser user, Set delRoles, Set newRoles) {
		boolean flag = false;
		// 删除要删除的权限
		user.getRoles().removeAll(delRoles);
		// 增加新权限
		if (newRoles != null && !newRoles.isEmpty()) {
			long ts = System.currentTimeMillis();
			Iterator iter = newRoles.iterator();
			while (iter.hasNext()) {
				Object obj = iter.next();
				if (obj instanceof SysUserRole) {
					SysUserRole r = (SysUserRole) iter.next();
					if (r.getId() == 0) {
						r.setId(ts++);
					}
				}
			}
			user.getRoles().addAll(newRoles);
		}

		flag = update(user);
		return flag;
	}

	/**
	 * 获取列表
	 * 
	 * @author key
	 * @date Mar 2, 2012
	 */
	public PageResult getSysUserList(int deptId, String userName,
			String account, int pageNo, int pageSize) {
		String query = "from SysUser a where 1=1 ";
		if (deptId != 0) {
			query += " and a.department.id=" + deptId;
		}
		if (null != userName && userName.trim().length() > 0) {
			query += " and a.name like '%" + userName + "%'";
		}
		if (null != account && account.trim().length() > 0) {
			query += " and a.account like '%" + account + "%'";
		}
		int count = abstractDao.getResutlTotalByQuery("select count(*) "
				+ query, null, null);
		query += " order by a.id,a.department.id";
		PageResult pager = abstractDao.getList(query, null, pageNo, pageSize,
				count);
		return pager;
	}

	/**
	 * 初始化所有用户密码进行加密
	 * 
	 * @author key
	 * @date Mar 2, 2012
	 */
	public void updateSysUserPassword() {
		String query = "from SysUser a where 1=1 ";
		List list = abstractDao.getList(query, null, null);
		System.out.println("size=" + list.size());
		for (int i = 0; i < list.size(); i++) {
			SysUser user = (SysUser) list.get(i);
			// user.setPassword(CryptUtil.EnCryptPassword(user.getPassword()));
			// System.out.println(user.getName()+","+user.getPassword()+","+CryptUtil.EnCryptPassword(user.getPassword()));
			abstractDao.update(user);
		}
	}

	// add by happy step2.0 判断角色
	public boolean isThisPlayer(SysUser user, String code) {
		boolean flag = false;
		Set set = user.getRoles();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			SysDeptRole s = (SysDeptRole) it.next();
			if (s.getRole() != null && code.equals(s.getRole().getCode())) {// 代判断用户是否拥有此角色
				flag = true;
				break;
			}
		}

		return flag;
	}
	// /**
	// * 获取上一级领导
	// * @param user
	// * @return List
	// */
	// public List getLeader(SysUser user){
	//
	// }
	// /**
	// * 获取上级领导，递归查询
	// * @return
	// */
	// public List getAllLeader(SysUser user){
	//
	// }
}
