package com.example.android.news;


import java.util.Random;

public class Article {
    private String mSectionName;
    private String mWebUrl;
    private String mWebTitle;
    private String mTime;

    public Article(String sectionName,String webTitle,String webUrl,String time){
        mSectionName = sectionName;
        mWebTitle = webTitle;
        mWebUrl = webUrl;
        mTime = time;

    }

    public String getSectionName(){ return mSectionName;};
    public String getWebUrl(){return mWebUrl;}
    public String getWebTitle(){return mWebTitle;}
    public String getTime(){return  mTime;};
}
