package com.glaf.base.modules.workspace.service;

import java.util.Collection;
import java.util.Map;
import com.glaf.base.modules.workspace.model.Message;
import com.glaf.base.utils.PageResult;

public interface MessageService {

	/**
	 * 保存新增信息
	 * 
	 * @param bean
	 * @return
	 */
	boolean create(Message bean);

	/**
	 * 保存或更新信息
	 * 
	 * @param bean
	 * @return
	 */
	boolean saveOrUpdate(Message bean);

	/**
	 * 更新信息
	 * 
	 * @param bean
	 * @return
	 */
	boolean update(Message bean);

	/**
	 * 单项删除
	 * 
	 * @param bean
	 * @return
	 */
	boolean delete(Message bean);

	/**
	 * 单项删除
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	boolean delete(long id);

	/**
	 * 批量删除
	 * 
	 * @param c
	 * @return
	 */
	boolean deleteAll(Collection c);

	/**
	 * 获取对象
	 * 
	 * @param id
	 * @return
	 */
	Message find(long id);

	/**
	 * 发送消息(给用户)
	 * 
	 * @param message
	 * @param recverIdStr
	 * @return
	 */
	boolean saveSendMessage(Message message, String[] recverIds);

	/**
	 * 发送消息(给部门的用户)
	 * 
	 * @param message
	 * @param recverIds
	 * @return
	 */
	boolean saveSendMessageToDept(Message message, String[] recverIds);

	/**
	 * 阅读消息
	 * 
	 * @param id
	 * @return
	 */
	Message updateReadMessage(long id);

	/**
	 * 获取列表
	 * 
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageResult getMessageList(Map params, int pageNo, int pageSize);

	/**
	 * 获取收件箱列表
	 * 
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageResult getReceiveList(long userId, Map params, int pageNo, int pageSize);

	/**
	 * 获取未读收件箱列表
	 * 
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageResult getNoReadList(long userId, Map params, int pageNo, int pageSize);

	/**
	 * 获取发件箱列表
	 * 
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageResult getSendedList(long userId, Map params, int pageNo, int pageSize);
}
