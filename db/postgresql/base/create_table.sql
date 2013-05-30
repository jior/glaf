

CREATE TABLE sys_access(
        roleid int8 NOT NULL,
        appid int8 NOT NULL,
        PRIMARY KEY (roleid, appid)
);


CREATE TABLE sys_application(
        id int8 NOT NULL,
        name varchar(255),
	code varchar(255),
        appdesc varchar(255),
        url varchar(255),
        sort int4,
        showmenu int4,
        nodeid int8 NOT NULL,
	locked int default 0,
        PRIMARY KEY (id)
);


CREATE TABLE sys_department(
        id int8 NOT NULL,
        name varchar(255),
        deptdesc varchar(255),
        createtime timestamp,
        sort int4,
        deptno varchar(255),
        code varchar(255),
        code2 varchar(255),
        status int4,
        fincode varchar(255),
        nodeid int8 NOT NULL,
        PRIMARY KEY (id)
);

CREATE TABLE sys_dept_role(
        id int8 NOT NULL,
        grade int4,
        code varchar(255),
        sort int4,
        sysroleid int8 NOT NULL,
        deptid int8 NOT NULL,
        PRIMARY KEY (id)
);

CREATE TABLE sys_function(
        id int8 NOT NULL,
        name varchar(255),
	code varchar(50),
        funcdesc varchar(255),
        funcmethod varchar(255),
        sort int4,
        appid int8 NOT NULL,
        PRIMARY KEY (id)
);

CREATE TABLE sys_log(
        id int8 NOT NULL,
        account varchar(255),
        ip varchar(255),
        createtime timestamp,
        operate varchar(255),
        flag int4,
        PRIMARY KEY (id)
);

CREATE TABLE sys_permission (
        roleid int8 NOT NULL,
        funcid int8 NOT NULL,
        PRIMARY KEY (roleid, funcid)
 );

CREATE TABLE sys_role(
        id int8 NOT NULL,
        name varchar(255),
        roledesc varchar(255),
        code varchar(255),
        sort int4,
        PRIMARY KEY (id)
);


CREATE TABLE sys_tree (
        id int8 NOT NULL,
        parent int8,
        name varchar(255),
        nodedesc varchar(255),
	cacheFlag varchar(1),
	discriminator varchar(1),
	moveable varchar(1),
	treeId varchar(200),
        sort int4,
        code varchar(255),
	icon varchar(255),
	iconCls varchar(255),
	url varchar(255),
	locked int4 default 0,
        PRIMARY KEY (id)
);

CREATE TABLE sys_user (
        id int8 NOT NULL,
        account varchar(255),
        password varchar(255),
        code varchar(255),
        name varchar(255),
        blocked int4,
        createtime timestamp,
        lastlogintime timestamp,
        lastloginip varchar(255),
        evection int4,
        mobile varchar(255),
        email varchar(255),
        telephone varchar(255),
        gender int4,
        headship varchar(255),
        usertype int4,
        fax varchar(255),
        accounttype int4,
        dumpflag int4,
        deptid int8,
	adminFlag varchar(1),
	superiorIds varchar(200),
        PRIMARY KEY (id)
);

CREATE TABLE sys_user_role(
        id int8 not null,
        userid int8 default 0,
        roleid int8 default 0,
        authorized int4 default 0,
        authorizefrom int8 default 0,
        availdatestart timestamp,
        availdateend timestamp,
        processdescription varchar(255),
        PRIMARY KEY (id)
);

CREATE TABLE sys_dictory (
        id int8 not null,
        typeId int8,
        name varchar(50),
        dictDesc varchar(200),
        code varchar(50),
	value_ varchar(2000),
        sort int4,
        blocked int4,
        ext1 varchar(200),
        ext2 varchar(200),
        ext3 varchar(200),
        ext4 varchar(200),
        ext5 timestamp,
        ext6 timestamp,
        ext7 timestamp,
        ext8 timestamp,
        ext9 timestamp,
        ext10 timestamp,
        ext11 bigint,
        ext12 bigint,
        ext13 bigint,
        ext14 bigint,
        ext15 bigint,
        ext16 double precision,
        ext17 double precision,
        ext18 double precision,
        ext19 double precision,
        ext20 double precision,
        PRIMARY KEY (id)
);

create table sys_dictory_def (
        id int8 NOT NULL,
        nodeId int8,
        name varchar(50),
        columnName varchar(50),
        title varchar(50),
        type varchar(50),
        length int4,
        sort int4,
        required int4,
        target varchar(50),
        PRIMARY KEY (id)
);

CREATE TABLE sys_workcalendar (
        id int8 NOT NULL,
        freeday int4,
        freemonth int4,
        freeyear int4,
        PRIMARY KEY (id)
);


CREATE TABLE sys_todo(
        id int8 NOT NULL,
        code varchar(255),
        content varchar(255),
        deptid int8,
        deptname varchar(255),
        enableflag int4,
        limitday int4,
        xa int4,
        xb int4,
        link varchar(255),
        listlink varchar(255),
	allListLink varchar(255),
        linktype varchar(255),
        appid int8,
        moduleid int8,
        modulename varchar(255),
        objectid varchar(255),
        objectvalue varchar(255),
        processname varchar(255),
        rolecode varchar(255),
        roleid int8,
        tablename varchar(255),
        taskname varchar(255),
        title varchar(255),
        type varchar(50),
	provider varchar(50),
        sql_ text,
	sortno int4,
        versionno int8,
        PRIMARY KEY (id)
    );

CREATE TABLE sys_todo_instance(
        id int8 NOT NULL,
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
        deptid int8,
        deptname varchar(255),
        roleid int8,
        rolecode varchar(255),
        rowid_ varchar(255),
        todoid int8,
        appid int8,
        moduleid int8,
        objectid varchar(255),
        objectvalue varchar(255),
        versionno int8,
        PRIMARY KEY (id)
    );

  CREATE TABLE sys_scheduler (
        id varchar(50)  NOT NULL,
	autoStartup int4,
        createBy varchar(255),
        createDate timestamp,
	title varchar(200),
        content varchar(500),
	startDate timestamp,
        endDate timestamp,
        expression_ varchar(500),
        jobClass varchar(200),
        locked_ int4,
        priority_ int4,
        repeatCount int4,
        repeatInterval int4,
        startDelay int4,
        startup_ int4,
        taskName varchar(200),
        taskType varchar(50),
        threadSize int4,
	attribute_ varchar(500),
        PRIMARY KEY (id)
    );

CREATE TABLE message(
        id int8 NOT NULL,
        type int4,
        sysType int4,
        sender int8,
        recver int8,
        recverList varchar(2000) ,
        title varchar(500) ,
        content varchar(2000) ,
        createDate timestamp,
        readed int4,
        category int4,
        crUser varchar(20),
        crDate timestamp,
        edUser varchar(20),
        edDate timestamp,
        PRIMARY KEY (id)
  );

CREATE TABLE mymenu(
        id int8 not null,
        userId int8,
        title varchar(100),
        url varchar(200),
        sort int4,
        crUser varchar(20),
        crDate timestamp,
        edUser varchar(20),
        edDate timestamp,
        PRIMARY KEY (id)
);

CREATE TABLE subjectcode(
        id int8 not null ,
        parent int8,
        subjectCode varchar(20),
        subjectName varchar(100),
        feeSum double precision ,
        month1 double precision ,
        month2 double precision ,
        month3 double precision ,
        month4 double precision ,
        month5 double precision ,
        month6 double precision ,
        month7 double precision ,
        month8 double precision ,
        month9 double precision ,
        month10 double precision ,
        month11 double precision ,
        month12 double precision ,
        feeYear int4 ,
        sort int4 ,
        PRIMARY KEY (id)
    );


CREATE TABLE  Attachment (
	id bigint  not null ,
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
);


  CREATE TABLE  MyAudit (
	id bigint not null ,
	referId bigint ,
	referType int ,
	deptId bigint ,
	deptName varchar (50) ,
	headship varchar (100) ,
	leaderName varchar (100) ,
	leaderId bigint ,
	createDate timestamp ,
	memo varchar (2000) ,
	flag int ,
	crUser varchar (20) ,
	crDate timestamp ,
	edUser varchar (20) ,
	edDate timestamp , 
	PRIMARY KEY (id)
);

create table sys_dbid(
        name_ varchar(50)  not null,
	title_ varchar(255),
        value_ varchar(255) not null,
        version_ int not null,
        primary key (name_)
);


create table sys_agent (
        ID_ varchar(50)  not null,
        AGENTTYPE_ int,
        ASSIGNFROM_ varchar(255) ,
        ASSIGNTO_ varchar(255) ,
        CREATEDATE_ timestamp,
        ENDDATE_ timestamp,
        LOCKED_ int,
        OBJECTID_ varchar(255) ,
        OBJECTVALUE_ varchar(255) ,
        PROCESSNAME_ varchar(255) ,
        SERVICEKEY_ varchar(50) ,
        STARTDATE_ timestamp,
        TASKNAME_ varchar(255) ,
        PRIMARY KEY (ID_)
 );


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
);

create table sys_lob (
        id_ varchar(50) not null,
        contenttype_ varchar(50),
        createby_ varchar(50),
        createdate_ timestamp,
        deleteflag_ integer,
        deviceid_ varchar(20),
        fileid_ varchar(50),
        filename_ varchar(500),
        lastmodified_ bigint,
        locked_ integer,
        name_ varchar(50),
        objectid_ varchar(255),
        objectvalue_ varchar(255),
        resourceid_ varchar(50),
        servicekey_ varchar(50),
        size_ bigint,
        status_ integer,
        type_ varchar(50),
        data_ bytea,
        primary key (id_)
);

create table sys_mail_file (
        id varchar(50) not null,
        createdate timestamp,
        filecontent bytea,
        filename varchar(255),
        size bigint,
        topid varchar(100),
        primary key (id)
);


create table sys_params(
        id varchar(50) not null,
        business_key varchar(200) not null,
        date_val timestamp,
        double_val double precision,
        int_val integer,
        java_type varchar(20) not null,
        key_name varchar(50) not null,
        long_val bigint,
        service_key varchar(50) not null,
        string_val varchar(2000),
        text_val text,
        title varchar(200),
        type_cd varchar(20) not null,
        primary key (id)
);


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
);


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
    );


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
    );

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
        add constraint FK_ACCESS_APP 
        foreign key (appId) 
        references sys_application;

    alter table sys_access 
        add constraint FK_ACCESS_DEPTROLE 
        foreign key (roleId) 
        references sys_dept_role;

    alter table sys_application 
        add constraint FK_APP_TREE 
        foreign key (nodeId) 
        references sys_tree;

    alter table sys_department 
        add constraint FK_DEPT_TREE 
        foreign key (nodeId) 
        references sys_tree;

    alter table sys_dept_role 
        add constraint FK_DEPTROLE_DEPT 
        foreign key (deptId) 
        references sys_department;

    alter table sys_dept_role 
        add constraint FK_DEPTROLE_ROLE 
        foreign key (sysRoleId) 
        references sys_role;

    alter table sys_function 
        add constraint FK_FUN_APP 
        foreign key (appId) 
        references sys_application;

    alter table sys_permission 
        add constraint FK_PERM_DEPTROLE 
        foreign key (roleId) 
        references sys_dept_role;

    alter table sys_permission 
        add constraint FK_PERM_FUN
        foreign key (funcId) 
        references sys_function;

    alter table sys_user_role 
        add constraint FK_USERROLE_ROLE 
        foreign key (roleId) 
        references sys_dept_role;

    alter table sys_user_role 
        add constraint FK_USERROLE_USER 
        foreign key (userId) 
        references sys_user;

