package com.glaf.base.modules.todo.model;

import java.util.*;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ToDoInstance implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ����
	 */
	private long id;

	/**
	 * TODO����
	 */
	private long todoId;

	/**
	 * Ӧ�ñ��
	 */
	private int appId;

	/**
	 * ģ����
	 */
	private int moduleId;

	/**
	 * �����߱�Ż��ɫ����
	 */
	private String actorId;

	/**
	 * ����������
	 */
	private String actorName;

	/**
	 * ��ɫ���
	 */
	private long roleId;

	/**
	 * ��ɫ����
	 */
	private String roleCode;

	/**
	 * ��ɫ����
	 */
	private String roleName;

	/**
	 * ���ű��
	 */
	private long deptId;

	/**
	 * ��������
	 */
	private String deptName;

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
	 * ��ʼ����
	 */
	private Date startDate;

	/**
	 * ��������
	 */
	private Date endDate;

	/**
	 * ��������
	 */
	private Date alarmDate;

	/**
	 * ����ʱ��
	 */
	private Date pastDueDate;

	/**
	 * ��¼���
	 */
	private String rowId;

	/**
	 * ����ʵ�����
	 */
	private String processInstanceId;

	/**
	 * ����ʵ�����
	 */
	private String taskInstanceId;

	/**
	 * ��������
	 */
	private Date createDate;

	/**
	 * TODO�ṩ��
	 */
	private String provider;

	/**
	 * ������
	 */
	private String objectId;

	/**
	 * ����ֵ
	 */
	private String objectValue;

	/**
	 * OK
	 */
	private int level01;

	/**
	 * Caution
	 */
	private int level02;

	/**
	 * Past Due
	 */
	private int level03;

	/**
	 * OK
	 */
	private int qty01;

	/**
	 * Caution
	 */
	private int qty02;

	/**
	 * Past Due
	 */
	private int qty03;
	
	private int qtyRedWarn;

	/**
	 * ״̬
	 */
	private int status;

	private long versionNo;

	private ToDo toDo;

	public ToDoInstance() {

	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ToDoInstance other = (ToDoInstance) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public String getActorId() {
		return actorId;
	}

	public String getActorName() {
		return actorName;
	}

	public Date getAlarmDate() {
		return alarmDate;
	}

	public int getAppId() {
		return appId;
	}

	public String getContent() {
		return content;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public long getDeptId() {
		return deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public Date getEndDate() {
		return endDate;
	}

	public long getId() {
		return id;
	}

	public int getLevel01() {
		return level01;
	}

	public int getLevel02() {
		return level02;
	}

	public int getLevel03() {
		return level03;
	}

	public String getLink() {
		return link;
	}

	public String getLinkType() {
		return linkType;
	}

	public String getListLink() {
		return listLink;
	}

	public int getModuleId() {
		return moduleId;
	}

	public String getObjectId() {
		return objectId;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public Date getPastDueDate() {
		return pastDueDate;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public String getProvider() {
		return provider;
	}

	public int getQty01() {
		return qty01;
	}

	public int getQty02() {
		return qty02;
	}

	public int getQty03() {
		return qty03;
	}

	public int getQtyRedWarn() {
		return qtyRedWarn;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public long getRoleId() {
		return roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public String getRowId() {
		if (rowId != null) {
			rowId = rowId.trim();
		}
		return rowId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public int getStatus() {
		return status;
	}

	public String getTaskInstanceId() {
		return taskInstanceId;
	}

	public String getTitle() {
		return title;
	}

	public ToDo getToDo() {
		return toDo;
	}

	public long getTodoId() {
		return todoId;
	}

	public long getVersionNo() {
		return versionNo;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	public void setAlarmDate(Date alarmDate) {
		this.alarmDate = alarmDate;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLevel01(int level01) {
		this.level01 = level01;
	}

	public void setLevel02(int level02) {
		this.level02 = level02;
	}

	public void setLevel03(int level03) {
		this.level03 = level03;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public void setListLink(String listLink) {
		this.listLink = listLink;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

	public void setPastDueDate(Date pastDueDate) {
		this.pastDueDate = pastDueDate;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public void setQty01(int qty01) {
		this.qty01 = qty01;
	}

	public void setQty02(int qty02) {
		this.qty02 = qty02;
	}

	public void setQty03(int qty03) {
		this.qty03 = qty03;
	}

	public void setQtyRedWarn(int qtyRedWarn) {
		this.qtyRedWarn = qtyRedWarn;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setTaskInstanceId(String taskInstanceId) {
		this.taskInstanceId = taskInstanceId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setToDo(ToDo toDo) {
		this.toDo = toDo;
	}

	public void setTodoId(long todoId) {
		this.todoId = todoId;
	}

	public void setVersionNo(long versionNo) {
		this.versionNo = versionNo;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
