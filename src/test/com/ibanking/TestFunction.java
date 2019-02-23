package test.com.ibanking;

import com.ibanking.payment.*;
import java.util.*;

public class TestFunction {

	public static void main(String[] args){
		// TODO Auto-generated method stub
		PaymentService payServices = new PaymentService();
		System.out.println(payServices.OTP(5));
	}

}
