
 
 
CREATE TABLE sys_access(
        roleid numeric(19,0)  NOT NULL,
        appid numeric(19,0) NOT NULL,
        PRIMARY KEY (roleid, appid)
);


CREATE TABLE sys_application(
        id numeric(19,0) identity not null,
        name nvarchar(255),
        appdesc nvarchar(255),
        url nvarchar(255),
        sort int,
        showmenu int,
        nodeid numeric(19,0) NOT NULL,
        PRIMARY KEY (id)
);


CREATE TABLE sys_department(
        id numeric(19,0) identity not null,
        name nvarchar(255),
        deptdesc nvarchar(255),
        createtime datetime,
        sort int,
        deptno nvarchar(255),
        code nvarchar(255),
        code2 nvarchar(255),
        status int,
        fincode nvarchar(255),
        nodeid numeric(19,0) NOT NULL,
        PRIMARY KEY (id)
);

CREATE TABLE sys_dept_role(
        id numeric(19,0) identity not null,
        grade int,
        code nvarchar(255),
        sort int,
        sysroleid numeric(19,0) NOT NULL,
        deptid numeric(19,0) NOT NULL,
        PRIMARY KEY (id)
);

CREATE TABLE sys_function(
        id numeric(19,0) identity not null,
        name nvarchar(255),
        funcdesc nvarchar(255),
        funcmethod nvarchar(255),
        sort int,
        appid numeric(19,0) NOT NULL,
        PRIMARY KEY (id)
);

CREATE TABLE sys_log(
        id numeric(19,0) identity not null,
        account nvarchar(255),
        ip nvarchar(255),
        createtime datetime,
        operate nvarchar(255),
        flag int,
        PRIMARY KEY (id)
);

CREATE TABLE sys_permission (
        roleid numeric(19,0) NOT NULL,
        funcid numeric(19,0) NOT NULL,
        PRIMARY KEY (roleid, funcid)
 );

CREATE TABLE sys_role(
        id numeric(19,0) identity not null,
        name nvarchar(255),
        roledesc nvarchar(255),
        code nvarchar(255),
        sort int,
        PRIMARY KEY (id)
);


CREATE TABLE sys_tree (
        id numeric(19,0) identity not null,
        parent numeric(19,0),
        name nvarchar(255),
        nodedesc nvarchar(255),
        sort int,
        code nvarchar(255),
        PRIMARY KEY (id)
);

CREATE TABLE sys_user (
        id numeric(19,0) identity not null,
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
        deptid numeric(19,0),
	adminFlag nvarchar(1),
        PRIMARY KEY (id)
);

CREATE TABLE sys_user_role(
        id numeric(19,0) identity not null ,
        userid numeric(19,0) not null,
        roleid numeric(19,0) not null,
        authorized int default 0,
        authorizefrom numeric(19,0) default 0,
        availdatestart datetime,
        availdateend datetime,
        processdescription nvarchar(255),
        PRIMARY KEY (id)
);

CREATE TABLE sys_dictory (
        id numeric(19,0) identity not null,
        typeId numeric(19,0),
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
        id numeric(19,0) identity not null,
        freeday int,
        freemonth int,
        freeyear int,
        PRIMARY KEY (id)
);

CREATE TABLE sys_membership(
        id_ numeric(19,0) identity not null,
        actorid_ nvarchar(255),
        superiorid_ nvarchar(255),
        modifydate_ datetime,
        attribute_ nvarchar(255),
        PRIMARY KEY (id_)
);

CREATE TABLE sys_todo(
        id numeric(19,0) NOT NULL,
        actorid nvarchar(255),
        alarm nvarchar(255),
        code nvarchar(255),
        content nvarchar(255),
        deptid numeric(19,0),
        deptname nvarchar(255),
        enableflag int,
        eventfrom nvarchar(255),
        eventto nvarchar(255),
        limitday int,
        xa int,
        xb int,
        link nvarchar(255),
        listlink nvarchar(255),
        linktype nvarchar(255),
        appid int,
        moduleid int,
        modulename nvarchar(255),
        news nvarchar(255),
        objectid nvarchar(255),
        objectvalue nvarchar(255),
        processname nvarchar(255),
        rolecode nvarchar(255),
        roleid numeric(19,0),
        tablename nvarchar(255),
        taskname nvarchar(255),
        title nvarchar(255),
        type nvarchar(255),
        SQL_ nvarchar(255),
        versionno numeric(19,0),
        PRIMARY KEY (id)
    );

CREATE TABLE sys_todo_instance(
        id numeric(19,0) identity not null,
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
        deptid numeric(19,0),
        deptname nvarchar(255),
        roleid numeric(19,0),
        rolecode nvarchar(255),
        rowid nvarchar(255),
        todoid numeric(19,0),
        appid int,
        moduleid int,
        objectid nvarchar(255),
        objectvalue nvarchar(255),
        versionno numeric(19,0),
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
        id numeric(19,0) identity not null,
        type int,
        sysType int,
        sender numeric(19,0),
        recver numeric(19,0),
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
        id numeric(19,0) identity not null,
        userId numeric(19,0),
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
        id numeric(19,0) identity not null ,
        parent numeric(19,0),
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
	id numeric(19,0)  identity not null ,
	referId numeric(19,0)  ,
	referType int  ,
	name nvarchar (100)   ,
	url nvarchar (200)   ,
	createDate datetime  ,
	createId numeric(19,0)  ,
	crUser nvarchar (20)   ,
	crDate datetime  ,
	edUser nvarchar (20)   ,
	edDate datetime ,
	PRIMARY KEY (id)
);


  CREATE TABLE  Audit (
	id numeric(19,0) identity not null,
	referId numeric(19,0) ,
	referType int ,
	deptId numeric(19,0) ,
	deptName nvarchar (50) ,
	headship nvarchar (100) ,
	leaderName nvarchar (100) ,
	leaderId numeric(19,0) ,
	createDate datetime ,
	memo text ,
	flag int ,
	crUser nvarchar (20) ,
	crDate datetime ,
	edUser nvarchar (20) ,
	edDate datetime , 
	PRIMARY KEY (id)
);