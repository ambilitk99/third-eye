package com.example.angeleye;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity implements View.OnClickListener {

    EditText eduname,edpassword;
    Button edbtn;
    TextView edtext;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logintemp);
        eduname=findViewById(R.id.editTextTextPersonName3);
        edpassword=findViewById(R.id.editTextTextPassword);
        edbtn=findViewById(R.id.button1);
        edtext=findViewById(R.id.textView2);

        edbtn.setOnClickListener(this);
        edtext.setOnClickListener(this);



    }


    @Override
    public void onClick(View view) {

        if(view==edtext) {

            Intent i=new Intent(getApplicationContext(),signup.class);
            startActivity(i);
        }
        else{
            String name = eduname.getText().toString();
            String password = edpassword.getText().toString();

            if (name.length() == 0) {
                eduname.setError("Missing");
                eduname.requestFocus();
            }
            if (password.length() == 0) {
                edpassword.setError("Missing");
                edpassword.requestFocus();
            } else {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                final String maclis = sh.getString("mac_list", "");
                String uid = sh.getString("uid", "");
                String hu = sh.getString("ip", "");
                String url = "http://" + hu + ":8000/myapp/andlogin/";


                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                // response
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                        SharedPreferences.Editor ed = sh.edit();

                                        String lid = jsonObj.getString("lid");
                                        ed.putString("lid", lid);
                                        ed.commit();
                                        startService(new Intent(getApplicationContext(),Notify.class));

                                        Intent i = new Intent(getApplicationContext(), home.class);
                                        startActivity(i);
                                        Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_LONG).show();


                                    }


                                    // }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                                    }

                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        Map<String, String> params = new HashMap<String, String>();


                        params.put("name", name);
                        params.put("password", password);
//                params.put("mac",maclis);

                        return params;
                    }
                };

                int MY_SOCKET_TIMEOUT_MS = 100000;

                postRequest.setRetryPolicy(new DefaultRetryPolicy(
                        MY_SOCKET_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(postRequest);

            }
        }

    }
}