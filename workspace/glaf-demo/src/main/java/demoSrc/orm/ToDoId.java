package demoSrc.orm;

/**
 * VTodoId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ToDoId implements java.io.Serializable {

	// Fields

	private Long processDefinitionId;
	private String processDefinitionName;
	private Long processDefinitionVersion;
	private Long processInstanceId;
	private Long taskInstanceId;
	private Long userId;
	private String userName;
	
	/** default constructor */
	public ToDoId() {
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ToDoId))
			return false;
		ToDoId castOther = (ToDoId) other;

		return ((this.getProcessDefinitionId() == castOther
				.getProcessDefinitionId()) || (this.getProcessDefinitionId() != null
				&& castOther.getProcessDefinitionId() != null && this
				.getProcessDefinitionId().equals(
						castOther.getProcessDefinitionId())))
				&& ((this.getProcessDefinitionName() == castOther
						.getProcessDefinitionName()) || (this
						.getProcessDefinitionName() != null
						&& castOther.getProcessDefinitionName() != null && this
						.getProcessDefinitionName().equals(
								castOther.getProcessDefinitionName())))
				&& ((this.getProcessDefinitionVersion() == castOther
						.getProcessDefinitionVersion()) || (this
						.getProcessDefinitionVersion() != null
						&& castOther.getProcessDefinitionVersion() != null && this
						.getProcessDefinitionVersion().equals(
								castOther.getProcessDefinitionVersion())))
				&& ((this.getProcessInstanceId() == castOther
						.getProcessInstanceId()) || (this
						.getProcessInstanceId() != null
						&& castOther.getProcessInstanceId() != null && this
						.getProcessInstanceId().equals(
								castOther.getProcessInstanceId())))
				&& ((this.getTaskInstanceId() == castOther.getTaskInstanceId()) || (this
						.getTaskInstanceId() != null
						&& castOther.getTaskInstanceId() != null && this
						.getTaskInstanceId().equals(
								castOther.getTaskInstanceId())))
				;
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getProcessDefinitionId() == null ? 0 : this
						.getProcessDefinitionId().hashCode());
		result = 37
				* result
				+ (getProcessDefinitionName() == null ? 0 : this
						.getProcessDefinitionName().hashCode());
		result = 37
				* result
				+ (getProcessDefinitionVersion() == null ? 0 : this
						.getProcessDefinitionVersion().hashCode());
		result = 37
				* result
				+ (getProcessInstanceId() == null ? 0 : this
						.getProcessInstanceId().hashCode());
		result = 37
				* result
				+ (getTaskInstanceId() == null ? 0 : this.getTaskInstanceId()
						.hashCode());
		
		return result;
	}

	public Long getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(Long processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getProcessDefinitionName() {
		return processDefinitionName;
	}

	public void setProcessDefinitionName(String processDefinitionName) {
		this.processDefinitionName = processDefinitionName;
	}

	public Long getProcessDefinitionVersion() {
		return processDefinitionVersion;
	}

	public void setProcessDefinitionVersion(Long processDefinitionVersion) {
		this.processDefinitionVersion = processDefinitionVersion;
	}

	public Long getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(Long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public Long getTaskInstanceId() {
		return taskInstanceId;
	}

	public void setTaskInstanceId(Long taskInstanceId) {
		this.taskInstanceId = taskInstanceId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}





	public ToDoId(Long processDefinitionId, Long processInstanceId,
			Long taskInstanceId) {
		super();
		this.processDefinitionId = processDefinitionId;
		this.processInstanceId = processInstanceId;
		this.taskInstanceId = taskInstanceId;
	}





	


}