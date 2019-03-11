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
	<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
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
	<!-- autocomplete School Fee  -->
	<script type="text/javascript">
		$(document).ready(function() {
			$('#student_id').change(function() {
				var studentID = document.getElementById('student_id').value;
				var xmlhttp = new XMLHttpRequest();
				var studentData;
				xmlhttp.onreadystatechange = function () {
					if(this.readyState == 4 && this.status == 200)
					{
						studentData = JSON.parse(this.responseText);
						console.log(this.responseText);
						document.getElementById("school_fee").value = studentData.scFee;
					}
				};
				xmlhttp.open("POST", "loadSchoolFee.php", true);
				xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
				xmlhttp.send("stuID=" + studentID);
			});
		});
	</script>
</head>

<body style="font-size:125%;font-family:Arial, Helvetica, sans-serif;">
	<h1>Thanh toan</h1>

	<p>
		Welcome <em><?php echo $_SESSION['username']; ?></em> to the portal!
	</p>

	<?php
	$url = "http://localhost:8080/iBankingPayment/rest/payment/get-data/".$_SESSION['username'];
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
	if(isset($_POST['submit'])&&!empty($data->email)){
		
		$_POST['email']=$data->email;

		$curl = curl_init();
		$url="http://localhost:8080/iBankingPayment/rest/payment/sendOTPcode/".$data->email;

		curl_setopt($curl, CURLOPT_URL, $url);
		curl_setopt($curl, CURLOPT_POST, 1);

		curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
		curl_setopt($ch,CURLOPT_FOLLOWLOCATION,true);
		curl_exec($curl);
		curl_close($curl);
		header("location: otpVerify.php");
		die();
		
	}
	?>
	


	<form  method="post" action="<?php echo htmlspecialchars($_SERVER['PHP_SELF']); ?>">
		<input type="text" id="name" name="name" disabled value="<?php echo $data->name ?>"/>
		<br/><br/>
		<input type="text" id="phone" name="phone" disabled value="<?php echo $data->phone ?>"/>
		<br/><br/>
		<input type="text" id="email" name="email" disabled value="<?php echo $data->email ?>"/>
		<br/><br/>
		<input type="text" placeholder="Hãy nhập mã sinh viên" id="student_id" name="student_id"/>
		<br/><br/>
		<input type="text" placeholder="Số tiền cần nộp" id="school_fee" name="school_fee" disabled/>
		<br/><br/>
		<input type="text" id="balance" name="balance" disabled value="<?php echo $data->balance ?>"/>
		<br/><br/>
		<input type="text" id="moneypay" placeholder="Số tiền cần chuyển" name="moneyPay"/>
		<br/><br/>
		<p>! Lưu ý : số tiền cần nộp cần phải lớn hơn hoặc bằng tiền học phí</p>
		<input type="submit" name="submit" value="submit"/>
	</form>
	<p>
		Click here to clean <a href="logout.php" tite="Logout">Logout</a>
	</p>
</body>
</html>
