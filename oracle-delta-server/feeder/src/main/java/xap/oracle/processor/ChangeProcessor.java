package xap.oracle.processor;

import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChangeProcessor {

	static Logger logger = LoggerFactory.getLogger(ChangeProcessor.class);

	private OracleChangeListener listener = null;

	@PostConstruct
	private void start() {
		if (logger.isDebugEnabled())
			logger.debug("Starting the feder tasks ");
	}

	@PreDestroy
	public void shutDdown() {

		try {
			if (logger.isDebugEnabled())
				logger.debug("Shutting down the Oracle listener");

			listener.shutDown();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public OracleChangeListener getListener() {
		return listener;
	}

	public void setListener(OracleChangeListener listener) {
		this.listener = listener;
	}

}