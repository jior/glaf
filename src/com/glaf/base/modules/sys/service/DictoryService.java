package com.glaf.base.modules.sys.service;

import java.util.List;
import java.util.Map;

import com.glaf.base.modules.sys.model.Dictory;
import com.glaf.base.utils.PageResult;

public interface DictoryService {

	/**
	 * ����
	 * 
	 * @param bean
	 *            Dictory
	 * @return boolean
	 */
	boolean create(Dictory bean);

	/**
	 * ɾ��
	 * 
	 * @param bean
	 *            Dictory
	 * @return boolean
	 */
	boolean delete(Dictory bean);

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
	Dictory find(long id);

	/**
	 * ����ĳ�����µ������ֵ��б�
	 * 
	 * @param parent
	 * @return
	 */
	List<Dictory> getAvailableDictoryList(long parent);

	/**
	 * ����ID��code
	 * 
	 * @param id
	 * @return
	 */
	String getCodeById(long id);

	/**
	 * ��ȡ��ҳ�б�
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageResult getDictoryList(int pageNo, int pageSize);

	/**
	 * ����ĳ�����µ������ֵ��б�
	 * 
	 * @param parent
	 * @return
	 */
	List<Dictory> getDictoryList(long parent);

	/**
	 * �����ͺ������б�
	 * 
	 * @param parent
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageResult getDictoryList(long parent, int pageNo, int pageSize);

	/**
	 * ��Ҫ���ڻ�ú�����Ŀ�ļ�ֵ��
	 * 
	 * @param list
	 * @param purchaseId
	 * @return
	 */
	Map<String, String> getDictoryMap(List<Dictory> list, long purchaseId);

	/**
	 * ����
	 * 
	 * @param bean
	 *            Dictory
	 * @param operate
	 *            int ����
	 */
	void sort(long parent, Dictory bean, int operate);

	/**
	 * ����
	 * 
	 * @param bean
	 *            Dictory
	 * @return boolean
	 */
	boolean update(Dictory bean);
}
