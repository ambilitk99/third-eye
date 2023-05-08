package com.example.angeleye;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class emergency_popup extends AppCompatActivity {

    TextView tv1;
    Button b1;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_popup);
        tv1=findViewById(R.id.textView23);
        b1=findViewById(R.id.button12);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        tv1.setText(sh.getString("name",""));



    }
}