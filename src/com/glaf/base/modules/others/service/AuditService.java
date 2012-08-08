package com.glaf.base.modules.others.service;

import java.util.List;

import com.glaf.base.modules.others.model.Audit;
import com.glaf.base.modules.sys.model.SysUser;

public interface AuditService {

	/**
	 * ����
	 * 
	 * @param bean
	 *            Audit
	 * @return boolean
	 */
	boolean create(Audit bean);

	/**
	 * ����
	 * 
	 * @param bean
	 *            Audit
	 * @return boolean
	 */
	boolean update(Audit bean);

	/**
	 * ɾ��
	 * 
	 * @param bean
	 *            Audit
	 * @return boolean
	 */
	boolean delete(Audit bean);

	/**
	 * ɾ��
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	boolean delete(long id);

	/**
	 * ����ɾ��
	 * 
	 * @param id
	 * @return
	 */
	boolean deleteAll(long[] id);

	/**
	 * ��ȡ����
	 * 
	 * @param id
	 * @return
	 */
	Audit findById(long id);

	/**
	 * �������������б���type
	 * 
	 * @param referId
	 * @param referType
	 * @return
	 */
	List getAuditList(long referId, int referType);

	/**
	 * ���زɹ���������������б�(������������˵����ᣬ��ֹ)
	 * 
	 * @param referId
	 * @param referType
	 * @return
	 */
	List getAuditList(long referId, String referTypes);

	/**
	 * �������������б������������б�ȥ���ظ���
	 * 
	 * @author zoumin
	 * @param referId
	 * @param referType
	 * @return
	 */
	List getAuditUserList(long referId, int referType);

	/**
	 * ���ز������һ�ε�������¼
	 * 
	 * @author zoumin
	 * @param referId
	 * @param referType
	 * @param leaderId
	 * @return
	 */
	List getAuditDeptList(long referId, int referType, long deptId);

	/**
	 * ����������¼
	 * 
	 * @param user
	 * @param referId
	 * @param referType
	 * @param confirm
	 * @return
	 */
	boolean saveAudit(SysUser user, long referId, int referType, boolean confirm);

	/**
	 * �������һ����ͨ����������¼
	 * 
	 * @author zoumin
	 * @param referId
	 * @param referType
	 * @param leaderId
	 * @return
	 */
	List getAuditNotList(long referId);
}
