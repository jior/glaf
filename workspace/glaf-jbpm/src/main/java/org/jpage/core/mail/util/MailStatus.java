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

	public static String NEW = "0"; // ���ʼ�

	public static String HAVE_READ = "1"; // �Ѷ��ʼ�

	public static String DRAFT = "2"; // �ݸ�

	public static String HAVE_SENT = "3"; // �ѷ��ʼ�

	public static String SEND_FAILED = "4"; // ����ʧ��

	public static String RUBBISH = "5"; // ��ʱ�ʼ�

	public static String TEMP = "T"; // ��ʱ�ʼ�

	protected static Map names = new HashMap();

	static {
		names.put(NEW, "���ʼ�");
		names.put(HAVE_READ, "�Ѷ��ʼ�");
		names.put(DRAFT, "�ݸ�");
		names.put(HAVE_SENT, "�ѷ��ʼ�");
		names.put(SEND_FAILED, "����ʧ��");
		names.put(RUBBISH, "�����ʼ�");
		names.put(TEMP, "��ʱ�ʼ�");
	}

	private MailStatus() {
	}

	public static String getStatusDesc(String status) {
		String tReturn = null;
		if ((status == null) || (status.trim().equals(""))) {
			status = NEW; // Ĭ�ϣ����ʼ�
		}
		status = status.trim();
		Object name = names.get(status);
		// Ĭ�ϣ����ʼ�
		tReturn = (name == null) ? (names.get(NEW).toString()) : (name
				.toString());
		return tReturn;
	}
}