package com.glaf.base.modules.others.service;

import java.util.List;
import java.util.Map;

import com.glaf.base.modules.others.model.Attachment;

public interface AttachmentService {

	/**
	 * ����
	 * 
	 * @param bean
	 *            Attachment
	 * @return boolean
	 */
	boolean create(Attachment bean);

	/**
	 * ����
	 * 
	 * @param bean
	 *            Attachment
	 * @return boolean
	 */
	boolean update(Attachment bean);

	/**
	 * ɾ��
	 * 
	 * @param bean
	 *            Attachment
	 * @return boolean
	 */
	boolean delete(Attachment bean);

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
	Attachment find(long id);

	/**
	 * �������и����б�
	 * 
	 * @param parent
	 * @return
	 */
	List getAttachmentList(long referId, int referType);

	/**
	 * ���ظ���
	 * 
	 * @param referId
	 * @param referType
	 * @return Attachment
	 */
	Attachment find(long referId, int referType);

	/**
	 * ���ظ���
	 * 
	 * @param referId
	 * @param referType
	 * @return Attachment
	 */
	Attachment find(long id, long referId, int referType);

	/**
	 * ���ظ���
	 * 
	 * @param referId
	 * @param referType
	 * @param name
	 * @return Attachment
	 */
	Attachment find(long referId, int referType, String name);

	/**
	 * �������и��������б�
	 * 
	 * @param parent
	 * @return
	 */
	Map getNameMap(long referId, int referType);

	/**
	 * �������и����б�
	 * 
	 * @param referId
	 * @param referType
	 * @return
	 */
	List getAttachmentList(long[] referIds, int referType);

	/**
	 * ���ظ�������
	 * 
	 * @param referId
	 * @param referType
	 * @return
	 */
	int getAttachmentCount(long[] referIds, int referType);

	/**
	 * ���ض�Ӧ�ĸ����б�
	 * 
	 * @param parent
	 * @return
	 */
	List getPurchaseAttachmentList(long[] ids, int referType);
}
