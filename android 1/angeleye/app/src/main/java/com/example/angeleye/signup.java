package com.example.angeleye;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
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

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class signup extends AppCompatActivity implements View.OnClickListener {
    EditText et1,et2,et3,et4,et5,et6,et7,et9,et8;
    RadioButton rb1,rb2;
    Button b1;
    ImageView im1;

    String gender="";
    String name,dob,place,post,pin,city,email,phone,password;

    String path, atype, fname, attach, attatch1;
    byte[] byteArray = null;
    final Calendar myCalendar= Calendar.getInstance();



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        et1=findViewById(R.id.editTextTextPersonName);
        et2=findViewById(R.id.editTextTextPersonName2);
        et3=findViewById(R.id.editTextTextPersonName6);
        et4=findViewById(R.id.editTextTextPersonName8);
        et5=findViewById(R.id.editTextTextPersonName7);
        et6=findViewById(R.id.editTextTextPersonName9);
        et7=findViewById(R.id.editTextTextPersonName10);
        et8=findViewById(R.id.editTextTextPassword2);
        et9=findViewById(R.id.editTextTextPersonName12);

//        et10=findViewById(R.id.editTextTextPersonName11);
        rb1=findViewById(R.id.radioButton3);
        rb2=findViewById(R.id.radioButton5);
        b1=findViewById(R.id.button1);
        im1=findViewById(R.id.imageView);


        b1.setOnClickListener(this);
        im1.setOnClickListener(this);

        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        et2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog ss=  new DatePickerDialog(signup.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
                Calendar c = Calendar.getInstance();
                c.set(2005, 0, 1);
                ss.getDatePicker().setMaxDate(c.getTimeInMillis());

                ss.show();
            }
        });




    }

    @Override
    public void onClick(View view) {

        if (view==im1){
            showfilechooser(1);
        }
        else {


        name=et1.getText().toString();
        dob=et2.getText().toString();
        place=et3.getText().toString();
         pin=et4.getText().toString();
         post=et5.getText().toString();
         city=et6.getText().toString();
         email=et7.getText().toString();
         password=et8.getText().toString();
         phone=et9.getText().toString();
//         cpassword=et10.getText().toString();

        if(name.length()==0){
            et1.setError("Missing");
            et1.requestFocus();
        }

        else if (!name.matches("^[a-z,A-Z]*$"))
            {
                et1.setError("Characters Allowed");
                et1.requestFocus();
            }

        else  if(dob.length()==0){
            et2.setError("Missing");
            et2.requestFocus();

        }
        else if(place.length()==0){
            et3.setError("Missing");
            et3.requestFocus();
        }
        else if (!place.matches("^[a-z,A-Z]*$"))
        {
            et3.setError("Characters Allowed");
            et3.requestFocus();
        }
        else if(pin.length()==0){
            et4.setError("Missing");
            et4.requestFocus();
        }
        else if (pin.length() != 6) {
            et4.setError("Invalid pin");
            et4.requestFocus();
        }
        else if(post.length()==0){
            et5.setError("Missing");
            et5.requestFocus();
        }
        else if (!post.matches("^[a-z,A-Z]*$"))
        {
            et5.setError("Characters Allowed");
            et5.requestFocus();
        }

        else  if(city.length()==0){
            et6.setError("Missing");
            et6.requestFocus();
        }
        else if (!city.matches("^[a-z,A-Z]*$"))
        {
            et6.setError("Characters Allowed");
            et6.requestFocus();
        }

            else if(email.length()==0){
            et7.setError("Missing");
            et7.requestFocus();
        }
            else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                et7.setError("Enter valid email");
                et7.requestFocus();
        }
        else if(phone.length()==0){
            et9.setError("Missing");
            et9.requestFocus();
        }
        else if (phone.length() != 10) {
            et9.setError("Invalid mobile");
            et9.requestFocus();
        }

        else if (password.length()==0){
            et8.setError("Missing");
            et8.requestFocus();

        }
        else if (!password.matches(("^[a-z,A-Z]*$")))
        {
            et8.setError(" characters  allowed");
            et8.requestFocus();
        }
//        else if (!password.equals(cpassword)){
//            et8.setError("Password mismatched");
//        }
//        else  if(cpassword.length()==0){
//            et10.setError("Missing");
//            et10.requestFocus();
//        }
//        else if (!cpassword.matches(("^[a-z,A-Z]*$"))) {
//            et10.setError(" characters  allowed");
//            et10.requestFocus();
//        }
        else {

            if(rb1.isChecked()){
                gender=rb1.getText().toString();
            }
            else{
                gender=rb2.getText().toString();
            }




            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            final String maclis = sh.getString("mac_list", "");
            String uid = sh.getString("uid", "");
            String hu = sh.getString("ip", "");
            String url = "http://" + hu + ":8000/myapp/andregistration/";


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

                                    Intent i = new Intent(getApplicationContext(), login.class);
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


                    params.put("name",name);
                    params.put("dob", dob);
                    params.put("place",place);

                    params.put("pin", pin);
                    params.put("post", post);
                    params.put("city", city);

                    params.put("email", email);
                    params.put("phone", phone);
                    params.put("photo", attach);

                    params.put("password", password);
                    params.put("gender", gender);

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

    private void updateLabel(){
        String myFormat="dd-MM-yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        et2.setText(dateFormat.format(myCalendar.getTime()));
    }

}