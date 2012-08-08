package com.glaf.base.modules.others.action;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;

import com.glaf.base.modules.Constants;
import com.glaf.base.modules.others.model.Attachment;
import com.glaf.base.modules.others.service.AttachmentService;
import com.glaf.base.utils.ParamUtil;

public class AttachmentDownloadAction extends DownloadAction {
	private static final Log logger = LogFactory
			.getLog(AttachmentDownloadAction.class);

	private AttachmentService attachmentService;

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
		logger.info("setAttachmentService");
	}

	/**
	 * 下载附件
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected StreamInfo getStreamInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StreamInfo sinfo = null;
		long id = ParamUtil.getLongParameter(request, "id", 0);
		long referId = ParamUtil.getLongParameter(request, "referId", 0);
		int referType = ParamUtil.getIntParameter(request, "referType", 0);
		List list = attachmentService.getAttachmentList(referId, referType);
		Attachment atta = null;
		logger.info("referId = " + referId);
		logger.info("referType = " + referType);
		logger.info("查找前附件的Id为   " + id + "       ,        list = " + list);
		if (list != null && list.size() > 0 && id == 0) {
			atta = (Attachment) list.get(0);
			if (atta != null) {
				id = atta.getId();
			}
		}
		logger.info("查找后附件的Id为   " + id);
		Attachment attachment = attachmentService.find(id, referId, referType);
		if (attachment == null) {
			throw new IllegalArgumentException("Attachment " + id
					+ " File not found.");
		}

		String file = Constants.UPLOAD_DIR + attachment.getUrl();
		String contentType = "application/x-msdownload";
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String((FilenameUtils.getName(file)).getBytes("GBK"),
						"ISO-8859-1"));
		sinfo = new FileStreamInfo(contentType, new File(request.getSession()
				.getServletContext().getRealPath(file)));
		return sinfo;
	}

}
