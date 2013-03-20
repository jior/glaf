
 

CREATE TABLE sys_access(
        roleid NUMBER(19) NOT NULL,
        appid NUMBER(19) NOT NULL,
        PRIMARY KEY (roleid, appid)
);


CREATE TABLE sys_application(
        id NUMBER(19) NOT NULL,
        name NVARCHAR2(255),
        appdesc NVARCHAR2(255),
        url NVARCHAR2(255),
        sort INTEGER,
        showmenu INTEGER,
        nodeid NUMBER(19) NOT NULL,
        PRIMARY KEY (id)
);


CREATE TABLE sys_department(
        id NUMBER(19) NOT NULL,
        name NVARCHAR2(255),
        deptdesc NVARCHAR2(255),
        createtime TIMESTAMP(6),
        sort INTEGER,
        deptno NVARCHAR2(255),
        code NVARCHAR2(255),
        code2 NVARCHAR2(255),
        status INTEGER,
        fincode NVARCHAR2(255),
        nodeid NUMBER(19) NOT NULL,
        PRIMARY KEY (id)
);

CREATE TABLE sys_dept_role(
        id NUMBER(19) NOT NULL,
        grade INTEGER,
        code NVARCHAR2(255),
        sort INTEGER,
        sysroleid NUMBER(19) NOT NULL,
        deptid NUMBER(19) NOT NULL,
        PRIMARY KEY (id)
);

CREATE TABLE sys_function(
        id NUMBER(19) NOT NULL,
        name NVARCHAR2(255),
        funcdesc NVARCHAR2(255),
        funcmethod NVARCHAR2(255),
        sort INTEGER,
        appid NUMBER(19) NOT NULL,
        PRIMARY KEY (id)
);

CREATE TABLE sys_log(
        id NUMBER(19) NOT NULL,
        account NVARCHAR2(255),
        ip NVARCHAR2(255),
        createtime TIMESTAMP(6),
        operate NVARCHAR2(255),
        flag INTEGER,
        PRIMARY KEY (id)
);

CREATE TABLE sys_permission (
        roleid NUMBER(19) NOT NULL,
        funcid NUMBER(19) NOT NULL,
        PRIMARY KEY (roleid, funcid)
 );

CREATE TABLE sys_role(
        id NUMBER(19) NOT NULL,
        name NVARCHAR2(255),
        roledesc NVARCHAR2(255),
        code NVARCHAR2(255),
        sort INTEGER,
        PRIMARY KEY (id)
);


CREATE TABLE sys_tree (
        id NUMBER(19) NOT NULL,
        parent NUMBER(19),
        name NVARCHAR2(255),
        nodedesc NVARCHAR2(255),
	discriminator NVARCHAR2(1),
	treeId NVARCHAR2(200),
        sort INTEGER,
        code NVARCHAR2(255),
	icon NVARCHAR2(255),
	iconCls NVARCHAR2(255),
	url NVARCHAR2(255),
	locked INTEGER default 0,
        PRIMARY KEY (id)
);

CREATE TABLE sys_user (
        id NUMBER(19) NOT NULL,
        account NVARCHAR2(255),
        password NVARCHAR2(255),
        code NVARCHAR2(255),
        name NVARCHAR2(255),
        blocked INTEGER,
        createtime TIMESTAMP(6),
        lastlogintime TIMESTAMP(6),
        lastloginip NVARCHAR2(255),
        evection INTEGER,
        mobile NVARCHAR2(255),
        email NVARCHAR2(255),
        telephone NVARCHAR2(255),
        gender INTEGER,
        headship NVARCHAR2(255),
        usertype INTEGER,
        fax NVARCHAR2(255),
        accounttype INTEGER,
        dumpflag INTEGER,
        deptid NUMBER(19),
	adminFlag NVARCHAR2(1),
	superiorIds NVARCHAR2(200),
        PRIMARY KEY (id)
);

CREATE TABLE sys_user_role(
        id NUMBER(19) not null,
        userid NUMBER(19) default 0,
        roleid NUMBER(19) default 0,
        authorized INTEGER default 0,
        authorizefrom NUMBER(19) default 0,
        availdatestart TIMESTAMP(6),
        availdateend TIMESTAMP(6),
        processdescription NVARCHAR2(255),
        PRIMARY KEY (id)
);

CREATE TABLE sys_dictory (
        id NUMBER(19) not null,
        typeId NUMBER(19),
        name NVARCHAR2(50),
        dictDesc NVARCHAR2(200),
        code NVARCHAR2(50),
	value_ NVARCHAR2(2000),
        sort INTEGER,
        blocked INTEGER,
        ext1 NVARCHAR2(200),
        ext2 NVARCHAR2(200),
        ext3 NVARCHAR2(200),
        ext4 NVARCHAR2(200),
        ext5 TIMESTAMP(6),
        ext6 TIMESTAMP(6),
        PRIMARY KEY (id)
);


CREATE TABLE sys_workcalendar (
        id NUMBER(19) NOT NULL,
        freeday INTEGER,
        freemonth INTEGER,
        freeyear INTEGER,
        PRIMARY KEY (id)
);


CREATE TABLE sys_todo(
        id NUMBER(19) NOT NULL,
        code NVARCHAR2(255),
        content NVARCHAR2(255),
        deptid NUMBER(19),
        deptname NVARCHAR2(255),
        enableflag INTEGER,
        limitday INTEGER,
        xa INTEGER,
        xb INTEGER,
        link NVARCHAR2(255),
        listlink NVARCHAR2(255),
	allListLink NVARCHAR2(255),
        linktype NVARCHAR2(255),
        appid NUMBER(19),
        moduleid NUMBER(19),
        modulename NVARCHAR2(255),
        objectid NVARCHAR2(255),
        objectvalue NVARCHAR2(255),
        processname NVARCHAR2(255),
        rolecode NVARCHAR2(255),
        roleid NUMBER(19),
        taskname NVARCHAR2(255),
        title NVARCHAR2(255),
        type NVARCHAR2(50),
	provider NVARCHAR2(50),
        sql_ NVARCHAR2(2000),
	sortno INTEGER,
        versionno NUMBER(19),
        PRIMARY KEY (id)
    );

CREATE TABLE sys_todo_instance(
        id NUMBER(19) NOT NULL,
        actorid NVARCHAR2(255),
        actorname NVARCHAR2(255),
        title NVARCHAR2(255),
        content NVARCHAR2(255),
        provider NVARCHAR2(255),
        link NVARCHAR2(255),
        linktype NVARCHAR2(255),
        createdate TIMESTAMP(6),
        startdate TIMESTAMP(6),
        enddate TIMESTAMP(6),
        alarmdate TIMESTAMP(6),
        pastduedate TIMESTAMP(6),
        taskinstanceid NVARCHAR2(255),
        processinstanceid NVARCHAR2(255),
        deptid NUMBER(19),
        deptname NVARCHAR2(255),
        roleid NUMBER(19),
        rolecode NVARCHAR2(255),
        rowid NVARCHAR2(255),
        todoid NUMBER(19),
        appid NUMBER(19),
        moduleid NUMBER(19),
        objectid NVARCHAR2(255),
        objectvalue NVARCHAR2(255),
        versionno NUMBER(19),
        PRIMARY KEY (id)
    );

  CREATE TABLE sys_scheduler (
        id NVARCHAR2(50)  NOT NULL,
	autoStartup INTEGER,
        createBy NVARCHAR2(255),
        createDate TIMESTAMP(6),
	title NVARCHAR2(200),
        content NVARCHAR2(500),
	startDate TIMESTAMP(6),
        endDate TIMESTAMP(6),
        expression_ NVARCHAR2(500),
        jobClass NVARCHAR2(200),
        locked_ INTEGER,
        priority_ INTEGER,
        repeatCount INTEGER,
        repeatInterval INTEGER,
        startDelay INTEGER,
        startup_ INTEGER,
        taskName NVARCHAR2(200),
        taskType NVARCHAR2(50),
        threadSize INTEGER,
	attribute_ NVARCHAR2(500),
        PRIMARY KEY (id)
    );

CREATE TABLE message(
        id NUMBER(19) NOT NULL,
        type INTEGER,
        sysType INTEGER,
        sender NUMBER(19),
        recver NUMBER(19),
        recverList NVARCHAR2(2000) ,
        title NVARCHAR2(500) ,
        content NVARCHAR2(2000) ,
        createDate TIMESTAMP(6),
        readed INTEGER,
        category INTEGER,
        crUser NVARCHAR2(20),
        crDate TIMESTAMP(6),
        edUser NVARCHAR2(20),
        edDate TIMESTAMP(6),
        PRIMARY KEY (id)
  );

CREATE TABLE mymenu(
        id NUMBER(19) not null,
        userId NUMBER(19),
        title NVARCHAR2(100),
        url NVARCHAR2(200),
        sort INTEGER,
        crUser NVARCHAR2(20),
        crDate TIMESTAMP(6),
        edUser NVARCHAR2(20),
        edDate TIMESTAMP(6),
        PRIMARY KEY (id)
);

CREATE TABLE subjectcode(
        id NUMBER(19) not null ,
        parent NUMBER(19),
        subjectCode NVARCHAR2(20),
        subjectName NVARCHAR2(100),
        feeSum NUMBER(*,10) ,
        month1 NUMBER(*,10) ,
        month2 NUMBER(*,10) ,
        month3 NUMBER(*,10) ,
        month4 NUMBER(*,10) ,
        month5 NUMBER(*,10) ,
        month6 NUMBER(*,10) ,
        month7 NUMBER(*,10) ,
        month8 NUMBER(*,10) ,
        month9 NUMBER(*,10) ,
        month10 NUMBER(*,10) ,
        month11 NUMBER(*,10) ,
        month12 NUMBER(*,10) ,
        feeYear INTEGER ,
        sort INTEGER ,
        PRIMARY KEY (id)
    );


CREATE TABLE  Attachment (
	id NUMBER(19)  not null ,
	referId NUMBER(19)  ,
	referType INTEGER  ,
	name NVARCHAR2 (100)   ,
	url NVARCHAR2 (200)   ,
	createDate TIMESTAMP(6)  ,
	createId NUMBER(19)  ,
	crUser NVARCHAR2 (20)   ,
	crDate TIMESTAMP(6)  ,
	edUser NVARCHAR2 (20)   ,
	edDate TIMESTAMP(6) ,
	PRIMARY KEY (id)
);


  CREATE TABLE  Audit (
	id NUMBER(19) not null ,
	referId NUMBER(19) ,
	referType INTEGER ,
	deptId NUMBER(19) ,
	deptName NVARCHAR2 (50) ,
	headship NVARCHAR2 (100) ,
	leaderName NVARCHAR2 (100) ,
	leaderId NUMBER(19) ,
	createDate TIMESTAMP(6) ,
	memo NVARCHAR2 (2000) ,
	flag INTEGER ,
	crUser NVARCHAR2 (20) ,
	crDate TIMESTAMP(6) ,
	edUser NVARCHAR2 (20) ,
	edDate TIMESTAMP(6) , 
	PRIMARY KEY (id)
);

create table sys_dbid(
        name_ NVARCHAR2(50)  not null,
        value_ NVARCHAR2(255) not null,
        version_ INTEGER not null,
        primary key (name_)
);

create table sys_agent (
        ID_ NVARCHAR2(50)  not null,
        AGENTTYPE_ INTEGER,
        ASSIGNFROM_ NVARCHAR2(255) ,
        ASSIGNTO_ NVARCHAR2(255) ,
        CREATEDATE_ TIMESTAMP(6),
        ENDDATE_ TIMESTAMP(6),
        LOCKED_ INTEGER,
        OBJECTID_ NVARCHAR2(255) ,
        OBJECTVALUE_ NVARCHAR2(255) ,
        PROCESSNAME_ NVARCHAR2(255) ,
        SERVICEKEY_ NVARCHAR2(50) ,
        STARTDATE_ TIMESTAMP(6),
        TASKNAME_ NVARCHAR2(255) ,
        PRIMARY KEY (ID_)
 );

CREATE TABLE sys_property (
        id_ NVARCHAR2(50) NOT NULL,
        category_ NVARCHAR2(200),
        description_ NVARCHAR2(500),
        initvalue_ NVARCHAR2(1000),
        locked_ INTEGER,
        name_ NVARCHAR2(50),
        title_ NVARCHAR2(200),
        type_ NVARCHAR2(50),
        value_ NVARCHAR2(1000),
        PRIMARY KEY (id_)
);
