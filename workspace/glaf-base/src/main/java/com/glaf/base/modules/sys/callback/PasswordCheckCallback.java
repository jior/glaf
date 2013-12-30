package com.glaf.base.modules.sys.callback;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.AuthorizeService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.util.DateUtils;
import com.glaf.core.web.callback.CallbackProperties;
import com.glaf.core.web.callback.LoginCallback;

public class PasswordCheckCallback implements LoginCallback {
	private static final Log logger = LogFactory
			.getLog(PasswordCheckCallback.class);

	public void afterLogin(String actorId, HttpServletRequest request,
			HttpServletResponse response) {
		AuthorizeService authorizeService = ContextFactory
				.getBean("authorizeService");
		SysUserService sysUserService = ContextFactory
				.getBean("sysUserService");
		SysUser bean = authorizeService.login(actorId);
		Integer userLoginCount = 0;
		if (null != bean) {
			userLoginCount = bean.getLoginCount() == null ? 0 : bean
					.getLoginCount();
			Integer userIsChangePassword = bean.getIsChangePassword() == null ? 0
					: bean.getIsChangePassword();
			String changePassWordFlag = "close";
			int ruleSwitch = CallbackProperties.getInt("parameter.ruleSwitch");
			int loginCount = CallbackProperties.getInt("parameter.loginCount");
			int periodMonths = CallbackProperties
					.getInt("parameter.periodDays");
			int disabledMonths = CallbackProperties
					.getInt("parameter.disabledDays");
			if (ruleSwitch == 1) {// �����������
				if (userLoginCount <= loginCount && userIsChangePassword == 0) {// С��ָ����½����&&δ�޸����룬������޸�����
					changePassWordFlag = "open";
				}
				if (userLoginCount > loginCount && userIsChangePassword == 0) {// ����ָ������δ�޸����룬�����ʺţ����ܵ�¼ϵͳ
					logger.info("����ָ������δ�޸����룬�����ʺţ����ܵ�¼ϵͳ");
					bean.setBlocked(1);
					bean.setUpdateDate(new Date());
					sysUserService.updateUser(bean);
					bean = null;
				}
				if ("close".equals(changePassWordFlag) && null != bean) {// ����(3����)�����޸����룺��ǰ5�쿪ʼ�����޸����루δ�޸ĵ����ÿ�ε�½�����ѣ�������δ�޸����룬�������û��ʺţ����ܵ�¼ϵͳ��
					if (null != bean.getLastChangePasswordDate()) {
						Date toDate = DateUtils.getDateAfter(
								bean.getLastChangePasswordDate(), periodMonths);
						long daysDiff = DateUtils.dateDiff(new Date(), toDate);
						if (daysDiff < 0) {
							logger.info("����(3����)�����޸����룺��ǰ5�쿪ʼ�����޸����루δ�޸ĵ����ÿ�ε�½�����ѣ�������δ�޸����룬�������û��ʺţ����ܵ�¼ϵͳ");
							bean.setBlocked(1);
							bean.setUpdateDate(new Date());
							sysUserService.updateUser(bean);
							bean = null;
						} else if (daysDiff <= 5 && userIsChangePassword != 2) {
							changePassWordFlag = "open";
						} else if (daysDiff > 5) {
							if (bean.getIsChangePassword() == 2) {
								bean.setIsChangePassword(1);
								bean.setUpdateDate(new Date());
								sysUserService.updateUser(bean);
							}
						}
					}
				}
				if (null != bean && null != bean.getLastLoginDate()) {// ����Ծ�û��ʺ�������N����δ��¼��ϵͳ�������û��ʺţ����ܵ�¼ϵͳ��
					Date toDate = DateUtils.getDateAfter(
							bean.getLastLoginDate(), disabledMonths);
					long daysDiff = DateUtils.dateDiff(new Date(), toDate);
					if (daysDiff < 0) {
						logger.info("����Ծ�û��ʺ�������N����δ��¼��ϵͳ�������û��ʺţ����ܵ�¼ϵͳ");
						bean.setBlocked(1);
						bean.setUpdateDate(new Date());
						sysUserService.updateUser(bean);
						bean = null;
					}
				}
			}
			request.setAttribute("changePassWordFlag", changePassWordFlag);
		}

	}

}
