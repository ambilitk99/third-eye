package com.example.angeleye;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class view_profile extends AppCompatActivity implements View.OnClickListener {

    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9;
    ImageView im1;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        t1=findViewById(R.id.textView4);
        t2=findViewById(R.id.textView3);
        t3=findViewById(R.id.textView5);
        t4=findViewById(R.id.textView8);
        t5=findViewById(R.id.textView6);
        t6=findViewById(R.id.textView9);
        t7=findViewById(R.id.textView7);
        t8=findViewById(R.id.textView10);
        t9=findViewById(R.id.textView11);
        b1=findViewById(R.id.button3);
        im1=findViewById(R.id.imageView2);
        b1.setOnClickListener(this);


        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final String maclis = sh.getString("mac_list", "");
        String uid = sh.getString("uid", "");
        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":8000/myapp/viewprofile/";


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

//                                Toast.makeText(view_profile.this, response, Toast.LENGTH_SHORT).show();
//                                JSONObject js= jsonObj.getJSONObject("name");


                                t1.setText(jsonObj.getString("name"));
                                t2.setText(jsonObj.getString("gender"));
                                t3.setText(jsonObj.getString("dob"));
                                t4.setText(jsonObj.getString("place"));
                                t5.setText(jsonObj.getString("pin"));
                                t6.setText(jsonObj.getString("post"));
                                t7.setText(jsonObj.getString("city"));
                                t8.setText(jsonObj.getString("email"));
                                t9.setText(jsonObj.getString("phone"));
                                String photo=jsonObj.getString("photo");
                                Toast.makeText(view_profile.this, photo, Toast.LENGTH_SHORT).show();
                                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                String ip=sh.getString("ip","");

                                String url="http://" + ip + ":8000"+photo;


                                Picasso.with(getApplicationContext()).load(url). into(im1);



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

    @Override
    public void onClick(View view) {
        Intent i = new Intent(getApplicationContext(), editprofile.class);
        startActivity(i);


    }
}