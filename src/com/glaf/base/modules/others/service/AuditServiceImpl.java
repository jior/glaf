package com.glaf.base.modules.others.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.type.Type;

import com.glaf.base.dao.AbstractSpringDao;
import com.glaf.base.modules.others.model.Audit;
import com.glaf.base.modules.sys.model.SysUser;

public class AuditServiceImpl implements AuditService {
	private static final Log logger = LogFactory.getLog(AuditServiceImpl.class);
	private AbstractSpringDao abstractDao;

	public void setAbstractDao(AbstractSpringDao abstractDao) {
		this.abstractDao = abstractDao;
		logger.info("setAbstractDao");
	}

	/**
	 * ����
	 * 
	 * @param bean
	 *            Audit
	 * @return boolean
	 */
	public boolean create(Audit bean) {
		return abstractDao.create(bean);
	}

	/**
	 * ����
	 * 
	 * @param bean
	 *            Audit
	 * @return boolean
	 */
	public boolean update(Audit bean) {
		return abstractDao.update(bean);
	}

	/**
	 * ɾ��
	 * 
	 * @param bean
	 *            Audit
	 * @return boolean
	 */
	public boolean delete(Audit bean) {
		return abstractDao.delete(bean);
	}

	/**
	 * ɾ��
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	public boolean delete(long id) {
		Audit bean = findById(id);
		if (bean != null) {
			return delete(bean);
		} else {
			return false;
		}
	}

	/**
	 * ����ɾ��
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteAll(long[] id) {
		List list = new ArrayList();
		for (int i = 0; i < id.length; i++) {
			Audit bean = findById(id[i]);
			if (bean != null)
				list.add(bean);
		}
		return abstractDao.deleteAll(list);
	}

	/**
	 * ��ȡ����
	 * 
	 * @param id
	 * @return
	 */
	public Audit findById(long id) {
		return (Audit) abstractDao.find(Audit.class, new Long(id));
	}

	/**
	 * �������������б���type
	 * 
	 * @param referId
	 * @param referType
	 * @return
	 */
	public List getAuditList(long referId, int referType) {
		Object[] values = new Object[] { new Long(referId),
				new Integer(referType) };
		String query = "from Audit a where a.referId=? and a.referType=? order by a.id asc";
		return abstractDao.getList(query, values, null);
	}

	/**
	 * ���زɹ���������������б�(������������˵����ᣬ��ֹ)
	 * 
	 * @param referId
	 * @param referType
	 * @return
	 */
	public List getAuditList(long referId, String referTypes) {
		Object[] values = new Object[] { new Long(referId) };
		String query = "from Audit a where a.referId=? and a.referType in("
				+ referTypes + ") order by a.id asc";
		return abstractDao
				.getList(query, values, new Type[] { Hibernate.LONG });
	}

	/**
	 * �������������б������������б�ȥ���ظ���
	 * 
	 * @author zoumin
	 * @param referId
	 * @param referType
	 * @return
	 */
	public List getAuditUserList(long referId, int referType) {
		Date rejectDate = null;
		String sql = "from Audit a where a.referId = " + referId
				+ " and a.referType = " + referType
				+ " and a.flag = 0 order by createDate desc";
		List list = abstractDao.getList(sql, null, null);
		if (list != null && list.size() > 0) {
			Audit audit = (Audit) list.get(0);
			if (audit != null) {
				rejectDate = audit.getCreateDate();
			}
		}

		String query = "from Audit a where a.referId = " + referId
				+ " and a.referType = " + referType + " and a.flag = 1";
		if (rejectDate != null) {
			query = "from Audit a where a.referId = " + referId
					+ " and a.referType = " + referType
					+ " and a.flag = 1 and a.createDate >= '" + rejectDate
					+ "'";
		}
		return abstractDao.getList(query, null, null);
	}

	/**
	 * ���ز������һ�ε�������¼
	 * 
	 * @author zoumin
	 * @param referId
	 * @param referType
	 * @param leaderId
	 * @return
	 */
	public List getAuditDeptList(long referId, int referType, long deptId) {
		Object[] values = new Object[] { new Long(referId),
				new Integer(referType), new Long(deptId) };
		String query = "from Audit where referId = ? and referType = ? "
				+ "and deptId = ? order by createDate desc";
		return abstractDao.getList(query, values, null);
	}

	/**
	 * ����������¼
	 * 
	 * @param user
	 * @param referId
	 * @param referType
	 * @param confirm
	 * @return
	 */
	public boolean saveAudit(SysUser user, long referId, int referType,
			boolean confirm) {
		Audit bean = new Audit();
		bean.setDeptId(user.getDepartment().getId());
		bean.setDeptName(user.getDepartment().getName());
		bean.setHeadship(user.getHeadship());
		bean.setLeaderId(user.getId());
		bean.setLeaderName(user.getName());
		bean.setMemo("");
		bean.setReferId(referId);
		bean.setReferType(referType);
		bean.setFlag(confirm ? 1 : 0);
		bean.setCreateDate(new Date());
		return create(bean);
	}

	/**
	 * �������һ����ͨ����������¼
	 * 
	 * @author zoumin
	 * @param referId
	 * @param referType
	 * @param leaderId
	 * @return
	 */
	public List getAuditNotList(long referId) {
		Object[] values = new Object[] { new Long(referId) };
		String query = "from Audit where referId = ? and flag = 0 order by id desc";
		return abstractDao.getList(query, values, null);
	}
}
