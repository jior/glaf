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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.glaf.setup.conf.Database;
import com.glaf.setup.conf.DatabaseConfig;

/**
 * 应用信息配置（目前只需配置应用路径）
 * 
 * 
 */
public class AppConfig extends JDialog {
	private static final long serialVersionUID = 1L;
	public static String appPath = "";
	private JLabel serverPath; // 服务器路径
	private JTextField serverPathText;
	private JButton browseFileBtn; // 路径浏览

	private JButton saveBtn; // 保存配置
	private JButton cancelBtn; // 清空

	public AppConfig() {
		initskin();

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());

		int y = 0;
		mainPanel.add(new JPanel(), new GBC(0, y, 1, 1).setInsets(2, 2, 2, 2)
				.setFill(GBC.BOTH).setWeight(30, 0));// 填充左边空白位置
		mainPanel.add(new JPanel(), new GBC(1, y, 1, 1).setInsets(2, 2, 2, 2)
				.setFill(GBC.BOTH).setWeight(40, 10));
		mainPanel.add(new JPanel(), new GBC(2, y, 1, 1).setInsets(2, 2, 2, 2)
				.setFill(GBC.BOTH).setWeight(30, 0));// 填充右边空白位置

		y++;
		mainPanel.add(this.initServerPanel(),
				new GBC(1, y, 1, 1).setInsets(2, 2, 2, 2).setFill(GBC.BOTH)
						.setWeight(100, 0));

		y++;
		mainPanel.add(new JPanel(), new GBC(1, y, 1, 1).setInsets(2, 2, 2, 2)
				.setFill(GBC.BOTH).setWeight(100, 90));

		y++;
		mainPanel.add(this.initButton(),
				new GBC(1, y, 1, 1).setInsets(2, 2, 2, 2).setFill(GBC.BOTH)
						.setWeight(40, 0));

		if (!appPath.equals("")) {
			checkAppPath(appPath);
		}

		this.add(mainPanel);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setBounds(0, 0, 400, 200);
		this.setLocationRelativeTo(null);
		this.setModal(true);
		this.setTitle("应用配置");
		// this.setVisible(true);
	}

	// 网站服务器面板初始化
	private JPanel initServerPanel() {
		serverPath = new JLabel("应用路径"); // 服务器路径
		serverPathText = new JTextField(appPath);

		browseFileBtn = new JButton("浏览");

		// 选择服务器文件路径
		browseFileBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setApproveButtonText("选择");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				File f = null;
				try {
					String currentDirectory = ".";
					if (!serverPathText.getText().trim().equals("")) {
						currentDirectory = serverPathText.getText().trim();
					}
					f = new File(new File(currentDirectory).getCanonicalPath());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				chooser.setCurrentDirectory(f);

				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {

					if (chooser.getSelectedFile().isDirectory()
							&& chooser.getSelectedFile().exists()) {
						String path = chooser.getSelectedFile()
								.getAbsolutePath();
						serverPathText.setText(path);
						serverPathText.setToolTipText(path);
						checkAppPath(path);
					}
				}

			}
		});

		JPanel serverPanel = new JPanel();
		serverPanel.setLayout(new GridBagLayout());
		serverPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("应用服务器配置"));

		int row = 0;

		row++;
		serverPanel
				.add(serverPath, new GBC(0, row, 1, 1).setInsets(2, 2, 2, 2));
		serverPanel.add(serverPathText,
				new GBC(1, row, 1, 1).setInsets(2, 2, 2, 2).setFill(GBC.BOTH)
						.setWeight(100, 0));
		serverPanel.add(browseFileBtn,
				new GBC(2, row, 1, 1).setInsets(2, 2, 2, 2));

		return serverPanel;
	}

	// 初始按钮面板 保存配置事件处理, 测试连接事件处理
	private JPanel initButton() {
		saveBtn = new JButton(" 确  定 "); // 保存配置
		cancelBtn = new JButton(" 取 消 "); // 测试连接

		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appPath = serverPathText.getText().trim();
				if (appPath.length() > 0) {
					boolean ok = checkAppPath(appPath);
					if (ok) {
						AppConfig.this.dispose();
					} else {
						javax.swing.JOptionPane.showMessageDialog(null,
								"应用路径不正确，请重新选择！");
						lockEditable(false);
					}
				} else {
					javax.swing.JOptionPane
							.showMessageDialog(null, "必须选择应用路径！");
					lockEditable(false);
				}
			}
		});

		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// System.exit(0);
			}
		});

		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new GridBagLayout());
		btnPanel.add(saveBtn, new GBC(0, 0, 1, 1).setInsets(2, 2, 2, 20));
		// btnPanel.add(cancelBtn, new GBC(1, 0, 1, 1).setInsets(2, 20, 2, 2));

		return btnPanel;
	}

	private boolean checkAppPath(String path) {
		DatabaseConfig dbConfig = new DatabaseConfig();

		// 这个数据来于面板路径
		List<Database> dbList = dbConfig.getDatabases(path);
		if (dbList.size() > 0) {
			lockEditable(true);
			return true;
		} else {
			javax.swing.JOptionPane.showMessageDialog(null, "应用路径不正确，请重新选择");
			lockEditable(false);
			return false;
		}
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

	private void lockEditable(boolean b) {
		saveBtn.setEnabled(b); // 保存配置
	}

}