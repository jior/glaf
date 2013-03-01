 
create table ${tableName} (
        ID_ varchar(50),
        TASKID_ varchar(50),
        MAILTO_ varchar(100),
        ACCOUNTID_ varchar(50),
        SENDDATE_ datetime,
        SENDSTATUS_  int,
        RETRYTIMES_  int,
        RECEIVEIP_ varchar(50),
        RECEIVEDATE_ datetime,
        RECEIVESTATUS_  int,
        BROWSER_ varchar(50),
        CONTENTTYPE_ varchar(500),
        CLIENTOS_ varchar(50),
	    LASTMODIFIED_ numeric(19,0),
        primary key (ID_)
) ;

