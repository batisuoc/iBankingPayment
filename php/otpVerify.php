<?php

session_start();

$msg = "";
if(isset($_POST['verify']) and !empty($_POST['otp']))
{
	$ch = curl_init();
	curl_setopt($ch, CURLOPT_URL, "http://localhost:8080/iBankingPayment/rest/payment/verifyOTP/" .$_POST['otp']);
	curl_setopt($ch, CURLOPT_POST, 1);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
	$resp = curl_exec($ch);
	curl_close($ch);
	if($resp == "true")
	{
		$_SESSION['otp'] = $_POST['otp'];
		header("location: action_after_verified.php");
		die();
	}
	else
	{
		$msg = "Verify failed , please send another OTP to verify again.";
	}
}

?>