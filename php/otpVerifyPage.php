<!DOCTYPE html>
<html>
<head>
	<title>Verify OTP Code</title>
	<?php include('head.php'); ?>
</head>
<body>
	<div class="container">
		<div class="d-flex justify-content-center h-100">
			<div class="card">
				<div class="card-header">
					<h3 class="text-center">Verify otpcode</h3>
				</div>
				<div class="card-body">
					<form class="login-form" method="post" action="otpVerify.php">
						<div class="input-group form-group">
							<div class="input-group-prepend"></div>
							<input type="text" name="otp" placeholder="Enter your otp code" class="form-control" required autofocus/>
						</div>
						<div class="form-group">
							<input type="submit" name="verify" value="verify" class="btn float-right login-btn"></input>
							<p class="btn-link"><a href="otpVerify.php">Send OTP again</a></p>
						</div>
					</form>
				</div>
				<div class="card-footer">
					<p class="alert"><? $msg ?></p>
				</div>
			</div>
		</div>
	</div>
</body>
</html>