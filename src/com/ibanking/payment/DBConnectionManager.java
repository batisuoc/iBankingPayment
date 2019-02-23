package com.ibanking.payment;

import java.sql.*;

public class DBConnectionManager {
	private Connection conn;
	
	public DBConnectionManager() throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver");
		String connectionUrl = "jdbc:mysql://localhost:3306/iBankingPayment";
		String connectionUser = "root";
		String connectionPassword = "";
		this.conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
	}
	
	public Connection getConnection()
	{
		return this.conn;
	}
}
