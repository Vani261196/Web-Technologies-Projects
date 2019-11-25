package com.example.android.viewpager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class wishlistadapter extends RecyclerView.Adapter<wishlistadapter.MyViewHolder>  {
    public static wishlistadapter adapter;
    private Context mContext;
    private List<item> mData;
    private View view1;
    private int flag;
    public static final String EXTRA_ITEMJSON = "com.example.android.viewpager.EXTRA_ITEMJSON";
    public static final String EXTRA_ITEMID = "com.example.android.viewpager.EXTRA_ITEMID";
    public static final String EXTRA_SHIP = "com.example.android.viewpager.EXTRA_SHIP";
    public static final String EXTRA_KEY = "com.example.android.viewpager.EXTRA_KEY";


    public wishlistadapter(Context mContext, List<item> mData, View view , int flag) {
        this.view1 = view;
        this.mContext = mContext;
        this.mData = mData;
        this.flag = flag;
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
        final String itemid = mData.get(position).getItemid();
        final String key = mData.get(position).getKeyword();

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

        final String content = sendcontent.toString();


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext , itemdetail.class);
                intent.putExtra(EXTRA_ITEMID, itemid);
                intent.putExtra(EXTRA_KEY, key);
                intent.putExtra(EXTRA_SHIP, ship);
                intent.putExtra(EXTRA_ITEMJSON, content);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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


//                    FragmentTransaction ft = ((AppCompatActivity)mContext).getSupportFragmentManager().beginTransaction();
//
//                    ft.detach(this).attach(this).commit();
                    // do something
                }else {
                    holder.cart_img.setImageResource(R.drawable.cart_plus);
                    holder.cart_img.setTag(R.drawable.cart_plus);

                    Toast.makeText(view.getContext(), finalTitle +" was removed from wishlist",Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedpref = PreferenceManager.getDefaultSharedPreferences(mContext);
                    SharedPreferences.Editor editor = sharedpref.edit();
                    System.out.println("before removal");
                    System.out.println(sharedpref.getAll().toString());

                    editor.remove(itemid);
                    //editor.putString(itemid, content);
                    editor.commit();
                    System.out.println("after removal");
                    System.out.println(sharedpref.getAll().toString());
                    item cur1 = null;
                    int i =0;
                    for(  i=0; i< mData.size();i++){
                        if(mData.get(i).getItemid().equals(itemid)){
                            System.out.println("Found: "+mData.get(i).getItemid());
                            break;
                        }
                    }
                    if(i!=mData.size()){
                        mData.remove(mData.get(i));
                        System.out.println("removed: ");
                    }

                    for(  i=0; i< mData.size();i++){
                        if(mData.get(i).getItemid().equals(itemid)){
                            System.out.println("Found: ");
                            break;
                        }
                    }

                    Double Total =0.0;
                    for(item wish: mData){
                        Total += Double.parseDouble(wish.getPrice());
                    }
                    TextView total = (TextView) view1.findViewById(R.id.Total);
                    total.setText("Wishlist total("+mData.size()+" items):                   $"+Double.toString(Total));

                    if(Total == 0.0){
                        TextView nowish = (TextView) view1.findViewById(R.id.content);
                        nowish.setText("No Wishes");
                    }

                    notifyDataSetChanged();

                    if(flag ==0){ flag=1;}
                    else{flag=0;}




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
        TextView total;
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
            //total =  (TextView) itemView.findViewById(R.id.Total);



        }
    }
}
