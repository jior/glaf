package com.glaf.base.modules.sys.service;

import java.util.List;
import java.util.Set;

import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.utils.PageResult;

public interface SysUserService {

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysUser
	 * @return boolean
	 */
	boolean create(SysUser bean);

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysUser
	 * @return boolean
	 */
	boolean update(SysUser bean);

	boolean updateUser(SysUser bean);

	/**
	 * ɾ��
	 * 
	 * @param bean
	 *            SysUser
	 * @return boolean
	 */
	boolean delete(SysUser bean);

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
	SysUser findById(long id);

	/**
	 * �����Ʋ��Ҷ���
	 * 
	 * @param name
	 *            String
	 * @return SysUser
	 */
	SysUser findByAccount(String account);

	/**
	 * �����Ʋ��Ҷ���
	 * 
	 * @param name
	 *            String
	 * @return SysUser
	 */
	SysUser findByAccountWithAll(String account);

	/**
	 * ��ȡ�ض����ŵ�Ա�����ݼ� ��ҳ�б�
	 * 
	 * @param deptId
	 *            int
	 * @param pageNo
	 *            int
	 * @param pageSize
	 *            int
	 * @return
	 */
	PageResult getSysUserList(int deptId, int pageNo, int pageSize);

	/**
	 * ��ѯ��ȡsysUser�б�
	 * 
	 * @param deptId
	 * @param fullName
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageResult getSysUserList(int deptId, String fullName, int pageNo,
			int pageSize);

	/**
	 * ��ȡ�б�
	 * 
	 * @param deptId
	 *            int
	 * @return List
	 */
	List getSysUserList(int deptId);

	/**
	 * ��ȡ�б�
	 * 
	 * @param deptId
	 *            int
	 * @return List
	 */
	List getSysUserList();

	/**
	 * ��ȡ�б�
	 * 
	 * @param deptId
	 *            int
	 * @return List
	 */
	List getSysUserWithDeptList();

	/**
	 * 
	 * @param user
	 * @return
	 */
	Set getUserRoles(SysUser user);

	/**
	 * ���û�Ȩ��
	 * 
	 * @param user
	 * @return
	 */
	SysUser getUserPrivileges(SysUser user);

	SysUser getUserAndPrivileges(SysUser user);

	/**
	 * ���ҹ�Ӧ���û� flag = true ��ʾ���û����ڣ�����Ϊ������
	 * 
	 * @param supplierNo
	 * @return
	 */
	List<SysUser> getSupplierUser(String supplierNo);

	/**
	 * �����û�Ȩ��
	 * 
	 * @param user
	 *            ϵͳ�û�
	 * @param delRoles
	 *            Ҫɾ�����û�Ȩ��
	 * @param newRoles
	 *            Ҫ���ӵ��û�Ȩ��
	 */
	boolean updateRole(SysUser user, Set delRoles, Set newRoles);

	/**
	 * ��ȡ�б�
	 * 
	 */
	PageResult getSysUserList(int deptId, String userName, String account,
			int pageNo, int pageSize);

	boolean isThisPlayer(SysUser user, String code);

}
