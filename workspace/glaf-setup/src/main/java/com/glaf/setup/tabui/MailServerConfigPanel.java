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
 * 邮件配置标签面板
 * 
 */
public class MailServerConfigPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static String sp = System.getProperty("file.separator");

	private JLabel serverAddress; // 邮件服务器地址 必填
	private JTextField serverAddressText;

	private JLabel serverPort; // 发送端口
	private JTextField serverPortText;

	private JLabel sendUser; // 发送者名称
	private JTextField sendUserText;

	private JLabel verify; // 是否需要认证
	private JComboBox verifyBox;

	private JLabel userName; // 用户名
	private JTextField userNameText;

	private JLabel password; // 密码
	private JPasswordField passwordText;

	private JPanel userPanel;

	private JLabel mailCode; // 邮件编码
	// private JTextField mailCodeText ;
	private JComboBox mailCodeBox;

	private JLabel mailContent; // //请填写测试文本
	private JTextArea mailContentText;
	// 请填写测试文本

	private JButton saveBtn; // 保存配置
	private JButton checkConnect; // 测试连接

	public MailServerConfigPanel() {
		this.setLayout(new GridBagLayout());

		int y = 0;
		// 左边空白填充
		this.add(new JPanel(), new GBC(0, y, 1, 1).setInsets(0, 2, 0, 2)
				.setFill(GBC.BOTH).setWeight(30, 0));
		// 顶部空白
		this.add(new JPanel(), new GBC(1, y, 1, 1).setInsets(0, 2, 0, 2)
				.setFill(GBC.BOTH).setWeight(40, 0));
		// 右边空白填充
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
		panel.setBorder(javax.swing.BorderFactory.createTitledBorder("邮件服务配置"));

		serverAddress = new JLabel("邮件服务器地址*"); // 邮件服务器地址 必填
		serverAddressText = new JTextField("");

		serverPort = new JLabel("发送端口*  		           "); // 发送端口
		serverPortText = new JTextField("25");

		sendUser = new JLabel("发送者名称*         "); // 发送者名称
		sendUserText = new JTextField();

		userName = new JLabel("用户名*               "); // 用户名
		userNameText = new JTextField();

		password = new JLabel("密码                      "); // 密码
		passwordText = new JPasswordField();

		verify = new JLabel("是否需要认证       "); // 是否需要认证
		verifyBox = new JComboBox(new Object[] { "需要", "不需要" });
		verifyBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object item = verifyBox.getSelectedItem();
				if (item != null) {
					if (item.toString().equals("需要")) {
						userPanel.setVisible(true);
					} else {
						userPanel.setVisible(false);
					}
				}
			}
		});

		mailCode = new JLabel("邮件编码               "); // 邮件编码
		// mailCodeText = new JTextField();
		mailCodeBox = new JComboBox(new Object[] { "GBK", "GB2312", "GB18030",
				"BIG5", "UTF-8", "UTF-16", "ISO-8859-1" });

		mailContent = new JLabel("请填写测试文本    "); // //请填写测试文本
		mailContentText = new JTextArea("测试邮件。");
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

		saveBtn = new JButton("保存配置"); // 保存配置
		checkConnect = new JButton(" 测  试  "); // 测试

		// 保存邮件配置
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
							"保存失败" + ex.getMessage());
				}
			}
		});

		// 测试邮件服务
		checkConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MailConfig mainConfig = checkValue();
				if (mainConfig != null) {
					try {
						mainConfig.check();
						javax.swing.JOptionPane.showMessageDialog(null, "发送成功");
					} catch (RuntimeException re) {
						javax.swing.JOptionPane.showMessageDialog(null,
								re.getMessage());
					} catch (Exception ex) {
						javax.swing.JOptionPane.showMessageDialog(null, "发送失败"
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

		if (serverAddressText.getText().trim().equals("")) { // 邮件服务器地址 必填
			javax.swing.JOptionPane.showMessageDialog(null, "请先填写邮件服务器地址！");
			return null;
		}
		mailConfig.setHost(serverAddressText.getText().trim());

		if (serverPortText.getText().trim().equals("")) { // 发送端口
			javax.swing.JOptionPane.showMessageDialog(null, "请先填写邮件服务器发送端口！");
			return null;
		}
		mailConfig.setPort(Integer.valueOf(serverPortText.getText().trim()));

		if (sendUserText.getText().trim().equals("")) { // 发送者名称
			javax.swing.JOptionPane.showMessageDialog(null, "请先填写邮件发送者名称！");
			return null;
		}
		mailConfig.setMailFrom(sendUserText.getText().trim());

		if (verifyBox.getSelectedItem().toString().equals("需要")) { // 是否需要认证
			if (userNameText.getText().trim().equals("")) { // 用户名
				javax.swing.JOptionPane.showMessageDialog(null, "请先填写用户名！");
				return null;
			}
			mailConfig.setAuth(true);

			mailConfig.setUsername(userNameText.getText().trim());

			mailConfig.setPassword(String.valueOf(passwordText.getPassword())); // 密码

		} else {
			mailConfig.setAuth(false);
		}

		mailConfig.setEncoding(mailCodeBox.getSelectedItem().toString()); // 邮件编码

		mailConfig.setText(mailContentText.getText()); // 请填写测试文本

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