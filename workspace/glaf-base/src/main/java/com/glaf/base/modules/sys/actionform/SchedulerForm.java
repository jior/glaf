package com.glaf.base.modules.sys.actionform;

import java.util.*;
import org.apache.struts.action.ActionForm;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SchedulerForm extends ActionForm {
	private static final long serialVersionUID = 1L;

	protected String id;

	protected String taskName;

	protected String taskType;

	protected String jobClass;

	protected String title;

	protected String content;

	protected String attribute;

	protected int threadSize;

	protected int repeatCount;

	protected int repeatInterval;

	protected int startDelay;

	protected int priority;

	protected int locked;

	protected int startup;

	protected int autoStartup;

	protected String expression;

	protected String createBy;

	public SchedulerForm() {

	}

	public String getAttribute() {
		return attribute;
	}

	public int getAutoStartup() {
		return autoStartup;
	}

	public String getContent() {
		return content;
	}

	public String getCreateBy() {
		return createBy;
	}

	public String getExpression() {
		return expression;
	}

	public String getId() {
		return id;
	}

	public String getJobClass() {
		return jobClass;
	}

	public int getLocked() {
		return locked;
	}

	public int getPriority() {
		return priority;
	}

	public int getRepeatCount() {
		return repeatCount;
	}

	public int getRepeatInterval() {
		return repeatInterval;
	}

	public int getStartDelay() {
		return startDelay;
	}

	public int getStartup() {
		return startup;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getTaskType() {
		return taskType;
	}

	public int getThreadSize() {
		return threadSize;
	}

	public String getTitle() {
		return title;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public void setAutoStartup(int autoStartup) {
		this.autoStartup = autoStartup;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

	public void setRepeatInterval(int repeatInterval) {
		this.repeatInterval = repeatInterval;
	}

	public void setStartDelay(int startDelay) {
		this.startDelay = startDelay;
	}

	public void setStartup(int startup) {
		this.startup = startup;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public void setThreadSize(int threadSize) {
		this.threadSize = threadSize;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
