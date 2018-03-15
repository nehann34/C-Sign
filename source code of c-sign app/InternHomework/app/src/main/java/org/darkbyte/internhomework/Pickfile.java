package org.darkbyte.internhomework;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;

import org.json.JSONObject;

import java.io.File;
import java.net.URI;

public class Pickfile extends AppCompatActivity {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    int PICK_REQUEST_CODE = 1;
    Intent mediaIntent;
    Button browse, upload;
    File sfile;
    TextView fname;
    String item, filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickfile);
        fname = (TextView) findViewById(R.id.upload_assign_fname);
        browse = (Button) findViewById(R.id.upload_assign_pick);
        upload = (Button) findViewById(R.id.uploadassignmentbutton);

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaIntent = new Intent(Intent.ACTION_GET_CONTENT);
                mediaIntent.setType("*/*"); //set mime type as per requirement
                startActivityForResult(mediaIntent, PICK_REQUEST_CODE);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(Pickfile.this,loginnext.class);
               sharedprefs sharedprefs=new sharedprefs(getApplicationContext());
                sharedprefs.savefilename(filename);
                startActivity(i);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_REQUEST_CODE
                && resultCode == Activity.RESULT_OK) {
            Uri Uri = data.getData();
            String type = mediaIntent.getType();
            Log.i("Pick completed: " + Uri + " " + type, "");
            if (Uri != null) {
                filename = data.getData().getLastPathSegment();

                String path = Uri.toString();
                sfile = new File(Uri.getPath());
                if (path.toLowerCase().startsWith("file://")) {
                    // Selected file/directory path is below
                    path = (new File(URI.create(path))).getAbsolutePath();
                    fname.setText(filename);

                }

            }



        }
    }

}
