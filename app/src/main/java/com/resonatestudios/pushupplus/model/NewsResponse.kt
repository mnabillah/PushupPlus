package com.resonatestudios.pushupplus.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NewsResponse {
    @SerializedName("totalResults")
    private int totalResults;
    @SerializedName("articles")
    private ArrayList<Article> articles;

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }
}
