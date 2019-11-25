package com.example.android.viewpager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class similarProductsAdapter extends RecyclerView.Adapter<similarProductsAdapter.MyViewHolder> {

    private Context mContext;
    private List<similarProductList> mData;

    public similarProductsAdapter(Context mContext, List<similarProductList> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public similarProductsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.similar_products_card, parent, false);
        return new similarProductsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(similarProductsAdapter.MyViewHolder holder, final int position) {
        final String itemurl = mData.get(position).getebayLink();
        holder.productTitle.setText(mData.get(position).getproductTitle());
        //holder.ebayLink.setText(mData.get(position).getebayLink());
        holder.price.setText(mData.get(position).getPrice());
        holder.shippingType.setText(mData.get(position).getShippingType());
        holder.daysLeft.setText(mData.get(position).getdaysLeft());
        //holder.img_url.setImageURI(mData.get(position).getImgLink());
        Picasso.with(mContext).load(mData.get(position).getImgLink()).into(holder.img_url);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = itemurl;

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView productTitle;
        //TextView ebayLink;
        TextView shippingType;
        TextView daysLeft;
        TextView price;
        ImageView img_url;
        CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            productTitle = (TextView) itemView.findViewById(R.id.cardTitle);
            daysLeft = (TextView) itemView.findViewById(R.id.daysLeft);
            shippingType = (TextView) itemView.findViewById(R.id.typeShipping);
            price = (TextView) itemView.findViewById(R.id.productPrice);
            img_url = (ImageView) itemView.findViewById(R.id.similarImg);
            cardView = (CardView) itemView.findViewById(R.id.similarCardView);
        }
    }
}
