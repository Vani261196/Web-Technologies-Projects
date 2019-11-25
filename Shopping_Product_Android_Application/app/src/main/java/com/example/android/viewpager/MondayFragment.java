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

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Fragment that displays "Monday".
 */
public class MondayFragment extends Fragment {
    public static final String EXTRA_URL = "com.example.android.viewpager.EXTRA_URL";
    public static final String EXTRA_NAME = "com.example.android.viewpager.EXTRA_NAME";
    private View view;
    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    private Handler handler;
    private AutosuggestAdapter autoSuggestAdapter;
    private String zipcode_data;


    public MondayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monday, container, false);
        Spinner spinner = (Spinner)view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.category_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final CheckBox enable_search = (CheckBox)view.findViewById(R.id.checkBox7);
        final EditText dist = (EditText)view.findViewById(R.id.distanceEditText);
        final TextView distText = (TextView)view.findViewById(R.id.distanceText);
        String distVal = dist.getText().toString();
        final RadioButton current = (RadioButton) view.findViewById(R.id.radioButton1);
        final RadioButton zipcode = (RadioButton) view.findViewById(R.id.radioButton2);
        final AppCompatAutoCompleteTextView zipBox = (AppCompatAutoCompleteTextView) view.findViewById(R.id.auto_complete_edit_text);

        final TextView from = (TextView) view.findViewById(R.id.fromText);

        enable_search.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    zipBox.setVisibility(View.VISIBLE);
                    distText.setVisibility(View.VISIBLE);
                    dist.setVisibility(View.VISIBLE);
                    current.setVisibility(View.VISIBLE);
                    zipcode.setVisibility(View.VISIBLE);
                    //zip_text.setVisibility(View.VISIBLE);
                    from.setVisibility(View.VISIBLE);
                }
                else
                {
                    zipBox.setVisibility(View.GONE);
                    distText.setVisibility(View.GONE);
                    dist.setVisibility(View.GONE);
                    current.setVisibility(View.GONE);
                    zipcode.setVisibility(View.GONE);
                    //zip_text.setVisibility(View.GONE);
                    from.setVisibility(View.GONE);
                }
            }
        });


        current.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    zipcode.setChecked(false);
                    current.setChecked(true);
                }



            }
        });

        zipcode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    current.setChecked(false);
                    zipcode.setChecked(true);
                }


            }
        });

        final AppCompatAutoCompleteTextView autoCompleteTextView = (AppCompatAutoCompleteTextView) view.findViewById(R.id.auto_complete_edit_text);
        //final TextView selectedText = view.findViewById(R.id.selected_item);

        //Setting up the adapter for AutoSuggest
        autoSuggestAdapter = new AutosuggestAdapter(getContext (), android.R.layout.simple_dropdown_item_1line);
        autoCompleteTextView.setThreshold(2);
        autoCompleteTextView.setAdapter(autoSuggestAdapter);
        autoCompleteTextView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        zipcode_data = autoSuggestAdapter.getObject(position).toString();
                    }
                });

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int
                    count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                        AUTO_COMPLETE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        handler = new Handler (new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == TRIGGER_AUTO_COMPLETE) {
                    if (!TextUtils.isEmpty(autoCompleteTextView.getText())) {
                        makeApiCall(autoCompleteTextView.getText().toString());
                    }
                }
                return false;
            }
        });

        Button button = (Button) view.findViewById(R.id.search);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intentToEventList = new Intent(getContext(), Searchresults.class);
                Bundle bundle_for_eventlist = new Bundle();
                //handle no zip
                TextView ziperror = (TextView)getView ().findViewById (R.id.zip_error);

                EditText txtname = (EditText)getView().findViewById(R.id.keyword);
                String name      =  txtname.getText().toString();

                final Spinner catspin = (Spinner) getView().findViewById(R.id.spinner);
                String catSpinVal = catspin.getSelectedItem().toString();

                final TextView KeywordError = (TextView)getView ().findViewById(R.id.keyword_error);

                final CheckBox responseCheckbox1 = (CheckBox) getView().findViewById(R.id.new1);
                boolean new1 = responseCheckbox1.isChecked();
                String new2 = Boolean.toString(new1);

                final CheckBox responseCheckbox2 = (CheckBox) getView().findViewById(R.id.used);
                boolean used1 = responseCheckbox2.isChecked();
                String used2 = Boolean.toString(used1);

                final CheckBox responseCheckbox3 = (CheckBox) getView().findViewById(R.id.unspecified);
                boolean unspecified1 = responseCheckbox3.isChecked();
                String unspecified2 = Boolean.toString(unspecified1);

                final CheckBox responseCheckbox4 = (CheckBox) getView().findViewById(R.id.local);
                boolean local1 = responseCheckbox4.isChecked();

                final CheckBox responseCheckbox5 = (CheckBox) getView().findViewById(R.id.free);
                boolean free1 = responseCheckbox5.isChecked();

                CheckBox check6 = (CheckBox) getView().findViewById(R.id.checkBox7);
                boolean enable = check6.isChecked();



                int anyError = 0;

                if(name.equals(""))
                {
                    anyError = 1;

                    KeywordError.setVisibility(View.VISIBLE);
                }
                else
                {
                    KeywordError.setVisibility(View.GONE);
                }
                String zip_string ="90007";
                if(enable) {
                    AppCompatAutoCompleteTextView zip = (AppCompatAutoCompleteTextView) getView().findViewById(R.id.auto_complete_edit_text);
                    zip_string = zip.getText().toString();

                    if (zip_string.equals("")) {
                        anyError = 1;
                        ziperror.setVisibility(View.VISIBLE);
                    } else {
                        ziperror.setVisibility(View.GONE);
                    }
                }

                //error logic for location


                if(anyError ==1 )
                {
                    Toast toast1 = Toast.makeText(getContext(), "Please fix all fields with errors" , Toast.LENGTH_SHORT);
                    toast1.show();
                }
                else {
                    int i=0;
                    String demo_url= "http://product-ebay-search.appspot.com/getProducts/?keywords="+name;



                    //Enable Search on click logic
                    String CatId = "";

                    if (catSpinVal.equals("Art")) {
                        CatId = "550";
                    } else if (catSpinVal.equals("Baby")) {
                        CatId = "2984";
                    } else if (catSpinVal.equals("Books")) {
                        CatId = "267";
                    }
                    else if (catSpinVal.equals("Clothing, Shoes and Accessories")) {
                        CatId = "11450";
                    }
                    else if (catSpinVal.equals("Computers, Tablets and Networking")) {
                        CatId = "58058";
                    }

                    else if (catSpinVal.equals("Health and Beauty")) {
                        CatId = "26395";
                    }
                    else if (catSpinVal.equals("Music")) {
                        CatId = "11233";
                    }
                    else if (catSpinVal.equals("Video Games and Consoles")) {
                        CatId = "1249";
                    }

                    else if (catSpinVal.equals("All")) {
                        CatId = "0";
                    }
                    if(CatId !="0"){
                        demo_url+="&categoryId=";
                        demo_url+=CatId;
                    }

                    bundle_for_eventlist.putString("Category", CatId);
                    bundle_for_eventlist.putString("miles", "10");
                    if (zipcode.isChecked()) {
                        bundle_for_eventlist.putString("ZipCode", zip_string);
                        demo_url+="&buyerPostalCode=";
                        demo_url+=zip_string;
                    } else {
                        bundle_for_eventlist.putString("ZipCode", "90007");
                        demo_url+="&buyerPostalCode=";
                        demo_url+="90007";
                    }

                    String distance = "10";
                    String dis = "&itemFilter("+i+").name=MaxDistance&itemFilter("+i+").value="+distance;
                    i=i+1;
                    demo_url+=dis;

                    String slocal, sfree;

                    if (local1 == true) {
                        String str_loc = "&itemFilter("+i+").name=LocalPickupOnly&itemFilter("+i+").value=true";
                        i =i+1;
                        demo_url+=str_loc;
                        slocal = "true";
                        sfree = "false";
                    }

                    if (free1 == true) {
                        String str_free ="&itemFilter("+i+").name=FreeShippingOnly&itemFilter("+i+").value=true";
                        i=i+1;
                        // strQuery+="&Free="+$scope.selectedShipping.free;
                        demo_url+=str_free;
                        slocal = "false";
                        sfree = "true";

                    }
//                    else if(local1 == true && free1 == true){
//                        slocal = "true";
//                        sfree = "true";
//                    }
//                    else {
//                        slocal = "false";
//                        sfree = "false";
//
//                    }

                    ArrayList<String> arr = new ArrayList<>();
                    int len = arr.size();
                    String url_add ="";

                    if(new1){
                        arr.add("New");
                        //url_contents+="&New="+$scope.selectedCondition.new;
                    }
                    if(used1){
                        arr.add("Used");
                        //url_contents+="&Used="+$scope.selectedCondition.used;
                    }
                    if(unspecified1){
                        arr.add("Unspecified");
                        //url_contents+="&Unspecified="+$scope.selectedCondition.unspecified;
                    }
                    len = arr.size();
                    if(len==1)
                    {
                        url_add+= "&itemFilter("+i+").name=Condition&itemFilter("+i+").value(0)="+arr.get(0);
                        //$url= $url."&itemFilter(".$i.").name=Condition&itemFilter(".$i.").value(0)=".$condition[0];
                        //$i=$i+1;
                        i=i+1;
                    }
                    else if(len==2){
                        url_add+= "&itemFilter("+i+").name=Condition&itemFilter("+i+").value(0)="+arr.get(0);
                        url_add+="&itemFilter("+i+").value(1)="+arr.get(1);
                        i=i+1;
                        // $url= $url."&itemFilter(".$i.").name=Condition&itemFilter(".$i.").value(0)=".$condition[0];
                        // $url= $url."&itemFilter(".$i.").value(1)=".$condition[1];
                        // $i=$i+1;
                    }
                    else if(len==3)
                    {
                        url_add+= "&itemFilter("+i+").name=Condition&itemFilter("+i+").value(0)="+arr.get(0);
                        url_add+="&itemFilter("+i+").value(1)="+arr.get(1);
                        url_add+="&itemFilter("+i+").value(2)="+arr.get(2);
                        i=i+1;
                        // 	$url= $url."&itemFilter(".$i.").name=Condition&itemFilter(".$i.").value(0)=".$condition[0];
                        // $url= $url."&itemFilter(".$i.").value(1)=".$condition[1];
                        // $url= $url."&itemFilter(".$i.").value(2)=".$condition[2];
                        // $i=$i+1;
                    }
                    //console.log(url_add);
                    demo_url+=url_add;

//                    bundle_for_eventlist.putString("keyword", name);
//                    bundle_for_eventlist.putString("local", slocal);
//                    bundle_for_eventlist.putString("free", sfree);
//                    bundle_for_eventlist.putString("New", new2);
//                    bundle_for_eventlist.putString("Used", used2);
//                    bundle_for_eventlist.putString("Unspecified", unspecified2);

                    String url = "http://product-ebay-search.appspot.com/getProducts/?keywords="+name+"&buyerPostalCode=90007&itemFilter(0).name=MaxDistance&itemFilter(0).value=10";
                    //Create url dynamically
                    //String demo_url= "http://product-ebay-search.appspot.com/getProducts/?keywords="+name+"&buyerPostalCode="+x;
                    TextView myAwesomeTextView = (TextView) getView().findViewById(R.id.result);
                    myAwesomeTextView = (TextView) getView().findViewById(R.id.result);
                    myAwesomeTextView.setText(demo_url);

                    //intentToEventList.putExtras(bundle_for_eventlist);
                    intentToEventList = new Intent(getContext(), Searchresults.class);
                    intentToEventList.putExtra(EXTRA_URL, demo_url);
                    intentToEventList.putExtra(EXTRA_NAME,name);
                    startActivity(intentToEventList);


                }









//                intentToEventList.putExtras(bundle_for_eventlist);
//                intentToEventList.putExtra(EXTRA_URL, url);
//                startActivity(intentToEventList);
                //searchresult(name);

                //String url = "http://my-json-feed";

//                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
//                //String url ="http://www.google.com";
//
//// Request a string response from the provided URL.
//                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                // Display the first 500 characters of the response string.
//                                myAwesomeTextView.setText("Response is: "+ response.substring(0,500));
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        myAwesomeTextView.setText("That didn't work!");
//                    }
//                });
//
//                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                myAwesomeTextView.setText("Response: " + response.toString());
//                            }
//                        }, new Response.ErrorListener() {
//
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                // TODO: Handle error
//
//                            }
//                        });
//
//// Add the request to the RequestQueue.
//                queue.add(jsonObjectRequest);
            }


        });

            Button clearBtn = (Button) view.findViewById(R.id.clearButton);
        clearBtn.setOnClickListener(new View.OnClickListener(){

        @Override
        public void onClick (View view){
            EditText txtname = (EditText)getView().findViewById(R.id.keyword);
        //EditText okeyword = (EditText) view.findViewById(R.id.keyword);
        txtname.getText().clear();

        TextView keyError = (TextView) getView().findViewById(R.id.keyword_error);
        keyError.setVisibility(View.GONE);
//
        Spinner ospinner1 = (Spinner) getView().findViewById(R.id.spinner);
        ospinner1.setSelection(0);
//
        CheckBox check1 = (CheckBox) getView().findViewById(R.id.new1);
        check1.setChecked(false);
        CheckBox check2 = (CheckBox) getView().findViewById(R.id.used);
        check2.setChecked(false);
        CheckBox check3 = (CheckBox) getView().findViewById(R.id.unspecified);
        check3.setChecked(false);

        CheckBox check4 = (CheckBox) getView().findViewById(R.id.local);
        check4.setChecked(false);
        CheckBox check5 = (CheckBox) getView().findViewById(R.id.free);
        check5.setChecked(false);
//
        CheckBox check6 = (CheckBox) getView().findViewById(R.id.checkBox7);
        check6.setChecked(false);
//
        EditText odistance = (EditText) getView().findViewById(R.id.distanceEditText);
        odistance.getText().clear();
        odistance.setVisibility(View.GONE);
//
        RadioButton radio1 = (RadioButton) getView().findViewById(R.id.radioButton1);
        radio1.setChecked(true);
        radio1.setVisibility(View.GONE);
//
        RadioButton radio2 = (RadioButton) getView().findViewById(R.id.radioButton2);
        radio2.setChecked(false);
        radio2.setVisibility(View.GONE);
//
        EditText olocationtext = (EditText) getView().findViewById(R.id.auto_complete_edit_text);
        olocationtext.setText("");
        olocationtext.setEnabled(false);
        olocationtext.setFocusable(false);
        olocationtext.setFocusableInTouchMode(false);
        olocationtext.setVisibility(View.GONE);
//
        TextView olocationError = (TextView) getView().findViewById(R.id.zip_error);
        olocationError.setVisibility(View.GONE);

    }
    });
        return view;
    }

    public void searchresult(String url){
        Intent intent = new Intent(getActivity().getApplicationContext() , Searchresults.class);
        intent.putExtra(EXTRA_URL, url);
        startActivity(intent);

    }

    private void makeApiCall(String text) {
        ApiCall.make(getContext (), text, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //parsing logic, please change it as per your requirement
                List<String> stringList = new ArrayList<>();
                try {
                    JSONObject responseObject = new JSONObject(response);
                    JSONArray array = responseObject.getJSONArray("postalCodes");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject row = array.getJSONObject(i);
                        stringList.add(row.getString("postalCode"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //IMPORTANT: set data here and notify
                autoSuggestAdapter.setData(stringList);
                autoSuggestAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

//    public void onButtonClickSearch(View view) {
//        EditText txtname = (EditText)view.findViewById(R.id.keyword);
//        String name      =  txtname.getText().toString();
//        TextView myAwesomeTextView = (TextView) view.findViewById(R.id.result);
//        myAwesomeTextView.setText(name);
//    }


}
