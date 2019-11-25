package com.example.android.viewpager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class itemdetail extends AppCompatActivity {
    TabLayout tabLayout;
    private int[] tabIcons = {
            R.drawable.information_variant,
            R.drawable.truck_delivery,
            R.drawable.google,
            R.drawable.equal
    };

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.CustomActionBarTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemdetail);


        Intent intent = getIntent();
        final String name = intent.getStringExtra(RecyclerViewAdapter.EXTRA_ITEMID);
        final String key = intent.getStringExtra(RecyclerViewAdapter.EXTRA_KEY);
        final String ship = intent.getStringExtra(RecyclerViewAdapter.EXTRA_SHIP);
        final String content = intent.getStringExtra(RecyclerViewAdapter.EXTRA_ITEMJSON);
        final String itemurl = intent.getStringExtra(RecyclerViewAdapter.EXTRA_URL);
        final String prodTitle = intent.getStringExtra(RecyclerViewAdapter.EXTRA_TITLE);

        final String price = intent.getStringExtra(RecyclerViewAdapter.EXTRA_PRICE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.prod_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle(prodTitle); // for set actionbar title
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView facebook = (ImageView) findViewById(R.id.facebook);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(productDetails.this,"I am clicked",Toast.LENGTH_LONG).show();
                Intent sharingIntent = new Intent(Intent.ACTION_VIEW);
                String appId= "2282286808676323";
                String stringToShare = "Buy"+ prodTitle+ " at " + "$" +price;
                // String url = "https://www.google.com";

                //  String url = "https://www.facebook.com/dialog/share?app_id=368212807366654&display=popup&href=https%3A%2F%2Fdevelopers.facebook.com%2Fdocs%2F&redirect_uri=https%3A%2F%2Fdevelopers.facebook.com%2Ftools%2Fexplorer";
                String url = "https://www.facebook.com/dialog/share?app_id=" + appId + "&display=popup"  + "&quote=" + stringToShare + "&href=" + itemurl + "&hashtag=%23CSCI571Spring2019Ebay";
                sharingIntent.setData(Uri.parse(url));


                //Intent chooserIntent = Intent.createChooser(sharingIntent, "Open in...");
                //chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{sharingIntent});
                startActivity(sharingIntent);
            }
        });

//        TextView myTextView = (TextView) findViewById(R.id.details);
//        myTextView.setText(name+"\n"+key);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        DetailFragmentPagerAdapter adapter = new DetailFragmentPagerAdapter(getSupportFragmentManager(), name , ship,key);

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        JSONObject json_content = null;
        final ImageButton img = (ImageButton) findViewById(R.id.button_add);
        SharedPreferences sharedpref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedpref.edit();
        if( sharedpref.getString(name, null) == null){
            img.setImageResource(R.drawable.cart_plus_org);
            img.setTag(R.drawable.cart_plus_org);
        }else{
            img.setImageResource(R.drawable.cart_remove_org);
            img.setTag(R.drawable.cart_remove_org);
        }
        try {
            json_content = new JSONObject(content);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JSONObject finalJson_content = json_content;
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SharedPreferences sharedPref = mContext.getPreferences(Context.MODE_PRIVATE);
                String finalTitle = "";
                try {
                    finalTitle = finalJson_content.getString("title");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String itemid = name;

                Integer resource = (Integer)img.getTag();
                if(resource == R.drawable.cart_plus_org){
                    img.setImageResource(R.drawable.cart_remove_org);
                    img.setTag(R.drawable.cart_remove_org);
                    Toast.makeText(view.getContext(), finalTitle +" was added to wishlist",Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedpref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedpref.edit();
                    editor.putString(itemid, content);
                    editor.commit();

//                    FragmentTransaction ft = ((AppCompatActivity)mContext).getSupportFragmentManager().beginTransaction();
//
//                    ft.detach(this).attach(this).commit();
                    // do something
                }else {
                    img.setImageResource(R.drawable.cart_plus_org);
                    img.setTag(R.drawable.cart_plus_org);
                    Toast.makeText(view.getContext(), finalTitle +" was removed from wishlist",Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedpref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedpref.edit();
                    editor.remove(itemid);
                    //editor.putString(itemid, content);
                    editor.commit();

                }

            }
        });


    }

}
