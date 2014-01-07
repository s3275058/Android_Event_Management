<?php
	include("db.php");
$eventName = $_POST['eventName'];
$password = $_POST['password'];
$creator = $_POST['creator'];
$version = $_POST['version'];

	
$sql = "INSERT INTO MAD_Event(eventName,password,creator,version) VALUES (:eventName,:password,:creator,:version)";
$statement = $pdo->prepare($sql);
$statement->bindValue(":eventName", $eventName);
$statement->bindValue(":password", $password);
$statement->bindValue(":creator", $creator);
$statement->bindValue(":version", $version);
$statement->execute();

$sql = "SELECT * from MAD_Event WHERE eventName='$eventName' && password='$password' && creator = '$creator'";
$result = $pdo->query($sql);
$row = $result->fetch();
$id = $row['eventId'];

echo $id;

?>