package model;

import java.time.LocalDate;


public class Courier implements calculate {

	private Customer cust;
	private Integer trackingNo;
	private Address sourceLoc;
	private Address destLoc;
	private String courierType;
	protected Double weight;
	private String recname;
	private Long reccntctno;
	private LocalDate pickupDate;
	private String status;
	private Double rate;

	public Courier() {

	}

	public Courier(Customer cust, Address sourceLoc, Address destLoc, String courierType, Double weight,
			String recname, Long reccntctno, LocalDate pickupDate, String status) {
		// super();
		this.cust = cust;
		this.sourceLoc = sourceLoc;
		this.destLoc = destLoc;
		this.courierType = courierType;
		this.weight = weight;
		this.recname = recname;
		this.reccntctno = reccntctno;
		this.pickupDate = pickupDate;
		this.status = status;
	}

	public Integer getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(Integer trackingNo) {
		this.trackingNo = trackingNo;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public String getCourierType() {
		return courierType;
	}

	public Address getSourceLoc() {
		return this.sourceLoc;

	}

	public Customer getCust() {
		return cust;
	}

	public void setCust(Customer cust) {
		this.cust = cust;
	}

	public Address getDestLoc() {
		return destLoc;
	}

	public void setDestLoc(Address destLoc) {
		this.destLoc = destLoc;
	}

	public void setSourceLoc(Address sourceLoc) {
		this.sourceLoc = sourceLoc;
	}

	public void setCourierType(String courierType) {
		this.courierType = courierType;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getRecname() {
		return recname;
	}

	public void setRecname(String recname) {
		this.recname = recname;
	}

	public Long getReccntctno() {
		return reccntctno;
	}

	public void setReccntctno(Long reccntctno) {
		this.reccntctno = reccntctno;
	}

	public LocalDate getPickupDate() {
		return pickupDate;
	}

	public void setPickupDate(LocalDate pickupDate) {
		this.pickupDate = pickupDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double calculateRate()
	{
		return 0;
	}

}// end class
