package com.glaf.mail.domain;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.DataFile;
import com.glaf.core.util.DateUtils;

@Entity
@Table(name = "SYS_MAIL_FILE")
public class MailDataFile implements Serializable, DataFile {
	private static final long serialVersionUID = 1L;

	/**
	 * createDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createDate")
	protected Date createDate;

	/**
	 * fileContent
	 */
	@Lob
	@Column(name = "fileContent")
	protected byte[] fileContent;

	/**
	 * fileName
	 */
	@Column(name = "filename", length = 255)
	protected String filename;

	@Id
	@Column(name = "id", length = 50, nullable = false)
	protected String id;

	/**
	 * filesize
	 */
	@Column(name = "size_")
	protected long size;

	/**
	 * topid
	 */
	@Column(name = "topId", length = 100)
	protected String topId;

	@javax.persistence.Transient
	protected InputStream inputStream;

	public MailDataFile() {

	}

	public String getBusinessKey() {
		return null;
	}

	public String getContentType() {

		return null;
	}

	public String getCreateBy() {

		return null;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public byte[] getData() {
		return fileContent;
	}

	public int getDeleteFlag() {
		return 0;
	}

	public String getDeviceId() {

		return null;
	}

	public byte[] getFileContent() {
		return this.fileContent;
	}

	public String getFileId() {
		return id;
	}

	public String getFilename() {
		return this.filename;
	}

	public String getId() {
		return id;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public long getLastModified() {

		return 0;
	}

	public int getLocked() {

		return 0;
	}

	public String getName() {

		return null;
	}

	public String getObjectId() {
		return null;
	}

	public String getObjectValue() {

		return null;
	}

	public String getPath() {
		return null;
	}

	public String getServiceKey() {
		return null;
	}

	public long getSize() {
		return size;
	}

	public int getStatus() {
		return 0;
	}

	public String getTopId() {
		return this.topId;
	}

	public String getType() {

		return null;
	}

	public MailDataFile jsonToObject(JSONObject jsonObject) {
		MailDataFile model = new MailDataFile();

		if (jsonObject.containsKey("topId")) {
			model.setTopId(jsonObject.getString("topId"));
		}
		if (jsonObject.containsKey("fileId")) {
			model.setFileId(jsonObject.getString("fileId"));
		}
		if (jsonObject.containsKey("filename")) {
			model.setFilename(jsonObject.getString("filename"));
		}
		if (jsonObject.containsKey("fileContent")) {
			// model.setFileContent(jsonObject.getString("fileContent"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("size")) {
			model.setSize(jsonObject.getLong("size"));
		}
		return model;
	}

	public void setBusinessKey(String businessKey) {

	}

	public void setContentType(String contentType) {

	}

	public void setCreateBy(String createBy) {

	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setData(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public void setDeleteFlag(int deleteFlag) {

	}

	public void setDeviceId(String deviceId) {

	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public void setFileId(String id) {
		this.id = id;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setLastModified(long lastModified) {

	}

	public void setLocked(int locked) {

	}

	public void setName(String name) {

	}

	public void setObjectId(String objectId) {

	}

	public void setObjectValue(String objectValue) {

	}

	public void setPath(String path) {

	}

	public void setServiceKey(String serviceKey) {

	}

	public void setSize(long size) {
		this.size = size;
	}

	public void setStatus(int status) {

	}

	public void setTopId(String topId) {
		this.topId = topId;
	}

	public void setType(String type) {

	}

	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("_id_", id);
		jsonObject.put("_oid_", id);
		if (topId != null) {
			jsonObject.put("topId", topId);
		}

		if (filename != null) {
			jsonObject.put("filename", filename);
		}
		if (fileContent != null) {
			jsonObject.put("fileContent", fileContent);
		}
		if (createDate != null) {
			jsonObject.put("createDate", DateUtils.getDate(createDate));
			jsonObject.put("createDate_date", DateUtils.getDate(createDate));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(createDate));
		}
		jsonObject.put("size", size);
		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", id);
		jsonObject.put("_id_", id);
		jsonObject.put("_oid_", id);
		if (topId != null) {
			jsonObject.put("topId", topId);
		}

		if (filename != null) {
			jsonObject.put("filename", filename);
		}
		if (fileContent != null) {
			jsonObject.put("fileContent", fileContent);
		}
		if (createDate != null) {
			jsonObject.put("createDate", DateUtils.getDate(createDate));
			jsonObject.put("createDate_date", DateUtils.getDate(createDate));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(createDate));
		}
		jsonObject.put("size", size);
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
