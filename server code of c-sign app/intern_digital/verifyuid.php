<?php

require 'db.php';

    
    $uid = mysqli_real_escape_string($connection, $_POST['uid']);
   
  
    $errorarray=array();
    $jsondata=array();
    
  
    $uidcheck = mysqli_query($connection,"select uid from uid_repo where uid= '$uid' ");
  
    
    
    if(mysqli_num_rows($uidcheck)<1){
    echo json_encode("no uid exists");
    }
    else{
    
    $getusername=mysqli_query($connection,"select * from uid_repo where uid='$uid' limit 1 ");
   
    if(mysqli_num_rows($getusername)>0){
  
                foreach($getusername as $rec){

                    $j_array['country_code']=utf8_encode($rec['country_code']); 
                    $j_array['commonName']=utf8_encode($rec['commonName']); 
                    $j_array['statename']=utf8_encode($rec['statename']);
                    $j_array['localityName']=utf8_encode($rec['localityName']);
                    $j_array['organizationalUnitName']=utf8_encode($rec['organizationalUnitName']);
                   
                
                    array_push($jsondata,$j_array);
                }
                
                echo json_encode($jsondata);

    }else {
            $e_data['AuthenticationError']="AuthenticationError";
            array_push($errorarray,$e_data);
             echo json_encode($errorarray);
    }
    
    }   
    
 
    mysqli_close($connection);
?>