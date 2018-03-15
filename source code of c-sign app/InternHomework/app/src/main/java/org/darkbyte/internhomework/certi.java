package org.darkbyte.internhomework;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

/**
 * Created by vijaya on 4/7/17.
 */


public class certi extends AppCompatActivity {
    TextView publickey,digitalsign, privatekey;
    Button next,cancel;
    sharedprefs sharedprefs;
    TextView country,name,issuer,expiry,version;
    String s;
    private static int delete_timeout = 300000;
    private static int dialog_timer = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Toast.makeText(getApplicationContext(),"dawda"+name,Toast.LENGTH_LONG).show();

        try {

            //    generateKey();
            fetchdetailsofcertificate();

        } catch (Exception e1) {

            // TODO Auto-generated catch block

            e1.printStackTrace();

        }
        setContentView(R.layout.certi);
        // publickey=(TextView)findViewById(R.id.user_public_key);
        //privatekey=(TextView)findViewById(R.id.user_private_key);
        // digitalsign=(TextView)findViewById(R.id.user_digital_sign);
        next=(Button) findViewById(R.id.preview_continue);
        cancel=(Button)findViewById(R.id.preview_cancel);
        country=(TextView)findViewById(R.id.responsecert);



        sharedprefs = new sharedprefs(this);
        s=sharedprefs.getcertresponse();
        country.setText(""+s);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(certi.this,ddd.class));
                finish();



            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                AlertDialog.Builder builder = new AlertDialog.Builder(certi.this);
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
                                certi.this.finish();
                                startActivity(new Intent(certi.this,Login.class));
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

    private void fetchdetailsofcertificate() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                . writeTimeout(120, TimeUnit.SECONDS)
                .build();
        AndroidNetworking.post("http://"+ Constants.digitalip+"certificate_display.php")

                .addBodyParameter("certificate_id","1" )

                .setPriority(Priority.MEDIUM)
                .setOkHttpClient(okHttpClient)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //  loadToast.success();
                        Log.d("LOG", "RESPONSE" + response);
                        // Toast.makeText(getApplicationContext(), "Response" + response.toString(), Toast.LENGTH_SHORT).show();
                        int j = response.length();

                        for (int i = 0; i < j; i++) {
                            Log.v("fgfgh","vlue"+j);
                            JSONObject json=null;
                            try {

                                json = response.getJSONObject(i);
                                Log.v("fgfgh","vlue"+json);


                                if (!json.has("NoItems")){

                                    //  progressBar.setVisibility(View.GONE);
                                    if (response != null) {

                                        //   country.setText(getString(R.string.country_code)+sharedprefs.getcountrycode());
                                        //  name.setText(getString(R.string.certificate_name)+sharedprefs.getcommonname());
//                                        issuer.setText(getString(R.string.issuer)+sharedprefs.get);
//                                        expiry.setText(getString(R.string.expiry)+json.getString("certificate_expiry"));
//                                        version.setText(getString(R.string.version)+json.getString("version"));
                                        //     issuer.setVisibility(View.INVISIBLE);
                                        //     expiry.setVisibility(View.INVISIBLE);
                                        //    version.setVisibility(View.INVISIBLE);


                                    }
                                }else {

                                    Toast.makeText(getApplicationContext(), "nothing in database", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Please try after sometime", Toast.LENGTH_SHORT).show();
                            }


                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        // loadToast.error();
                        Log.d("LOG", "RESPONSE" + anError);
                        Toast.makeText(getApplicationContext(), "Check Your Network Connection", Toast.LENGTH_SHORT).show();

                    }
                });

    }

}