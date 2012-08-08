package com.glaf.base.modules.others.service;

import java.util.List;

import com.glaf.base.modules.others.model.Audit;
import com.glaf.base.modules.sys.model.SysUser;

public interface AuditService {

	/**
	 * 保存
	 * 
	 * @param bean
	 *            Audit
	 * @return boolean
	 */
	boolean create(Audit bean);

	/**
	 * 更新
	 * 
	 * @param bean
	 *            Audit
	 * @return boolean
	 */
	boolean update(Audit bean);

	/**
	 * 删除
	 * 
	 * @param bean
	 *            Audit
	 * @return boolean
	 */
	boolean delete(Audit bean);

	/**
	 * 删除
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	boolean delete(long id);

	/**
	 * 批量删除
	 * 
	 * @param id
	 * @return
	 */
	boolean deleteAll(long[] id);

	/**
	 * 获取对象
	 * 
	 * @param id
	 * @return
	 */
	Audit findById(long id);

	/**
	 * 返回所有审批列表，分type
	 * 
	 * @param referId
	 * @param referType
	 * @return
	 */
	List getAuditList(long referId, int referType);

	/**
	 * 返回采购申请的所有审批列表(新增，变更，退单重提，废止)
	 * 
	 * @param referId
	 * @param referType
	 * @return
	 */
	List getAuditList(long referId, String referTypes);

	/**
	 * 返回所有审批列表最终审批人列表（去除重复）
	 * 
	 * @author zoumin
	 * @param referId
	 * @param referType
	 * @return
	 */
	List getAuditUserList(long referId, int referType);

	/**
	 * 返回部门最后一次的审批记录
	 * 
	 * @author zoumin
	 * @param referId
	 * @param referType
	 * @param leaderId
	 * @return
	 */
	List getAuditDeptList(long referId, int referType, long deptId);

	/**
	 * 创建审批记录
	 * 
	 * @param user
	 * @param referId
	 * @param referType
	 * @param confirm
	 * @return
	 */
	boolean saveAudit(SysUser user, long referId, int referType, boolean confirm);

	/**
	 * 返回最后一个不通过的审批记录
	 * 
	 * @author zoumin
	 * @param referId
	 * @param referType
	 * @param leaderId
	 * @return
	 */
	List getAuditNotList(long referId);
}
