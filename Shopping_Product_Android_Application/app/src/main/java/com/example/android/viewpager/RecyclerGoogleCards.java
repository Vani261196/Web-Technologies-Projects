package com.example.android.viewpager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerGoogleCards extends RecyclerView.Adapter<RecyclerGoogleCards.MyViewHolder> {

    private Context mContext;
    private List<PhotosList> mData;

    public RecyclerGoogleCards(Context mContext, List<PhotosList> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public RecyclerGoogleCards.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.google_photos_card, parent, false);
        return new RecyclerGoogleCards.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerGoogleCards.MyViewHolder holder, final int position) {
        //holder.img_url.setImageURI(mData.get(position).getImgLink());
        Picasso.with(mContext).load(mData.get(position).getImgLink()).into(holder.img_url);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView img_url;
        CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            img_url = (ImageView) itemView.findViewById(R.id.google_card);
            cardView = (CardView) itemView.findViewById(R.id.googleCard);
        }
    }
}
