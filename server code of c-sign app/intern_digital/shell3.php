
<?php
 $commonName =  $_POST['commonName'];
    $country_code = $_POST['country_code']);
$statename = $_POST['statename']);
    $localityName = $_POST['localityName']);
    $organizationalUnitName = $_POST['organizationalUnitName']);
    
$jsondata=array();
$str="/CN=$commonName/O=$organizationalUnitName/C=$country_code/ST=$statename/L=$localityName ";

$output = shell_exec("openssl genrsa -out ca/intermediate/private/www.'$commonName'.key.pem  2048 2>&1");

$cdd = shell_exec(" openssl req -config ca/intermediate/openssl.cnf -new -sha256  -subj '$str'   -key ca/intermediate/private/www.'$commonName'.key.pem  -out ca/intermediate/csr/www.'$commonName'.csr.pem  2>&1");
$adwa= shell_exec(" openssl   ca -batch -config  ca/openssl.cnf -extensions v3_intermediate_ca   -days 1 -notext -md sha256   -in ca/intermediate/csr/www.'$commonName'.csr.pem   -out ca/intermediate/certs/www.'$commonName'.pem 2>&1");
$j_array['status']=$adwa;
array_push($jsondata,$j_array);
				
				echo json_encode($jsondata);

?>

