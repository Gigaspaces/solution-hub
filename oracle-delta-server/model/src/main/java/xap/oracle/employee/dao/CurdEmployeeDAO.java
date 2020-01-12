package xap.oracle.employee.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import xap.model.AbstractJpaDAO;
import xap.oracle.employee.entity.CrudEmployee;

@Service
public class CurdEmployeeDAO extends AbstractJpaDAO<CrudEmployee, Long> {

	Logger logger = LoggerFactory.getLogger(CrudEmployee.class);

	public void create(CrudEmployee employee) {
		super.persist(employee);
	}

	public void update(CrudEmployee employee) {
		super.update(employee);
	}

	public void remove(CrudEmployee employee) {
		super.remove(employee);
	}

	public CrudEmployee findEmloyeeById(Long id) {
		Session session = this.getSession();

		Query query = session.getNamedQuery("findEmployeeById").setLong("id",
				id);

		if (logger.isDebugEnabled())
			logger.debug(query.toString());

		return (CrudEmployee) query.uniqueResult();
	}

	public CrudEmployee findEmloyeeByRowId(String id) {
		Session session = this.getSession();

		Query query = session.getNamedQuery("findEmployeeByRowId").setString(
				"rowId", id);

		if (logger.isDebugEnabled())
			logger.debug(query.toString());

		return (CrudEmployee) query.uniqueResult();
	}

	public CrudEmployee findEmloyeeByDepartment(Long departmentId) {
		Session session = this.getSession();

		Query query = session.getNamedQuery("findEmployeeByDepartmentId")
				.setLong("departmentId", departmentId);

		if (logger.isDebugEnabled())
			logger.debug(query.toString());

		return (CrudEmployee) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<CrudEmployee> findEmloyeesByRowIds(List<String> ids) {
		Session session = this.getSession();

		Query query = session.getNamedQuery("findEmployeesByRowIds")
				.setParameterList("rowIds", ids);

		if (logger.isDebugEnabled())
			logger.debug(query.toString());

		return query.list();
	}


	@Override
	protected Class<?> getKlass() {
		return CrudEmployee.class;
	}
}
