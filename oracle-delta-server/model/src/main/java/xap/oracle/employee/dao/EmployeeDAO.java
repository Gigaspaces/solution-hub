package xap.oracle.employee.dao;

import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import xap.model.AbstractJpaDAO;
import xap.oracle.employee.entity.CrudEmployee;
import xap.oracle.employee.entity.Employee;

@Service
public class EmployeeDAO extends AbstractJpaDAO<Employee, Long> {

	Logger logger = LoggerFactory.getLogger(CrudEmployee.class);

	public Employee findEmloyeeByRowId(String id) {
		Session session = this.getSession();

		Query query = session.getNamedQuery("findEmployeeByRowId");
		query.setString(0, id);

		if (logger.isDebugEnabled())
			logger.debug(query.toString());

		return (Employee) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public Collection<Employee> findAllEmloyees() {
		Session session = this.getSession();

		Query query = session.getNamedQuery("findEmployees");

		if (logger.isDebugEnabled())
			logger.debug(query.toString());

		return query.list();
	}

	@SuppressWarnings("unchecked")
	public Collection<Employee> findAllEmloyeesByPartition(
			int numberOfPartitions, int member) {
		Session session = this.getSession();

		Query query = session.getNamedQuery("findEmployeesByPartition");
		query.setInteger(0, numberOfPartitions);
		query.setInteger(1, member);

		if (logger.isDebugEnabled())
			logger.debug(query.toString());

		return query.list();
	}

	@Override
	protected Class<?> getKlass() {
		return Employee.class;
	}
}
