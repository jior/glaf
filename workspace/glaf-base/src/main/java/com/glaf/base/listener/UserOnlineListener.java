package com.glaf.base.listener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.SysUser;

public class UserOnlineListener implements HttpSessionAttributeListener {
	private static Log logger = LogFactory.getLog(UserOnlineListener.class);
	private static Map<Long, String> userList = new HashMap<Long, String>();

	public void attributeAdded(HttpSessionBindingEvent se) {
		if (SysConstants.LOGIN.equals(se.getName())) {
			logger.info(se.getName() + ";" + se.getValue());
			inStack((SysUser) se.getValue());
		}
	}

	public void attributeRemoved(HttpSessionBindingEvent se) {
		if (SysConstants.LOGIN.equals(se.getName())) {
			logger.info(se.getName() + ";" + se.getValue());
			outStack((SysUser) se.getValue());
		}
	}

	public void attributeReplaced(HttpSessionBindingEvent se) {

	}

	/**
	 * session�����ջ
	 * 
	 */
	private void inStack(SysUser user) {
		if (user == null)
			return;

		synchronized (this) {
			logger.info("in stack userId:" + user.getId());
			if (findUser(user.getId()) == null) {// �û�δ��½
				userList.put(new Long(user.getId()), user.getLoginIP());
			}
		}
	}

	/**
	 * session�����ջ
	 * 
	 */
	private void outStack(SysUser user) {
		if (user == null)
			return;
		synchronized (this) {
			logger.info("out stack userId:" + user.getId());
			userList.remove(new Long(user.getId()));
		}
	}

	/**
	 * �����û���½ip
	 * 
	 * @param userId
	 * @return
	 */
	public static String findUser(long userId) {
		String ip = null;
		logger.info("find userId:" + userId);

		if (userList.containsKey(new Long(userId))) {// �û��Ƿ����
			ip = (String) userList.get(new Long(userId));
		}
		logger.info("ip:" + ip);
		return ip;
	}

	/**
	 * ��ӡ�û��б�
	 */
	public static void print() {
		Iterator<Long> iter = userList.keySet().iterator();
		while (iter.hasNext()) {
			Long userId = (Long) iter.next();
			logger.info("userId:" + userId + ", ip:" + userList.get(userId));
		}
	}
}
