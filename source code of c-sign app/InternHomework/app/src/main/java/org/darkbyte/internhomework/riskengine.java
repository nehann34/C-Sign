package org.darkbyte.internhomework;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

//import com.example.dheep.rba1.R;

import java.io.File;

public class riskengine extends AppCompatActivity {
sharedprefs sharedprefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedprefs=new sharedprefs(this);
        setContentView(R.layout.activity_riskengine);
    }

    @Override
    public void onBackPressed() {
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sharedprefs.clearkeys();
                        File file = new File(getApplicationContext().getFilesDir(), "cdac.vijaya.pem");
                        file.delete();
                        finish();
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
