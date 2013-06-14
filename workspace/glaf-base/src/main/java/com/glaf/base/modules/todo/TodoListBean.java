package com.glaf.base.modules.todo;

import java.util.*;

import com.glaf.base.modules.sys.service.WorkCalendarService;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.todo.Todo;
import com.glaf.core.todo.TodoTotal;
import com.glaf.core.todo.service.ISysTodoService;
import com.glaf.core.todo.util.TodoUtils;
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
		WorkCalendarService workCalendarService = ContextFactory
				.getBean("workCalendarService");
		List<Todo> todoList = todoService.getTodoList();
		List<TaskItem> taskItems = ProcessContainer.getContainer()
				.getTaskItems(actorId);
		Map<String, Todo> todoMap = new HashMap<String, Todo>();
		Map<String, TodoTotal> todoTotalMap = new HashMap<String, TodoTotal>();
		for (Todo todo : todoList) {
			if (todo.getEnableFlag() == 1) {
				String key = todo.getProcessName() + "_" + todo.getTaskName();
				todoMap.put(key, todo);
				TodoTotal total = new TodoTotal();
				total.setTodo(todo);
				total.setTotalQty(0);
				todoTotalMap.put(key, total);
			}
		}

		for (TaskItem task : taskItems) {
			String key = task.getProcessName() + "_" + task.getTaskName();
			Todo todo = todoMap.get(key);
			TodoTotal todoTotal = todoTotalMap.get(key);
			if (todoTotal != null && todo != null) {
				double limitDay = todo.getLimitDay();
				Date limitWorkDate = workCalendarService.getWorkDate(
						task.getCreateDate(), (int) limitDay);
				int status = TodoUtils.getTodoStatus(todo, limitWorkDate);
				todoTotal.setTotalQty(todoTotal.getTotalQty() + 1);
				todoTotal.getProcessInstanceIds().add(
						task.getProcessInstanceId());
				todoTotal.getAllBuffer().append(task.getRowId()).append(",");
				todoTotal.getAllProcessBuffer()
						.append(task.getProcessInstanceId()).append(",");

				switch (status) {
				case TodoUtils.CAUTION_STATUS:
					todoTotal.setCautionQty(todoTotal.getCautionQty() + 1);
					todoTotal.getCaution().add(task.getRowId());
					todoTotal.getCautionBuffer().append(task.getRowId())
							.append(",");
					todoTotal.getCautionProcessBuffer()
							.append(task.getProcessInstanceId()).append(",");
					break;
				case TodoUtils.PAST_DUE_STATUS:
					todoTotal.setPastDueQty(todoTotal.getPastDueQty() + 1);
					todoTotal.getPastDue().add(task.getRowId());
					todoTotal.getPastDueBuffer().append(task.getRowId())
							.append(",");
					todoTotal.getPastDueProcessBuffer()
							.append(task.getProcessInstanceId()).append(",");
					break;
				default:
					todoTotal.setOkQty(todoTotal.getOkQty() + 1);
					todoTotal.getOk().add(task.getRowId());
					todoTotal.getOkBuffer().append(task.getRowId()).append(",");
					todoTotal.getOkProcessBuffer()
							.append(task.getProcessInstanceId()).append(",");
					break;
				}
				todoTotalMap.put(key, todoTotal);
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
