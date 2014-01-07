<?php
include("db.php");


$sql = "SELECT * from MAD_Company";
$result = $pdo->query($sql);
while($row = $result->fetch())
$company[] = $row;
echo(json_encode($company));

?>