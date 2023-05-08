package com.example.angeleye;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText et1;
    Button bt1;

    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1=findViewById(R.id.editTextTextPersonName4);
        bt1=findViewById(R.id.button2);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        et1.setText(sh.getString("ip",""));
    }

    public void click(View view) {
        String name=et1.getText().toString();


        if(name.length()==0){
            et1.setError("Missing");
            et1.requestFocus();
        }
        else {

            SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor ed= sh.edit();
            ed.putString("ip",name);
            ed.commit();


            Intent i=new Intent(getApplicationContext(),login.class);
            startActivity(i);
            Toast.makeText(getApplicationContext(), "Successfull", Toast.LENGTH_LONG).show();
        }
    }
}