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
			if (ruleSwitch == 1) {// 允许密码策略
				if (userLoginCount <= loginCount && userIsChangePassword == 0) {// 小于指定登陆次数&&未修改密码，需提出修改密码
					changePassWordFlag = "open";
				}
				if (userLoginCount > loginCount && userIsChangePassword == 0) {// 超过指定次数未修改密码，锁定帐号，不能登录系统
					logger.info("超过指定次数未修改密码，锁定帐号，不能登录系统");
					bean.setBlocked(1);
					bean.setUpdateDate(new Date());
					sysUserService.updateUser(bean);
					bean = null;
				}
				if ("close".equals(changePassWordFlag) && null != bean) {// 定期(3个月)提醒修改密码：提前5天开始提醒修改密码（未修改的情况每次登陆都提醒）；超期未修改密码，则锁定用户帐号，不能登录系统；
					if (null != bean.getLastChangePasswordDate()) {
						Date toDate = DateUtils.getDateAfter(
								bean.getLastChangePasswordDate(), periodMonths);
						long daysDiff = DateUtils.dateDiff(new Date(), toDate);
						if (daysDiff < 0) {
							logger.info("定期(3个月)提醒修改密码：提前5天开始提醒修改密码（未修改的情况每次登陆都提醒）；超期未修改密码，则锁定用户帐号，不能登录系统");
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
				if (null != bean && null != bean.getLastLoginDate()) {// 不活跃用户帐号锁定：N个月未登录过系统则锁定用户帐号，不能登录系统；
					Date toDate = DateUtils.getDateAfter(
							bean.getLastLoginDate(), disabledMonths);
					long daysDiff = DateUtils.dateDiff(new Date(), toDate);
					if (daysDiff < 0) {
						logger.info("不活跃用户帐号锁定：N个月未登录过系统则锁定用户帐号，不能登录系统");
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
