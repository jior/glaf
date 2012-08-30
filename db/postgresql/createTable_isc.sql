CREATE TABLE t_arraygrid
(
  company character varying(20) NOT NULL,
  price numeric(6),
  change numeric(6),
  changeper numeric(6),
  laseupdated timestamp without time zone,
  CONSTRAINT t_arraygrid_pk PRIMARY KEY (company)
);

CREATE TABLE t_attachment
(
  f_id integer NOT NULL,
  f_name character varying(500),
  f_url character varying(500),
  f_flag character varying(1),
  f_cdatetime timestamp without time zone NOT NULL,
  f_cuid character varying(20),
  f_cpid character varying(20),
  f_cip character varying(15),
  f_udatetime timestamp without time zone,
  f_uuid character varying(20),
  f_upid character varying(20),
  f_uip character varying(15),
  f_refertype numeric(1),
  f_referid character varying(20),
  CONSTRAINT pk_t_attachment PRIMARY KEY (f_id)
);

CREATE TABLE t_batchargsback
(
  batchname character varying(80) NOT NULL,
  backdate timestamp without time zone NOT NULL,
  args character varying(200),
  CONSTRAINT t_batchargsback_pk PRIMARY KEY (batchname, backdate)
);

CREATE TABLE t_batchconfig
(
  batname character varying(80) NOT NULL,
  lastexedate timestamp without time zone,
  "interval" numeric(18),
  what character varying(200),
  lockflag character(1),
  "enable" character(1),
  batclassname character varying(80),
  batbusname character varying(200),
  CONSTRAINT t_batchconfig_pk PRIMARY KEY (batname)
);

CREATE TABLE t_batchexelog
(
  batname character varying(80) NOT NULL,
  exedate timestamp without time zone NOT NULL,
  execontext character varying(200),
  exeresult character varying(200),
  exeuser character varying(20),
  logfile character varying(200),
  exeenddate timestamp without time zone,
  batbusname character varying(200),
  exeresultmemo character varying(500),
  checkerrordata character varying(200),
  id numeric(18) NOT NULL,
  logfilepath character varying(200),
  CONSTRAINT t_batchexelog_pk PRIMARY KEY (id)
);


CREATE TABLE t_batchiofile
(
  id numeric(18) NOT NULL,
  ioflag character(1) NOT NULL,
  filepath character varying(200),
  filename character varying(200)
);

CREATE TABLE t_cellediting
(
  select1 character varying(20) NOT NULL,
  common character varying(20),
  light character varying(20),
  price character varying(20) NOT NULL,
  availdate character varying(20),
  CONSTRAINT t_cellediting_pk PRIMARY KEY (select1, price)
);

CREATE TABLE t_gridpay
(
  cid integer NOT NULL,
  payableyears character varying(8), -- 处理年月
  suppno character varying(20) NOT NULL, -- 供应商
  productno character varying(10) NOT NULL, -- 品番
  productname character varying(50) NOT NULL, -- 品名
  sumpricedis integer, -- 销售金额
  sumprice integer, -- 基准金额
  CONSTRAINT t_gridpay_pk PRIMARY KEY (cid)
);
COMMENT ON TABLE t_gridpay IS '统计查询例子';
COMMENT ON COLUMN t_gridpay.payableyears IS '处理年月';
COMMENT ON COLUMN t_gridpay.suppno IS '供应商';
COMMENT ON COLUMN t_gridpay.productno IS '品番';
COMMENT ON COLUMN t_gridpay.productname IS '品名';
COMMENT ON COLUMN t_gridpay.sumpricedis IS '销售金额';
COMMENT ON COLUMN t_gridpay.sumprice IS '基准金额';


CREATE TABLE t_iscsample
(
  sysno numeric(2) NOT NULL,
  "name" character(10),
  sex character(1),
  age character(2),
  city character(3),
  deadyear character(4),
  money numeric(20,8),
  CONSTRAINT t_iscsample_pkey PRIMARY KEY (sysno)
);

CREATE TABLE t_light
(
  id character varying(1) NOT NULL,
  "name" character varying(20),
  CONSTRAINT t_light_pk PRIMARY KEY (id)
);



CREATE TABLE t_privilege
(
  f_privilegeid character varying(6) NOT NULL,
  f_privilegename character varying(200),
  f_privilegetype character varying(2),
  f_url character varying(200),
  CONSTRAINT pk_t_privilege PRIMARY KEY (f_privilegeid)
);

CREATE TABLE t_role
(
  f_roleid numeric(16) NOT NULL,
  f_rolename character varying(100),
  f_remark character varying(200),
  CONSTRAINT pk_t_role PRIMARY KEY (f_roleid)
);

CREATE TABLE t_role_privilege
(
  f_roleid numeric(16) NOT NULL,
  f_privilegeid character varying(6) NOT NULL,
  f_creatuserid character varying(40),
  f_updateuserid character varying(40),
  f_creatdate timestamp without time zone,
  f_updatedate timestamp without time zone,
  CONSTRAINT pk_t_role_privilege PRIMARY KEY (f_roleid, f_privilegeid),
  CONSTRAINT fk_t_role_p_reference_t_privil FOREIGN KEY (f_privilegeid)
      REFERENCES t_privilege (f_privilegeid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_t_role_p_reference_t_role FOREIGN KEY (f_roleid)
      REFERENCES t_role (f_roleid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);



CREATE TABLE t_sysdictionary
(
  f_bigtype character varying(10) NOT NULL,
  f_smalltype character varying(10) NOT NULL,
  f_const character varying(50) NOT NULL,
  f_islock character varying(1) NOT NULL, -- 0:表示未锁定;1:表示锁定
  f_remark character varying(200),
  CONSTRAINT pk_t_sysdictionary PRIMARY KEY (f_bigtype, f_smalltype)
);
COMMENT ON COLUMN t_sysdictionary.f_islock IS '0:表示未锁定;1:表示锁定';

CREATE TABLE t_test
(
  id numeric(10) NOT NULL,
  "name" character varying(200),
  memo character varying(200),
  CONSTRAINT t_test_pk PRIMARY KEY (id)
);

CREATE TABLE t_test_l
(
  f_stuffapplyno character(9) NOT NULL,
  f_changereason character varying(500),
  f_safetypeid character(6),
  f_applytypeid character(6),
  f_produceuserid numeric(19),
  f_parttypeid numeric(18),
  f_partname character varying(20),
  f_partnameja character varying(20),
  f_use character varying(20),
  f_useja character varying(20),
  f_specification character(60),
  f_unitid character(6),
  f_partno character(10),
  f_priority character(6),
  f_oldpartno character(10),
  f_destineqty numeric(7,2),
  f_suggestarrivecyc numeric(7),
  f_suggestleastqty numeric(7,2),
  f_suggestsupplier character(4),
  f_suggestmanufacturer character varying(100),
  f_intendinguseqty numeric(7,2),
  f_cartype character varying(20),
  f_chargecode character(5),
  f_managecode character(3),
  f_machinestuffno character(8),
  f_machinestuffname character varying(100),
  f_machinestuffuseqty numeric(7,2),
  f_machinestuffmanufacturer character varying(100),
  f_remark character varying(200),
  f_applicant numeric(19),
  f_applydept numeric(19),
  f_writetime timestamp without time zone,
  f_processinstanceid numeric(19),
  f_usedeptsubmitdate timestamp without time zone,
  f_producedeptsubmitdate timestamp without time zone,
  f_decisionsupplierdate timestamp without time zone,
  f_brand character varying(20),
  f_producingarea character varying(200),
  f_suggeststatus character(6) DEFAULT '0'::bpchar,
  f_stockdisclaim character(1) DEFAULT '1'::bpchar,
  f_producedisclaim character(1) DEFAULT '1'::bpchar,
  f_usedisclaim character(1) DEFAULT '1'::bpchar,
  f_msdsattachmentid numeric(18),
  f_usedeptattachmentid numeric(18),
  f_confirmleastqty numeric(7,2),
  f_comfirmarrivecyc numeric(7),
  f_comfirmpartname character varying(20),
  f_comfirmf_specification character(60),
  f_comfirmunit character(6),
  f_comfirmsupplier character(4),
  f_stuffstatus character(1),
  f_exportstatus character(1),
  f_safecheck timestamp without time zone,
  f_safecheckuser numeric(19),
  f_exigency character(6),
  f_safemind character(6),
  f_sortid numeric(18),
  f_cuid character varying(20) NOT NULL,
  f_cpid character varying(20) NOT NULL,
  f_cip character varying(15) NOT NULL,
  f_udatetime timestamp without time zone,
  f_uuid character varying(20),
  f_upid character varying(20),
  f_uip character varying(15),
  f_issendattachment character(1) DEFAULT '1'::bpchar,
  f_produceoption character varying(500),
  f_producedispatchdate timestamp without time zone,
  f_stockdispatchdate timestamp without time zone,
  f_qinputnum character(1) DEFAULT '0'::bpchar,
  f_safemindremark character varying(500),
  f_comfirmmanufacturer character varying(100),
  f_comfirmproducingarea character varying(200),
  CONSTRAINT pk_t_test_l PRIMARY KEY (f_stuffapplyno)
);


CREATE TABLE t_test_wfdemo
(
  f_stuffapplyno character(9) NOT NULL,
  f_changeereason character varying(500),
  f_safetypeid character varying(6),
  f_applytypeid character varying(6),
  processinstanceid numeric(18),
  CONSTRAINT pk_t_test_wfdemo PRIMARY KEY (f_stuffapplyno)
);

CREATE TABLE t_test_wfdemo_bak
(
  f_taskinstanceid numeric(18) NOT NULL,
  f_stuffapplyno character(9) NOT NULL,
  f_changeereason character varying(500),
  f_safetypeid character varying(6),
  f_applytypeid character varying(6),
  processinstanceid numeric(18),
  CONSTRAINT pk_t_test_wfdemo_bak PRIMARY KEY (f_stuffapplyno, f_taskinstanceid)
);


CREATE TABLE t_users
(
  f_userid character varying(20) NOT NULL,
  f_name character varying(30),
  f_password character varying(30),
  f_email character varying(100),
  f_status character varying(1), -- 1：可用...
  f_creatuserid character varying(40),
  f_creatdate timestamp without time zone,
  f_updateuserid character varying(40),
  f_updatedate timestamp without time zone,
  f_gysbm character varying(6),
  f_gysgq character varying(2),
  CONSTRAINT pk_t_users PRIMARY KEY (f_userid)
);

CREATE TABLE t_users_role
(
  f_userid character varying(20) NOT NULL,
  f_roleid numeric(16) NOT NULL,
  f_creatuserid character varying(40),
  f_creatdate timestamp without time zone,
  f_updateuserid character varying(40),
  f_updatedate timestamp without time zone,
  CONSTRAINT pk_t_use_role PRIMARY KEY (f_userid, f_roleid),
  CONSTRAINT fk_t_use_ro_reference_t_role FOREIGN KEY (f_roleid)
      REFERENCES t_role (f_roleid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_t_use_ro_reference_t_users FOREIGN KEY (f_userid)
      REFERENCES t_users (f_userid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE s_attachment
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 20;
  
CREATE SEQUENCE s_batchexelogid
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 20;
  