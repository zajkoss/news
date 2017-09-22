package com.example.android.news;

import android.content.Context;
import android.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by Lukasz on 2017-09-21.
 */

public class ArticleLoader extends AsyncTaskLoader<List<Article>> {
    private String mUrl;

    public ArticleLoader(Context context,String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Article> loadInBackground() {
       if(mUrl == null){
           return null;
       }
       List<Article> articleList = QueryUtils.fetchArticles(mUrl);
        return articleList;
    }
}
