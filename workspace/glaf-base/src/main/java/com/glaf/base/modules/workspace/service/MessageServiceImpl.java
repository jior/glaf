package com.glaf.base.modules.workspace.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;

import com.glaf.base.dao.AbstractSpringDao;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.workspace.model.Message;
import com.glaf.base.utils.PageResult;
import com.glaf.base.utils.WebUtil;

public class MessageServiceImpl implements MessageService{
	private static final Log logger = LogFactory
			.getLog(MessageServiceImpl.class);

	private AbstractSpringDao abstractDao;

	public void setAbstractDao(AbstractSpringDao abstractDao) {
		this.abstractDao = abstractDao;
		logger.info("setAbstractDao");
	}

	/**
	 * 保存新增信息
	 * 
	 * @param bean
	 * @return
	 */
	public boolean create(Message bean) {
		return abstractDao.create(bean);
	}

	/**
	 * 保存或更新信息
	 * 
	 * @param bean
	 * @return
	 */
	public boolean saveOrUpdate(Message bean) {
		return abstractDao.saveOrUpdate(bean);
	}

	/**
	 * 更新信息
	 * 
	 * @param bean
	 * @return
	 */
	public boolean update(Message bean) {
		return abstractDao.update(bean);
	}

	/**
	 * 单项删除
	 * 
	 * @param bean
	 * @return
	 */
	public boolean delete(Message bean) {
		return abstractDao.delete(bean);
	}

	/**
	 * 单项删除
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	public boolean delete(long id) {
		Message bean = find(id);
		if (bean != null) {
			return delete(bean);
		} else {
			return false;
		}
	}

	/**
	 * 批量删除
	 * 
	 * @param c
	 * @return
	 */
	public boolean deleteAll(Collection c) {
		return abstractDao.deleteAll(c);
	}

	/**
	 * 获取对象
	 * 
	 * @param id
	 * @return
	 */
	public Message find(long id) {
		return (Message) abstractDao.find(Message.class, new Long(id));
	}

	/**
	 * 发送消息(给用户)
	 * 
	 * @param message
	 * @param recverIdStr
	 * @return
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public boolean saveSendMessage(Message message, String[] recverIds) {
		boolean rst = true;

		// 收件人列表
		StringBuffer recverList = new StringBuffer("");

		// 发送消息给收件人
		for (int i = 0; i < recverIds.length; i++) {
			if (recverIds[i] == null || recverIds[i].trim().length() == 0) {
				continue;
			}

			Message newMessage = new Message();
			try {
				PropertyUtils.copyProperties(newMessage, message);
			} catch (Exception ex) {
				org.springframework.beans.BeanUtils.copyProperties(newMessage,
						message);
			}

			SysUser recver = (SysUser) abstractDao.find(SysUser.class,
					new Long(recverIds[i]));
			newMessage.setRecver(recver);

			recverList.append(recver.getName()).append(",");

			if (!saveOrUpdate(newMessage)) {
				rst = false;
			}
		}

		// 发送消息给自己,保存在发件箱
		if (rst) {
			sendToSelf(message, recverList.toString());
		}

		return rst;
	}

	/**
	 * 发送消息(给部门的用户)
	 * 
	 * @param message
	 * @param recverIds
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public boolean saveSendMessageToDept(Message message, String[] recverIds) {
		boolean rst = true;

		// 收件部门列表
		StringBuffer recverList = new StringBuffer("");

		// 发送消息给收件人
		for (int i = 0; i < recverIds.length; i++) {
			if (recverIds[i] == null || recverIds[i].trim().length() == 0) {
				continue;
			}

			String query = "from SysUser a where a.department.id=?";
			List list = abstractDao.getList(query, new Object[] { new Long(
					recverIds[i]) }, null);

			if (list == null) {
				continue;
			}

			Iterator iter = list.iterator();
			int index = 0;
			while (iter.hasNext()) {

				SysUser recver = (SysUser) iter.next();
				if (index == 0) {// 取部门名称
					recverList.append(recver.getDepartment().getName()).append(
							",");
				}
				index++;

				Message newMessage = new Message();
				try {
					PropertyUtils.copyProperties(newMessage, message);
				} catch (Exception ex) {
					org.springframework.beans.BeanUtils.copyProperties(
							newMessage, message);
				}
				newMessage.setRecver(recver);

				if (!saveOrUpdate(newMessage)) {
					rst = false;
				}
			}

		}

		// 发送消息给自己,保存在发件箱
		if (rst) {
			sendToSelf(message, recverList.toString());
		}

		return rst;
	}

	/**
	 * 发送消息给自己,保存在发件箱
	 * 
	 * @param message
	 * @param recverLists
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private boolean sendToSelf(Message message, String recverLists) {
		Message newMessage = new Message();
		try {
			PropertyUtils.copyProperties(newMessage, message);
		} catch (Exception ex) {
			org.springframework.beans.BeanUtils.copyProperties(newMessage,
					message);
		}

		newMessage.setCategory(1);// 发件箱
		newMessage.setRecver(message.getSender());

		if (recverLists.endsWith(",")) {
			recverLists = recverLists.substring(0, recverLists.length() - 1);
		}
		if (recverLists.length() > 2000) {
			recverLists = recverLists.substring(0, 2000);
		}
		newMessage.setRecverList(recverLists);
		return saveOrUpdate(newMessage);
	}

	/**
	 * 阅读消息
	 * 
	 * @param id
	 * @return
	 */
	public Message updateReadMessage(long id) {
		Message message = find(id);
		message.setReaded(1);// 设置已读
		update(message);
		return message;
	}

	/**
	 * 获取列表
	 * 
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageResult getMessageList(Map params, int pageNo, int pageSize) {

		DetachedCriteria detachedCriteria = WebUtil.getCriteria(params,
				Message.class);

		return abstractDao.getList(detachedCriteria, pageNo, pageSize);
	}

	/**
	 * 获取收件箱列表
	 * 
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageResult getReceiveList(long userId, Map params, int pageNo,
			int pageSize) {

		params.put("query_recver.id_el", userId + "");
		params.put("query_category_ex", "0");// 收件箱
		params.put("order_id", "desc");

		return getMessageList(params, pageNo, pageSize);
	}

	/**
	 * 获取未读收件箱列表
	 * 
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageResult getNoReadList(long userId, Map params, int pageNo,
			int pageSize) {

		params.put("query_recver.id_el", userId + "");
		params.put("query_category_ex", "0");// 收件箱
		params.put("query_readed_ex", "0");// 未读
		params.put("order_id", "desc");

		return getMessageList(params, pageNo, pageSize);
	}

	/**
	 * 获取发件箱列表
	 * 
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageResult getSendedList(long userId, Map params, int pageNo,
			int pageSize) {

		params.put("query_sender.id_el", userId + "");
		params.put("query_category_ex", "1");// 发件箱
		params.put("order_id", "desc");

		return getMessageList(params, pageNo, pageSize);
	}
}
