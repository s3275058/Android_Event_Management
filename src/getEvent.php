<?php
include("db.php");

$eventId= $_POST['eventId'];
$password = $_POST['password'];


$sql = "SELECT * from MAD_Event WHERE eventId='$eventId' AND password='$password'";
$stm = $pdo->prepare($sql);
$stm->execute();
if(($stm->rowCount())  >= 1){
echo "0";
}else{
echo "2";
}
?>