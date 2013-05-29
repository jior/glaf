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

package com.glaf.base.modules;

import javax.servlet.http.HttpServlet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.utils.ContextUtil;

public class InitBaseDataServlet extends HttpServlet {
	private static final long serialVersionUID = 2072103368980714549L;

	private final static Log logger = LogFactory
			.getLog(InitBaseDataServlet.class);

	private BaseDataManager bdm = BaseDataManager.getInstance();// ������Ϣ����

	public void init() {
		long startTime = System.currentTimeMillis();
		logger.info("��ʼ��������Ϣ...");
		try {
			bdm.refreshBaseData();// ˢ������
			logger.info("��ʼ��������Ϣ���.");
			// װ��ϵͳ�����б�
			ContextUtil.put("function", bdm.getBaseData(Constants.SYS_FUNCTIONS));
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("��ʼ��������Ϣʧ�ܣ�");
		}
		logger.info("��ʱ��" + (System.currentTimeMillis() - startTime) + " ms.");
	}
}