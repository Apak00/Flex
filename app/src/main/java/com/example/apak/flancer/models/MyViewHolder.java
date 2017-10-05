package com.example.apak.flancer.models;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apak.flancer.R;

/**
 * Created by Apak on 06/05/2017.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView nameText;
    ImageView imageView;
    public MyViewHolder(View itemView) {
        super(itemView);
        nameText=(TextView)itemView.findViewById(R.id.recycler_item_textview);
        imageView=(ImageView)itemView.findViewById(R.id.recycler_item_image);
    }
}
