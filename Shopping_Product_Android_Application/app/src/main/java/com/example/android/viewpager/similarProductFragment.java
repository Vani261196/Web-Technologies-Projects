package com.example.android.viewpager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class similarProductFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<similarProductList> arreventList;
    private List<similarProductList> arrCopy;
    private similarProductsAdapter adapter;


    public similarProductFragment() {
        // Required empty public constructor
    }

    String[] sort_array={"Default", "Name", "Price", "Days"};
    String[] order_array={"Ascending", "Descending"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_similar_product, container, false);

        String keyWord = getArguments().getString("key");
        String productId = getArguments().getString("itemid");

        recyclerView = (RecyclerView) view.findViewById(R.id.similarProductsRV);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        arreventList = new ArrayList<>();
        arrCopy = new ArrayList<>();
        adapter = new similarProductsAdapter(getContext(), arreventList);

        //Apply Sort dropdown
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        //layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        //mAdapter = new MyAdapter(myDataset);
        recyclerView.setAdapter(adapter);

        //final TextView result = (TextView) findViewById(R.id.active_text);
        final RequestQueue queue = Volley.newRequestQueue(getContext());

        final String similar_product_url = "https://product-ebay-search.appspot.com/getsimilar?itemId="+productId;

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, similar_product_url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray res = response.getJSONObject("getSimilarItemsResponse").getJSONObject("itemRecommendations").getJSONArray("item");

                            for (int i = 0; i < res.length(); i++) {
                                JSONObject jsonObj = res.getJSONObject(i);
                                similarProductList similarObj = new similarProductList();
                                similarObj.setImgLink(jsonObj.getString("imageURL"));
                                similarObj.setProductTitle(jsonObj.getString("title"));
                                //similarObj.setDaysLeft(jsonObj.getString("timeLeft"));
                                String Days = StringUtils.substringBetween(jsonObj.getString("timeLeft"), "P", "D");
                                similarObj.setDaysLeft(Days +"\tDays\tLeft");
                                similarObj.setebayLink(jsonObj.getString("viewItemURL"));
                                similarObj.setPrice(jsonObj.getJSONObject("buyItNowPrice").getString("__value__"));
                                similarObj.setShippingType(jsonObj.getJSONObject("shippingCost").getString("__value__"));
                                arreventList.add(similarObj);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for(int i=0; i<arreventList.size(); i++){
                            arrCopy.add(arreventList.get(i));
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        System.out.print(similar_product_url);

                    }
                });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
        final Spinner spinner1 = (Spinner) view.findViewById(R.id.sortById);
        ArrayAdapter<String> adapter1 = new ArrayAdapter( getActivity().getBaseContext(), android.R.layout.simple_spinner_item, sort_array);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        //Apply order dropdown
        final Spinner spinner2 = (Spinner) view.findViewById(R.id.orderById);
        ArrayAdapter<String> adapter2 = new ArrayAdapter(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, order_array);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        //When spinner 1 changes
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String sortby_val = spinner1.getSelectedItem().toString();
                String orderby_val = spinner2.getSelectedItem().toString();
                System.out.println("On 1 changed "+ sortby_val+" "+orderby_val);
                if(sortby_val.equals("Default")){
                    spinner2.setSelection(0);
                    spinner2.setEnabled(false);
                }else{
                    spinner2.setEnabled(true);
                }
                if(arreventList!= null){
                    sortFunction(sortby_val,orderby_val);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String sortby_val = spinner1.getSelectedItem().toString();
                String orderby_val = spinner2.getSelectedItem().toString();
                System.out.println("On 2 changed "+ sortby_val+" "+orderby_val);
                if(arreventList!= null){
                    sortFunction(sortby_val,orderby_val);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
        return view;
    }

    private void sortFunction(final String sortby_val, final String orderby_val) {
        System.out.println("SORTED VAL IN INNER FUNC: "+sortby_val);
        System.out.println("ORDER VAL IN INNER FUNC: "+orderby_val);
        Collections.sort(arreventList, new Comparator<similarProductList>() {
            @Override
            public int compare(similarProductList o1, similarProductList o2) {
                if(sortby_val.equals("Name"))
                    return o1.getproductTitle().compareTo(o2.getproductTitle());
                else if(sortby_val.equals("Price")){
//                    Double i1 = Double.parseDouble(o1.getPrice());
//                    Double i2 = Double.parseDouble(o2.getPrice());
                    return Double.parseDouble(o1.getPrice())>Double.parseDouble(o2.getPrice()) ? 1 : -1;
                }
//                else if(sortby_val.equals("Artist")){
//                    return o1.getArtist().compareTo(o2.getArtist());
//                }
//                else if(sortby_val.equals("Type")){
//                    return o1.getEtype().compareTo(o2.getEtype());
//                }else if(sortby_val.equals("Days")){
//                    SimpleDateFormat format=new SimpleDateFormat("yyyy-dd-MM");
//                    try{
//                        return format.parse(o1.getEdate()).compareTo(format.parse(o2.getEdate()));
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    return 0;
//                }
                else return 0;
            }
        });
//        for(int i=0; i < arreventList.size(); i++){
//            System.out.println(arreventList.get(i).getArtist());
//        }

        if(sortby_val.equals("Default")){
            for(int i=0; i<arreventList.size(); i++){
                arreventList.set(i, arrCopy.get(i));
            }
        }
        //if descending reverse too
        if(orderby_val.equals("Descending")){
            Collections.reverse(arreventList);
//            System.out.println("In descending order:");
//            for(int i=0; i<arreventList.size(); i++){
//                System.out.println(arreventList.get(i).getproductTitle());
//            }
        }
        if(adapter!=null) {
            adapter.notifyDataSetChanged();
        }
    }

}