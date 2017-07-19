package model;

import java.time.LocalDate;

public class NormalCourier extends Courier{

	final float fixed_rate = 5;
	
	public NormalCourier(Customer cust, Address sourceLoc, Address destLoc, String courierType, Double weight,
			String recname, Long reccntctno, LocalDate pickupDate, String status) {
		
		super(cust,sourceLoc,destLoc,courierType,weight,recname,reccntctno,pickupDate,status);
		
	}
	public double calculateRate()
	{	//System.out.println("inside normal courier ka calculate rate");
		double rate = 0;
		 rate = weight* fixed_rate;
		return rate;
		
	}
}
