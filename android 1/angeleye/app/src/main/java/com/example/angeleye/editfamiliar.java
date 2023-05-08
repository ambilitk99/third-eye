package com.example.angeleye;

import android.annotation.SuppressLint;
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
import android.util.Patterns;
import android.view.View;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

    public class editfamiliar extends AppCompatActivity implements View.OnClickListener {
        EditText e1, e2;
        Button b1;
        ImageView im1;



        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_editfamiliar);
            e1 = findViewById(R.id.editTextTextPersonName11);
            e2 = findViewById(R.id.editTextTextPersonName18);
            b1 = findViewById(R.id.button4);
            im1 = findViewById(R.id.imageView6);
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

                            //     Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                            // response
                            try {
                                JSONObject jsonObj = new JSONObject(response);
                                if (jsonObj.getString("status").equalsIgnoreCase("ok")) {


//                                Toast.makeText(getApplicationContext(), "Successfull", Toast.LENGTH_LONG).show();


//                                Toast.makeText(viewfamiliar.this, response, Toast.LENGTH_SHORT).show();
//                                JSONObject js= jsonObj.getJSONObject("name");


                                    e1.setText(jsonObj.getString("name"));
                                    e2.setText(jsonObj.getString("relation"));
                                    String photo = jsonObj.getString("photo");
//                                Toast.makeText(viewfamiliar.this, photo, Toast.LENGTH_SHORT).show();
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


            im1.setOnClickListener(this);

        }


        void showfilechooser(int string) {
            // TODO Auto-generated method stub
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            //getting all types of files

            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);

            try {
                startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), string);
            } catch (android.content.ActivityNotFoundException ex) {
                // Potentially direct the user to the Market with a Dialog
                Toast.makeText(getApplicationContext(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();

            }
        }

        String path, atype, fname, attach = "no", attatch1;
        byte[] byteArray = null;

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK) {
                if (requestCode == 1) {
                    ////
                    Uri uri = data.getData();


                    try {
                        path = FileUtils.getPath(this, uri);

                        File fil = new File(path);
                        float fln = (float) (fil.length() / 1024);
                        atype = path.substring(path.lastIndexOf(".") + 1);


                        fname = path.substring(path.lastIndexOf("/") + 1);
//                    ed15.setText(fname);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }

                    try {

                        File imgFile = new File(path);

                        if (imgFile.exists()) {

                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                            im1.setImageBitmap(myBitmap);

                        }


                        File file = new File(path);
                        byte[] b = new byte[8192];
                        Log.d("bytes read", "bytes read");

                        InputStream inputStream = new FileInputStream(file);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();

                        int bytesRead = 0;

                        while ((bytesRead = inputStream.read(b)) != -1) {
                            bos.write(b, 0, bytesRead);
                        }
                        byteArray = bos.toByteArray();

                        String str = Base64.encodeToString(byteArray, Base64.NO_WRAP);
                        attach = str;


                    } catch (Exception e) {
                        Toast.makeText(this, "String :" + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }


                    ///

                }
            }
        }

        @Override
        public void onClick(View view) {
            if
            (view == im1) {
                showfilechooser(1);
            } else {
                String name = e1.getText().toString();
                String relation = e2.getText().toString();



                if (name.length() == 0) {
                    e1.setError("Missing");
                    e1.requestFocus();
                }
                else if (!name.matches("^[a-z,A-Z]*$"))
                {
                    e1.setError("Characters Allowed");
                    e1.requestFocus();
                }
                else if (relation.length() == 0) {
                    e2.setError("Missing");
                    e2.requestFocus();
                }


                    SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    final String maclis = sh.getString("mac_list", "");
                    String uid = sh.getString("uid", "");
                    String hu = sh.getString("ip", "");
                    String url = "http://" + hu + ":8000/myapp/editfamiliar/";


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

                                            Intent i = new Intent(getApplicationContext(), viewfamiliar.class);
                                            startActivity(i);
//                                        Toast.makeText(getApplicationContext(), "Successfull", Toast.LENGTH_LONG).show();


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
//
//
                            params.put("name", name);
                            params.put("relation", relation);

                            params.put("photo", attach);



                            params.put("mac", maclis);

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











