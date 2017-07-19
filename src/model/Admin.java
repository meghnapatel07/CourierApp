package model;

import java.util.ArrayList;
import java.util.Iterator;



public class Admin extends User {
	private Integer ID; //primary key of table
	private String zone;
	
	ArrayList<Location> loclist = new ArrayList<Location>();
	
	public Admin()
	{}
	
	public ArrayList<Location> getLoclist()
	{
		return loclist;
	}
	public void setLoclist(ArrayList<Location> list)
	{
		Iterator<Location> i = list.iterator();
		while (i.hasNext())
		{
		loclist.add((Location)i.next());
		}
	}
	
	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public Admin(String username, String password, String zone) {
		super(username, password);
		this.zone = zone;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

}// end class
