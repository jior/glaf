package com.glaf.base.modules.sys.interceptor;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.web.context.WebApplicationContext;
 
import com.glaf.base.modules.sys.model.BaseDataInfo;
import com.glaf.base.modules.sys.model.SysFunction;
import com.glaf.base.modules.sys.model.SysLog;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysLogService;
import com.glaf.base.modules.utils.ContextUtil;
import com.glaf.base.utils.RequestUtil;

public class AuthorizeInterceptor implements MethodBeforeAdvice {
	private static final Logger logger = Logger
			.getLogger(AuthorizeInterceptor.class);

	/**
	 * method - method being invoked args - arguments to the method target -
	 * target of the method invocation. May be null.
	 */
	public void before(Method method, Object[] args, Object target)
			throws Throwable {
		boolean authorized = false;

		String objectName = target.getClass().getName();
		String methodName = method.getName();
		logger.debug("object:" + objectName);
		logger.debug("method:" + methodName);
		String ip = "";
		String account = "";

		for (int i = 0; i < args.length; i++) {
			logger.debug("args:" + args[i]);
			if (args[i] instanceof HttpServletRequest) {
				HttpServletRequest request = (HttpServletRequest) args[i];
				if (request != null && request.getParameter("method") != null) {
					methodName = request.getParameter("method");
					ip = request.getRemoteHost();
					SysUser user = RequestUtil.getLoginUser(request);
					if (user != null) {
						account = user.getAccount();
					}
					logger.debug("IP:" + ip + ", Account:" + account);
				}
			}
		}
		methodName = objectName + "." + methodName;
		logger.debug("methodName:" + methodName);

		// ���صĹ�����ϵͳ�����б���
		if (findSysFunction(methodName)) {
			// ���û������б��У�ͨ��
			if (findUserFunction(account, methodName)) {
				logger.debug("method is in user functions");
				authorized = true;
			}
		} else {// ���صĹ��ܲ���ϵͳ�����б��У�ͨ��
			logger.debug("method isn't in sys functions");
			authorized = true;
		}

		// ��¼�û�����
		createLog(account, methodName, ip, authorized ? 1 : 0);

		if (!authorized) {
			throw new AuthorizeException("No Privileges.");
		}
	}

	/**
	 * ��¼��־
	 * 
	 * @param methodName
	 * @param ip
	 * @param flag
	 */
	private void createLog(String account, String methodName, String ip,
			int flag) {
		SysLog log = new SysLog();
		SysUser user = (SysUser) ContextUtil.get(account);
		if (user != null) {
			log.setAccount(user.getName() + "[" + user.getAccount() + "]");
			log.setIp(ip);
			log.setCreateTime(new Date());
			log.setOperate(methodName);
			log.setFlag(flag);

			WebApplicationContext wac = (WebApplicationContext) ContextUtil
					.get("wac");
			SysLogService logService = (SysLogService) wac
					.getBean("sysLogProxy");
			logService.create(log);
		}
	}

	/**
	 * ��鹦���Ƿ����ϵͳ�����б���
	 * 
	 * @param methodName
	 * @return
	 */
	private boolean findSysFunction(String methodName) {
		boolean ret = false;
		try {
			// ϵͳ�����б��ڳ�ʼ��servlet�м���
			Iterator iter = ((List) ContextUtil.get("function")).iterator();
			logger.debug("function:" + iter);
			while (iter.hasNext()) {
				BaseDataInfo bdi = (BaseDataInfo) iter.next();
				logger.debug("sys function:" + bdi.getCode());
				if (bdi.getCode().equals(methodName)) {// �ҵ�
					ret = true;
					break;
				}
			}
			iter = null;
		} catch (Exception e) {
			logger.error(e);
		}
		return ret;
	}

	/**
	 * ��鹦���Ƿ�����û������б���
	 * 
	 * @param methodName
	 * @return
	 */
	private boolean findUserFunction(String account, String methodName) {
		boolean ret = false;
		// �û������ڵ�½�����
		SysUser user = (SysUser) ContextUtil.get(account);
		logger.debug("user:" + user);
		Iterator iter = user.getFunctions().iterator();// �û������б�
		while (iter.hasNext()) {
			SysFunction function = (SysFunction) iter.next();
			if (function.getFuncMethod().equals(methodName)) {
				ret = true;
				break;
			}
		}
		iter = null;
		return ret;
	}
}
