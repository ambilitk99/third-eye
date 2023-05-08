package com.example.angeleye;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class Emergemcycontact extends AppCompatActivity implements View.OnClickListener  {
    EditText name,phone;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergemcycontact);
        name=findViewById(R.id.editTextTextPersonName21);
        phone=findViewById(R.id.editTextTextPersonName22);
        submit=findViewById(R.id.button8);

        submit.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {

        String nm=name.getText().toString();
        String phn = phone.getText().toString();
        if (nm.length() == 0) {
            name.setError("Missing");
            name.requestFocus();
        }
        else if (!nm.matches("^[a-z,A-Z]*$"))
        {
            name.setError("Characters Allowed");
            name.requestFocus();
        }
        else if (phn.length() == 0) {
            phone.setError("Missing");
            phone.requestFocus();
        }
        else if (phn.length() != 10) {
            phone.setError("Invalid mobile");
            phone.requestFocus();
        }
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final String maclis = sh.getString("mac_list", "");
        String uid = sh.getString("uid", "");
        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":8000/myapp/addemergency/";


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

                                Intent i = new Intent(getApplicationContext(), viewblinds.class);
                                startActivity(i);
                                Toast.makeText(getApplicationContext(), "Successfull", Toast.LENGTH_LONG).show();


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
                params.put("lid",sh.getString("lid",""));


                params.put("name", nm);
                params.put("phone", phn);
                params.put("bid",sh.getString("blid",""));


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
