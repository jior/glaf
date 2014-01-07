package com.glaf.cluster.catalina.session;

import java.io.Serializable;

public class SessionMetaData implements Serializable {
	private static final long serialVersionUID = -1L;
	/** session��id */
	private String id;
	/** session�Ĵ���ʱ�� */
	private Long createTime;
	/** session��������ʱ�� */
	private Long maxIdle;
	/** session�����һ�η���ʱ�� */
	private Long lastAccessTime;
	/** �Ƿ���� */
	private Boolean validate = false;
	/** ��ǰ�汾 */
	private int version = 0;

	/**
	 * ���췽��
	 */
	public SessionMetaData() {
		this.createTime = System.currentTimeMillis();
		this.lastAccessTime = this.createTime;
		this.validate = true;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public String getId() {
		return id;
	}

	public Long getLastAccessTime() {
		return lastAccessTime;
	}

	public Long getMaxIdle() {
		return maxIdle;
	}

	public Boolean getValidate() {
		return validate;
	}

	public int getVersion() {
		return version;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLastAccessTime(Long lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public void setMaxIdle(Long maxIdle) {
		this.maxIdle = maxIdle;
	}

	public void setValidate(Boolean validate) {
		this.validate = validate;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}