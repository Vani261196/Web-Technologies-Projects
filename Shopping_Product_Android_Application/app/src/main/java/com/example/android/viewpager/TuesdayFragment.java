/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.viewpager;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Fragment that displays "Tuesday".
 */
public class TuesdayFragment extends Fragment {
    private List<item> lstitem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tuesday, container, false);


        SharedPreferences sharedpref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        SharedPreferences.Editor editor = sharedpref.edit();




        Map<String, ?> allEntries = sharedpref.getAll();
        TextView myAwesomeTextView = (TextView) view.findViewById(R.id.content);
        //myAwesomeTextView.setText(allEntries.toString());

        lstitem = new ArrayList<>();
        for(Map.Entry<String,?> entry : allEntries.entrySet()){
            String key = entry.getKey();
            String val = entry.getValue().toString();
            JSONObject json = null;
            try {
                json = new JSONObject(val);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String itemid = key;
            String title = null;
            try {
                title = json.getString("title");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String keyword = null;
            try {
                keyword = json.getString("key");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String zip = null;
            try {
                zip = json.getString("pin");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String Shipping = null;
            try {
                Shipping = json.getString("ship");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String condition = null;
            try {
                condition = json.getString("condition");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String price = null;
            try {
                price = json.getString("price");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            String gallery = null;
            try {
                gallery = json.getString("img_url");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String subtitle ="✔Charger ✔3 Day Shipping ✔A/B GRADE ✔1 Year Warranty";
            String itemURL =null;
            try{
                itemURL = json.getString("itemurl");
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

                    //final String content = "{"+ "title:"+title+",itemid:"+itemid+",key:"+key+",pin:"+pin+",ship:"+ship+",price"+price+",condition:"+condition+"}";
            lstitem.add(new item(title,gallery,zip,Shipping,condition,price,itemid,keyword,subtitle,itemURL));

        }
        int flag=0;
        if(lstitem.size()==0){
            TextView nowish = (TextView) view.findViewById(R.id.content);
            nowish.setText("No Wishes");
        }
        //myAwesomeTextView.setText(lstitem.toString());
        RecyclerView myrv = (RecyclerView) view.findViewById(R.id.recycler_view);
        wishlistadapter myAdapter = new wishlistadapter(getActivity().getApplicationContext(),lstitem, view,flag);
        myrv.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2));
        myrv.setAdapter(myAdapter);

        //myAdapter.getData().remove(0);
        myAdapter.notifyDataSetChanged();
        //recyclervie(lstitem,view);
        Double Total =0.0;
        for(item wish: lstitem){
             Total += Double.parseDouble(wish.getPrice());
        }
        TextView wishtotal = (TextView) view.findViewById(R.id.Total);
        wishtotal.setText("Wishlist total("+lstitem.size()+" items):                   $"+Double.toString(Total));





        return view;
    }

    public void recyclervie(List<item> lstitem, View view){
        RecyclerView myrv = (RecyclerView) view.findViewById(R.id.recycler_view);
        wishlistadapter myAdapter = new wishlistadapter(getActivity().getApplicationContext(),lstitem, view, 0);
        myrv.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2));
        myrv.setAdapter(myAdapter);

        //myAdapter.getData().remove(0);
        myAdapter.notifyDataSetChanged();
        //do something
    }
}
