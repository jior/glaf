/** 清空数据

delete from SYS_ACCESS;
delete from SYS_FUNCTION;
delete from SYS_USER_ROLE;
delete from SYS_DEPT_ROLE;
delete from SYS_USER;
delete from SYS_DEPARTMENT;
delete from SYS_APPLICATION;
delete from SYS_TREE;
delete from SYS_ROLE;
delete from SYS_DICTORY;
delete from SYS_DICTORY_DEF;
delete from SYS_DBID;
delete from SYS_PROPERTY;
delete from SYS_WORKCALENDAR;
delete from SYS_PARAMS;
delete from SYS_INPUT_DEF;

**/

/**系统内置主键**/
insert into SYS_DBID(name_, title_, value_, version_) values ('next.dbid', '系统内置主键', '10001', 1);


/*初始化角色信息*/

insert into SYS_ROLE (id, name, roleDesc, sort, code) values (1, '系统管理员', '', 1000, 'SystemAdministrator');
insert into SYS_ROLE (id, name, roleDesc, sort, code) values (16, '分级管理员', '', 2, 'BranchAdmin');
insert into SYS_ROLE (id, name, roleDesc, sort, code) values (17, '流程管理员', '', 1, 'ProcessManager');
insert into SYS_ROLE (id, name, roleDesc, sort, code) values (6,  '部门管理员', '', 3, 'R006');



/*初始系统树信息*/
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (1, 0, '/', '根目录', 1000, '0');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (2, 1, '数据结构', '', 999, '01');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (3, 1, '应用模块', '', 998, '02');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (4, 2, '基础数据', '', 997, '011');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code, discriminator) values (5, 2, '部门结构', '部门结构', 996, '012','D');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code, discriminator) values (6, 5, '技术部', '技术部', 995, 'JS000','D');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (8, 3, '系统管理', '系统管理', 994, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (10, 3, '安全设置', '安全设置', 993, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (11, 8, '系统目录', '系统目录', 992, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (12, 10, '部门管理', '部门管理', 991, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (14, 10, '角色管理', '角色管理', 990, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (15, 10, '模块管理', '模块管理', 989, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (16, 8, '字典管理', '字典管理', 988, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (17, 8, '区域管理', '区域管理', 987, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (20, 3, '个人设置', '个人设置', 986, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (21, 20, '修改密码', '修改密码', 985, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (22, 20, '修改个人信息', '修改个人信息', 984, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (26, 8, '数据重载', '数据重载', 983, '');

insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (42, 3, '统计报表', '统计报表', 890, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (43, 42, '查询管理', '查询管理', 889, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (44, 42, '表管理', '表管理', 888, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (45, 42, '图表管理', '图表管理', 887, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (46, 42, '报表管理', '报表管理', 886, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (47, 42, '报表发送管理', '报表发送管理', 885, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (48, 42, '调度管理', '调度管理', 884, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (49, 42, '任务管理', '任务管理', 883, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (50, 42, '参数设置', '参数设置', 882, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (51, 42, '模板管理', '模板管理', 881, 'template');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (52, 42, '抓取管理', '抓取管理', 880, '');


insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (53, 3, 'Activiti流程管理', 'Activiti流程管理', 590, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (54, 53, '发布流程包', '发布流程包', 589, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (55, 53, '流程定义', '流程定义', 588, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (56, 53, '流程实例', '流程实例', 587, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (57, 53, '历史流程实例', '历史流程实例', 586, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (58, 53, '任务列表', '任务列表', 585, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (59, 53, '流程演示', '流程演示', 584, '');

insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (308, 51, '打印模板', '打印模板', 0, 'print_tpl');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (312, 51, '邮件模板', '邮件模板', 0, 'mail_tpl');

insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (34, 3, '流程管理', '流程管理', 34, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (435, 34, '流程发布', '流程发布', 435, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (437, 34, '流程演示', '流程演示', 437, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (438, 34, '流程监控', '流程监控', 438, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (449, 8, '工作日历', '工作日历', 449, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (450, 8, '系统参数', '系统参数', 450, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (452, 8, '邮件服务设置', '邮件服务设置', 452, '');

insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (494, 8, 'TODO配置', 'TODO配置', 494, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (495, 8, '调度管理', '调度管理', 495, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (498, 4, '货币类型', '货币类型', 498, 'money');

insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (512, 10, '授权管理', '授权管理', 512, '');


insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (701, 3, '内容管理', '内容管理', 0, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (702, 701, '新闻管理', '新闻管理', 0, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (703, 701, '公告管理', '公告管理', 0, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (720, 1, '内容管理分类', '', 0, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (721, 720, '新闻', '新闻', 0, 'news');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (722, 721, '公司新闻', '公司新闻', 0, 'news_01');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (723, 721, '业界新闻', '业界新闻', 0, 'news_02');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (731, 720, '公告', '公告', 0, 'bulletin');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (732, 731, '普通', '', 0, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (733, 731, '重要', '', 0, '');


insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (750, 1, '报表分类', '', 0, 'report_category');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (751, 750, '研发', '研发', 0, 'report_01');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (752, 750, '生产', '生产', 0, 'report_02');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (753, 750, '销售', '销售', 0, 'report_03');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (754, 750, '财务', '财务', 0, 'report_04');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (755, 750, '人事', '人事', 0, 'report_05');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (756, 750, '其他', '其他', 0, 'report_06');



/*初始化部门信息*/
insert into SYS_DEPARTMENT (id, name, deptdesc, createtime, sort, deptno, code, code2, status, fincode, nodeid) values (1, '系统管理部', '系统管理部', null, 284, 'SYS000', 'SYS', 'S', 0, 'SYS000', 10000);
 
/*初始化用户信息*/
insert into SYS_USER (id, deptId, account, password, code, name, blocked, createTime, lastLoginTime, lastLoginIP, mobile, email, telephone, evection, gender, headship, userType, fax, accountType, dumpFlag, adminFlag) values (1, 6, 'root', 'lueSGJZetyySpUndWjMB', 'root', 'root', 0, null, null, '127.0.0.1', '111', 'root@127.0.0.1', '111', 0, 0, '管理员', 40, null, 0, 0, '1');

/*初始化应用信息*/
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (3, '应用模块', '应用模块', '', 1001, 1, 3);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (8, '系统管理', '系统管理', '', 999, 1, 8);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (9, '基础数据', '基础数据', '', 998, 1, 4);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (10, '安全设置', '', '', 997, 2, 10);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (11, '系统目录', '', '/sys/tree.do?method=showMain', 996, 2, 11);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (12, '部门管理', '', '/sys/department.do?method=showFrame', 995, 2, 12);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (14, '角色管理', '', '/sys/role.do?method=showList', 994, 2, 14);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (15, '模块管理', '', '/sys/application.do?method=showFrame', 993, 2, 15);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (16, '字典管理', '', '/sys/dictory.do?method=showFrame', 992, 2, 16);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (17, '区域管理', '', '/mx/sys/district', 991, 2, 17);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (20, '个人设置', '', '', 1000, 2, 20);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (21, '修改密码', '', '/identity/user.do?method=prepareModifyPwd', 980, 2, 21);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (22, '修改个人信息', '', '/identity/user.do?method=prepareModifyInfo', 979, 2, 22);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (26, '数据重载', '', '/sys/dictory.do?method=loadDictory', 976, 2, 26);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (34, '流程管理', '工作流管理', '', 0, 1, 34);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (36, '流程监控', '流程监控', '/mx/jbpm/tree', 36, 1, 438);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (435, '流程发布', '', '/mx/jbpm/deploy', 435, 1, 435);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (437, '流程演示', '', '/workflow/test/index.jsp', 437, 1, 437);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (449, '工作日历', '工作日历', '/sys/workCalendar.do?method=showList', 449, 1, 449);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (450, '系统参数', '系统参数', '/mx/sys/property/edit?category=SYS', 450, 1, 450);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (452, '邮件服务设置', '邮件服务设置', '/mx/sys/mailConfig', 452, 1, 452);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (512, '授权管理', '', '/sys/sysUserRole.do?method=showUsers', 512, 2, 512);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (494, 'TODO配置', '', '/sys/todo.do?method=showList', 494, 1, 494);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (495, '调度管理', '', '/sys/scheduler.do?method=showList', 495, 1, 495);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (42, '统计报表', '统计报表', '', 42, 1, 42);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (43, '查询管理', '', '/mx/dts/query', 43, 1, 43);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (44, '表管理', '表管理', '/mx/dts/table', 44, 1, 44);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (45, '图表管理', '图表管理', '/mx/bi/chart', 45, 1, 45);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (46, '报表管理', '报表管理', '/mx/bi/report', 46, 1, 46);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (47, '报表发送管理', '报表发送管理', '/mx/bi/reportTask', 47, 1, 47);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (48, '调度管理', '调度管理', '/mx/dts/scheduler', 48, 1, 48);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (49, '任务管理', '任务管理', '/mx/dts/task', 49, 1, 49);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (50, '参数设置', '参数设置', '/mx/sys/property/edit?category=RPT', 50, 1, 50);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (51, '模板管理', '模板管理', '/mx/sys/template', 51, 1, 51);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (52, '抓取管理', '抓取管理', '/mx/dts/dataTransfer', 52, 1, 52);

insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (53, 'Activiti流程管理', 'Activiti流程管理', '', 53, 1, 53);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (54, '发布流程包', '', '/mx/activiti/deploy', 54, 1, 54);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (55, '流程定义', '流程定义', '/mx/activiti/process/processDefinitions', 55, 1, 55);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (56, '流程实例', '流程实例', '/mx/activiti/process/processInstances', 56, 1, 56);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (57, '历史流程实例', '历史流程实例', '/mx/activiti/history/historyProcessInstances', 57, 1, 57);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (58, '任务列表', '任务列表', '/mx/activiti/task/taskList', 58, 1, 58);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (59, '流程演示', '流程演示', '/workflow/activiti/index.jsp', 59, 1, 59);

insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid, code) values (71, '内容管理', '', '', 1, 1, 701, null);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid, code) values (72, '新闻管理', '', '/mx/cms/info?serviceKey=news', 2, 1, 702, null);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid, code) values (73, '公告管理', '', '/mx/cms/info?serviceKey=bulletin', 3, 1, 703, null);

/*插入部门角色*/ 
insert into SYS_DEPT_ROLE (id, grade, code, sort, sysroleid, deptid) values (1, 0, null, 0, 1, 1);


/*插入用户角色*/
insert into SYS_USER_ROLE (id, userid, roleid, authorized, authorizefrom, availdatestart, availdateend, processdescription) values (2, 1, 1, 1, 1, null, null, null);
 
   
 
/*插入访问权限*/
insert into SYS_ACCESS (roleid, appid) values (1, 3);
insert into SYS_ACCESS (roleid, appid) values (1, 8);
insert into SYS_ACCESS (roleid, appid) values (1, 9);
insert into SYS_ACCESS (roleid, appid) values (1, 10);
insert into SYS_ACCESS (roleid, appid) values (1, 11);
insert into SYS_ACCESS (roleid, appid) values (1, 12);
insert into SYS_ACCESS (roleid, appid) values (1, 14);
insert into SYS_ACCESS (roleid, appid) values (1, 15);
insert into SYS_ACCESS (roleid, appid) values (1, 16);
insert into SYS_ACCESS (roleid, appid) values (1, 20);
insert into SYS_ACCESS (roleid, appid) values (1, 21);
insert into SYS_ACCESS (roleid, appid) values (1, 22);
insert into SYS_ACCESS (roleid, appid) values (1, 26);

insert into SYS_ACCESS (roleid, appid) values (1, 36);
insert into SYS_ACCESS (roleid, appid) values (1, 34);
insert into SYS_ACCESS (roleid, appid) values (1, 435);
insert into SYS_ACCESS (roleid, appid) values (1, 437);

insert into SYS_ACCESS (roleid, appid) values (1, 449);
insert into SYS_ACCESS (roleid, appid) values (1, 512); 
insert into SYS_ACCESS (roleid, appid) values (1, 494); 
insert into SYS_ACCESS (roleid, appid) values (1, 495); 

/*插入系统功能*/

insert into SYS_FUNCTION (id, name, funcdesc, funcmethod, sort, appid, code) values (1, '模块列表', '模块列表', 'com.glaf.base.modules.sys.springmvc.SysApplicationController.showList', 1, 15, 'app:list');
insert into SYS_FUNCTION (id, name, funcdesc, funcmethod, sort, appid, code) values (2, '增加模块', '增加模块', 'com.glaf.base.modules.sys.springmvc.SysApplicationController.prepareAdd', 2, 15, 'app:create');
insert into SYS_FUNCTION (id, name, funcdesc, funcmethod, sort, appid, code) values (3, '修改模块', '修改模块', 'com.glaf.base.modules.sys.springmvc.SysApplicationController.prepareModify', 3, 15, 'app:update');
insert into SYS_FUNCTION (id, name, funcdesc, funcmethod, sort, appid, code) values (4, '删除模块', '删除模块', 'com.glaf.base.modules.sys.springmvc.SysApplicationController.batchDelete', 4, 15, 'app:delete');


insert into SYS_DICTORY (id, typeId, code, name, sort, dictDesc, blocked, ext1, ext2) values (1, 498, 'CNY', '人民币', 1, null, 0, '', '');
insert into SYS_DICTORY (id, typeId, code, name, sort, dictDesc, blocked, ext1, ext2) values (2, 498, 'USD', '美元', 2, null, 0, '', '');
insert into SYS_DICTORY (id, typeId, code, name, sort, dictDesc, blocked, ext1, ext2) values (3, 498, 'JPY', '日元', 3, null, 0, '', '');
insert into SYS_DICTORY (id, typeId, code, name, sort, dictDesc, blocked, ext1, ext2) values (4, 498, 'HKD', '港币', 4, null, 0, '', '');
insert into SYS_DICTORY (id, typeId, code, name, sort, dictDesc, blocked, ext1, ext2) values (5, 498, 'EUR', '欧元', 5, null, 0, '', '');
 

insert into SYS_DICTORY_DEF (id, nodeid, name, columnname, title, type, length, sort, required, target) values (4,  0, 'ext1',  'EXT1', '',  'String', 200, 3, 0, 'SYS_DICTORY');
insert into SYS_DICTORY_DEF (id, nodeid, name, columnname, title, type, length, sort, required, target) values (5,  0, 'ext2',  'EXT2', '',  'String', 200, 4, 0, 'SYS_DICTORY');
insert into SYS_DICTORY_DEF (id, nodeid, name, columnname, title, type, length, sort, required, target) values (6,  0, 'ext3',  'EXT3', '',  'String', 200, 5, 0, 'SYS_DICTORY');
insert into SYS_DICTORY_DEF (id, nodeid, name, columnname, title, type, length, sort, required, target) values (7,  0, 'ext4',  'EXT4', '',  'String', 200, 5, 0, 'SYS_DICTORY');
insert into SYS_DICTORY_DEF (id, nodeid, name, columnname, title, type, length, sort, required, target) values (8,  0, 'ext5',  'EXT5', '',  'Date', 20, 7, 0, 'SYS_DICTORY');
insert into SYS_DICTORY_DEF (id, nodeid, name, columnname, title, type, length, sort, required, target) values (9,  0, 'ext6',  'EXT6', '',  'Date', 20, 8, 0, 'SYS_DICTORY');
insert into SYS_DICTORY_DEF (id, nodeid, name, columnname, title, type, length, sort, required, target) values (10, 0, 'ext7',  'EXT7', '',  'Date', 20, 9, 0, 'SYS_DICTORY');
insert into SYS_DICTORY_DEF (id, nodeid, name, columnname, title, type, length, sort, required, target) values (11, 0, 'ext8',  'EXT8', '',  'Date', 20, 10, 0, 'SYS_DICTORY');
insert into SYS_DICTORY_DEF (id, nodeid, name, columnname, title, type, length, sort, required, target) values (12, 0, 'ext9',  'EXT9', '',  'Date', 20, 11, 0, 'SYS_DICTORY');
insert into SYS_DICTORY_DEF (id, nodeid, name, columnname, title, type, length, sort, required, target) values (13, 0, 'ext10', 'EXT10', '', 'Date', 20, 12, 0, 'SYS_DICTORY');
insert into SYS_DICTORY_DEF (id, nodeid, name, columnname, title, type, length, sort, required, target) values (14, 0, 'ext11', 'EXT11', '', 'Long', 20, 13, 0, 'SYS_DICTORY');
insert into SYS_DICTORY_DEF (id, nodeid, name, columnname, title, type, length, sort, required, target) values (15, 0, 'ext12', 'EXT12', '', 'Long', 20, 14, 0, 'SYS_DICTORY');
insert into SYS_DICTORY_DEF (id, nodeid, name, columnname, title, type, length, sort, required, target) values (16, 0, 'ext13', 'EXT13', '', 'Long', 20, 15, 0, 'SYS_DICTORY');
insert into SYS_DICTORY_DEF (id, nodeid, name, columnname, title, type, length, sort, required, target) values (17, 0, 'ext14', 'EXT14', '', 'Long', 20, 16, 0, 'SYS_DICTORY');
insert into SYS_DICTORY_DEF (id, nodeid, name, columnname, title, type, length, sort, required, target) values (18, 0, 'ext15', 'EXT15', '', 'Long', 20, 17, 0, 'SYS_DICTORY');
insert into SYS_DICTORY_DEF (id, nodeid, name, columnname, title, type, length, sort, required, target) values (19, 0, 'ext16', 'EXT16', '', 'Double', 20, 18, 0, 'SYS_DICTORY');
insert into SYS_DICTORY_DEF (id, nodeid, name, columnname, title, type, length, sort, required, target) values (20, 0, 'ext17', 'EXT17', '', 'Double', 20, 19, 0, 'SYS_DICTORY');
insert into SYS_DICTORY_DEF (id, nodeid, name, columnname, title, type, length, sort, required, target) values (21, 0, 'ext18', 'EXT18', '', 'Double', 20, 20, 0, 'SYS_DICTORY');
insert into SYS_DICTORY_DEF (id, nodeid, name, columnname, title, type, length, sort, required, target) values (22, 0, 'ext19', 'EXT19', '', 'Double', 20, 21, 0, 'SYS_DICTORY');
insert into SYS_DICTORY_DEF (id, nodeid, name, columnname, title, type, length, sort, required, target) values (23, 0, 'ext20', 'EXT20', '', 'Double', 20, 22, 0, 'SYS_DICTORY');

insert into SYS_PROPERTY (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('1', '系统名称', 0, 'res_system_name', '系统名称', null, 'GLAF基础应用框架', 'SYS', null);
insert into SYS_PROPERTY (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('2', '系统版本', 0, 'res_version', '系统版本', null, 'V3.0', 'SYS', null);
insert into SYS_PROPERTY (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('3', '版权信息', 0, 'res_copyright', '版权信息', null, 'GLAF 版权所有', 'SYS', null);
insert into SYS_PROPERTY (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('4', null, 0, 'res_mail_from', '邮件发送者', null, 'jior2008@gmail.com', 'SYS', null);
insert into SYS_PROPERTY (id_, category_, description_, initvalue_, locked_, name_, title_, type_, value_, inputtype_) values ('5', 'SYS', '限定每个账号只能在一处登录', '[{"name":"限制","value":"true"},{"name":"不限制","value":"false"}]', 0, 'login_limit', '系统登录账号限制', null, 'true', 'combobox');
insert into SYS_PROPERTY (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('6', '在线用户检测时间（秒）', 0, 'login_time_check', '在线用户检测时间（秒）', null, '600', 'SYS', null);
insert into SYS_PROPERTY (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('8', '允许接入系统的IP地址', 0, 'login.allow.ip', '允许接入系统的IP地址', null, '', 'SYS', null);
insert into SYS_PROPERTY (id_, category_, description_, initvalue_, locked_, name_, title_, type_, value_, inputtype_) values ('9', 'SYS', '文件存储方式', '[{"name":"数据库","value":"DATABASE"},{"name":"硬盘","value":"DISK"}]', 0, 'fs_storage_strategy', '文件存储方式', null, 'DATABASE', 'combobox');
insert into sys_property (id_, category_, description_, initvalue_, locked_, name_, title_, type_, value_, inputtype_) values ('10', 'SYS', '存储备份到Mongodb', '[{"name":"是","value":"true"},{"name":"否","value":"false"}]', 0, 'fs_storage_mongodb', '存储备份到Mongodb', null, 'false', 'combobox');


insert into SYS_PROPERTY (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('101', '当天的6位年月格式（YYYYMM），如201312', 0, 'curr_yyyymm', '当前年月', null, '', 'RPT', '${curr_yyyymm}');
insert into SYS_PROPERTY (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('102', '当天的8位日期格式（YYYYMMDD），如20130630', 0, 'curr_yyyymmdd', '当前日期', null, '', 'RPT', '${curr_yyyymmdd}');
insert into SYS_PROPERTY (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('103', '数据文件路径', 0, 'dataDir', '数据文件路径', null, '', 'RPT', null);
insert into SYS_PROPERTY (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('104', '报表输入年月，${curr_yyyymm}表示当前月份', 0, 'input_yyyymm', '报表输入年月', null, '', 'RPT', '');
insert into SYS_PROPERTY (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('105', '报表输入的日期，${curr_yyyymmdd}-1表示当前日期的前一天', 0, 'input_yyyymmdd', '报表输入日期', null, '', 'RPT', '');
insert into SYS_PROPERTY (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('106', '报表生成根目录', 0, 'report_save_path', '报表生成根目录', null, '', 'RPT', null);

insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20140101, 1, 1, 2014);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20140501, 1, 5, 2014); 
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20140502, 2, 5, 2014);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20140503, 3, 5, 2014);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20141001, 1, 10, 2014);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20141002, 2, 10, 2014);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20141003, 3, 10, 2014);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20141004, 4, 10, 2014);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20141005, 5, 10, 2014);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20141006, 6, 10, 2014);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20141007, 7, 10, 2014);

insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20150101, 1, 1, 2015);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20150501, 1, 5, 2015); 
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20150502, 2, 5, 2015);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20150503, 3, 5, 2015);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20151001, 1, 10, 2015);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20151002, 2, 10, 2015);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20151003, 3, 10, 2015);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20151004, 4, 10, 2015);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20151005, 5, 10, 2015);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20151006, 6, 10, 2015);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20151007, 7, 10, 2015);

insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20160101, 1, 1, 2016);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20160501, 1, 5, 2016); 
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20160502, 2, 5, 2016);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20160503, 3, 5, 2016);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20161001, 1, 10, 2016);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20161002, 2, 10, 2016);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20161003, 3, 10, 2016);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20161004, 4, 10, 2016);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20161005, 5, 10, 2016);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20161006, 6, 10, 2016);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20161007, 7, 10, 2016);

insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20170101, 1, 1, 2017);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20170501, 1, 5, 2017); 
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20170502, 2, 5, 2017);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20170503, 3, 5, 2017);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20171001, 1, 10, 2017);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20171002, 2, 10, 2017);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20171003, 3, 10, 2017);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20171004, 4, 10, 2017);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20171005, 5, 10, 2017);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20171006, 6, 10, 2017);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20171007, 7, 10, 2017);

insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20180101, 1, 1, 2018);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20180501, 1, 5, 2018); 
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20180502, 2, 5, 2018);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20180503, 3, 5, 2018);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20181001, 1, 10, 2018);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20181002, 2, 10, 2018);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20181003, 3, 10, 2018);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20181004, 4, 10, 2018);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20181005, 5, 10, 2018);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20181006, 6, 10, 2018);
insert into SYS_WORKCALENDAR (id, freeday, freemonth, freeyear) values (20181007, 7, 10, 2018);

insert into SYS_PARAMS (id, business_key, date_val, double_val, int_val, java_type, key_name, long_val, service_key, string_val, text_val, title, type_cd) values ('news__news_processName', 'news', null, null, null, 'string', 'processName', null, 'news', 'PublicInfoProcess', 'PublicInfoProcess', '流程名称', 'news');
insert into SYS_PARAMS (id, business_key, date_val, double_val, int_val, java_type, key_name, long_val, service_key, string_val, text_val, title, type_cd) values ('bulletin__bulletin_processName', 'bulletin', null, null, null, 'string', 'processName', null, 'bulletin', 'PublicInfoProcess', 'PublicInfoProcess', '流程名称', 'bulletin');

insert into SYS_PARAMS (id, business_key, date_val, double_val, int_val, java_type, key_name, long_val, service_key, string_val, text_val, title, type_cd) values ('SYS_TABLE', 'SYS_TABLE', null, null, null, 'json', 'SYS_TABLE', null, 'SYS_TABLE', '[{"tablename":"SYS_ROLE","title":"角色"},{"tablename":"SYS_TREE","title":"系统树"},{"tablename":"SYS_DEPARTMENT","title":"部门"},{"tablename":"SYS_USER","title":"用户"},{"tablename":"SYS_APPLICATION","title":"模块"},{"tablename":"SYS_DEPT_ROLE","title":"部门角色"},{"tablename":"SYS_USER_ROLE","title":"用户角色"},{"tablename":"SYS_ACCESS","title":"访问权限"},{"tablename":"SYS_PERMISSION","title":"系统权限"},{"tablename":"SYS_FUNCTION","title":"系统功能"},{"tablename":"SYS_DICTORY","title":"数据字典"},{"tablename":"SYS_DICTORY_DEF","title":"字典定义"},{"tablename":"SYS_DBID","title":"系统主键"},{"tablename":"SYS_PROPERTY","title":"系统属性"},{"tablename":"SYS_WORKCALENDAR","title":"工作日历"},{"tablename":"SYS_PARAMS","title":"参数定义"},{"tablename":"SYS_INPUT_DEF","title":"系统输入定义"},{"tablename":"sys_membership","title":"成员关系"},{"tablename":"sys_query","title":"查询定义"},{"tablename":"sys_calendar","title":"日历"},{"tablename":"SYS_SCHEDULER","title":"系统调度"},{"tablename":"SYS_SCHEDULER_params","title":"系统调度参数"}]', '[{"tablename":"SYS_ROLE","title":"角色"},{"tablename":"SYS_TREE","title":"系统树"},{"tablename":"SYS_DEPARTMENT","title":"部门"},{"tablename":"SYS_USER","title":"用户"},{"tablename":"SYS_APPLICATION","title":"模块"},{"tablename":"SYS_DEPT_ROLE","title":"部门角色"},{"tablename":"SYS_USER_ROLE","title":"用户角色"},{"tablename":"SYS_ACCESS","title":"访问权限"},{"tablename":"SYS_PERMISSION","title":"系统权限"},{"tablename":"SYS_FUNCTION","title":"系统功能"},{"tablename":"SYS_DICTORY","title":"数据字典"},{"tablename":"SYS_DICTORY_DEF","title":"字典定义"},{"tablename":"SYS_DBID","title":"系统主键"},{"tablename":"SYS_PROPERTY","title":"系统属性"},{"tablename":"SYS_WORKCALENDAR","title":"工作日历"},{"tablename":"SYS_PARAMS","title":"参数定义"},{"tablename":"SYS_INPUT_DEF","title":"系统输入定义"},{"tablename":"sys_membership","title":"成员关系"},{"tablename":"sys_query","title":"查询定义"},{"tablename":"sys_calendar","title":"日历"},{"tablename":"SYS_SCHEDULER","title":"系统调度"},{"tablename":"SYS_SCHEDULER_params","title":"系统调度参数"}]', '系统表', 'SYS_TABLE');


insert into SYS_INPUT_DEF (id, init_value, input_type, java_type, key_name, required, service_key, text_field, title, type_cd, type_title, url, valid_type, value_field) values ('7', '/rs/jbpm/definition/json', 'combobox', 'String', 'processName', null, 'news', 'text', '流程名称', 'news', '新闻发布', '/rs/jbpm/definition/json', null, 'name');
insert into SYS_INPUT_DEF (id, init_value, input_type, java_type, key_name, required, service_key, text_field, title, type_cd, type_title, url, valid_type, value_field) values ('8', '/rs/jbpm/definition/json', 'combobox', 'String', 'processName', null, 'bulletin', 'text', '流程名称', 'bulletin', '公告发布', '/rs/jbpm/definition/json', null, 'name');


/*UI管理初始化数据*/
insert into UI_PANEL (id_,actorid_,close_,collapsible_,columnindex_,height_,link_,locked_,name_,resize_,title_,type_,width_) values ( '1010','root',0,0,0,250,'/user/todo.do?method=userTasks',0,'todo',0,'待办事项','L',0);
insert into UI_PANEL (id_,actorid_,close_,collapsible_,columnindex_,height_,link_,locked_,name_,resize_,title_,type_,width_) values ( '1016','root',0,0,0,250,'/mx/public/info/indexList?serviceKey=news',0,'news',0,'新闻','L',0);
insert into UI_PANEL (id_,actorid_,close_,collapsible_,columnindex_,height_,link_,locked_,name_,resize_,title_,type_,width_) values ( '1018','root',0,0,0,250,'/mx/public/info/indexList?serviceKey=bulletin',0,'bulletin',0,'公告','L',0);
insert into UI_PANEL (id_,actorid_,close_,collapsible_,columnindex_,content_,height_,locked_,name_,resize_,title_,type_,width_) values ( '1020','root',0,0,0,'<p>&nbsp;</p><p>&nbsp;&nbsp;&nbsp;&nbsp;GLAF 1.0准备发布！</p>',250,0,'notice',0,'通知','T',0);

insert into UI_PANELINSTANCE (id_,name_,panel_,userpanel_) values ( '1024','2','1020','1023');
insert into UI_PANELINSTANCE (id_,name_,panel_,userpanel_) values ( '1025','1','1010','1023');
insert into UI_PANELINSTANCE (id_,name_,panel_,userpanel_) values ( '1026','4','1018','1023');
insert into UI_PANELINSTANCE (id_,name_,panel_,userpanel_) values ( '1027','3','1016','1023');

insert into UI_USERPANEL (id_,actorid_,layoutname_,refreshseconds_) values ( '1023','root','P2',0);

insert into UI_USERPORTAL (id_,actorid_,columnindex_,panelid_,position_) values ( '1028','root',0,'1010',1);
insert into UI_USERPORTAL (id_,actorid_,columnindex_,panelid_,position_) values ( '1029','root',0,'1016',3);
insert into UI_USERPORTAL (id_,actorid_,columnindex_,panelid_,position_) values ( '1030','root',1,'1018',4);
insert into UI_USERPORTAL (id_,actorid_,columnindex_,panelid_,position_) values ( '1031','root',1,'1020',2);

update SYS_APPLICATION set locked = 0 where locked is null;

update SYS_TREE set locked = 0 where locked is null;

