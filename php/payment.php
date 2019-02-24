<!DOCTYPE html>
<html>
<head>
	<title>Info</title>
</head>
<body>
	<?php
		if (isset($_POST['verify']) && !empty($_POST['email'])) {
			$curl = curl_init();

			curl_setopt($curl, CURLOPT_URL, "http://localhost:8080/iBankingPayment/rest/payment/sendOTPcode");
			curl_setopt($curl, CURLOPT_POST, 1);
			curl_setopt($curl, CURLOPT_POSTFIELDS, 
				http_build_query(array('email' => $_POST['email'])));
			curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);

		    $resp = curl_exec($curl);
		    curl_close($curl);

		    if($resp == "true")
		    {
		    	header("location: otpVerify.php");
		    	die();
		    } else {
		    	header("location: payment.php");
		    }
		}
	?>

	<form action="<?php echo htmlspecialchars($_SERVER['PHP_SELF']);?>" method="post">
		Email : <input type="Email" name="email" placeholder="Enter your email">
		<button type="submit" name="verify">
			Verify
		</button>
	</form>
</body>
</html>