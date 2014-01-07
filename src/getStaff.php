<?php
include("db.php");

$companyTaxNumb= $_POST['companyTaxNumb'];
//$companyTaxNumb= "009232";

$sql = "SELECT * from MAD_Staff WHERE companyTaxNumb = '$companyTaxNumb'";
$result = $pdo->query($sql);

while($row = $result->fetch())
$staff[] = $row;
echo(json_encode($staff));

?>