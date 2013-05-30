
 
 
CREATE TABLE sys_access(
        roleid bigint  NOT NULL,
        appid bigint NOT NULL,
        PRIMARY KEY (roleid, appid)
);


CREATE TABLE sys_application(
        id bigint not null,
        name nvarchar(255),
	code nvarchar(255),
        appdesc nvarchar(255),
        url nvarchar(255),
        sort int,
        showmenu int,
        nodeid bigint NOT NULL,
	locked int default 0,
        PRIMARY KEY (id)
);


CREATE TABLE sys_department(
        id bigint not null,
        name nvarchar(255),
        deptdesc nvarchar(255),
        createtime datetime,
        sort int,
        deptno nvarchar(255),
        code nvarchar(255),
        code2 nvarchar(255),
        status int,
        fincode nvarchar(255),
        nodeid bigint NOT NULL,
        PRIMARY KEY (id)
);

CREATE TABLE sys_dept_role(
        id bigint not null,
        grade int,
        code nvarchar(255),
        sort int,
        sysroleid bigint NOT NULL,
        deptid bigint NOT NULL,
        PRIMARY KEY (id)
);

CREATE TABLE sys_function(
        id bigint not null,
        name nvarchar(255),
	code nvarchar(50),
        funcdesc nvarchar(255),
        funcmethod nvarchar(255),
        sort int,
        appid bigint NOT NULL,
        PRIMARY KEY (id)
);

CREATE TABLE sys_log(
        id bigint not null,
        account nvarchar(255),
        ip nvarchar(255),
        createtime datetime,
        operate nvarchar(255),
        flag int,
        PRIMARY KEY (id)
);

CREATE TABLE sys_permission (
        roleid bigint NOT NULL,
        funcid bigint NOT NULL,
        PRIMARY KEY (roleid, funcid)
 );

CREATE TABLE sys_role(
        id bigint not null,
        name nvarchar(255),
        roledesc nvarchar(255),
        code nvarchar(255),
        sort int,
        PRIMARY KEY (id)
);


CREATE TABLE sys_tree (
        id bigint not null,
        parent bigint,
        name nvarchar(255),
        nodedesc nvarchar(255),
	cacheFlag nvarchar(1),
	discriminator nvarchar(1),
	moveable nvarchar(1),
	treeId nvarchar(200),
        sort int,
        code nvarchar(255),
	icon nvarchar(255),
	iconCls nvarchar(255),
	url nvarchar(255),
	locked int default 0,
        PRIMARY KEY (id)
);

CREATE TABLE sys_user (
        id bigint not null,
        account nvarchar(255),
        password nvarchar(255),
        code nvarchar(255),
        name nvarchar(255),
        blocked int,
        createtime datetime,
        lastlogintime datetime,
        lastloginip nvarchar(255),
        evection int,
        mobile nvarchar(255),
        email nvarchar(255),
        telephone nvarchar(255),
        gender int,
        headship nvarchar(255),
        usertype int,
        fax nvarchar(255),
        accounttype int,
        dumpflag int,
        deptid bigint,
	adminFlag nvarchar(1),
	superiorIds nvarchar(200),
        PRIMARY KEY (id)
);

CREATE TABLE sys_user_role(
        id bigint not null,
        userid bigint default 0,
        roleid bigint default 0,
        authorized int default 0,
        authorizefrom bigint default 0,
        availdatestart datetime,
        availdateend datetime,
        processdescription nvarchar(255),
        PRIMARY KEY (id)
);

CREATE TABLE sys_dictory (
        id bigint not null,
        typeId bigint,
        name nvarchar(50),
        dictDesc nvarchar(200),
        code nvarchar(50),
	value_ nvarchar(2000),
        sort int,
        blocked int,
        ext1 nvarchar(200),
        ext2 nvarchar(200),
        ext3 nvarchar(200),
        ext4 nvarchar(200),
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
        ext16 double precision,
        ext17 double precision,
        ext18 double precision,
        ext19 double precision,
        ext20 double precision,
        PRIMARY KEY (id)
);

create table sys_dictory_def (
        id bigint NOT NULL,
        nodeId bigint,
        name nvarchar(50),
        columnName nvarchar(50),
        title nvarchar(50),
        type nvarchar(50),
        length int,
        sort int,
        required int,
        target nvarchar(50),
        PRIMARY KEY (id)
);

CREATE TABLE sys_workcalendar (
        id bigint not null,
        freeday int,
        freemonth int,
        freeyear int,
        PRIMARY KEY (id)
);


CREATE TABLE sys_todo(
        id bigint not null,
        code nvarchar(255),
        content nvarchar(255),
        deptid bigint,
        deptname nvarchar(255),
        enableflag int,
        limitday int,
        xa int,
        xb int,
        link nvarchar(255),
        listlink nvarchar(255),
	allListLink nvarchar(255),
        linktype nvarchar(255),
        appid bigint,
        moduleid bigint,
        modulename nvarchar(255),
        objectid nvarchar(255),
        objectvalue nvarchar(255),
        processname nvarchar(255),
        rolecode nvarchar(255),
        roleid bigint,
        taskname nvarchar(255),
        title nvarchar(255),
        type nvarchar(50),
	provider varchar(50),
        sql_ text,
	sortno int,
        versionno bigint,
        PRIMARY KEY (id)
    );

CREATE TABLE sys_todo_instance(
        id bigint not null,
        actorid nvarchar(255),
        actorname nvarchar(255),
        title nvarchar(255),
        content nvarchar(255),
        provider nvarchar(255),
        link_ nvarchar(255),
        linktype nvarchar(255),
        createdate datetime,
        startdate datetime,
        enddate datetime,
        alarmdate datetime,
        pastduedate datetime,
        taskinstanceid nvarchar(255),
        processinstanceid nvarchar(255),
        deptid bigint,
        deptname nvarchar(255),
        roleid bigint,
        rolecode nvarchar(255),
        rowid_ nvarchar(255),
        todoid bigint,
        appid bigint,
        moduleid bigint,
        objectid nvarchar(255),
        objectvalue nvarchar(255),
        versionno bigint,
        PRIMARY KEY (id)
    );

  CREATE TABLE sys_scheduler (
        id nvarchar(50) not null,
	autoStartup int,
        createBy nvarchar(255),
        createDate datetime,
	title nvarchar(200),
        content nvarchar(500),
	startDate datetime,
        endDate datetime,
        expression_ nvarchar(500),
        jobClass nvarchar(200),
        locked_ int,
        priority_ int,
        repeatCount int,
        repeatInterval int,
        startDelay int,
        startup_ int,
        taskName nvarchar(200),
        taskType nvarchar(50),
        threadSize int,
	attribute_ nvarchar(500),
        PRIMARY KEY (id)
    );

CREATE TABLE message(
        id bigint not null,
        type int,
        sysType int,
        sender bigint,
        recver bigint,
        recverList nvarchar(2000) ,
        title nvarchar(500) ,
        content nvarchar(2000) ,
        createDate datetime,
        readed int,
        category int,
        crUser nvarchar(20),
        crDate datetime,
        edUser nvarchar(20),
        edDate datetime,
        PRIMARY KEY (id)
  );

CREATE TABLE mymenu(
        id bigint not null,
        userId bigint,
        title nvarchar(100),
        url nvarchar(200),
        sort int,
        crUser nvarchar(20),
        crDate datetime,
        edUser nvarchar(20),
        edDate datetime,
        PRIMARY KEY (id)
);

CREATE TABLE subjectcode(
        id bigint not null ,
        parent bigint,
        subjectCode nvarchar(20),
        subjectName nvarchar(100),
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
        feeYear int ,
        sort int ,
        PRIMARY KEY (id)
    );


CREATE TABLE  Attachment (
	id bigint  not null,
	referId bigint  ,
	referType int  ,
	name nvarchar (100)   ,
	url nvarchar (200)   ,
	createDate datetime  ,
	createId bigint  ,
	crUser nvarchar (20)   ,
	crDate datetime  ,
	edUser nvarchar (20)   ,
	edDate datetime ,
	PRIMARY KEY (id)
);


  CREATE TABLE  MyAudit (
	id bigint not null,
	referId bigint ,
	referType int ,
	deptId bigint ,
	deptName nvarchar (50) ,
	headship nvarchar (100) ,
	leaderName nvarchar (100) ,
	leaderId bigint ,
	createDate datetime ,
	memo text ,
	flag int ,
	crUser nvarchar (20) ,
	crDate datetime ,
	edUser nvarchar (20) ,
	edDate datetime , 
	PRIMARY KEY (id)
);

create table sys_dbid(
        name_ nvarchar(50)  not null,
	title_ varchar(255),
        value_ nvarchar(255) not null,
        version_ int not null,
        primary key (name_)
);

create table sys_agent (
        ID_ nvarchar(50)  not null,
        AGENTTYPE_ int,
        ASSIGNFROM_ nvarchar(255) ,
        ASSIGNTO_ nvarchar(255) ,
        CREATEDATE_ datetime,
        ENDDATE_ datetime,
        LOCKED_ int,
        OBJECTID_ nvarchar(255) ,
        OBJECTVALUE_ nvarchar(255) ,
        PROCESSNAME_ nvarchar(255) ,
        SERVICEKEY_ nvarchar(50) ,
        STARTDATE_ datetime,
        TASKNAME_ nvarchar(255) ,
        PRIMARY KEY (ID_)
 );

CREATE TABLE sys_property (
        id_ nvarchar(50) NOT NULL,
        category_ nvarchar(200),
        description_ nvarchar(500),
        initvalue_ nvarchar(1000),
        locked_ int,
        name_ nvarchar(50),
        title_ nvarchar(200),
        type_ nvarchar(50),
        value_ nvarchar(1000),
        PRIMARY KEY (id_)
);

create table sys_params(
        id nvarchar(50) not null,
        business_key nvarchar(200) not null,
        date_val datetime,
        double_val double precision,
        int_val int,
        java_type nvarchar(20) not null,
        key_name nvarchar(50) not null,
        long_val bigint,
        service_key nvarchar(50) not null,
        string_val nvarchar(2000),
        text_val text,
        title nvarchar(200),
        type_cd nvarchar(20) not null,
        primary key (id)
);


create table sys_input_def (
        id nvarchar(50) not null,
        init_value nvarchar(500),
        input_type nvarchar(50),
        java_type nvarchar(20) not null,
        key_name nvarchar(50) not null,
        required nvarchar(10),
        service_key nvarchar(50) not null,
        text_field nvarchar(50),
        title nvarchar(200) not null,
        type_cd nvarchar(20) not null,
        type_title nvarchar(200),
        url nvarchar(250),
        valid_type nvarchar(50),
        value_field nvarchar(50),
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
        columnqty_ int,
        addtype_ int,
        sysnum_ varchar(100),
        issubtable_ varchar(2),
        topid_ varchar(50),
        aggregationkeys_ varchar(500),
        queryids_ varchar(500),
        temporaryflag_ varchar(1),
        deletefetch_ varchar(1),
        createtime_ datetime,
        createby_ varchar(50),
        description_ varchar(500),
        type_ varchar(50),
        displaytype_ varchar(50),
        insertcascade_ int,
        updatecascade_ int,
        deletecascade_ int,
        locked_ int,
        deleteflag_ int,
        systemflag_ varchar(2),
        revision_ int,
        sortno_ int,
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
        length_ int,
        scale_ int,
        precision_ int,
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
        ordinal_ int,
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
        displaytype_ int,
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