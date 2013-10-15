 
create table ${tableName} (
        ID_ varchar(50),
        TASKID_ varchar(50),
        MAILTO_ varchar(100),
        ACCOUNTID_ varchar(50),
        SENDDATE_ timestamp,
        SENDSTATUS_  integer,
        RETRYTIMES_  integer,
        RECEIVEIP_ varchar(50),
        RECEIVEDATE_ timestamp,
        RECEIVESTATUS_  integer,
        BROWSER_ varchar(50),
        CONTENTTYPE_ varchar(500),
        CLIENTOS_ varchar(50),        
	    LASTMODIFIED_ bigint,
        primary key (ID_)
) ;

