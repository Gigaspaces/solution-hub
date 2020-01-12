package xap.oracle.processor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.annotation.PostConstruct;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleDriver;
import oracle.jdbc.OracleStatement;
import oracle.jdbc.dcn.DatabaseChangeEvent;
import oracle.jdbc.dcn.DatabaseChangeListener;
import oracle.jdbc.dcn.DatabaseChangeRegistration;
import oracle.jdbc.dcn.RowChangeDescription;
import oracle.jdbc.dcn.RowChangeDescription.RowOperation;
import oracle.jdbc.dcn.TableChangeDescription;

import org.openspaces.core.cluster.ClusterInfo;
import org.openspaces.core.cluster.ClusterInfoContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import xap.model.ISpaceService;
import xap.spring.SpringService;

public class OracleChangeListener implements DatabaseChangeListener {

	static Logger logger = LoggerFactory.getLogger(OracleChangeListener.class);

	private DatabaseChangeRegistration dcr = null;

	private OracleConnection dbConnection;

	private String listenerQuery;

	@ClusterInfoContext
	private ClusterInfo clusterInfo;

	private ISpaceService<?> service;

	PreparedStatement statement = null;

	@PostConstruct
	public void init() throws SQLException {

		this.initializeDB();

		this.registerListener();

		statement = dbConnection.prepareStatement(listenerQuery);
	}

	@Override
	public void onDatabaseChangeNotification(
			DatabaseChangeEvent databaseChangeEvent) {

		if (logger.isDebugEnabled()) {
			logger.debug("Event handler received event");
		}

		TableChangeDescription[] tableChanges = databaseChangeEvent
				.getTableChangeDescription();

		for (TableChangeDescription tableChange : tableChanges) {

			RowChangeDescription[] rcds = tableChange.getRowChangeDescription();

			for (RowChangeDescription rcd : rcds) {

				if (logger.isDebugEnabled()) {
					logger.debug("Affected row : "
							+ rcd.getRowid().stringValue() + rcd
							+ " operation :");
				}

				RowOperation ro = rcd.getRowOperation();
				String rowId = rcd.getRowid().stringValue();

				if (ro.equals(RowOperation.INSERT)) {
					service.insertSpace(rowId);
				} else if (ro.equals(RowOperation.UPDATE)) {
					service.updateSpace(rowId);
				} else if (ro.equals(RowOperation.DELETE)) {
					service.removeSpace(rowId);
				} else {
					logger.info("Event Not Replicated - Only INSERT/DELETE/UPDATE are handled.");
				}
			}
		}

	}

	private void registerListener() {
		if (logger.isDebugEnabled())
			logger.debug("Registering the Oracle listener");

		Properties props = new Properties();

		// props.setProperty(OracleConnection.DCN_NOTIFY_ROWIDS, "true");
		// props.setProperty(OracleConnection.DCN_QUERY_CHANGE_NOTIFICATION,
		// "true");

		props.put(OracleConnection.DCN_NOTIFY_ROWIDS, "true");
		props.put(OracleConnection.NTF_QOS_RELIABLE, "false");
		props.setProperty(OracleConnection.DCN_BEST_EFFORT, "true");

		Statement stmt = null;
		ResultSet rs = null;

		try {

			if (logger.isDebugEnabled())
				logger.debug("Creating DatabaseChangeRegistration ");

			dcr = dbConnection.registerDatabaseChangeNotification(props);

			if (logger.isDebugEnabled())
				logger.debug("Associate the statement with the registration ");
			stmt = dbConnection.createStatement();

			// Associate the statement with the registration.
			((OracleStatement) stmt).setDatabaseChangeRegistration(dcr);
			rs = stmt.executeQuery(listenerQuery);

			while (rs.next()) {
				// Do Nothing
			}

			if (logger.isDebugEnabled())
				logger.debug("Attaching the listener to the processor");

			dcr.addListener(this);

			if (logger.isDebugEnabled())
				logger.debug("Attached listener for " + listenerQuery);

			if (logger.isDebugEnabled()) {
				String[] tableNames = dcr.getTables();

				for (int i = 0; i < tableNames.length; i++) {
					logger.debug(tableNames[i] + " Successfully registered.");
				}
			}

		} catch (SQLException e) {
			logger.error("Error during listener registartion " + e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					logger.error("Error closing Statement " + e);
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("Error closing ResultSet " + e);
				}
			}
		}
	}

	private void initializeDB() throws SQLException {

		if (logger.isDebugEnabled())
			logger.debug("Creating the Oracle Connection");

		OracleDriver dr = new OracleDriver();

		DriverManagerDataSource ds = SpringService
				.getBean(DriverManagerDataSource.class);

		Properties prop = new Properties();
		prop.setProperty("user", ds.getUsername());
		prop.setProperty("password", ds.getPassword());

		dbConnection = (OracleConnection) dr.connect(ds.getUrl(), prop);
	}

	public void shutDown() throws SQLException {

		if (logger.isDebugEnabled())
			logger.debug("Shutting down the Oracle Connection");

		statement.close();

		dbConnection.unregisterDatabaseChangeNotification(dcr);

		dbConnection.close();
	}

	public String getListenerQuery() {
		return listenerQuery;
	}

	public void setListenerQuery(String listenerQuery) {
		this.listenerQuery = listenerQuery;
	}

	public ISpaceService<?> getService() {
		return service;
	}

	public void setService(ISpaceService<?> service) {
		this.service = service;
	}

}
