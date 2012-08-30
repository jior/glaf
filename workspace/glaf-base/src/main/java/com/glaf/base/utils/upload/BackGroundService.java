package com.glaf.base.utils.upload;

/**
 * <p>Title: ��̨����</p>
 *
 * <p>Description: Ϊ�ͻ����ṩ�ϴ����ļ�����״̬��ѯ����</p>
 *
 */

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.glaf.base.modules.Constants;
import com.glaf.base.utils.StringUtil;

public class BackGroundService extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 6162190133320085279L;

	public static final String UPLOAD_DIR = Constants.ROOT_PATH
			+ Constants.UPLOAD_DIR;

	public static final String DEFAULT_UPLOAD_FAILURE_URL = "/inc/upload/result.jsp";

	public static final long UPLOAD_FILE_SIZE_MAX = Constants.UPLOAD_FILE_SIZE_MAX;

	public static final long UPLOAD_SIZE_MAX = Constants.UPLOAD_MAX_SIZE;

	public BackGroundService() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * ���ļ�·����ȡ���ļ���
	 */
	private String takeOutFileName(String filePath) {
		int pos = filePath.lastIndexOf(File.separator);
		if (pos > 0) {
			return filePath.substring(pos + 1);
		} else {
			return filePath;
		}
	}

	/**
	 * ��request��ȡ��FileUploadStatus Bean
	 */
	public static FileUploadStatus getStatusBean(HttpServletRequest request) {
		BeanControler beanCtrl = BeanControler.getInstance();
		return beanCtrl.getUploadStatus(request.getRemoteAddr());
	}

	/**
	 * ��FileUploadStatus Bean���浽�������BeanControler
	 */
	public static void saveStatusBean(HttpServletRequest request,
			FileUploadStatus statusBean) {
		statusBean.setUploadAddr(request.getRemoteAddr());
		BeanControler beanCtrl = BeanControler.getInstance();
		beanCtrl.setUploadStatus(statusBean);
	}

	/**
	 * ɾ���Ѿ��ϴ����ļ�
	 */
	private void deleteUploadedFile(HttpServletRequest request) {
		FileUploadStatus satusBean = getStatusBean(request);
		for (int i = 0; i < satusBean.getUploadFileUrlList().size(); i++) {
			File uploadedFile = new File(UPLOAD_DIR + File.separator
					+ satusBean.getUploadFileUrlList().get(i));
			uploadedFile.delete();
		}
		satusBean.getUploadFileUrlList().clear();
		satusBean.setStatus("ɾ�����ϴ����ļ�");
		saveStatusBean(request, satusBean);
	}

	/**
	 * �ϴ������г�����
	 */
	private void uploadExceptionHandle(HttpServletRequest request, String errMsg)
			throws ServletException, IOException {
		// ����ɾ���Ѿ��ϴ����ļ�
		deleteUploadedFile(request);
		FileUploadStatus satusBean = getStatusBean(request);
		satusBean.setStatus(errMsg);
		saveStatusBean(request, satusBean);
	}

	/**
	 * ��ʼ���ļ��ϴ�״̬Bean
	 */
	private FileUploadStatus initStatusBean(HttpServletRequest request) {
		FileUploadStatus satusBean = new FileUploadStatus();
		satusBean.setStatus("����׼������");
		satusBean.setUploadTotalSize(request.getContentLength());
		satusBean.setProcessStartTime(System.currentTimeMillis());
		satusBean.setBaseDir(Constants.UPLOAD_DIR);
		return satusBean;
	}

	/**
	 * �����ļ��ϴ�
	 */
	private void processFileUpload(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// �����ڴ滺������������д����ʱ�ļ�
		factory.setSizeThreshold(204800);
		File tempDir = new File(UPLOAD_DIR + "/temp");
		if (!tempDir.exists()) {
			tempDir.mkdir();
		}
		// ������ʱ�ļ��洢λ��
		factory.setRepository(tempDir);
		ServletFileUpload upload = new ServletFileUpload(factory);
		// ���õ����ļ�������ϴ�ֵ
		upload.setFileSizeMax(UPLOAD_FILE_SIZE_MAX);
		// ��������request�����ֵ
		upload.setSizeMax(UPLOAD_SIZE_MAX);
		upload.setProgressListener(new FileUploadListener(request));
		// �����ʼ�����FileUploadStatus Bean
		saveStatusBean(request, initStatusBean(request));

		String forwardURL = "";
		try {
			List items = upload.parseRequest(request);
			// ��÷���url
			for (int i = 0; i < items.size(); i++) {
				FileItem item = (FileItem) items.get(i);
				if (item.isFormField()) {
					forwardURL = item.getString();
					break;
				}
			}
			// �ļ�����Ŀ¼
			String uploadDir = "";
			// �����ļ��ϴ�
			for (int i = 0; i < items.size(); i++) {
				if (i == 0) {
					// �����������Ŀ¼
					uploadDir = StringUtil.createDir(UPLOAD_DIR);
				}
				FileItem item = (FileItem) items.get(i);

				// ȡ���ϴ�
				if (getStatusBean(request).getCancel()) {
					deleteUploadedFile(request);
					break;
				}

				// �����ļ�
				else if (!item.isFormField() && item.getName().length() > 0) {
					String fileName = getFileName(takeOutFileName(item
							.getName()));
					File uploadedFile = new File(
							new File(UPLOAD_DIR, uploadDir), fileName);
					item.write(uploadedFile);
					// �����ϴ��ļ��б�
					FileUploadStatus satusBean = getStatusBean(request);
					satusBean.getUploadFileUrlList().add(
							new File(uploadDir, fileName).toString());
					saveStatusBean(request, satusBean);
					Thread.sleep(500);
				}
			}

		} catch (FileUploadException e) {
			uploadExceptionHandle(request, "�ϴ��ļ�ʱ��������:" + e.getMessage());
		} catch (Exception e) {
			uploadExceptionHandle(request, "�����ϴ��ļ�ʱ��������:" + e.getMessage());
		}
		if (forwardURL.length() == 0) {
			forwardURL = DEFAULT_UPLOAD_FAILURE_URL;
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
		FileUploadStatus satusBean = getStatusBean(request);
		response.getWriter().write(satusBean.toJSon());
	}

	/**
	 * ����ȡ���ļ��ϴ�
	 */
	private void processCancelFileUpload(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		FileUploadStatus satusBean = getStatusBean(request);
		satusBean.setCancel(true);
		saveStatusBean(request, satusBean);
		responseStatusQuery(request, response);
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

		}
	}

	// ȡ���ļ���
	private String getFileName(String fileName) {
		// SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		int random = (int) (Math.random() * 1000);
		String newFileName = getPrefixName(fileName) + "_" + random
				+ getFileExtension(fileName);
		return newFileName;
	}

	// ȡ�ļ���(������׺��)
	private String getPrefixName(String fileName) {
		if (fileName == null) {
			return null;
		}
		String s = fileName;
		int pos = fileName.lastIndexOf("/");
		if (pos != -1) {
			s = fileName.substring(pos + 1);
		} else {
			pos = fileName.lastIndexOf("\\");
			s = fileName.substring(pos + 1);
		}
		pos = s.lastIndexOf(".");
		if (pos != -1) {
			s = s.substring(0, pos);
		}
		return s;
	}

	// ȡ�ļ���׺��
	private String getFileExtension(String fileName) {
		if (fileName == null) {
			return null;
		}
		String ext = "";
		int pos = fileName.lastIndexOf(".");
		if (pos != -1) {
			ext = fileName.substring(pos);
		}
		return ext;
	}

	public static void main(String[] args) {
		System.out
				.println(new BackGroundService().getFileName("/test/PMS.zip"));
		System.out.println(new BackGroundService()
				.getPrefixName("/test/t/1.z.ip"));
		System.out.println(new BackGroundService().getPrefixName("E:\\1.zip"));
		long size = 2 * 1024 * 1124;
		double mSize = (int) (((double) (size / 1024 / 1024.0)) * 100) / 100.0;
		System.out.println(mSize);
	}
}
