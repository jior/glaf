package com.glaf.base.modules.todo.model;

import java.util.*;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ToDo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ����
	 */
	private long id;

	/**
	 * ����
	 */
	private String code;

	/**
	 * Ӧ�ñ��
	 */
	private int appId = 0;

	/**
	 * ģ����
	 */
	private int moduleId = 0;

	/**
	 * ģ������
	 */
	private String moduleName;

	/**
	 * �����߱�Ż��ɫ����
	 */
	private String actorId;

	/**
	 * ��ɫ���
	 */
	private long roleId = 0;

	/**
	 * ��ɫ����
	 */
	private String roleCode;

	/**
	 * ���ű��
	 */
	private long deptId = 0;

	/**
	 * ��������
	 */
	private String deptName;

	/**
	 * ����
	 */
	private String alarm;

	/**
	 * ����
	 */
	private String news;

	/**
	 * ��ʼ�¼�
	 */
	private String eventFrom;

	/**
	 * �����¼�
	 */
	private String eventTo;

	/**
	 * ��������
	 */
	private int limitDay = 2;

	/**
	 * a Сʱ
	 */
	private int xa = 0;

	/**
	 * Сʱ
	 */
	private int xb = 0;

	/**
	 * ����
	 */
	private String title;

	/**
	 * ����
	 */
	private String content;

	/**
	 * ���ӵ�ַ
	 */
	private String link;

	/**
	 * �б�����ӵ�ַ
	 */
	private String listLink;

	/**
	 * ��������
	 */
	private String linkType;

	/**
	 * ����
	 */
	private String type;

	/**
	 * ��������
	 */
	private String processName;

	/**
	 * ��������
	 */
	private String taskName;

	/**
	 * ����
	 */
	private String tablename;

	/**
	 * ִ�е�sql���
	 */
	private String sql;

	/**
	 * ������
	 */
	private String objectId;

	/**
	 * ����ֵ
	 */
	private String objectValue;

	/**
	 * �Ƿ����á�1�����á�0������
	 */
	private int enableFlag = 1;

	private long versionNo = 0;

	private Collection ok = new HashSet();

	private Collection caution = new HashSet();

	private Collection pastDue = new HashSet();

	public ToDo() {

	}

	public String getActorId() {
		return actorId;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public String getAlarm() {
		return alarm;
	}

	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getDeptId() {
		return deptId;
	}

	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public int getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(int enableFlag) {
		this.enableFlag = enableFlag;
	}

	public String getEventFrom() {
		return eventFrom;
	}

	public void setEventFrom(String eventFrom) {
		this.eventFrom = eventFrom;
	}

	public String getEventTo() {
		return eventTo;
	}

	public void setEventTo(String eventTo) {
		this.eventTo = eventTo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getLimitDay() {
		return limitDay;
	}

	public void setLimitDay(int limitDay) {
		this.limitDay = limitDay;
	}

	public int getXa() {
		return xa;
	}

	public void setXa(int xa) {
		this.xa = xa;
	}

	public int getXb() {
		return xb;
	}

	public void setXb(int xb) {
		this.xb = xb;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getListLink() {
		return listLink;
	}

	public void setListLink(String listLink) {
		this.listLink = listLink;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getNews() {
		return news;
	}

	public void setNews(String news) {
		this.news = news;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(long versionNo) {
		this.versionNo = versionNo;
	}

	public Collection getOk() {
		return ok;
	}

	public void setOk(Collection ok) {
		this.ok = ok;
	}

	public Collection getCaution() {
		return caution;
	}

	public void setCaution(Collection caution) {
		this.caution = caution;
	}

	public Collection getPastDue() {
		return pastDue;
	}

	public void setPastDue(Collection pastDue) {
		this.pastDue = pastDue;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ToDo other = (ToDo) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
