package org.darkbyte.internhomework;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
//import com.example.dheep.rba1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import javax.crypto.Cipher;

import okhttp3.OkHttpClient;

public class preview extends AppCompatActivity {
    TextView publickey,digitalsign, privatekey;
    Button next,cancel;
    sharedprefs sharedprefs;
    TextView country,name,issuer,expiry,version;
    String s,f, imei;
    private static int delete_timeout = 300000;
    private static int dialog_timer = 1000;
    TelephonyManager tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            generateKey();
           // fetchdetailsofcertificate();

        } catch (Exception e1) {

            // TODO Auto-generated catch block

            e1.printStackTrace();

        }
        setContentView(R.layout.activity_preview);
        publickey=(TextView)findViewById(R.id.user_public_key);
        privatekey=(TextView)findViewById(R.id.user_private_key);
        // digitalsign=(TextView)findViewById(R.id.user_digital_sign);
      next=(Button) findViewById(R.id.preview_continue);
        cancel=(Button)findViewById(R.id.preview_cancel);
       // country=(TextView)findViewById(R.id.country_code);
       // name=(TextView)findViewById(R.id.organisation_name);
       // issuer=(TextView)findViewById(R.id.issuing_Authority);
        //expiry=(TextView)findViewById(R.id.cert_expiry);
        //version=(TextView)findViewById(R.id.cert_version);


        sharedprefs = new sharedprefs(this);
        s=sharedprefs.getuid();
        f=sharedprefs.getfile();
        //imei = sharedprefs.getIMEI();
       // TelephonyManager telephonyManager = (TelephonyManager)getSystemService(getApplicationContext().TELEPHONY_SERVICE);
        //imei=telephonyManager.getDeviceId();
        try {
            tel = (TelephonyManager) getSystemService(getApplicationContext().TELEPHONY_SERVICE);
            imei = tel.getDeviceId().toString();
        }catch (Exception  e){
            Log.v("eror",""+e);
        }

        final String name=  sharedprefs.getcommonname();
        publickey.setText(getString(R.string.publickey)
                +
                uk.toString());
        privatekey.setText(getString(R.string.privatekey)
                +
                rk.toString());
        //digitalsign.setText("Digrital Signature:"+sharedprefs.getdigitalsign());

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AndroidNetworking.post("http://"+Constants.digitalip+"verifyuidimei.php")
                        .addBodyParameter("uid", s)
                        .addBodyParameter("imei", imei )

                        .setPriority(Priority.MEDIUM)

                        .build()
                        .getAsJSONArray(new JSONArrayRequestListener() {
                            @Override
                            public void onResponse(JSONArray response) {
                                // loadToast.success();
                                //  Toast.makeText(getApplicationContext(),response.toString(), Toast.LENGTH_SHORT).show();
                                int j = response.length();
                                for (int i = 0; i < j; i++) {
                                    JSONObject json;
                                    try {
                                        json = response.getJSONObject(i);

                                       // Toast.makeText(getApplicationContext(),""+s+imei,Toast.LENGTH_SHORT).show();
                                         if (!json.has("AuthenticationError")) {

                                            /*String commonName = json.getString("commonName");
                                            String country_code = json.getString("country_code");
                                            String localityName = json.getString("localityName");
                                            String statename = json.getString("statename");
                                            String organizationalUnitName = json.getString("organizationalUnitName");
                                            sharedprefs.saveuid(s);
                                            sharedprefs.saveusercertdetails(commonName,country_code,statename,localityName,organizationalUnitName);
*/
                                             //Toast.makeText(getApplicationContext(), "Key Pair generated successfully ", Toast.LENGTH_SHORT).show();
                                             startActivity(new Intent(getApplicationContext(), loginnext.class));
                                             finish();//whose ip is this?? prerna//is there intern folder??yes
                                             AndroidNetworking.download("http://"+Constants.digitalip+"ca/intermediate/certs/", String.valueOf(getFilesDir()),"www."+name+".pem")
                                                     .setTag("downloadTest")
                                                     .setPriority(Priority.MEDIUM)
                                                     .build()
                                                     .setDownloadProgressListener(new DownloadProgressListener() {
                                                         @Override

                                                         public void onProgress(long bytesDownloaded, long totalBytes) {
                                                             AlertDialog alertDialog = new AlertDialog.Builder(preview.this).create(); //Read Update
                                                             alertDialog.setTitle("Digital Certificate ");
                                                             alertDialog.setMessage("Downloading Digital certificate don't close Dialog"+bytesDownloaded+"total:"+totalBytes );
                                                             alertDialog.show();  // do anything with progress
                                                             //    Toast.makeText(getApplicationContext(),"das"+getFilesDir(),Toast.LENGTH_SHORT).show();

                                                         }
                                                     })
                                                     .startDownload(new DownloadListener() {
                                                         @Override
                                                         public void onDownloadComplete() {
//                                                             AlertDialog alertDialog = new AlertDialog.Builder(preview.this).create(); //Read Update
//                                                             alertDialog.setTitle("Download Complete");
//                                                             alertDialog.setMessage("file Downloaded succesfully "+"www"+name+"pem");
//                                                       alertDialog.show();  // do anything after Toacompletion
                                                            // Toast.makeText(getApplicationContext(),"dawda"+name,Toast.LENGTH_SHORT).show();

                                                             try {
                                                                 sharedprefs.saveprivateandpublickey(rk.toString(), uk.toString(), encrypt(myhash(f.getBytes())));
                                                             } catch (NoSuchAlgorithmException e) {
                                                                 e.printStackTrace();
                                                             }
                                                             // Toast.makeText(getApplicationContext(),"Digital Signature generated successfully  ",Toast.LENGTH_SHORT).show();
                                                             startActivity(new Intent(preview.this,beforecerti.class));
                                                             finish();
                                                         }
                                                         @Override
                                                         public void onError(ANError error) {
                                                             Log.v("daws",""+error);
                                                             Toast.makeText(getApplicationContext(),"Download Failed . Try again"+error,Toast.LENGTH_SHORT).show();

                                                         }
                                                     });


                                        } else {
                                            Toast.makeText(getApplicationContext(), "wrong credentials", Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getApplicationContext(), "Please try after sometime", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }



                            @Override
                            public void onError(ANError anError) {
                                //  loadToast.error();
                                Log.d("LOG", "RESPONSE" + anError);

                                Toast.makeText(getApplicationContext(), "Check Your Network Connection"+anError, Toast.LENGTH_SHORT).show();
                            }
                        });



            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                AlertDialog.Builder builder = new AlertDialog.Builder(preview.this);
                builder.setMessage("Cancel will take to to the login screen.Do you still want to cancel?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        File file = new File(getApplicationContext().getFilesDir(), "cdac.vijaya.pem");
                                        file.delete();
                                        sharedprefs.clearkeys();
                                        finish();
                                        System.exit(0);

                                    }
                                }, delete_timeout);
                                preview.this.finish();
                                startActivity(new Intent(preview.this,Login.class));
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();


//                Toast.makeText(getApplicationContext(),"Digital Signature generated successfully  ",Toast.LENGTH_SHORT).show();

            }
        });
    }



    private final static String RSA = "RSA";

    public static PublicKey uk;

    public static PrivateKey rk;

    public static int stepcount = 0;



    public static void generateKey() throws Exception {

        KeyPairGenerator gen = KeyPairGenerator.getInstance(RSA);

        gen.initialize(512, new SecureRandom());

        KeyPair keyPair = gen.generateKeyPair();

        uk = keyPair.getPublic();

        rk = keyPair.getPrivate();

    }



    public final String myhash(byte[] by) throws NoSuchAlgorithmException {

        byte[] output1;

        // MD5



        MessageDigest md5 = MessageDigest.getInstance("MD5");



        md5.reset();



        md5.update(by);



        output1 = md5.digest();



        // create hex output



        StringBuffer hexString1 = new StringBuffer();



        for (int i = 0; i < md5.digest().length; i++)



            hexString1.append(Integer.toHexString(0xFF & output1[i]));



        return hexString1.toString();



    }



    private static byte[] encrypt(String text, PrivateKey priRSA)

            throws Exception {

        Cipher cipher = Cipher.getInstance(RSA);

        cipher.init(Cipher.ENCRYPT_MODE, priRSA);

        return cipher.doFinal(text.getBytes());

    }



    public final static String encrypt(String text) {

        try {

            return byte2hex(encrypt(text, rk));

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;

    }



    public final static String decrypt(String data) {

        try {

            return new String(decrypt(hex2byte(data.getBytes())));

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;

    }



    private static byte[] decrypt(byte[] src) throws Exception {

        Cipher cipher = Cipher.getInstance(RSA);

        cipher.init(Cipher.DECRYPT_MODE, uk);

        return cipher.doFinal(src);

    }



    public static String byte2hex(byte[] b) {

        String hs = "";

        String stmp = "";

        for (int n = 0; n < b.length; n++) {

            stmp = Integer.toHexString(b[n] & 0xFF);

            if (stmp.length() == 1)

                hs += ("0" + stmp);

            else

                hs += stmp;

        }

        return hs.toUpperCase();

    }



    public static byte[] hex2byte(byte[] b) {

        if ((b.length % 2) != 0)

            throw new IllegalArgumentException("hello");



        byte[] b2 = new byte[b.length / 2];



        for (int n = 0; n < b.length; n += 2) {

            String item = new String(b, n, 2);

            b2[n / 2] = (byte) Integer.parseInt(item, 16);

        }

        return b2;

    }
}
