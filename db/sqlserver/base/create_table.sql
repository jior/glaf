
 
 
CREATE TABLE SYS_ACCESS(
        roleid bigint  NOT NULL,
        appid bigint NOT NULL,
        PRIMARY KEY (roleid, appid)
);


CREATE TABLE SYS_APPLICATION(
        id bigint not null,
        name nvarchar(255),
	type nvarchar(50),
	code nvarchar(255),
        appdesc nvarchar(500),
        url nvarchar(255),
        sort int,
        showmenu int,
        nodeid bigint NOT NULL,
	locked int default 0,
        PRIMARY KEY (id)
);


CREATE TABLE SYS_DEPARTMENT(
        id bigint not null,
        name nvarchar(255),
        deptdesc nvarchar(500),
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

CREATE TABLE SYS_DEPT_ROLE(
        id bigint not null,
        grade int,
        code nvarchar(255),
        sort int,
        sysroleid bigint NOT NULL,
        deptid bigint NOT NULL,
        PRIMARY KEY (id)
);

CREATE TABLE SYS_FUNCTION(
        id bigint not null,
        name nvarchar(255),
	code nvarchar(50),
        funcdesc nvarchar(500),
        funcmethod nvarchar(255),
        sort int,
        appid bigint NOT NULL,
        PRIMARY KEY (id)
);

CREATE TABLE SYS_LOG(
        id bigint not null,
        account nvarchar(50),
	moduleid nvarchar(50),
        ip nvarchar(255),
        createtime datetime,
        operate nvarchar(255),
	content nvarchar(2000),
        flag int,
	timems int,
        PRIMARY KEY (id)
);

 CREATE TABLE SYS_USER_ONLINE (
        ID_ bigint NOT NULL,
        ACTORID_ nvarchar(50),
        CHECKDATE_ datetime,
        CHECKDATEMS_ bigint,
        LOGINDATE_ datetime,
        LOGINIP_ nvarchar(100),
        NAME_ nvarchar(50),
        SESSIONID_ nvarchar(200),
        PRIMARY KEY (ID_)
 );

CREATE TABLE SYS_USER_ONLINE_LOG (
        ID_ bigint NOT NULL,
        ACTORID_ nvarchar(50),
        LOGINDATE_ datetime,
        LOGINIP_ nvarchar(100),
        LOGOUTDATE_ bigint,
        NAME_ nvarchar(50),
        SESSIONID_ nvarchar(200),
        PRIMARY KEY (ID_)
 );

CREATE TABLE SYS_PERMISSION (
        roleid bigint NOT NULL,
        funcid bigint NOT NULL,
        PRIMARY KEY (roleid, funcid)
 );

CREATE TABLE SYS_ROLE(
        id bigint not null,
        name nvarchar(255),
        roledesc nvarchar(500),
        code nvarchar(255),
	type nvarchar(50),
	isusebranch nvarchar(10),
        sort int,
        PRIMARY KEY (id)
);


CREATE TABLE SYS_TREE (
        id bigint not null,
        parent bigint,
        name nvarchar(255),
        nodedesc nvarchar(500),
	cacheFlag nvarchar(1),
	discriminator nvarchar(1),
	moveable nvarchar(1),
	treeId nvarchar(500),
        sort int,
        code nvarchar(255),
	icon nvarchar(255),
	iconCls nvarchar(255),
	url nvarchar(255),
	locked int default 0,
        PRIMARY KEY (id)
);

CREATE TABLE SYS_USER (
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
	loginCount int,
	isChangePassword int,
	lastChangePasswordDate datetime,
        PRIMARY KEY (id)
);

CREATE TABLE SYS_USER_ROLE(
        id bigint not null,
        userid bigint default 0,
        roleid bigint default 0,
        authorized int default 0,
        authorizefrom bigint default 0,
        availdatestart datetime,
        availdateend datetime,
        processdescription nvarchar(500),
        PRIMARY KEY (id)
);

CREATE TABLE SYS_DICTORY (
        id bigint not null,
        typeId bigint,
        name nvarchar(50),
        dictDesc nvarchar(500),
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

create table SYS_DICTORY_DEF (
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

CREATE TABLE SYS_WORKCALENDAR (
        id bigint not null,
        freeday int,
        freemonth int,
        freeyear int,
        PRIMARY KEY (id)
);


CREATE TABLE SYS_TODO(
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
        sql_ nvarchar(max) null,
	sortno int,
        versionno bigint,
        PRIMARY KEY (id)
    );

CREATE TABLE SYS_TODO_INSTANCE(
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

  CREATE TABLE SYS_SCHEDULER (
        id nvarchar(50) not null,
	autoStartup int,
        createBy nvarchar(50),
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
	jobRunTime bigint,
        nextFireTime datetime,
        previousFireTime datetime,
        runStatus int,
        runType int,
        PRIMARY KEY (id)
    );

CREATE TABLE MESSAGE(
        id bigint not null,
        type int,
        sysType int,
        sender bigint,
        recver bigint,
        recverList nvarchar(2000) ,
        title nvarchar(500) ,
        content nvarchar(max) null,
        createDate datetime,
        readed int,
        category int,
        crUser nvarchar(20),
        crDate datetime,
        edUser nvarchar(20),
        edDate datetime,
        PRIMARY KEY (id)
  );

CREATE TABLE MYMENU(
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


CREATE TABLE ATTACHMENT (
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

 

create table SYS_DBID(
        name_ nvarchar(50)  not null,
	title_ varchar(255),
        value_ nvarchar(255) not null,
        version_ int not null,
        primary key (name_)
);

create table SYS_AGENT (
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

CREATE TABLE SYS_PROPERTY (
        id_ nvarchar(50) NOT NULL,
        category_ nvarchar(200),
        description_ nvarchar(500),
        initvalue_ nvarchar(1000),
        locked_ int,
        name_ nvarchar(50),
        title_ nvarchar(200),
        type_ nvarchar(50),
	inputtype_ nvarchar(50),
        value_ nvarchar(1000),
        PRIMARY KEY (id_)
);

create table SYS_PARAMS(
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
        text_val nvarchar(max),
        title nvarchar(200),
        type_cd nvarchar(20) not null,
        primary key (id)
);


create table SYS_INPUT_DEF (
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


    create table SYS_TABLE (
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


    create table SYS_COLUMN (
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

    alter table SYS_ACCESS 
        add constraint FK_ACCESS_APP 
        foreign key (appId) 
        references SYS_APPLICATION;

    alter table SYS_ACCESS 
        add constraint FK_ACCESS_DEPTROLE 
        foreign key (roleId) 
        references SYS_DEPT_ROLE;

    alter table SYS_APPLICATION 
        add constraint FK_APP_TREE 
        foreign key (nodeId) 
        references SYS_TREE;

    alter table SYS_DEPARTMENT 
        add constraint FK_DEPT_TREE 
        foreign key (nodeId) 
        references SYS_TREE;

    alter table SYS_DEPT_ROLE 
        add constraint FK_DEPTROLE_DEPT 
        foreign key (deptId) 
        references SYS_DEPARTMENT;

    alter table SYS_DEPT_ROLE 
        add constraint FK_DEPTROLE_ROLE 
        foreign key (sysRoleId) 
        references SYS_ROLE;

    alter table SYS_FUNCTION 
        add constraint FK_FUN_APP 
        foreign key (appId) 
        references SYS_APPLICATION;

    alter table SYS_PERMISSION 
        add constraint FK_PERM_DEPTROLE 
        foreign key (roleId) 
        references SYS_DEPT_ROLE;

    alter table SYS_PERMISSION 
        add constraint FK_PERM_FUN
        foreign key (funcId) 
        references SYS_FUNCTION;

    alter table SYS_USER_ROLE 
        add constraint FK_USERROLE_ROLE 
        foreign key (roleId) 
        references SYS_DEPT_ROLE;

    alter table SYS_USER_ROLE 
        add constraint FK_USERROLE_USER 
        foreign key (userId) 
        references SYS_USER;

alter table SYS_APPLICATION
    add constraint SYS_UNIQ_APPLICATION
    unique (code);

alter table SYS_DEPARTMENT
    add constraint SYS_UNIQ_DEPARTMENT
    unique (code);

alter table SYS_TREE
    add constraint SYS_UNIQ_TREE
    unique (code);

alter table SYS_ROLE
    add constraint SYS_UNIQ_ROLE
    unique (code);

alter table SYS_USER
    add constraint SYS_UNIQ_USER
    unique (account);