package com.pace.library.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
    //New instances of Connection
	private static Connection connection=null;
	//Opening connection with MySQL database
	public static Connection createConnection() throws ClassNotFoundException,SQLException{
		String url,userId,passWord;
		url="jdbc:mysql://@localhost:3306/library";
		userId="root";
		passWord="Sanjana#29";
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection=DriverManager.getConnection(url,userId,passWord);
		return connection;
	}
	//Closing connection
	public static void closeConnection() throws SQLException{
		connection.close();
	}
}
