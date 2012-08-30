package com.glaf.base.modules.sys.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.jpage.util.UUID32;

import com.glaf.base.dao.AbstractSpringDao;
import com.glaf.base.modules.Constants;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.model.SysUserRole;
import com.glaf.base.modules.utils.BaseUtil;
import com.glaf.base.utils.PageResult;

public class SysUserRoleServiceImpl implements SysUserRoleService {
	private static final Log logger = LogFactory
			.getLog(SysUserRoleServiceImpl.class);
	private AbstractSpringDao abstractDao;
	private SysDepartmentService sysDepartmentService;
	private SysUserService sysUserService;

	public void setAbstractDao(AbstractSpringDao abstractDao) {
		this.abstractDao = abstractDao;
		logger.info("setAbstractDao");
	}

	public void setSysDepartmentService(
			SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	/**
	 * 保存
	 * 
	 * @param bean
	 *            SysUserRole
	 * @return boolean
	 */
	public boolean create(SysUserRole bean) {
		if (bean.getId() == 0) {
			bean.setId(System.currentTimeMillis());
		}
		return abstractDao.create(bean);
	}

	/**
	 * 更新
	 * 
	 * @param bean
	 *            SysUserRole
	 * @return boolean
	 */
	public boolean update(SysUserRole bean) {
		return abstractDao.update(bean);
	}

	/**
	 * 删除
	 * 
	 * @param bean
	 *            SysUserRole
	 * @return boolean
	 */
	public boolean delete(SysUserRole bean) {
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
		SysUserRole bean = findById(id);
		if (bean != null) {
			return delete(bean);
		} else {
			return false;
		}
	}

	/**
	 * 获取对象
	 * 
	 * @param id
	 *            long
	 * @return
	 */
	public SysUserRole findById(long id) {
		return (SysUserRole) abstractDao.find(SysUserRole.class, new Long(id));
	}

	/**
	 * 判断是否已经授权了
	 * 
	 * @param fromUserId
	 *            long
	 * @param toUserId
	 *            long
	 * @return
	 */
	public boolean isAuthorized(long fromUserId, long toUserId) {
		Object[] values = new Object[] { new Long(toUserId),
				new Long(fromUserId) };
		String query = "select count(*) from SysUserRole a where a.user.id=? and a.authorizeFrom.id=?";
		int count = ((Long) abstractDao.getList(query, values, null).iterator()
				.next()).intValue();
		logger.info("count:" + count + ",fromUserId:" + fromUserId
				+ ",toUserId:" + toUserId);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 某人已授权的用户列表
	 * 
	 * @param user
	 *            SysUser
	 * @return
	 */
	public List getAuthorizedUser(SysUser user) {
		Object[] values = new Object[] { user };
		String query = "select distinct a.user,a.availDateStart,a.availDateEnd,a.processDescription from SysUserRole a where a.authorizeFrom=?";
		return abstractDao.getList(query, values, null);
	}

	/**
	 * 获取用户所有的审批工作流
	 */
	public List getProcessByUser(SysUser user) {
		Object[] values = new Object[] { user };
		String query = "select distinct t.moduleName,t.processName,t.objectValue from ToDo t where t.type='1' and t.processName<>'finance_process' and t.roleId in "
				+ " (select sdr.role.id from SysDeptRole sdr where sdr.id in "
				+ " (select sur.deptRole.id from SysUserRole sur where sur.user=?))";
		return abstractDao.getList(query, values, null);
	}

	/**
	 * 取本部门下的未授权用户列表（除了自己、已授权用户）
	 * 
	 * @param user
	 *            SysUser
	 * @return
	 */
	public List getUnAuthorizedUser(SysUser user) {
		if (user == null)
			return new ArrayList();
		logger.info("name:" + user.getName());
		user = sysUserService.findById(user.getId());

		// 取所在部门,判断是否还有下级部门
		SysDepartment dept = user.getDepartment();
		logger.info("dept:" + dept.getName());
		List list = sysUserService.getSysUserList((int) dept.getId());

		// 循环取各部门用户
		List deptList = sysDepartmentService.getSysDepartmentList((int) dept
				.getId());
		Iterator iter = deptList.iterator();
		while (iter.hasNext()) {
			SysDepartment dept2 = (SysDepartment) iter.next();
			logger.info("dept:" + dept2.getName());
			list.addAll(sysUserService.getSysUserList((int) dept2.getId()));
		}
		// 除去自己
		list.remove(user);
		// 除去已授权用户
		list.removeAll(getAuthorizedUser(user));

		return list;
	}

	/**
	 * 授权
	 * 
	 * @param fromUser
	 *            SysUser 授权人
	 * @param toUser
	 *            SysUser 被授权人
	 * @param startDate
	 *            String
	 * @param endDate
	 *            String
	 */
	public boolean addRole(long fromUserId, long toUserId, String startDate,
			String endDate, int mark, String processNames,
			String processDescriptions) {
		boolean ret = false;
		if (null != processNames && processNames.length() > 0)
			processNames = processNames.substring(0,
					processNames.lastIndexOf(","));
		if (null != processDescriptions && processDescriptions.length() > 0)
			processDescriptions = processDescriptions.substring(0,
					processDescriptions.lastIndexOf(","));
		SysUser fromUser = sysUserService.findById(fromUserId);
		SysUser toUser = sysUserService.findById(toUserId);// ////////////////????????????????????????
		if (fromUser == null || toUser == null || fromUserId == toUserId)
			return ret;

		// 取出授权人fromUser的角色集合
		Object[] values = new Object[] { fromUser };
		String query = "from SysUserRole a where a.user=?";
		List userRoles = abstractDao.getList(query, values, null);
		long ts = System.currentTimeMillis();
		Iterator iter = userRoles.iterator();
		while (iter.hasNext()) {
			SysUserRole userRole = (SysUserRole) iter.next();
			Hibernate.initialize(userRole.getDeptRole());
			if (userRole.getDeptRole() != null) {
				Hibernate.initialize(userRole.getDeptRole().getRole());
			}
			// logger.info("add role:"
			// + userRole.getDeptRole().getRole().getName());

			// 授给被授权人
			SysUserRole bean = new SysUserRole();
			bean.setId(ts++);
			bean.setAuthorizeFrom(fromUser);
			bean.setUser(toUser);
			bean.setDeptRole(userRole.getDeptRole());
			bean.setAuthorized(1);
			bean.setAvailDateStart(BaseUtil.stringToDate(startDate));
			bean.setAvailDateEnd(BaseUtil.stringToDate(endDate));
			bean.setProcessDescription(processDescriptions);
			if (mark == 1) {
				bean.setProcessDescription("全局代理");
			}
			// if (toUser.getUserRoles().size() == 0) {
			// System.out
			// .println("!!!!!!!!!!toUser.getUserRoles().size() ==
			// 0!!!!!!!!!!!!!!!");
			// }

			toUser.getUserRoles().add(bean);

			// System.out
			// .println("-------------SysUserRoleService.addRole()-----------------");
			// System.out.println("-------------fromUser:" + fromUser.getName()
			// + "------------------------");
			// System.out.println("-------------toUser:" + toUser.getName()
			// + "----------------------------");
			ret = sysUserService.update(toUser);

		}
		// @todo 增加工作流
		insertAgent(fromUser, toUser, startDate, endDate, mark, processNames);
		return ret;
	}

	/**
	 * 取消授权
	 * 
	 * @param fromUser
	 *            SysUser 授权人
	 * @param toUser
	 *            SysUser 被授权人
	 */
	public boolean removeRole(long fromUserId, long toUserId) {
		boolean ret = false;
		SysUser fromUser = sysUserService.findById(fromUserId);
		SysUser toUser = sysUserService.findById(toUserId);
		if (fromUser == null || toUser == null)
			return ret;

		// 取出被授权人toUser的角色集合
		Object[] values = new Object[] { toUser };
		String query = "from SysUserRole a where a.user=?";
		List userRoles = abstractDao.getList(query, values, null);
		Iterator iter = userRoles.iterator();
		while (iter.hasNext()) {
			SysUserRole userRole = (SysUserRole) iter.next();
			// 判断是否是授权人
			if (userRole.getAuthorizeFrom() != null
					&& fromUserId == userRole.getAuthorizeFrom().getId()) {
				logger.info("remove role:"
						+ userRole.getDeptRole().getRole().getName());
				delete(userRole);// 删除权限
			}
		}
		// @todo 删除工作流
		removeAgent(fromUser, toUser);
		return true;
	}

	/**
	 * 定时批量删除过期代理的权限
	 * 
	 * @return
	 * @author key
	 * @date Jul 18, 2012
	 */
	public boolean removeRoles() {
		boolean ret = false;
		// 取出被授权人toUser的角色集合
		Object[] values = new Object[] { new Date() };
		String query = "from SysUserRole a where a.authorized=1 and a.availDateEnd<?";
		List userRoles = abstractDao.getList(query, values, null);
		Iterator iter = userRoles.iterator();
		while (iter.hasNext()) {
			SysUserRole userRole = (SysUserRole) iter.next();
			SysUser fromUser = userRole.getAuthorizeFrom();
			SysUser toUser = userRole.getUser();
			// 判断是否是授权人
			logger.info("toUser:" + toUser.getName() + ",fromUser:"
					+ fromUser.getName() + ",remove role:"
					+ userRole.getDeptRole().getRole().getName()
					+ ",availDateEnd="
					+ userRole.getAvailDateEnd().toLocaleString());
			delete(userRole);// 删除权限
			removeAgent(fromUser, toUser);// @todo 删除工作流
		}
		return true;
	}

	/**
	 * 工作流授权
	 * 
	 * @param fromUser
	 * @param toUser
	 */
	public void insertAgent(SysUser fromUser, SysUser toUser, String startDate,
			String endDate, int mark, String processNames) {
		endDate += " 23:59:59";
		if (mark == 1) {// 全局代理
			String sql = "insert into JPAGE_JBPM_AGENT_INSTANCE(id, actorId, agentId, objectId, objectValue, startDate, endDate) "
					+ "values('"
					+ UUID32.getUUID()
					+ "','"
					+ toUser.getAccount()
					+ "','"
					+ fromUser.getAccount()
					+ "', 'agent', 'jbpm', '"
					+ startDate
					+ "', '"
					+ endDate
					+ "')";
			abstractDao.executeSQL(sql);
		} else {// 流程代理
			String[] ss = processNames.split(",");
			for (int i = 0; i < ss.length; i++) {
				String processName = ss[i];
				String sql = "insert into JPAGE_JBPM_AGENT_INSTANCE(id, actorId, agentId, objectId, objectValue,attribute01, startDate, endDate) "
						+ "values('"
						+ UUID32.getUUID()
						+ "','"
						+ toUser.getAccount()
						+ "','"
						+ fromUser.getAccount()
						+ "', 'agent', 'jbpm_process','"
						+ processName
						+ "','"
						+ startDate + "', '" + endDate + "')";
				abstractDao.executeSQL(sql);
			}
		}
	}

	public void removeAgent(SysUser fromUser, SysUser toUser) {
		String sql = "delete from JPAGE_JBPM_AGENT_INSTANCE where actorId='"
				+ toUser.getAccount() + "' and agentId='"
				+ fromUser.getAccount() + "'";
		abstractDao.executeSQL(sql);
	}

	/**
	 * 
	 * @param filter
	 * @return
	 */
	public PageResult getAllAuthorizedUser(Map filter) {
		StringBuffer query = new StringBuffer();
		query.append("from SysUser a ").append("inner join a.userRoles as b ")
				.append("where a.accountType=0 ");
		// 部门
		String deptId = (String) filter.get("deptId");
		if (deptId != null) {
			query.append(" and a.department.id=").append(deptId);
		}
		// 用户名称
		String name = (String) filter.get("name");
		if (name != null) {
			query.append(" and a.name like '%").append(name).append("%'");
		}
		// 有效日期
		String startDate = (String) filter.get("startDate");
		if (startDate != null) {
			query.append(" and b.availDateStart>='").append(startDate)
					.append("'");
		}
		String endDate = (String) filter.get("endDate");
		if (endDate != null) {
			query.append(" and b.availDateEnd<='").append(endDate).append("'");
		}
		int pageNo = 1;
		if ((String) filter.get("page_no") != null) {
			pageNo = Integer.parseInt((String) filter.get("page_no"));
		}
		int pageSize = 2 * Constants.PAGE_SIZE;
		if ((String) filter.get("page_size") != null) {
			pageSize = Integer.parseInt((String) filter.get("page_size"));
		}

		// 计算总数
		StringBuffer countQuery = new StringBuffer();
		countQuery.append("select count(distinct a.id) ");
		countQuery.append(query);
		int count = ((Long) abstractDao
				.getList(countQuery.toString(), null, null).iterator().next())
				.intValue();
		if (count == 0) {// 结果集为空
			PageResult pager = new PageResult();
			pager.setPageSize(pageSize);
			return pager;
		}

		// 查询
		StringBuffer selectQuery = new StringBuffer();
		selectQuery.append("select distinct a ");
		selectQuery.append(query);
		selectQuery.append(" order by a.id asc");
		return abstractDao.getList(selectQuery.toString(), null, pageNo,
				pageSize, count);
	}
}
