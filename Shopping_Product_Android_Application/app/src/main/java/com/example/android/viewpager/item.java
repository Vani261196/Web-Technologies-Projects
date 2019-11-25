package com.example.android.viewpager;

import android.net.Uri;

public class item {
    private  String itemid;
    private String Title;
    private String img_src;
    private String zip;
    private String ship;
    private String state;
    private String price;
    private String keyword;
    private String subtitle;
    private String itemurl;

    public item(String title, String Img_src, String Zip, String Ship, String State, String Price,String Itemid, String key,String Subtitle, String Itemurl) {
        Title = title;
        img_src = Img_src;
        zip = Zip;
        ship = Ship;
        state = State;
        price = Price;
        itemid = Itemid;
        keyword = key;
        subtitle = Subtitle;
        itemurl = Itemurl;

    }

    public String getTitle() {
        return Title;
    }

    public String getItemurl() {
        return itemurl;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getItemid() {
        return itemid;
    }

    public Uri getImg_src() {
        return Uri.parse(img_src);
    }

    public String getZip() {
        return zip;
    }

    public String getShip() {
        return ship;
    }

    public String getState() {
        return state;
    }

    public String getPrice() {
        return price;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setImg_src(String img_src) {
        this.img_src = img_src;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setShip(String ship) {
        this.ship = ship;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setItemurl(String itemurl) {
        this.itemurl = itemurl;
    }
}
