package com.example.android.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class DetailFragmentPagerAdapter extends FragmentPagerAdapter {
    private String itemidfrag;
    private String Ship;
    private String Key;

    public DetailFragmentPagerAdapter(FragmentManager fm, String Itemid ,String ship, String key) {
        super(fm);
        itemidfrag = Itemid;
        Ship = ship;
        Key = key;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            Bundle bundle = new Bundle();
            bundle.putString("itemid", itemidfrag);
            bundle.putString("ship", Ship);
// set Fragmentclass Arguments
            Product fragobj = new Product();
            fragobj.setArguments(bundle);
            //return new MondayFragment();
            return fragobj;
        } else if(position == 1){
            Bundle bundle = new Bundle();
            bundle.putString("itemid", itemidfrag);
            bundle.putString("ship", Ship);
// set Fragmentclass Arguments
            Shipping fragobj = new Shipping();
            fragobj.setArguments(bundle);
            //return new MondayFragment();
            return fragobj;

        }
        else if(position == 2){
            Bundle bundle = new Bundle();
            bundle.putString("itemid", itemidfrag);
            bundle.putString("ship", Ship);
            bundle.putString("key", Key);
// set Fragmentclass Arguments
            photosFragment fragobj = new photosFragment();
            fragobj.setArguments(bundle);
            //return new MondayFragment();
            return fragobj;

        }

        else{
            Bundle bundle = new Bundle();
            bundle.putString("itemid", itemidfrag);
            bundle.putString("ship", Ship);
            bundle.putString("key", Key);
// set Fragmentclass Arguments
            similarProductFragment fragobj = new similarProductFragment();
            fragobj.setArguments(bundle);
            //return new MondayFragment();
            return fragobj;

        }

    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0){
            return "PRODUCT";
        }
        else if(position==1){
            return "SHIPPING";
        }
        else if(position==2){
            return "PHOTOS";
        }
        else{
            return "SIMILAR";
        }
//        return super.getPageTitle(position);
    }
}
