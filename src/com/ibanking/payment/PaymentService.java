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
}
