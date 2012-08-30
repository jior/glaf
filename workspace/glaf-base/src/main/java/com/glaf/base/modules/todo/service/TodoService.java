package com.glaf.base.modules.todo.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.todo.model.ToDo;
import com.glaf.base.modules.todo.model.ToDoInstance;
import com.glaf.base.modules.todo.model.UserEntity;

public interface TodoService {

	/**
	 * 创建Todo
	 * 
	 * @param todo
	 */
	void create(ToDo todo);

	/**
	 * 重建指定流程实例的待办事项
	 * 
	 * @param processInstanceIds
	 * @param rows
	 */
	void createTasks(Collection<String> processInstanceIds,
			List<ToDoInstance> rows);

	/**
	 * 重建指定流程实例的待办事项
	 * 
	 * @param processInstanceId
	 * @param rows
	 */
	void createTasks(String processInstanceId, List<ToDoInstance> rows);

	/**
	 * 重建SQL查询的待办事项
	 * 
	 * @param rows
	 */
	void createTasksOfSQL(List<ToDoInstance> rows);

	/**
	 * 重建工作流的待办事项
	 * 
	 * @param rows
	 */
	void createTasksOfWorkflow(List<ToDoInstance> rows);

	/**
	 * 重建某个用户工作流的待办事项
	 * 
	 * @param actorId
	 * @param rows
	 */
	void createTasksOfWorkflow(String actorId, List<ToDoInstance> rows);

	/**
	 * 重建某个todo的待办事项
	 * 
	 * @param todoId
	 * @param rows
	 */
	void createTodoInstances(long todoId, List<ToDoInstance> rows);

	/**
	 * 获取全部Todo列表
	 * 
	 * @return
	 */
	List<ToDo> getAllToDoList();

	/**
	 * 获取部门信息
	 * 
	 * @return
	 */
	Map<Long, SysDepartment> getDepartmentMap();

	/**
	 * 获取某个部门的父部门
	 * 
	 * @param id
	 * @return
	 */
	SysDepartment getParentDepartment(long id);

	/**
	 * 获取角色信息
	 * 
	 * @return
	 */
	Map<Long, SysRole> getRoleMap();

	/**
	 * 获取SQL配置的Todo
	 * 
	 * @return
	 */
	List<ToDo> getSQLToDos();

	/**
	 * 获取用户及用户部门
	 * 
	 * @return
	 */
	List<SysUser> getSysUserWithDeptList();

	/**
	 * 根据Todo编号获取Todo
	 * 
	 * @param todoId
	 * @return
	 */
	ToDo getToDo(long todoId);

	/**
	 * 根据参数获取待办事项列表
	 * 
	 * @param paramMap
	 * @return
	 */
	List<ToDoInstance> getToDoInstanceList(Map<String, Object> paramMap);

	/**
	 * 获取Todo列表，但排除已经禁用的
	 * 
	 * @return
	 */
	List<ToDo> getToDoList();

	/**
	 * 获取Todo信息
	 * 
	 * @return
	 */
	Map<String, ToDo> getToDoMap();

	/**
	 * 获取可用的Todo信息
	 * 
	 * @return
	 */
	Map<Long, ToDo> getEnabledToDoMap();

	/**
	 * 获取用户信息
	 * 
	 * @param actorId
	 * @return
	 */
	SysUser getUser(String actorId);

	/**
	 * 获取用户信息，包含部门、角色
	 * 
	 * @param actorId
	 * @return
	 */
	List<UserEntity> getUserEntityList(String actorId);

	/**
	 * 获取用户集合
	 * 
	 * @return
	 */
	Map<String, SysUser> getUserMap();

	/**
	 * 获取每个用户的角色集合
	 * 
	 * @param actorId
	 * @return
	 */
	Map<String, SysRole> getUserRoleMap(String actorId);

	/**
	 * 获取某个用户的Todo集合
	 * 
	 * @param actorId
	 * @return
	 */
	Map<Long, ToDo> getUserTodoMap(String actorId);

	/**
	 * 判断某个用户是否有任务
	 * 
	 * @param actorId
	 * @param taskInstanceId
	 * @return
	 */
	boolean hasTask(String actorId, String taskInstanceId);

	/**
	 * 判断某个日期是否为工作日
	 * 
	 * @param date
	 * @return
	 */
	boolean isWorkDate(java.util.Date date);

	/**
	 * 将待办事项按Todo进行分组
	 * 
	 * @return
	 */
	Collection<ToDoInstance> populate(Collection<ToDoInstance> rows,
			Map<Long, ToDo> todoMap);

	/**
	 * 批量保存Todo
	 * 
	 * @param rows
	 */
	void saveAll(List<ToDo> rows);

	/**
	 * 修改Todo
	 * 
	 * @param todo
	 */
	void update(ToDo todo);
}
