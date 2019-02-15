package com.ibanking.payment;

import java.text.*;
import java.util.*;
import java.util.Date;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import com.sun.xml.internal.ws.client.sei.ResponseBuilder;
import javax.mail.*;
import javax.mail.internet.*;
import java.sql.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/payment")
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
	public Response signIn(@FormParam("username") String username, @FormParam("password") String password)
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
				while (rs.next())
				{
					// Write your code here
					String u = rs.getString("bank_id");
					String p = rs.getString("password");
					if(username.equals(u) && password.equals(p))
					{
//						emailHandler(e);
						return Response.temporaryRedirect(new URI("https://www.google.com/")).build();
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

			return Response.temporaryRedirect(new URI("http://localhost:8080/iBankingPayment/login/")).build();
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
		
		
		// Instead of returning boolean value, you can redirect client to another site if successfully signed in.
		// return Response.temporaryRedirect(new URI("http://localhost:8081/ibanking/")).build();
	}
}
