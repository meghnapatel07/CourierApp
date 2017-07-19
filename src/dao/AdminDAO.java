package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import model.Admin;
import model.Location;


public class AdminDAO {

	
	public ArrayList<Location> getLocations(Admin a) {
		Connection connection = ConnectToDB.getMySQLConnection();
		ArrayList<Location> loclist = new ArrayList<Location>();

		try {
			Statement statement = (Statement) connection.createStatement();
			String query = "SELECT * FROM MPATE117_Location WHERE zone='" + a.getZone() + "'";
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				Location loc = new Location();
				loc.setId(resultSet.getInt(1));
				loc.setName(resultSet.getString(2));
				loc.setAddress(resultSet.getString(3));
				loc.setCity(resultSet.getString(4));
				loc.setState(resultSet.getString(5));
				loc.setPincode(resultSet.getLong(6));
				loc.setContactno(resultSet.getLong(7));
				loc.setZone(resultSet.getString(8));

				loclist.add(loc);
			}

		} catch (SQLException e) {

			System.out.println("Error getting locations");
			System.out.println(e.getMessage());
		}

		// Close the connection to the database - Very important!!!
		try {
			connection.close();
			connection = null;
		} catch (SQLException e) {
			System.out.println("Error closing connection: " + e);
		}

		return loclist;
	}
	public void addEmployee() {

	}
}
