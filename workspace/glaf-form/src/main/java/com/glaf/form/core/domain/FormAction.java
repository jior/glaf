package com.glaf.form.core.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.glaf.core.base.JSONable;
import com.glaf.form.core.util.*;

@Entity
@Table(name = "FORM_ACTION")
public class FormAction implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	/**
	 * ����
	 */
	@Column(name = "NAME_")
	protected String name;

	/**
	 * ����
	 */
	@Column(name = "TITLE_")
	protected String title;

	/**
	 * ������ַ
	 */
	@Column(name = "URL_")
	protected String url;

	/**
	 * ����������
	 */
	@Column(name = "PROVIDER_")
	protected String provider;

	/**
	 * ������
	 */
	@Column(name = "CREATEBY_")
	protected String createBy;

	/**
	 * ��������
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	/**
	 * ����
	 */
	@Column(name = "DESCRIPTION_")
	protected String description;

	/**
	 * �����
	 */
	@Column(name = "ACTIVITY_")
	protected String activity;

	/**
	 * ������
	 */
	@Column(name = "FORMNAME_")
	protected String formName;

	/**
	 * Ӧ�ñ��
	 */
	@Column(name = "APPID_")
	protected String appId;

	public FormAction() {

	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getTitle() {
		return this.title;
	}

	public String getUrl() {
		return this.url;
	}

	public String getProvider() {
		return this.provider;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public String getDescription() {
		return this.description;
	}

	public String getActivity() {
		return this.activity;
	}

	public String getFormName() {
		return this.formName;
	}

	public String getAppId() {
		return this.appId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormAction other = (FormAction) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public FormAction jsonToObject(JSONObject jsonObject) {
		return FormActionJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return FormActionJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FormActionJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
