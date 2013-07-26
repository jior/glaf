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

import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class Main extends JFrame {
	public Main() {
	}

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		Main sc = new Main();
		sc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sc.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		AppConfig appConfig = new AppConfig();
		appConfig.setVisible(true);
		sc.init();
		sc.setSize(430, 550);
		sc.setTitle("配置工具");
		sc.setLocationRelativeTo(null);
		sc.setVisible(true);
	}

	// 初始化皮肤
	public void initskin() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

		} catch (Exception e) {
			System.out.println("装载皮肤错误" + e);
		}
	}

	public void init() {
		initskin();
		this.getContentPane().setLayout(new GridBagLayout());

		MainMenu menu = new MainMenu();
		this.setJMenuBar(menu);

		// 标签面板，所有的显示界面都放在tab标签里面
		MainTabPanel mainTabPanel = new MainTabPanel();
		// this.getContentPane().add(mainTabPanel,BorderLayout.CENTER);
		getContentPane().add(mainTabPanel,
				new GBC(0, 0, 1, 1).setFill(GBC.BOTH).setWeight(100, 100));

	}

}