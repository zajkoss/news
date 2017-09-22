package com.example.android.news;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


/**
 * Created by Lukasz on 2017-09-21.
 */

public final class QueryUtils {
    private static final String LOG_TAG =  QueryUtils.class.getSimpleName();

    private QueryUtils(){

    }


    public static List<Article> fetchArticles(String requestUrl){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        URL url = createUrl(requestUrl);
        String json = null;
        json = makeHttpRequest(url);
        List<Article> articleList = extractJson(json);
        return articleList;
    }

    private static URL createUrl(String s){
        URL url = null;
        try{
            url =new URL(s);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG,"Problem z budowa URL",e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url){
        String json = "";
        if(url == null){
            return json;
        }
        HttpsURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                inputStream = urlConnection.getInputStream();
                json = readFromStream(inputStream);
            }
        }catch(IOException e){

        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return json;
    }


    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Article> extractJson(String json){
        if (TextUtils.isEmpty(json)) {
            return null;
//            Log.i(LOG_TAG,"Empty JSON ");
        }
        Log.i(LOG_TAG,"" + json);
        List<Article> articleList =  new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonObject1 = jsonObject.getJSONObject("response");
            JSONArray jsonArray = jsonObject1.getJSONArray("results");
            for(int i = 0;i< jsonArray.length();i++){
                JSONObject currentObject = jsonArray.getJSONObject(i);
                String sectionName = currentObject.getString("sectionName");
                String webTitle = currentObject.getString("webTitle");
                String url = currentObject.getString("webUrl");
                String time = currentObject.getString("webPublicationDate");
                Log.i(LOG_TAG,"NOWY ARTYKUL  " + sectionName + " " + webTitle);
                Article article = new Article(sectionName,webTitle,url,time);
                articleList.add(article);

            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem z JSON", e);
        }
        return articleList;

    }
}
