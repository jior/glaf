package com.glaf.base.modules.sys.service;

import java.util.List;

import org.json.JSONArray;

import com.glaf.base.modules.sys.model.SysApplication;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.utils.PageResult;

public interface SysApplicationService {

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysApplication
	 * @return boolean
	 */
	boolean create(SysApplication bean);

	/**
	 * ɾ��
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	boolean delete(long id);

	/**
	 * ɾ��
	 * 
	 * @param bean
	 *            SysApplication
	 * @return boolean
	 */
	boolean delete(SysApplication bean);

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
	SysApplication findById(long id);

	/**
	 * �����Ʋ��Ҷ���
	 * 
	 * @param name
	 *            String
	 * @return SysApplication
	 */
	SysApplication findByName(String name);

	/**
	 * ��ȡ�û��ܷ��ʵ���ģ���б�
	 * 
	 * @param userId
	 *            int
	 * @param parent
	 *            int
	 * @return List
	 */
	List<SysApplication> getAccessAppList(long parent, SysUser user);

	/**
	 * ��ȡȫ���б�
	 * 
	 * @return List
	 */
	List<SysApplication> getApplicationList();

	/**
	 * ��ȡ�б�
	 * 
	 * @param parent
	 *            int
	 * @return List
	 */
	List<SysApplication> getApplicationList(int parent);

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
	PageResult getApplicationList(int parent, int pageNo, int pageSize);

	/**
	 * ��ȡ�˵�
	 * 
	 * @param parent
	 * @param userId
	 * @return
	 */
	String getMenu(long parent, SysUser user);

	/**
	 * ��ȡ�û��˵�֮Json����
	 * 
	 * @param parent
	 *            ���ڵ���
	 * @param userId
	 *            �û���¼�˺�
	 * @return
	 */
	JSONArray getUserMenu(long parent, String userId);

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysApplication
	 * @param operate
	 *            int ����
	 */
	void sort(long parent, SysApplication bean, int operate);

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysApplication
	 * @return boolean
	 */
	boolean update(SysApplication bean);
}
