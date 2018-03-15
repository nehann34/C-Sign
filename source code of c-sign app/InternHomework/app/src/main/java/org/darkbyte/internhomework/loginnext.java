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
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


public class loginnext extends AppCompatActivity {

    public Button btnloginnext;

    private KeyPair rsaKey = null;
    sharedprefs sharedprefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_next);
        sharedprefs=new sharedprefs(this);

        btnloginnext = (Button) findViewById(R.id.btnloginnext);
        btnloginnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                        .connectTimeout(120, TimeUnit.SECONDS)
                        .readTimeout(120, TimeUnit.SECONDS)
                        .writeTimeout(120, TimeUnit.SECONDS)
                        .build();

                AndroidNetworking.post("http://"+Constants.digitalip+"shell.php")

                        .addBodyParameter("commonName", sharedprefs.getcommonname())



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
                                        //Toast.makeText(getApplicationContext(),""+json.getString("status"),Toast.LENGTH_SHORT).show();
                                        Log.v("dawdwadw",""+json.getString("status"));
                                        Intent k = new Intent(loginnext.this, preview.class);
                                        sharedprefs.savecertresponse(json.getString("status"));
                                        startActivity(k);

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

                                Toast.makeText(getApplicationContext(), "Check Your Network Connection" + anError, Toast.LENGTH_SHORT).show();
                            }
                        });
            }


        }
        );


    }
}





