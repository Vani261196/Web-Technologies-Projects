package com.example.android.viewpager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Searchresults extends AppCompatActivity {
    private ProgressBar mProgress;
    List<item> lstitem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.ActionBarTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchresults);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.prod_toolbar_results);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//
//        getSupportActionBar().setTitle("Search Results"); // for set actionbar title
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        Intent intent = getIntent();
        final String name = intent.getStringExtra(MondayFragment.EXTRA_URL);
        final String title_name = intent.getStringExtra(MondayFragment.EXTRA_NAME);
        //String url = "http://product-ebay-search.appspot.com/getProducts/?keywords="+name+"&buyerPostalCode=90007&itemFilter(0).name=MaxDistance&itemFilter(0).value=10";
        final TextView myAwesomeTextView = (TextView) findViewById(R.id.result);
        final TextView search_prod_text = (TextView) findViewById(R.id.textViewsp);
        String url = name;
        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        RequestQueue queue = Volley.newRequestQueue(this);

        //String url = "http://product-ebay-search.appspot.com/getProducts/?keywords="+"iphone"+"&buyerPostalCode=90007&itemFilter(0).name=MaxDistance&itemFilter(0).value=10";
        if (!(url == null)) {

// Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            myAwesomeTextView.setText("Response is: " + response.substring(0, 500));
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    myAwesomeTextView.setText("That didn't work!");
                }
            });

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            mProgress.setVisibility(ProgressBar.INVISIBLE);
                            myAwesomeTextView.setVisibility(View.VISIBLE);
                            search_prod_text.setVisibility(View.INVISIBLE);
                            TextView countview = (TextView) findViewById(R.id.count);
                            JSONObject result = response;
                            JSONArray output = new JSONArray();
                            JSONObject searchres = new JSONObject();
                            JSONArray resultjsonarray = new JSONArray();
                            JSONObject count_item = new JSONObject();
                            JSONArray itemlist = new JSONArray();
                            String count = new String();
                            try {
                                output = result.getJSONArray("findItemsAdvancedResponse");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                searchres = output.getJSONObject(0);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                resultjsonarray = searchres.getJSONArray("searchResult");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                count_item = resultjsonarray.getJSONObject(0);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                count = count_item.getString("@count");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String text = "<b>Showing <font color='#FF7043'>"+count+"</font> results for <font color='#FF7043'>" + title_name +"</font></b><br>";

                            //countview.setText("Showing "+count+" results for "+title_name+"\n");
                            countview.setText(Html.fromHtml(text));
                            try {
                                itemlist = count_item.getJSONArray("item");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //myAwesomeTextView.setText("Response: "+ itemlist.toString());
                            String out = new String();
                            lstitem = new ArrayList<>();

                            for(int i=0, size = itemlist.length();i<size;i++){
                                JSONObject item_array = null;
                                String itemid = new String();
                                String title = new String();
                                String gallery = new String();
                                String zip = new String();
                                String Shipping = new String();
                                String condition = new String();
                                String price = new String();
                                String key = new String();
                                String itemurl = new String();
                                String subtitle = "";
                                try {
                                    item_array = itemlist.getJSONObject(i);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    itemid = item_array.getJSONArray("itemId").getString(0);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    title = item_array.getJSONArray("title").getString(0);
                                } catch (JSONException e) {
                                    title = "N/A";
                                    e.printStackTrace();
                                }

                                try {
                                    gallery = item_array.getJSONArray("galleryURL").getString(0);
                                } catch (JSONException e) {
                                    gallery = "N/A";
                                    e.printStackTrace();
                                }

                                try {
                                    itemurl = item_array.getJSONArray("viewItemURL").getString(0);
                                } catch (JSONException e) {
                                    itemurl = "N/A";
                                    e.printStackTrace();
                                }
                                try {
                                    zip = item_array.getJSONArray("postalCode").getString(0);
                                } catch (JSONException e) {
                                    zip ="N/A";
                                    e.printStackTrace();
                                }
                                try {
                                    Shipping = item_array.getJSONArray("shippingInfo").getJSONObject(0).getJSONArray("shippingServiceCost").getJSONObject(0).getString("__value__");
                                    if(Float.parseFloat(Shipping) == 0.0){
                                        Shipping = "Free Shipping";
                                    }
                                    else{
                                        Shipping = "$"+Shipping;
                                    }
                                } catch (JSONException e) {
                                    Shipping = "N/A";
                                    e.printStackTrace();
                                }
                                try {
                                    condition = item_array.getJSONArray("condition").getJSONObject(0).getJSONArray("conditionDisplayName").getString(0);
                                } catch (JSONException e) {
                                    condition = "N/A";
                                    e.printStackTrace();
                                }
                                try {
                                    price = item_array.getJSONArray("sellingStatus").getJSONObject(0).getJSONArray("currentPrice").getJSONObject(0).getString("__value__");
                                } catch (JSONException e) {
                                    price = "N/A";
                                    e.printStackTrace();
                                }
                                key = title_name;
                                lstitem.add(new item(title,gallery,zip,Shipping,condition,price,itemid,key,subtitle,itemurl));

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
                                    sendcontent.put("pin",zip);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    sendcontent.put("ship",Shipping);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    sendcontent.put("price",price);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    sendcontent.put("img_url",gallery);
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

                                recyclervie(lstitem);
//                                RecyclerView myrv = (RecyclerView) findViewById(R.id.recycler_view_id);
//                                RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this,lstitem);

                                out = out+" Itemid: "+ itemid + " title: "+title+"\n galleryURL: "+gallery+" \n zip: "+zip + " \n Shipping: "+Shipping+ " \n Condition: "+condition + "\n price"+ price+"\n";


                            }
                            //myAwesomeTextView.setText("Response: "+ out);

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error

                        }
                    });

// Add the request to the RequestQueue.
            queue.add(jsonObjectRequest);

        }
    }

    public void recyclervie(List<item> lstitem){
        RecyclerView myrv = (RecyclerView) findViewById(R.id.recycler_view_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this,lstitem);
        myrv.setLayoutManager(new GridLayoutManager(this, 2));
        myrv.setAdapter(myAdapter);
        //do something
    }
}
