package org.darkbyte.internhomework;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//import com.example.dheep.rba1.R;

import java.security.KeyPair;


public class Sign extends AppCompatActivity {

    public Button btnsign;

    private KeyPair rsaKey = null;
    sharedprefs sharedprefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign);
        sharedprefs=new sharedprefs(this);

        btnsign = (Button) findViewById(R.id.btnsign);
        btnsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getpublickey = sharedprefs.getpublickey();
                String getPrivatekey = sharedprefs.getprivatekey();
                Toast.makeText(getApplicationContext(), "Digital Signature generated successfully  ", Toast.LENGTH_SHORT).show();
               // Toast.makeText(Sign.this,"Public key:"+  rsaKey.getPublic(),Toast.LENGTH_SHORT).show();
                //Toast.makeText(Sign.this,"private key:"+rsaKey.getPrivate(),Toast.LENGTH_SHORT).show();
            }
        });

        }
    }



