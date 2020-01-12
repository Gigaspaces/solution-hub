package xap.oracle.loader.listener;

import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xap.oracle.employee.entity.Employee;

 
public class RemoveEmployeeListener {

	static Logger logger = LoggerFactory.getLogger(RemoveEmployeeListener.class);

	@EventTemplate
	Employee unprocessedData() {
		Employee template = new Employee();
		return template;
	}

	@SpaceDataEvent
	public Employee eventListener(Employee event) {

		if (logger.isDebugEnabled())
			logger.debug("An Employee was removed from space");

	 
		return null;
	}

}
