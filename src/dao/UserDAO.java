package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Admin;
import model.Customer;
import model.User;

public class UserDAO {

	public boolean checkRole(User usr, String tbl) {
		boolean flag = false;
		String username = usr.getUsername();
		String password = usr.getPassword();
		System.out.println("before");
		Connection connect = ConnectToDB.getMySQLConnection();
		System.out.println("after" + connect);
		try {
			Statement statement = (Statement) connect.createStatement();
			String query = "SELECT * FROM MPATE117_" + tbl + " ;";
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				String usernamedb = resultSet.getString("username");
				String passworddb = resultSet.getString("password");
				if (username.equals(usernamedb) && password.equals(passworddb)) {
					flag = true;
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		// Close the connection to the database - Very important!!!
		try {
			connect.close();
			connect = null;
		} catch (SQLException e) {
			System.out.println("Error closing connection: " + e);
		}
		return flag;
	}// end checkRole
	
	public Admin getAdmin(User user) {
		Connection connection = ConnectToDB.getMySQLConnection();
		Admin admin = new Admin();
		try {
			Statement statement = (Statement) connection.createStatement();
			String query = "SELECT * FROM MPATE117_ADMIN WHERE username='" + user.getUsername() + "'";
			ResultSet resultSet = statement.executeQuery(query);

			if (resultSet.next()) {
				admin.setID(resultSet.getInt(1));
				admin.setUsername(resultSet.getString(2));
				admin.setPassword(resultSet.getString(3));
				admin.setZone(resultSet.getString(4));
			}

		} catch (SQLException e) {
			admin = null;
			System.out.println("Error getting admin");
			System.out.println(e.getMessage());
		} // Close the connection to the database - Very important!!!
		try {
			connection.close();
			connection = null;
		} catch (SQLException e) {
			System.out.println("Error closing connection: " + e);
		}

		return admin;

	}// end getAdmin


	public Customer getCustomer(User user) {
		Connection connection = ConnectToDB.getMySQLConnection();
		Customer cust = new Customer();
		try {
			Statement statement = (Statement) connection.createStatement();
			String query = "SELECT * FROM MPATE117_Customer WHERE username='" + user.getUsername() + "'";
			ResultSet resultSet = statement.executeQuery(query);

			if (resultSet.next()) {
				cust.setCustid(resultSet.getInt(1));
				cust.setFname(resultSet.getString(2));
				cust.setLname(resultSet.getString(3));
				cust.setUsername(resultSet.getString(4));
				cust.setPassword(resultSet.getString(5));
				cust.setEmail(resultSet.getString(6));
				cust.setContactno(resultSet.getLong(7));
			}

		} catch (SQLException e) {
			cust = null;
			System.out.println("Error getting customer");
			System.out.println(e.getMessage());
		}
		// Close the connection to the database - Very important!!!
		try {
			connection.close();
			connection = null;
		} catch (SQLException e) {
			System.out.println("Error closing connection: " + e);
		}

		return cust;

	}// end getCustomer

}// end UserDAO
