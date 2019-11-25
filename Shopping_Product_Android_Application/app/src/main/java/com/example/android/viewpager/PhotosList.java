package com.example.android.viewpager;

import android.net.Uri;

public class PhotosList {
    private String imgLink;

    public PhotosList(){
        this.imgLink= imgLink;
    }

    public Uri getImgLink() {
        return Uri.parse(imgLink);
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

}
