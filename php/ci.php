<?php
session_start();
if(empty($_SESSION)){
	header("location: login.php");
	die();
}
?>
<html>
<head>
	<meta charset="UTF-8">
	<title>Thanh toan</title>
	<!--Bootsrap 4 CDN-->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

	<!--Fontawesome CDN-->
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
	<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
	<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

	<!--Custom styles-->
	<link rel="stylesheet" type="text/css" href="style.css">

	<!-- Jquery -->
	<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<style>
		button {
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
	
	<script type="text/javascript">
		function getBankAccountData() {
			var xmlhttp = new XMLHttpRequest();
			var bankData;
			xmlhttp.onreadystatechange = function() {
				// location.replace("loadBankAccountData.php");
				if(this.readyState == 4 && this.status == 200)
				{
					bankData = JSON.parse(this.responseText);
					console.log(this.responseText);
					document.getElementById("realname").value = bankData.name;
					document.getElementById("phone").value = bankData.phone;
					document.getElementById("email").value = bankData.email;
					document.getElementById("balance").value = bankData.balance;
				}
			};
			xmlhttp.open("GET", "loadBankAccountData.php", true);
			xmlhttp.send();
		}

		function checkMoneyPay() {
			var schoolFee = parseInt(document.getElementById('school_fee').value);
			var moneyPay = parseInt(document.getElementById('moneypay').value);
			if(moneyPay < schoolFee)
			{
				//Write code here
			}
		}

		$(document).ready(function() {
			//autocomplete School Fee
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

<body style="font-size:125%;font-family:Arial, Helvetica, sans-serif;" onload="getBankAccountData()">
	<h1>Thanh toan</h1>

	<p>
		Welcome <em><?php echo $_SESSION['username']; ?></em> to the portal!
	</p>

	<form action="sendEmail_storeSession.php" method="post" id="myForm">
		<input type="text" id="realname" name="realname" readonly required/>
		<br/><br/>
		<input type="text" id="phone" name="phone" readonly required/>
		<br/><br/>
		<input type="text" id="email" name="email" readonly required/>
		<br/><br/>
		<input type="text" placeholder="Hãy nhập mã sinh viên" id="student_id" name="student_id" required/>
		<br/><br/>
		<input type="text" placeholder="Số tiền cần nộp" id="school_fee" name="school_fee" readonly required/>
		<br/><br/>
		<input type="text" id="balance" name="balance" readonly required/>
		<br/><br/>
		<input type="text" id="moneypay" placeholder="Hãy nhập số tiền cần chuyển" name="moneypay" required/>
		<br/><br/>
		<input type="submit" name="submit" value="Xác nhận">
		<p class="alert-warning">! Lưu ý : số tiền cần nộp cần phải lớn hơn hoặc bằng tiền học phí</p>
	</form>
	<p>
		Click here to <a href="logout.php" tite="Logout">Logout</a>
	</p>
</body>
</html>
