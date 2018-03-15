package org.darkbyte.internhomework;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by z on 13/6/17.
 */

public class Register extends AppCompatActivity {
    EditText uid, pass,name, cnfpass;
    Button register, linkologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        uid = (EditText) findViewById(R.id.register_uid);
        name = (EditText) findViewById(R.id.register_name);
        pass = (EditText) findViewById(R.id.reg_pass);
        cnfpass = (EditText) findViewById(R.id.reg_cnf_pass);
        register = (Button) findViewById(R.id.btnRegister);
        linkologin = (Button) findViewById(R.id.btnLinkToLoginScreen);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid1, pass1, cnfpass1;
                uid1 = uid.getText().toString();
                pass1 = pass.getText().toString();
                cnfpass1 = cnfpass.getText().toString();
                if (uid1.equals("") || uid1.length() != 14 && pass1.equals("") || pass1.length() < 8) {
                    Toast.makeText(Register.this, "uid number is wrong or password is short", Toast.LENGTH_SHORT).show();
                } else if (!pass1.equals(cnfpass1)) {
                    Toast.makeText(Register.this, "password not matching", Toast.LENGTH_SHORT).show();
                } else {
                    reg(uid1, pass1);

                }
            }
        });
        linkologin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),
                        Login.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void reg(final String uid1, final String pass1) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();

        AndroidNetworking.post("http://"+Constants.digitalip+"register.php")

                .addBodyParameter("uid", uid1)
                .addBodyParameter("password", pass1)
                .addBodyParameter("commonName",name.getText().toString())
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

                                if(json.has("uid exists")){
                                    Toast.makeText(getApplicationContext(),"uid already exists",Toast.LENGTH_SHORT).show();
                                    uid.setText("");
                                    cnfpass.setText("");
                                    pass.setText("");
                                }

                                  else  if (!json.has("InsertionError")) {

                                             Toast.makeText(getApplicationContext(), "Successfully Registered, Please Login In to Continue", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(getApplicationContext(), Login.class));
                                    finish();


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