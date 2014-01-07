
<?php
include("db.php");
$username = $_POST['username'];
$password = $_POST['password'];

$sql = "SELECT * from MAD_Account WHERE `username`='$username' AND `password`='$password'";
$stm = $pdo->prepare($sql);
$stm->execute();
if(($stm->rowCount())  == 0){
echo "2";
}else{
echo "0";
}
?>