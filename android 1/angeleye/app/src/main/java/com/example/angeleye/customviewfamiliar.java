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

public class customviewfamiliar extends BaseAdapter {
    String[]  id, image, name,relation;
//    Button edit,delete;
    private Context context;

    public customviewfamiliar(Context appcontext,String[]id,String[]image,String[]name,String[]relation)
    {
        this.context=appcontext;
        this.id=id;
        this.name=name;
        this.image=image;
        this.relation=relation;




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
            gridView=inflator.inflate(R.layout.activity_customviewfamiliar,null);

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView17);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView19);
        ImageView im=(ImageView) gridView.findViewById(R.id.imageView6);
//        Button edit=(Button) gridView.findViewById(R.id.button4);
//        Button delete=(Button) gridView.findViewById(R.id.button8);
//        Button edit=(Button)  gridView.findViewById(R.id.button6);
//        Button delete=(Button)gridView.findViewById((R.id.button7));



//        edit.setTag(id[i]);
//
//
//        edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(context);
//                SharedPreferences.Editor ed=sh.edit();
//                ed.putString("blid",v.getTag().toString());
//                ed.commit();
//                Intent in= new Intent(context,viewfamiliar.class);
//                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(in);
//            }
//        });






        tv1.setTextColor(Color.BLACK);


        tv1.setText(name[i]);
        tv2.setText(relation[i]);
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
        String ip=sh.getString("ip","");

        String url="http://" + ip + ":8000"+image[i];


        Picasso.with(context).load(url). into(im);

        return gridView;
    }
}
