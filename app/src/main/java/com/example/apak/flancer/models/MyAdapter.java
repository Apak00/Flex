package com.example.apak.flancer.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.apak.flancer.Entry.FlancerMain;
import com.example.apak.flancer.R;
import com.example.apak.flancer.fragments.FlancerMainPage;
import com.example.apak.flancer.models.MyViewHolder;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by Apak on 06/05/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>   {
    Context c;
    DataofRecyclerview dataofRecyclerview;



    public MyAdapter(Context c, DataofRecyclerview dataofRecyclerview) {
        this.c = c;
        this.dataofRecyclerview = dataofRecyclerview;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.recycler_list_item,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        try{
        holder.nameText.setText(dataofRecyclerview.getWorkername().get(position));
System.out.println(dataofRecyclerview.getWorkericon().get(position));
        Picasso.with(this.c)
                .load(Uri.parse(dataofRecyclerview.getWorkericon().get(position)))
                .resize(60,60)
                .transform(new FlancerMain.CircleTransform())
                .into(holder.imageView);
        }
        catch (NullPointerException e){System.out.println("Hahaha Got u:D");}

    }

    @Override
    public int getItemCount() {return dataofRecyclerview.getWorkername().size();
    }



}