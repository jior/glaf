
 
 
CREATE TABLE sys_access(
        roleid bigint  NOT NULL,
        appid bigint NOT NULL,
        PRIMARY KEY (roleid, appid)
);


CREATE TABLE sys_application(
        id bigint identity not null,
        name nvarchar(255),
        appdesc nvarchar(255),
        url nvarchar(255),
        sort int,
        showmenu int,
        nodeid bigint NOT NULL,
        PRIMARY KEY (id)
);


CREATE TABLE sys_department(
        id bigint identity not null,
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
        id bigint identity not null,
        grade int,
        code nvarchar(255),
        sort int,
        sysroleid bigint NOT NULL,
        deptid bigint NOT NULL,
        PRIMARY KEY (id)
);

CREATE TABLE sys_function(
        id bigint identity not null,
        name nvarchar(255),
        funcdesc nvarchar(255),
        funcmethod nvarchar(255),
        sort int,
        appid bigint NOT NULL,
        PRIMARY KEY (id)
);

CREATE TABLE sys_log(
        id bigint identity not null,
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
        id bigint identity not null,
        name nvarchar(255),
        roledesc nvarchar(255),
        code nvarchar(255),
        sort int,
        PRIMARY KEY (id)
);


CREATE TABLE sys_tree (
        id bigint identity not null,
        parent bigint,
        name nvarchar(255),
        nodedesc nvarchar(255),
        sort int,
        code nvarchar(255),
        PRIMARY KEY (id)
);

CREATE TABLE sys_user (
        id bigint identity not null,
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
        id bigint identity not null ,
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
        id bigint identity not null,
        typeId bigint,
        name nvarchar(50),
        dictDesc nvarchar(200),
        code nvarchar(50),
        sort int,
        blocked int,
        ext1 nvarchar(200),
        ext2 nvarchar(200),
        ext3 nvarchar(200),
        ext4 nvarchar(200),
        ext5 datetime,
        ext6 datetime,
        PRIMARY KEY (id)
);


CREATE TABLE sys_workcalendar (
        id bigint identity not null,
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
        versionno bigint,
        PRIMARY KEY (id)
    );

CREATE TABLE sys_todo_instance(
        id bigint identity not null,
        actorid nvarchar(255),
        actorname nvarchar(255),
        title nvarchar(255),
        content nvarchar(255),
        provider nvarchar(255),
        link nvarchar(255),
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
        rowid nvarchar(255),
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
        id bigint identity not null,
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
        id bigint identity not null,
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
        id bigint identity not null ,
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
	id bigint  identity not null ,
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


  CREATE TABLE  Audit (
	id bigint identity not null,
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