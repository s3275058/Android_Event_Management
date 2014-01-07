	<?php
	include("db.php");
	
$username = $_POST['username'];
//$username = "test";
	
$sql = "SELECT * from MAD_Account WHERE username = '$username'";
$result = $pdo->query($sql);
$row = $result->fetch();

echo $row['companyTaxNumb'];

	?>