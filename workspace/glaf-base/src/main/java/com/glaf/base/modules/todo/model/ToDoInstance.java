package com.glaf.base.modules.todo.model;

import java.util.*;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ToDoInstance implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private long id;

	/**
	 * TODO主键
	 */
	private long todoId;

	/**
	 * 应用编号
	 */
	private int appId;

	/**
	 * 模块编号
	 */
	private int moduleId;

	/**
	 * 参与者编号或角色代码
	 */
	private String actorId;

	/**
	 * 参与者名称
	 */
	private String actorName;

	/**
	 * 角色编号
	 */
	private long roleId;

	/**
	 * 角色代码
	 */
	private String roleCode;

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 部门编号
	 */
	private long deptId;

	/**
	 * 部门名称
	 */
	private String deptName;

	/**
	 * 主题
	 */
	private String title;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 链接地址
	 */
	private String link;

	/**
	 * 列表的链接地址
	 */
	private String listLink;

	/**
	 * 链接属性
	 */
	private String linkType;

	/**
	 * 开始日期
	 */
	private Date startDate;

	/**
	 * 结束日期
	 */
	private Date endDate;

	/**
	 * 报警日期
	 */
	private Date alarmDate;

	/**
	 * 过期时间
	 */
	private Date pastDueDate;

	/**
	 * 记录编号
	 */
	private String rowId;

	/**
	 * 流程实例编号
	 */
	private String processInstanceId;

	/**
	 * 任务实例编号
	 */
	private String taskInstanceId;

	/**
	 * 创建日期
	 */
	private Date createDate;

	/**
	 * TODO提供者
	 */
	private String provider;

	/**
	 * 对象编号
	 */
	private String objectId;

	/**
	 * 对象值
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
	 * 状态
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
