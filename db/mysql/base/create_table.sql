
--如果首次建表，要创建sequence


--重新创建hibernate的SEQ
--drop sequence hibernate_sequence;
--CREATE sequence hibernate_sequence START WITH 10000 increment BY 1;

CREATE TABLE sys_access(
        roleid bigint NOT NULL,
        appid bigint NOT NULL,
        PRIMARY KEY (roleid, appid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


CREATE TABLE sys_application(
        id bigint NOT NULL auto_increment 10000,
        name varchar(255),
        appdesc varchar(255),
        url varchar(255),
        sort int,
        showmenu int,
        nodeid bigint NOT NULL,
        PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


CREATE TABLE sys_department(
        id bigint NOT NULL auto_increment,
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
        id bigint NOT NULL auto_increment,
        grade int,
        code varchar(255),
        sort int,
        sysroleid bigint NOT NULL,
        deptid bigint NOT NULL,
        PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

CREATE TABLE sys_function(
        id bigint NOT NULL auto_increment,
        name varchar(255),
        funcdesc varchar(255),
        funcmethod varchar(255),
        sort int,
        appid bigint NOT NULL,
        PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

CREATE TABLE sys_log(
        id bigint NOT NULL auto_increment,
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
        id bigint NOT NULL auto_increment,
        name varchar(255),
        roledesc varchar(255),
        code varchar(255),
        sort int,
        PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


CREATE TABLE sys_tree (
        id bigint NOT NULL auto_increment,
        parent bigint,
        name varchar(255),
        nodedesc varchar(255),
        sort int,
        code varchar(255),
        PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

CREATE TABLE sys_user (
        id bigint NOT NULL auto_increment,
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
        PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

CREATE TABLE sys_user_role(
        id bigint not null auto_increment,
        userid bigint not null default 0,
        roleid bigint not null default 0,
        authorized int default 0,
        authorizefrom bigint default 0,
        availdatestart timestamp,
        availdateend timestamp,
        processdescription varchar(255),
        PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

CREATE TABLE sys_dictory (
        id bigint not null auto_increment,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


CREATE TABLE sys_workcalendar (
        id bigint NOT NULL auto_increment,
        freeday int,
        freemonth int,
        freeyear int,
        PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

CREATE TABLE sys_membership(
        id_ bigint NOT NULL auto_increment,
        actorid_ varchar(255),
        superiorid_ varchar(255),
        modifydate_ timestamp,
        attribute_ varchar(255),
        PRIMARY KEY (id_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

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
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

CREATE TABLE sys_todo_instance(
        id bigint NOT NULL auto_increment,
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
        id bigint NOT NULL auto_increment,
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
        id bigint not null auto_increment,
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
        id bigint not null auto_increment,
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
	id bigint  not null auto_increment,
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


  CREATE TABLE  Audit (
	id bigint not null auto_increment,
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