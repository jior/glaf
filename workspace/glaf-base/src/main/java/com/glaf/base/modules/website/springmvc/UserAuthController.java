/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.glaf.base.modules.website.springmvc;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.config.BaseConfiguration;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.ComplexUserService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.online.domain.UserOnline;
import com.glaf.base.online.service.UserOnlineService;
import com.glaf.base.utils.ContextUtil;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.security.DigestUtil;
import com.glaf.core.util.RequestUtils;

@Controller("/public/auth")
@RequestMapping("/public/auth")
public class UserAuthController {

	protected static final Log logger = LogFactory
			.getLog(UserAuthController.class);

	protected static Configuration conf = BaseConfiguration.create();

	protected SysUserService sysUserService;

	protected ComplexUserService complexUserService;

	protected UserOnlineService userOnlineService;

	public UserAuthController() {

	}

	@ResponseBody
	@RequestMapping
	public byte[] auth(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String json = request.getParameter("json");
		JSONObject jsonObject = JSON.parseObject(json);
		JSONObject result = new JSONObject();
		String actorId = jsonObject.getString("x");
		String password = jsonObject.getString("y");
		String email = jsonObject.getString("email");
		if (sysUserService.findByMail(email) != null) {
			result.put("status", 500);
			result.put("message", "邮箱已经存在，请换个再来！");
			return result.toJSONString().getBytes("UTF-8");
		}
		SysUser user = sysUserService.findByAccount(actorId);
		int status = 0;
		if (user != null) {
			// 用户已经存在，验证用户名及密码是否正确
			String pwd = DigestUtil.digestString(password, "MD5");
			if (pwd != null && !user.getPassword().equals(pwd)) {
				status = 500;
				result.put("status", 500);
				result.put("message", "密码不匹配");
			} else if (user.getBlocked() == 1) {
				status = 500;
				result.put("status", 500);
				result.put("message", "帐号已经禁止");
			} else {
				status = 200;
				result.put("status", 200);
				result.put("message", "验证通过");
			}
		} else {
			user = new SysUser();
			user.setAccount(actorId);

			try {
				String pwd = DigestUtil.digestString(password, "MD5");
				user.setPassword(pwd);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			long deptId = 0;
			if (jsonObject.containsKey("deptId")) {
				deptId = Long.parseLong(jsonObject.getString("deptId"));
			}
			user.setDeptId(deptId);

			user.setName(jsonObject.getString("name"));
			if (user.getName() == null) {
				user.setName(actorId);
			}
			user.setMobile(jsonObject.getString("mobile"));
			user.setEmail(jsonObject.getString("email"));
			user.setUserType(0);
			user.setAccountType(2);
			user.setEvection(0);
			user.setCreateTime(new Date());
			user.setLastLoginTime(new Date());
			user.setLastChangePasswordDate(new Date());
			user.setIsChangePassword(0);
			user.setCreateBy("website");
			user.setUpdateBy("website");
			List<String> roleCodes = new java.util.ArrayList<String>();
			roleCodes.add("website");
			try {
				if (complexUserService.createUser(user, roleCodes)) {
					status = 200;
				}
			} catch (Exception ex) {
				status = 500;
				logger.error(ex);
			}
			if (status == 200) {// 保存成功
				result.put("status", 200);
				result.put("message", "注册成功");
			} else if (status == 500) {
				result.put("status", 500);
				result.put("message", "注册失败");
			}
		}

		if (status == 200) {

			HttpSession session = request.getSession(true);
			ContextUtil.put(actorId, user);// 传入全局变量
			RequestUtils.setLoginUser(request, response, "default", actorId);

			java.util.Random random = new java.util.Random();
			String token = org.apache.commons.codec.digest.DigestUtils
					.md5Hex(actorId)
					+ Math.abs(random.nextInt(9999))
					+ com.glaf.core.util.UUID32.getUUID()
					+ Math.abs(random.nextInt(9999));
			result.put("actorId", actorId);
			result.put("token", token);

			try {
				if (user.getLoginCount() != null) {
					user.setLoginCount(user.getLoginCount() + 1);
				} else {
					user.setLoginCount(1);
				}

				// 登录成功，修改最近一次登录时间
				user.setLastLoginDate(new Date());
				sysUserService.updateUser(user);

				UserOnline online = new UserOnline();
				online.setActorId(user.getActorId());
				online.setName(user.getName());
				online.setCheckDate(new Date());
				online.setLoginDate(new Date());
				online.setLoginIP(RequestUtils.getIPAddress(request));
				online.setSessionId(session.getId());
				userOnlineService.login(online);
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			}
		}

		return result.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 登录
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request,
			HttpServletResponse response) {
		String ip = RequestUtils.getIPAddress(request);
		/**
		 * 允许从指定的机器上通过用户名密码登录
		 */
		if (StringUtils.contains(conf.get("login.allow.ip", "127.0.0.1"), ip)
				|| StringUtils.contains(
						SystemConfig.getString("login.allow.ip", "127.0.0.1"),
						ip)) {
			String actorId = request.getParameter("x");
			String password = request.getParameter("y");
			HttpSession session = request.getSession(true);
			java.util.Random random = new java.util.Random();
			String rand = Math.abs(random.nextInt(999999))
					+ com.glaf.core.util.UUID32.getUUID()
					+ Math.abs(random.nextInt(999999));
			session = request.getSession(true);
			if (session != null) {
				session.setAttribute("x_y", rand);
			}
			String url = request.getContextPath() + "/mx/login/doLogin?x="
					+ actorId + "&y=" + rand + password;
			try {
				response.sendRedirect(url);
				return null;
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return new ModelAndView("/modules/login");
	}

	@ResponseBody
	@RequestMapping("/register")
	public byte[] register(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String json = request.getParameter("json");
		JSONObject jsonObject = JSON.parseObject(json);
		JSONObject result = new JSONObject();
		String actorId = jsonObject.getString("x");
		String password = jsonObject.getString("y");
		String email = jsonObject.getString("email");
		if (sysUserService.findByMail(email) != null) {
			result.put("status", 500);
			result.put("message", "邮箱已经存在，请换个再来！");
			return result.toJSONString().getBytes("UTF-8");
		}
		SysUser user = sysUserService.findByAccount(actorId);
		int status = 0;
		if (user != null) {
			status = 400;
			result.put("status", 400);
			result.put("message", "用户已经存在！");
		} else {
			user = new SysUser();
			user.setAccount(actorId);

			try {
				String pwd = DigestUtil.digestString(password, "MD5");
				user.setPassword(pwd);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			long deptId = 0;
			if (jsonObject.containsKey("deptId")) {
				deptId = Long.parseLong(jsonObject.getString("deptId"));
			}
			user.setDeptId(deptId);

			user.setName(jsonObject.getString("name"));
			if (user.getName() == null) {
				user.setName(actorId);
			}
			user.setMobile(jsonObject.getString("mobile"));
			user.setEmail(jsonObject.getString("email"));
			user.setUserType(0);
			user.setAccountType(2);
			user.setEvection(0);
			user.setCreateTime(new Date());
			user.setLastLoginTime(new Date());
			user.setLastChangePasswordDate(new Date());
			user.setIsChangePassword(0);
			user.setCreateBy("website");
			user.setUpdateBy("website");
			List<String> roleCodes = new java.util.ArrayList<String>();
			roleCodes.add("website");
			try {
				if (complexUserService.createUser(user, roleCodes)) {
					status = 200;
				}
			} catch (Exception ex) {
				status = 500;
				logger.error(ex);
			}
			if (status == 200) {// 保存成功
				result.put("status", 200);
				result.put("message", "注册成功");
			} else if (status == 500) {
				result.put("status", 500);
				result.put("message", "注册失败");
			}
		}

		return result.toJSONString().getBytes("UTF-8");
	}

	@javax.annotation.Resource
	public void setComplexUserService(ComplexUserService complexUserService) {
		this.complexUserService = complexUserService;
	}

	@javax.annotation.Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	@javax.annotation.Resource
	public void setUserOnlineService(UserOnlineService userOnlineService) {
		this.userOnlineService = userOnlineService;
	}

}
