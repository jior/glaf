


/*��ʼ����ɫ��Ϣ*/
insert into sys_role (id, name, roleDesc, sort, code) values (1, '����', '', 15, 'R001');
insert into sys_role (id, name, roleDesc, sort, code) values (2, '�Ƴ�', '', 14, 'R002');
insert into sys_role (id, name, roleDesc, sort, code) values (3, 'CO', '', 12, 'R003');
insert into sys_role (id, name, roleDesc, sort, code) values (4, 'ϵ��', '', 13, 'R004');
insert into sys_role (id, name, roleDesc, sort, code) values (5, 'Ԥ��Ա', '', 11, 'R005');
insert into sys_role (id, name, roleDesc, sort, code) values (6, '���Ź���Ա', '', 10, 'R006');
insert into sys_role (id, name, roleDesc, sort, code) values (7, '�ɹ�����Ա', '', 9, 'R007');
insert into sys_role (id, name, roleDesc, sort, code) values (10, '�ɹ�����', '', 6, 'R010');
insert into sys_role (id, name, roleDesc, sort, code) values (11, '���뵣��', 'ʹ�ò���������Ʊ������', 5, 'R011');
insert into sys_role (id, name, roleDesc, sort, code) values (12, '�յ�����', '', 4, 'R012');
insert into sys_role (id, name, roleDesc, sort, code) values (15, 'ϵͳ����Ա', '', 1, 'R015');
insert into sys_role (id, name, roleDesc, sort, code) values (18, '����', '', 18, 'R017');


/*��ʼϵͳ����Ϣ*/
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (1, 0, '/', '��Ŀ¼', 1, '0');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (2, 1, '���ݽṹ', '', 20, '01');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (3, 1, 'Ӧ��ģ��', '', 10, '02');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (4, 2, '��������', '', 50, '011');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (5, 2, '���Žṹ', '���Žṹ', 40, '012');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (6, 5, '������', '������', 284, 'JS000');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (8, 3, 'ϵͳ����', 'ϵͳ����', 5, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (10, 3, '��ȫ����', '��ȫ����', 30, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (11, 8, 'ϵͳĿ¼', 'ϵͳĿ¼', 20, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (12, 10, '���Ź���', '���Ź���', 20, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (14, 10, '��ɫ����', '��ɫ����', 15, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (15, 10, 'ģ�����', 'ģ�����', 12, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (16, 8, '�ֵ����', '�ֵ����', 20, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (20, 3, '��������', '��������', 30, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (21, 20, '�޸�����', '�޸�����', 30, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (22, 20, '�޸ĸ�����Ϣ', '�޸ĸ�����Ϣ', 30, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (26, 8, '��������', '��������', 26, '');

insert into sys_tree (id, parent, name, nodeDesc, sort, code) values (42, 3, 'ͳ�Ʊ���', 'ͳ�Ʊ���', 42, '');
insert into sys_tree (id, parent, name, nodeDesc, sort, code) values (43, 42, '��ѯ����', '��ѯ����', 43, '');
insert into sys_tree (id, parent, name, nodeDesc, sort, code) values (44, 42, '�����', '�����', 44, '');
insert into sys_tree (id, parent, name, nodeDesc, sort, code) values (45, 42, 'ͼ�����', 'ͼ�����', 45, '');
insert into sys_tree (id, parent, name, nodeDesc, sort, code) values (46, 42, '�������', '�������', 46, '');
insert into sys_tree (id, parent, name, nodeDesc, sort, code) values (47, 42, '�����͹���', '�����͹���', 47, '');
insert into sys_tree (id, parent, name, nodeDesc, sort, code) values (48, 42, '���ȹ���', '���ȹ���', 48, '');
insert into sys_tree (id, parent, name, nodeDesc, sort, code) values (49, 42, '�������', '�������', 49, '');

insert into sys_tree (id, parent, name, nodedesc, sort, code) values (190, 163, '�޸�����', '�޸�����', 188, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (231, 229, '��������', '��������', 230, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (235, 231, '�޸�����', '�޸�����', 235, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (236, 231, '�޸�����', '�޸�����', 236, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (34, 3, '���̹���', '���̹���', 34, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (435, 34, '���̷���', '���̷���', 435, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (437, 34, '������ʾ', '������ʾ', 437, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (438, 34, '���̼��', '���̼��', 438, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (449, 8, '��������', '��������', 449, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (482, 10, '��Ȩ����', '��Ȩ����', 482, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (494, 8, 'TODO����', 'TODO����', 494, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (495, 8, '���ȹ���', '���ȹ���', 495, '');
insert into sys_tree (id, parent, name, nodeDesc, sort, code) values (498, 4, '��������', '��������', 498, 'money');

/*��ʼ��������Ϣ*/
insert into sys_department (id, name, deptdesc, createtime, sort, deptno, code, code2, status, fincode, nodeid) values (6, '������', '������', '2006-07-16 16:30:39', 284, 'JS000', 'JS', 'J', 0, 'JS000', 6);
 
/*��ʼ���û���Ϣ*/
insert into sys_user (id, deptId, account, password, code, name, blocked, createTime, lastLoginTime, lastLoginIP, mobile, email, telephone, evection, gender, headship, userType, fax, accountType, dumpFlag, adminFlag) values (1, 6, 'root', 'lueSGJZetyySpUndWjMB', 'root', 'root', 0, '2006-07-16 16:41:52', '2009-07-08 10:27:05', '127.0.0.1', '111', 'admin@127.0.0.1', '111', 0, 0, '����', 40, null, 0, 0, '1');
insert into sys_user (id, deptId, account, password, code, name, blocked, createTime, lastLoginTime, lastLoginIP, mobile, email, telephone, evection, gender, headship, userType, fax, accountType, dumpFlag, adminFlag) values (2, 6, 'admin', 'lueSGJZetyySpUndWjMB', 'admin', 'admin', 0, '2006-07-16 16:41:52', '2009-07-08 10:27:05', '127.0.0.1', '111', 'admin@127.0.0.1', '111', 0, 0, '����', 40, null, 0, 0, '1');

/*��ʼ��Ӧ����Ϣ*/
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (3, 'Ӧ��ģ��', 'Ӧ��ģ��', '', 3, 1, 3);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (8, 'ϵͳ����', 'ϵͳ����', '', 5, 1, 8);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (9, '��������', '��������', '', 30, 1, 9);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (10, '��ȫ����', '', '', 14, 2, 10);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (11, 'ϵͳĿ¼', '', '/sys/tree.do?method=showMain', 10, 2, 11);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (12, '���Ź���', '', '/sys/department.do?method=showFrame', 20, 2, 12);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (14, '��ɫ����', '', '/sys/role.do?method=showList', 15, 2, 14);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (15, 'ģ�����', '', '/sys/application.do?method=showFrame', 12, 2, 15);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (16, '�ֵ����', '', '/sys/dictory.do?method=showFrame', 15, 2, 16);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (20, '��������', '', '', 15, 2, 20);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (21, '�޸�����', '', '/sys/user.do?method=prepareModifyPwd', 15, 2, 21);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (22, '�޸ĸ�����Ϣ', '', '/sys/user.do?method=prepareModifyInfo', 15, 2, 22);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (26, '��������', '', '/sys/dictory.do?method=loadDictory', 10, 2, 26);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (34, '���̹���', '����������', '', 0, 1, 34);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (36, '���̼��', '���̼��', '/mx/jbpm/tree', 36, 1, 438);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (435, '���̷���', '', '/mx/jbpm/deploy', 435, 1, 435);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (437, '������ʾ', '', '/workflow/test/index.jsp', 437, 1, 437);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (449, '��������', '��������', '/others/workCalendar.do?method=showList', 449, 1, 449);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (482, '��Ȩ����', '', '/sys/sysUserRole.do?method=showUsers', 482, 2, 482);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (494, 'TODO����', '', '/sys/todo.do?method=showList', 494, 1, 494);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (495, '���ȹ���', '', '/sys/scheduler.do?method=showList', 495, 1, 495);
insert into sys_application (id, name, appDesc, url, sort, showMenu, nodeId) values (42, 'ͳ�Ʊ���', 'ͳ�Ʊ���', '', 42, 1, 42);
insert into sys_application (id, name, appDesc, url, sort, showMenu, nodeId) values (43, '��ѯ����', '', '/mx/dts/query', 43, 1, 43);
insert into sys_application (id, name, appDesc, url, sort, showMenu, nodeId) values (44, '�����', '�����', '/mx/dts/table', 44, 1, 44);
insert into sys_application (id, name, appDesc, url, sort, showMenu, nodeId) values (45, 'ͼ�����', 'ͼ�����', '/mx/bi/chart', 45, 1, 45);
insert into sys_application (id, name, appDesc, url, sort, showMenu, nodeId) values (46, '�������', '�������', '/mx/bi/report', 46, 1, 46);
insert into sys_application (id, name, appDesc, url, sort, showMenu, nodeId) values (47, '�����͹���', '�����͹���', '/mx/bi/reportTask', 47, 1, 47);
insert into sys_application (id, name, appDesc, url, sort, showMenu, nodeId) values (48, '���ȹ���', '���ȹ���', '/mx/dts/scheduler', 48, 1, 48);
insert into sys_application (id, name, appDesc, url, sort, showMenu, nodeId) values (49, '�������', '�������', '/mx/dts/task', 49, 1, 49);


/*���벿�Ž�ɫ*/ 
insert into sys_dept_role (id, grade, code, sort, sysroleid, deptid) values (1, 0, null, 0, 15, 6);


/*�����û���ɫ*/
insert into sys_user_role (id, userid, roleid, authorized, authorizefrom, availdatestart, availdateend, processdescription) values (2, 1, 1, 1, 1, null, null, null);
insert into sys_user_role (id, userid, roleid, authorized, authorizefrom, availdatestart, availdateend, processdescription) values (4, 2, 1, 2, 2, null, null, null);

   
 
/*�������Ȩ��*/
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

/*����ϵͳ����*/
insert into sys_function (id, appId, name, funcDesc, funcMethod, sort) values (1, 15, 'ģ���б�', null, 'com.glaf.base.modules.sys.action.SysApplicationAction.showList', 5);
insert into sys_function (id, appId, name, funcDesc, funcMethod, sort) values (2, 15, '����ģ��', null, 'com.glaf.base.modules.sys.action.SysApplicationAction.prepareAdd', 3);
insert into sys_function (id, appId, name, funcDesc, funcMethod, sort) values (3, 15, '�޸�ģ��', null, 'com.glaf.base.modules.sys.action.SysApplicationAction.prepareModify', 2);
insert into sys_function (id, appId, name, funcDesc, funcMethod, sort) values (4, 15, 'ɾ��ģ��', null, 'com.glaf.base.modules.sys.action.SysApplicationAction.batchDelete', 1);

insert into sys_function (id, appId, name, funcDesc, funcMethod, sort) values (11, 15, 'ģ���б�', null, 'com.glaf.base.modules.sys.springmvc.SysApplicationController.showList', 5);
insert into sys_function (id, appId, name, funcDesc, funcMethod, sort) values (12, 15, '����ģ��', null, 'com.glaf.base.modules.sys.springmvc.SysApplicationController.prepareAdd', 3);
insert into sys_function (id, appId, name, funcDesc, funcMethod, sort) values (13, 15, '�޸�ģ��', null, 'com.glaf.base.modules.sys.springmvc.SysApplicationController.prepareModify', 2);
insert into sys_function (id, appId, name, funcDesc, funcMethod, sort) values (14, 15, 'ɾ��ģ��', null, 'com.glaf.base.modules.sys.springmvc.SysApplicationController.batchDelete', 1);


insert into  sys_permission (roleid, funcid) values (1, 1);
insert into  sys_permission (roleid, funcid) values (1, 2);
insert into  sys_permission (roleid, funcid) values (1, 3);
insert into  sys_permission (roleid, funcid) values (1, 4);


insert into sys_dictory (id, typeId, code, name, sort, dictDesc, blocked, ext1, ext2, ext3, ext4, ext5, ext6) values (1, 498, 'RMB', '�����', 1, null, 0, '', '', null, null, null, null);
insert into sys_dictory (id, typeId, code, name, sort, dictDesc, blocked, ext1, ext2, ext3, ext4, ext5, ext6) values (2, 498, 'USD', '��Ԫ', 2, null, 0, '', '', null, null, null, null);
insert into sys_dictory (id, typeId, code, name, sort, dictDesc, blocked, ext1, ext2, ext3, ext4, ext5, ext6) values (3, 498, 'JPY', '��Ԫ', 3, null, 0, '', '', null, null, null, null);


insert into sys_dbid (NAME_, VALUE_, VERSION_) values ('next.dbid', '10001', 1);