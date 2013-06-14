package com.glaf.base.modules.todo;

import java.util.*;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.todo.Todo;
import com.glaf.core.todo.TodoTotal;
import com.glaf.core.todo.service.ISysTodoService;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.model.TaskItem;

public class TodoListBean {

	/**
	 * 获取用户的任务汇总
	 * 
	 * @param actorId
	 * @return
	 */
	public List<TodoTotal> getUserTasks(String actorId,
			Map<String, Object> params) {
		List<TodoTotal> userTasks = new ArrayList<TodoTotal>();
		ISysTodoService todoService = ContextFactory.getBean("sysTodoService");
		List<Todo> todoList = todoService.getTodoList();
		List<TaskItem> taskItems = ProcessContainer.getContainer()
				.getTaskItems(actorId);
		Map<String, Todo> todoMap = new HashMap<String, Todo>();
		Map<String, TodoTotal> todoTotalMap = new HashMap<String, TodoTotal>();
		for (Todo todo : todoList) {
			String key = todo.getProcessName() + "_" + todo.getTaskName();
			todoMap.put(key, todo);
			TodoTotal total = new TodoTotal();
			total.setTodo(todo);
			total.setTotalQty(0);
			todoTotalMap.put(key, total);
		}

		for (TaskItem task : taskItems) {
			String key = task.getProcessName() + "_" + task.getTaskName();
			TodoTotal total = todoTotalMap.get(key);
			if (total != null) {
				total.setTotalQty(total.getTotalQty() + 1);
				total.getAllProcessBuffer().append(task.getProcessInstanceId());
				total.getProcessInstanceIds().add(task.getProcessInstanceId());
				todoTotalMap.put(key, total);
			}
		}

		Collection<TodoTotal> values = todoTotalMap.values();
		if (!values.isEmpty()) {
			for (TodoTotal t : values) {
				if (t.getTotalQty() > 0) {
					userTasks.add(t);
				}
			}
		}

		LoginContext loginContext = IdentityFactory.getLoginContext(actorId);

		List<TodoTotal> rows = todoService.getTodoTotalList(loginContext,
				params);
		if (rows != null && !rows.isEmpty()) {
			userTasks.addAll(rows);
		}

		return userTasks;
	}

}
