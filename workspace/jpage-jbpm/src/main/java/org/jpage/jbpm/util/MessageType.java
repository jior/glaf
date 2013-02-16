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


package org.jpage.jbpm.util;

public class MessageType {

	/**
	 * ����Ϣ
	 */
	public final static int NEW = 0;

	/**
	 * �Ѿ��Ķ�����Ϣ
	 */
	public final static int HAS_READ = 1;

	/**
	 * �Ѿ�����
	 */
	public final static int HAS_SENT = 2;
	
	/**
	 * ���������з����ʼ���Ϣ
	 */
	public final static int PROCESS_MAIL_MESSAGE = 2000;

	/**
	 * ���������е���Ϣ״̬
	 */
	public final static int PROCESS_RUNNING_STATUS = 5000;

	/**
	 * ��������ɵ���Ϣ״̬
	 */
	public final static int PROCESS_FINISHED_STATUS = 10000;

	/**
	 * �����ʼ�
	 */
	public final static String MAIL_MESSAGE = "mail";

	/**
	 * �ֻ�����
	 */
	public final static String SMS_MESSAGE = "sms";

	/**
	 * ϵͳ����Ϣ
	 */
	public final static String SYSTEM_MESSAGE = "system";

	/**
	 * ��ʱ��Ϣ
	 */
	public final static String IM_MESSAGE = "im";

	/**
	 * ϵͳĬ�ϵ�
	 */
	public final static String DEFAULT_MESSAGE = "default";

}
