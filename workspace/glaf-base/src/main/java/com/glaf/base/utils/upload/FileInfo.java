package com.glaf.base.utils.upload;

import java.io.File;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.util.DateUtils;

public class FileInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	protected Date createDate;

	protected File file;

	protected String fileId;

	protected String filename;

	protected String path;

	protected long size;

	public FileInfo() {

	}

	public Date getCreateDate() {
		return createDate;
	}

	public File getFile() {
		return file;
	}

	public String getFileId() {
		return fileId;
	}

	public String getFilename() {
		return filename;
	}

	public String getPath() {
		return path;
	}

	public long getSize() {
		return size;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", fileId);
		jsonObject.put("_id_", fileId);
		jsonObject.put("fileId", fileId);
		jsonObject.put("size", size);
		if (filename != null) {
			jsonObject.put("filename", filename);
		}
		if (file != null) {
			jsonObject.put("file", file.getAbsolutePath());
		}
		if (filename != null) {
			jsonObject.put("filename", filename);
		}
		if (path != null) {
			jsonObject.put("path", path);
		}
		if (createDate != null) {
			jsonObject.put("createDate", DateUtils.getDateTime(createDate));
		}
		return jsonObject;
	}

}
