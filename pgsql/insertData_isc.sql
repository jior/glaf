--测试demo数据
INSERT INTO t_iscsample(
            sysno, name, sex, age, city, deadyear, money)
    VALUES ('6', 'test', '1', '12', '028', '2012', 0);
    
--权限表
INSERT INTO t_privilege(
            f_privilegeid, f_privilegename, f_privilegetype, f_url)
VALUES ('fun001','gqlogin'              ,'0','/sys/authorize.do'               );
INSERT INTO t_privilege(
            f_privilegeid, f_privilegename, f_privilegetype, f_url)
VALUES ('fun020','旧版DEMO'             ,'2','/glaf/demo/demoJsp/demoIndex.jsp');
INSERT INTO t_privilege(
            f_privilegeid, f_privilegename, f_privilegetype, f_url)
VALUES ('fun021','旧版增删查改例子'     ,'2','../../baseSample.do'             );
INSERT INTO t_privilege(
            f_privilegeid, f_privilegename, f_privilegetype, f_url)
VALUES ('fun022','分页检索grid'         ,'2','../../arrayGrid.do'              );
INSERT INTO t_privilege(
            f_privilegeid, f_privilegename, f_privilegetype, f_url)
VALUES ('fun023','更新grid'             ,'2','../../cellEditing.do'            );
INSERT INTO t_privilege(
            f_privilegeid, f_privilegename, f_privilegetype, f_url)
VALUES ('fun024','csv测试'              ,'2','/glaf/demo/demoJsp/CsvTest.jsp'  );
INSERT INTO t_privilege(
            f_privilegeid, f_privilegename, f_privilegetype, f_url)
VALUES ('fun025','xls导入和导出&PDF生成','2','/glaf/demo/demoJsp/xlsDo.jsp'    );
INSERT INTO t_privilege(
            f_privilegeid, f_privilegename, f_privilegetype, f_url)
VALUES ('fun026','报表示例'             ,'2','../../reportShow.do'             );
INSERT INTO t_privilege(
            f_privilegeid, f_privilegename, f_privilegetype, f_url)
VALUES ('fun034','选项2'                ,'3','/glaf/sys/sysJsp/main.jsp'       );
INSERT INTO t_privilege(
            f_privilegeid, f_privilegename, f_privilegetype, f_url)
VALUES ('fun041','用户管理'             ,'4','../../userManage.do'             );
INSERT INTO t_privilege(
            f_privilegeid, f_privilegename, f_privilegetype, f_url)
VALUES ('fun042','batch管理'            ,'4','../../manageBatch.do'            );
INSERT INTO t_privilege(
            f_privilegeid, f_privilegename, f_privilegetype, f_url)
VALUES ('fun043','查询batch执行日志'    ,'4','../../showBatchExeLog.do'        );
INSERT INTO t_privilege(
            f_privilegeid, f_privilegename, f_privilegetype, f_url)
VALUES ('fun044','系统字典管理'         ,'4','../../showSysDictionary.do'      );
INSERT INTO t_privilege(
            f_privilegeid, f_privilegename, f_privilegetype, f_url)
VALUES ('fun045','统计查询'             ,'2','../../BPF0107-02.do'             );
INSERT INTO t_privilege(
            f_privilegeid, f_privilegename, f_privilegetype, f_url)
VALUES ('fun046','上传下载'             ,'2','../../uploadDown.do'             );

--角色
INSERT INTO t_role(
            f_roleid, f_rolename, f_remark)
    VALUES (1, '系统管理员', '系统管理员');
    
--角色权限表
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

--用户表
INSERT INTO t_users(
            f_userid, f_name, f_password, f_email, f_status, f_creatuserid, f_creatdate, f_updateuserid, f_updatedate, f_gysbm, f_gysgq)
    VALUES ('admin', '系统管理员', '1234567', '', '1', null, null, 'admin', current_timestamp, null, null);
    
--用户角色关系表
INSERT INTO t_users_role(
            f_userid, f_roleid, f_creatuserid, f_creatdate, f_updateuserid, f_updatedate)
    VALUES ('admin', 1, 'admin', current_timestamp, null, null);

