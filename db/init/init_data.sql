/** �������
delete from sys_access;
delete from sys_function;
delete from sys_user;
delete from sys_user_role;
delete from sys_dept_role;
delete from sys_department;
delete from sys_application;
delete from sys_tree;
delete from sys_role;
delete from sys_dictory;
**/



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
insert into sys_role (id, name, roleDesc, sort, code) values (15, 'ϵͳ����Ա', '', 1, 'SystemAdministrator');
insert into sys_role (id, name, roleDesc, sort, code) values (18, '����', '', 18, 'R017');


/*��ʼϵͳ����Ϣ*/
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (1, 0, '/', '��Ŀ¼', 1, '0');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (2, 1, '���ݽṹ', '', 20, '01');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (3, 1, 'Ӧ��ģ��', '', 10, '02');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (4, 2, '��������', '', 50, '011');
insert into sys_tree (id, parent, name, nodedesc, sort, code, discriminator) values (5, 2, '���Žṹ', '���Žṹ', 40, '012','D');
insert into sys_tree (id, parent, name, nodedesc, sort, code, discriminator) values (6, 5, '������', '������', 284, 'JS000','D');
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
insert into sys_tree (id, parent, name, nodeDesc, sort, code) values (50, 42, '��������', '��������', 50, '');
insert into sys_tree (id, parent, name, nodeDesc, sort, code) values (51, 42, 'ģ�����', 'ģ�����', 51, 'template');


insert into sys_tree (id, parent, name, nodeDesc, sort, code) values (53, 3, 'Activiti���̹���', 'Activiti���̹���', 53, '');
insert into sys_tree (id, parent, name, nodeDesc, sort, code) values (54, 53, '�������̰�', '�������̰�', 54, '');
insert into sys_tree (id, parent, name, nodeDesc, sort, code) values (55, 53, '���̶���', '���̶���', 55, '');
insert into sys_tree (id, parent, name, nodeDesc, sort, code) values (56, 53, '����ʵ��', '����ʵ��', 56, '');
insert into sys_tree (id, parent, name, nodeDesc, sort, code) values (57, 53, '��ʷ����ʵ��', '��ʷ����ʵ��', 57, '');
insert into sys_tree (id, parent, name, nodeDesc, sort, code) values (58, 53, '�����б�', '�����б�', 58, '');
insert into sys_tree (id, parent, name, nodeDesc, sort, code) values (59, 53, '������ʾ', '������ʾ', 59, '');

insert into sys_tree (id, parent, name, nodedesc, sort, code) values (190, 163, '�޸�����', '�޸�����', 188, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (231, 229, '��������', '��������', 230, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (235, 231, '�޸�����', '�޸�����', 235, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (236, 231, '�޸�����', '�޸�����', 236, '');

insert into sys_tree (id, parent, name, nodedesc, sort, code) values (308, 51, '��ӡģ��', '��ӡģ��', 0, 'print_tpl');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (312, 51, '�ʼ�ģ��', '�ʼ�ģ��', 0, 'mail_tpl');

insert into sys_tree (id, parent, name, nodedesc, sort, code) values (34, 3, '���̹���', '���̹���', 34, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (435, 34, '���̷���', '���̷���', 435, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (437, 34, '������ʾ', '������ʾ', 437, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (438, 34, '���̼��', '���̼��', 438, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (449, 8, '��������', '��������', 449, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (450, 8, 'ϵͳ����', 'ϵͳ����', 450, '');
insert into sys_tree (id, parent, name, nodeDesc, sort, code) values (452, 8, '�ʼ���������', '�ʼ���������', 452, '');

insert into sys_tree (id, parent, name, nodedesc, sort, code) values (494, 8, 'TODO����', 'TODO����', 494, '');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (495, 8, '���ȹ���', '���ȹ���', 495, '');
insert into sys_tree (id, parent, name, nodeDesc, sort, code) values (498, 4, '��������', '��������', 498, 'money');

insert into sys_tree (id, parent, name, nodedesc, sort, code) values (512, 10, '��Ȩ����', '��Ȩ����', 512, '');



/*��ʼ��������Ϣ*/
insert into sys_department (id, name, deptdesc, createtime, sort, deptno, code, code2, status, fincode, nodeid) values (6, '������', '������', null, 284, 'JS000', 'JS', 'J', 0, 'JS000', 6);
 
/*��ʼ���û���Ϣ*/
insert into sys_user (id, deptId, account, password, code, name, blocked, createTime, lastLoginTime, lastLoginIP, mobile, email, telephone, evection, gender, headship, userType, fax, accountType, dumpFlag, adminFlag) values (1, 6, 'root', 'lueSGJZetyySpUndWjMB', 'root', 'root', 0, null, null, '127.0.0.1', '111', 'root@127.0.0.1', '111', 0, 0, '����Ա', 40, null, 0, 0, '1');

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
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (21, '�޸�����', '', '/identity/user.do?method=prepareModifyPwd', 15, 2, 21);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (22, '�޸ĸ�����Ϣ', '', '/identity/user.do?method=prepareModifyInfo', 15, 2, 22);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (26, '��������', '', '/sys/dictory.do?method=loadDictory', 10, 2, 26);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (34, '���̹���', '����������', '', 0, 1, 34);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (36, '���̼��', '���̼��', '/mx/jbpm/tree', 36, 1, 438);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (435, '���̷���', '', '/mx/jbpm/deploy', 435, 1, 435);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (437, '������ʾ', '', '/workflow/test/index.jsp', 437, 1, 437);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (449, '��������', '��������', '/sys/workCalendar.do?method=showList', 449, 1, 449);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (450, 'ϵͳ����', 'ϵͳ����', '/mx/system/property/edit?category=SYS', 450, 1, 450);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (452, '�ʼ���������', '�ʼ���������', '/mx/system/mailConfig', 452, 1, 452);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (512, '��Ȩ����', '', '/sys/sysUserRole.do?method=showUsers', 512, 2, 512);
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
insert into sys_application (id, name, appDesc, url, sort, showMenu, nodeId) values (50, '��������', '��������', '/mx/system/property/edit?category=RPT', 50, 1, 50);
insert into sys_application (id, name, appDesc, url, sort, showMenu, nodeId) values (51, 'ģ�����', 'ģ�����', '/mx/system/template/showFrame', 51, 1, 51);

insert into sys_application (id, name, appDesc, url, sort, showMenu, nodeId) values (53, 'Activiti���̹���', 'Activiti���̹���', '', 53, 1, 53);
insert into sys_application (id, name, appDesc, url, sort, showMenu, nodeId) values (54, '�������̰�', '', '/mx/activiti/deploy', 54, 1, 54);
insert into sys_application (id, name, appDesc, url, sort, showMenu, nodeId) values (55, '���̶���', '���̶���', '/mx/activiti/process/processDefinitions', 55, 1, 55);
insert into sys_application (id, name, appDesc, url, sort, showMenu, nodeId) values (56, '����ʵ��', '����ʵ��', '/mx/activiti/process/processInstances', 56, 1, 56);
insert into sys_application (id, name, appDesc, url, sort, showMenu, nodeId) values (57, '��ʷ����ʵ��', '��ʷ����ʵ��', '/mx/activiti/history/historyProcessInstances', 57, 1, 57);
insert into sys_application (id, name, appDesc, url, sort, showMenu, nodeId) values (58, '�����б�', '�����б�', '/mx/activiti/task/taskList', 58, 1, 58);
insert into sys_application (id, name, appDesc, url, sort, showMenu, nodeId) values (59, '������ʾ', '������ʾ', '/workflow/activiti/index.jsp', 59, 1, 59);

/*���벿�Ž�ɫ*/ 
insert into sys_dept_role (id, grade, code, sort, sysroleid, deptid) values (1, 0, null, 0, 15, 6);


/*�����û���ɫ*/
insert into sys_user_role (id, userid, roleid, authorized, authorizefrom, availdatestart, availdateend, processdescription) values (2, 1, 1, 1, 1, null, null, null);
 
   
 
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
insert into sys_access (roleid, appid) values (1, 512); 
insert into sys_access (roleid, appid) values (1, 494); 
insert into sys_access (roleid, appid) values (1, 495); 

/*����ϵͳ����*/

insert into sys_function (id, appId, name, funcDesc, funcMethod, sort) values (11, 15, 'ģ���б�', null, 'com.glaf.base.modules.sys.springmvc.SysApplicationController.showList', 5);
insert into sys_function (id, appId, name, funcDesc, funcMethod, sort) values (12, 15, '����ģ��', null, 'com.glaf.base.modules.sys.springmvc.SysApplicationController.prepareAdd', 3);
insert into sys_function (id, appId, name, funcDesc, funcMethod, sort) values (13, 15, '�޸�ģ��', null, 'com.glaf.base.modules.sys.springmvc.SysApplicationController.prepareModify', 2);
insert into sys_function (id, appId, name, funcDesc, funcMethod, sort) values (14, 15, 'ɾ��ģ��', null, 'com.glaf.base.modules.sys.springmvc.SysApplicationController.batchDelete', 1);


insert into sys_dictory (id, typeId, code, name, sort, dictDesc, blocked, ext1, ext2, ext3, ext4, ext5, ext6) values (1, 498, 'RMB', '�����', 1, null, 0, '', '', null, null, null, null);
insert into sys_dictory (id, typeId, code, name, sort, dictDesc, blocked, ext1, ext2, ext3, ext4, ext5, ext6) values (2, 498, 'USD', '��Ԫ', 2, null, 0, '', '', null, null, null, null);
insert into sys_dictory (id, typeId, code, name, sort, dictDesc, blocked, ext1, ext2, ext3, ext4, ext5, ext6) values (3, 498, 'JPY', '��Ԫ', 3, null, 0, '', '', null, null, null, null);


insert into sys_dictory_def (id, nodeid, name, columnname, title, type, length, sort, required, target) values (3,  0, 'value', 'VALUE_', '', 'String', 2000, 2, 0, 'sys_dictory');
insert into sys_dictory_def (id, nodeid, name, columnname, title, type, length, sort, required, target) values (4,  0, 'ext1',  'EXT1', '',  'String', 200, 3, 0, 'sys_dictory');
insert into sys_dictory_def (id, nodeid, name, columnname, title, type, length, sort, required, target) values (5,  0, 'ext2',  'EXT2', '',  'String', 200, 4, 0, 'sys_dictory');
insert into sys_dictory_def (id, nodeid, name, columnname, title, type, length, sort, required, target) values (6,  0, 'ext3',  'EXT3', '',  'String', 200, 5, 0, 'sys_dictory');
insert into sys_dictory_def (id, nodeid, name, columnname, title, type, length, sort, required, target) values (7,  0, 'ext4',  'EXT4', '',  'String', 200, 5, 0, 'sys_dictory');
insert into sys_dictory_def (id, nodeid, name, columnname, title, type, length, sort, required, target) values (8,  0, 'ext5',  'EXT5', '',  'Date', 20, 7, 0, 'sys_dictory');
insert into sys_dictory_def (id, nodeid, name, columnname, title, type, length, sort, required, target) values (9,  0, 'ext6',  'EXT6', '',  'Date', 20, 8, 0, 'sys_dictory');
insert into sys_dictory_def (id, nodeid, name, columnname, title, type, length, sort, required, target) values (10, 0, 'ext7',  'EXT7', '',  'Date', 20, 9, 0, 'sys_dictory');
insert into sys_dictory_def (id, nodeid, name, columnname, title, type, length, sort, required, target) values (11, 0, 'ext8',  'EXT8', '',  'Date', 20, 10, 0, 'sys_dictory');
insert into sys_dictory_def (id, nodeid, name, columnname, title, type, length, sort, required, target) values (12, 0, 'ext9',  'EXT9', '',  'Date', 20, 11, 0, 'sys_dictory');
insert into sys_dictory_def (id, nodeid, name, columnname, title, type, length, sort, required, target) values (13, 0, 'ext10', 'EXT10', '', 'Date', 20, 12, 0, 'sys_dictory');
insert into sys_dictory_def (id, nodeid, name, columnname, title, type, length, sort, required, target) values (14, 0, 'ext11', 'EXT11', '', 'Long', 20, 13, 0, 'sys_dictory');
insert into sys_dictory_def (id, nodeid, name, columnname, title, type, length, sort, required, target) values (15, 0, 'ext12', 'EXT12', '', 'Long', 20, 14, 0, 'sys_dictory');
insert into sys_dictory_def (id, nodeid, name, columnname, title, type, length, sort, required, target) values (16, 0, 'ext13', 'EXT13', '', 'Long', 20, 15, 0, 'sys_dictory');
insert into sys_dictory_def (id, nodeid, name, columnname, title, type, length, sort, required, target) values (17, 0, 'ext14', 'EXT14', '', 'Long', 20, 16, 0, 'sys_dictory');
insert into sys_dictory_def (id, nodeid, name, columnname, title, type, length, sort, required, target) values (18, 0, 'ext15', 'EXT15', '', 'Long', 20, 17, 0, 'sys_dictory');
insert into sys_dictory_def (id, nodeid, name, columnname, title, type, length, sort, required, target) values (19, 0, 'ext16', 'EXT16', '', 'Double', 20, 18, 0, 'sys_dictory');
insert into sys_dictory_def (id, nodeid, name, columnname, title, type, length, sort, required, target) values (20, 0, 'ext17', 'EXT17', '', 'Double', 20, 19, 0, 'sys_dictory');
insert into sys_dictory_def (id, nodeid, name, columnname, title, type, length, sort, required, target) values (21, 0, 'ext18', 'EXT18', '', 'Double', 20, 20, 0, 'sys_dictory');
insert into sys_dictory_def (id, nodeid, name, columnname, title, type, length, sort, required, target) values (22, 0, 'ext19', 'EXT19', '', 'Double', 20, 21, 0, 'sys_dictory');
insert into sys_dictory_def (id, nodeid, name, columnname, title, type, length, sort, required, target) values (23, 0, 'ext20', 'EXT20', '', 'Double', 20, 22, 0, 'sys_dictory');

insert into SYS_DBID(NAME_, TITLE_, VALUE_, VERSION_) values ('next.dbid', 'ϵͳ��������', '1001', 1);

insert into sys_property (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('1', 'ϵͳ����', 0, 'res_system_name', 'ϵͳ����', null, 'GLAF����Ӧ�ÿ��', 'SYS', null);
insert into sys_property (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('2', 'ϵͳ�汾', 0, 'res_version', 'ϵͳ�汾', null, 'V3.0', 'SYS', null);
insert into sys_property (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('3', '��Ȩ��Ϣ', 0, 'res_copyright', '��Ȩ��Ϣ', null, 'GLAF ��Ȩ����', 'SYS', null);
insert into sys_property (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('4', null, 0, 'res_mail_from', '�ʼ�������', null, 'jior2008@gmail.com', 'SYS', null);

insert into sys_property (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('101', '�����6λ���¸�ʽ��YYYYMM������201312', 0, 'curr_yyyymm', '��ǰ����', null, '', 'RPT', '${curr_yyyymm}');
insert into sys_property (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('102', '�����8λ���ڸ�ʽ��YYYYMMDD������20130630', 0, 'curr_yyyymmdd', '��ǰ����', null, '', 'RPT', '${curr_yyyymmdd}');
insert into sys_property (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('103', '�����ļ�·��', 0, 'dataDir', '�����ļ�·��', null, '', 'RPT', null);
insert into sys_property (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('104', '�����������£�${curr_yyyymm}��ʾ��ǰ�·�', 0, 'input_yyyymm', '������������', null, '', 'RPT', '');
insert into sys_property (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('105', '������������ڣ�${curr_yyyymmdd}-1��ʾ��ǰ���ڵ�ǰһ��', 0, 'input_yyyymmdd', '������������', null, '', 'RPT', '');
insert into sys_property (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('106', '�������ɸ�Ŀ¼', 0, 'report_save_path', '�������ɸ�Ŀ¼', null, '', 'RPT', null);

update sys_application set locked = 0 where locked is null;

update sys_tree set locked = 0 where locked is null;

