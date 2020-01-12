package xap.oracle.test;

import java.util.Collection;

import org.springframework.context.support.FileSystemXmlApplicationContext;

import xap.oracle.employee.dao.EmployeeDAO;
import xap.oracle.employee.entity.Employee;

public class LoadAll {

	public static void main(String[] args) throws InterruptedException {

		FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(
				"classpath:/spring/test-context.xml");

		EmployeeDAO service = context.getBean(EmployeeDAO.class);

		 Collection<Employee> employees = service.findAllEmloyeesByPartition(2,1);
		 
		 System.out.println(employees.size());
		 
		 for(Employee emp : employees)
		 {
			 System.out.println(emp);
		 } 
	}

}
