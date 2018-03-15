package org.darkbyte.internhomework;

/**
 * Created by vijaya on 23/6/17.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

//import com.example.dheep.rba1.R;

public class SplashScreen extends Activity {


    private static int SPLASH_TIME_OUT = 1000;

    sharedprefs sharedprefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedprefs=new sharedprefs(this);


        new Handler().postDelayed(new Runnable() {



            @Override
            public void run() {

                if (sharedprefs.getuid()!=null){
                  //  Toast.makeText(getApplicationContext(),sharedprefs.getuid(),Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SplashScreen.this,Login.class));}
                else {
                    Intent i = new Intent(SplashScreen.this, Login.class);

                    startActivity(i);
                }

                finish();
            }
        }, SPLASH_TIME_OUT);

    }

}
