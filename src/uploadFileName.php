<?php
include("db.php");

		$eventId = $_POST['eventId'];
		$version = $_POST['version'];
		
		$sql = "UPDATE `MAD_Event` SET `version`='$version' WHERE `eventId`='$eventId' ";
		$pdo->exec($sql);
		
		echo "0";
?>