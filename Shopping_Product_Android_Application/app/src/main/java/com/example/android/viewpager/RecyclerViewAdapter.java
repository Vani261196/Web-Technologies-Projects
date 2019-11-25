package com.example.android.viewpager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<item> mData;
    public static final String EXTRA_ITEMJSON = "com.example.android.viewpager.EXTRA_ITEMJSON";
    public static final String EXTRA_ITEMID = "com.example.android.viewpager.EXTRA_ITEMID";
    public static final String EXTRA_SHIP = "com.example.android.viewpager.EXTRA_SHIP";
    public static final String EXTRA_KEY = "com.example.android.viewpager.EXTRA_KEY";
    public static final String EXTRA_URL = "com.example.android.viewpager.EXTRA_URL";
    public static final String EXTRA_TITLE = "com.example.android.viewpager.EXTRA_TITLE";
    public static final String EXTRA_PRICE = "com.example.android.viewpager.EXTRA_PRICE";

    public RecyclerViewAdapter(Context mContext, List<item> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflator = LayoutInflater.from(mContext);
        view = mInflator.inflate(R.layout.cardview_item_detail,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        String title = mData.get(position).getTitle();
        if(title.length() > 40)
            title = title.substring(0, 40)+"...";
        holder.tv_item_title.setText(title);
        final String itemurl = mData.get(position).getItemurl();
        final String itemid = mData.get(position).getItemid();
        final String key = mData.get(position).getKeyword();
        String img_src = mData.get(position).getImg_src().toString();

        Picasso.with(mContext).load(mData.get(position).getImg_src()).into(holder.img_item_thumbnail);
        String pin= "Zip:"+ mData.get(position).getZip();
        final String ship= mData.get(position).getShip();
        final String price = mData.get(position).getPrice();
        final String condition = mData.get(position).getState();
        holder.zip_item.setText(pin);
        holder.ship_item.setText(ship);
        holder.condition_item.setText(condition);
        holder.cost_view.setText("$"+price);
        SharedPreferences sharedpref = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedpref.edit();
        if( sharedpref.getString(itemid, null) == null){
            holder.cart_img.setImageResource(R.drawable.cart_plus);
            holder.cart_img.setTag(R.drawable.cart_plus);
        }else{
            holder.cart_img.setImageResource(R.drawable.cart_remove);
            holder.cart_img.setTag(R.drawable.cart_remove);
        }
//        holder.cart_img.setImageResource(R.drawable.cart_plus);
//        holder.cart_img.setTag(R.drawable.cart_plus);
        //final String content = "{"+ "title:"+title+",itemid:"+itemid+",key:"+key+",pin:"+pin+",ship:"+ship+",price"+price+",condition:"+condition+"}";
        JSONObject sendcontent = new JSONObject();
        try {
            sendcontent.put("title",title);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            sendcontent.put("title",title);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            sendcontent.put("itemid",itemid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            sendcontent.put("key",key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            sendcontent.put("pin",pin);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            sendcontent.put("ship",ship);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            sendcontent.put("price",price);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            sendcontent.put("img_url",img_src);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            sendcontent.put("condition",condition);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            sendcontent.put("itemurl",itemurl);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String content = sendcontent.toString();
        final String finalTitle1;
        if(title.length()>30){
             finalTitle1 = title.substring(0,30)+"...";
        }
        else
            finalTitle1 = title;
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext , itemdetail.class);
                intent.putExtra(EXTRA_ITEMID, itemid);
                intent.putExtra(EXTRA_KEY, key);
                intent.putExtra(EXTRA_SHIP, ship);
                intent.putExtra(EXTRA_ITEMJSON, content);
                intent.putExtra(EXTRA_URL, itemurl);
                intent.putExtra(EXTRA_TITLE, finalTitle1);
                intent.putExtra(EXTRA_PRICE, price);
                mContext.startActivity(intent);
            }
        });

        final String finalTitle = title;
        holder.cart_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SharedPreferences sharedPref = mContext.getPreferences(Context.MODE_PRIVATE);

                Integer resource = (Integer)holder.cart_img.getTag();
                if(resource == R.drawable.cart_plus){
                    holder.cart_img.setImageResource(R.drawable.cart_remove);
                    holder.cart_img.setTag(R.drawable.cart_remove);
                    Toast.makeText(view.getContext(), finalTitle +" was added to wishlist",Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedpref = PreferenceManager.getDefaultSharedPreferences(mContext);
                    SharedPreferences.Editor editor = sharedpref.edit();
                    editor.putString(itemid, content);
                    editor.commit();
                    // do something
                }else {
                    holder.cart_img.setImageResource(R.drawable.cart_plus);
                    holder.cart_img.setTag(R.drawable.cart_plus);
                    Toast.makeText(view.getContext(), finalTitle +" was removed from wishlist",Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedpref = PreferenceManager.getDefaultSharedPreferences(mContext);
                    SharedPreferences.Editor editor = sharedpref.edit();
                    editor.remove(itemid);
                    //editor.putString(itemid, content);
                    editor.commit();

                }

            }
        });

        //holder.img_item_thumbnail.setImageURI(mData.get(position).getImg_src());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_item_title;
        ImageView img_item_thumbnail;
        TextView zip_item;
        TextView ship_item;
        CardView cardView;
        TextView condition_item;
        TextView cost_view;
        ImageButton cart_img;
        public MyViewHolder(View itemView){
            super(itemView);
            tv_item_title = (TextView) itemView.findViewById(R.id.item_title_id);
            img_item_thumbnail = (ImageView) itemView.findViewById(R.id.item_img_id);
            zip_item = (TextView) itemView.findViewById(R.id.zip_id);
            ship_item = (TextView) itemView.findViewById(R.id.shipping);
            cardView = (CardView) itemView.findViewById(R.id.cardviewid);
            condition_item = (TextView) itemView.findViewById(R.id.condition_furb);
            cost_view = (TextView) itemView.findViewById(R.id.cost);
            cart_img = (ImageButton) itemView.findViewById(R.id.imagebutton);



        }
    }
}
