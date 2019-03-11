<?php
	session_start();
?>
<!DOCTYPE html>
<html>
<head>
	<title>Verify OTP Code</title>
</head>
<body>
	<?php
		$resp="";
		if(isset($_POST['verify']) and !empty($_POST['otp']))
		{
			$ch = curl_init();
			curl_setopt($ch, CURLOPT_URL, "http://localhost:8080/iBankingPayment/rest/payment/verifyOTP/" .$_POST['otp']);
			curl_setopt($ch, CURLOPT_POST, 1);
			curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
			curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
			$resp = curl_exec($ch);
			curl_close($ch);
			echo $resp;
		}	
    ?>

	<form action="<?php echo htmlspecialchars($_SERVER['PHP_SELF']); ?>" method="post">
		<input type="text" name="otp" placeholder="Enter your otp code">
		<input type="submit" name="verify"/>
	</form>
</body>
</html>