	<?php
	include("db.php");
	
$displayName = $_POST['displayName'];
$email = $_POST['email'];
	$phoneNumber = $_POST['phoneNumber'];
	$companyTaxNumb = $_POST['companyTaxNumb'];
	
	
	
	$sql = "INSERT INTO MAD_Staff(displayName,email,phoneNumber,companyTaxNumb) VALUES (:displayName,:email,:phoneNumber,:companyTaxNumb)";
$statement = $pdo->prepare($sql);
$statement->bindValue(":displayName", $displayName);
$statement->bindValue(":email", $email);
$statement->bindValue(":phoneNumber", $phoneNumber);
$statement->bindValue(":companyTaxNumb", $companyTaxNumb);
$statement->execute();
	echo "0";
	?>