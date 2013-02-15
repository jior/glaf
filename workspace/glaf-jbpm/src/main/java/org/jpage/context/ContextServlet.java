/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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