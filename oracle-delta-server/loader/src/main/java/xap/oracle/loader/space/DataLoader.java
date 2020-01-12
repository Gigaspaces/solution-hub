package xap.oracle.loader.space;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.cluster.ClusterInfo;
import org.openspaces.core.cluster.ClusterInfoContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import xap.oracle.employee.dao.EmployeeDAO;
import xap.oracle.employee.entity.Employee;

public class DataLoader {

	@Autowired
	@Qualifier("deltaSpace")
	private GigaSpace space;

	@Autowired
	private EmployeeDAO dao;

	@ClusterInfoContext
	private ClusterInfo clusterInfo;

	@PostConstruct
	private void initialize() {

		System.out.println("Starting to load data ");

		Collection<Employee> employees = null;

		if (clusterInfo.getNumberOfInstances() > 1) {

			employees = dao.findAllEmloyeesByPartition(
					clusterInfo.getNumberOfInstances(),
					clusterInfo.getInstanceId() - 1);
		} else {
			employees = dao.findAllEmloyees();
		}

		for (Employee emp : employees) {
			space.write(emp);
		}
	}
}
