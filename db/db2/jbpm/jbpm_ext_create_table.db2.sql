
 create table EX_JBPM_ACTIVITY_INSTANCE ( 
    ID_ bigint  not null ,
    ACTORID_ varchar  (50) ,
    PROCESSINSTANCEID_ bigint ,
    TASKINSTANCEID_ bigint ,
    TASKNAME_ varchar  (50) ,
    TASKDESCRIPTION_ varchar  (200) ,
    OBJECTID_ varchar  (200) ,
    OBJECTVALUE_ varchar  (200) ,
    ROWID_ varchar  (50) ,
    TITLE_ varchar  (100) ,
    ISAGREE_ varchar  (20) ,
    CONTENT_ varchar  (1000) ,
    VARIABLE_ varchar  (1000) ,
    DATE_ timestamp ,
    PREVIOUSACTORS_ varchar  (500) ,
    primary key (ID_) 
);


 create table EX_JBPM_CONFIGFILE ( 
    ID_ bigint  not null ,
    FILENAME_ varchar  (200) ,
    LASTMODIFIED_ bigint ,
    primary key (ID_) 
);


 create table EX_JBPM_EXFIELD ( 
    ID_ bigint  not null ,
    EXTENDID_ varchar  (100) ,
    NAME_ varchar  (50) ,
    VALUE_ varchar  (2000) ,
    EXTENSION_ bigint ,
    primary key (ID_) 
);


 create table EX_JBPM_EXPARAM ( 
    ID_ bigint  not null ,
    EXTENDID_ varchar  (100) ,
    NAME_ varchar  (50) ,
    TYPE_ varchar  (50) ,
    VALUE_ varchar  (2000) ,
    EXTENSION_ bigint ,
    PARAMCOLLECTIONINDEX_ integer ,
    primary key (ID_) 
);


 create table EX_JBPM_EXTENSION ( 
    ID_ bigint  not null ,
    EXTENDID_ varchar  (100) ,
    NAME_ varchar  (50) ,
    TYPE_ varchar  (50) ,
    DESCRIPTION_ varchar  (200) ,
    PROCESSNAME_ varchar  (50) ,
    TASKNAME_ varchar  (50) ,
    OBJECTID_ varchar  (50) ,
    OBJECTVALUE_ varchar  (255) ,
    LOCKED_ integer ,
    CREATEDATE_ timestamp ,
    CREATEACTORID_ varchar  (255) ,
    UPDATEDATE_ timestamp ,
    UPDATEACTORID_ varchar  (255) ,
    primary key (ID_) 
);

