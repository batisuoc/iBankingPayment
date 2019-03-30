<?php

session_start();


$ch = curl_init();
curl_setopt($ch, CURLOPT_URL, "http://localhost:8080/iBankingPayment/rest/payment/afterVerifyOTP/".$_SESSION['username']."/".$_SESSION['student_id']."/".$_SESSION['email']."/".$_SESSION['moneypay']."/".$_SESSION['balance']."/".$_SESSION['otp']);
curl_setopt($ch, CURLOPT_POST, 1);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
$resp = curl_exec($ch);
if($resp == "true")
{
	header("location: VerifySuccessfulPage.php");
	die();
}
else
{
	header("refresh: 3; url=index.php");
	echo 'Transaction failed for some reason, please do another transaction.';
	die();
}


?>