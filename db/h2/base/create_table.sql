
--如果首次建表，要创建sequence


--重新创建hibernate的SEQ
--drop sequence hibernate_sequence;
CREATE sequence hibernate_sequence START WITH 10000 increment BY 1 ;


CREATE TABLE sys_access(
        roleid bigint NOT NULL,
        appid bigint NOT NULL,
        PRIMARY KEY (roleid, appid)
);


CREATE TABLE sys_application(
        id bigint IDENTITY NOT NULL,
        name varchar(255),
        appdesc varchar(255),
        url varchar(255),
        sort int,
        showmenu int,
        nodeid bigint NOT NULL,
        PRIMARY KEY (id)
);


CREATE TABLE sys_department(
        id bigint IDENTITY NOT NULL,
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
);

CREATE TABLE sys_dept_role(
        id bigint IDENTITY NOT NULL,
        grade int,
        code varchar(255),
        sort int,
        sysroleid bigint NOT NULL,
        deptid bigint NOT NULL,
        PRIMARY KEY (id)
);

CREATE TABLE sys_function(
        id bigint IDENTITY NOT NULL,
        name varchar(255),
        funcdesc varchar(255),
        funcmethod varchar(255),
        sort int,
        appid bigint NOT NULL,
        PRIMARY KEY (id)
);

CREATE TABLE sys_log(
        id bigint IDENTITY NOT NULL,
        account varchar(255),
        ip varchar(255),
        createtime timestamp,
        operate varchar(255),
        flag int,
        PRIMARY KEY (id)
);

CREATE TABLE sys_permission (
        roleid bigint NOT NULL,
        funcid bigint NOT NULL,
        PRIMARY KEY (roleid, funcid)
 );

CREATE TABLE sys_role(
        id bigint IDENTITY NOT NULL,
        name varchar(255),
        roledesc varchar(255),
        code varchar(255),
        sort int,
        PRIMARY KEY (id)
);


CREATE TABLE sys_tree (
        id bigint IDENTITY NOT NULL,
        parent bigint,
        name varchar(255),
        nodedesc varchar(255),
        sort int,
        code varchar(255),
        PRIMARY KEY (id)
);

CREATE TABLE sys_user (
        id bigint IDENTITY NOT NULL,
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
);

CREATE TABLE sys_user_role (
        id bigint IDENTITY not null,
        userid bigint not null,
        roleid bigint not null,
        authorized int default 0,
        authorizefrom bigint default 0,
        availdatestart timestamp,
        availdateend timestamp,
        processdescription varchar(255),
        PRIMARY KEY (id)
);

CREATE TABLE sys_dictory (
        id bigint IDENTITY  not null,
        typeId bigint,
        name varchar(50),
        dictDesc varchar(200),
        code varchar(50),
        sort int,
        blocked int,
        ext1 varchar(200),
        ext2 varchar(200),
        ext3 varchar(200),
        ext4 varchar(200),
        ext5 timestamp,
        ext6 timestamp,
        PRIMARY KEY (id)
);


CREATE TABLE sys_workcalendar (
        id bigint IDENTITY NOT NULL,
        freeday int,
        freemonth int,
        freeyear int,
        PRIMARY KEY (id)
);


CREATE TABLE sys_todo(
        id bigint NOT NULL,
        actorid varchar(255),
        alarm varchar(255),
        code varchar(255),
        content varchar(255),
        deptid bigint,
        deptname varchar(255),
        enableflag int,
        eventfrom varchar(255),
        eventto varchar(255),
        limitday int,
        xa int,
        xb int,
        link varchar(255),
        listlink varchar(255),
        linktype varchar(255),
        appid int,
        moduleid int,
        modulename varchar(255),
        news varchar(255),
        objectid varchar(255),
        objectvalue varchar(255),
        processname varchar(255),
        rolecode varchar(255),
        roleid bigint,
        tablename varchar(255),
        taskname varchar(255),
        title varchar(255),
        type varchar(255),
        SQL_ varchar(255),
        versionno bigint,
        PRIMARY KEY (id)
    );

CREATE TABLE sys_todo_instance(
        id bigint IDENTITY NOT NULL,
        actorid varchar(255),
        actorname varchar(255),
        title varchar(255),
        content varchar(255),
        provider varchar(255),
        link varchar(255),
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
        rowid varchar(255),
        todoid bigint,
        appid int,
        moduleid int,
        objectid varchar(255),
        objectvalue varchar(255),
        versionno bigint,
        PRIMARY KEY (id)
    );

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
    );

CREATE TABLE message(
        id bigint IDENTITY NOT NULL,
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
  );

CREATE TABLE mymenu(
        id bigint IDENTITY not null,
        userId bigint,
        title varchar(100),
        url varchar(200),
        sort int,
        crUser varchar(20),
        crDate timestamp,
        edUser varchar(20),
        edDate timestamp,
        PRIMARY KEY (id)
);

CREATE TABLE subjectcode(
        id bigint IDENTITY not null ,
        parent bigint,
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
        feeYear int ,
        sort int ,
        PRIMARY KEY (id)
    );


CREATE TABLE  Attachment (
	id bigint  IDENTITY not null ,
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


  CREATE TABLE  Audit (
	id bigint IDENTITY not null ,
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
        value_ varchar(255) not null,
        version_ int not null,
        primary key (name_)
);

commit;