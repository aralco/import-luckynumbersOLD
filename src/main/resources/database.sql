--------------------------------------------------------
--  DDL for Table ILN_CITY
--------------------------------------------------------

  CREATE TABLE "IMPORTLUCKY"."ILN_CITY"
   (	"ILN_CITY_ID" NUMBER(19,0),
	"ILN_CITY_CODE" NUMBER(10,0),
	"ILN_CITY_NAME" VARCHAR2(255 CHAR),
	"ILN_CITY_DESCRIPTION" VARCHAR2(255 CHAR),
	"ILN_CITY_ENABLED" NUMBER(1,0),
	"ILN_CITY_CREATED_DATE" TIMESTAMP (6),
	"ILN_CITY_LAST_UPDATE" TIMESTAMP (6)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table ILN_OUT_AUDIT
--------------------------------------------------------

  CREATE TABLE "IMPORTLUCKY"."ILN_OUT_AUDIT"
   (	"ILN_OUT_AUDIT_ID" NUMBER(19,0),
	"ILN_OUT_AUDIT_ROW" VARCHAR2(255 CHAR),
	"ILN_OUT_AUDIT_NUMBER" VARCHAR2(255 CHAR),
	"ILN_OUT_AUDIT_LUCKY_RESERVED" NUMBER(1,0),
	"ILN_OUT_AUDIT_CODE_PASSED" NUMBER(10,0),
	"ILN_OUT_AUDIT_CODE_FAILED" VARCHAR2(255 CHAR),
	"ILN_OUT_AUDIT_MESSAGE" VARCHAR2(255 CHAR),
	"ILN_OUT_AUDIT_FILENAME" VARCHAR2(255 CHAR),
	"ILN_OUT_AUDIT_TASKID" NUMBER(19,0),
	"ILN_OUT_AUDIT_JOBID" NUMBER(19,0),
	"ILN_OUT_AUDIT_CREATED_DATE" TIMESTAMP (6),
	"ILN_OUT_AUDIT_LAST_UPDATE" TIMESTAMP (6)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table ILN_USER
--------------------------------------------------------

  CREATE TABLE "IMPORTLUCKY"."ILN_USER"
   (	"ILN_USER_ID" NUMBER(19,0),
	"ILN_USER_NAME" VARCHAR2(255 CHAR),
	"ILN_USER_EMAIL1" VARCHAR2(255 CHAR),
	"ILN_USER_EMAIL2" VARCHAR2(255 CHAR),
	"ILN_USER_PHONE1" VARCHAR2(255 CHAR),
	"ILN_USER_PHONE2" VARCHAR2(255 CHAR),
	"ILN_USER_PHONE3" VARCHAR2(255 CHAR),
	"ILN_USER_DESCRIPTION" VARCHAR2(255 CHAR),
	"ILN_USER_CREATED_DATE" TIMESTAMP (6),
	"ILN_USER_LAST_UPDATE" TIMESTAMP (6),
	"ILN_USER_ENABLED" NUMBER(1,0),
	"ILN_USER_USERNAME" VARCHAR2(255 CHAR),
	"ILN_USER_ROLE" VARCHAR2(255 CHAR)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table ILN_JOB
--------------------------------------------------------

  CREATE TABLE "IMPORTLUCKY"."ILN_JOB"
   (	"ILN_JOB_ID" NUMBER(19,0),
	"ILN_JOB_NAME" VARCHAR2(255 CHAR),
	"ILN_JOB_DESCRIPTION" VARCHAR2(255 CHAR),
	"ILN_JOB_SCHEDULED_DATE" TIMESTAMP (6),
	"ILN_JOB_NOW" NUMBER(1,0),
	"ILN_JOB_STATE" VARCHAR2(255 CHAR),
	"ILN_JOB_OWNER" VARCHAR2(255 CHAR),
	"ILN_JOB_TOTAL_TASKS" NUMBER(10,0),
	"ILN_JOB_PASSED_TASKS" NUMBER(10,0),
	"ILN_JOB_FAILED_TASKS" NUMBER(10,0),
	"ILN_JOB_TOTAL_COVERAGE" VARCHAR2(255 CHAR),
	"ILN_JOB_SUMMARY" CLOB,
	"ILN_JOB_CREATED_DATE" TIMESTAMP (6),
	"ILN_JOB_LAST_UPDATE" TIMESTAMP (6)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"
 LOB ("ILN_JOB_SUMMARY") STORE AS BASICFILE (
  TABLESPACE "USERS" ENABLE STORAGE IN ROW CHUNK 8192 RETENTION
  NOCACHE LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)) ;
--------------------------------------------------------
--  DDL for Table ILN_IN_AUDIT
--------------------------------------------------------

  CREATE TABLE "IMPORTLUCKY"."ILN_IN_AUDIT"
   (	"ILN_IN_AUDIT_ID" NUMBER(19,0),
	"ILN_IN_AUDIT_ROW" VARCHAR2(255 CHAR),
	"ILN_IN_AUDIT_NUMBER" VARCHAR2(255 CHAR),
	"ILN_IN_AUDIT_CITY" NUMBER(10,0),
	"ILN_IN_AUDIT_CHANNEL" NUMBER(10,0),
	"ILN_IN_AUDIT_FILENAME" VARCHAR2(255 CHAR),
	"ILN_IN_AUDIT_TASKID" NUMBER(19,0),
	"ILN_IN_AUDIT_JOBID" NUMBER(19,0),
	"ILN_IN_AUDIT_CREATED_DATE" TIMESTAMP (6),
	"ILN_IN_AUDIT_LAST_UPDATE" TIMESTAMP (6)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table ILN_TASK
--------------------------------------------------------

  CREATE TABLE "IMPORTLUCKY"."ILN_TASK"
   (	"ILN_TASK_ID" NUMBER(19,0),
	"ILN_TASK_TYPE" VARCHAR2(255 CHAR),
	"ILN_TASK_CITY" NUMBER(10,0),
	"ILN_TASK_FROM" VARCHAR2(255 CHAR),
	"ILN_TASK_TO" VARCHAR2(255 CHAR),
	"ILN_TASK_EXECUTION_DATE" TIMESTAMP (6),
	"ILN_TASK_STATUS" VARCHAR2(255 CHAR),
	"ILN_TASK_PROCESSED" NUMBER(10,0),
	"ILN_TASK_PASSED" NUMBER(10,0),
	"ILN_TASK_FAILED" NUMBER(10,0),
	"ILN_TASK_SUMMARY" CLOB,
	"ILN_TASK_COVERAGE" VARCHAR2(255 CHAR),
	"ILN_TASK_URL_IN" VARCHAR2(255 CHAR),
	"ILN_TASK_URL_OUT" VARCHAR2(255 CHAR),
	"ILN_TASK_CREATED_DATE" TIMESTAMP (6),
	"ILN_TASK_LAST_UPDATE" TIMESTAMP (6),
	"ILN_JOB_ID" NUMBER(19,0)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"
 LOB ("ILN_TASK_SUMMARY") STORE AS BASICFILE (
  TABLESPACE "USERS" ENABLE STORAGE IN ROW CHUNK 8192 RETENTION
  NOCACHE LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)) ;
--------------------------------------------------------
--  DDL for Table ILN_ACCESS_LOG
--------------------------------------------------------

  CREATE TABLE "IMPORTLUCKY"."ILN_ACCESS_LOG"
   (	"ILN_ACCESS_LOG_ID" NUMBER(19,0),
	"ILN_ACCESS_LOG_TIMESTAMP" TIMESTAMP (6),
	"ILN_ACCESS_LOG_USER" VARCHAR2(255 CHAR),
	"ILN_ACCESS_LOG_ACTION" VARCHAR2(255 CHAR),
	"ILN_ACCESS_LOG_DESCRIPTION" VARCHAR2(255 CHAR)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table ILN_CONTACT
--------------------------------------------------------

  CREATE TABLE "IMPORTLUCKY"."ILN_CONTACT"
   (	"ILN_CONTACT_ID" NUMBER(19,0),
	"ILN_CONTACT_NAME" VARCHAR2(255 CHAR),
	"ILN_CONTACT_EMAIL1" VARCHAR2(255 CHAR),
	"ILN_CONTACT_EMAIL2" VARCHAR2(255 CHAR),
	"ILN_CONTACT_PHONE1" VARCHAR2(255 CHAR),
	"ILN_CONTACT_PHONE2" VARCHAR2(255 CHAR),
	"ILN_CONTACT_PHONE3" VARCHAR2(255 CHAR),
	"ILN_CONTACT_DESCRIPTION" VARCHAR2(255 CHAR),
	"ILN_CONTACT_CREATED_DATE" TIMESTAMP (6),
	"ILN_CONTACT_LAST_UPDATE" TIMESTAMP (6),
	"ILN_CONTACT_ENABLED" NUMBER(1,0)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index SYS_C0018390
--------------------------------------------------------

  CREATE UNIQUE INDEX "IMPORTLUCKY"."SYS_C0018390" ON "IMPORTLUCKY"."ILN_CITY" ("ILN_CITY_ID")
  PCTFREE 10 INITRANS 2 MAXTRANS 255
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index SYS_C0018391
--------------------------------------------------------

  CREATE UNIQUE INDEX "IMPORTLUCKY"."SYS_C0018391" ON "IMPORTLUCKY"."ILN_CITY" ("ILN_CITY_CODE")
  PCTFREE 10 INITRANS 2 MAXTRANS 255
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index SYS_C0018392
--------------------------------------------------------

  CREATE UNIQUE INDEX "IMPORTLUCKY"."SYS_C0018392" ON "IMPORTLUCKY"."ILN_CITY" ("ILN_CITY_NAME")
  PCTFREE 10 INITRANS 2 MAXTRANS 255
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index SYS_C0018401
--------------------------------------------------------

  CREATE UNIQUE INDEX "IMPORTLUCKY"."SYS_C0018401" ON "IMPORTLUCKY"."ILN_OUT_AUDIT" ("ILN_OUT_AUDIT_ID")
  PCTFREE 10 INITRANS 2 MAXTRANS 255
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index SYS_C0018409
--------------------------------------------------------

  CREATE UNIQUE INDEX "IMPORTLUCKY"."SYS_C0018409" ON "IMPORTLUCKY"."ILN_USER" ("ILN_USER_ID")
  PCTFREE 10 INITRANS 2 MAXTRANS 255
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index SYS_C0018410
--------------------------------------------------------

  CREATE UNIQUE INDEX "IMPORTLUCKY"."SYS_C0018410" ON "IMPORTLUCKY"."ILN_USER" ("ILN_USER_USERNAME")
  PCTFREE 10 INITRANS 2 MAXTRANS 255
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index SYS_C0018399
--------------------------------------------------------

  CREATE UNIQUE INDEX "IMPORTLUCKY"."SYS_C0018399" ON "IMPORTLUCKY"."ILN_JOB" ("ILN_JOB_ID")
  PCTFREE 10 INITRANS 2 MAXTRANS 255
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index SYS_C0018397
--------------------------------------------------------

  CREATE UNIQUE INDEX "IMPORTLUCKY"."SYS_C0018397" ON "IMPORTLUCKY"."ILN_IN_AUDIT" ("ILN_IN_AUDIT_ID")
  PCTFREE 10 INITRANS 2 MAXTRANS 255
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index SYS_C0018404
--------------------------------------------------------

  CREATE UNIQUE INDEX "IMPORTLUCKY"."SYS_C0018404" ON "IMPORTLUCKY"."ILN_TASK" ("ILN_TASK_ID")
  PCTFREE 10 INITRANS 2 MAXTRANS 255
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index SYS_C0018385
--------------------------------------------------------

  CREATE UNIQUE INDEX "IMPORTLUCKY"."SYS_C0018385" ON "IMPORTLUCKY"."ILN_ACCESS_LOG" ("ILN_ACCESS_LOG_ID")
  PCTFREE 10 INITRANS 2 MAXTRANS 255
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index SYS_C0018395
--------------------------------------------------------

  CREATE UNIQUE INDEX "IMPORTLUCKY"."SYS_C0018395" ON "IMPORTLUCKY"."ILN_CONTACT" ("ILN_CONTACT_ID")
  PCTFREE 10 INITRANS 2 MAXTRANS 255
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  Constraints for Table ILN_CITY
--------------------------------------------------------

  ALTER TABLE "IMPORTLUCKY"."ILN_CITY" ADD UNIQUE ("ILN_CITY_NAME")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "IMPORTLUCKY"."ILN_CITY" ADD UNIQUE ("ILN_CITY_CODE")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "IMPORTLUCKY"."ILN_CITY" ADD PRIMARY KEY ("ILN_CITY_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "IMPORTLUCKY"."ILN_CITY" MODIFY ("ILN_CITY_ENABLED" NOT NULL ENABLE);
  ALTER TABLE "IMPORTLUCKY"."ILN_CITY" MODIFY ("ILN_CITY_NAME" NOT NULL ENABLE);
  ALTER TABLE "IMPORTLUCKY"."ILN_CITY" MODIFY ("ILN_CITY_CODE" NOT NULL ENABLE);
  ALTER TABLE "IMPORTLUCKY"."ILN_CITY" MODIFY ("ILN_CITY_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ILN_OUT_AUDIT
--------------------------------------------------------

  ALTER TABLE "IMPORTLUCKY"."ILN_OUT_AUDIT" ADD PRIMARY KEY ("ILN_OUT_AUDIT_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "IMPORTLUCKY"."ILN_OUT_AUDIT" MODIFY ("ILN_OUT_AUDIT_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ILN_USER
--------------------------------------------------------

  ALTER TABLE "IMPORTLUCKY"."ILN_USER" ADD UNIQUE ("ILN_USER_USERNAME")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "IMPORTLUCKY"."ILN_USER" ADD PRIMARY KEY ("ILN_USER_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "IMPORTLUCKY"."ILN_USER" MODIFY ("ILN_USER_ROLE" NOT NULL ENABLE);
  ALTER TABLE "IMPORTLUCKY"."ILN_USER" MODIFY ("ILN_USER_USERNAME" NOT NULL ENABLE);
  ALTER TABLE "IMPORTLUCKY"."ILN_USER" MODIFY ("ILN_USER_ENABLED" NOT NULL ENABLE);
  ALTER TABLE "IMPORTLUCKY"."ILN_USER" MODIFY ("ILN_USER_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ILN_JOB
--------------------------------------------------------

  ALTER TABLE "IMPORTLUCKY"."ILN_JOB" ADD PRIMARY KEY ("ILN_JOB_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "IMPORTLUCKY"."ILN_JOB" MODIFY ("ILN_JOB_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ILN_IN_AUDIT
--------------------------------------------------------

  ALTER TABLE "IMPORTLUCKY"."ILN_IN_AUDIT" ADD PRIMARY KEY ("ILN_IN_AUDIT_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "IMPORTLUCKY"."ILN_IN_AUDIT" MODIFY ("ILN_IN_AUDIT_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ILN_TASK
--------------------------------------------------------

  ALTER TABLE "IMPORTLUCKY"."ILN_TASK" ADD PRIMARY KEY ("ILN_TASK_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "IMPORTLUCKY"."ILN_TASK" MODIFY ("ILN_JOB_ID" NOT NULL ENABLE);
  ALTER TABLE "IMPORTLUCKY"."ILN_TASK" MODIFY ("ILN_TASK_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ILN_ACCESS_LOG
--------------------------------------------------------

  ALTER TABLE "IMPORTLUCKY"."ILN_ACCESS_LOG" ADD PRIMARY KEY ("ILN_ACCESS_LOG_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "IMPORTLUCKY"."ILN_ACCESS_LOG" MODIFY ("ILN_ACCESS_LOG_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ILN_CONTACT
--------------------------------------------------------

  ALTER TABLE "IMPORTLUCKY"."ILN_CONTACT" ADD PRIMARY KEY ("ILN_CONTACT_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "IMPORTLUCKY"."ILN_CONTACT" MODIFY ("ILN_CONTACT_ENABLED" NOT NULL ENABLE);
  ALTER TABLE "IMPORTLUCKY"."ILN_CONTACT" MODIFY ("ILN_CONTACT_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Ref Constraints for Table ILN_TASK
--------------------------------------------------------

  ALTER TABLE "IMPORTLUCKY"."ILN_TASK" ADD CONSTRAINT "FKDE0B49B9DB14F06A" FOREIGN KEY ("ILN_JOB_ID")
	  REFERENCES "IMPORTLUCKY"."ILN_JOB" ("ILN_JOB_ID") ENABLE;
