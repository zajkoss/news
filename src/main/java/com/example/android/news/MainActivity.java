package com.example.android.news;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.content.AsyncTaskLoader;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static android.os.Build.VERSION_CODES.N;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Article>> {

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    private Toolbar mToolbar;
    boolean doListView = true;
    private final String REQUEST_URL = this.getResources().getString(R.string.request_url);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

                if(item.getItemId() == R.id.refresh){
                    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                    if(networkInfo != null && networkInfo.isConnected()){

                        getLoaderManager().restartLoader(0,null,this);

                    }else{
                        Snackbar.make(findViewById(android.R.id.content),"No Internet coonection",Snackbar.LENGTH_SHORT).show();
                    }
                }else if(item.getItemId() == R.id.view){
                    if(doListView == false){
                        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                        doListView = true;
                    }else if(doListView == true){

                        recyclerView.setLayoutManager(new LinearLayoutManager(this));
                        doListView = false;
                    }
                }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);


        //Toolbar
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

        //Check INTERNET CONNECTION
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            getLoaderManager().initLoader(0,null,this);
        }else{
//            View loading = findViewById(R.id.loading);
            Snackbar.make(findViewById(android.R.id.content),"No Internet coonection",Snackbar.LENGTH_SHORT).show();
        }




        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //Comment this
        recyclerView.setHasFixedSize(true);
        //Simple List
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());;
        recyclerAdapter = new RecyclerAdapter(new ArrayList<Article>(),recyclerView);
        recyclerView.setAdapter(recyclerAdapter);






    }


    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {
        return new ArticleLoader(this,REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> data) {
        View loading = findViewById(R.id.loading);
        loading.setVisibility(View.GONE);

        recyclerAdapter.addAll((ArrayList<Article>) data);
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
       loader.reset();
    }
}


