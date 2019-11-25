package com.example.android.viewpager;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class Product extends Fragment {
    private LinearLayout mGallery;
    private String[] mImgIds;
    private ArrayList<String> imgs = new ArrayList<String>();
    private LayoutInflater mInflater;
    private HorizontalScrollView horizontalScrollView;


    public Product() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view  = inflater.inflate(R.layout.fragment_product, container, false);
        final String itemid_frag = getArguments().getString("itemid");
        final String ship = getArguments().getString("ship");
        // Inflate the layout for this fragment
        final TextView myAwesomeTextView = (TextView) view.findViewById(R.id.proddetails);


        //myAwesomeTextView.setText("Response is: "+ itemid_frag);
        //https://product-ebay-search.appspot.com/getitems?itemid=293050639534
        String url = "https://product-ebay-search.appspot.com/getitems?itemid="+itemid_frag;
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        if(itemid_frag != null){
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            JSONObject result = response;
                            //myAwesomeTextView.setText("id: " +itemid_frag+ "\n response: "+ response.toString());
                            //Image gallery
                            JSONArray img = null;
                            try {
                                img = result.getJSONObject("Item").getJSONArray("PictureURL");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

//                            try {
//                                //myAwesomeTextView.setText(img.getString(10));
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }

                            if (img.length() == 0) {
                                System.out.println("json is empty");
                            }
                            else
                            {
                                //int length = img.length();
                                for (int i=0;i<img.length();i++){
                                    try {
                                        imgs.add(img.getString(i));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                //myAwesomeTextView.setText(imgs.toString());
                            }
                            ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpage);
                            viewPagerAdapter adapter = new viewPagerAdapter(getActivity().getApplicationContext(),imgs);
                            viewPager.setAdapter(adapter);

                            //price
                            String Price = "0.0";
                            try {
                                Double price = result.getJSONObject("Item").getJSONObject("CurrentPrice").getDouble("Value");
                                Price = price.toString();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //title
                            String title_item="" ;
                            try {
                                title_item = result.getJSONObject("Item").getString("Title");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            TextView priceview = (TextView) view.findViewById(R.id.title_item);
                            priceview.setText(title_item);
                            //price shipping
                            String priceship ="";

                            //Shipping
                            if(ship.equalsIgnoreCase("free shipping"))
                            {
                                priceship = "$"+Price+" with "+ship ;
                            }
                            else
                            {
                                priceship = "$"+Price+" with "+ship +"for shipping" ;
                            }
                            TextView shipview = (TextView) view.findViewById(R.id.price_shipping_item);
                            shipview.setText(priceship);

                            //price only
                            TextView priceonlyview = (TextView) view.findViewById(R.id.Price_only);
                            priceonlyview.setText("Price          $"+Price);
                            JSONArray specs = null;
                            //brand
                            try {
                                specs = result.getJSONObject("Item").getJSONObject("ItemSpecifics").getJSONArray("NameValueList");
                                String brand="";
                                if(specs != null)
                                {try {
                                    brand = specs.getJSONObject(0).getJSONArray("Value").getString(0);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }}
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }





                            HashMap<String,String> name_val = new HashMap<>();
                            for(int i=0;i<specs.length();i++){
                                String name = null;
                                try {
                                    name = specs.getJSONObject(i).getString("Name");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                String val = null;
                                try {
                                    val = specs.getJSONObject(i).getJSONArray("Value").getString(0);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                name_val.put(name,val);

                            }

                            TextView brandonlyview = (TextView) view.findViewById(R.id.brand);
                            brandonlyview.setText("Brand          "+name_val.get("Brand"));

                            //txtTitlevalue.setText(Html.fromHtml("Line1"+"<br>"+"Line2" + " <br>"+"Line3"));
                            //setText("\u2022 Bullet");
                            TextView specsview = (TextView) view.findViewById(R.id.specs);
                            String specifics ="";
                            for (String key : name_val.keySet()) {
                                System.out.println("Key = " + key);
                                if(!key.equalsIgnoreCase("Brand")){
                                    specifics+="\u2022 ";
                                    specifics+=name_val.get(key);
                                    specifics+="<br>";
                                }
                            }
                            specsview.setText(Html.fromHtml(specifics));




                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error

                        }
                    });
            queue.add(jsonObjectRequest);
        }

        return view;
    }

    public void setpage(View view, JSONObject itemdetail, String ship, TextView myAwesomeTextView){
        //Extract data
        // urls
        String urlarray;
        //ship
//        try {
        JSONArray img = null;
        imgs = new ArrayList<String>();
        try {
            img = itemdetail.getJSONObject("Item").getJSONArray("PictureURL");
            System.out.print(img.toString());

            myAwesomeTextView.setText(img.getString(10));

            if (img.length() == 0) {
                System.out.println("json is empty");
            }
            else
            {
                //int length = img.length();
                for (int i=0;i<img.length();i++){
                    imgs.add(img.getString(i));
                }
                myAwesomeTextView.setText(imgs.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

////            mImgIds = itemdetail.getJSONObject("Item").getJSONArray("PictureURL");
////        } catch (JSONException e) {
////            e.printStackTrace();
////        }
        //ItemSpecifics
        //price
        String Price;
        try {
            Double price = itemdetail.getJSONObject("Item").getJSONObject("CurrentPrice").getDouble("Value");
            Price = price.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }





    }

//    private void initData()
//    {
//        mImgIds = new String[] { "https://i.ebayimg.com/00/s/MTYwMFgxMjAw/z/2p4AAOSw6jJcvkFl/$_12.JPG?set_id=880000500F",
//                "https://i.ebayimg.com/00/s/MTYwMFgxMjAw/z/aUQAAOSws3pcvkF4/$_12.JPG?set_id=880000500F"
//        };
//    }

//    private void initView(View view, Context mContext)
//    {
//        mGallery = (LinearLayout) view.findViewById(R.id.id_gallery);
//
//        for (int i = 0; i < imgs.size(); i++)
//        {
//            View cview = mInflater.inflate(R.layout.activity_gallery_item,
//                    mGallery, false);
//            ImageView img = (ImageView) cview
//                    .findViewById(R.id.id_index_gallery_item_image);
//            Picasso.with(mContext).load(Uri.parse(imgs.get(i))).fit().into(img);
//            TextView txt = (TextView) cview
//                    .findViewById(R.id.id_index_gallery_item_text);
//            txt.setText("info "+i);
//            mGallery.addView(cview);
//        }
//    }

}
