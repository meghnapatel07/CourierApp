package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Address;

public class AddressDAO {

	public Address createAdd(Address add) {
		Connection connection = ConnectToDB.getMySQLConnection();

		// Query to insert a record to the Address table
		String query = "INSERT INTO mpate117_address (street, city, state, zipcode) VALUES (?, ?, ?, ?) ;";
		// Use prepared statements to avoid SQL injection attacks
		try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			// Set the parameters to the query
			statement.setString(1, add.getStreet());
			statement.setString(2, add.getCity());
			statement.setString(3, add.getState());
			statement.setInt(4, add.getZipcode());
	
			// Execute the insert
			statement.executeUpdate();
			// To get the primary key (id) of the newly inserted record
			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				// Set the id field of the database to the model
				add.setAddid(resultSet.getInt(1));
			}
			System.out.println("Address added successfully");
		} catch (SQLException e) {
			add = null;
			System.out.println("Error Creating Address: " + e);
		}
		// Close the connection to the database - Very important!!!
		try {
			connection.close();
			connection = null;
		} catch (SQLException e) {
			System.out.println("Error closing connection: " + e);
		}

		// Return the Address object that was inserted with the id field set.
		return add;
	}// end create

	
}
