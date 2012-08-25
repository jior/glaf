package com.glaf.base.modules.workspace.service;

import java.util.Collection;
import java.util.Map;
import com.glaf.base.modules.workspace.model.Message;
import com.glaf.base.utils.PageResult;

public interface MessageService {

	/**
	 * ����������Ϣ
	 * 
	 * @param bean
	 * @return
	 */
	boolean create(Message bean);

	/**
	 * ����������Ϣ
	 * 
	 * @param bean
	 * @return
	 */
	boolean saveOrUpdate(Message bean);

	/**
	 * ������Ϣ
	 * 
	 * @param bean
	 * @return
	 */
	boolean update(Message bean);

	/**
	 * ����ɾ��
	 * 
	 * @param bean
	 * @return
	 */
	boolean delete(Message bean);

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
	Message find(long id);

	/**
	 * ������Ϣ(���û�)
	 * 
	 * @param message
	 * @param recverIdStr
	 * @return
	 */
	boolean saveSendMessage(Message message, String[] recverIds);

	/**
	 * ������Ϣ(�����ŵ��û�)
	 * 
	 * @param message
	 * @param recverIds
	 * @return
	 */
	boolean saveSendMessageToDept(Message message, String[] recverIds);

	/**
	 * �Ķ���Ϣ
	 * 
	 * @param id
	 * @return
	 */
	Message updateReadMessage(long id);

	/**
	 * ��ȡ�б�
	 * 
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageResult getMessageList(Map params, int pageNo, int pageSize);

	/**
	 * ��ȡ�ռ����б�
	 * 
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageResult getReceiveList(long userId, Map params, int pageNo, int pageSize);

	/**
	 * ��ȡδ���ռ����б�
	 * 
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageResult getNoReadList(long userId, Map params, int pageNo, int pageSize);

	/**
	 * ��ȡ�������б�
	 * 
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageResult getSendedList(long userId, Map params, int pageNo, int pageSize);
}
