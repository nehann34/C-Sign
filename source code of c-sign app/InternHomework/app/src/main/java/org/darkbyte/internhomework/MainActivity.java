package org.darkbyte.internhomework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.dheep.rba1.R;

public class MainActivity extends AppCompatActivity {
TextView text;
    EditText pin;
    Button save;
    sharedprefs sharedprefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text=(TextView)findViewById(R.id.textpin);
        pin=(EditText)findViewById(R.id.editTextpin);
        save=(Button)findViewById(R.id.button);
        sharedprefs=new sharedprefs(this);
        String s = sharedprefs.getloginstatus();
        if(s==null){
            text.setText("Configure your PIN");
            save.setText("Save PIN");
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String pin1 = pin.getText().toString();
                    sharedprefs.saveprefs(pin1);
                    startActivity(new Intent(MainActivity.this,Login.class));
                }
            });
        }
        else {
            text.setText("Enter Your PIN");
            save.setText("Continue");
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String s = sharedprefs.getloginstatus();
                    String k = pin.getText().toString();
                    if (s.equals(k)) {
                        startActivity(new Intent(MainActivity.this, Login.class));
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this,"wrong Pin",Toast.LENGTH_SHORT).show();
                        pin.setText("");
                    }
                }
            });
        }

    }
}
