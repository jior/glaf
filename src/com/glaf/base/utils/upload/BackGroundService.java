package com.glaf.base.utils.upload;

/**
 * <p>Title: 后台服务</p>
 *
 * <p>Description: 为客户端提供上传及文件传输状态查询服务</p>
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
	 * 从文件路径中取出文件名
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
	 * 从request中取出FileUploadStatus Bean
	 */
	public static FileUploadStatus getStatusBean(HttpServletRequest request) {
		BeanControler beanCtrl = BeanControler.getInstance();
		return beanCtrl.getUploadStatus(request.getRemoteAddr());
	}

	/**
	 * 把FileUploadStatus Bean保存到类控制器BeanControler
	 */
	public static void saveStatusBean(HttpServletRequest request,
			FileUploadStatus statusBean) {
		statusBean.setUploadAddr(request.getRemoteAddr());
		BeanControler beanCtrl = BeanControler.getInstance();
		beanCtrl.setUploadStatus(statusBean);
	}

	/**
	 * 删除已经上传的文件
	 */
	private void deleteUploadedFile(HttpServletRequest request) {
		FileUploadStatus satusBean = getStatusBean(request);
		for (int i = 0; i < satusBean.getUploadFileUrlList().size(); i++) {
			File uploadedFile = new File(UPLOAD_DIR + File.separator
					+ satusBean.getUploadFileUrlList().get(i));
			uploadedFile.delete();
		}
		satusBean.getUploadFileUrlList().clear();
		satusBean.setStatus("删除已上传的文件");
		saveStatusBean(request, satusBean);
	}

	/**
	 * 上传过程中出错处理
	 */
	private void uploadExceptionHandle(HttpServletRequest request, String errMsg)
			throws ServletException, IOException {
		// 首先删除已经上传的文件
		deleteUploadedFile(request);
		FileUploadStatus satusBean = getStatusBean(request);
		satusBean.setStatus(errMsg);
		saveStatusBean(request, satusBean);
	}

	/**
	 * 初始化文件上传状态Bean
	 */
	private FileUploadStatus initStatusBean(HttpServletRequest request) {
		FileUploadStatus satusBean = new FileUploadStatus();
		satusBean.setStatus("正在准备处理");
		satusBean.setUploadTotalSize(request.getContentLength());
		satusBean.setProcessStartTime(System.currentTimeMillis());
		satusBean.setBaseDir(Constants.UPLOAD_DIR);
		return satusBean;
	}

	/**
	 * 处理文件上传
	 */
	private void processFileUpload(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 设置内存缓冲区，超过后写入临时文件
		factory.setSizeThreshold(204800);
		File tempDir = new File(UPLOAD_DIR + "/temp");
		if (!tempDir.exists()) {
			tempDir.mkdir();
		}
		// 设置临时文件存储位置
		factory.setRepository(tempDir);
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 设置单个文件的最大上传值
		upload.setFileSizeMax(UPLOAD_FILE_SIZE_MAX);
		// 设置整个request的最大值
		upload.setSizeMax(UPLOAD_SIZE_MAX);
		upload.setProgressListener(new FileUploadListener(request));
		// 保存初始化后的FileUploadStatus Bean
		saveStatusBean(request, initStatusBean(request));

		String forwardURL = "";
		try {
			List items = upload.parseRequest(request);
			// 获得返回url
			for (int i = 0; i < items.size(); i++) {
				FileItem item = (FileItem) items.get(i);
				if (item.isFormField()) {
					forwardURL = item.getString();
					break;
				}
			}
			// 文件储存目录
			String uploadDir = "";
			// 处理文件上传
			for (int i = 0; i < items.size(); i++) {
				if (i == 0) {
					// 生成两层随机目录
					uploadDir = StringUtil.createDir(UPLOAD_DIR);
				}
				FileItem item = (FileItem) items.get(i);

				// 取消上传
				if (getStatusBean(request).getCancel()) {
					deleteUploadedFile(request);
					break;
				}

				// 保存文件
				else if (!item.isFormField() && item.getName().length() > 0) {
					String fileName = getFileName(takeOutFileName(item
							.getName()));
					File uploadedFile = new File(
							new File(UPLOAD_DIR, uploadDir), fileName);
					item.write(uploadedFile);
					// 更新上传文件列表
					FileUploadStatus satusBean = getStatusBean(request);
					satusBean.getUploadFileUrlList().add(
							new File(uploadDir, fileName).toString());
					saveStatusBean(request, satusBean);
					Thread.sleep(500);
				}
			}

		} catch (FileUploadException e) {
			uploadExceptionHandle(request, "上传文件时发生错误:" + e.getMessage());
		} catch (Exception e) {
			uploadExceptionHandle(request, "保存上传文件时发生错误:" + e.getMessage());
		}
		if (forwardURL.length() == 0) {
			forwardURL = DEFAULT_UPLOAD_FAILURE_URL;
		}
		request.getRequestDispatcher(forwardURL).forward(request, response);
	}

	/**
	 * 回应上传状态查询
	 */
	private void responseStatusQuery(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		FileUploadStatus satusBean = getStatusBean(request);
		response.getWriter().write(satusBean.toJSon());
	}

	/**
	 * 处理取消文件上传
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

	// 取新文件名
	private String getFileName(String fileName) {
		// SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		int random = (int) (Math.random() * 1000);
		String newFileName = getPrefixName(fileName) + "_" + random
				+ getFileExtension(fileName);
		return newFileName;
	}

	// 取文件名(不含后缀名)
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

	// 取文件后缀名
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
