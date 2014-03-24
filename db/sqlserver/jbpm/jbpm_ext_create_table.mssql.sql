
 create table EX_JBPM_ACTIVITY_INSTANCE ( 
    ID_ bigint  not null ,
    ACTORID_ nvarchar  (50) ,
    PROCESSINSTANCEID_ bigint ,
    TASKINSTANCEID_ bigint ,
    TASKNAME_ nvarchar  (50) ,
    TASKDESCRIPTION_ nvarchar  (200) ,
    OBJECTID_ nvarchar  (200) ,
    OBJECTVALUE_ nvarchar  (200) ,
    ROWID_ nvarchar  (50) ,
    TITLE_ nvarchar  (100) ,
    ISAGREE_ nvarchar  (20) ,
    CONTENT_ nvarchar  (1000) ,
    VARIABLE_ nvarchar  (1000) ,
    DATE_ datetime ,
    PREVIOUSACTORS_ nvarchar  (500) ,
    primary key (ID_) 
);


 create table EX_JBPM_CONFIGFILE ( 
    ID_ bigint  not null ,
    FILENAME_ nvarchar  (200) ,
    LASTMODIFIED_ bigint ,
    primary key (ID_) 
);


 create table EX_JBPM_EXFIELD ( 
    ID_ bigint  not null ,
    EXTENDID_ nvarchar  (100) ,
    NAME_ nvarchar  (50) ,
    VALUE_ nvarchar  (2000) ,
    EXTENSION_ bigint ,
    primary key (ID_) 
);


 create table EX_JBPM_EXPARAM ( 
    ID_ bigint  not null ,
    EXTENDID_ nvarchar  (100) ,
    NAME_ nvarchar  (50) ,
    TYPE_ nvarchar  (50) ,
    VALUE_ nvarchar  (2000) ,
    EXTENSION_ bigint ,
    PARAMCOLLECTIONINDEX_ integer ,
    primary key (ID_) 
);


 create table EX_JBPM_EXTENSION ( 
    ID_ bigint  not null ,
    EXTENDID_ nvarchar  (100) ,
    NAME_ nvarchar  (50) ,
    TYPE_ nvarchar  (50) ,
    DESCRIPTION_ nvarchar  (200) ,
    PROCESSNAME_ nvarchar  (50) ,
    TASKNAME_ nvarchar  (50) ,
    OBJECTID_ nvarchar  (50) ,
    OBJECTVALUE_ nvarchar  (255) ,
    LOCKED_ integer ,
    CREATEDATE_ datetime ,
    CREATEACTORID_ nvarchar  (255) ,
    UPDATEDATE_ datetime ,
    UPDATEACTORID_ nvarchar  (255) ,
    primary key (ID_) 
);

