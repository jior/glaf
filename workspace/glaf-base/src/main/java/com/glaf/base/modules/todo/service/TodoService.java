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
	 * ����Todo
	 * 
	 * @param todo
	 */
	void create(ToDo todo);

	/**
	 * �ؽ�ָ������ʵ���Ĵ�������
	 * 
	 * @param processInstanceIds
	 * @param rows
	 */
	void createTasks(Collection<String> processInstanceIds,
			List<ToDoInstance> rows);

	/**
	 * �ؽ�ָ������ʵ���Ĵ�������
	 * 
	 * @param processInstanceId
	 * @param rows
	 */
	void createTasks(String processInstanceId, List<ToDoInstance> rows);

	/**
	 * �ؽ�SQL��ѯ�Ĵ�������
	 * 
	 * @param rows
	 */
	void createTasksOfSQL(List<ToDoInstance> rows);

	/**
	 * �ؽ��������Ĵ�������
	 * 
	 * @param rows
	 */
	void createTasksOfWorkflow(List<ToDoInstance> rows);

	/**
	 * �ؽ�ĳ���û��������Ĵ�������
	 * 
	 * @param actorId
	 * @param rows
	 */
	void createTasksOfWorkflow(String actorId, List<ToDoInstance> rows);

	/**
	 * �ؽ�ĳ��todo�Ĵ�������
	 * 
	 * @param todoId
	 * @param rows
	 */
	void createTodoInstances(long todoId, List<ToDoInstance> rows);

	/**
	 * ��ȡȫ��Todo�б�
	 * 
	 * @return
	 */
	List<ToDo> getAllToDoList();

	/**
	 * ��ȡ������Ϣ
	 * 
	 * @return
	 */
	Map<Long, SysDepartment> getDepartmentMap();

	/**
	 * ��ȡĳ�����ŵĸ�����
	 * 
	 * @param id
	 * @return
	 */
	SysDepartment getParentDepartment(long id);

	/**
	 * ��ȡ��ɫ��Ϣ
	 * 
	 * @return
	 */
	Map<Long, SysRole> getRoleMap();

	/**
	 * ��ȡSQL���õ�Todo
	 * 
	 * @return
	 */
	List<ToDo> getSQLToDos();

	/**
	 * ��ȡ�û����û�����
	 * 
	 * @return
	 */
	List<SysUser> getSysUserWithDeptList();

	/**
	 * ����Todo��Ż�ȡTodo
	 * 
	 * @param todoId
	 * @return
	 */
	ToDo getToDo(long todoId);

	/**
	 * ���ݲ�����ȡ���������б�
	 * 
	 * @param paramMap
	 * @return
	 */
	List<ToDoInstance> getToDoInstanceList(Map<String, Object> paramMap);

	/**
	 * ��ȡTodo�б����ų��Ѿ����õ�
	 * 
	 * @return
	 */
	List<ToDo> getToDoList();

	/**
	 * ��ȡTodo��Ϣ
	 * 
	 * @return
	 */
	Map<String, ToDo> getToDoMap();

	/**
	 * ��ȡ���õ�Todo��Ϣ
	 * 
	 * @return
	 */
	Map<Long, ToDo> getEnabledToDoMap();

	/**
	 * ��ȡ�û���Ϣ
	 * 
	 * @param actorId
	 * @return
	 */
	SysUser getUser(String actorId);

	/**
	 * ��ȡ�û���Ϣ���������š���ɫ
	 * 
	 * @param actorId
	 * @return
	 */
	List<UserEntity> getUserEntityList(String actorId);

	/**
	 * ��ȡ�û�����
	 * 
	 * @return
	 */
	Map<String, SysUser> getUserMap();

	/**
	 * ��ȡÿ���û��Ľ�ɫ����
	 * 
	 * @param actorId
	 * @return
	 */
	Map<String, SysRole> getUserRoleMap(String actorId);

	/**
	 * ��ȡĳ���û���Todo����
	 * 
	 * @param actorId
	 * @return
	 */
	Map<Long, ToDo> getUserTodoMap(String actorId);

	/**
	 * �ж�ĳ���û��Ƿ�������
	 * 
	 * @param actorId
	 * @param taskInstanceId
	 * @return
	 */
	boolean hasTask(String actorId, String taskInstanceId);

	/**
	 * �ж�ĳ�������Ƿ�Ϊ������
	 * 
	 * @param date
	 * @return
	 */
	boolean isWorkDate(java.util.Date date);

	/**
	 * ���������Todo���з���
	 * 
	 * @return
	 */
	Collection<ToDoInstance> populate(Collection<ToDoInstance> rows,
			Map<Long, ToDo> todoMap);

	/**
	 * ��������Todo
	 * 
	 * @param rows
	 */
	void saveAll(List<ToDo> rows);

	/**
	 * �޸�Todo
	 * 
	 * @param todo
	 */
	void update(ToDo todo);
}
