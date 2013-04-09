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

package com.glaf.base.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.business.AuthorizeBean;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.core.util.RequestUtils;

public class RequestUtil {
	protected final static Log logger = LogFactory.getLog(RequestUtil.class);

	public static SysUser getLoginUser(HttpServletRequest request) {
		String actorId = RequestUtils.getActorId(request);
		SysUser sysUser = null;
		if (actorId != null) {
			AuthorizeBean bean = new AuthorizeBean();
			sysUser = bean.getUser(actorId);
		}
		return sysUser;
	}

}