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

package com.glaf.mail.util;

import java.util.*;

public class MailStatus {

	public final static int NEW = 0; // ���ʼ�

	public final static int HAS_READ = 1; // �Ѷ��ʼ�

	public final static int DRAFT = 2; // �ݸ�

	public final static int HAS_SENT = 3; // �ѷ��ʼ�

	public final static int SEND_FAILED = 4; // ����ʧ��

	public final static int RUBBISH = 5; // �����ʼ�

	protected static Map<Integer, String> names = new java.util.concurrent.ConcurrentHashMap<Integer, String>();

	static {
		names.put(NEW, "���ʼ�");
		names.put(HAS_READ, "�Ѷ��ʼ�");
		names.put(DRAFT, "�ݸ�");
		names.put(HAS_SENT, "�ѷ��ʼ�");
		names.put(SEND_FAILED, "����ʧ��");
		names.put(RUBBISH, "�����ʼ�");
	}

	private MailStatus() {
	}

}