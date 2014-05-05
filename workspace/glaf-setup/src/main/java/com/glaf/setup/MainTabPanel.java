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

package com.glaf.setup;

import javax.swing.JTabbedPane;

import com.glaf.setup.tabui.DataBaseConfigPanel;
import com.glaf.setup.tabui.MailServerConfigPanel;

//标签面板，
public class MainTabPanel extends JTabbedPane {
	private static final long serialVersionUID = 1L;

	public MainTabPanel() {

		DataBaseConfigPanel dbConfig = new DataBaseConfigPanel();

		this.add("数据库配置", dbConfig);

		MailServerConfigPanel escp = new MailServerConfigPanel();
		this.add("邮件服务器配置", escp);
	}
}