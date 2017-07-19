package model;

public class Employee extends User {

	private Integer empid; //Primary column of the table
	private String ename; //Name of employee
	private String designation; 
	private String email;
	private Long contactno;
	
	private Location emploc;
	
	public Location getEmploc() {
		return emploc;
	}
	public void setEmploc(Location emploc) {
		this.emploc = emploc;
	}
	public Integer getEmpid() {
		return empid;
	}
	public void setEmpid(Integer empid) {
		this.empid = empid;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getContactno() {
		return contactno;
	}
	public void setContactno(Long contactno) {
		this.contactno = contactno;
	}
	
	
}
