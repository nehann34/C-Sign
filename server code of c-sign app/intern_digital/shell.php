
<?php
 $commonName =  $_POST['commonName'];
    $country_code = 'IN';//mysqli_real_escape_string($connection, $_POST['country_code']);
$statename = 'Banglore';//mysqli_real_escape_string($connection, $_POST['statename']);
    $localityName ='asdsad';// mysqli_real_escape_string($connection, $_POST['localityName']);
    $organizationalUnitName ='CDAC Ltd';// mysqli_real_escape_string($connection, $_POST['organizationalUnitName']);
    
$jsondata=array();
$str="/CN=$commonName/O=$organizationalUnitName/C=$country_code/ST=$statename/L=$localityName ";
//echo $str;
//echo 'vijaya97' | sudo -s
$output = shell_exec("openssl genrsa -out ca/intermediate/private/www.'$commonName'.key.pem  2048 2>&1");
//$abc = shell_exec("openssl req -config ~/ca/intermediate/openssl.cnf  -subj '/CN=$commonName'  -key ~/ca/intermediate/private/www.$commonName.key.pem    -new -sha256 -out ~/ca/intermediate/csr/www.'$commonName'.csr.pem ");
//$abcd=shell_exec("openssl ca -config ~/ca/intermediate/openssl.cnf       -extensions server_cert -days 375 -notext -md sha256  -in ~/ca/intermediate/csr/www.$commonName.csr.pem       -out ~/ca/intermediate/certs/www.$commonName.pem");
$cdd = shell_exec(" openssl req -config ca/intermediate/openssl.cnf -new -sha256  -subj '$str'   -key ca/intermediate/private/www.'$commonName'.key.pem  -out ca/intermediate/csr/www.'$commonName'.csr.pem  2>&1");
$adwa= shell_exec(" openssl   ca -batch -config  ca/openssl.cnf -extensions v3_intermediate_ca   -days 3650 -notext -md sha256   -in ca/intermediate/csr/www.'$commonName'.csr.pem   -out ca/intermediate/certs/www.'$commonName'.pem 2>&1");
$j_array['status']=$adwa;
array_push($jsondata,$j_array);
				
				echo json_encode($jsondata);

?>

