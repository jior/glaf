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
 * ���ݿ����ý���
 */
public class DataBaseConfigPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public final String sp = System.getProperty("file.separator");

	private JLabel jarPath; // Ӧ��·��
	private JTextField jarPathText;
	private JButton upLoadJarBtn; // ·�����
	private JButton browseJarBtn; // ·�����

	private JLabel dataDrive; // ���ݿ�����
	private JComboBox dataDriveBox;

	private JLabel serverIp; // ���ݿ��������ַ
	private JTextField serverIpText;

	private JLabel dataBaseName; // ���ݿ�����
	private JTextField dataBaseNameText;

	private JLabel dataBasePort; // ���ݿ�˿�
	private JTextField dataBasePortText;

	private JLabel userName; // �û���
	private JTextField userNameText;

	private JLabel password; // ����
	private JPasswordField passwordText;

	private JButton saveBtn; // ��������
	private JButton connectBtn; // ���

	public DataBaseConfigPanel() {
		this.setLayout(new GridBagLayout());

		int row = 0;
		this.add(new JPanel(), new GBC(0, row, 1, 1).setInsets(2, 2, 2, 2)
				.setFill(GBC.BOTH).setWeight(30, 0));// �����߿հ�λ��
		this.add(new JPanel(), new GBC(1, row, 1, 1).setInsets(2, 2, 2, 2)
				.setFill(GBC.BOTH).setWeight(15, 10));
		this.add(new JPanel(), new GBC(2, 0, 1, 1).setInsets(2, 2, 2, 2)
				.setFill(GBC.BOTH).setWeight(30, 0));// ����ұ߿հ�λ��

		row++;
		this.add(initDataBasePanel(),
				new GBC(1, row, 1, 1).setInsets(2, 2, 2, 2).setFill(GBC.BOTH)
						.setWeight(40, 0));

		row++;
		this.add(new JPanel(), new GBC(0, row, 3, 1).setInsets(2, 2, 2, 2)
				.setFill(GBC.BOTH).setWeight(100, 100));// ���ײ��հ�λ��

		row++;
		this.add(initButton(), new GBC(1, row, 1, 1).setInsets(2, 2, 2, 2)
				.setFill(GBC.BOTH).setWeight(100, 100));

	}

	// ��ʼ�����ݿ��������
	private JPanel initDataBasePanel() {
		jarPath = new JLabel("������·��"); // ������·��
		jarPathText = new JTextField();
		upLoadJarBtn = new JButton("�ϴ�");
		browseJarBtn = new JButton("���"); // �������

		// �ϴ�
		upLoadJarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filePaths = jarPathText.getText();
				String jarToolText = "";
				String[] JarFiles = filePaths.split(";");
				if (JarFiles.length > 0) {
					String appPath = AppConfig.appPath;
					String libPath = appPath + sp + "WEB-INF" + sp + "lib"; // ����·��
					for (int i = 0; i < JarFiles.length; i++) {
						File jarFile = new File(JarFiles[i]);

						// ���ж���ѡ�������Ƿ����
						if (jarFile.exists()) {
							// ���ж�Ӧ��·���Ƿ���ȷ serverPathText
							File appFile = new File(appPath);
							if (appFile.exists() == false) {
								javax.swing.JOptionPane.showConfirmDialog(null,
										"Ӧ��·������ȷ��������ѡ��!");
								return;
							}

							String fileName = jarFile.getName();
							File newFile = new File(libPath + sp + fileName);
							int choice = JOptionPane.YES_OPTION;
							if (newFile.exists()) {
								choice = JOptionPane.showConfirmDialog(null,
										"Ӧ��·��" + libPath + "�Ѿ�����" + fileName
												+ "������Ҫ�滻����", "��ʾ",
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
													+ "�������ϴ�ʧ�ܣ������ϴ�·���Ƿ���ȷ��");
									return;
								} else {
									jarToolText = jarToolText + JarFiles[i]
											+ ";\n";
								}
							}
						} else {
							JOptionPane.showMessageDialog(null, JarFiles[i]
									+ "�����������ڣ�������ѡ��");
							return;
						}
					}
					if (!jarToolText.trim().equals("")) {
						JOptionPane.showMessageDialog(null, "�ɹ��ϴ�\n"
								+ jarToolText + "��Ӧ��·��\n" + libPath, "�������ϴ��ɹ�",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});

		// ѡ������jar·��
		browseJarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setMultiSelectionEnabled(true);
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"jar ������", "jar");
				chooser.addChoosableFileFilter(filter);
				chooser.setApproveButtonText("ѡ��");
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

		dataDrive = new JLabel("���ݿ�����"); // ���ݿ�����
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
		// ��������������·��
		List<Database> dbList = dbConfig.getDatabases(AppConfig.appPath);
		if (dbList.size() > 0) {
			for (int i = 0; i < dbList.size(); i++) {
				Database db = (Database) dbList.get(i);
				dataDriveBox.addItem(db);
			}
		}

		serverIp = new JLabel("���ݿ��ַ"); // ���ݿ��������ַ
		serverIpText = new JTextField("127.0.0.1");

		dataBaseName = new JLabel("���ݿ�����"); // ���ݿ�����
		dataBaseNameText = new JTextField("pMagic");

		dataBasePort = new JLabel("���ݿ�˿�"); // �˿�
		dataBasePortText = new JTextField("");

		userName = new JLabel("��    ��   ��"); // �û���
		userNameText = new JTextField();

		password = new JLabel("��            ��"); // ����
		passwordText = new JPasswordField();

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("���ݿ�����"));

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
				.setWeight(100, 0));// ���հ�

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

	// ��ʼ��ť��� ���������¼�����, ���������¼�����
	private JPanel initButton() {
		saveBtn = new JButton("��������"); // ��������
		connectBtn = new JButton("��������"); // ��������

		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Database db = checkConnect();
				if (db != null) {
					DatabaseConfig dbConfig = new DatabaseConfig();
					try {
						dbConfig.reconfig(AppConfig.appPath, db);
						javax.swing.JOptionPane
								.showMessageDialog(null, "����ɹ���");
					} catch (RuntimeException ex) {
						ex.printStackTrace();
						javax.swing.JOptionPane.showMessageDialog(null, "����ʧ�ܣ�"
								+ ex.getMessage());
					} catch (Exception ex) {
						ex.printStackTrace();
						javax.swing.JOptionPane.showMessageDialog(null, "����ʧ��,"
								+ ex.getMessage());
					}
				}
			}
		});

		connectBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (checkConnect() != null) {
					javax.swing.JOptionPane.showMessageDialog(null, "���ݿ����ӳɹ���");
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
				javax.swing.JOptionPane.showMessageDialog(null, "���������ݿ��ַ��");
				return null;
			}
			if (dataBaseNameText.getText().trim().equals("")) {
				javax.swing.JOptionPane.showMessageDialog(null, "���������ݿ����ƣ�");
				return null;
			}
			if (dataBasePortText.getText().trim().equals("")) {
				javax.swing.JOptionPane.showMessageDialog(null, "���������ݿ�˿ڣ�");
				return null;
			}
			if (userNameText.getText().trim().equals("")) {
				javax.swing.JOptionPane.showMessageDialog(null, "�������û�����");
				return null;
			}
			// ((Database)obj)

			db.setHost(serverIpText.getText().trim()); // ������IP
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
					javax.swing.JOptionPane.showMessageDialog(null, "���ݿ�����ʧ�ܣ�"
							+ ex.getMessage());
					return null;
				} catch (Exception ex) {
					ex.printStackTrace();
					javax.swing.JOptionPane.showMessageDialog(null, "���ݿ�����ʧ�ܣ�"
							+ ex.getMessage());
					return null;
				}

			}

			return db;
		} else {
			javax.swing.JOptionPane.showMessageDialog(null, "����ѡ�����ݿ����ͣ�");
		}
		return null;
	}

	/**
	 * ���Ƶ����ļ�
	 */
	public boolean copyFile(String oldPath, String newPath) {
		FileInputStream fin = null;
		FileOutputStream fout = null;
		try {
			int byteread = 0;
			fin = new FileInputStream(oldPath); // ����ԭ�ļ�
			fout = new FileOutputStream(newPath);
			byte[] buffer = new byte[256];
			while ((byteread = fin.read(buffer)) != -1) {
				fout.write(buffer, 0, byteread);
			}
		} catch (IOException ex) {
			throw new RuntimeException("�ļ�����ʧ�ܣ�", ex);
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