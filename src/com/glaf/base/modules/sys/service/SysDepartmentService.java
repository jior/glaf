package com.glaf.base.modules.sys.service;

import java.util.List;

import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.utils.PageResult;

public interface SysDepartmentService {

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysDepartment
	 * @return boolean
	 */
	boolean create(SysDepartment bean);

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysDepartment
	 * @return boolean
	 */
	boolean update(SysDepartment bean);

	/**
	 * ɾ��
	 * 
	 * @param bean
	 *            SysDepartment
	 * @return boolean
	 */
	boolean delete(SysDepartment bean);

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
	SysDepartment findById(long id);

	/**
	 * ��������Ҷ���
	 * 
	 * @param name
	 *            String
	 * @return SysDepartment
	 */

	SysDepartment findByNo(String code);

	/**
	 * �����Ʋ��Ҷ���
	 * 
	 * @param name
	 *            String
	 * @return SysDepartment
	 */
	SysDepartment findByName(String name);

	/**
	 * ��ȡ��ҳ�б�
	 * 
	 * @param parent
	 *            int
	 * @param pageNo
	 *            int
	 * @param pageSize
	 *            int
	 * @return
	 */
	PageResult getSysDepartmentList(int parent, int pageNo, int pageSize);

	/**
	 * ��ȡ�б�
	 * 
	 * @return List
	 */
	List getSysDepartmentList();

	/**
	 * ��ȡ�б�
	 * 
	 * @param parent
	 *            int
	 * @return List
	 */
	List getSysDepartmentList(int parent);

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysDepartment
	 * @param operate
	 *            int ����
	 */
	void sort(long parent, SysDepartment bean, int operate);

	/**
	 * ��ȡ�û������б�
	 * 
	 * @param list
	 * @param node
	 */
	void findNestingDepartment(List list, SysDepartment node);
}
