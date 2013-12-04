package com.glaf.activiti.test;

import java.util.*;

import org.activiti.engine.*;
import org.activiti.engine.history.*;
import org.activiti.engine.task.*;

public class TenMinuteTutorial {

	public static void main(String[] args) {
		// Create Activiti process engine
		ProcessEngine processEngine = ProcessEngineConfiguration
				.createStandaloneProcessEngineConfiguration()
				.buildProcessEngine();
		// Get Activiti services
		RepositoryService repositoryService = processEngine
				.getRepositoryService();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		// Deploy the process definition
		repositoryService.createDeployment()
				.addClasspathResource("FinancialReport.bpmn20.xml").deploy();
		// Start a process instance
		String procId = runtimeService.startProcessInstanceByKey(
				"financialReport").getId();
		// Get the first task
		TaskService taskService = processEngine.getTaskService();
		List<Task> tasks = taskService.createTaskQuery()
				.taskCandidateGroup("accountancy").list();
		for (Task task : tasks) {
			System.out
					.println("Following task is available for accountancy group: "
							+ task.getName());
			// claim it
			taskService.claim(task.getId(), "fozzie");
		}
		// Verify Fozzie can now retrieve the task
		tasks = taskService.createTaskQuery().taskAssignee("fozzie").list();
		for (Task task : tasks) {
			System.out.println("Task for fozzie: " + task.getName());
			// Complete the task
			taskService.complete(task.getId());
		}
		System.out.println("Number of tasks for fozzie: "
				+ taskService.createTaskQuery().taskAssignee("fozzie").count());
		// Retrieve and claim the second task
		tasks = taskService.createTaskQuery().taskCandidateGroup("management")
				.list();
		for (Task task : tasks) {
			System.out
					.println("Following task is available for accountancy group: "
							+ task.getName());
			taskService.claim(task.getId(), "kermit");
		}
		// Completing the second task ends the process
		for (Task task : tasks) {
			taskService.complete(task.getId());
		}
		// verify that the process is actually finished
		HistoryService historyService = processEngine.getHistoryService();
		HistoricProcessInstance historicProcessInstance = historyService
				.createHistoricProcessInstanceQuery().processInstanceId(procId)
				.singleResult();
		System.out.println("Process instance end time: "
				+ historicProcessInstance.getEndTime());
	}
}
