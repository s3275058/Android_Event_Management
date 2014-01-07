	<?php
	include("db.php");
	
$eventId = $_POST['eventId'];
//$username = "test";
	
$sql = "SELECT * from MAD_Event WHERE eventId = '$eventId'";
$result = $pdo->query($sql);
$row = $result->fetch();

echo $row['version'];

	?>