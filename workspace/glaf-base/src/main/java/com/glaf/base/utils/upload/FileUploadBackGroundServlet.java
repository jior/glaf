/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.glaf.base.utils.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.modules.Constants;
import com.glaf.base.modules.others.service.AttachmentService;
import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.UUID32;

/**
 * Title: ��̨����<br/>
 * Description: Ϊ�ͻ����ṩ�ϴ����ļ�����״̬��ѯ����<br/>
 */

public class FileUploadBackGroundServlet extends HttpServlet {
	private static Configuration conf = BaseConfiguration.create();

	private static final Log logger = LogFactory
			.getLog(FileUploadBackGroundServlet.class);

	private static final long serialVersionUID = 6162190133320085279L;

	protected AttachmentService attachmentService;

	public FileUploadBackGroundServlet() {
		super();
	}

	/**
	 * ɾ���Ѿ��ϴ����ļ�
	 */
	private void deleteUploadedFile(HttpServletRequest request) {
		FileUploadStatus satusBean = FileMgmtFactory.getStatusBean(request);
		for (int i = 0; i < satusBean.getUploadFileUrlList().size(); i++) {
			FileInfo fileInfo = satusBean.getUploadFileUrlList().get(i);
			fileInfo.getFile().delete();
		}
		satusBean.getUploadFileUrlList().clear();
		satusBean.setStatus("ɾ�����ϴ����ļ�");
		FileMgmtFactory.saveStatusBean(request, satusBean);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			processFileUpload(request, response);
		} else {
			request.setCharacterEncoding("UTF-8");

			if (request.getParameter("uploadStatus") != null) {
				responseStatusQuery(request, response);
			}
			if (request.getParameter("cancelUpload") != null) {
				processCancelFileUpload(request, response);
			}
			if (request.getParameter("download") != null) {
				processDownload(request, response);
			}

		}
	}

	public AttachmentService getAttachmentService() {
		if (attachmentService == null) {
			attachmentService = ContextFactory.getBean("attachmentService");
		}
		return attachmentService;
	}

	private String getUploadDir() {
		return SystemProperties.getAppPath() + Constants.UPLOAD_DIR;
	}

	/**
	 * ��ʼ���ļ��ϴ�״̬Bean
	 */
	private FileUploadStatus initStatusBean(HttpServletRequest request) {
		FileUploadStatus satusBean = new FileUploadStatus();
		satusBean.setStatus("����׼������");
		satusBean.setUploadTotalSize(request.getContentLength());
		satusBean.setProcessStartTime(System.currentTimeMillis());
		satusBean.setBaseDir(getUploadDir());
		return satusBean;
	}

	/**
	 * ����ȡ���ļ��ϴ�
	 */
	private void processCancelFileUpload(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		FileUploadStatus satusBean = FileMgmtFactory.getStatusBean(request);
		satusBean.setCancel(true);
		FileMgmtFactory.saveStatusBean(request, satusBean);
		responseStatusQuery(request, response);
	}

	/**
	 * ��������
	 */
	private void processDownload(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		FileUploadStatus uploadStatus = FileMgmtFactory.getStatusBean(request);
		int num = uploadStatus.getUploadFileUrlList().size();
		String fileId = request.getParameter("fileId");
		for (int i = 0; i < num; i++) {
			FileInfo fileInfo = (FileInfo) uploadStatus.getUploadFileUrlList()
					.get(i);
			String filename = fileInfo.getFilename();
			if (StringUtils.equals(fileId, fileInfo.getFileId())) {
				FileInputStream fin = new FileInputStream(fileInfo.getFile());
				ResponseUtils.download(request, response, fin, filename);
				return;
			}
		}
	}

	/**
	 * �����ļ��ϴ�
	 */
	private void processFileUpload(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String serviceKey = request.getParameter("serviceKey");
		if (serviceKey == null) {
			serviceKey = "global";
		}

		int maxUploadSize = conf.getInt(serviceKey + ".maxUploadSize", 0);
		if (maxUploadSize == 0) {
			maxUploadSize = conf.getInt("upload.maxUploadSize", 10);// 10MB
		}
		maxUploadSize = maxUploadSize * FileUtils.MB_SIZE;
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// �����ڴ滺������������д����ʱ�ļ�
		factory.setSizeThreshold(204800);
		File tempDir = new File(getUploadDir() + "/temp");
		if (!tempDir.exists()) {
			tempDir.mkdir();
		}

		// ������ʱ�ļ��洢λ��
		factory.setRepository(tempDir);
		ServletFileUpload upload = new ServletFileUpload(factory);
		// ���õ����ļ�������ϴ�ֵ
		upload.setFileSizeMax(maxUploadSize);
		// ��������request�����ֵ
		upload.setSizeMax(maxUploadSize);
		upload.setProgressListener(new FileUploadListener(request));
		upload.setHeaderEncoding("UTF-8");
		// �����ʼ�����FileUploadStatus Bean
		FileMgmtFactory.saveStatusBean(request, initStatusBean(request));

		try {
			List<?> items = upload.parseRequest(request);
			// ��÷���url
			for (int i = 0; i < items.size(); i++) {
				FileItem item = (FileItem) items.get(i);
				if (item.isFormField()) {

					break;
				}
			}
			// �ļ�����Ŀ¼
			String uploadDir = com.glaf.base.utils.StringUtil
					.createDir(getUploadDir());
			// �����ļ��ϴ�
			for (int i = 0; i < items.size(); i++) {
				FileItem item = (FileItem) items.get(i);
				// ȡ���ϴ�
				if (FileMgmtFactory.getStatusBean(request).getCancel()) {
					deleteUploadedFile(request);
					break;
				} else if (!item.isFormField() && item.getName().length() > 0) {
					logger.debug("ԭʼ�ļ���" + item.getName());
					// �����ļ�
					String fileId = UUID32.getUUID();
					String path = uploadDir + FileUtils.sp + fileId;
					File uploadedFile = new File(new File(getUploadDir(),
							uploadDir), fileId);
					item.write(uploadedFile);
					// �����ϴ��ļ��б�
					FileUploadStatus satusBean = FileMgmtFactory
							.getStatusBean(request);
					FileInfo fileInfo = new FileInfo();
					fileInfo.setCreateDate(new Date());
					fileInfo.setFileId(fileId);
					fileInfo.setFile(uploadedFile);
					fileInfo.setFilename(FileUtils.getFilename(item.getName()));
					fileInfo.setSize(item.getSize());
					fileInfo.setPath(path);
					satusBean.getUploadFileUrlList().add(fileInfo);
					FileMgmtFactory.saveStatusBean(request, satusBean);
					Thread.sleep(500);
				}
			}

		} catch (FileUploadException ex) {
			uploadExceptionHandle(request, "�ϴ��ļ�ʱ��������:" + ex.getMessage());
		} catch (Exception ex) {
			uploadExceptionHandle(request, "�����ϴ��ļ�ʱ��������:" + ex.getMessage());
		}
		String forwardURL = request.getParameter("forwardURL");
		if (StringUtils.isEmpty(forwardURL)) {
			forwardURL = "/others/attachment.do?method=showResult";
		}
		request.getRequestDispatcher(forwardURL).forward(request, response);
	}

	/**
	 * ��Ӧ�ϴ�״̬��ѯ
	 */
	private void responseStatusQuery(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		FileUploadStatus satusBean = FileMgmtFactory.getStatusBean(request);
		response.getWriter().write(satusBean.toJson());
	}

	/**
	 * �ϴ������г�����
	 */
	private void uploadExceptionHandle(HttpServletRequest request, String errMsg)
			throws ServletException, IOException {
		// ����ɾ���Ѿ��ϴ����ļ�
		deleteUploadedFile(request);
		FileUploadStatus satusBean = FileMgmtFactory.getStatusBean(request);
		satusBean.setStatus(errMsg);
		FileMgmtFactory.saveStatusBean(request, satusBean);
	}

}
