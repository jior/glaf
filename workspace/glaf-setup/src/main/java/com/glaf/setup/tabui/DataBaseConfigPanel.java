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

package com.glaf.setup.tabui;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.util.List;

import com.glaf.setup.AppConfig;
import com.glaf.setup.GBC;
import com.glaf.setup.conf.Database;
import com.glaf.setup.conf.DatabaseConfig;

/**
 * 数据库配置界面
 */
public class DataBaseConfigPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public final String sp = System.getProperty("file.separator");

	private JLabel jarPath; // 应用路径
	private JTextField jarPathText;
	private JButton upLoadJarBtn; // 路径浏览
	private JButton browseJarBtn; // 路径浏览

	private JLabel dataDrive; // 数据库驱动
	private JComboBox dataDriveBox;

	private JLabel serverIp; // 数据库服务器地址
	private JTextField serverIpText;

	private JLabel dataBaseName; // 数据库名称
	private JTextField dataBaseNameText;

	private JLabel dataBasePort; // 数据库端口
	private JTextField dataBasePortText;

	private JLabel userName; // 用户名
	private JTextField userNameText;

	private JLabel password; // 密码
	private JPasswordField passwordText;

	private JButton saveBtn; // 保存配置
	private JButton connectBtn; // 清空

	public DataBaseConfigPanel() {
		this.setLayout(new GridBagLayout());

		int row = 0;
		this.add(new JPanel(), new GBC(0, row, 1, 1).setInsets(2, 2, 2, 2)
				.setFill(GBC.BOTH).setWeight(30, 0));// 填充左边空白位置
		this.add(new JPanel(), new GBC(1, row, 1, 1).setInsets(2, 2, 2, 2)
				.setFill(GBC.BOTH).setWeight(15, 10));
		this.add(new JPanel(), new GBC(2, 0, 1, 1).setInsets(2, 2, 2, 2)
				.setFill(GBC.BOTH).setWeight(30, 0));// 填充右边空白位置

		row++;
		this.add(initDataBasePanel(),
				new GBC(1, row, 1, 1).setInsets(2, 2, 2, 2).setFill(GBC.BOTH)
						.setWeight(40, 0));

		row++;
		this.add(new JPanel(), new GBC(0, row, 3, 1).setInsets(2, 2, 2, 2)
				.setFill(GBC.BOTH).setWeight(100, 100));// 填充底部空白位置

		row++;
		this.add(initButton(), new GBC(1, row, 1, 1).setInsets(2, 2, 2, 2)
				.setFill(GBC.BOTH).setWeight(100, 100));

	}

	// 初始化数据库配置面板
	private JPanel initDataBasePanel() {
		jarPath = new JLabel("驱动包路径"); // 服务器路径
		jarPathText = new JTextField();
		upLoadJarBtn = new JButton("上传");
		browseJarBtn = new JButton("浏览"); // 驱动浏览

		// 上传
		upLoadJarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filePaths = jarPathText.getText();
				String jarToolText = "";
				String[] JarFiles = filePaths.split(";");
				if (JarFiles.length > 0) {
					String appPath = AppConfig.appPath;
					String libPath = appPath + sp + "WEB-INF" + sp + "lib"; // 驱动路径
					for (int i = 0; i < JarFiles.length; i++) {
						File jarFile = new File(JarFiles[i]);

						// 先判断所选驱动包是否存在
						if (jarFile.exists()) {
							// 先判断应用路径是否正确 serverPathText
							File appFile = new File(appPath);
							if (appFile.exists() == false) {
								javax.swing.JOptionPane.showConfirmDialog(null,
										"应用路径不正确，请重新选择!");
								return;
							}

							String fileName = jarFile.getName();
							File newFile = new File(libPath + sp + fileName);
							int choice = JOptionPane.YES_OPTION;
							if (newFile.exists()) {
								choice = JOptionPane.showConfirmDialog(null,
										"应用路径" + libPath + "已经存在" + fileName
												+ "驱动，要替换它吗？", "提示",
										JOptionPane.YES_NO_OPTION,
										JOptionPane.WARNING_MESSAGE);
							}
							if (choice == JOptionPane.YES_OPTION) {
								boolean uploadFlag = true;
								uploadFlag = copyFile(
										jarFile.getAbsolutePath(),
										newFile.getAbsolutePath()); // jarFile.renameTo(newFile);
								if (uploadFlag == false) {
									JOptionPane.showMessageDialog(null,
											JarFiles[i]
													+ "驱动包上传失败，请检查上传路径是否正确！");
									return;
								} else {
									jarToolText = jarToolText + JarFiles[i]
											+ ";\n";
								}
							}
						} else {
							JOptionPane.showMessageDialog(null, JarFiles[i]
									+ "驱动包不存在，请重新选择！");
							return;
						}
					}
					if (!jarToolText.trim().equals("")) {
						JOptionPane.showMessageDialog(null, "成功上传\n"
								+ jarToolText + "到应用路径\n" + libPath, "驱动包上传成功",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});

		// 选择驱动jar路径
		browseJarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setMultiSelectionEnabled(true);
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"jar 驱动包", "jar");
				chooser.addChoosableFileFilter(filter);
				chooser.setApproveButtonText("选择");
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				File f = null;
				try {
					String currentDirectory = ".";
					if (!AppConfig.appPath.equals("")) {
						currentDirectory = AppConfig.appPath;
					}
					f = new File(new File(currentDirectory).getCanonicalPath());

				} catch (Exception ex) {
					ex.printStackTrace();
				}
				chooser.setCurrentDirectory(f);

				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {

					if (chooser.getSelectedFile().isFile()
							&& chooser.getSelectedFile().exists()) {

						File[] jarFile = chooser.getSelectedFiles();
						jarPathText.setText("");
						if (jarFile.length > 0) {
							StringBuilder allFileAbsolutePath = new StringBuilder();
							for (File f1 : jarFile) {
								// System.out.println("aaaaa:" +
								// f1.getAbsolutePath());
								if (f1.getAbsolutePath().endsWith(".jar")) {
									allFileAbsolutePath.append(f1
											.getAbsolutePath());
									allFileAbsolutePath.append(";");

								}
							}
							String pathText = allFileAbsolutePath.toString()
									.trim();
							jarPathText.setText(pathText);

							pathText = pathText.replace(";", ";<br>");
							jarPathText.setToolTipText("<html>" + pathText
									+ "</html>");

						}
					}
				}

			}
		});

		dataDrive = new JLabel("数据库类型"); // 数据库驱动
		dataDriveBox = new JComboBox(new Object[] { "" });
		dataDriveBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object item = dataDriveBox.getSelectedItem();
				if (item != null && item instanceof Database) {
					Database db = (Database) item;
					dataBasePortText.setText(String.valueOf(db.getPort()));
				}
			}
		});
		DatabaseConfig dbConfig = new DatabaseConfig();
		// 这个数据来于面板路径
		List<Database> dbList = dbConfig.getDatabases(AppConfig.appPath);
		if (dbList.size() > 0) {
			for (int i = 0; i < dbList.size(); i++) {
				Database db = (Database) dbList.get(i);
				dataDriveBox.addItem(db);
			}
		}

		serverIp = new JLabel("数据库地址"); // 数据库服务器地址
		serverIpText = new JTextField("127.0.0.1");

		dataBaseName = new JLabel("数据库名称"); // 数据库名称
		dataBaseNameText = new JTextField("pMagic");

		dataBasePort = new JLabel("数据库端口"); // 端口
		dataBasePortText = new JTextField("");

		userName = new JLabel("用    户   名"); // 用户名
		userNameText = new JTextField();

		password = new JLabel("密            码"); // 密码
		passwordText = new JPasswordField();

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("数据库配置"));

		int row = 0;
		mainPanel.add(jarPath, new GBC(0, row, 1, 1).setInsets(2, 2, 2, 2));
		JPanel jarPanel = new JPanel();
		jarPanel.setLayout(new GridBagLayout());
		jarPanel.add(jarPathText, new GBC(0, 0, 1, 1).setInsets(2, 2, 2, 2)
				.setFill(GBC.BOTH).setWeight(100, 0));
		jarPanel.add(upLoadJarBtn, new GBC(1, 0, 1, 1).setInsets(2, 2, 2, 2));
		jarPanel.add(browseJarBtn, new GBC(2, 0, 1, 1).setInsets(2, 2, 2, 2));
		mainPanel.add(jarPanel, new GBC(1, row, 2, 1).setInsets(2, 2, 2, 2)
				.setFill(GBC.BOTH).setWeight(100, 0));

		row++;
		mainPanel.add(dataDrive, new GBC(0, row, 1, 1).setInsets(10, 2, 2, 2));
		mainPanel.add(dataDriveBox,
				new GBC(1, row, 1, 1).setInsets(10, 2, 2, 2));
		mainPanel.add(new JPanel(), new GBC(2, row, 1, 1)
				.setInsets(10, 2, 2, 2).setAnchor(GBC.WEST).setFill(GBC.BOTH)
				.setWeight(100, 0));// 填充空白

		row++;
		mainPanel.add(serverIp, new GBC(0, row, 1, 1).setInsets(2, 2, 2, 2));
		mainPanel.add(serverIpText, new GBC(1, row, 2, 1).setInsets(2, 2, 2, 2)
				.setFill(GBC.BOTH).setWeight(80, 0));

		row++;
		mainPanel
				.add(dataBaseName, new GBC(0, row, 1, 1).setInsets(2, 2, 2, 2));
		mainPanel.add(dataBaseNameText,
				new GBC(1, row, 2, 1).setInsets(2, 2, 2, 2).setFill(GBC.BOTH)
						.setWeight(80, 0));

		row++;
		mainPanel
				.add(dataBasePort, new GBC(0, row, 1, 1).setInsets(2, 2, 2, 2));
		mainPanel.add(dataBasePortText,
				new GBC(1, row, 2, 1).setInsets(2, 2, 2, 2).setFill(GBC.BOTH)
						.setWeight(100, 0));

		row++;
		mainPanel.add(userName, new GBC(0, row, 1, 1).setInsets(2, 2, 2, 2));
		mainPanel.add(userNameText, new GBC(1, row, 2, 1).setInsets(2, 2, 2, 2)
				.setFill(GBC.BOTH).setWeight(100, 0));

		row++;
		mainPanel.add(password, new GBC(0, row, 1, 1).setInsets(2, 2, 2, 2));
		mainPanel.add(passwordText, new GBC(1, row, 2, 1).setInsets(2, 2, 2, 2)
				.setFill(GBC.BOTH).setWeight(100, 0));

		return mainPanel;
	}

	// 初始按钮面板 保存配置事件处理, 测试连接事件处理
	private JPanel initButton() {
		saveBtn = new JButton("保存配置"); // 保存配置
		connectBtn = new JButton("测试连接"); // 测试连接

		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Database db = checkConnect();
				if (db != null) {
					DatabaseConfig dbConfig = new DatabaseConfig();
					try {
						dbConfig.reconfig(AppConfig.appPath, db);
						javax.swing.JOptionPane
								.showMessageDialog(null, "保存成功！");
					} catch (RuntimeException ex) {
						ex.printStackTrace();
						javax.swing.JOptionPane.showMessageDialog(null, "保存失败，"
								+ ex.getMessage());
					} catch (Exception ex) {
						ex.printStackTrace();
						javax.swing.JOptionPane.showMessageDialog(null, "保存失败,"
								+ ex.getMessage());
					}
				}
			}
		});

		connectBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (checkConnect() != null) {
					javax.swing.JOptionPane.showMessageDialog(null, "数据库连接成功！");
				}
			}
		});

		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new GridBagLayout());
		btnPanel.add(saveBtn, new GBC(0, 0, 1, 1).setInsets(2, 2, 2, 20));
		btnPanel.add(connectBtn, new GBC(1, 0, 1, 1).setInsets(2, 20, 2, 2));

		return btnPanel;
	}

	private Database checkConnect() {
		Object obj = dataDriveBox.getSelectedItem();
		if (obj instanceof Database) {
			Database db = new Database();// ((Database)obj);

			if (serverIpText.getText().trim().equals("")) {
				javax.swing.JOptionPane.showMessageDialog(null, "请输入数据库地址！");
				return null;
			}
			if (dataBaseNameText.getText().trim().equals("")) {
				javax.swing.JOptionPane.showMessageDialog(null, "请输入数据库名称！");
				return null;
			}
			if (dataBasePortText.getText().trim().equals("")) {
				javax.swing.JOptionPane.showMessageDialog(null, "请输入数据库端口！");
				return null;
			}
			if (userNameText.getText().trim().equals("")) {
				javax.swing.JOptionPane.showMessageDialog(null, "请输入用户名！");
				return null;
			}
			// ((Database)obj)

			db.setHost(serverIpText.getText().trim()); // 服务器IP
			db.setDatabaseName(dataBaseNameText.getText().trim());
			db.setPort(Integer.valueOf(dataBasePortText.getText()));
			db.setUsername(userNameText.getText().trim());
			db.setPassword(String.valueOf(passwordText.getPassword()));

			Database dbComboBoxItem = (Database) obj;
			db.setUrl(dbComboBoxItem.getUrl());
			db.setDialect(dbComboBoxItem.getDialect());
			db.setDriverClassName(dbComboBoxItem.getDriverClassName());
			db.setType(dbComboBoxItem.getType());
			db.setSubject(dbComboBoxItem.getSubject());
			db.setName(dbComboBoxItem.getName());
			db.setDataMap(dbComboBoxItem.getDataMap());

			if (db != null) {
				DatabaseConfig dbConfig = new DatabaseConfig();
				try {
					dbConfig.check(AppConfig.appPath, db);
				} catch (RuntimeException ex) {
					ex.printStackTrace();
					javax.swing.JOptionPane.showMessageDialog(null, "数据库连接失败，"
							+ ex.getMessage());
					return null;
				} catch (Exception ex) {
					ex.printStackTrace();
					javax.swing.JOptionPane.showMessageDialog(null, "数据库连接失败，"
							+ ex.getMessage());
					return null;
				}

			}

			return db;
		} else {
			javax.swing.JOptionPane.showMessageDialog(null, "请先选择数据库类型！");
		}
		return null;
	}

	/**
	 * 复制单个文件
	 */
	public boolean copyFile(String oldPath, String newPath) {
		FileInputStream fin = null;
		FileOutputStream fout = null;
		try {
			int byteread = 0;
			fin = new FileInputStream(oldPath); // 读入原文件
			fout = new FileOutputStream(newPath);
			byte[] buffer = new byte[256];
			while ((byteread = fin.read(buffer)) != -1) {
				fout.write(buffer, 0, byteread);
			}
		} catch (IOException ex) {
			throw new RuntimeException("文件复制失败！", ex);
		} finally {
			try {
				if (fin != null) {
					fin.close();
					fin = null;
				}

			} catch (IOException e) {
			}
			try {
				if (fout != null) {
					fout.close();
					fout = null;
				}
			} catch (IOException e) {
			}
		}
		return true;
	}

}