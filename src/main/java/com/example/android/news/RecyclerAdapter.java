package com.example.android.news;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.attr.y;

public class RecyclerAdapter extends RecyclerView.Adapter {

    private ArrayList<Article> mArticles = new ArrayList<>();
    private RecyclerView mRecyclerView;

   private class MyViewHolder extends RecyclerView.ViewHolder{

       public TextView title;
       public TextView content;
       public TextView time;


       public MyViewHolder(View itemView) {
           super(itemView);

           title = (TextView) itemView.findViewById(R.id.title);
           content = (TextView) itemView.findViewById(R.id.content);
           time = (TextView) itemView.findViewById(R.id.time);
       }
   }


   public void addAll(ArrayList<Article> data){
       mArticles.addAll(data);
       notifyDataSetChanged();


   }
   public RecyclerAdapter(ArrayList<Article> articles,RecyclerView recyclerView){
       mArticles = articles;
       mRecyclerView = recyclerView;


   }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view,parent,false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mRecyclerView.getChildAdapterPosition(v);
                Article article = mArticles.get(position);
                Uri uri = Uri.parse(article.getWebUrl());
                Intent web = new Intent(Intent.ACTION_VIEW,uri);
                v.getContext().startActivity(web);



            }
        });

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Article article =  mArticles.get(position);
        ((MyViewHolder) holder).title.setText(article.getWebTitle());
        ((MyViewHolder) holder).content.setText(article.getSectionName());
        String full_time = article.getTime();
//        String date  = full_time.substring(5,10);
        String time = full_time.substring(11,16);
        String add = time.substring(1,2);
        Log.i("AAAAA",add);
        int add_int = Integer.parseInt(add);
        add_int += 2;
        String time2 = add_int + time.substring(2,5);

//        StringBuilder sb = new StringBuilder();
//        sb.append(date.charAt(3));
//        sb.append(date.charAt(4));
//        sb.append('.');
//        sb.append(date.charAt(0));
//        sb.append(date.charAt(1));
//        ((MyViewHolder) holder).time.setText(time + " " + sb.toString());
        ((MyViewHolder) holder).time.setText(time2);

    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }
}
