	<?php
	include("db.php");
		$companyTaxNumb = $_POST['companyTaxNumb'];
	$companyName = $_POST['companyName'];

//	$companyTaxNumb = "0965745";
//	$companyName = "Default";


	
	$sql = "SELECT * from MAD_Company WHERE companyTaxNumb='$companyTaxNumb'";
	
	//check num row in database
$stm = $pdo->prepare($sql);
$stm->execute();

//some form validate
if(($stm->rowCount())  >= 1){
echo "3";

}else{

$sql = "SELECT * from MAD_Company WHERE companyName='$companyName'";
$stm = $pdo->prepare($sql);
$stm->execute();
	
	if(($stm->rowCount())  >= 1){
echo "4";
}else{
	
	$sql = "INSERT INTO MAD_Company(companyTaxNumb,companyName) VALUES (:companyTaxNumb,:companyName)";
$statement = $pdo->prepare($sql);
$statement->bindValue(":companyTaxNumb", $companyTaxNumb);
$statement->bindValue(":companyName", $companyName);
$statement->execute();
echo "0";
	}
	}
	?>