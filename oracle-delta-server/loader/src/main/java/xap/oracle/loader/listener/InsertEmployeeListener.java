package xap.oracle.loader.listener;

import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xap.oracle.employee.entity.Employee;

public class InsertEmployeeListener {

	static Logger logger = LoggerFactory
			.getLogger(InsertEmployeeListener.class);

	@EventTemplate
	Employee unprocessedData() {
		Employee template = new Employee();
		return template;
	}

	@SpaceDataEvent
	public Employee eventListener(Employee event) {

		if (logger.isDebugEnabled())
			logger.debug("An Employee was inserted in the space");

		return null;
	}

}
