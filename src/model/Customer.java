package model;

public class Customer extends User {
	private Integer custid; //Primary column of the table
	private String fname; //Name of customer
	private String lname; 
	private String email;
	private Long contactno;
	
	//default constructor
	public Customer()
	{
		
	}
	
	public Customer(Integer custid, String fname, String lname, String email, Long contactno) {
		this.custid = custid;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.contactno = contactno;
	}
	// getters and setters
	public Integer getCustid() {
		return custid;
	}
	public void setCustid(Integer custid) {
		this.custid = custid;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
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
