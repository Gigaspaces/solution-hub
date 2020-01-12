package xap.oracle.employee.entity;

import javax.persistence.Id;
import javax.persistence.Entity;


import xap.model.IDomainEntity;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;

@SpaceClass
@Entity
public class Employee implements IDomainEntity<Long> {

	private static final long serialVersionUID = 1L;

	private String rowid;

	@Id
	private Long id;

	private Boolean processed;

	private String firstName;

	private String lastName;

	private Integer age;

	private Integer departmentid;

	public Employee() {
	}

	@SpaceId
	public Long getId() {
		return id;
	}

	@SpaceRouting
	public Integer getDepartmentid() {
		return departmentid;
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

 

	public void setDepartmentid(Integer departmentid) {
		this.departmentid = departmentid;
	}

	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String toString() {
		return "Employee [rowid=" + rowid + ", id=" + id + ", processed="
				+ processed + ", firstName=" + firstName + ", lastName="
				+ lastName + ", age=" + age + ", departmentid=" + departmentid
				+ "]";
	}

}
