package org.darkbyte.internhomework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
//import com.example.dheep.rba1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class ddd extends AppCompatActivity {
    Button btnsign;
    sharedprefs sharedprefs;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign);


        sharedprefs = new sharedprefs(this);
        btnsign = (Button) findViewById(R.id.btnsign);
        btnsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s = sharedprefs.getdigitalsign();
                try {
                    //  Toast.makeText(getApplicationContext(),"Digital Signature generated successfully  ",Toast.LENGTH_SHORT).show();
                    uploadkeys(sharedprefs.getpublickey(), sharedprefs.getdigitalsign());
                    //Toast.makeText(ddd.this, "digital signature" + encrypt(myhash(s.getBytes())).toString(), Toast.LENGTH_SHORT).show();
                }  catch (Exception e) {
                    e.printStackTrace();
                }
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



        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");



        sha256.reset();



        sha256.update(by);



        output1 = sha256.digest();



        // create hex output



        StringBuffer hexString1 = new StringBuffer();



        for (int i = 0; i < sha256.digest().length; i++)



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
    private void uploadkeys(String s1, String encrypt) {
        sharedprefs sh= new sharedprefs(this);
        String uid = sh.getuid();
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();

        AndroidNetworking.post("http://"+Constants.digitalip+"uploadsignature.php")
                .addBodyParameter("uid",uid)
                .addBodyParameter("publickey", s1)
                .addBodyParameter("digitalsign",encrypt)

                .setPriority(Priority.MEDIUM)
                .setOkHttpClient(okHttpClient)
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

                                if (!json.has("AuthenticationError")) {
                                    String status = json.getString("status");
                                    if(status.equals("ok")) {
//                                        Toast.makeText(ddd.this, "hash encoded:" + myhash(s.getBytes()), Toast.LENGTH_SHORT).show();
//                                        Toast.makeText(ddd.this, "public key:" + uk.toString(), Toast.LENGTH_SHORT).show();
//                                        Toast.makeText(ddd.this, "private key:" + rk.toString(), Toast.LENGTH_SHORT).show();

                                        startActivity(new Intent(getApplicationContext(), Signature.class));
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(ddd.this,""+status,Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "please try after sometime", Toast.LENGTH_SHORT).show();
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

                        Toast.makeText(getApplicationContext(), "Check Your Network Connection", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}
//ipaddress change kiya??