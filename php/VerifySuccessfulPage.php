<?php session_start(); ?>
<!DOCTYPE html>
<html>
<head>
	<title>Verify Successful</title>
	<?php include('head.php'); ?>
</head>
<body>
	<?php

	if(isset($_POST['verify']))
	{
		unset($_SESSION['student_id']);
		unset($_SESSION['moneypay']);
		unset($_SESSION['balance']);
		unset($_SESSION['otp']);
		header("location: index.php");
		die();
	}

	?>

	<div class="container">
		<div class="d-flex justify-content-center h-100">
			<div class="card">
				<div class="card-header">
					<h3 class="text-center">Verify Successfully !</h3>
				</div>
				<div class="card-body">
					<form class="login-form" method="post" action="<?php echo htmlspecialchars($_SERVER['PHP_SELF']); ?>">
						<div class="form-group">
							<input type="submit" name="verify" value="Click here to back to your portal" class="btn float-right login-btn"></input>
						</div>
					</form>
				</div>
				<div class="card-footer"></div>
			</div>
		</div>
	</div>
</body>
</html>