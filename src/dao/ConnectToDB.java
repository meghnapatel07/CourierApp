package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToDB {
	// Connection object
		private  static Connection connection;
		// Database connection parameters
		private static String url = "jdbc:mysql://www.papademas.net:3306/dbfp";
		private static String username = "fpuser";
		private static String password = "510";

		public static Connection getMySQLConnection() {
			connection = null;
			try {
				// This will load the MySQL driver, each DB has its own driver
				Class.forName("com.mysql.jdbc.Driver");
				// Setup the connection with the DB
				connection = DriverManager.getConnection(url, username, password);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				System.out.println("Error creating connection to database: " + e);
				System.exit(-1);
			}
			catch(ClassNotFoundException e)
			{	System.out.println("cnf exception");
				System.out.println(e.getMessage());	
			}
			catch (Exception e) {
				System.out.println(" exception");
				System.out.println(e.getMessage());
			}
			return connection;
					
		}// end getMySQL connection
		
		
		
}// end class
