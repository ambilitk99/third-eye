package com.example.angeleye;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class editblind extends AppCompatActivity {

    EditText et1, et2, et3, et4, et5;
    RadioButton rb1, rb2;
    Button b1;
    ImageView im1;

    String gender = "";

    String path, atype, fname, attach, attatch1;
    byte[] byteArray = null;
    final Calendar myCalendar= Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editblind);
        et1 = findViewById(R.id.editTextTextPersonName5);
        et2 = findViewById(R.id.editTextTextPersonName13);
        et3 = findViewById(R.id.editTextTextPersonName14);
        et4 = findViewById(R.id.editTextTextPersonName19);
        et5 = findViewById(R.id.editTextTextPersonName20);
        rb1 = findViewById(R.id.radioButton);
        rb2 = findViewById(R.id.radioButton5);
        b1 = findViewById(R.id.button);
        im1 = findViewById(R.id.imageView4);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final String maclis = sh.getString("mac_list", "");
        String uid = sh.getString("uid", "");
        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":8000/myapp/editblind/";


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //     Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        // response
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {


//                                Toast.makeText(getApplicationContext(), "Successfull", Toast.LENGTH_LONG).show();


//                                Toast.makeText(view_profile.this, response, Toast.LENGTH_SHORT).show();
//                                JSONObject js= jsonObj.getJSONObject("name");


                                et1.setText(jsonObj.getString("name"));
                                et2.setText(jsonObj.getString("dob"));
                                et3.setText(jsonObj.getString("place"));
                                et4.setText(jsonObj.getString("email"));
                                et5.setText(jsonObj.getString("phone"));
                                rb1.setText(jsonObj.getString("male"));
                                rb2.setText(jsonObj.getString("female"));
                                String photo = jsonObj.getString("photo");
//                                Toast.makeText(view_profile.this, photo, Toast.LENGTH_SHORT).show();
                                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                String ip = sh.getString("ip", "");

                                String url = "http://" + ip + ":8000" + photo;


                                Picasso.with(getApplicationContext()).load(url).into(im1);


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


                params.put("lid", sh.getString("lid", ""));

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