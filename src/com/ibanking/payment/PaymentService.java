package com.ibanking.payment;

import java.text.*;
import java.util.*;
import java.util.Date;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.sql.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/payment/")
public class PaymentService {
	
	@GET
	@Path("/get-datetime")
	@Produces(MediaType.TEXT_PLAIN)
	public String getDatetime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy hh:mm:ss aa");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	@POST
	@Path("/sign-in/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean signIn(@FormParam("username") String username, @FormParam("password") String password)
	{
		try {
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String connectionUrl = "jdbc:mysql://localhost:3306/iBankingPayment";
				String connectionUser = "root";
				String connectionPassword = "";
				conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT * FROM account");
				System.out.println("connect");
				while (rs.next())
				{
					// Write your code here
					String u = rs.getString("bank_id");
					String p = rs.getString("password");
					URI uri = new java.net.URI("../thanhtoan");
					if(username.equals(u) && password.equals(p))
					{
//						emailHandler(e);
						System.out.println("asd");
						/*rs = stmt.executeQuery("select *"
								+ "from bank_account"
								+ "join account"
								+ "on bank_account.bank_id = account.bank_id"
								+ "");*/
						return true;
					
						//return Response.temporaryRedirect(uri).build();
					}
				}
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
				try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
				try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
			}

			//return Response.temporaryRedirect(new URI("http://localhost:8080/iBankingPayment/login/")).build();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return null;
		System.out.println(username+""+password);
		return false;
		
		// Instead of returning boolean value, you can redirect client to another site if successfully signed in.
		// return Response.temporaryRedirect(new URI("http://localhost:8081/ibanking/")).build();
	}
	@POST
	@Path("/get-data/{username}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public BankAccount getBankAccount(@PathParam("username") String id) {
		BankAccount bankAccount = new BankAccount();
		try {
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String connectionUrl = "jdbc:mysql://localhost:3306/iBankingPayment";
				String connectionUser = "root";
				String connectionPassword = "";
				System.out.println("eeee");
				conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT *"
						+ "FROM bank_account"
								);
				System.out.println("connect");
				while (rs.next())
				{
					// Write your code here
					String u = rs.getString("bank_id");
					if(id.equals(u))
					{
						System.out.println("asd");
						bankAccount.setBankId(rs.getString("bank_id"));
						bankAccount.setName(rs.getString("name"));
						bankAccount.setPhone(rs.getInt("phone"));
						bankAccount.setEmail(rs.getString("email"));
						bankAccount.setBalance(rs.getInt("balance"));
						return bankAccount;
					}
				}
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
				try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
				try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(id);
		
		return bankAccount;
	}
	@POST
	@Path("/get-fee/{student_id}/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Student getFee(@PathParam("student_id") int st_id) {
		Student st = new Student();
		System.out.println(st_id);
		try {
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String connectionUrl = "jdbc:mysql://localhost:3306/iBankingPayment";
				String connectionUser = "root";
				String connectionPassword = "";
				System.out.println("bbbb");
				conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT *"
						+ "FROM student"
								);
				System.out.println("connect");
				System.out.println(st_id);
				while (rs.next())
				{
					// Write your code here
					if(st_id==(rs.getInt("student_id")))
					{
						st.setScFee(rs.getInt("school_fee"));
						st.setStId(rs.getInt("student_id"));
						return st;
					}
				}
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
				try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
				try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return st;
	}
}
