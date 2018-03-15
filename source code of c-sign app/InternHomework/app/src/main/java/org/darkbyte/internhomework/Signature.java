package org.darkbyte.internhomework;

/**
 * Created by vijaya on 27/6/17.
 */

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
//import com.example.dheep.rba1.mainFrame1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

//import static com.example.dheep.rba1.R.id.uid;

public class Signature extends AppCompatActivity {
    TextView publickey, digitalsign;
    Button next, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signature);
        // publickey = (TextView) findViewById(R.id.user_public_key);
        digitalsign = (TextView) findViewById(R.id.user_digital_sign);
         next = (Button) findViewById(R.id.preview_continue);

        final sharedprefs sharedprefs = new sharedprefs(this);
        Toast.makeText(getApplicationContext(), "Digital Signature generated successfully  ", Toast.LENGTH_SHORT).show();
        // publickey.setText("public key:" + sharedprefs.getpublickey());
        digitalsign.setText("Digital Signature:" + sharedprefs.getdigitalsign());

        next.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        Login.class);
                startActivity(i);
                finish();
            }
        });
       // Toast.makeText(getApplicationContext(), "" + sharedprefs.getpublickey(), Toast.LENGTH_SHORT).show();
       // Toast.makeText(getApplicationContext(), "" + ddd.decrypt(sharedprefs.getdigitalsign()), Toast.LENGTH_SHORT).show();
        if (sharedprefs.getpublickey().equals(ddd.decrypt(sharedprefs.getdigitalsign()))) {
            //Toast.makeText(getApplicationContext(), "Authenticated", Toast.LENGTH_SHORT).show();
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .writeTimeout(120, TimeUnit.SECONDS)
                    .build();

            AndroidNetworking.post("http://" + Constants.digitalip + "auth.php")
                    .addBodyParameter("uid", "8EF6C32F")


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
                                     //   Toast.makeText(getApplicationContext(), "verified", Toast.LENGTH_SHORT).show();
                                        // startActivity(new Intent(Signature.this, mainFrame1.class));
                                        finish();
                                    } else {
                                        Toast.makeText(Signature.this, "error updating", Toast.LENGTH_SHORT).show();
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

        } else {
            Toast.makeText(getApplicationContext(), "keys no more valid", Toast.LENGTH_SHORT).show();
        }

    }
}






