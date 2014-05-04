/** �������

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

/**ϵͳ��������**/
insert into SYS_DBID(name_, title_, value_, version_) values ('next.dbid', 'ϵͳ��������', '10001', 1);


/*��ʼ����ɫ��Ϣ*/

insert into SYS_ROLE (id, name, roleDesc, sort, code) values (1, 'ϵͳ����Ա', '', 1000, 'SystemAdministrator');
insert into SYS_ROLE (id, name, roleDesc, sort, code) values (16, '�ּ�����Ա', '', 2, 'BranchAdmin');
insert into SYS_ROLE (id, name, roleDesc, sort, code) values (17, '���̹���Ա', '', 1, 'ProcessManager');
insert into SYS_ROLE (id, name, roleDesc, sort, code) values (6,  '���Ź���Ա', '', 3, 'R006');



/*��ʼϵͳ����Ϣ*/
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (1, 0, '/', '��Ŀ¼', 1000, '0');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (2, 1, '���ݽṹ', '', 999, '01');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (3, 1, 'Ӧ��ģ��', '', 998, '02');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (4, 2, '��������', '', 997, '011');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code, discriminator) values (5, 2, '���Žṹ', '���Žṹ', 996, '012','D');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code, discriminator) values (6, 5, '������', '������', 995, 'JS000','D');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (8, 3, 'ϵͳ����', 'ϵͳ����', 994, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (10, 3, '��ȫ����', '��ȫ����', 993, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (11, 8, 'ϵͳĿ¼', 'ϵͳĿ¼', 992, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (12, 10, '���Ź���', '���Ź���', 991, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (14, 10, '��ɫ����', '��ɫ����', 990, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (15, 10, 'ģ�����', 'ģ�����', 989, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (16, 8, '�ֵ����', '�ֵ����', 988, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (17, 8, '�������', '�������', 987, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (20, 3, '��������', '��������', 986, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (21, 20, '�޸�����', '�޸�����', 985, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (22, 20, '�޸ĸ�����Ϣ', '�޸ĸ�����Ϣ', 984, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (26, 8, '��������', '��������', 983, '');

insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (42, 3, 'ͳ�Ʊ���', 'ͳ�Ʊ���', 890, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (43, 42, '��ѯ����', '��ѯ����', 889, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (44, 42, '�����', '�����', 888, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (45, 42, 'ͼ�����', 'ͼ�����', 887, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (46, 42, '�������', '�������', 886, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (47, 42, '�����͹���', '�����͹���', 885, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (48, 42, '���ȹ���', '���ȹ���', 884, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (49, 42, '�������', '�������', 883, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (50, 42, '��������', '��������', 882, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (51, 42, 'ģ�����', 'ģ�����', 881, 'template');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (52, 42, 'ץȡ����', 'ץȡ����', 880, '');


insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (53, 3, 'Activiti���̹���', 'Activiti���̹���', 590, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (54, 53, '�������̰�', '�������̰�', 589, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (55, 53, '���̶���', '���̶���', 588, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (56, 53, '����ʵ��', '����ʵ��', 587, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (57, 53, '��ʷ����ʵ��', '��ʷ����ʵ��', 586, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (58, 53, '�����б�', '�����б�', 585, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (59, 53, '������ʾ', '������ʾ', 584, '');

insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (308, 51, '��ӡģ��', '��ӡģ��', 0, 'print_tpl');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (312, 51, '�ʼ�ģ��', '�ʼ�ģ��', 0, 'mail_tpl');

insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (34, 3, '���̹���', '���̹���', 34, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (435, 34, '���̷���', '���̷���', 435, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (437, 34, '������ʾ', '������ʾ', 437, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (438, 34, '���̼��', '���̼��', 438, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (449, 8, '��������', '��������', 449, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (450, 8, 'ϵͳ����', 'ϵͳ����', 450, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (452, 8, '�ʼ���������', '�ʼ���������', 452, '');

insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (494, 8, 'TODO����', 'TODO����', 494, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (495, 8, '���ȹ���', '���ȹ���', 495, '');
insert into SYS_TREE (id, parent, name, nodeDesc, sort, code) values (498, 4, '��������', '��������', 498, 'money');

insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (512, 10, '��Ȩ����', '��Ȩ����', 512, '');


insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (701, 3, '���ݹ���', '���ݹ���', 0, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (702, 701, '���Ź���', '���Ź���', 0, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (703, 701, '�������', '�������', 0, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (720, 1, '���ݹ������', '', 0, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (721, 720, '����', '����', 0, 'news');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (722, 721, '��˾����', '��˾����', 0, 'news_01');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (723, 721, 'ҵ������', 'ҵ������', 0, 'news_02');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (731, 720, '����', '����', 0, 'bulletin');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (732, 731, '��ͨ', '', 0, '');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (733, 731, '��Ҫ', '', 0, '');


insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (750, 1, '�������', '', 0, 'report_category');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (751, 750, '�з�', '�з�', 0, 'report_01');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (752, 750, '����', '����', 0, 'report_02');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (753, 750, '����', '����', 0, 'report_03');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (754, 750, '����', '����', 0, 'report_04');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (755, 750, '����', '����', 0, 'report_05');
insert into SYS_TREE (id, parent, name, nodedesc, sort, code) values (756, 750, '����', '����', 0, 'report_06');



/*��ʼ��������Ϣ*/
insert into SYS_DEPARTMENT (id, name, deptdesc, createtime, sort, deptno, code, code2, status, fincode, nodeid) values (1, 'ϵͳ����', 'ϵͳ����', null, 284, 'SYS000', 'SYS', 'S', 0, 'SYS000', 10000);
 
/*��ʼ���û���Ϣ*/
insert into SYS_USER (id, deptId, account, password, code, name, blocked, createTime, lastLoginTime, lastLoginIP, mobile, email, telephone, evection, gender, headship, userType, fax, accountType, dumpFlag, adminFlag) values (1, 6, 'root', 'lueSGJZetyySpUndWjMB', 'root', 'root', 0, null, null, '127.0.0.1', '111', 'root@127.0.0.1', '111', 0, 0, '����Ա', 40, null, 0, 0, '1');

/*��ʼ��Ӧ����Ϣ*/
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (3, 'Ӧ��ģ��', 'Ӧ��ģ��', '', 1001, 1, 3);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (8, 'ϵͳ����', 'ϵͳ����', '', 999, 1, 8);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (9, '��������', '��������', '', 998, 1, 4);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (10, '��ȫ����', '', '', 997, 2, 10);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (11, 'ϵͳĿ¼', '', '/sys/tree.do?method=showMain', 996, 2, 11);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (12, '���Ź���', '', '/sys/department.do?method=showFrame', 995, 2, 12);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (14, '��ɫ����', '', '/sys/role.do?method=showList', 994, 2, 14);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (15, 'ģ�����', '', '/sys/application.do?method=showFrame', 993, 2, 15);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (16, '�ֵ����', '', '/sys/dictory.do?method=showFrame', 992, 2, 16);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (17, '�������', '', '/mx/sys/district', 991, 2, 17);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (20, '��������', '', '', 1000, 2, 20);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (21, '�޸�����', '', '/identity/user.do?method=prepareModifyPwd', 980, 2, 21);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (22, '�޸ĸ�����Ϣ', '', '/identity/user.do?method=prepareModifyInfo', 979, 2, 22);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (26, '��������', '', '/sys/dictory.do?method=loadDictory', 976, 2, 26);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (34, '���̹���', '����������', '', 0, 1, 34);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (36, '���̼��', '���̼��', '/mx/jbpm/tree', 36, 1, 438);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (435, '���̷���', '', '/mx/jbpm/deploy', 435, 1, 435);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (437, '������ʾ', '', '/workflow/test/index.jsp', 437, 1, 437);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (449, '��������', '��������', '/sys/workCalendar.do?method=showList', 449, 1, 449);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (450, 'ϵͳ����', 'ϵͳ����', '/mx/sys/property/edit?category=SYS', 450, 1, 450);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (452, '�ʼ���������', '�ʼ���������', '/mx/sys/mailConfig', 452, 1, 452);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (512, '��Ȩ����', '', '/sys/sysUserRole.do?method=showUsers', 512, 2, 512);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (494, 'TODO����', '', '/sys/todo.do?method=showList', 494, 1, 494);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid) values (495, '���ȹ���', '', '/sys/scheduler.do?method=showList', 495, 1, 495);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (42, 'ͳ�Ʊ���', 'ͳ�Ʊ���', '', 42, 1, 42);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (43, '��ѯ����', '', '/mx/dts/query', 43, 1, 43);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (44, '�����', '�����', '/mx/dts/table', 44, 1, 44);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (45, 'ͼ�����', 'ͼ�����', '/mx/bi/chart', 45, 1, 45);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (46, '�������', '�������', '/mx/bi/report', 46, 1, 46);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (47, '�����͹���', '�����͹���', '/mx/bi/reportTask', 47, 1, 47);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (48, '���ȹ���', '���ȹ���', '/mx/dts/scheduler', 48, 1, 48);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (49, '�������', '�������', '/mx/dts/task', 49, 1, 49);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (50, '��������', '��������', '/mx/sys/property/edit?category=RPT', 50, 1, 50);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (51, 'ģ�����', 'ģ�����', '/mx/sys/template', 51, 1, 51);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (52, 'ץȡ����', 'ץȡ����', '/mx/dts/dataTransfer', 52, 1, 52);

insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (53, 'Activiti���̹���', 'Activiti���̹���', '', 53, 1, 53);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (54, '�������̰�', '', '/mx/activiti/deploy', 54, 1, 54);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (55, '���̶���', '���̶���', '/mx/activiti/process/processDefinitions', 55, 1, 55);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (56, '����ʵ��', '����ʵ��', '/mx/activiti/process/processInstances', 56, 1, 56);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (57, '��ʷ����ʵ��', '��ʷ����ʵ��', '/mx/activiti/history/historyProcessInstances', 57, 1, 57);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (58, '�����б�', '�����б�', '/mx/activiti/task/taskList', 58, 1, 58);
insert into SYS_APPLICATION (id, name, appDesc, url, sort, showMenu, nodeId) values (59, '������ʾ', '������ʾ', '/workflow/activiti/index.jsp', 59, 1, 59);

insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid, code) values (71, '���ݹ���', '', '', 1, 1, 701, null);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid, code) values (72, '���Ź���', '', '/mx/cms/info?serviceKey=news', 2, 1, 702, null);
insert into SYS_APPLICATION (id, name, appdesc, url, sort, showmenu, nodeid, code) values (73, '�������', '', '/mx/cms/info?serviceKey=bulletin', 3, 1, 703, null);

/*���벿�Ž�ɫ*/ 
insert into SYS_DEPT_ROLE (id, grade, code, sort, sysroleid, deptid) values (1, 0, null, 0, 1, 1);


/*�����û���ɫ*/
insert into SYS_USER_ROLE (id, userid, roleid, authorized, authorizefrom, availdatestart, availdateend, processdescription) values (2, 1, 1, 1, 1, null, null, null);
 
   
 
/*�������Ȩ��*/
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

/*����ϵͳ����*/

insert into SYS_FUNCTION (id, name, funcdesc, funcmethod, sort, appid, code) values (1, 'ģ���б�', 'ģ���б�', 'com.glaf.base.modules.sys.springmvc.SysApplicationController.showList', 1, 15, 'app:list');
insert into SYS_FUNCTION (id, name, funcdesc, funcmethod, sort, appid, code) values (2, '����ģ��', '����ģ��', 'com.glaf.base.modules.sys.springmvc.SysApplicationController.prepareAdd', 2, 15, 'app:create');
insert into SYS_FUNCTION (id, name, funcdesc, funcmethod, sort, appid, code) values (3, '�޸�ģ��', '�޸�ģ��', 'com.glaf.base.modules.sys.springmvc.SysApplicationController.prepareModify', 3, 15, 'app:update');
insert into SYS_FUNCTION (id, name, funcdesc, funcmethod, sort, appid, code) values (4, 'ɾ��ģ��', 'ɾ��ģ��', 'com.glaf.base.modules.sys.springmvc.SysApplicationController.batchDelete', 4, 15, 'app:delete');


insert into SYS_DICTORY (id, typeId, code, name, sort, dictDesc, blocked, ext1, ext2) values (1, 498, 'CNY', '�����', 1, null, 0, '', '');
insert into SYS_DICTORY (id, typeId, code, name, sort, dictDesc, blocked, ext1, ext2) values (2, 498, 'USD', '��Ԫ', 2, null, 0, '', '');
insert into SYS_DICTORY (id, typeId, code, name, sort, dictDesc, blocked, ext1, ext2) values (3, 498, 'JPY', '��Ԫ', 3, null, 0, '', '');
insert into SYS_DICTORY (id, typeId, code, name, sort, dictDesc, blocked, ext1, ext2) values (4, 498, 'HKD', '�۱�', 4, null, 0, '', '');
insert into SYS_DICTORY (id, typeId, code, name, sort, dictDesc, blocked, ext1, ext2) values (5, 498, 'EUR', 'ŷԪ', 5, null, 0, '', '');
 

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

insert into SYS_PROPERTY (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('1', 'ϵͳ����', 0, 'res_system_name', 'ϵͳ����', null, 'GLAF����Ӧ�ÿ��', 'SYS', null);
insert into SYS_PROPERTY (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('2', 'ϵͳ�汾', 0, 'res_version', 'ϵͳ�汾', null, 'V3.0', 'SYS', null);
insert into SYS_PROPERTY (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('3', '��Ȩ��Ϣ', 0, 'res_copyright', '��Ȩ��Ϣ', null, 'GLAF ��Ȩ����', 'SYS', null);
insert into SYS_PROPERTY (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('4', null, 0, 'res_mail_from', '�ʼ�������', null, 'jior2008@gmail.com', 'SYS', null);
insert into SYS_PROPERTY (id_, category_, description_, initvalue_, locked_, name_, title_, type_, value_, inputtype_) values ('5', 'SYS', '�޶�ÿ���˺�ֻ����һ����¼', '[{"name":"����","value":"true"},{"name":"������","value":"false"}]', 0, 'login_limit', 'ϵͳ��¼�˺�����', null, 'true', 'combobox');
insert into SYS_PROPERTY (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('6', '�����û����ʱ�䣨�룩', 0, 'login_time_check', '�����û����ʱ�䣨�룩', null, '600', 'SYS', null);
insert into SYS_PROPERTY (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('8', '�������ϵͳ��IP��ַ', 0, 'login.allow.ip', '�������ϵͳ��IP��ַ', null, '', 'SYS', null);
insert into SYS_PROPERTY (id_, category_, description_, initvalue_, locked_, name_, title_, type_, value_, inputtype_) values ('9', 'SYS', '�ļ��洢��ʽ', '[{"name":"���ݿ�","value":"DATABASE"},{"name":"Ӳ��","value":"DISK"}]', 0, 'fs_storage_strategy', '�ļ��洢��ʽ', null, 'DATABASE', 'combobox');
insert into sys_property (id_, category_, description_, initvalue_, locked_, name_, title_, type_, value_, inputtype_) values ('10', 'SYS', '�洢���ݵ�Mongodb', '[{"name":"��","value":"true"},{"name":"��","value":"false"}]', 0, 'fs_storage_mongodb', '�洢���ݵ�Mongodb', null, 'false', 'combobox');


insert into SYS_PROPERTY (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('101', '�����6λ���¸�ʽ��YYYYMM������201312', 0, 'curr_yyyymm', '��ǰ����', null, '', 'RPT', '${curr_yyyymm}');
insert into SYS_PROPERTY (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('102', '�����8λ���ڸ�ʽ��YYYYMMDD������20130630', 0, 'curr_yyyymmdd', '��ǰ����', null, '', 'RPT', '${curr_yyyymmdd}');
insert into SYS_PROPERTY (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('103', '�����ļ�·��', 0, 'dataDir', '�����ļ�·��', null, '', 'RPT', null);
insert into SYS_PROPERTY (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('104', '�����������£�${curr_yyyymm}��ʾ��ǰ�·�', 0, 'input_yyyymm', '������������', null, '', 'RPT', '');
insert into SYS_PROPERTY (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('105', '������������ڣ�${curr_yyyymmdd}-1��ʾ��ǰ���ڵ�ǰһ��', 0, 'input_yyyymmdd', '������������', null, '', 'RPT', '');
insert into SYS_PROPERTY (id_, description_, locked_, name_, title_, type_, value_, category_, initvalue_) values ('106', '�������ɸ�Ŀ¼', 0, 'report_save_path', '�������ɸ�Ŀ¼', null, '', 'RPT', null);

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

insert into SYS_PARAMS (id, business_key, date_val, double_val, int_val, java_type, key_name, long_val, service_key, string_val, text_val, title, type_cd) values ('news__news_processName', 'news', null, null, null, 'string', 'processName', null, 'news', 'PublicInfoProcess', 'PublicInfoProcess', '��������', 'news');
insert into SYS_PARAMS (id, business_key, date_val, double_val, int_val, java_type, key_name, long_val, service_key, string_val, text_val, title, type_cd) values ('bulletin__bulletin_processName', 'bulletin', null, null, null, 'string', 'processName', null, 'bulletin', 'PublicInfoProcess', 'PublicInfoProcess', '��������', 'bulletin');

insert into SYS_PARAMS (id, business_key, date_val, double_val, int_val, java_type, key_name, long_val, service_key, string_val, text_val, title, type_cd) values ('SYS_TABLE', 'SYS_TABLE', null, null, null, 'json', 'SYS_TABLE', null, 'SYS_TABLE', '[{"tablename":"SYS_ROLE","title":"��ɫ"},{"tablename":"SYS_TREE","title":"ϵͳ��"},{"tablename":"SYS_DEPARTMENT","title":"����"},{"tablename":"SYS_USER","title":"�û�"},{"tablename":"SYS_APPLICATION","title":"ģ��"},{"tablename":"SYS_DEPT_ROLE","title":"���Ž�ɫ"},{"tablename":"SYS_USER_ROLE","title":"�û���ɫ"},{"tablename":"SYS_ACCESS","title":"����Ȩ��"},{"tablename":"SYS_PERMISSION","title":"ϵͳȨ��"},{"tablename":"SYS_FUNCTION","title":"ϵͳ����"},{"tablename":"SYS_DICTORY","title":"�����ֵ�"},{"tablename":"SYS_DICTORY_DEF","title":"�ֵ䶨��"},{"tablename":"SYS_DBID","title":"ϵͳ����"},{"tablename":"SYS_PROPERTY","title":"ϵͳ����"},{"tablename":"SYS_WORKCALENDAR","title":"��������"},{"tablename":"SYS_PARAMS","title":"��������"},{"tablename":"SYS_INPUT_DEF","title":"ϵͳ���붨��"},{"tablename":"sys_membership","title":"��Ա��ϵ"},{"tablename":"sys_query","title":"��ѯ����"},{"tablename":"sys_calendar","title":"����"},{"tablename":"SYS_SCHEDULER","title":"ϵͳ����"},{"tablename":"SYS_SCHEDULER_params","title":"ϵͳ���Ȳ���"}]', '[{"tablename":"SYS_ROLE","title":"��ɫ"},{"tablename":"SYS_TREE","title":"ϵͳ��"},{"tablename":"SYS_DEPARTMENT","title":"����"},{"tablename":"SYS_USER","title":"�û�"},{"tablename":"SYS_APPLICATION","title":"ģ��"},{"tablename":"SYS_DEPT_ROLE","title":"���Ž�ɫ"},{"tablename":"SYS_USER_ROLE","title":"�û���ɫ"},{"tablename":"SYS_ACCESS","title":"����Ȩ��"},{"tablename":"SYS_PERMISSION","title":"ϵͳȨ��"},{"tablename":"SYS_FUNCTION","title":"ϵͳ����"},{"tablename":"SYS_DICTORY","title":"�����ֵ�"},{"tablename":"SYS_DICTORY_DEF","title":"�ֵ䶨��"},{"tablename":"SYS_DBID","title":"ϵͳ����"},{"tablename":"SYS_PROPERTY","title":"ϵͳ����"},{"tablename":"SYS_WORKCALENDAR","title":"��������"},{"tablename":"SYS_PARAMS","title":"��������"},{"tablename":"SYS_INPUT_DEF","title":"ϵͳ���붨��"},{"tablename":"sys_membership","title":"��Ա��ϵ"},{"tablename":"sys_query","title":"��ѯ����"},{"tablename":"sys_calendar","title":"����"},{"tablename":"SYS_SCHEDULER","title":"ϵͳ����"},{"tablename":"SYS_SCHEDULER_params","title":"ϵͳ���Ȳ���"}]', 'ϵͳ��', 'SYS_TABLE');


insert into SYS_INPUT_DEF (id, init_value, input_type, java_type, key_name, required, service_key, text_field, title, type_cd, type_title, url, valid_type, value_field) values ('7', '/rs/jbpm/definition/json', 'combobox', 'String', 'processName', null, 'news', 'text', '��������', 'news', '���ŷ���', '/rs/jbpm/definition/json', null, 'name');
insert into SYS_INPUT_DEF (id, init_value, input_type, java_type, key_name, required, service_key, text_field, title, type_cd, type_title, url, valid_type, value_field) values ('8', '/rs/jbpm/definition/json', 'combobox', 'String', 'processName', null, 'bulletin', 'text', '��������', 'bulletin', '���淢��', '/rs/jbpm/definition/json', null, 'name');


/*UI�����ʼ������*/
insert into UI_PANEL (id_,actorid_,close_,collapsible_,columnindex_,height_,link_,locked_,name_,resize_,title_,type_,width_) values ( '1010','root',0,0,0,250,'/user/todo.do?method=userTasks',0,'todo',0,'��������','L',0);
insert into UI_PANEL (id_,actorid_,close_,collapsible_,columnindex_,height_,link_,locked_,name_,resize_,title_,type_,width_) values ( '1016','root',0,0,0,250,'/mx/public/info/indexList?serviceKey=news',0,'news',0,'����','L',0);
insert into UI_PANEL (id_,actorid_,close_,collapsible_,columnindex_,height_,link_,locked_,name_,resize_,title_,type_,width_) values ( '1018','root',0,0,0,250,'/mx/public/info/indexList?serviceKey=bulletin',0,'bulletin',0,'����','L',0);
insert into UI_PANEL (id_,actorid_,close_,collapsible_,columnindex_,content_,height_,locked_,name_,resize_,title_,type_,width_) values ( '1020','root',0,0,0,'<p>&nbsp;</p><p>&nbsp;&nbsp;&nbsp;&nbsp;GLAF 1.0׼��������</p>',250,0,'notice',0,'֪ͨ','T',0);

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

