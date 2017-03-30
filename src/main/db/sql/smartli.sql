CREATE TABLE THIRDPARTY_INFO
(
  	ID                    	VARCHAR(40) NOT NULL,
	SHORT_NAME				VARCHAR(40) NOT NULL,
	LONG_NAME				VARCHAR(100),
	CLIENT_STATUS			VARCHAR(30),
	CLIENT_ID				VARCHAR(100),
	CLIENT_SECRET			VARCHAR(2000),
	CLIENT_GRANTS			VARCHAR(500),
	OUT_HEADERS_MAP			VARCHAR(500),
	OUT_REQ_KEYS_4GRANT		VARCHAR(500),
	OUT_REQ_BODY_4GRANT		VARCHAR(2000),
	IN_RESPONSE_KEYS		VARCHAR(500),
	AUTHORIZE_URL			VARCHAR(500),
	ACCESS_URL				VARCHAR(500),
	RESOURCE_URL			VARCHAR(500),
	PROVIDER_STATUS			VARCHAR(30),
	PROVIDER_ID				VARCHAR(100),
	PROVIDER_SECRET			VARCHAR(2000),
  	CREATED_BY				VARCHAR(100),
  	CREATED_DTM				DATETIME DEFAULT CURRENT_TIMESTAMP,
  	MODIFIED_BY				VARCHAR(100),
  	MODIFIED_DTM			DATETIME DEFAULT CURRENT_TIMESTAMP  
);

ALTER TABLE THIRDPARTY_INFO ADD CONSTRAINT TP_INFO_PK PRIMARY KEY (ID);

CREATE UNIQUE INDEX IX_TP_SN ON THIRDPARTY_INFO (SHORT_NAME);


CREATE TABLE smartelligyntdb.3P_TOKEN_STORE
(
  ID                      VARCHAR(40) NOT NULL,
  TP_NAME         VARCHAR(40) NOT NULL,
  CUSTOMER_ID       VARCHAR(40) NOT NULL,
  ACCESS_TOKEN        VARCHAR(2000) NOT NULL,
  ACCESS_TOKEN_CREATE_TS  DATETIME DEFAULT CURRENT_TIMESTAMP,
  ACCESS_TOKEN_EXPIRE_TS  DATETIME DEFAULT CURRENT_TIMESTAMP,
  REFRESH_TOKEN       VARCHAR(2000),
  REFRESH_TOKEN_CREATE_TS DATETIME DEFAULT CURRENT_TIMESTAMP,
  REFRESH_TOKEN_EXPIRE_TS DATETIME DEFAULT CURRENT_TIMESTAMP,
  CREATED_BY        VARCHAR(100),
  CREATED_DTM       DATETIME DEFAULT CURRENT_TIMESTAMP,
  MODIFIED_BY       VARCHAR(100),
  MODIFIED_DTM        DATETIME DEFAULT CURRENT_TIMESTAMP  
);

CREATE INDEX IX_CID_TP ON 3P_TOKEN_STORE (CUSTOMER_ID, TP_NAME);