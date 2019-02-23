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
	
	private Session setEmailSession(String email)
	{
		final String username = "batisuoc@gmail.com";//Change to your email
		final String password = "619BatisUoc";//Change to your email password

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
	@Path("/sendOTPcode")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean sendOPT(@FormParam("email") String email)
	{
		Session temp = setEmailSession(email);
		if(temp != null)
		{
			String otpCode = OTP(5);
			try {

				Message message = new MimeMessage(temp);
				message.setFrom(new InternetAddress("batisuoc@gmail.com"));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
				message.setSubject("OTP verify code");
				message.setText("Dear " + email + ","
						+ "\n\n Your verify code is " + otpCode + " ."
						+ "\n\nRegards,"
						+ "\niBanking.");

				Transport.send(message);

				System.out.println("Done");

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
	
	@POST
	@Path("/sign-in/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean signIn(@FormParam("username") String username, @FormParam("password") String password)
	{

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			DBConnectionManager dbConn = new DBConnectionManager();
			conn = dbConn.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM account");
			while (rs.next()) {
				// Write your code here
				String u = rs.getString("bank_id");
				String p = rs.getString("password");
				if (username.equals(u) && password.equals(p)) {
					return true;
				}
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	private void emailHandler(String email)
	{
		try {

			Message message = new MimeMessage(setEmailSession(email));
			message.setFrom(new InternetAddress("batisuoc@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject("Login Confirmation on DHPIT iBanking on " + getDatetime());
			message.setText("Dear " + email + ","
					+ "\n\nThis email is to announce that your username/password has been signed in to iBanking website on " + getDatetime() + "."
					+ "\n\nRegards,"
					+ "\niBanking.");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
