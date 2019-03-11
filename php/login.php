	<?php 
	ob_start();
	session_start();
	?>
	<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

	<!DOCTYPE html>
	<html>
	<head>
		<meta charset="UTF-8">
		<title>Login</title>
		<!--Made with love by Mutiullah Samim -->
   
	<!--Bootsrap 4 CDN-->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    
    <!--Fontawesome CDN-->
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">

	<!--Custom styles-->
		<link rel="stylesheet" type="text/css" href="style.css">

	</head>
	<body>
		<?php
		if(!empty($_SESSION)){
			header("refresh: 3; url=ci.php");
			echo 'User has been signed in. Redirecting to portal...;';
			die();
		}
		$msg='';
		if(isset($_POST['login'])&&!empty($_POST['username'])&&!empty($_POST['password'])){
			$ch=curl_init();
			curl_setopt($ch, CURLOPT_URL,"http://localhost:8080/iBankingPayment/rest/payment/sign-in");
			curl_setopt($ch, CURLOPT_POST, 1);
			curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query(array('username' => $_POST['username'],
				'password' => $_POST['password'])));
				//Receive server's response
			curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
			$server_output = curl_exec($ch);
			curl_close($ch);
			if($server_output=="true"){
				$_SESSION['valid'] = true;
				$_SESSION['timeout'] = time();
				$_SESSION['username']=$_POST['username'];
				header("location: ci.php");
				die();

			}
			else{
				$msg = 'You have entered an invalid username or password!';
			}
		}
		?>
		
		<div class="container">
	<div class="d-flex justify-content-center h-100">
		<div class="card">
			<div class="card-header">
				<h3>Sign In</h3>
				<div class="d-flex justify-content-end social_icon">
					<span><i class="fab fa-facebook-square"></i></span>
					<span><i class="fab fa-google-plus-square"></i></span>
					<span><i class="fab fa-twitter-square"></i></span>
				</div>
			</div>
			<div class="card-body">
				<form class="login-form" method="post" action="<?php echo htmlspecialchars($_SERVER['PHP_SELF']); ?>">
					<div class="input-group form-group">
						<div class="input-group-prepend">
							<span class="input-group-text"><i class="fas fa-user"></i></span>
						</div>
						<input type="text" placeholder="username" name="username" class="form-control" required autofocus/>
						
					</div>
					<div class="input-group form-group">
						<div class="input-group-prepend">
							<span class="input-group-text"><i class="fas fa-key"></i></span>
						</div>
						<input type="password" placeholder="password" name="password" class="form-control" required/>
					</div>
					
					<div class="form-group">
						<input type="submit" name="login" value="Đăng nhập" class="btn float-right login-btn"></input>
					</div>
				</form>
			</div>
			<div class="card-footer">
				
			</div>
		</div>
	</div>
</div>
	</body>
	</html>