package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Address;
import model.Courier;

public class CourierDAO {

	public Courier createCourier(Courier courier) {
		Connection connection = ConnectToDB.getMySQLConnection();

		// Query to insert a record to the Courier table
		String query = "INSERT INTO mpate117_courier (cust_id,source,destination,type,weight,receivername,receivercontactno,pickupdate,status,amount) VALUES (?, ?, ?, ?,?, ?, ?, ?,?,?) ;";
		// Use prepared statements to avoid SQL injection attacks
		try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			// Set the parameters to the query
			statement.setInt(1, courier.getCust().getCustid());
			statement.setInt(2, courier.getSourceLoc().getAddid());
			statement.setInt(3, courier.getDestLoc().getAddid());
			statement.setString(4, courier.getCourierType());
			statement.setDouble(5, courier.getWeight());
			statement.setString(6, courier.getRecname());
			statement.setLong(7, courier.getReccntctno());
			statement.setDate(8, Date.valueOf(courier.getPickupDate()));
			statement.setString(9, courier.getStatus());
			statement.setDouble(10, courier.getRate());
			// Execute the insert
			statement.executeUpdate();
			// To get the primary key (id) of the newly inserted record
			ResultSet resultSet = statement.getGeneratedKeys();

			if (resultSet.next()) {
				// Set the id field of the database to the model
				courier.setTrackingNo(resultSet.getInt(1));
				// System.out.println("COurier ka id:" +
				// courier.getTrackingNo());

			}
			System.out.println("Courier added successfully");
		} catch (SQLException e) {
			courier = null;
			System.out.println("Error Creating Courier: " + e);
			// e.printStackTrace();
		}
		// Close the connection to the database - Very important!!!
		try {
			connection.close();
			connection = null;
		} catch (SQLException e) {
			System.out.println("Error closing connection: " + e);
		}

		// Return the Courier object that was inserted with the id field set.
		return courier;
	}// end create

	public Courier viewCourierByTrackingNo(Courier courier) {
		Connection connection = ConnectToDB.getMySQLConnection();
		String query = "SELECT temp.street, temp.city,temp.state, temp.zipcode,d.street,d.city,d.state,d.zipcode,temp.type,temp.weight, temp.receivername, temp.receivercontactno,temp.pickupdate,temp.status, temp.amount FROM (SELECT * from mpate117_courier c INNER JOIN mpate117_address s ON c.source = s.addressid) AS temp INNER JOIN mpate117_address d ON temp.destination = d.addressid WHERE trackingno = ?";
		// independent of courier customer.?
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			// Set the parameters to the query
			statement.setInt(1, courier.getTrackingNo());

			// Execute the query
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				courier.setSourceLoc(new Address(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4)));
				courier.setDestLoc(new Address(resultSet.getString(5),resultSet.getString(6),resultSet.getString(7),resultSet.getInt(8)));
				courier.setCourierType(resultSet.getString(9));
				courier.setWeight(resultSet.getDouble(10));
				courier.setRecname(resultSet.getString(11));
				courier.setReccntctno(resultSet.getLong(12));
				courier.setPickupDate(resultSet.getDate(13).toLocalDate());
				courier.setStatus(resultSet.getString(14));
				courier.setRate(resultSet.getDouble(15));
			}
			else
			{
				courier = null;
			}
			
		} catch (SQLException e) {
			courier = null;
			System.out.println("Error fetching Courier: " + e);
			// e.printStackTrace();
		}
		// Close the connection to the database - Very important!!!
		try {
			connection.close();
			connection = null;
		} catch (SQLException e) {
			System.out.println("Error closing connection: " + e);
		}

		// Return the Courier object that was inserted with the id field set.
		return courier;

	}// end view courier
}// end class
