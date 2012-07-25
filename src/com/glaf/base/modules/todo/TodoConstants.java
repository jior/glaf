package com.glaf.base.modules.todo;

import java.util.Date;

import com.glaf.base.modules.todo.model.ToDoInstance;

public class TodoConstants {

	public final static int OK_STATUS = 1;

	public final static int CAUTION_STATUS = 2;

	public final static int PAST_DUE_STATUS = 3;

	private TodoConstants() {

	}

	public static int getTodoStatus(ToDoInstance model) {
		int status = 0;
		if (model != null) {
			long now = System.currentTimeMillis();
			Date startDate = model.getStartDate();
			Date alarmDate = model.getAlarmDate();
			Date pastDueDate = model.getPastDueDate();
			if (pastDueDate != null && now > pastDueDate.getTime()) {
				status = PAST_DUE_STATUS;
			} else if (alarmDate != null && now < alarmDate.getTime()) {
				status = OK_STATUS;
			} else {
				status = CAUTION_STATUS;
			}

			if (startDate != null) {
				if (pastDueDate.getTime() < startDate.getTime()
						|| alarmDate.getTime() < startDate.getTime()) {
					status = PAST_DUE_STATUS;
				}
			}
		}
		return status;
	}
}
