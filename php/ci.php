<?php
	session_start();
	if(empty($_SESSION)){
		header("location: index.php");
		die();
	}
?>
<html>
<head>
	<meta charset="UTF-8">
	<title>Thanh toan</title>
  <style>
	.button {
		background-color: #4CAF50;
		border: none;
		color: white;
		padding: 15px 32px;
		text-align: center;
		text-decoration: none;
		display: inline-block;
		font-size: 16px;
		margin: 4px 2px;
		cursor: pointer;
	}
  </style>
</head>

<body style="font-size:125%;font-family:Arial, Helvetica, sans-serif;">
  <h1>Thanh toan</h1>

  <p>
		Welcome <em><?php echo $_SESSION['username']; ?></em> to the portal!
  </p>
  <?php
	$url = "http://localhost:8080/iBankingPayment/rest/payment/get-data/" . $_SESSION['username'];
	$ch = curl_init();
	curl_setopt($ch, CURLOPT_URL, $url);
	curl_setopt($ch, CURLOPT_POST, 1);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
	$data = curl_exec($ch);
	curl_close($ch);
	$data = json_decode($data);
  ?>
  <?php
	
	if(!empty($_POST['student_id']))
	{
		
		$url="http://localhost:8080/iBankingPayment/rest/payment/get-fee/".$_POST['student_id'];
		$ch=curl_init();
		curl_setopt($ch,CURLOPT_URL,$url);
		curl_setopt($ch,CURLOPT_POST,1);
		curl_setopt($ch,CURLOPT_RETURNTRANSFER,true);
		curl_setopt($ch,CURLOPT_FOLLOWLOCATION,true);
		$resp=curl_exec($ch);
		curl_close($ch);
		$resp=json_decode($resp);
	}
	if(isset($_POST['submit'])&&!empty($data->email)){
			$_POST['email']=$data->email;
			
			$curl = curl_init();
			$url="http://localhost:8080/iBankingPayment/rest/payment/sendOTPcode/".$data->email;

			curl_setopt($curl, CURLOPT_URL, $url);
			curl_setopt($curl, CURLOPT_POST, 1);
			
			curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
			curl_setopt($ch,CURLOPT_FOLLOWLOCATION,true);
		    $resp2 = curl_exec($curl);
		    curl_close($curl);

		    if($resp2 == "true")
		    {
		    	header("location: otpVerify.php");
		    	die();
		    } else {
		    	header("location: payment.php");
		    }
	}
  ?>
  
  <form action="<?php echo htmlspecialchars($_SERVER['PHP_SELF']); ?>" method="post" id="myForm">
	<input type="text" placeholder="Name" name="name" disabled value="<?php echo $data->name ?>"/>
	<br/><br/>
	<input type="text" placeholder="Phone" name="phone" disabled value="<?php echo $data->phone ?>"/>
	<br/><br/>
	<input type="text" placeholder="Email" name="email" disabled value="<?php echo $data->email ?>"/>
	<br/><br/>
	<input type="text" placeholder="Student_ID" id="student_id" name="student_id" value="<?php if(!empty($_POST['student_id']))echo $resp->stId;?>"/>
	<br/><br/>
	<input type="submit" name="login" value="Submit" style="display: none;"/>
	<br/><br/>
	<input type="text" placeholder="Fee" name="school_fee" disabled value="<?php if(empty($_POST['student_id'])) echo 0;
		else echo $resp->scFee; ?>"/>
	<br/><br/>
	<input type="text" placeholder="Balance" name="balance" disabled value="<?php echo $data->balance ?>"/>
	<br/><br/>
	<input type="text" placeholder="So tien can chuyen" name="moneyPay" value=""/>
	<br/><br/>
	<p>So du kha dung >= so tien can chuyen</p>
	<br></br>
	<input type="submit" name="submit"  value="Submit"/>
  </form>
  
  <p>
		Click here to clean <a href="logout.php" tite="Logout">Session</a>
  </p>
</body>
</html>
