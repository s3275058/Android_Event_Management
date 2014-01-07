<?php
$filename = $_FILES['file']['name'];
$tmpname=$_FILES['file']['tmp_name'];
if(move_uploaded_file($tmpname, "upload/$filename")) {
 echo "The file ".  basename( $_FILES['file']['name']).
 " has been uploaded";
} else{
 echo "There was an error uploading the file ".  basename( $_FILES['file']['name']).
 ", please try again!";
}
?>