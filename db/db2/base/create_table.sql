 

CREATE TABLE sys_access(
        roleid bigint not null,
        appid bigint not null,
        PRIMARY KEY (roleid, appid)
);


CREATE TABLE sys_application(
        id bigint not null,
        name varchar(255),
        appdesc varchar(255),
        url varchar(255),
        sort integer,
        showmenu integer,
        nodeid bigint not null,
        PRIMARY KEY (id)
);


CREATE TABLE sys_department(
        id bigint not null,
        name varchar(255),
        deptdesc varchar(255),
        createtime timestamp,
        sort integer,
        deptno varchar(255),
        code varchar(255),
        code2 varchar(255),
        status integer,
        fincode varchar(255),
        nodeid bigint not null,
        PRIMARY KEY (id)
);

CREATE TABLE sys_dept_role(
        id bigint not null,
        grade integer,
        code varchar(255),
        sort integer,
        sysroleid bigint not null,
        deptid bigint not null,
        PRIMARY KEY (id)
);

CREATE TABLE sys_function(
        id bigint not null,
        name varchar(255),
        funcdesc varchar(255),
        funcmethod varchar(255),
        sort integer,
        appid bigint  not null,
        PRIMARY KEY (id)
);

CREATE TABLE sys_log(
        id bigint not null,
        account varchar(255),
        ip varchar(255),
        createtime timestamp,
        operate varchar(255),
        flag integer,
        PRIMARY KEY (id)
);

CREATE TABLE sys_permission (
        roleid bigint  not null,
        funcid bigint  not null,
        PRIMARY KEY (roleid, funcid)
 );

CREATE TABLE sys_role(
        id bigint not null,
        name varchar(255),
        roledesc varchar(255),
        code varchar(255),
        sort integer,
        PRIMARY KEY (id)
);


CREATE TABLE sys_tree (
        id bigint not null,
        parent bigint,
        name varchar(255),
        nodedesc varchar(255),
	discriminator varchar(1),
	treeId varchar(200),
        sort integer,
        code varchar(255),
	icon varchar(255),
	iconCls varchar(255),
	url varchar(255),
	locked integer default 0,
        PRIMARY KEY (id)
);

CREATE TABLE sys_user (
	id bigint not null,
        account varchar(255),
        password varchar(255),
        code varchar(255),
        name varchar(255),
        blocked integer,
        createtime timestamp,
        lastlogintime timestamp,
        lastloginip varchar(255),
        evection integer,
        mobile varchar(255),
        email varchar(255),
        telephone varchar(255),
        gender integer,
        headship varchar(255),
        usertype integer,
        fax varchar(255),
        accounttype integer,
        dumpflag integer,
        deptid bigint,
	adminFlag varchar(1),
	superiorIds varchar(200),
        PRIMARY KEY (id)
);



CREATE TABLE sys_user_role(
        id bigint not null,
        userid bigint default 0,
        roleid bigint default 0,
        authorized integer default 0,
        authorizefrom bigint default 0,
        availdatestart timestamp,
        availdateend timestamp,
        processdescription varchar(255),
        PRIMARY KEY (id)
);

CREATE TABLE sys_dictory (
        id bigint not null,
        typeId bigint,
        name varchar(50),
        dictDesc varchar(200),
        code varchar(50),
	value_ varchar(2000),
        sort integer,
        blocked integer,
        ext1 varchar(200),
        ext2 varchar(200),
        ext3 varchar(200),
        ext4 varchar(200),
        ext5 timestamp,
        ext6 timestamp,
        PRIMARY KEY (id)
);


CREATE TABLE sys_workcalendar (
        id bigint not null,
        freeday integer,
        freemonth integer,
        freeyear integer,
        PRIMARY KEY (id)
);


CREATE TABLE sys_todo(
        id bigint not null,
        code varchar(255),
        content varchar(255),
        deptid bigint,
        deptname varchar(255),
        enableflag integer,
        limitday integer,
        xa integer,
        xb integer,
        link varchar(255),
        listlink varchar(255),
	allListLink varchar(255),
        linktype varchar(255),
        appid bigint,
        moduleid bigint,
        modulename varchar(255),
        objectid varchar(255),
        objectvalue varchar(255),
        processname varchar(255),
        rolecode varchar(255),
        roleid bigint,
        taskname varchar(255),
        title varchar(255),
        type varchar(50),
	provider varchar(50),
        sql_ varchar(2000),
	sortno integer,
        versionno bigint,
        PRIMARY KEY (id)
    );

CREATE TABLE sys_todo_instance(
        id bigint not null,
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
        appid bigint,
        moduleid bigint,
        objectid varchar(255),
        objectvalue varchar(255),
        versionno bigint,
        PRIMARY KEY (id)
    );

  CREATE TABLE sys_scheduler (
        id varchar(50) not null,
	autoStartup integer,
        createBy varchar(255),
        createDate timestamp,
	title varchar(200),
        content varchar(500),
	startDate timestamp,
        endDate timestamp,
        expression_ varchar(500),
        jobClass varchar(200),
        locked_ integer,
        priority_ integer,
        repeatCount integer,
        repeatInterval integer,
        startDelay integer,
        startup_ integer,
        taskName varchar(200),
        taskType varchar(50),
        threadSize integer,
	attribute_ varchar(500),
        PRIMARY KEY (id)
    );

CREATE TABLE message(
        id bigint not null,
        type integer,
        sysType integer,
        sender bigint,
        recver bigint,
        recverList varchar(2000) ,
        title varchar(500) ,
        content varchar(2000) ,
        createDate timestamp,
        readed integer,
        category integer,
        crUser varchar(20),
        crDate timestamp,
        edUser varchar(20),
        edDate timestamp,
        PRIMARY KEY (id)
  );

CREATE TABLE mymenu(
        id bigint not null,
        userId bigint,
        title varchar(100),
        url varchar(200),
        sort integer,
        crUser varchar(20),
        crDate timestamp,
        edUser varchar(20),
        edDate timestamp,
        PRIMARY KEY (id)
);

CREATE TABLE subjectcode(
        id bigint not null,
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
        feeYear integer ,
        sort integer ,
        PRIMARY KEY (id)
    );


CREATE TABLE  Attachment (
	id bigint not null,
	referId bigint  ,
	referType integer  ,
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
	id bigint not null,
	referId bigint ,
	referType integer ,
	deptId bigint ,
	deptName varchar (50) ,
	headship varchar (100) ,
	leaderName varchar (100) ,
	leaderId bigint ,
	createDate timestamp ,
	memo varchar (2000) ,
	flag integer ,
	crUser varchar (20) ,
	crDate timestamp ,
	edUser varchar (20) ,
	edDate timestamp , 
	PRIMARY KEY (id)
);

create table sys_dbid(
        name_ varchar(50)  not null,
        value_ varchar(255) not null,
        version_ integer not null,
        primary key (name_)
);

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
 );


 
CREATE TABLE sys_property (
        id_ varchar(50) not null,
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