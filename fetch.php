<?php
include("conn.php");
$qry="select * from employee_data";
$result=mysqli_query($conn,$qry);
while($raw=mysqli_fetch_assoc($result)){
	$data[]=$raw;
}
print(json_encode($data));
?>