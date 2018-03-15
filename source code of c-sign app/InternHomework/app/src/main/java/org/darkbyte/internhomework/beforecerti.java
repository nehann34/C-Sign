package org.darkbyte.internhomework;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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


public class beforecerti extends AppCompatActivity {

    public Button btnbeforecerti1;
    public Button btnbeforecerti2;
    TextView name,issuer,expiry;

    private KeyPair rsaKey = null;
    sharedprefs sharedprefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.before_certi);
        sharedprefs = new sharedprefs(this);
        name = (TextView) findViewById(R.id.organisation_name1);
        issuer = (TextView) findViewById(R.id.issuing_Authority1);
        expiry = (TextView) findViewById(R.id.cert_expiry1);
        btnbeforecerti1 = (Button) findViewById(R.id.btnbeforecerti1);
        btnbeforecerti2 = (Button) findViewById(R.id.btnbeforecerti2);
        name.setText("www."+sharedprefs.getcommonname()+".pem");
        issuer.setText(sharedprefs.getorganisationalname());
        //expiry.setText(sharedprefs.getcountrycode());
        btnbeforecerti1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(beforecerti.this, certi.class);

                startActivity(i);


            }
        });
        btnbeforecerti2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(beforecerti.this, ddd.class);

                startActivity(i);

            }
        });


    }



    }





