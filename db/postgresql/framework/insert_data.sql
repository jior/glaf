--����demo����
INSERT INTO t_iscsample(
            sysno, name, sex, age, city, deadyear, money)
    VALUES ('6', 'test', '1', '12', '028', '2012', 0);
    
--Ȩ�ޱ�
INSERT INTO t_privilege(
            f_privilegeid, f_privilegename, f_privilegetype, f_url)
VALUES ('fun001','gqlogin'              ,'0','/sys/authorize.do'               );
INSERT INTO t_privilege(
            f_privilegeid, f_privilegename, f_privilegetype, f_url)
VALUES ('fun020','�ɰ�DEMO'             ,'2','/glaf/demo/demoJsp/demoIndex.jsp');
INSERT INTO t_privilege(
            f_privilegeid, f_privilegename, f_privilegetype, f_url)
VALUES ('fun021','�ɰ���ɾ�������'     ,'2','../../baseSample.do'             );
INSERT INTO t_privilege(
            f_privilegeid, f_privilegename, f_privilegetype, f_url)
VALUES ('fun022','��ҳ����grid'         ,'2','../../arrayGrid.do'              );
INSERT INTO t_privilege(
            f_privilegeid, f_privilegename, f_privilegetype, f_url)
VALUES ('fun023','����grid'             ,'2','../../cellEditing.do'            );
INSERT INTO t_privilege(
            f_privilegeid, f_privilegename, f_privilegetype, f_url)
VALUES ('fun024','csv����'              ,'2','/glaf/demo/demoJsp/CsvTest.jsp'  );
INSERT INTO t_privilege(
            f_privilegeid, f_privilegename, f_privilegetype, f_url)
VALUES ('fun025','xls����͵���&PDF����','2','/glaf/demo/demoJsp/xlsDo.jsp'    );
INSERT INTO t_privilege(
            f_privilegeid, f_privilegename, f_privilegetype, f_url)
VALUES ('fun026','����ʾ��'             ,'2','../../reportShow.do'             );
INSERT INTO t_privilege(
            f_privilegeid, f_privilegename, f_privilegetype, f_url)
VALUES ('fun034','ѡ��2'                ,'3','/glaf/sys/sysJsp/main.jsp'       );
INSERT INTO t_privilege(
            f_privilegeid, f_privilegename, f_privilegetype, f_url)
VALUES ('fun041','�û�����'             ,'4','../../userManage.do'             );
INSERT INTO t_privilege(
            f_privilegeid, f_privilegename, f_privilegetype, f_url)
VALUES ('fun042','batch����'            ,'4','../../manageBatch.do'            );
INSERT INTO t_privilege(
            f_privilegeid, f_privilegename, f_privilegetype, f_url)
VALUES ('fun043','��ѯbatchִ����־'    ,'4','../../showBatchExeLog.do'        );
INSERT INTO t_privilege(
            f_privilegeid, f_privilegename, f_privilegetype, f_url)
VALUES ('fun044','ϵͳ�ֵ����'         ,'4','../../showSysDictionary.do'      );
INSERT INTO t_privilege(
            f_privilegeid, f_privilegename, f_privilegetype, f_url)
VALUES ('fun045','ͳ�Ʋ�ѯ'             ,'2','../../BPF0107-02.do'             );
INSERT INTO t_privilege(
            f_privilegeid, f_privilegename, f_privilegetype, f_url)
VALUES ('fun046','�ϴ�����'             ,'2','../../uploadDown.do'             );

--��ɫ
INSERT INTO t_role(
            f_roleid, f_rolename, f_remark)
    VALUES (1, 'ϵͳ����Ա', 'ϵͳ����Ա');
    
--��ɫȨ�ޱ�
INSERT INTO t_role_privilege(
            f_roleid, f_privilegeid, f_creatuserid, f_updateuserid, f_creatdate,  f_updatedate)
VALUES (1,'fun020','','',NULL,NULL);
INSERT INTO t_role_privilege(
            f_roleid, f_privilegeid, f_creatuserid, f_updateuserid, f_creatdate,  f_updatedate)
VALUES (1,'fun021','','',NULL,NULL);
INSERT INTO t_role_privilege(
            f_roleid, f_privilegeid, f_creatuserid, f_updateuserid, f_creatdate,  f_updatedate)
VALUES (1,'fun022','','',NULL,NULL);
INSERT INTO t_role_privilege(
            f_roleid, f_privilegeid, f_creatuserid, f_updateuserid, f_creatdate,  f_updatedate)
VALUES (1,'fun023','','',NULL,NULL);
INSERT INTO t_role_privilege(
            f_roleid, f_privilegeid, f_creatuserid, f_updateuserid, f_creatdate,  f_updatedate)
VALUES (1,'fun024','','',NULL,NULL);
INSERT INTO t_role_privilege(
            f_roleid, f_privilegeid, f_creatuserid, f_updateuserid, f_creatdate,  f_updatedate)
VALUES (1,'fun025','','',NULL,NULL);
INSERT INTO t_role_privilege(
            f_roleid, f_privilegeid, f_creatuserid, f_updateuserid, f_creatdate,  f_updatedate)
VALUES (1,'fun026','','',NULL,NULL);
INSERT INTO t_role_privilege(
            f_roleid, f_privilegeid, f_creatuserid, f_updateuserid, f_creatdate,  f_updatedate)
VALUES (1,'fun034','','',NULL,NULL);
INSERT INTO t_role_privilege(
            f_roleid, f_privilegeid, f_creatuserid, f_updateuserid, f_creatdate,  f_updatedate)
VALUES (1,'fun041','','',NULL,NULL);
INSERT INTO t_role_privilege(
            f_roleid, f_privilegeid, f_creatuserid, f_updateuserid, f_creatdate,  f_updatedate)
VALUES (1,'fun042','','',NULL,NULL);
INSERT INTO t_role_privilege(
            f_roleid, f_privilegeid, f_creatuserid, f_updateuserid, f_creatdate,  f_updatedate)
VALUES (1,'fun043','','',NULL,NULL);
INSERT INTO t_role_privilege(
            f_roleid, f_privilegeid, f_creatuserid, f_updateuserid, f_creatdate,  f_updatedate)
VALUES (1,'fun044','','',NULL,NULL);
INSERT INTO t_role_privilege(
            f_roleid, f_privilegeid, f_creatuserid, f_updateuserid, f_creatdate,  f_updatedate)
VALUES (1,'fun045','','',NULL,NULL);
INSERT INTO t_role_privilege(
            f_roleid, f_privilegeid, f_creatuserid, f_updateuserid, f_creatdate,  f_updatedate)
VALUES (1,'fun046','','',NULL,NULL);

--�û���
INSERT INTO t_users(
            f_userid, f_name, f_password, f_email, f_status, f_creatuserid, f_creatdate, f_updateuserid, f_updatedate, f_gysbm, f_gysgq)
    VALUES ('admin', 'ϵͳ����Ա', '1234567', '', '1', null, null, 'admin', current_timestamp, null, null);
    
--�û���ɫ��ϵ��
INSERT INTO t_users_role(
            f_userid, f_roleid, f_creatuserid, f_creatdate, f_updateuserid, f_updatedate)
    VALUES ('admin', 1, 'admin', current_timestamp, null, null);

