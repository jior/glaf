package com.glaf.core.base;

public class ConnectionDefinition implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	protected String provider;

	protected String type;

	protected String name;

	protected String subject;

	protected String datasource;

	protected String driver;

	protected String url;

	protected String user;

	protected String password;

	protected boolean autoCommit;

	public ConnectionDefinition() {

	}

	public String getDatasource() {
		return datasource;
	}

	public String getDriver() {
		return driver;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getProvider() {
		return provider;
	}

	public String getSubject() {
		return subject;
	}

	public String getType() {
		return type;
	}

	public String getUrl() {
		return url;
	}

	public String getUser() {
		return user;
	}

	public boolean isAutoCommit() {
		return autoCommit;
	}

	public void setAutoCommit(boolean autoCommit) {
		this.autoCommit = autoCommit;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
