package com.glaf.base.modules.workspace.model;

import java.io.Serializable;

public class MyMenu implements Serializable {

	private static final long serialVersionUID = 3488265849082732239L;

	private long id;

	private long userId;

	private String title;

	private String url;

	private int sort;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

}
