package org.mokai.persist.jdbc.sms;

import javax.sql.DataSource;

import org.mokai.persist.jdbc.JdbcHelper;
import org.mokai.persist.jdbc.SqlEngine;

public class DerbyEngine implements SqlEngine {
	
	private DataSource dataSource;
	
	private boolean initialized;
	
	public DerbyEngine() {}
	
	public DerbyEngine(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void init() throws Exception {
		
		if (initialized) {
			return;
		}
		
		JdbcHelper.checkCreateTable(dataSource, null, ConnectionsSmsHandler.DEFAULT_TABLENAME, getConnectionsCreateScript());
		JdbcHelper.checkCreateTable(dataSource, null, ApplicationsSmsHandler.DEFAULT_TABLENAME, getApplicationsCreateScript());
		
		initialized = true;
	}

	@Override
	public boolean isInitialized() {
		return initialized;
	}

	@Override
	public String addLimitToQuery(String query, int offset, int numRows) {
		return query + " OFFSET " + offset + " ROWS FETCH NEXT " + numRows + " ROWS ONLY";
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	protected String getConnectionsCreateScript() {
		return "CREATE TABLE " + ConnectionsSmsHandler.DEFAULT_TABLENAME + " (" +
					"id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
					"account VARCHAR(30), " +
					"reference VARCHAR(100), " +
					"source VARCHAR(30) NOT NULL, " +
					"sourcetype SMALLINT NOT NULL, " +
					"destination VARCHAR(30), " +
					"destinationtype SMALLINT, " +
					"status SMALLINT NOT NULL, " +
					"smsc_to VARCHAR(30), " +
					"smsc_from VARCHAR(30), " +
					"smsc_text VARCHAR(1000), " +
					"smsc_sequencenumber INTEGER, " +
					"smsc_messageid VARCHAR(50), " +
					"smsc_commandstatus INTEGER, " +
					"smsc_receiptstatus VARCHAR(20), " +
					"smsc_receipttime TIMESTAMP, " +
					"creation_time TIMESTAMP NOT NULL, " +
					"modification_time TIMESTAMP)";
	}
	
	protected String getApplicationsCreateScript() {
		return "CREATE TABLE " + ApplicationsSmsHandler.DEFAULT_TABLENAME + " (" +
					"id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
					"account VARCHAR(30), " +
					"reference VARCHAR(100), " +
					"source VARCHAR(30) NOT NULL, " +
					"sourcetype SMALLINT NOT NULL, " +
					"destination VARCHAR(30), " +
					"destinationtype SMALLINT, " +
					"status SMALLINT NOT NULL, " +
					"smsc_to VARCHAR(30), " +
					"smsc_from VARCHAR(30), " +
					"smsc_text VARCHAR(1000), " +
					"smsc_sequencenumber INTEGER, " +
					"smsc_messageid VARCHAR(50), " +
					"smsc_commandstatus INTEGER, " +
					"smsc_receiptstatus VARCHAR(20), " +
					"smsc_receipttime TIMESTAMP, " +
					"creation_time TIMESTAMP NOT NULL, " +
					"modification_time TIMESTAMP)";
	}

}
