<?php

require 'db.php';

    
    $uid = mysqli_real_escape_string($connection, $_POST['uid']);
    $digitalsign = mysqli_real_escape_string($connection, $_POST['digitalsign']);
    $publickey = mysqli_real_escape_string($connection, $_POST['publickey']);
   
    
   
  $errorarray=array();
  
    
    $res=mysqli_query($connection,"INSERT INTO transactions(uid, publickey ,digital_signature) VALUES('$uid','$publickey','$digitalsign')");
    
   if ($res) {
   
   $userdata=array();
   $u_data['status']="ok";
   
    array_push($userdata,$u_data);
       
       echo json_encode($userdata);
       
       }
           
       else {
        
        $e_data['InsertionError']="InsertionError";
        array_push($errorarray,$e_data);
            echo json_encode($errorarray);
        }
    
    mysqli_close($connection);
?>