package xap.oracle.loader.space;

import org.hibernate.SessionFactory;
import org.openspaces.core.cluster.ClusterInfo;
import org.openspaces.core.cluster.ClusterInfoContext;
import org.openspaces.persistency.hibernate.DefaultHibernateSpaceDataSource;
import org.openspaces.persistency.hibernate.iterator.DefaultScrollableDataIterator;

import com.gigaspaces.datasource.DataIterator;

public class SpaceDataSource extends DefaultHibernateSpaceDataSource {

	public SpaceDataSource(SessionFactory sessionFactory) {
		super(sessionFactory, null, 1000, false, null, 5, -1, true);
	}

	@ClusterInfoContext
	private ClusterInfo clusterInfo;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public DataIterator initialDataLoad() {

		String hquery = "";
		//
		// if (clusterInfo.getNumberOfInstances() > 1) {
		// hquery =
		// "from xap.oracle.employee.entity.Employee where MOD(department_id,"
		// + clusterInfo.getNumberOfInstances()
		// + ") = "
		// + (clusterInfo.getInstanceId() - 1);
		// } else {
		// hquery = "from xap.oracle.employee.entity.Employee  ";
		hquery = "\"select rowid, id, processed, firstName, lastname,age, departmentid  from employee\", resultClass = Employee.class";

		// }

		DataIterator[] iterators = new DataIterator[1];
		int iteratorCounter = 0;
		int fetchSize = 100;
		int from = -1;
		int size = -1;
		iterators[iteratorCounter++] = new DefaultScrollableDataIterator(
				hquery, getSessionFactory(), fetchSize, from, size);

		return createInitialLoadIterator(iterators);
	}

}
