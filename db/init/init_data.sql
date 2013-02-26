


/*初始化角色信息*/
insert into sys_role (id, name, roleDesc, sort, code) values (1, '部长', '', 15, 'R001');
insert into sys_role (id, name, roleDesc, sort, code) values (2, '科长', '', 14, 'R002');
insert into sys_role (id, name, roleDesc, sort, code) values (3, 'CO', '', 12, 'R003');
insert into sys_role (id, name, roleDesc, sort, code) values (4, '系长', '', 13, 'R004');
insert into sys_role (id, name, roleDesc, sort, code) values (5, '预算员', '', 11, 'R005');
insert into sys_role (id, name, roleDesc, sort, code) values (6, '部门管理员', '', 10, 'R006');
insert into sys_role (id, name, roleDesc, sort, code) values (7, '采购联络员', '', 9, 'R007');
insert into sys_role (id, name, roleDesc, sort, code) values (10, '采购担当', '', 6, 'R010');
insert into sys_role (id, name, roleDesc, sort, code) values (11, '申请担当', '使用部门申请起票经办人', 5, 'R011');
insert into sys_role (id, name, roleDesc, sort, code) values (12, '收单担当', '', 4, 'R012');
insert into sys_role (id, name, roleDesc, sort, code) values (15, '系统管理员', '', 1, 'R015');
insert into sys_role (id, name, roleDesc, sort, code) values (18, '主任', '', 18, 'R017');


/*初始系统树信息*/
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (1, 0, '/', '根目录', 1, '0');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (2, 1, '数据结构', '', 20, '01');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (3, 1, '应用模块', '', 10, '02');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (4, 2, '基础数据', '', 50, '011');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (5, 2, '部门结构', '部门结构', 40, '012');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (6, 5, '技术部', '技术部', 284, 'JS000');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (8, 3, '系统管理', '系统管理', 5, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (10, 3, '安全设置', '安全设置', 30, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (11, 8, '系统目录', '系统目录', 20, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (12, 10, '部门管理', '部门管理', 20, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (14, 10, '角色管理', '角色管理', 15, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (15, 10, '模块管理', '模块管理', 12, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (16, 8, '字典管理', '字典管理', 20, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (20, 3, '个人设置', '个人设置', 30, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (21, 20, '修改密码', '修改密码', 30, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (22, 20, '修改个人信息', '修改个人信息', 30, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (26, 8, '数据重载', '数据重载', 26, '');

insert into sys_tree (id, parent, name, nodeDesc, sort, code) values (42, 3, '统计报表', '统计报表', 42, '');
insert into sys_tree (id, parent, name, nodeDesc, sort, code) values (43, 42, '查询管理', '查询管理', 43, '');
insert into sys_tree (id, parent, name, nodeDesc, sort, code) values (44, 42, '表管理', '表管理', 44, '');
insert into sys_tree (id, parent, name, nodeDesc, sort, code) values (45, 42, '图表管理', '图表管理', 45, '');
insert into sys_tree (id, parent, name, nodeDesc, sort, code) values (46, 42, '报表管理', '报表管理', 46, '');
insert into sys_tree (id, parent, name, nodeDesc, sort, code) values (47, 42, '报表发送管理', '报表发送管理', 47, '');
insert into sys_tree (id, parent, name, nodeDesc, sort, code) values (48, 42, '调度管理', '调度管理', 48, '');
insert into sys_tree (id, parent, name, nodeDesc, sort, code) values (49, 42, '任务管理', '任务管理', 49, '');

insert into sys_tree (id, parent, name, nodedesc, sort, code) values (190, 163, '修改密码', '修改密码', 188, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (231, 229, '基础数据', '基础数据', 230, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (235, 231, '修改密码', '修改密码', 235, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (236, 231, '修改资料', '修改资料', 236, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (34, 3, '流程管理', '流程管理', 34, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (435, 34, '流程发布', '流程发布', 435, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (437, 34, '流程演示', '流程演示', 437, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (438, 34, '流程监控', '流程监控', 438, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (449, 8, '工作日历', '工作日历', 449, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (482, 10, '授权管理', '授权管理', 482, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (494, 8, 'TODO配置', 'TODO配置', 494, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (495, 8, '调度管理', '调度管理', 495, '');
insert into sys_tree (id, parent, name, nodeDesc, sort, code) values (498, 4, '货币类型', '货币类型', 498, 'money');

/*初始化部门信息*/
insert into sys_department (id, name, deptdesc, createtime, sort, deptno, code, code2, status, fincode, nodeid) values (6, '技术部', '技术部', '2006-07-16 16:30:39', 284, 'JS000', 'JS', 'J', 0, 'JS000', 6);
 
/*初始化用户信息*/
insert into sys_user (id, deptId, account, password, code, name, blocked, createTime, lastLoginTime, lastLoginIP, mobile, email, telephone, evection, gender, headship, userType, fax, accountType, dumpFlag, adminFlag) values (1, 6, 'root', 'lueSGJZetyySpUndWjMB', 'root', 'root', 0, '2006-07-16 16:41:52', '2009-07-08 10:27:05', '127.0.0.1', '111', 'admin@127.0.0.1', '111', 0, 0, '担当', 40, null, 0, 0, '1');
insert into sys_user (id, deptId, account, password, code, name, blocked, createTime, lastLoginTime, lastLoginIP, mobile, email, telephone, evection, gender, headship, userType, fax, accountType, dumpFlag, adminFlag) values (2, 6, 'admin', 'lueSGJZetyySpUndWjMB', 'admin', 'admin', 0, '2006-07-16 16:41:52', '2009-07-08 10:27:05', '127.0.0.1', '111', 'admin@127.0.0.1', '111', 0, 0, '担当', 40, null, 0, 0, '1');

/*初始化应用信息*/
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (3, '应用模块', '应用模块', '', 3, 1, 3);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (8, '系统管理', '系统管理', '', 5, 1, 8);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (9, '基础数据', '基础数据', '', 30, 1, 9);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (10, '安全设置', '', '', 14, 2, 10);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (11, '系统目录', '', '/sys/tree.do?method=showMain', 10, 2, 11);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (12, '部门管理', '', '/sys/department.do?method=showFrame', 20, 2, 12);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (14, '角色管理', '', '/sys/role.do?method=showList', 15, 2, 14);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (15, '模块管理', '', '/sys/application.do?method=showFrame', 12, 2, 15);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (16, '字典管理', '', '/sys/dictory.do?method=showFrame', 15, 2, 16);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (20, '个人设置', '', '', 15, 2, 20);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (21, '修改密码', '', '/sys/user.do?method=prepareModifyPwd', 15, 2, 21);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (22, '修改个人信息', '', '/sys/user.do?method=prepareModifyInfo', 15, 2, 22);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (26, '数据重载', '', '/sys/dictory.do?method=loadDictory', 10, 2, 26);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (34, '流程管理', '工作流管理', '', 0, 1, 34);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (36, '流程监控', '流程监控', '/mx/jbpm/tree', 36, 1, 438);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (435, '流程发布', '', '/mx/jbpm/deploy', 435, 1, 435);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (437, '流程演示', '', '/workflow/test/index.jsp', 437, 1, 437);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (449, '工作日历', '工作日历', '/others/workCalendar.do?method=showList', 449, 1, 449);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (482, '授权管理', '', '/sys/sysUserRole.do?method=showUsers', 482, 2, 482);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (494, 'TODO配置', '', '/sys/todo.do?method=showList', 494, 1, 494);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (495, '调度管理', '', '/sys/scheduler.do?method=showList', 495, 1, 495);
insert into sys_application (id, name, appDesc, url, sort, showMenu, nodeId) values (42, '统计报表', '统计报表', '', 42, 1, 42);
insert into sys_application (id, name, appDesc, url, sort, showMenu, nodeId) values (43, '查询管理', '', '/mx/dts/query', 43, 1, 43);
insert into sys_application (id, name, appDesc, url, sort, showMenu, nodeId) values (44, '表管理', '表管理', '/mx/dts/table', 44, 1, 44);
insert into sys_application (id, name, appDesc, url, sort, showMenu, nodeId) values (45, '图表管理', '图表管理', '/mx/bi/chart', 45, 1, 45);
insert into sys_application (id, name, appDesc, url, sort, showMenu, nodeId) values (46, '报表管理', '报表管理', '/mx/bi/report', 46, 1, 46);
insert into sys_application (id, name, appDesc, url, sort, showMenu, nodeId) values (47, '报表发送管理', '报表发送管理', '/mx/bi/reportTask', 47, 1, 47);
insert into sys_application (id, name, appDesc, url, sort, showMenu, nodeId) values (48, '调度管理', '调度管理', '/mx/dts/scheduler', 48, 1, 48);
insert into sys_application (id, name, appDesc, url, sort, showMenu, nodeId) values (49, '任务管理', '任务管理', '/mx/dts/task', 49, 1, 49);


/*插入部门角色*/ 
insert into sys_dept_role (id, grade, code, sort, sysroleid, deptid) values (1, 0, null, 0, 15, 6);


/*插入用户角色*/
insert into sys_user_role (id, userid, roleid, authorized, authorizefrom, availdatestart, availdateend, processdescription) values (2, 1, 1, 1, 1, null, null, null);
insert into sys_user_role (id, userid, roleid, authorized, authorizefrom, availdatestart, availdateend, processdescription) values (4, 2, 1, 2, 2, null, null, null);

   
 
/*插入访问权限*/
insert into sys_access (roleid, appid) values (1, 3);
insert into sys_access (roleid, appid) values (1, 8);
insert into sys_access (roleid, appid) values (1, 9);
insert into sys_access (roleid, appid) values (1, 10);
insert into sys_access (roleid, appid) values (1, 11);
insert into sys_access (roleid, appid) values (1, 12);
insert into sys_access (roleid, appid) values (1, 14);
insert into sys_access (roleid, appid) values (1, 15);
insert into sys_access (roleid, appid) values (1, 16);
insert into sys_access (roleid, appid) values (1, 20);
insert into sys_access (roleid, appid) values (1, 21);
insert into sys_access (roleid, appid) values (1, 22);
insert into sys_access (roleid, appid) values (1, 26);

insert into sys_access (roleid, appid) values (1, 36);
insert into sys_access (roleid, appid) values (1, 34);
insert into sys_access (roleid, appid) values (1, 435);
insert into sys_access (roleid, appid) values (1, 437);

insert into sys_access (roleid, appid) values (1, 449);
insert into sys_access (roleid, appid) values (1, 482); 
insert into sys_access (roleid, appid) values (1, 494); 
insert into sys_access (roleid, appid) values (1, 495); 

/*插入系统功能*/
insert into sys_function (id, appId, name, funcDesc, funcMethod, sort) values (1, 15, '模块列表', null, 'com.glaf.base.modules.sys.action.SysApplicationAction.showList', 5);
insert into sys_function (id, appId, name, funcDesc, funcMethod, sort) values (2, 15, '增加模块', null, 'com.glaf.base.modules.sys.action.SysApplicationAction.prepareAdd', 3);
insert into sys_function (id, appId, name, funcDesc, funcMethod, sort) values (3, 15, '修改模块', null, 'com.glaf.base.modules.sys.action.SysApplicationAction.prepareModify', 2);
insert into sys_function (id, appId, name, funcDesc, funcMethod, sort) values (4, 15, '删除模块', null, 'com.glaf.base.modules.sys.action.SysApplicationAction.batchDelete', 1);

insert into sys_function (id, appId, name, funcDesc, funcMethod, sort) values (11, 15, '模块列表', null, 'com.glaf.base.modules.sys.springmvc.SysApplicationController.showList', 5);
insert into sys_function (id, appId, name, funcDesc, funcMethod, sort) values (12, 15, '增加模块', null, 'com.glaf.base.modules.sys.springmvc.SysApplicationController.prepareAdd', 3);
insert into sys_function (id, appId, name, funcDesc, funcMethod, sort) values (13, 15, '修改模块', null, 'com.glaf.base.modules.sys.springmvc.SysApplicationController.prepareModify', 2);
insert into sys_function (id, appId, name, funcDesc, funcMethod, sort) values (14, 15, '删除模块', null, 'com.glaf.base.modules.sys.springmvc.SysApplicationController.batchDelete', 1);


insert into  sys_permission (roleid, funcid) values (1, 1);
insert into  sys_permission (roleid, funcid) values (1, 2);
insert into  sys_permission (roleid, funcid) values (1, 3);
insert into  sys_permission (roleid, funcid) values (1, 4);


insert into sys_dictory (id, typeId, code, name, sort, dictDesc, blocked, ext1, ext2, ext3, ext4, ext5, ext6) values (1, 498, 'RMB', '人民币', 1, null, 0, '', '', null, null, null, null);
insert into sys_dictory (id, typeId, code, name, sort, dictDesc, blocked, ext1, ext2, ext3, ext4, ext5, ext6) values (2, 498, 'USD', '美元', 2, null, 0, '', '', null, null, null, null);
insert into sys_dictory (id, typeId, code, name, sort, dictDesc, blocked, ext1, ext2, ext3, ext4, ext5, ext6) values (3, 498, 'JPY', '日元', 3, null, 0, '', '', null, null, null, null);


insert into sys_dbid (NAME_, VALUE_, VERSION_) values ('next.dbid', '10001', 1);