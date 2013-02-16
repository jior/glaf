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


package org.jpage.context;

import java.io.IOException;
import java.security.Provider;
import java.security.cert.CertificateFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpage.util.FileTools;

public class ContextServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final static Log logger = LogFactory.getLog(ContextServlet.class);

	static {
		try {
			String provider = "org.bouncycastle.jce.provider.BouncyCastleProvider";
			java.security.Security.insertProviderAt((Provider) Class.forName(
					provider).newInstance(), 0);
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			System.out.println(cf.getType());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void init() {
		String appPath = this.getServletContext().getRealPath("");
		ApplicationContext.setAppPath(appPath);
		System.out.println("application root path:" + appPath);
		try {
			FileTools.deleteFileTree(appPath + "/WEB-INF/temp");
			FileTools.deleteFileTree(appPath + "/public/temp");
			FileTools.deleteFileTree(appPath + "/temp");
			FileTools.deleteFileTree(appPath + "/eXtree/xtree");
		} catch (IOException ex) {
			logger.error("delete temp file fail:" + ex.getMessage());
		}

		try {
			FileTools.mkdir(appPath + "/WEB-INF/temp");
			FileTools.mkdir(appPath + "/eXtree/xtree");
			FileTools.mkdir(appPath + "/public/temp");
			FileTools.mkdir(appPath + "/temp");
		} catch (IOException ex) {
			logger.error("create folder fail:" + ex.getMessage());
		}

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doProcess(request, response);
	}

	public void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		if ("reload".equals(request.getParameter("actionType"))) {
			logger.info("系统信息正在装载,请等待......");
			//SetupManager.getSetupManager().setup();
			logger.info("系统信息装载完成.");
		}
	}

}