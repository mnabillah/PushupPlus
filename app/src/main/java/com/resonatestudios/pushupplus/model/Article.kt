package com.resonatestudios.pushupplus.model

import com.google.gson.annotations.SerializedName

class Article {
    @SerializedName("source")
    var source: Source? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("url")
    var url: String? = null

    @SerializedName("urlToImage")
    var urlToImage: String? = null

    inner class Source {
        @SerializedName("name")
        var name: String? = null
    }
}