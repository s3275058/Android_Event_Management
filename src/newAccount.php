	<?php
	include("db.php");
	
	$username = $_POST['username'];
	$displayName = $_POST['username'];
	$password  = $_POST['password'];
	$email = $_POST['email'];
	$phoneNumber = $_POST['phoneNumber'];
	$companyTaxNumb = $_POST['companyTaxNumb'];
//	$username = "ae";
//	$displayName = "hello";
//	$password  = "psas";
//	$email = "76783@mail";
//	$phoneNumber = "0981236";
//	$companyTaxNumb = "6237128";
	
	$sql = "SELECT * from MAD_Account WHERE username='$username'";
	
	//check num row in database
$stm = $pdo->prepare($sql);
$stm->execute();

//some form validate
if(($stm->rowCount())  >= 1){
echo "1";

}else{
	
	
	$sql = "INSERT INTO MAD_Account(username,displayName,password,email,phoneNumber,companyTaxNumb) VALUES (:username,:displayName,:password,:email,:phoneNumber,:companyTaxNumb)";
$statement = $pdo->prepare($sql);
$statement->bindValue(":username", $username);
$statement->bindValue(":displayName", $displayName);
$statement->bindValue(":password", $password);
$statement->bindValue(":email", $email);
$statement->bindValue(":phoneNumber", $phoneNumber);
$statement->bindValue(":companyTaxNumb", $companyTaxNumb);
$statement->execute();
echo "0";
	}
	?>