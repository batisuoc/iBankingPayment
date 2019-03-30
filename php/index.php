<?php
session_start();
if(empty($_SESSION)){
	header("location: LoginPage.php");
	die();
}
?>
<html>
<head>
	<meta charset="UTF-8">
	<title>Thanh toan</title>
	<?php include('head.php'); ?>

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
		function numberWithCommas(x) {
			return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		}

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
				alert('So tien phai lon hon hoac bang tien hoc phi');
			}
		}

		$(document).ready(function() {
			//autocomplete School Fee
			$('#student_id').change(function() {
				var studentID = document.getElementById('student_id').value;
				var username = document.getElementById('username').innerHTML;
				console.log(username);
				var xmlhttp = new XMLHttpRequest();
				var studentData;
				xmlhttp.onreadystatechange = function () {
					if(this.readyState == 4 && this.status == 200)
					{
						studentData = JSON.parse(this.responseText);
						console.log(this.responseText);
						if(studentData.stId != studentID)
						{
							document.getElementById("school_fee").value = "This is not your student id";
						}
						else if(studentData.scFee == 0)
						{
							document.getElementById("school_fee").value = "Your school fee is payed.";
						}
						else
						{
							document.getElementById("school_fee").value = studentData.scFee;
						}
					}
				};
				xmlhttp.open("POST", "loadSchoolFee.php", true);
				xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
				xmlhttp.send("stuID=" + studentID + "&username=" + username);
			});
		});
	</script>
</head>

<body style="font-size:125%;font-family:Arial, Helvetica, sans-serif;" onload="getBankAccountData()">
	<div class="container">
		<nav class="navbar navbar-expand-sm bg-light navbar-light">
			<span class="navbar-text">
				Welcome <em id="username"><?php echo $_SESSION['username']; ?></em> to the portal!
			</span>
			<ul class="navbar-nav ml-auto">
				<li class="nav-item">
					<a class="nav-link" href="logout.php">Log out</a>
				</li>
			</ul>
		</nav>
		<div class="form-horizontal"> 
			<div class="card-header">
				<h3>
					
				</h3>
				<h3>Thông tin thanh toán</h3>
			</div>
			<div class="card-body">
				<form action="sendEmail_storeSession.php" method="post" id="myForm">
					<div class="input-group form-group">
						<input type="text" id="realname" name="realname" class="form-control" readonly required/>
					</div>
					<div class="input-group form-group">
						<input type="text" id="phone" name="phone" class="form-control" readonly required/>
					</div>
					<div class="input-group form-group">
						<input type="text" id="email" name="email" class="form-control" readonly required/>
					</div>
					<div class="input-group form-group">
						<input type="text" placeholder="Hãy nhập mã sinh viên" id="student_id" name="student_id" class="form-control" required/>
					</div>
					<div class="input-group form-group">
						<input type="text" placeholder="Số tiền cần nộp" id="school_fee" name="school_fee" class="form-control" readonly required/>
					</div>
					<div class="input-group form-group">
						<input type="text" id="balance" name="balance" class="form-control" readonly required/>
					</div>
					<div class="input-group form-group">
						<input type="text" id="moneypay" placeholder="Hãy nhập số tiền cần chuyển" name="moneypay" class="form-control" onchange="checkMoneyPay()" required/>
					</div>
					<div class="form-group">
						<p class="alert-warning">! Lưu ý : số tiền cần nộp cần phải lớn hơn hoặc bằng tiền học phí</p>
					</div>
					<div class="form-group btn-center">
						<input type="submit" name="submit" value="Xác nhận" class="btn btn-light"></input>
					</div>
				</form>
				<div class="card-footer"></div>
			</div>
		</div>
		
	</div>
</body>
</html>
