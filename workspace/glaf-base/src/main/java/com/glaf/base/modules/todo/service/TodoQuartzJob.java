package com.glaf.base.modules.todo.service;

import java.io.*;
import java.net.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.modules.BaseDataManager;

public class TodoQuartzJob {

	private final static Log logger = LogFactory.getLog(TodoQuartzJob.class);

	private String sendMessageServiceUrl;

	public String getSendMessageServiceUrl() {
		return sendMessageServiceUrl;
	}

	public void setSendMessageServiceUrl(String sendMessageServiceUrl) {
		this.sendMessageServiceUrl = sendMessageServiceUrl;
	}

	public void createTasksFromSQL() {
		try {
			TodoJobBean bean = (TodoJobBean) BaseDataManager.getInstance()
					.getBean("todoJobBean");
			if (bean != null) {
				bean.createTasksFromSQL();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
	}

	public void sendMessageToAllUsers() {
		try {
			TodoJobBean bean = (TodoJobBean) BaseDataManager.getInstance()
					.getBean("todoJobBean");
			if (bean != null) {
				bean.sendMessageToAllUsers();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
	}

	public void sendMessageToAllUsersViaJSP() {
		try {
			if (sendMessageServiceUrl != null) {
				URL url = new URL(sendMessageServiceUrl);
				URLConnection con = url.openConnection();
				con.setDoOutput(true);
				InputStream in = con.getInputStream();
				in = new BufferedInputStream(in);
				Reader r = new InputStreamReader(in);
				int c;
				System.out.println("==============Beging====================");
				System.out.println();
				while ((c = r.read()) != -1) {
					System.out.print((char) c);
				}
				in.close();
				System.out.println();
				System.out.println("===============End======================");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
	}

}
