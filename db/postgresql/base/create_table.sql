
--如果首次建表，要创建sequence


--重新创建hibernate的SEQ
--drop sequence hibernate_sequence;
CREATE sequence hibernate_sequence START WITH 10000 increment BY 1 no maxvalue no minvalue cache 20 no cycle;


CREATE TABLE sys_access(
        roleid int8 NOT NULL,
        appid int8 NOT NULL,
        PRIMARY KEY (roleid, appid)
);


CREATE TABLE sys_application(
        id int8 NOT NULL,
        name varchar(255),
        appdesc varchar(255),
        url varchar(255),
        sort int4,
        showmenu int4,
        nodeid int8 NOT NULL,
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
        sort int4,
        code varchar(255),
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
        PRIMARY KEY (id)
);

CREATE TABLE sys_user_role(
        id int8 default nextval('hibernate_sequence') not null,
        userid int8 not null,
        roleid int8 not null,
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
        sort int4,
        blocked int4,
        ext1 varchar(200),
        ext2 varchar(200),
        ext3 varchar(200),
        ext4 varchar(200),
        ext5 timestamp,
        ext6 timestamp,
        PRIMARY KEY (id)
);


CREATE TABLE sys_workcalendar (
        id int8 NOT NULL,
        freeday int4,
        freemonth int4,
        freeyear int4,
        PRIMARY KEY (id)
);

CREATE TABLE sys_membership(
        id_ int8 NOT NULL,
        actorid_ varchar(255),
        superiorid_ varchar(255),
        modifydate_ timestamp,
        attribute_ varchar(255),
        PRIMARY KEY (id_)
);

CREATE TABLE sys_todo(
        id int8 NOT NULL,
        actorid varchar(255),
        alarm varchar(255),
        code varchar(255),
        content varchar(255),
        deptid int8,
        deptname varchar(255),
        enableflag int4,
        eventfrom varchar(255),
        eventto varchar(255),
        limitday int4,
        xa int4,
        xb int4,
        link varchar(255),
        listlink varchar(255),
        linktype varchar(255),
        appid int4,
        moduleid int4,
        modulename varchar(255),
        news varchar(255),
        objectid varchar(255),
        objectvalue varchar(255),
        processname varchar(255),
        rolecode varchar(255),
        roleid int8,
        tablename varchar(255),
        taskname varchar(255),
        title varchar(255),
        type varchar(255),
        SQL_ varchar(255),
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
        link varchar(255),
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
        rowid varchar(255),
        todoid int8,
        appid int4,
        moduleid int4,
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


  CREATE TABLE  Audit (
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