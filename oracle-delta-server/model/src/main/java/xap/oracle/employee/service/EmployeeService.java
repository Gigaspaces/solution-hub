package xap.oracle.employee.service;

import org.openspaces.core.GigaSpace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import xap.oracle.employee.dao.EmployeeDAO;
import xap.oracle.employee.entity.Employee;

import com.gigaspaces.client.WriteModifiers;
import com.j_spaces.core.client.SQLQuery;

public class EmployeeService implements IEmployeeService {

	static Logger logger = LoggerFactory.getLogger(EmployeeService.class);

	@Autowired
	private EmployeeDAO dao;

	@Autowired
	@Qualifier("deltaSpace")
	private GigaSpace space;

	public Employee findByRowId(String id) {
		return dao.findEmloyeeByRowId(id);

	}

	@Override
	@Transactional(value = "txManager")
	public void insertSpace(String rowId) {

		Employee employee = dao.findEmloyeeByRowId(rowId);
		employee.setRowid(rowId);

		if (employee != null) {
			space.write(employee, WriteModifiers.WRITE_ONLY);

			if (logger.isDebugEnabled()) {
				logger.debug("Wrote Employee to the Space");
			}
		}
	}

	@Transactional(value = "txManager")
	public void removeSpace(String rowId) {

		SQLQuery<Employee> query = new SQLQuery<Employee>(Employee.class,
				"rowid = ?");
		query.setParameter(1, rowId);

		Employee employee = space.take(query);

		if (employee != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Removed Employee from Space");
			}
		} else {
			logger.error("Remove Employee with rowdID " + rowId + "  failed !");
		}
	}

	@Transactional(value = "txManager")
	public void updateSpace(String rowId) {
		Employee employee = dao.findEmloyeeByRowId(rowId);
		employee.setRowid(rowId);

		if (employee != null) {
			space.write(employee, WriteModifiers.UPDATE_ONLY);
		}
	}

	public GigaSpace getSpace() {
		return space;
	}

	public void setSpace(GigaSpace space) {
		this.space = space;
	}

}
