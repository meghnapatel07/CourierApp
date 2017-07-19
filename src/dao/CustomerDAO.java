package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Customer;


public class CustomerDAO {

		public Customer addCustomer(Customer cust) {
		Connection connection = ConnectToDB.getMySQLConnection();

		// Query to insert a record to the location table
		String query = "INSERT INTO mpate117_customer (fname, lname,username, password,email, contactno) VALUES (?, ?, ?, ?, ?, ?) ;";
		// Use prepared statements to avoid SQL injection attacks
		try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			// Set the parameters to the query
			statement.setString(1, cust.getFname());
			statement.setString(2, cust.getLname());
			statement.setString(3, cust.getUsername());
			statement.setString(4, cust.getPassword());
			statement.setString(5, cust.getEmail());
			statement.setLong(6, cust.getContactno());
			

			// Execute the insert
			statement.executeUpdate();
			// To get the primary key (id) of the newly inserted record
			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				// Set the id field of the database to the model
				cust.setCustid(resultSet.getInt(1));
			}
			System.out.println("Customer added successfully");
		} catch (SQLException e) {
			cust = null;
			System.out.println("Error Creating Customer: " + e);
			e.printStackTrace();
		}
		// Close the connection to the database - Very important!!!
		try {
			connection.close();
			connection = null;
		} catch (SQLException e) {
			System.out.println("Error closing connection: " + e);
		}

		// Return the location object that was inserted with the id field set.
		return cust;

	}// end of create

	public Customer updateCustomer(Customer c) {
		Connection connection = ConnectToDB.getMySQLConnection();

		// Query to update a record to the location table
		String query = "UPDATE mpate117_customer SET fname=?, lname=?, email=?, contactno=? "
				+ "WHERE cust_id=?";

		// Use prepared statements to avoid SQL injection attacks
		try (PreparedStatement statement = connection.prepareStatement(query)) {

			// Set the parameters to the query
			statement.setString(1, c.getFname());
			statement.setString(2, c.getLname());
			statement.setString(3, c.getEmail());
			statement.setLong(4, c.getContactno());
			statement.setLong(5, c.getCustid());
			
			statement.executeUpdate();

			System.out.println("Customer updated successfully");

		} catch (SQLException e) {
			c = null;
			System.out.println("Error updating customer");
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
		return c;

	}

}
