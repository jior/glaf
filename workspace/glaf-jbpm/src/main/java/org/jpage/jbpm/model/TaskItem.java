/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jpage.jbpm.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class TaskItem implements Serializable, Comparable<TaskItem> {

	private static final long serialVersionUID = 1L;

	/**
	 * 参与者编号
	 */
	protected String actorId;

	/**
	 * 参与者姓名
	 */
	protected String actorName;

	protected String rowId;

	/**
	 * 任务实例编号
	 */
	protected String taskInstanceId;

	/**
	 * 任务名称
	 */
	protected String taskName;

	/**
	 * 任务描述
	 */
	protected String taskDescription;

	/**
	 * 任务创建日期
	 */
	protected Date taskCreateDate;

	/**
	 * 流程名称
	 */
	protected String processName;

	/**
	 * 流程实例名称
	 */
	protected String processInstanceId;

	protected String objectId;

	protected String objectValue;

	protected int objectType;

	protected int wfStatus;

	/**
	 * 是否完成
	 */
	protected boolean end;

	public TaskItem() {

	}

	public int compareTo(TaskItem o) {
		if (o == null) {
			return -1;
		}

		int l = taskInstanceId.compareTo(o.getTaskInstanceId());

		int ret = 0;

		if (l > 0) {
			ret = 1;
		} else if (l < 0) {
			ret = -1;
		}
		return ret;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskItem other = (TaskItem) obj;
		if (actorId == null) {
			if (other.actorId != null)
				return false;
		} else if (!actorId.equals(other.actorId))
			return false;
		if (rowId == null) {
			if (other.rowId != null)
				return false;
		} else if (!rowId.equals(other.rowId))
			return false;
		if (taskInstanceId == null) {
			if (other.taskInstanceId != null)
				return false;
		} else if (!taskInstanceId.equals(other.taskInstanceId))
			return false;
		return true;
	}

	public String getActorId() {
		return actorId;
	}

	public String getActorName() {
		return actorName;
	}

	public String getBusinessValue() {
		return rowId;
	}

	public String getObjectId() {
		return objectId;
	}

	public int getObjectType() {
		return objectType;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public String getProcessName() {
		return processName;
	}

	public String getRowId() {
		return rowId;
	}

	public Date getTaskCreateDate() {
		return taskCreateDate;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public String getTaskInstanceId() {
		return taskInstanceId;
	}

	public String getTaskName() {
		return taskName;
	}

	public int getWfStatus() {
		return wfStatus;
	}

	public boolean hasEnded() {
		return end;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actorId == null) ? 0 : actorId.hashCode());
		result = prime * result + ((rowId == null) ? 0 : rowId.hashCode());
		result = prime * result
				+ ((taskInstanceId == null) ? 0 : taskInstanceId.hashCode());
		return result;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public void setTaskCreateDate(Date taskCreateDate) {
		this.taskCreateDate = taskCreateDate;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public void setTaskInstanceId(String taskInstanceId) {
		this.taskInstanceId = taskInstanceId;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setWfStatus(int wfStatus) {
		this.wfStatus = wfStatus;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
