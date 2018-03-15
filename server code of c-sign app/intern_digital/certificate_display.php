 <?php

require 'db.php';

    $studentid = mysqli_real_escape_string($connection, $_POST['certificate_id']);

    $errorarray=array();
    $jsondata=array();
    
  
 
    $details=mysqli_query($connection,"select * from certificates where certificate_id='$studentid' limit 1 ");
  
    if(mysqli_num_rows($details)>0){
  
		foreach($details as $rec){
	$j_array['certificate_name']=utf8_encode($rec['certificate_name']);
    $j_array['issuing_authority']=utf8_encode($rec['issuing_authority']);
    $j_array['certificate_expiry']=utf8_encode($rec['certificate_expiry']);
    $j_array['version']=utf8_encode($rec['version']);
    $j_array['country_code']=utf8_encode($rec['country_code']);

  

	array_push($jsondata,$j_array);
		}
		echo json_encode($jsondata);

    }else {
    
    		$e_data['NoItems']="NoItems";
        	array_push($errorarray,$e_data);
           	 echo json_encode($errorarray);
    }
    
    
  
 
    mysqli_close($connection);
?>