package com.resonatestudios.pushupplus.model

import com.google.gson.annotations.SerializedName
import java.util.*

class NewsResponse {
    @SerializedName("totalResults")
    var totalResults = 0

    @SerializedName("articles")
    var articles: ArrayList<Article>? = null
}