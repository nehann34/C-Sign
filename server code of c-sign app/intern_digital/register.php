<?php

require 'db.php';

    
    $uid = mysqli_real_escape_string($connection, $_POST['uid']);
    $password = mysqli_real_escape_string($connection, $_POST['password']);
  $commonName = mysqli_real_escape_string($connection, $_POST['commonName']);
    $country_code = 'IN';//mysqli_real_escape_string($connection, $_POST['country_code']);
  $statename = 'Banglore';//mysqli_real_escape_string($connection, $_POST['statename']);
    $localityName = 'Bangloreee';//mysqli_real_escape_string($connection, $_POST['localityName']);
 $organizationalUnitName = 'CDAC Ltd';//mysqli_real_escape_string($connection, $_POST['organizationalUnitName']);
   
    
   
  $errorarray=array();
  

  

 
    $uidcheck = mysqli_query($connection,"select uid from uid_repo where uid= '$uid' ");
  
    
    
    if(mysqli_num_rows($uidcheck)>0){
	echo json_encode("uid exists");
    }
    else{
    
    
    $res=mysqli_query($connection, "INSERT INTO uid_repo(uid,password,commonName,country_code,statename,localityName,organizationalUnitName) VALUES('$uid','$password','$commonName','$country_code','$statename','$localityName','$organizationalUnitName')");
    
   if ($res) {
   
   $userdata=array();
   $u_data['uid']=$uid;
   $u_data['password']=$password;
    array_push($userdata,$u_data);
       
       echo json_encode($userdata);
       
       }
           
       else {
        
        $e_data['InsertionError']="InsertionError";
        array_push($errorarray,$e_data);
            echo json_encode($errorarray);
        }
    }
 
    mysqli_close($connection);
?>
