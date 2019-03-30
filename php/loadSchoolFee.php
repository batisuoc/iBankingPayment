<?php

if(!empty($_POST['stuID']) && !empty($_POST['username']))
{
	// echo $_GET['stuID'];
	$url="http://localhost:8080/iBankingPayment/rest/payment/get-fee/".$_POST['stuID']."/".$_POST['username'];
	$ch=curl_init();
	curl_setopt($ch,CURLOPT_URL,$url);
	curl_setopt($ch,CURLOPT_POST,1);
	curl_setopt($ch,CURLOPT_RETURNTRANSFER,0);
	curl_setopt($ch,CURLOPT_FOLLOWLOCATION,true);
	$studentData = curl_exec($ch);
	curl_close($ch);
	$jsonData = json_encode($studentData);
	$jsonData = str_replace("true", "", $jsonData);
	echo $jsonData;
}
?>