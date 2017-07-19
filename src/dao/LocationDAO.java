package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Location;

public class LocationDAO {

	public Location createLoc(Location loc) {
		Connection connection = ConnectToDB.getMySQLConnection();

		// Query to insert a record to the location table
		String query = "INSERT INTO mpate117_location (loc_name, address, city, state, pincode, contact_no, zone) VALUES (?, ?, ?, ?, ?, ?, ?) ;";
		// Use prepared statements to avoid SQL injection attacks
		try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			// Set the parameters to the query
			statement.setString(1, loc.getName());
			statement.setString(2, loc.getAddress());
			statement.setString(3, loc.getCity());
			statement.setString(4, loc.getState());
			statement.setLong(5, loc.getPincode());
			statement.setLong(6, loc.getContactno());
			statement.setString(7, loc.getZone());

			// Execute the insert
			statement.executeUpdate();
			// To get the primary key (id) of the newly inserted record
			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				// Set the id field of the database to the model
				loc.setId(resultSet.getInt(1));
			}
			System.out.println("location added successfully");
		} catch (SQLException e) {
			loc = null;
			System.out.println("Error Creating Location: " + e);
		}
		// Close the connection to the database - Very important!!!
		try {
			connection.close();
			connection = null;
		} catch (SQLException e) {
			System.out.println("Error closing connection: " + e);
		}

		// Return the location object that was inserted with the id field set.
		return loc;
	}// end create

	public Location updateLoc(Location loc) {

		Connection connection = ConnectToDB.getMySQLConnection();

		// Query to update a record to the location table
		String query = "UPDATE mpate117_location SET loc_name=?,address=?, city=?, state=?, pincode=?, contact_no=? "
				+ "WHERE loc_id=?";

		// Use prepared statements to avoid SQL injection attacks
		try (PreparedStatement statement = connection.prepareStatement(query)) {

			// Set the parameters to the query
			statement.setString(1, loc.getName());
			statement.setString(2, loc.getAddress());
			statement.setString(3, loc.getCity());
			statement.setString(4, loc.getState());
			statement.setLong(5, loc.getPincode());
			statement.setLong(6, loc.getContactno());
			statement.setInt(7, loc.getId());

			statement.executeUpdate();

			System.out.println("Location updated successfully");

		} catch (SQLException e) {
			loc = null;
			System.out.println("Error updating location");
			e.printStackTrace();
		}
		// Close the connection to the database - Very important!!!
		try {
			connection.close();
			connection = null;
		} catch (SQLException e) {
			System.out.println("Error closing connection: " + e);
		}

		// Return the location object that was updated with the id field set.
		return loc;
	}// end updateLoc()

	public void deleteLoc(Location loc) {
		Connection connection = ConnectToDB.getMySQLConnection();
		try {
			// Statement statement = (Statement) connection.createStatement();
			// Query to delete a record to the location table
			System.out.println("aa walu delete thase:" + loc.getId());
			String query = "DELETE FROM mpate117_location where loc_id = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			if (loc != null) {
				statement.setInt(1, loc.getId());
				statement.executeUpdate();

				System.out.println("Location deleted successfully");
			}

		} catch (SQLException e) {
			System.out.println("Error deleting location");
			e.printStackTrace();
		}
		// Close the connection to the database - Very important!!!
		try {
			connection.close();
			connection = null;
		} catch (SQLException e) {
			System.out.println("Error closing connection: " + e);
		}

	}// end deleteLoc()

}// end class
