package com.example.android.viewpager;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class viewPagerAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<String> imageURLs;

    viewPagerAdapter(Context context, ArrayList<String> imageURLs){
        this.context = context;
        this.imageURLs = imageURLs;
    }
    @Override
    public int getCount() {
        return imageURLs.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        Picasso.with(context).load(Uri.parse(imageURLs.get(position))).fit().into(imageView);
        container.addView(imageView);
        return imageView;


    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
