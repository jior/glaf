package com.glaf.base.modules.sys.model;

public class RealmInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	protected String item;

	protected String url;

	public RealmInfo() {

	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
