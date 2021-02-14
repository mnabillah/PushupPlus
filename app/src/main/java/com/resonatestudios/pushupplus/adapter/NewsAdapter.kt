package com.resonatestudios.pushupplus.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.resonatestudios.pushupplus.R;
import com.resonatestudios.pushupplus.model.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.Row> {
    private ArrayList<Article> articles;
    private Context context;

    public NewsAdapter(Context context) {
        this.context = context;
        articles = new ArrayList<>();
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }

    @NonNull
    @Override
    public Row onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new Row(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Row holder, int position) {
        final Article article = articles.get(position);

        holder.newsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openUrl = new Intent(Intent.ACTION_VIEW);
                openUrl.setData(Uri.parse(article.getUrl()));
                context.startActivity(openUrl);
            }
        });
        Picasso.get().load(article.getUrlToImage()).into(holder.imageViewImage);
        holder.textViewTitle.setText(article.getTitle());
        holder.textViewSource.setText(article.getSource().getName());
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class Row extends RecyclerView.ViewHolder {
        MaterialCardView newsCard;
        ImageView imageViewImage;
        TextView textViewTitle;
        TextView textViewSource;

        Row(@NonNull View itemView) {
            super(itemView);
            newsCard = itemView.findViewById(R.id.news_card);
            imageViewImage = itemView.findViewById(R.id.news_image);
            textViewTitle = itemView.findViewById(R.id.news_title);
            textViewSource = itemView.findViewById(R.id.news_source);
        }
    }
}
