

CREATE TABLE sys_access(
        roleid bigint NOT NULL,
        appid bigint NOT NULL,
        PRIMARY KEY (roleid, appid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


CREATE TABLE sys_application(
        id bigint NOT NULL,
        name varchar(255),
	code varchar(255),
        appdesc varchar(255),
        url varchar(255),
        sort int,
        showmenu int,
        nodeid bigint NOT NULL,
	locked int default 0,
        PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


CREATE TABLE sys_department(
        id bigint NOT NULL,
        name varchar(255),
        deptdesc varchar(255),
        createtime timestamp,
        sort int,
        deptno varchar(255),
        code varchar(255),
        code2 varchar(255),
        status int,
        fincode varchar(255),
        nodeid bigint NOT NULL,
        PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

CREATE TABLE sys_dept_role(
        id bigint NOT NULL,
        grade int,
        code varchar(255),
        sort int,
        sysroleid bigint NOT NULL,
        deptid bigint NOT NULL,
        PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

CREATE TABLE sys_function(
        id bigint NOT NULL,
        name varchar(255),
	code varchar(50),
        funcdesc varchar(255),
        funcmethod varchar(255),
        sort int,
        appid bigint NOT NULL,
        PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

CREATE TABLE sys_log(
        id bigint NOT NULL,
        account varchar(255),
        ip varchar(255),
        createtime timestamp,
        operate varchar(255),
        flag int,
        PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

CREATE TABLE sys_permission (
        roleid bigint NOT NULL,
        funcid bigint NOT NULL,
        PRIMARY KEY (roleid, funcid)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

CREATE TABLE sys_role(
        id bigint NOT NULL,
        name varchar(255),
        roledesc varchar(255),
        code varchar(255),
        sort int,
        PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


CREATE TABLE sys_tree (
        id bigint NOT NULL,
        parent bigint,
        name varchar(255),
        nodedesc varchar(255),
	cacheFlag varchar(1),
	discriminator varchar(1),
	moveable varchar(1),
	treeId varchar(200),
        sort int,
        code varchar(255),
	icon varchar(255),
	iconCls varchar(255),
	url varchar(255),
	locked int default 0,
        PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

CREATE TABLE sys_user (
        id bigint NOT NULL,
        account varchar(255),
        password varchar(255),
        code varchar(255),
        name varchar(255),
        blocked int,
        createtime timestamp,
        lastlogintime timestamp,
        lastloginip varchar(255),
        evection int,
        mobile varchar(255),
        email varchar(255),
        telephone varchar(255),
        gender int,
        headship varchar(255),
        usertype int,
        fax varchar(255),
        accounttype int,
        dumpflag int,
        deptid bigint,
	adminFlag varchar(1),
	superiorIds varchar(200),
        PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

CREATE TABLE sys_user_role(
        id bigint not null,
        userid bigint default 0,
        roleid bigint default 0,
        authorized int default 0,
        authorizefrom bigint default 0,
        availdatestart timestamp,
        availdateend timestamp,
        processdescription varchar(255),
        PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

CREATE TABLE sys_dictory (
        id bigint not null,
        typeId bigint,
        name varchar(50),
        dictDesc varchar(200),
        code varchar(50),
	value_ varchar(2000),
        sort int,
        blocked int,
        ext1 varchar(200),
        ext2 varchar(200),
        ext3 varchar(200),
        ext4 varchar(200),
        ext5 datetime,
        ext6 datetime,
        ext7 datetime,
        ext8 datetime,
        ext9 datetime,
        ext10 datetime,
        ext11 bigint,
        ext12 bigint,
        ext13 bigint,
        ext14 bigint,
        ext15 bigint,
        ext16 double,
        ext17 double,
        ext18 double,
        ext19 double,
        ext20 double,
        PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table sys_dictory_def (
        id bigint NOT NULL,
        nodeId bigint,
        name varchar(50),
        columnName varchar(50),
        title varchar(50),
        type varchar(50),
        length int,
        sort int,
        required int,
        target varchar(50),
        PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

CREATE TABLE sys_workcalendar (
        id bigint NOT NULL,
        freeday int,
        freemonth int,
        freeyear int,
        PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


CREATE TABLE sys_todo(
        id bigint NOT NULL,
        code varchar(255),
        content varchar(255),
        deptid bigint,
        deptname varchar(255),
        enableflag int,
        limitday int,
        xa int,
        xb int,
        link varchar(255),
        listlink varchar(255),
	allListLink varchar(255),
        linktype varchar(255),
        appid bigint,
        moduleid bigint,
        modulename varchar(255),
        news varchar(255),
        objectid varchar(255),
        objectvalue varchar(255),
        processname varchar(255),
        rolecode varchar(255),
        roleid bigint,
        taskname varchar(255),
        title varchar(255),
        type varchar(50),
	provider varchar(50),
        sql_ longtext,
	sortno int,
        versionno bigint,
        PRIMARY KEY (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

CREATE TABLE sys_todo_instance(
        id bigint NOT NULL,
        actorid varchar(255),
        actorname varchar(255),
        title varchar(255),
        content varchar(255),
        provider varchar(255),
        link_ varchar(255),
        linktype varchar(255),
        createdate timestamp,
        startdate timestamp,
        enddate timestamp,
        alarmdate timestamp,
        pastduedate timestamp,
        taskinstanceid varchar(255),
        processinstanceid varchar(255),
        deptid bigint,
        deptname varchar(255),
        roleid bigint,
        rolecode varchar(255),
        rowid_ varchar(255),
        todoid bigint,
        appid bigint,
        moduleid bigint,
        objectid varchar(255),
        objectvalue varchar(255),
        versionno bigint,
        PRIMARY KEY (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

  CREATE TABLE sys_scheduler (
        id varchar(50)  NOT NULL,
	autoStartup int,
        createBy varchar(255),
        createDate timestamp,
	title varchar(200),
        content varchar(500),
	startDate timestamp,
        endDate timestamp,
        expression_ varchar(500),
        jobClass varchar(200),
        locked_ int,
        priority_ int,
        repeatCount int,
        repeatInterval int,
        startDelay int,
        startup_ int,
        taskName varchar(200),
        taskType varchar(50),
        threadSize int,
	attribute_ varchar(500),
        PRIMARY KEY (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

CREATE TABLE message(
        id bigint NOT NULL,
        type int,
        sysType int,
        sender bigint,
        recver bigint,
        recverList varchar(2000) ,
        title varchar(500) ,
        content varchar(2000) ,
        createDate timestamp,
        readed int,
        category int,
        crUser varchar(20),
        crDate timestamp,
        edUser varchar(20),
        edDate timestamp,
        PRIMARY KEY (id)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

CREATE TABLE mymenu(
        id bigint not null,
        userId bigint,
        title varchar(100),
        url varchar(200),
        sort int,
        crUser varchar(20),
        crDate timestamp,
        edUser varchar(20),
        edDate timestamp,
        PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

CREATE TABLE subjectcode(
        id bigint not null,
        parent bigint,
        subjectCode varchar(20),
        subjectName varchar(100),
        feeSum double ,
        month1 double ,
        month2 double ,
        month3 double ,
        month4 double ,
        month5 double ,
        month6 double ,
        month7 double ,
        month8 double ,
        month9 double ,
        month10 double ,
        month11 double ,
        month12 double ,
        feeYear int ,
        sort int ,
        PRIMARY KEY (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


CREATE TABLE  Attachment (
	id bigint  not null,
	referId bigint  ,
	referType int  ,
	name varchar (100)   ,
	url varchar (200)   ,
	createDate timestamp  ,
	createId bigint  ,
	crUser varchar (20)   ,
	crDate timestamp  ,
	edUser varchar (20)   ,
	edDate timestamp ,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


  CREATE TABLE  MyAudit (
	id bigint not null,
	referId bigint ,
	referType int ,
	deptId bigint ,
	deptName varchar (50) ,
	headship varchar (100) ,
	leaderName varchar (100) ,
	leaderId bigint ,
	createDate timestamp ,
	memo longtext ,
	flag int ,
	crUser varchar (20) ,
	crDate timestamp ,
	edUser varchar (20) ,
	edDate timestamp , 
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


create table sys_dbid(
        name_ varchar(50)  not null,
	title_ varchar(255),
        value_ varchar(255) not null,
        version_ integer not null,
        primary key (name_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


create table sys_agent (
        ID_ varchar(50)  not null,
        AGENTTYPE_ integer,
        ASSIGNFROM_ varchar(255) ,
        ASSIGNTO_ varchar(255) ,
        CREATEDATE_ timestamp ,
        ENDDATE_ timestamp ,
        LOCKED_ integer ,
        OBJECTID_ varchar(255) ,
        OBJECTVALUE_ varchar(255) ,
        PROCESSNAME_ varchar(255) ,
        SERVICEKEY_ varchar(50) ,
        STARTDATE_ timestamp ,
        TASKNAME_ varchar(255) ,
        PRIMARY KEY (ID_)
 )ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 
CREATE TABLE sys_property (
        id_ varchar(50) NOT NULL,
        category_ varchar(200),
        description_ varchar(500),
        initvalue_ varchar(1000),
        locked_ integer,
        name_ varchar(50),
        title_ varchar(200),
        type_ varchar(50),
        value_ varchar(1000),
        PRIMARY KEY (id_)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


create table sys_params(
        id varchar(50) not null,
        business_key varchar(200) not null,
        date_val timestamp,
        double_val double,
        int_val integer,
        java_type varchar(20) not null,
        key_name varchar(50) not null,
        long_val bigint,
        service_key varchar(50) not null,
        string_val varchar(2000),
        text_val longtext,
        title varchar(200),
        type_cd varchar(20) not null,
        primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


create table sys_input_def (
        id varchar(50) not null,
        init_value varchar(500),
        input_type varchar(50),
        java_type varchar(20) not null,
        key_name varchar(50) not null,
        required varchar(10),
        service_key varchar(50) not null,
        text_field varchar(50),
        title varchar(200) not null,
        type_cd varchar(20) not null,
        type_title varchar(200),
        url varchar(250),
        valid_type varchar(50),
        value_field varchar(50),
        primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


create table sys_table (
        tablename_ varchar(50) not null,
        parenttablename_ varchar(50),
        packagename_ varchar(200),
        entityname_ varchar(50),
        classname_ varchar(250),
        title_ varchar(255),
        englishtitle_ varchar(255),
        columnqty_ integer,
        addtype_ integer,
        sysnum_ varchar(100),
        issubtable_ varchar(2),
        topid_ varchar(50),
        aggregationkeys_ varchar(500),
        queryids_ varchar(500),
        temporaryflag_ varchar(1),
        deletefetch_ varchar(1),
        createtime_ timestamp,
        createby_ varchar(50),
        description_ varchar(500),
        type_ varchar(50),
        displaytype_ varchar(50),
        insertcascade_ integer,
        updatecascade_ integer,
        deletecascade_ integer,
        locked_ integer,
        deleteflag_ integer,
        systemflag_ varchar(2),
        revision_ integer,
        sortno_ integer,
        primary key (tablename_)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


    create table sys_column (
        id_ varchar(100) not null,
        queryid_ varchar(50),
        tablename_ varchar(50),
        targetid_ varchar(50),
        alias_ varchar(50),
        columnname_ varchar(50),
        columnlabel_ varchar(50),
        name_ varchar(50),
        title_ varchar(100),
        englishtitle_ varchar(100),
        length_ integer,
        scale_ integer,
        precision_ integer,
        primarykey_ varchar(10),
        null_ varchar(10),
        frozen_ varchar(10),
        unique_ varchar(10),
        searchable_ varchar(10),
        editable_ varchar(10),
        updateable_ varchar(10),
        resizable_ varchar(10),
        hidden_ varchar(10),
        tooltip_ varchar(100),
        ordinal_ integer,
        javatype_ varchar(20),
        inputtype_ varchar(50),
        valuefield_ varchar(50),
        textfield_ varchar(50),
        url_ varchar(250),
        validtype_ varchar(50),
        required varchar(10),
        regex_ varchar(100),
        defaultvalue_ varchar(200),
        discriminator_ varchar(10),
        formula_ varchar(200),
        mask_ varchar(100),
        datacode_ varchar(50),
        rendertype_ varchar(50),
        translator_ varchar(100),
        summarytype_ varchar(50),
        summaryexpr_ varchar(200),
        displaytype_ integer,
        sortable_ varchar(10),
        sorttype_ varchar(50),
        systemflag_ varchar(2),
        formatter_ varchar(200),
        align_ varchar(50),
        height_ varchar(50),
        width_ varchar(50),
        link_ varchar(200),
        iscollection_ varchar(10),
        valueexpression_ varchar(200),
        renderer_ varchar(100),
        primary key (id_)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


    create index IDX_USER_ACCOUNT on SYS_USER (ACCOUNT);

    create index IDX_USER_NAME on SYS_USER (NAME);

    create index IDX_TREE_NAME on SYS_TREE (NAME);

    create index IDX_TREE_CODE on SYS_TREE (CODE);

    create index IDX_ROLE_NAME on SYS_ROLE (NAME);

    create index IDX_ROLE_CODE on SYS_ROLE (CODE);

    create index SYS_DEPT_NAME on SYS_DEPARTMENT (NAME);

    create index SYS_DEPT_CODE on SYS_DEPARTMENT (CODE);

    create index SYS_DEPT_NODE on SYS_DEPARTMENT (NODEID);

    create index SYS_APP_NAME on SYS_APPLICATION (NAME);

    create index SYS_APP_CODE on SYS_APPLICATION (CODE);

    create index SYS_APP_NODE on SYS_APPLICATION (NODEID);

    create index SYS_DEPTROLE_DEPT on SYS_DEPT_ROLE (DEPTID);

    create index SYS_DEPTROLE_ROLE on SYS_DEPT_ROLE (SYSROLEID);

    create index SYS_USERROLE_ROLE on SYS_USER_ROLE (ROLEID);

    create index SYS_USERROLE_USER on SYS_USER_ROLE (USERID);


    alter table sys_access 
        add index FK_ACCESS_APP (appId), 
        add constraint FK_ACCESS_APP 
        foreign key (appId) 
        references sys_application (id);

    alter table sys_access 
        add index FK_ACCESS_DEPTROLE (roleId), 
        add constraint FK_ACCESS_DEPTROLE 
        foreign key (roleId) 
        references sys_dept_role (id);

    alter table sys_application 
        add index FK_APP_TREE (nodeId), 
        add constraint FK_APP_TREE 
        foreign key (nodeId) 
        references sys_tree (id);

    alter table sys_department 
        add index FK_DEPT_TREE (nodeId), 
        add constraint FK_DEPT_TREE 
        foreign key (nodeId) 
        references sys_tree (id);

    alter table sys_dept_role 
        add index FK_DEPTROLE_DEPT (deptId), 
        add constraint FK_DEPTROLE_DEPT 
        foreign key (deptId) 
        references sys_department (id);

    alter table sys_dept_role 
        add index FK_DEPTROLE_ROLE (sysRoleId), 
        add constraint FK_DEPTROLE_ROLE 
        foreign key (sysRoleId) 
        references sys_role (id);

    alter table sys_function 
        add index FK_FUN_APP (appId), 
        add constraint FK_FUN_APP 
        foreign key (appId) 
        references sys_application (id);

    alter table sys_permission 
        add index FK_PERM_DEPTROLE (roleId), 
        add constraint FK_PERM_DEPTROLE 
        foreign key (roleId) 
        references sys_dept_role (id);

    alter table sys_permission 
        add index FK_PERM_FUN (funcId), 
        add constraint FK_PERM_FUN 
        foreign key (funcId) 
        references sys_function (id);

    alter table sys_user_role 
        add index FK_USERROLE_ROLE (roleId), 
        add constraint FK_USERROLE_ROLE 
        foreign key (roleId) 
        references sys_dept_role (id);

    alter table sys_user_role 
        add index FK_USERROLE_USER (userId), 
        add constraint FK_USERROLE_USER 
        foreign key (userId) 
        references sys_user (id);