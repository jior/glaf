package com.glaf.base.modules.sys.service;

import java.util.List;

import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.utils.PageResult;

public interface SysRoleService {

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysRole
	 * @return boolean
	 */
	boolean create(SysRole bean);

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysRole
	 * @return boolean
	 */
	boolean update(SysRole bean);

	/**
	 * ɾ��
	 * 
	 * @param bean
	 *            SysRole
	 * @return boolean
	 */
	boolean delete(SysRole bean);

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
	SysRole findById(long id);

	/**
	 * �����Ʋ��Ҷ���
	 * 
	 * @param name
	 *            String
	 * @return SysRole
	 */
	SysRole findByName(String name);

	/**
	 * ��code���Ҷ���
	 * 
	 * @param name
	 *            String
	 * @return SysRole
	 */
	SysRole findByCode(String code);

	/**
	 * ��ȡ��ҳ�б�
	 * 
	 * @param pageNo
	 *            int
	 * @param pageSize
	 *            int
	 * @return
	 */
	PageResult getSysRoleList(int pageNo, int pageSize);

	/**
	 * ��ȡ�б�
	 * 
	 * @return List
	 */
	List<SysRole> getSysRoleList();

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysRole
	 * @param operate
	 *            int ����
	 */
	void sort(SysRole bean, int operate);

}
