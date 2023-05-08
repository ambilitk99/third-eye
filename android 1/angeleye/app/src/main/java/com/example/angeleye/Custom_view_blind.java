package com.example.angeleye;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Custom_view_blind extends BaseAdapter {
    String[]  id, image, name,dob,gender,place,email,phone;
    Button edit,delete,familiar;
    private Context context;

    public Custom_view_blind(Context appcontext,String[]id,String[]image,String[]name,String[]dob,String[]gender,String[]place,String[]email,String[]phone)
    {
        this.context=appcontext;
        this.id=id;
        this.name=name;
        this.dob=dob;
        this.place=place;
        this.email=email;
        this.phone=phone;
        this.gender=gender;
        this.image=image;



    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(view==null)
        {
            gridView=new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView=inflator.inflate(R.layout.custom_blind_view,null);

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView12);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView13);
        TextView tv3=(TextView)gridView.findViewById(R.id.textView14);
        TextView tv4=(TextView)gridView.findViewById(R.id.textView15);
        TextView tv5=(TextView)gridView.findViewById(R.id.textView16);
        ImageView im=(ImageView) gridView.findViewById(R.id.imageView5);
//        Button fperson=(Button) gridView.findViewById(R.id.button5);
        Button viewfperson=(Button) gridView.findViewById(R.id.button9);
        Button edit=(Button) gridView.findViewById(R.id.button7);
        Button viewemergency=(Button)gridView.findViewById(R.id.button11);



//        Button edit=(Button)  gridView.findViewById(R.id.button6);
//        Button delete=(Button)gridView.findViewById((R.id.button7));


//
//        fperson.setTag(id[i]);
//
//
//        fperson.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(context);
//                SharedPreferences.Editor ed=sh.edit();
//                ed.putString("blid",v.getTag().toString());
//                ed.commit();
//                Intent in= new Intent(context,add_familiar_person.class);
//                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(in);
//            }
//        });
//

        viewfperson.setTag(id[i]);


        viewfperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("blid",v.getTag().toString());
                ed.commit();
                Intent in= new Intent(context,viewfamiliar.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(in);
            }
        });

        edit.setTag(id[i]);


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("blid",v.getTag().toString());
                ed.commit();
                Intent in= new Intent(context,editblind.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(in);
            }
        });

        viewemergency.setTag(id[i]);


        viewemergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("blid",v.getTag().toString());
                ed.commit();
                Intent in= new Intent(context,viewemergency.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(in);
            }
        });




        tv1.setTextColor(Color.BLACK);


        tv1.setText(name[i]);
        tv2.setText(gender[i]);
        tv3.setText(dob[i]);
        tv4.setText(place[i]);
        tv5.setText(email[i]);



        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
        String ip=sh.getString("ip","");

        String url="http://" + ip + ":8000"+image[i];


        Picasso.with(context).load(url). into(im);

        return gridView;
    }
}
