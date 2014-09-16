insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (53, 3, 'Activiti流程管理', 'Activiti流程管理', 590, '53');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (54, 53, '发布流程包', '发布流程包', 589, '54');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (55, 53, '流程定义', '流程定义', 588, '55');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (56, 53, '流程实例', '流程实例', 587, '56');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (57, 53, '历史流程实例', '历史流程实例', 586, '57');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (58, 53, '任务列表', '任务列表', 585, '58');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (59, 53, '流程演示', '流程演示', 584, '59');



insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (53, 'Activiti流程管理', 'Activiti流程管理', '', 53, 1, 53);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (54, '发布流程包', '', '/mx/activiti/deploy', 54, 1, 54);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (55, '流程定义', '流程定义', '/mx/activiti/process/processDefinitions', 55, 1, 55);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (56, '流程实例', '流程实例', '/mx/activiti/process/processInstances', 56, 1, 56);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (57, '历史流程实例', '历史流程实例', '/mx/activiti/history/historyProcessInstances', 57, 1, 57);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (58, '任务列表', '任务列表', '/mx/activiti/task/taskList', 58, 1, 58);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (59, '流程演示', '流程演示', '/workflow/activiti/index.jsp', 59, 1, 59);
