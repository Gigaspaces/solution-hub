package xap.oracle.employee.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import xap.model.IDomainEntity;

import com.gigaspaces.annotation.pojo.SpaceRouting;

@NamedNativeQueries({
		@NamedNativeQuery(name = "findEmployees", query = "select e.rowid, e.id, e.processed, e.firstName, e.lastname, e.age, e.departmentid from employee e", resultClass = Employee.class),
		@NamedNativeQuery(name = "findEmployeesByPartition", query = "select rowid, id, processed, firstName, lastname, age, departmentid from employee where mod(departmentid,?) = ? ", resultClass = Employee.class),
		@NamedNativeQuery(name = "findEmployeeByRowId", query = "select rowid, id, processed, firstName, lastname, age, departmentid from employee where rowId = ? ", resultClass = Employee.class)})
@Entity
@Table(name = "employee")
public class CrudEmployee implements IDomainEntity<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false, precision = 38, scale = 0)
	private Long id;

	@Column(name = "PROCESSED", nullable = false)
	private Boolean processed;

	@Column(name = "FIRSTNAME", nullable = false, length = 255)
	private String fistName;

	@Column(name = "LASTNAME", nullable = false, length = 255)
	private String lastName;

	@Column(name = "AGE", nullable = false)
	private Integer age;

	@Column(name = "DEPARTMENTID", nullable = false)
	private Integer departmentId;

	public CrudEmployee() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getProcessed() {
		return processed;
	}

	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}

	public String getFistName() {
		return fistName;
	}

	public void setFistName(String fistName) {
		this.fistName = fistName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@SpaceRouting
	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer department) {
		this.departmentId = department;
	}

}
