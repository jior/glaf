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

package com.glaf.mail.website.springmvc;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glaf.mail.business.MailDataFacede;
import com.glaf.mail.domain.MailItem;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.RequestUtils;

@Controller("/website/mail/receive")
@RequestMapping("/mail/receive")
public class MailReceiveController {

	protected static Log logger = LogFactory
			.getLog(MailReceiveController.class);

	protected MailDataFacede mailDataFacede;

	@javax.annotation.Resource
	public void setMailDataFacede(MailDataFacede mailDataFacede) {
		this.mailDataFacede = mailDataFacede;
	}

	@ResponseBody
	@RequestMapping("/view")
	public void view(HttpServletRequest request) {
		String messageId = request.getParameter("messageId");
		if (messageId != null) {
			messageId = RequestUtils.decodeString(messageId);
			Map<String, Object> dataMap = JsonUtils.decode(messageId);
			String taskId = (String) dataMap.get("taskId");
			String itemId = (String) dataMap.get("itemId");
			if (taskId != null && itemId != null) {
				MailItem mailItem = mailDataFacede.getMailItem(taskId, itemId);
				if (mailItem != null) {
					mailItem.setReceiveStatus(1);
					mailItem.setReceiveDate(new Date());
					mailItem.setReceiveIP(RequestUtils.getIPAddress(request));
					String contentType = request.getContentType();
					mailItem.setContentType(contentType);
					logger.debug("contentType:" + contentType);
					java.util.Enumeration<String> e = request.getHeaderNames();
					while (e.hasMoreElements()) {
						String name = e.nextElement();
						logger.debug(name + "=" + request.getHeader(name));
					}
					String userAgent = request.getHeader("user-agent");
					if (userAgent != null) {
						if (userAgent.indexOf("Chrome") != -1) {
							mailItem.setBrowser("Chrome");
						} else if (userAgent.indexOf("MSIE") != -1) {
							mailItem.setBrowser("IE");
						} else if (userAgent.indexOf("Firefox") != -1) {
							mailItem.setBrowser("Firefox");
						}
						if (userAgent.indexOf("Windows") != -1) {
							mailItem.setClientOS("Windows");
						}
					}
					mailDataFacede.updateMail(taskId, mailItem);
				}
			}
		}
	}

}