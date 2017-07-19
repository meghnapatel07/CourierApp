package model;

public class Address {
	private Integer addid;
	private String street;
	private String city;
	private String state;
	private Integer zipcode;
	
	public Address()
	{	}
	
	/*public Address(Address a)
	{
		addid = a.addid;
		street = a.street;
		city = a.city;
		state = a.state;
		zipcode = a.zipcode;
	}*/
	public Integer getAddid() {
		return addid;
	}

	public void setAddid(Integer addid) {
		this.addid = addid;
	}

	public Address(String street, String city,String state, Integer zipcode)
	{
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
	}
	//getters and setters
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getZipcode() {
		return zipcode;
	}
	public void setZipcode(Integer zipcode) {
		this.zipcode = zipcode;
	}
	
	
}
