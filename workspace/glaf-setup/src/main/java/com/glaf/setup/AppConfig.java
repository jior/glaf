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
 * Ӧ����Ϣ���ã�Ŀǰֻ������Ӧ��·����
 * 
 * 
 */
public class AppConfig extends JDialog {
	private static final long serialVersionUID = 1L;
	public static String appPath = "";
	private JLabel serverPath; // ������·��
	private JTextField serverPathText;
	private JButton browseFileBtn; // ·�����

	private JButton saveBtn; // ��������
	private JButton cancelBtn; // ���

	public AppConfig() {
		initskin();

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());

		int y = 0;
		mainPanel.add(new JPanel(), new GBC(0, y, 1, 1).setInsets(2, 2, 2, 2)
				.setFill(GBC.BOTH).setWeight(30, 0));// �����߿հ�λ��
		mainPanel.add(new JPanel(), new GBC(1, y, 1, 1).setInsets(2, 2, 2, 2)
				.setFill(GBC.BOTH).setWeight(40, 10));
		mainPanel.add(new JPanel(), new GBC(2, y, 1, 1).setInsets(2, 2, 2, 2)
				.setFill(GBC.BOTH).setWeight(30, 0));// ����ұ߿հ�λ��

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
		this.setTitle("Ӧ������");
		// this.setVisible(true);
	}

	// ��վ����������ʼ��
	private JPanel initServerPanel() {
		serverPath = new JLabel("Ӧ��·��"); // ������·��
		serverPathText = new JTextField(appPath);

		browseFileBtn = new JButton("���");

		// ѡ��������ļ�·��
		browseFileBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setApproveButtonText("ѡ��");
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
				.createTitledBorder("Ӧ�÷���������"));

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

	// ��ʼ��ť��� ���������¼�����, ���������¼�����
	private JPanel initButton() {
		saveBtn = new JButton(" ȷ  �� "); // ��������
		cancelBtn = new JButton(" ȡ �� "); // ��������

		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appPath = serverPathText.getText().trim();
				if (appPath.length() > 0) {
					boolean ok = checkAppPath(appPath);
					if (ok) {
						AppConfig.this.dispose();
					} else {
						javax.swing.JOptionPane.showMessageDialog(null,
								"Ӧ��·������ȷ��������ѡ��");
						lockEditable(false);
					}
				} else {
					javax.swing.JOptionPane
							.showMessageDialog(null, "����ѡ��Ӧ��·����");
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

		// ��������������·��
		List<Database> dbList = dbConfig.getDatabases(path);
		if (dbList.size() > 0) {
			lockEditable(true);
			return true;
		} else {
			javax.swing.JOptionPane.showMessageDialog(null, "Ӧ��·������ȷ��������ѡ��");
			lockEditable(false);
			return false;
		}
	}

	// ��ʼ��Ƥ��
	public void initskin() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

		} catch (Exception e) {
			System.out.println("װ��Ƥ������" + e);
		}
	}

	private void lockEditable(boolean b) {
		saveBtn.setEnabled(b); // ��������
	}

}