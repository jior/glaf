package com.glaf.cluster.catalina.session;

import java.io.Serializable;

public class SessionMetaData implements Serializable {
	private static final long serialVersionUID = -1L;
	/** session的id */
	private String id;
	/** session的创建时间 */
	private Long createTime;
	/** session的最大空闲时间 */
	private Long maxIdle;
	/** session的最后一次访问时间 */
	private Long lastAccessTime;
	/** 是否可用 */
	private Boolean validate = false;
	/** 当前版本 */
	private int version = 0;

	/**
	 * 构造方法
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