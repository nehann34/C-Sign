package org.darkbyte.internhomework;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


public class Login extends AppCompatActivity {

    public Button btnLogin;
    public Button btnLinkToRegister;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };
    EditText uid,password;
    sharedprefs sharedprefs;
    private KeyPair rsaKey = null;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.login);
            verifyStoragePermissions(this);
            uid = (EditText) findViewById(R.id.uid);
            password=(EditText)findViewById(R.id.password);

            sharedprefs = new sharedprefs(this);



            btnLogin = (Button) findViewById(R.id.btnLogin);
            btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
            btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(),
                            Register.class);
                    startActivity(i);
                    finish();
                }
            });
            btnLogin.setText("Verify Uid");
            btnLogin.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    if (uid.getText().toString().length() < 14 && password.getText().toString().equals("") ) {
                        Toast.makeText(Login.this, "uid length is wrong", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        verify(uid.getText().toString(),password.getText().toString());
                    }

                }
            });



        }



    private void verify(final String s, final String p) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();

        AndroidNetworking.post("http://"+Constants.digitalip+"login.php")

                .addBodyParameter("uid", s)

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



                                if (json.has("no uid exists")) {
                                    Toast.makeText(getApplicationContext(), " no uid  exists", Toast.LENGTH_SHORT).show();
                                    uid.setText("");

                                } else if ( p.equals(json.getString("password")) ){

                                    String commonName = json.getString("commonName");
                                    String country_code = json.getString("country_code");
                                    String localityName = json.getString("localityName");
                                    String statename = json.getString("statename");
                                    String organizationalUnitName = json.getString("organizationalUnitName");
                                    sharedprefs.saveuid(s);

                                    sharedprefs.saveusercertdetails(commonName,country_code,statename,localityName,organizationalUnitName);
                                        Toast.makeText(getApplicationContext(), "Successfully Logged in ", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),Pickfile   .class));
                                    finish();


                                } else {
                                    Toast.makeText(getApplicationContext(), "wrong credentials"+json.getString("password")+p, Toast.LENGTH_SHORT).show();
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
        public static void verifyStoragePermissions(Activity activity) {
            // Check if we have read or write permission
            int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

            if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(
                        activity,
                        PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE
                );
            }
        }


    }

