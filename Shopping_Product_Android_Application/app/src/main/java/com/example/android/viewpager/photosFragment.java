package com.example.android.viewpager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class photosFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<PhotosList> arreventList;
    private RecyclerGoogleCards adapter;
    private View view;

    public photosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_photos, container, false);

        String keyWord = getArguments().getString("key");
        String productId = getArguments().getString("itemid");

        recyclerView = (RecyclerView) view.findViewById(R.id.googlePhotosRV);
        recyclerView.setHasFixedSize(true);

        arreventList = new ArrayList<>();
        final RecyclerGoogleCards adapter = new RecyclerGoogleCards(getContext(), arreventList);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        //layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        //mAdapter = new MyAdapter(myDataset);
        recyclerView.setAdapter(adapter);

        final RequestQueue queue = Volley.newRequestQueue(getContext());

        String google_url = "https://product-ebay-search.appspot.com/photos?q="+keyWord;

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, google_url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray res = response.getJSONArray("items");

                            for (int i = 0; i < res.length(); i++) {
                                JSONObject jsonObj = res.getJSONObject(i);
                                PhotosList photosObj = new PhotosList();
                                photosObj.setImgLink(jsonObj.getString("link"));
                                arreventList.add(photosObj);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);

        return view;
    }


}
