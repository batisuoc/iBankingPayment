package com.ibanking.payment;

import java.text.*;
import java.util.*;
import java.util.Date;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.ibanking.database.DBConnectionManager;
import com.ibanking.object.*;

import javax.mail.*;
import javax.mail.internet.*;
import java.sql.*;
//import java.io.*;
//import java.net.URI;
//import java.net.URISyntaxException;

@Path("/payment/")
public class PaymentService {
	private Statement stmt = null;
	private ResultSet rs = null;
	private Connection conn = null;
	private PreparedStatement pstmt = null;

	private Session setEmailSession(String email) {
		final String username = "hongloc2206@gmail.com";// Change to your email
		final String password = "kimthuong1125";// Change to your email password

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		return session;
	}

	public String createOTP(int leng) {
		// Using numeric values
		String numbers = "0123456789";

		// Using random method
		Random rndm_method = new Random();

		char[] otp = new char[leng];

		for (int i = 0; i < leng; i++) {
			// Use of charAt() method : to get character value
			// Use of nextInt() as it is scanning the value as int
			otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
		}

		return String.valueOf(otp);
	}

	private boolean insertOTPcode(String otpCode) {
		try {
			Connection conn = new DBConnectionManager().getConnection();
			String sql = "insert into otp values(?,?,?)";
			PreparedStatement stm = conn.prepareStatement(sql);
			stm.setString(1, otpCode);
			stm.setInt(2, 0);
			stm.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			stm.execute();
			conn.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean sendOTPcode(String email, String otpCode) {
		Session temp = setEmailSession(email);
		if (temp != null) {
			try {
				Message message = new MimeMessage(temp);
				message.setFrom(new InternetAddress("hongloc2206@gmail.com"));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
				message.setSubject("OTP verify code");
				message.setText("Dear " + email + "," + "\n\n Your verify code is " + otpCode + " ." + "\n\nRegards,"
						+ "\niBanking.");

				Transport.send(message);
//				System.out.println("Done");
				return true;
			} catch (MessagingException e) {
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean sendVerifySuccessfulEmail(String email) {
		Session temp = setEmailSession(email);
		if (temp != null) {
			try {
				Message message = new MimeMessage(temp);
				message.setFrom(new InternetAddress("hongloc2206@gmail.com"));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
				message.setSubject("Transaction verify successful");
				message.setText("Dear " + email + "," + "\n\n Your transaction is successfully verified." + "\n\nRegards,"
						+ "\niBanking.");

				Transport.send(message);
//				System.out.println("Done");
				return true;
			} catch (MessagingException e) {
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean updateSchoolFee(String student_id, String username) {
		try {
			try {
				conn = new DBConnectionManager().getConnection();
				pstmt = conn.prepareStatement("select * from student where student_id='" + student_id + "';");
				rs = pstmt.executeQuery();
				while (rs.next()) {
					if (rs.getString(1).equals(username) && rs.getString(2).equals(student_id)) {
						pstmt.executeUpdate(
								"UPDATE student " + "SET school_fee = 0" + " WHERE student_id='" + student_id + "';");
						return true;
					}
				}
			} finally {
				try {
					if (rs != null)
						rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					if (pstmt != null)
						pstmt.close();
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateBalance(String username, int balance, int sended_money) {
		int updateBalance = balance - sended_money;
		try {
			try {
				conn = new DBConnectionManager().getConnection();
				pstmt = conn.prepareStatement("select * from bank_account where bank_id='" + username + "';");
				rs = pstmt.executeQuery();
				while (rs.next()) {
					if (rs.getString(1).equals(username)) {
						pstmt.executeUpdate("UPDATE bank_account " + "SET balance = " + updateBalance
								+ " WHERE bank_id='" + username + "';");
						return true;
					}
				}
			} finally {
				try {
					if (rs != null)
						rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					if (pstmt != null)
						pstmt.close();
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateOTPstatus(String otpCode) {
		try {
			try {
				conn = new DBConnectionManager().getConnection();
				pstmt = conn.prepareStatement("select * from otp where otp_code='" + otpCode + "'");
				rs = pstmt.executeQuery();
				while (rs.next()) {
					if (rs.getString(1).equals(otpCode)) {
						pstmt.executeUpdate("UPDATE otp " + "SET status = 1 " + "WHERE otp_code='" + otpCode + "';");
						return true;
					}
				}
			} finally {
				try {
					if (rs != null)
						rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					if (pstmt != null)
						pstmt.close();
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@GET
	@Path("/get-datetime")
	@Produces(MediaType.TEXT_PLAIN)
	public String getDatetime() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy hh:mm:ss aa");
		Date date = new Date();
		return dateFormat.format(date);
	}

	@POST
	@Path("/sign-in/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean signIn(@FormParam("username") String username, @FormParam("password") String password) {
		try {
			try {
				conn = new DBConnectionManager().getConnection();
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
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(username+""+password);
		return false;

		// Instead of returning boolean value, you can redirect client to another site
		// if successfully signed in.
		// return Response.temporaryRedirect(new
		// URI("http://localhost:8081/ibanking/")).build();
	}

	@POST
	@Path("/get-data/{username}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public BankAccount getBankAccount(@PathParam("username") String id) {
		BankAccount bankAccount = new BankAccount();
		try {
			try {
				conn = new DBConnectionManager().getConnection();
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT * FROM bank_account;");
//				System.out.println("connect");
				while (rs.next()) {
					// Write your code here
					String u = rs.getString("bank_id");
					if (id.equals(u)) {
						bankAccount.setBankId(rs.getString("bank_id"));
						bankAccount.setName(rs.getString("name"));
						bankAccount.setPhone(rs.getString("phone"));
						bankAccount.setEmail(rs.getString("email"));
						bankAccount.setBalance(rs.getInt("balance"));
						return bankAccount;
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
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(id);

		return bankAccount;
	}

	@POST
	@Path("/get-fee/{student_id}/{username}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Student getFee(@PathParam("student_id") int st_id, @PathParam("username") String username) {
		Student st = new Student();
//		System.out.println(st_id);
		try {
			try {
				conn = new DBConnectionManager().getConnection();
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT * FROM student");
				while (rs.next()) {
					// Write your code here
					if (st_id == (rs.getInt("student_id")) && username.equals(rs.getString("bank_id"))) {
						st.setScFee(rs.getInt("school_fee"));
						st.setStId(rs.getInt("student_id"));
						return st;
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
		} catch (Exception e) {
			e.printStackTrace();
		}

		return st;
	}

	// Insert otpcode into otp and send mail
	@POST
	@Path("/send_and_check_otp/{email}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean checkInsertSendOTP(@PathParam("email") String email) {
		String otpCode = createOTP(5);
		if (sendOTPcode(email, otpCode) && insertOTPcode(otpCode)) {
			return true;
		}
		return false;
	}

	@POST
	@Path("/verifyOTP/{otpcode}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean verifyOTP(@PathParam("otpcode") String otpcode) {
		try {
			try {
				conn = new DBConnectionManager().getConnection();
				pstmt = conn.prepareStatement("select * from otp where otp_code='" + otpcode + "'");
				rs = pstmt.executeQuery();
				while (rs.next()) {
					if (rs.getString(1).equals(otpcode)) {
						Timestamp temporary = new Timestamp(System.currentTimeMillis());
						long milliseconds = temporary.getTime() - rs.getTimestamp(3).getTime();
						int seconds = (int) milliseconds / 1000;
						int hours = seconds / 3600;
						int minutes = (seconds % 3600) / 60;
						if (hours == 0 && minutes < 26) {
							return true;
						}
					}
				}
			} finally {
				try {
					if (rs != null)
						rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					if (pstmt != null)
						pstmt.close();
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@POST
	@Path("/afterVerifyOTP/{username}/{student_id}/{email}/{moneypay}/{balance}/{otp}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean afterOTPverifyAction(@PathParam("username") String username,
			@PathParam("student_id") String student_id, @PathParam("email") String email,
			@PathParam("moneypay") int sended_money, @PathParam("balance") int balance,
			@PathParam("otp") String otpCode) {
		if (updateBalance(username, balance, sended_money) && updateSchoolFee(student_id, username)) {
			if (sendVerifySuccessfulEmail(email)) {
				if (updateOTPstatus(otpCode)) {
					return true;
				}
			}
		}
		return false;
	}

}
