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


package org.jpage.core.mail.util;

import java.util.HashMap;
import java.util.Map;

public class MailStatus {

	public static String NEW = "0"; // 新邮件

	public static String HAVE_READ = "1"; // 已读邮件

	public static String DRAFT = "2"; // 草稿

	public static String HAVE_SENT = "3"; // 已发邮件

	public static String SEND_FAILED = "4"; // 发送失败

	public static String RUBBISH = "5"; // 临时邮件

	public static String TEMP = "T"; // 临时邮件

	protected static Map names = new HashMap();

	static {
		names.put(NEW, "新邮件");
		names.put(HAVE_READ, "已读邮件");
		names.put(DRAFT, "草稿");
		names.put(HAVE_SENT, "已发邮件");
		names.put(SEND_FAILED, "发送失败");
		names.put(RUBBISH, "垃圾邮件");
		names.put(TEMP, "临时邮件");
	}

	private MailStatus() {
	}

	public static String getStatusDesc(String status) {
		String tReturn = null;
		if ((status == null) || (status.trim().equals(""))) {
			status = NEW; // 默认：新邮件
		}
		status = status.trim();
		Object name = names.get(status);
		// 默认：新邮件
		tReturn = (name == null) ? (names.get(NEW).toString()) : (name
				.toString());
		return tReturn;
	}
}