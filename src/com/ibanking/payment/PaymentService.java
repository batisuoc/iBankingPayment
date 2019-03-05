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
	private DBConnectionManager dbc = null;
	
	private Session setEmailSession(String email)
	{
		final String username = "hongloc2206@gmail.com";//Change to your email
		final String password = "kimthuong1125";//Change to your email password

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587");

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});
		return session;
	}
	
	public String OTP(int leng)
	{
		// Using numeric values 
        String numbers = "0123456789"; 
  
        // Using random method 
        Random rndm_method = new Random(); 
  
        char[] otp = new char[leng]; 
  
        for (int i = 0; i < leng; i++) 
        { 
            // Use of charAt() method : to get character value 
            // Use of nextInt() as it is scanning the value as int 
        	otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length())); 
        }
        
        return String.valueOf(otp); 
	}
	
	
	
	@POST
	@Path("/sendOTPcode/{email}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean sendOPT(@PathParam("email") String email)
	{
		
		Session temp = setEmailSession(email);
		if(temp != null)
		{	
//			System.out.println("abc");
			String otpCode = OTP(5);
			try {
//				System.out.println("ef");
				Message message = new MimeMessage(temp);
				message.setFrom(new InternetAddress("batisuoc@gmail.com"));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
				message.setSubject("OTP verify code");
				message.setText("Dear " + email + ","
						+ "\n\n Your verify code is " + otpCode + " ."
						+ "\n\nRegards,"
						+ "\niBanking.");

				Transport.send(message);
//				System.out.println("Done");

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
			
			return true;
		}
		else
		{
			return false;
		}
	}
	
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

				dbc = new DBConnectionManager();
				conn = dbc.getConnection();
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT * FROM account");
				while (rs.next())
				{
					// Write your code here
					String u = rs.getString("bank_id");
					String p = rs.getString("password");
					if(username.equals(u) && password.equals(p))
					{
						return true;
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
				dbc = new DBConnectionManager();
				conn = dbc.getConnection();
				Class.forName("com.mysql.jdbc.Driver");
				String connectionUrl = "jdbc:mysql://localhost:3306/iBankingPayment";
				String connectionUser = "root";
				String connectionPassword = "";
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
				dbc = new DBConnectionManager();
				conn = dbc.getConnection();
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT *"
						+ "FROM student"
								);
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
