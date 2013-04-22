
CREATE TABLE UI_LAYOUT(
        ID_ varchar(50) NOT NULL,
        ACTORID_ varchar(255),
        COLUMNSTYLE_ varchar(255),
        COLUMNS_ integer,
        CREATEDATE_ timestamp,
        DATAINDEX_ integer,
        NAME_ varchar(50) NOT NULL,
        PANELS_ varchar(255),
        SPACESTYLE_ varchar(50),
        TEMPLATEID_ varchar(50),
        TITLE_ varchar(255),
        PRIMARY KEY (ID_)
 );


 CREATE TABLE UI_PANEL (
        ID_ varchar(50) NOT NULL,
        ACTORID_ varchar(255),
        CLOSE_ integer,
        COLLAPSIBLE_ integer,
        COLOR_ varchar(50),
        COLUMNINDEX_ integer,
        CONTENT_ clob,
        CREATEDATE_ timestamp,
        HEIGHT_ integer,
        ICON_ varchar(255),
        LINK_ varchar(200),
        LOCKED_ integer,
        MODULEID_ varchar(255),
        MODULENAME_ varchar(255),
        MORELINK_ varchar(200),
        NAME_ varchar(50) NOT NULL,
        QUERYID_ varchar(200),
        RESIZE_ integer,
        STYLE_ varchar(200),
        TITLE_ varchar(255),
        TYPE_ varchar(20),
        WIDTH_ integer,
        PRIMARY KEY (ID_)
);


CREATE TABLE UI_PANELINSTANCE (
        ID_ varchar(50) NOT NULL,
        NAME_ varchar(50),
        PANEL_ varchar(50),
        USERPANEL_ varchar(50),
        PRIMARY KEY (ID_)
);

CREATE TABLE UI_SKIN (
        ID_ varchar(50) NOT NULL,
        CREATEDATE_ timestamp,
        DESCRIPTION_ varchar(500),
        IMAGE_ varchar(255),
        LOCKED_ integer NOT NULL,
        NAME_ varchar(50) NOT NULL,
        STYLECLASS_ varchar(255),
        TITLE_ varchar(255),
        PRIMARY KEY (ID_)
);

CREATE TABLE UI_SKININSTANCE (
        ID_ varchar(50) NOT NULL,
        ACTORID_ varchar(255),
        SKIN_ varchar(50),
        PRIMARY KEY (ID_)
);

CREATE TABLE UI_USERPANEL (
        ID_ varchar(50) NOT NULL,
        ACTORID_ varchar(50),
        CREATEDATE_ timestamp,
        LAYOUTNAME_ varchar(20),
        REFRESHSECONDS_ integer,
        LAYOUT_ varchar(50),
        PRIMARY KEY (ID_)
);

CREATE TABLE UI_USERPORTAL (
        ID_ varchar(50) NOT NULL,
        ACTORID_ varchar(50),
        COLUMNINDEX_ integer,
        CREATEDATE_ timestamp,
        PANELID_ varchar(50),
        POSITION_ integer,
        PRIMARY KEY (ID_)
);

