<?php

session_start();

if(isset($_POST['submit']))
{
	if(isset($_POST['email']) && isset($_POST['student_id']) && isset($_POST['school_fee']) && isset($_POST['moneypay']) && isset($_POST['balance'])){

		$_SESSION['email'] 		= $_POST['email'];
		$_SESSION['student_id'] = $_POST['student_id'];
		$_SESSION['moneypay']	= $_POST['moneypay'];
		$_SESSION['balance']	= $_POST['balance'];

		$curl = curl_init();
		$url="http://localhost:8080/iBankingPayment/rest/payment/send_and_check_otp/".$_SESSION['email'];

		curl_setopt($curl, CURLOPT_URL, $url);
		curl_setopt($curl, CURLOPT_POST, 1);

		curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
		curl_setopt($ch,CURLOPT_FOLLOWLOCATION,true);
		$resp = curl_exec($curl);
		curl_close($curl);

		if($resp == "true")
		{
			header("location: otpVerify.php");
			die();
		} else {
			header("location: ci.php");
		}
	}
}

?>