package xap.oracle.test;

import org.springframework.context.support.FileSystemXmlApplicationContext;

import xap.oracle.employee.dao.CurdEmployeeDAO;
import xap.oracle.employee.entity.CrudEmployee;

public class DBTest {

	public static void main(String[] args) throws InterruptedException {

		FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(
				"classpath:/spring/test-context.xml");

		CurdEmployeeDAO service = context.getBean(CurdEmployeeDAO.class);

		int timer = 10000;
		int start = 0;
		int end = 100;
		
		CrudEmployee emp = new CrudEmployee();

		for (int i = start; i < end; i++) {

			emp.setAge(10);
			emp.setFistName("John");
			emp.setLastName("Dow" + i);
			emp.setId(new Long(i));
			emp.setProcessed(Boolean.FALSE);
			emp.setDepartmentId(i % 5);

			System.out.println("Creating an Employee");
			service.create(emp);
			Thread.sleep(timer);

		}

		for (int i = start; i < end; i++) {
			emp.setAge(22);
			emp.setFistName("Fritz");
			emp.setLastName("Shultz" + i);
			emp.setId(new Long(i));
			emp.setProcessed(Boolean.TRUE);
			emp.setDepartmentId(i % 5);

			System.out.println("Updating an Employee");
			service.update(emp);
			Thread.sleep(timer);

		}

		for (int i = start; i < end; i++) {
			emp.setId(new Long(i));

			System.out.println("Deleting an Employee");
			service.remove(emp);
			Thread.sleep(timer);
		}
	}

}
