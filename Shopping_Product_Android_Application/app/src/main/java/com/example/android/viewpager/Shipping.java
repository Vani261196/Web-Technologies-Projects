package com.example.android.viewpager;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class Shipping extends Fragment {


    public Shipping() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        View view =  inflater.inflate(R.layout.shippingtab, container, false);
//
//            TableLayout ll = (TableLayout) view.findViewById(R.id.shipping_table);
//
//
//            for (int i = 0; i <2; i++) {
//
//                TableRow row= new TableRow(getActivity().getApplicationContext());
//                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
//                row.setLayoutParams(lp);
//                TextView image = new TextView(getActivity().getApplicationContext());
//                TextView heading = new TextView(getActivity().getApplicationContext());
//
//                image.setText("hello");
//                heading.setText("10");
//                row.addView(image);
//                row.addView(heading);
//                System.out.print("row added "+i);
//
//                ll.addView(row,i);
//            }
//        return view;
        final View rootView = inflater.inflate(R.layout.shippingtab, container,
                false);

        final TextView txt = (TextView) rootView.findViewById(R.id.hashval);
//        txt.setText("Blood Pressure History Logs");
        final String itemid_frag = getArguments().getString("itemid");
        final String ship = getArguments().getString("ship");
        String url = "https://product-ebay-search.appspot.com/getitems?itemid="+itemid_frag;
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        if(itemid_frag != null){
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            JSONObject result = response;
                            JSONObject sold = null;
                            HashMap<String,String> ship_val = new HashMap<>();
                            ship_val.put("ItemID", itemid_frag);

                            //SoldBy info
                            try {
                                sold = result.getJSONObject("Item").getJSONObject("Storefront");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if(sold != null) {
                                try {
                                    String storeurl = sold.getString("StoreURL");
                                    ship_val.put("storeurl", storeurl);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    String storename = sold.getString("StoreName");
                                    ship_val.put("storename", storename);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            //storename, storeurl, FeedbackScore, PositiveFeedbackPercent, FeedbackRatingStar

                            JSONObject seller = null;
                            try {
                                seller = result.getJSONObject("Item").getJSONObject("Seller");
                                //ship_val.put("Seller",seller.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if(seller != null){
                                int FeedbackScore = 0;
                                try {
                                    FeedbackScore = seller.getInt("FeedbackScore");
                                    ship_val.put("FeedbackScore", Integer.toString(FeedbackScore));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Double PositiveFeedbackPercent = 0.0;
                                try {
                                    PositiveFeedbackPercent = seller.getDouble("PositiveFeedbackPercent");
                                    ship_val.put("PositiveFeedbackPercent", Double.toString(PositiveFeedbackPercent));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                String FeedbackRatingStar = null;
                                try {
                                    FeedbackRatingStar = seller.getString("FeedbackRatingStar");
                                    ship_val.put("FeedbackRatingStar", FeedbackRatingStar);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            //Shipping info
                            ship_val.put("ShippingCost", ship);

                            //ShippingCost, GlobalShipping, HandlingTime, ConditionDescription

                            //globalshipping
                            Boolean globalshipping = false;

                            try {
                                globalshipping = result.getJSONObject("Item").getBoolean("GlobalShipping");
                                if(globalshipping) ship_val.put("GlobalShipping", "Yes");
                                else ship_val.put("GlobalShipping", "No");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            //HandlingTime
                            int HandlingTime =0;

                            try {
                                HandlingTime = result.getJSONObject("Item").getInt("HandlingTime");
                                ship_val.put("HandlingTime", Integer.toString(HandlingTime));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            //ConditionDescription
                            String ConditionDescription = null;
                            try {
                                ConditionDescription = result.getJSONObject("Item").getString("ConditionDescription");
                                ship_val.put("ConditionDescription", ConditionDescription);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            //Return Policy

                            JSONObject Returnpolicy = null;

                            try {
                                Returnpolicy = result.getJSONObject("Item").getJSONObject("ReturnPolicy");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            //returns accepted
                            //ReturnsAccepted,ReturnsWithin,Refund,ShippingCostPaidBy
                            if(Returnpolicy != null){
                                //ReturnsAccepted
                                String ReturnsAccepted = null;
                                try {
                                    ReturnsAccepted = Returnpolicy.getString("ReturnsAccepted");
                                    ship_val.put("ReturnsAccepted", ReturnsAccepted);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                //ReturnsWithin
                                String ReturnsWithin = null;
                                try {
                                    ReturnsWithin = Returnpolicy.getString("ReturnsWithin");
                                    ship_val.put("ReturnsWithin", ReturnsWithin);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                //Refund
                                String Refund = null;

                                try {
                                    Refund = Returnpolicy.getString("Refund");
                                    ship_val.put("Refund", Refund);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                //ShippingCostPaidBy

                                String ShippingCostPaidBy = null;

                                try {
                                    ShippingCostPaidBy = Returnpolicy.getString("ShippingCostPaidBy");
                                    ship_val.put("ShippingCostPaidBy", ShippingCostPaidBy);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }


                            //txt.setText(ship_val.toString());

                            createtable(ship_val, rootView);
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error

                        }
                    });
            queue.add(jsonObjectRequest);
        }

//        TableLayout tl;
//        tl = (TableLayout) rootView.findViewById(R.id.shipping_table);
//
//        for (int i = 0; i < 30; i++) {
//
//            TableRow tr = new TableRow(getActivity());
//
//            tr.setId(i);
//            tr.setBackgroundResource(R.color.bluetheme);
//            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
//
//            //TEXTVIEWS********
//            TextView tv1 = new TextView(getActivity());
//            tv1.setText("TEST NUMBER");
//            tv1.setId(i);
//            tv1.setTextColor(Color.WHITE);
//            tv1.setTextSize(20);
//            tv1.setPadding(5, 5, 5, 5);
//            tr.addView(tv1);
//
//            TextView tv2 = new TextView(getActivity());
//
//            tv2.setText("nº: "+ i);
//            tv2.setId(i+i);
//            tv2.setTextColor(Color.WHITE);
//            tv1.setTextSize(20);
//            tv2.setPadding(5, 5, 5, 5);
//            tr.addView(tv2);
//
//            tl.addView(tr, new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
//
//        }

        return rootView;

    }

    public void createtable(HashMap<String,String> ship_val , View rootView){
        TableLayout tl;
        tl = (TableLayout) rootView.findViewById(R.id.shipping_table);
        //storename, storeurl, FeedbackScore, PositiveFeedbackPercent, FeedbackRatingStar

        String[] soldby = new String[]{"storename", "storeurl", "FeedbackScore", "PositiveFeedbackPercent", "FeedbackRatingStar"};
        String[] header = new String[]{"Store name", "Do Nothing","Feedback Score", "Popularity","Feedback Star"};
        int j=0;
        if( ship_val.containsKey("storename") || ship_val.containsKey("storeurl") || ship_val.containsKey("FeedbackScore") || ship_val.containsKey("PositiveFeedbackPercent") || ship_val.containsKey("FeedbackRatingStar") ){
            TableRow tr = new TableRow(getActivity());
            tr.setId(j++);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            ImageView icontruck = new ImageView(getActivity());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(40, 40);
            icontruck.setLayoutParams(layoutParams);
            icontruck.setImageResource(R.drawable.truck);
//            icontruck.getLayoutParams().width = 20;
            tr.addView(icontruck);

            TextView tv1 = new TextView(getActivity());
            tv1.setText("Sold By:");
            tv1.setId(j++);
            tv1.setTextColor(Color.BLACK);
            tv1.setTypeface(Typeface.MONOSPACE,Typeface.BOLD);
            tv1.setTextSize(20);
            tv1.setPadding(5, 5, 5, 5);
            tr.addView(tv1);

            TextView tv2 = new TextView(getActivity());
            tv2.setText("nº: "+ j);
            tv2.setId(j++);
            tv2.setTextColor(Color.WHITE);
            tv1.setTextSize(20);
            tv2.setPadding(5, 5, 5, 5);
            tr.addView(tv2);

            tl.addView(tr, new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            for(int k=0; k<soldby.length;k++){
                if(ship_val.containsKey(soldby[k])){

                    TableRow tr1 = new TableRow(getActivity());
                    tr1.setId(j++);
                    tr1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                    ImageView icon = new ImageView(getActivity());
                    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(40, 40);
                    icon.setLayoutParams(layoutParams1);
                    //icon.getLayoutParams().width = 20;
                    tr1.addView(icon);

                    TextView tv11 = new TextView(getActivity());

                    if(soldby[k]=="storename"){
                        TextView tv12 = new TextView(getActivity());
                        tv12.setText("Store Name");
                        tv12.setId(j++);
                        tv12.setTextColor(Color.BLACK);
                        tv12.setTextSize(18);
                        tv12.setPadding(5, 5, 5, 5);
                        tr1.addView(tv12);

                        String store = ship_val.get("storename");
                        String storeurl = "<a href="+ship_val.get("storeurl")+">";
                        tv11.setText(Html.fromHtml(storeurl+" "+store));
//                        tv.setText(Html.fromHtml("<a href=http://www.stackoverflow.com> STACK OVERFLOW "));
                        tv11.setMovementMethod(LinkMovementMethod.getInstance());
                        tv11.setId(j++);
                        tv11.setTextColor(Color.BLACK);
                        tv11.setTextSize(18);
                        tv11.setPadding(5, 5, 5, 5);
                        tr1.addView(tv11);
                        tl.addView(tr1, new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    }
                    else if(soldby[k]!="storeurl"){

//                        if(soldby[k]=="PositiveFeedbackPercent"){
//                            CircularProgressBar circularProgressBar = new CircularProgressBar(this,true);
//                            circularProgressBar.setColor(ContextCompat.getColor(this, R.color.progressBarColor));
//                            circularProgressBar.setBackgroundColor(ContextCompat.getColor(this, R.color.backgroundProgressBarColor));
//                            circularProgressBar.setProgressBarWidth(getResources().getDimension(R.dimen.progressBarWidth));
//                            circularProgressBar.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.backgroundProgressBarWidth));
//                            int animationDuration = 2500; // 2500ms = 2,5s
//                            circularProgressBar.setProgressWithAnimation(65, animationDuration);
//
//                        }

//                            <antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator
//                        android:id="@+id/circular_progress"
//                        android:layout_width="200dp"
//                        android:layout_height="200dp"
//                        android:layout_gravity="center"
//                        android:layout_marginBottom="8dp"
//                        android:layout_marginTop="8dp" />
                        if(soldby[k]=="FeedbackRatingStar"){
                            TextView tv12 = new TextView(getActivity());
                            String textset = header[k];
                            tv12.setText(textset);
                            tv12.setId(j++);
                            tv12.setTextColor(Color.BLACK);
                            tv12.setTextSize(18);
                            tv12.setPadding(5, 5, 5, 5);
                            tr1.addView(tv12);

                            ImageView iv12 = new ImageView(getActivity());
                            String value_ship = ship_val.get("FeedbackScore");
                            Double score = Double.parseDouble(value_ship.trim());
                            System.out.println(score);
                            if((Double.compare(score, 10.0) > 0) && (Double.compare(score, 49.0) < 0) )
                                iv12.setImageResource(R.drawable.yellow_star_circle);
                            else if((Double.compare(score, 50.0) > 0) && (Double.compare(score, 99.0) < 0) || (Double.compare(score, 50.0) == 0) || (Double.compare(score, 99.0) == 0) )
                                iv12.setImageResource(R.drawable.blue_star_circle);
                            else if((Double.compare(score, 100.0) > 0) && (Double.compare(score, 499.0) < 0) || (Double.compare(score, 100.0) == 0) || (Double.compare(score, 499.0) == 0) )
                                iv12.setImageResource(R.drawable.turq_star_circle);
                            else if((Double.compare(score, 500.0) > 0) && (Double.compare(score, 999.0) < 0) || (Double.compare(score, 500.0) == 0) || (Double.compare(score, 999.0) == 0) )
                                iv12.setImageResource(R.drawable.purple_star_circle);
                            else if((Double.compare(score, 1000.0) > 0) && (Double.compare(score, 4999.0) < 0) || (Double.compare(score, 1000.0) == 0) || (Double.compare(score, 4999.0) == 0) )
                                iv12.setImageResource(R.drawable.red_star_circle);
                            else if((Double.compare(score, 5000.0) > 0) && (Double.compare(score, 9999.0) < 0) )
                                iv12.setImageResource(R.drawable.green_star_circle);
                            else if(Double.compare(score, 10000.0) > 0)
                                iv12.setImageResource(R.drawable.yellow_star_circle_outline);
                            else
                                iv12.setImageResource(R.drawable.black_star_circle);
//                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                            lp.setMargins(0, 100, 100, 100);
//                            iv12.setLayoutParams(lp);
                            iv12.setPadding(5, 5, 5, 5);
                            tr1.addView(iv12);

                            tl.addView(tr1, new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));





                        }
                        else {

                            TextView tv12 = new TextView(getActivity());
                            String textset = header[k];
                            tv12.setText(textset);
                            tv12.setId(j++);
                            tv12.setTextColor(Color.BLACK);
                            tv12.setTextSize(18);
                            tv12.setPadding(5, 5, 5, 5);
                            tr1.addView(tv12);

                            String value_ship = ship_val.get(soldby[k]);
                            tv11.setText(value_ship);
//                        tv.setText(Html.fromHtml("<a href=http://www.stackoverflow.com> STACK OVERFLOW "));
                            tv11.setMovementMethod(LinkMovementMethod.getInstance());
                            tv11.setId(j++);
                            tv11.setTextColor(Color.BLACK);
                            tv11.setTextSize(18);
                            tv11.setPadding(5, 5, 5, 5);
                            tr1.addView(tv11);
                            tl.addView(tr1, new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                        }
                        }

                }
            }





        }

        //ShippingCost, GlobalShipping, HandlingTime, ConditionDescription

        if( ship_val.containsKey("ShippingCost") || ship_val.containsKey("GlobalShipping") || ship_val.containsKey("HandlingTime") || ship_val.containsKey("ConditionDescription") ) {
            TableRow tr = new TableRow(getActivity());
            tr.setId(j++);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            ImageView icontruck = new ImageView(getActivity());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(40, 40);
            icontruck.setLayoutParams(layoutParams);
            icontruck.setImageResource(R.drawable.ferry);
//            icontruck.getLayoutParams().width = 20;
            tr.addView(icontruck);

            TextView tv1 = new TextView(getActivity());
            tv1.setText("Shipping Info:");
            tv1.setId(j++);
            tv1.setTextColor(Color.BLACK);
            tv1.setTypeface(Typeface.MONOSPACE,Typeface.BOLD);
            tv1.setTextSize(20);
            tv1.setPadding(5, 5, 5, 5);
            tr.addView(tv1);

            TextView tv2 = new TextView(getActivity());
            tv2.setText("nº: "+ j);
            tv2.setId(j++);
            tv2.setTextColor(Color.WHITE);
            tv1.setTextSize(20);
            tv2.setPadding(5, 5, 5, 5);
            tr.addView(tv2);
            //ShippingCost, GlobalShipping, HandlingTime, ConditionDescription
            String[] shipinginfo = new String[]{"ShippingCost", "GlobalShipping", "HandlingTime", "ConditionDescription"};
            String[] header_ship = new String[]{"Shipping Cost", "Global Shipping","Handling Time", "Condition"};

            tl.addView(tr, new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            for(int k=0; k<shipinginfo.length;k++){
                if(ship_val.containsKey(shipinginfo[k])){

                    TableRow tr1 = new TableRow(getActivity());
                    tr1.setId(j++);
                    tr1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                    ImageView icon = new ImageView(getActivity());
                    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(40, 40);
                    icon.setLayoutParams(layoutParams1);
                    //icon.getLayoutParams().width = 20;
                    tr1.addView(icon);

                    TextView tv11 = new TextView(getActivity());


                        TextView tv12 = new TextView(getActivity());
                        String textset = header_ship[k];
                        tv12.setText(textset);
                        tv12.setId(j++);
                        tv12.setTextColor(Color.BLACK);
                        tv12.setTextSize(18);
                        tv12.setPadding(5, 5, 5, 5);
                        tr1.addView(tv12);

                        String value_ship = ship_val.get(shipinginfo[k]);
                        tv11.setText(value_ship);
//                        tv.setText(Html.fromHtml("<a href=http://www.stackoverflow.com> STACK OVERFLOW "))
                        tv11.setId(j++);
                        tv11.setTextColor(Color.BLACK);
                        tv11.setTextSize(18);
                        tv11.setPadding(5, 5, 5, 5);
                        tr1.addView(tv11);
                        tl.addView(tr1, new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));


                }
            }





        }
        ////ReturnsAccepted,ReturnsWithin,Refund,ShippingCostPaidBy

        if( ship_val.containsKey("ReturnsAccepted") || ship_val.containsKey("ReturnsWithin") || ship_val.containsKey("Refund") || ship_val.containsKey("ShippingCostPaidBy") ) {
            TableRow tr = new TableRow(getActivity());
            tr.setId(j++);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            ImageView icontruck = new ImageView(getActivity());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(40, 40);
            icontruck.setLayoutParams(layoutParams);
            icontruck.setImageResource(R.drawable.dump_truck);
//            icontruck.getLayoutParams().width = 20;
            tr.addView(icontruck);

            TextView tv1 = new TextView(getActivity());
            tv1.setText("Return Policy:");
            tv1.setId(j++);
            tv1.setTextColor(Color.BLACK);
            tv1.setTypeface(Typeface.MONOSPACE,Typeface.BOLD);
            tv1.setTextSize(20);
            tv1.setPadding(5, 5, 5, 5);
            tr.addView(tv1);

            TextView tv2 = new TextView(getActivity());
            tv2.setText("nº: "+ j);
            tv2.setId(j++);
            tv2.setTextColor(Color.WHITE);
            tv1.setTextSize(20);
            tv2.setPadding(5, 5, 5, 5);
            tr.addView(tv2);
            ///ReturnsAccepted,ReturnsWithin,Refund,ShippingCostPaidBy
            String[] shipinginfo = new String[]{"ReturnsAccepted", "ReturnsWithin", "Refund", "ShippingCostPaidBy"};
            String[] header_ship = new String[]{"Policy", "Returns Within","Refund Mode", "Shipped by"};

            tl.addView(tr, new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            for(int k=0; k<shipinginfo.length;k++){
                if(ship_val.containsKey(shipinginfo[k])){

                    TableRow tr1 = new TableRow(getActivity());
                    tr1.setId(j++);
                    tr1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                    ImageView icon = new ImageView(getActivity());
                    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(40, 40);
                    icon.setLayoutParams(layoutParams1);
                    //icon.getLayoutParams().width = 20;
                    tr1.addView(icon);

                    TextView tv11 = new TextView(getActivity());


                    TextView tv12 = new TextView(getActivity());
                    String textset = header_ship[k];
                    tv12.setText(textset);
                    tv12.setId(j++);
                    tv12.setTextColor(Color.BLACK);
                    tv12.setTextSize(18);
                    tv12.setPadding(5, 5, 5, 5);
                    tr1.addView(tv12);

                    String value_ship = ship_val.get(shipinginfo[k]);
                    tv11.setText(value_ship);
//                        tv.setText(Html.fromHtml("<a href=http://www.stackoverflow.com> STACK OVERFLOW "))
                    tv11.setId(j++);
                    tv11.setTextColor(Color.BLACK);
                    tv11.setTextSize(18);
                    tv11.setPadding(5, 5, 5, 5);
                    tr1.addView(tv11);
                    tl.addView(tr1, new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));


                }
            }





        }





    }

}
