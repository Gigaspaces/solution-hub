package xap.oracle.loader.listener;

import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xap.oracle.employee.entity.Employee;

 
public class UpdateEmployeeListener {

	static Logger logger = LoggerFactory.getLogger(UpdateEmployeeListener.class);

	@EventTemplate
	Employee unprocessedData() {
		Employee template = new Employee();
		return template;
	}

	@SpaceDataEvent
	public Employee eventListener(Employee event) {

		if (logger.isDebugEnabled())
			logger.debug("Employee was updated in space");
		
		return null;
	}

}
