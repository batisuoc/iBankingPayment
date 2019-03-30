<?php 
ob_start();
session_start();
?>
	
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Login</title>
	<?php include('head.php'); ?>
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
			header("location: index.php");
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
				<div class="card-footer"></div>
			</div>
		</div>
	</div>
</body>
</html>