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

import javax.swing.JButton;
import javax.swing.JComboBox;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.glaf.setup.AppConfig;
import com.glaf.setup.GBC;

import com.glaf.setup.conf.MailConfig;

/**
 * �ʼ����ñ�ǩ���
 * 
 */
public class MailServerConfigPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static String sp = System.getProperty("file.separator");

	private JLabel serverAddress; // �ʼ���������ַ ����
	private JTextField serverAddressText;

	private JLabel serverPort; // ���Ͷ˿�
	private JTextField serverPortText;

	private JLabel sendUser; // ����������
	private JTextField sendUserText;

	private JLabel verify; // �Ƿ���Ҫ��֤
	private JComboBox verifyBox;

	private JLabel userName; // �û���
	private JTextField userNameText;

	private JLabel password; // ����
	private JPasswordField passwordText;

	private JPanel userPanel;

	private JLabel mailCode; // �ʼ�����
	// private JTextField mailCodeText ;
	private JComboBox mailCodeBox;

	private JLabel mailContent; // //����д�����ı�
	private JTextArea mailContentText;
	// ����д�����ı�

	private JButton saveBtn; // ��������
	private JButton checkConnect; // ��������

	public MailServerConfigPanel() {
		this.setLayout(new GridBagLayout());

		int y = 0;
		// ��߿հ����
		this.add(new JPanel(), new GBC(0, y, 1, 1).setInsets(0, 2, 0, 2)
				.setFill(GBC.BOTH).setWeight(30, 0));
		// �����հ�
		this.add(new JPanel(), new GBC(1, y, 1, 1).setInsets(0, 2, 0, 2)
				.setFill(GBC.BOTH).setWeight(40, 0));
		// �ұ߿հ����
		this.add(new JPanel(), new GBC(2, y, 1, 1).setInsets(0, 2, 0, 2)
				.setFill(GBC.BOTH).setWeight(30, 0));

		y++;
		this.add(initPanel(), new GBC(1, y, 1, 1).setInsets(2, 2, 2, 2)
				.setFill(GBC.BOTH).setWeight(40, 80));

		y++;
		this.add(new JPanel(), new GBC(1, y, 1, 1).setInsets(2, 2, 2, 2)
				.setFill(GBC.BOTH).setWeight(40, 20));

		y++;
		this.add(initBtn(),
				new GBC(1, y, 1, 1).setInsets(2, 2, 2, 2).setFill(GBC.BOTH)
						.setWeight(40, 0));

		// this.lockEditable(false);
	}

	public JPanel initPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBorder(javax.swing.BorderFactory.createTitledBorder("�ʼ���������"));

		serverAddress = new JLabel("�ʼ���������ַ*"); // �ʼ���������ַ ����
		serverAddressText = new JTextField("");

		serverPort = new JLabel("���Ͷ˿�*  		           "); // ���Ͷ˿�
		serverPortText = new JTextField("25");

		sendUser = new JLabel("����������*         "); // ����������
		sendUserText = new JTextField();

		userName = new JLabel("�û���*               "); // �û���
		userNameText = new JTextField();

		password = new JLabel("����                      "); // ����
		passwordText = new JPasswordField();

		verify = new JLabel("�Ƿ���Ҫ��֤       "); // �Ƿ���Ҫ��֤
		verifyBox = new JComboBox(new Object[] { "��Ҫ", "����Ҫ" });
		verifyBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object item = verifyBox.getSelectedItem();
				if (item != null) {
					if (item.toString().equals("��Ҫ")) {
						userPanel.setVisible(true);
					} else {
						userPanel.setVisible(false);
					}
				}
			}
		});

		mailCode = new JLabel("�ʼ�����               "); // �ʼ�����
		// mailCodeText = new JTextField();
		mailCodeBox = new JComboBox(new Object[] { "GBK", "GB2312", "GB18030",
				"BIG5", "UTF-8", "UTF-16", "ISO-8859-1" });

		mailContent = new JLabel("����д�����ı�    "); // //����д�����ı�
		mailContentText = new JTextArea("�����ʼ���");
		mailContentText.setRows(3);

		int y = 0;
		panel.add(serverAddress, new GBC(0, y, 1, 1).setInsets(0, 2, 0, 2));
		panel.add(serverAddressText, new GBC(1, y, 1, 1).setInsets(0, 2, 0, 2)
				.setFill(GBC.BOTH).setWeight(100, 0));

		y++;
		panel.add(serverPort, new GBC(0, y, 1, 1).setInsets(0, 2, 0, 2));
		panel.add(serverPortText, new GBC(1, y, 1, 1).setInsets(0, 2, 0, 2)
				.setFill(GBC.BOTH).setWeight(100, 0));

		y++;
		panel.add(sendUser, new GBC(0, y, 1, 1).setInsets(0, 2, 0, 2));
		panel.add(sendUserText, new GBC(1, y, 1, 1).setInsets(0, 2, 0, 2)
				.setFill(GBC.BOTH).setWeight(100, 0));

		y++;
		panel.add(verify, new GBC(0, y, 1, 1).setInsets(0, 2, 0, 2));
		JPanel boxPanel = new JPanel();
		boxPanel.setLayout(new GridBagLayout());
		boxPanel.add(verifyBox, new GBC(0, 0, 1, 1).setInsets(0, 2, 0, 2)
				.setAnchor(GBC.WEST).setFill(GBC.BOTH).setWeight(0, 0));
		boxPanel.add(new JPanel(), new GBC(1, 0, 1, 1).setInsets(0, 2, 0, 2)
				.setAnchor(GBC.WEST).setFill(GBC.BOTH).setWeight(100, 0));
		panel.add(boxPanel, new GBC(1, y, 1, 1).setInsets(0, 2, 0, 2)
				.setAnchor(GBC.WEST).setFill(GBC.BOTH).setWeight(100, 0));

		y++;
		userPanel = new JPanel();
		userPanel.setLayout(new GridBagLayout());
		userPanel.add(userName, new GBC(0, 0, 1, 1).setInsets(0, 2, 0, 2));
		userPanel.add(userNameText, new GBC(1, 0, 1, 1).setInsets(0, 8, 0, 2)
				.setFill(GBC.BOTH).setWeight(100, 0));
		userPanel.add(password, new GBC(0, 1, 1, 1).setInsets(0, 2, 0, 2));
		userPanel.add(passwordText, new GBC(1, 1, 1, 1).setInsets(0, 8, 0, 2)
				.setFill(GBC.BOTH).setWeight(100, 0));

		panel.add(userPanel,
				new GBC(0, y, 2, 1).setFill(GBC.BOTH).setWeight(100, 0));

		y++;
		panel.add(mailCode, new GBC(0, y, 1, 1).setInsets(0, 2, 0, 2));
		JPanel mailPanel = new JPanel();
		mailPanel.setLayout(new GridBagLayout());
		mailPanel.add(mailCodeBox, new GBC(0, 0, 1, 1).setInsets(0, 2, 0, 2));
		mailPanel.add(new JPanel(), new GBC(1, 0, 1, 1).setInsets(0, 8, 0, 2)
				.setFill(GBC.BOTH).setWeight(100, 0));
		panel.add(mailPanel,
				new GBC(1, y, 1, 1).setInsets(0, 2, 0, 2).setFill(GBC.BOTH)
						.setWeight(100, 0));

		y++;
		panel.add(mailContent, new GBC(0, y, 1, 1).setInsets(0, 2, 0, 2));
		panel.add(new JScrollPane(mailContentText), new GBC(1, y, 1, 1)
				.setInsets(0, 2, 0, 2).setFill(GBC.BOTH).setWeight(100, 100));

		return panel;
	}

	private JPanel initBtn() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		saveBtn = new JButton("��������"); // ��������
		checkConnect = new JButton(" ��  ��  "); // ����

		// �����ʼ�����
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MailConfig mainConfig = checkValue();
				try {
					mainConfig.config(AppConfig.appPath);
				} catch (RuntimeException re) {
					javax.swing.JOptionPane.showMessageDialog(null,
							re.getMessage());
				} catch (Exception ex) {
					javax.swing.JOptionPane.showMessageDialog(null,
							"����ʧ��" + ex.getMessage());
				}
			}
		});

		// �����ʼ�����
		checkConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MailConfig mainConfig = checkValue();
				if (mainConfig != null) {
					try {
						mainConfig.check();
						javax.swing.JOptionPane.showMessageDialog(null, "���ͳɹ�");
					} catch (RuntimeException re) {
						javax.swing.JOptionPane.showMessageDialog(null,
								re.getMessage());
					} catch (Exception ex) {
						javax.swing.JOptionPane.showMessageDialog(null, "����ʧ��"
								+ ex.getMessage());
					}
				}
			}
		});

		panel.add(saveBtn, new GBC(0, 0, 1, 1).setInsets(2, 2, 2, 20));
		panel.add(checkConnect, new GBC(1, 0, 1, 1).setInsets(2, 20, 2, 2));

		return panel;

	}

	public MailConfig checkValue() {
		MailConfig mailConfig = new MailConfig();

		if (serverAddressText.getText().trim().equals("")) { // �ʼ���������ַ ����
			javax.swing.JOptionPane.showMessageDialog(null, "������д�ʼ���������ַ��");
			return null;
		}
		mailConfig.setHost(serverAddressText.getText().trim());

		if (serverPortText.getText().trim().equals("")) { // ���Ͷ˿�
			javax.swing.JOptionPane.showMessageDialog(null, "������д�ʼ����������Ͷ˿ڣ�");
			return null;
		}
		mailConfig.setPort(Integer.valueOf(serverPortText.getText().trim()));

		if (sendUserText.getText().trim().equals("")) { // ����������
			javax.swing.JOptionPane.showMessageDialog(null, "������д�ʼ����������ƣ�");
			return null;
		}
		mailConfig.setMailFrom(sendUserText.getText().trim());

		if (verifyBox.getSelectedItem().toString().equals("��Ҫ")) { // �Ƿ���Ҫ��֤
			if (userNameText.getText().trim().equals("")) { // �û���
				javax.swing.JOptionPane.showMessageDialog(null, "������д�û�����");
				return null;
			}
			mailConfig.setAuth(true);

			mailConfig.setUsername(userNameText.getText().trim());

			mailConfig.setPassword(String.valueOf(passwordText.getPassword())); // ����

		} else {
			mailConfig.setAuth(false);
		}

		mailConfig.setEncoding(mailCodeBox.getSelectedItem().toString()); // �ʼ�����

		mailConfig.setText(mailContentText.getText()); // ����д�����ı�

		return mailConfig;
	}

	// private void lockEditable(boolean b){
	// serverAddressText.setEnabled(b);
	// serverPortText.setEnabled(b);
	// sendUserText.setEnabled(b);
	// verifyBox.setEnabled(b);
	// userNameText.setEnabled(b);
	// passwordText.setEnabled(b);
	// mailCodeBox.setEnabled(b);
	// mailContentText.setEnabled(b);
	// saveBtn.setEnabled(b);
	// checkConnect.setEnabled(b);
	// }
}