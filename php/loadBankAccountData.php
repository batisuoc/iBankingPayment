<?php

ob_start();
session_start();

if(!empty($_SESSION['username']))
{
	$url = "http://localhost:8080/iBankingPayment/rest/payment/get-data/".$_SESSION['username'];
	$ch = curl_init();
	curl_setopt($ch, CURLOPT_URL, $url);
	curl_setopt($ch, CURLOPT_POST, 1);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, 0);
	curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
	$data = curl_exec($ch);
	curl_close($ch);
	$jsonData = json_encode($data);
	$jsonData = str_replace("true", "", $jsonData);
	echo $jsonData;
}

?>