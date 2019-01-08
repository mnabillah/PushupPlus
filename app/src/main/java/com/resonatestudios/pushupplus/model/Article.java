package com.resonatestudios.pushupplus.model;

import com.google.gson.annotations.SerializedName;

public class Article {
    @SerializedName("source")
    private Article.Source source;
    @SerializedName("title")
    private String title;
    @SerializedName("url")
    private String url;
    @SerializedName("urlToImage")
    private String urlToImage;

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public class Source {
        @SerializedName("name")
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
