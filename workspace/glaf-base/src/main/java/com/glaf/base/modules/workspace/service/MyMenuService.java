package com.glaf.base.modules.workspace.service;

import java.util.Collection;
import java.util.List;

import com.glaf.base.modules.workspace.model.MyMenu;
import com.glaf.base.utils.PageResult;

public interface MyMenuService {

	/**
	 * ����������Ϣ
	 * 
	 * @param myMenu
	 * @return
	 */
	boolean create(MyMenu bean);

	/**
	 * ����ɾ��
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	boolean delete(long id);

	/**
	 * ����ɾ��
	 * 
	 * @param myMenu
	 * @return
	 */
	boolean delete(MyMenu myMenu);

	/**
	 * ����ɾ��
	 * 
	 * @param c
	 * @return
	 */
	boolean deleteAll(Collection c);

	/**
	 * ��ȡ����
	 * 
	 * @param id
	 * @return
	 */
	MyMenu find(long id);

	/**
	 * ��ȡ�б�(�����кš��������)
	 * 
	 * @param userId
	 * @return
	 */
	List getMyMenuList(long userId);

	/**
	 * ��ȡ�б�(�����кš��������)
	 * 
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageResult getMyMenuList(long userId, int pageNo, int pageSize);

	/**
	 * ����
	 * 
	 * @param bean
	 *            MyMenu
	 * @param operate
	 *            int ����
	 */
	void sort(MyMenu bean, int operate);

	/**
	 * ������Ϣ
	 * 
	 * @param myMenu
	 * @return
	 */
	boolean update(MyMenu myMenu);

}
