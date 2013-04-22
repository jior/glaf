
CREATE TABLE UI_LAYOUT(
        ID_ nvarchar(50) NOT NULL,
        ACTORID_ nvarchar(255),
        COLUMNSTYLE_ nvarchar(255),
        COLUMNS_ int,
        CREATEDATE_ datetime,
        DATAINDEX_ int,
        NAME_ nvarchar(50) NOT NULL,
        PANELS_ nvarchar(255),
        SPACESTYLE_ nvarchar(50),
        TEMPLATEID_ nvarchar(50),
        TITLE_ nvarchar(255),
        PRIMARY KEY (ID_)
 );


 CREATE TABLE UI_PANEL (
        ID_ nvarchar(50) NOT NULL,
        ACTORID_ nvarchar(255),
        CLOSE_ int,
        COLLAPSIBLE_ int,
        COLOR_ nvarchar(50),
        COLUMNINDEX_ int,
        CONTENT_ text,
        CREATEDATE_ datetime,
        HEIGHT_ int,
        ICON_ nvarchar(255),
        LINK_ nvarchar(200),
        LOCKED_ int,
        MODULEID_ nvarchar(255),
        MODULENAME_ nvarchar(255),
        MORELINK_ nvarchar(200),
        NAME_ nvarchar(50) NOT NULL,
        QUERYID_ nvarchar(200),
        RESIZE_ int,
        STYLE_ nvarchar(200),
        TITLE_ nvarchar(255),
        TYPE_ nvarchar(20),
        WIDTH_ int,
        PRIMARY KEY (ID_)
);


CREATE TABLE UI_PANELINSTANCE (
        ID_ nvarchar(50) NOT NULL,
        NAME_ nvarchar(50),
        PANEL_ nvarchar(50),
        USERPANEL_ nvarchar(50),
        PRIMARY KEY (ID_)
);

CREATE TABLE UI_SKIN (
        ID_ nvarchar(50) NOT NULL,
        CREATEDATE_ datetime,
        DESCRIPTION_ nvarchar(500),
        IMAGE_ nvarchar(255),
        LOCKED_ int NOT NULL,
        NAME_ nvarchar(50) NOT NULL,
        STYLECLASS_ nvarchar(255),
        TITLE_ nvarchar(255),
        PRIMARY KEY (ID_)
);

CREATE TABLE UI_SKININSTANCE (
        ID_ nvarchar(50) NOT NULL,
        ACTORID_ nvarchar(255),
        SKIN_ nvarchar(50),
        PRIMARY KEY (ID_)
);

CREATE TABLE UI_USERPANEL (
        ID_ nvarchar(50) NOT NULL,
        ACTORID_ nvarchar(50),
        CREATEDATE_ datetime,
        LAYOUTNAME_ nvarchar(20),
        REFRESHSECONDS_ int,
        LAYOUT_ nvarchar(50),
        PRIMARY KEY (ID_)
);

CREATE TABLE UI_USERPORTAL (
        ID_ nvarchar(50) NOT NULL,
        ACTORID_ nvarchar(50),
        COLUMNINDEX_ int,
        CREATEDATE_ datetime,
        PANELID_ nvarchar(50),
        POSITION_ int,
        PRIMARY KEY (ID_)
);

