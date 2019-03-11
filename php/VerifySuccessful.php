<!DOCTYPE html>
<html>
<head>
	<title>Verify Successful</title>
	<!--Bootsrap 4 CDN-->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

	<!--Fontawesome CDN-->
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
	<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
	<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

	<!--Custom styles-->
	<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
	<?php

	if(isset($_POST['verify']))
	{
		header("location: ci.php");
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