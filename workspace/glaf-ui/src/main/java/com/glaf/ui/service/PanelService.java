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

package com.glaf.ui.service;

import java.util.List;

 
import com.glaf.ui.model.Panel;
import com.glaf.ui.model.UserPanel;

public interface PanelService {

	Panel getPanel(String panelId);

	Panel getPanelByName(String name);

	Panel getPanelByTitle(String title);

	/**
	 * 获取用户自定义面板
	 * 
	 * @return
	 */
	List<Panel> getPanels(String actorId);

	/**
	 * 获取系统面板
	 * 
	 * @return
	 */
	List<Panel> getSystemPanels();

	UserPanel getUserPanel(String actorId);

	void installPanels(List<Panel> panels);

	void savePanel(Panel panel);

	void saveUserPanel(UserPanel userPanel);

	void updatePanel(Panel panel);

}