package model;

import java.time.LocalDate;

public class ExpressCourier extends Courier{

	final float fixed_rate = 5;
	final int extra_charges = 100;
	
	public ExpressCourier(Customer cust, Address sourceLoc, Address destLoc, String courierType, Double weight,
			String recname, Long reccntctno, LocalDate pickupDate, String status) {
		
		super(cust,sourceLoc,destLoc,courierType,weight,recname,reccntctno,pickupDate,status);
		
	}
	public double calculateRate()
	{
		double rate = 0;
		 rate = (weight* fixed_rate) + extra_charges ;
		return rate;
		
	}
}

