package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoadDriver {
	public static void main(String[] args) {
		try {
			// The newInstance() call is a work around for some
			// broken Java implementations
			Object className = Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println(className);
		} catch (Exception e) {
			// handle the error
			e.printStackTrace();
		}

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {

			// db parameters
			// on of the limitation of MySQL Connector/J is the returned value of timestamp
			// to prevent it pass a serverTimezone property in the JDBC connection string
			// directly
			String url = "jdbc:mysql://localhost:3307/studentinfosys?serverTimezone=UTC";
			String user = "root";
			String password = "rfV7h@123";

			// create a connection to the database
			conn = DriverManager.getConnection(url, user, password);

			stmt = conn.createStatement();
			stmt.executeQuery("SELECT * FROM student;");
			rs = stmt.getResultSet();
			System.out.println("stmt size: "+stmt.getFetchSize());
			while(rs.next()) {
				System.out.println(rs.getString(2));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			// it is a good idea to release
			// resources in a finally{} block
			// in reverse-order of their creation
			// if they are no-longer needed

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore

				stmt = null;
			}
		}
	}
}
